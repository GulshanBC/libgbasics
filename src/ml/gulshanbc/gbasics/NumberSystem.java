package ml.gulshanbc.gbasics;

public class NumberSystem {

    public static String DecimalToBinary(int decimal, int numOfBits) {
        String binaryString = "";

        int bit = 0;
        while (decimal > 0) {
            bit = decimal % 2;
            binaryString = bit + binaryString;
            decimal = decimal / 2;
        }

        if (binaryString.length() < numOfBits) {
            for (int i = binaryString.length(); i < numOfBits; i++) {
                binaryString = "0" + binaryString;
            }
        }
        return binaryString;
    }
}
