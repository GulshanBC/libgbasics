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
public class OptimizedArrayListIterator {
    String currentRoot = null;
    String currentPath = null;
    int currentIndex = 2;
    ArrayList<String> optimizedArrayList;
    
    public OptimizedArrayListIterator(ArrayList<String> optimizedArrayList){
        this.optimizedArrayList = optimizedArrayList;
        if (optimizedArrayList.get(0).contentEquals("/*Is an optimized Index File*/")) {                
                currentRoot = optimizedArrayList.get(1);
        }        
    }
    
    public boolean hasNext(){
        if(currentIndex<optimizedArrayList.size()-1) return true;//minus 1 because first line is /*Is an optimized Index File*/
        return false;
    }
    
    public String getNext(){
        try {
            if (!optimizedArrayList.get(0).contentEquals("/*Is an optimized Index File*/")) {                
                return null;
            }
            String path = currentRoot;
            String readLine;
            String filePath = "";
            String oldPath = currentPath;
            int countOfMinus = 0;
            //for(int h = 2; h<optimizedArrayList.size()-1; h++)//readLine = br.readLine()) != null) {
            {
                readLine = optimizedArrayList.get(currentIndex);
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
                    }
                    //GConsoleTextArea.println("path after rmoving + sign :"+path);
                } else {
                    filePath = path + readLine;
                    currentPath = filePath;
                    //GConsoleTextArea.println(">>"+filePath);
                    //files.add(filePath);                    
                }
                currentRoot = path;
                currentIndex++;
                //when there are lines with +or- sign, it signifies change in root
                //and it causes currentPath to be blank for that line
                //so instead of returning blank line, return next line
                if(currentPath==null || currentPath.equals(oldPath)) {
                    System.out.println("currentIndex="+currentIndex);
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
            //StackTraceElement[] stes= e.getStackTrace();            for( Object ste: stes)                GConsoleTextArea.println(ste);
        }
        return null;
    }
}
