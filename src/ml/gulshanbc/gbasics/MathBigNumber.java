/*
 * Written By: Gulshan Bhagat Chaurasia
 * Date Written: 6 June 2021 @date
 * Date Modified: 6 June 2021 @date
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ml.gulshanbc.gbasics;

/**
 *
 * @author Gulshan <Gulshan at gulsanbc@gmail.com>
 */

 /** //w w  w .j a  v a  2 s . com
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

import java.math.BigDecimal;

import java.math.BigDecimal;

public class MathBigNumber {
    /**
     * Raises e to the power of the input big decimal.
     * @param    BigDecimal    the input power
     * @return                 the result
     */
    public static BigDecimal exp(BigDecimal power) {

        int precision = power.precision();

        MathContext context = new MathContext(precision, RoundingMode.HALF_DOWN);

        return exp(power, context);
    }
    
    public static BigDecimal exp(BigDecimal power, int precision) {
        
        MathContext context = new MathContext(precision, RoundingMode.HALF_DOWN);

        return exp(power, context);
    }

    /**
     * Returns e raised to the input power using a Taylor expansion with
     * @param    BigDecimal     the power
     * @param    MathContext    the math context
     * @return                  e raised to the input power
     */
    public static BigDecimal exp(BigDecimal input, MathContext context) {

        int taylorTerms = 8;

        if (input.compareTo(BigDecimal.ZERO) < 0) {
            BigDecimal inverse = exp(input.negate(), context);
            return BigDecimal.ONE.divide(inverse, context);

        } else if (input.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ONE.setScale(context.getPrecision(), RoundingMode.HALF_DOWN);

        } else {
            double inputDouble = input.doubleValue();
//            System.out.println("inputDouble="+inputDouble);
            BigDecimal inputUlp = input.ulp();
            /**
             * compareTo returns 0 when both are equal
             * returns -1 if leftSide is less than rightSide
             * returns 1 if leftSide is greater than rightSide
             */
            if(input.ulp().compareTo(new BigDecimal("1E-138"))<=0) inputUlp = new BigDecimal("1.0E-121");
            //else System.out.println("else of input.ulp()="+input.ulp());
            double inputUlpDouble = inputUlp.doubleValue();
            
            double taylorNumberCheck1 = Math.pow(inputDouble, taylorTerms);
            double taylorNumberCheck2 = taylorTerms * (taylorTerms - 1.0) * (taylorTerms - 2.0) * inputUlpDouble;

            if (taylorNumberCheck1 < taylorNumberCheck2) {
                BigDecimal result = BigDecimal.ONE;
                BigDecimal inputPowerNum = BigDecimal.ONE;
                BigInteger factorialNum = BigInteger.ONE;

                //int taylorPrecision = 1 + (int) (Math.log10(Math.abs(0.5 / (inputUlpDouble / taylorTerms))));
                int taylorPrecision = context.getPrecision();
                MathContext taylorContext = new MathContext(taylorPrecision);

                for (int i = 1; i <= taylorTerms; i++) {

                    factorialNum = factorialNum.multiply(new BigInteger(i + ""));
                    inputPowerNum = inputPowerNum.multiply(input);

                    BigDecimal c = inputPowerNum.divide(new BigDecimal(factorialNum), taylorContext);
                    result = result.add(c);

                    double taylorNumberCheck3 = Math.abs(inputPowerNum.doubleValue());
                    double taylorNumberCheck4 = Math.abs(c.doubleValue());
                    double taylorNumberCheck5 = inputUlpDouble / 2;

                    if (taylorNumberCheck3 < i && taylorNumberCheck4 < taylorNumberCheck5) {
                        break;
                    }
                    
                }

//                int finalPrecision = 1 + (int) (Math.log10(Math.abs(0.5 / (inputUlpDouble / 2.0))));
//                
//                MathContext finalContext = new MathContext(6);
                    return result;
            } else {
                double taylorProduct = taylorTerms * (taylorTerms - 1.0) * (taylorTerms - 2.0) * inputUlpDouble;
                double taylorPower = Math.pow(inputDouble, taylorTerms);
//                System.out.println("inputUlpDouble="+inputUlpDouble);
//                System.out.println("taylorProduct="+taylorProduct);
//                System.out.println("taylorPower="+taylorPower);
                Double scaleBy10Double = 1.0 - Math.log10(taylorProduct / taylorPower) / (taylorTerms - 1.0);
                int scaleBy10 = scaleBy10Double.intValue();
//                System.out.println("Math.log10(taylorProduct / taylorPower)="+Math.log10(taylorProduct / taylorPower));
//                System.out.println("(taylorTerms - 1.0)="+(taylorTerms - 1.0));
//                System.out.println("scaleBy10Double="+scaleBy10Double);
//                System.out.println("scaleBy10="+scaleBy10);
                BigDecimal inputRaised10 = input.scaleByPowerOfTen(-scaleBy10);
                BigDecimal expxby10 = exp(inputRaised10, context);

                MathContext contextRaised10 = new MathContext(expxby10.precision() - scaleBy10);

                while (scaleBy10 > 0) {

                    int minimum = Math.min(8, scaleBy10);
                    scaleBy10 -= minimum;

                    MathContext minContext = new MathContext(expxby10.precision() - minimum + 2);
                    int precisionMin = 1;

                    while (minimum-- > 0) {
                        precisionMin *= 10;
                    }

                    expxby10 = expxby10.pow(precisionMin, minContext);

                }

                return expxby10.round(contextRaised10);
            }
        }
    }

    /** 
    * Raises the base to the power and returns the result. Calculates the power
    * by finding the natural log using a Taylor sequence and exp using Newton's
    * method of approximating the nth root.
    * @param    BigDecimal base    the base
    * @param    BigDecimal power   the power
    * @return            the result
     */
    static public BigDecimal pow(BigDecimal base, BigDecimal power) {

        int precisionBase = base.precision();
        int precisionPower = power.precision();
        int precision = precisionBase < precisionPower ? precisionBase : precisionPower;

        MathContext context = new MathContext(precision, RoundingMode.HALF_DOWN);

        return pow(base, power, context);
    }

    /** 
     * Raises the base to the power and returns the result. Calculates the power
    * by finding the natural log using a Taylor sequence and exp using Newton's
    * method of approximating the nth root.
     * @param    BigDecimal     the base
     * @param    BigDecimal     the power
     * @param    MathContext    the math context
     * @return                  the result
     */
    static public BigDecimal pow(BigDecimal base, BigDecimal power, MathContext context) {

        if (base.compareTo(BigDecimal.ZERO) < 0) {
            throw new ArithmeticException("Base cannot be negative.");
        }

        if (base.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal result = ln(base);
        result = power.multiply(result, context);
        result = exp(result, context);

        return result;
    }

    /**
     * Calculates the natural logarithm using a Taylor sequqnce.
     * @param    BigDecimal    the input big decimal > 0
     * @return                 the natural logarithm
     */
    public static BigDecimal ln(BigDecimal input) {

        int precision = input.precision();
        MathContext context = new MathContext(precision, RoundingMode.HALF_DOWN);

        return ln(input, context);
    }

    /**
     * Calculates the natural logarithm using a Taylor sequqnce.
     * @param    BigDecimal    the input big decimal > 0
     * @return                 the natural logarithm
     */
    public static BigDecimal ln(BigDecimal input, MathContext context) {

        BigDecimal two = new BigDecimal("2", context);

        BigDecimal inputMinus = input.subtract(BigDecimal.ONE, context);
        BigDecimal inputPlus = input.add(BigDecimal.ONE, context);
        BigDecimal y = inputMinus.divide(inputPlus, context);

        BigDecimal result = new BigDecimal("0", context);
        BigDecimal last = new BigDecimal(result.toString(), context);

        int k = 0;
        while (true) {

            BigDecimal argumentOne = BigDecimal.ONE
                    .divide(BigDecimal.ONE.add(two.multiply(new BigDecimal(k), context), context), context);
            BigDecimal argumentTwo = y.pow(k * 2, context);
            result = result.add(argumentOne.multiply(argumentTwo, context), context);

            if (last.equals(result)) {
                break;
            }

            last = new BigDecimal(result.toString(), context);
            k++;
        }

        return y.multiply(two, context).multiply(result, context);
    }
}   
