/*
 * Written By: Gulshan Bhagat Chaurasia
 * Date Written: 24 April 2021 @date
 * Date Modified: 24 April 2021 @date
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ml.gulshanbc.gbasics;

import java.util.ArrayList;

/*Is an optimized Index File*/
/*is the first line of every optimized index file or ArrayList
 *second line is the rootPath
 *last line is the curRootPath that makes appending possible without having to
 *iterate through all the lines
 * all the functions related to optimized Index File/ArrayList is present in
 * IndexFiles.java file!!
 * @author Gulshan <Gulshan at gulsanbc@gmail.com>
 */
public class GOptimizedPathFileIterator {
    String currentPath = null;
    String readLine = "";
    String readLineNext = "";
    int currentIndex = 2;
    String lastRoot = "";
    boolean alwaysReturnFalseForHasNext = false;
    
    GOptimizedPathFile goif = null;
    
    public GOptimizedPathFileIterator(GOptimizedPathFile goif) {
         this.goif = goif;  
         resetIterator();
    }
    
    public boolean resetIterator(){
        System.out.println("resetIterator() "+goif.file.getName());
        goif.closeReader();
        goif.openReader();
        System.out.println("files length in resetIterator "+goif.file.length());
        if(goif.file.length()<="/*Is an optimized Index File*/".length()) {
            alwaysReturnFalseForHasNext = true;
            return false;
        }
        try{
        if (!goif.readLine().contentEquals("/*Is an optimized Index File*/")) {                
                return false;
        }
        lastRoot = goif.readLine();
        
        currentIndex=2;
        hasNext();
        readLine=readLineNext;
        return true;
        }catch(Exception e){e.printStackTrace();}//try{Thread.sleep(30000);}catch(Exception e2){}
        return false;
    }
    
    public boolean hasNext(){
        if(alwaysReturnFalseForHasNext) return false;
        try{
        if((readLineNext=goif.readLine())!=null){
            if(readLineNext.charAt(0)=='/'||readLineNext.charAt(0)=='-'||readLineNext.charAt(0)=='+'){                
                return true;
            }
            else return hasNext();
        }
        }catch(Exception e){e.printStackTrace();}
        return false;
    }
    
    public String getNext(){
        try {
            
            String path = lastRoot;
            String oldPath = currentPath;
            int countOfMinus = 0;
            //for(int h = 2; h<optimizedArrayList.size()-1; h++)//readLine = br.readLine()) != null) {
            {
                if (readLine.charAt(0) == '+') {
                    path = path + readLine.substring(1);
                    //GConsoleTextArea.println("path after removing only + :"+path);
                } else if (readLine.charAt(0) == '-') {
                    countOfMinus = 0;
                    for (int i = 0; i < readLine.length(); i++) {
                        if (readLine.charAt(i) == '-') {
                            countOfMinus++;
                        } else {
                            break;
                        }
                    }
                    readLine = readLine.substring(countOfMinus);
                    //GConsoleTextArea.println("after removing - sign :"+readLine);
                    for (int i = 0; i < countOfMinus; i++) {
                        path = path.substring(0, path.lastIndexOf("/"));
                    }
                    //GConsoleTextArea.println("path after removing extra folders "+path);
                    if (readLine.charAt(0) == '+') {
                        path = path + readLine.substring(1);
                    }else{//added this else part for special case, this occurs when added path has no folder, i.e. its path  is /filename
                        if(path.length()==0) {
                            String temp = readLine;
                            lastRoot = path;
                            currentIndex++;
                            readLine=readLineNext;
                            return temp;
                        }
                    }
                    //GConsoleTextArea.println("path after rmoving + sign :"+path);
                } else {
                    currentPath = path + readLine;
                    //GConsoleTextArea.println(">>"+filePath);
                    //files.add(filePath);                    
                }
                lastRoot = path;
                currentIndex++;
                readLine=readLineNext;
                //when there are lines with +or- sign, it signifies change in root
                //and it causes currentPath to be blank for that line
                //so instead of returning blank line, return next line
                if(currentPath==null || currentPath.equals(oldPath)) {
                    //System.out.println(lastRoot+"=lastRoot, oldPath="+oldPath+", currentIndex="+currentIndex+", currentPath="+currentPath);
                    if(hasNext()) return getNext();
                }                                
            }

//            String[] filesArray = new String[files.size()];
//            for (int i = 0; i < filesArray.length; i++) {
//                filesArray[i] = files.get(i);
//            }
            //files.toArray(filesArray);
            
            return currentPath;
        } catch (Exception e) {
            e.printStackTrace();
            //StackTraceElement[] stes= e.getStackTrace();            for( Object ste: stes)                System.out.println(ste);
        }
        return null;
    }

}
