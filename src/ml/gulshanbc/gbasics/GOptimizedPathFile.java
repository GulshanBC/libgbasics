/*
 * Written By: Gulshan Bhagat Chaurasia
 * Date Written: 9 November 2020 @date
 * Date Modified: 10 November 2020 @date
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ml.gulshanbc.gbasics;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import ml.gulshanbc.gbasics.GString;

/**
 *  When you create a new GOptimizedPathFile, just open the Writer and use 
  addPath() function to add paths in the optimized index way
 
  however, if the file already exists(i.e you are opening a file that has already
  been written to and has been closed using closeReader()), you need to reopen it
  for appending using function reopenForAppending() and use addPath() function to add paths
 
  use GOptimizedIndexFileIterator to read paths back
  use readLine() to read the coded(formatted) lines as they are stored in the file
 
  example: when /home/gulshan/test.txt is an already existing optimized file,
 
                   GOptimizedPathFile oif = new GOptimizedPathFile("/home/gulshan/test.txt");
                     if(oif.reopenForAppending())
                     {
                         oif.addPath("/home/gulshan/test/path");
                         oif.closeWriter();
                         
                         GOptimizedIndexFileIterator goifi = new GOptimizedIndexFileIterator(oif);
                         while(goifi.hasNext())
                             System.out.println(goifi.getNext());
                     }
                     else System.out.println("unable fot reopen for appending");
                     
                     
    example: creating new optimized file
                    GOptimizedPathFile oif2 = new GOptimizedPathFile("/home/gulshan/test3.txt");
                    oif2.openWriter();
                    oif2.addPath("/home/gulshan/covid/virus.txt");
                    oif2.addPath("/home/gulshan/covid/strain.txt");
                    oif2.addPath("/home/gulshan/hero/features.txt");
                    oif2.addPath("/home/gulshan/covid/remedy/medication.txt");
                    oif2.addPath("/home/gulshan/hero/fly.txt");
                    oif2.addPath("/media/gulshan/hero/fly.txt");
                    oif2.addPath("/media/gulshan/hero/fly2.txt");
                    oif2.addPath("/something/dragonfly.txt");
                    oif2.addPath("/ant.txt");
                    oif2.addPath("/bash/ammy.txt");
                    oif2.addPath("/test/bin/dust.txt");
                    oif2.closeWriter();
                    GOptimizedIndexFileIterator goifi2 = new GOptimizedIndexFileIterator(oif2);
                         while(goifi2.hasNext())
                             System.out.println(goifi2.getNext());
                    
       example 3:
                    GOptimizedPathFile oif = new GOptimizedPathFile("/home/gulshan/test.txt");
                    GOptimizedPathFile oif3 = new GOptimizedPathFile("/home/gulshan/test2.txt");
                    oif.reopenForAppending();
                    //appending contents of oif3 thrice to depict its advantage over addPath with Iterator
                    oif.addAllPath(oif3);
                    oif.addAllPath(oif3);
                    oif.addAllPath(oif3);
                    oif.closeWriter();
                    GOptimizedIndexFileIterator goifi = new GOptimizedIndexFileIterator(oif);                    
                    while(goifi.hasNext()){
                        System.out.println(goifi.getNext());
                    }  
       
   This class helps create file and append paths so that they are stored efficiently
 reducing redundancy of common path between two adjacent paths, the name is
 GOptimizedPathFile because I used this format to compress Index files
 however, a better name would be GOptimizedPathFile or GCompressedPathFile
 but have kept this name as I used this initially
 * @author Gulshan <Gulshan at gulsanbc@gmail.com>
 */
public class GOptimizedPathFile {

    BufferedReader br = null;
    BufferedWriter bw = null;
    File file = null;
    File fileForAppend = null;

    String lastRoot = null;
    boolean zeroLinesAdded = true;
    boolean flagReadyForAppending = false;
    
    public GOptimizedPathFile(String filename) {
        file = new File(filename);
        fileForAppend = new File(filename + ".append");
    }

    public void openReader() {
        //System.out.println("openReader() "+file.getName());
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readLine() {
        try {
            return br.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeReader() {
        //System.out.println("closeReader() "+file.getName());
        try {
            if(br!=null) br.close();
            br = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This automatically adds the first line indicator for /*Is an optimized Index File * /
     */
    public void openWriter() {
        //System.out.println("openWriter() "+file.getName());
        try {
            bw = new BufferedWriter(new FileWriter(file));            
            bw.write("/*Is an optimized Index File*/");
            bw.newLine();
            zeroLinesAdded = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void addPathAndFlushImmideately(String pathToBeAppended){
        try{
        addPath(pathToBeAppended);
        bw.flush();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @param pathToBeAppended is the path that goes inside the index file, don't confuse this for the path of optimized file
     */
    public void addPath(String pathToBeAppended) {
        try {
            if(zeroLinesAdded){                
                lastRoot = pathToBeAppended.substring(0, pathToBeAppended.lastIndexOf("/"));
                String filenameOnly = pathToBeAppended.substring(pathToBeAppended.lastIndexOf("/"));
                if(lastRoot==null) System.out.println(pathToBeAppended);
                if(bw==null) System.out.println(pathToBeAppended+" bw is null");
                bw.write(lastRoot);
                bw.write("\n");
                bw.write(filenameOnly);
                bw.write("\n");
                zeroLinesAdded = false;
                return;
            }
            //below code adjusts(-/+ sign) path relative to oldRoot and also determines absolute path of newroot
            String[] newRootAndRelativePath = adjustPath(lastRoot,pathToBeAppended);
            this.bw.write(newRootAndRelativePath[1]);
            //System.out.println("newRoot="+newRootAndRelativePath[0]);
            lastRoot = newRootAndRelativePath[0];                                               
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Logic is simple, 
     * ->first open this file for appending
     * ->next read rootPath at second line of supplied goif and first path stored 
     * at third line and recreate the absolutePath as pathToBeAppended
     * ->calculate newRoot and relative path from firstPath
     * ->set value of lastRoot of this object as of newRoot
     * ->write the relative path to this file
     * ->copy remaining line as it is using copyReadAhead() which is a special
     *   function that copies only upto second last line while last line which
     *   is lastRoot of goif file as lastRoot of this file object
     * ->keeps bw open for subsequent calls
     * 
     * this function neither opens nor closes the BufferedReader/Writer of this
     * file object where supplied goif file will be appended
     * 
     * thus, you must call reopenForAppending() before calling this
     * also, once you are done calling this, call closeWriter()
     * writer is not opened/closed here to make successive calls faster as 
     * reopenForAppending() will be called only once, if it were called here in
     * this function, for each call, it would duplicate the existing file before 
     * appending supplied goif file and with each append, it would increase duplication
     * time!!
     * 
     * @param goif
     * @return 
     */
    public int addAllPath(GOptimizedPathFile goif){
        //GConsoleTextArea.println("addAllToOptimizedIndexFile called on ");            
        try{ 
            if (goif.file.length() == 0) {
                System.out.println("returning from goif.file.length() == 0");
                return -1;
            }            
            goif.openReader();

            if (!goif.readLine().equals("/*Is an optimized Index File*/")) {//ignore first line /*Is an optimized index file*/
                System.out.println("returning from /*Is an optimized Index File*/");
                return -1;
            }        
                    
            String secondLine = goif.readLine();
            if(secondLine==null) {
                System.out.println("returning from second line is null");
                return -1;
            }
            String thirdLine=goif.readLine();
            if(thirdLine==null) {
                System.out.println("returning from third line is null");
                return -1;
            }
            
            if(lastRoot==null){//this happens when this object is empty,i.e. its file is empty
                //must not add this line here as this function is called after 
                //calling openWriter() which already writes this line!!
//                this.bw.write("/*Is an optimized Index File*/"); 
//                this.bw.newLine();
                this.bw.write(secondLine);
                this.bw.newLine();
                this.bw.write(thirdLine);
                this.bw.newLine();
            }else{
                String pathToBeAppended = secondLine + thirdLine;
                //below code adjusts(-/+ sign) path relative to oldRoot and also determines absolute path of newroot
                String[] newRootAndRelativePath = adjustPath(lastRoot,pathToBeAppended);
                this.bw.write(newRootAndRelativePath[1]);
                lastRoot = newRootAndRelativePath[0];
            }
            
            copyReadAhead(goif,bw);            
            lastRoot = goif.lastRoot;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        
        return 0;
    }
    /**
     * below code adjusts(-/+ sign) path relative to oldRoot and also determines absolute path of newroot
     * returns newRoot at 0th index
     *         and relativePath at 1th index(relativePath consists of two lines separated by \n
     *              first part is -+ adjusted relative path, second line is filename only with / sign as /filenameonly
     * @param oldRoot
     * @param pathToBeAppended
     * @return 
     */
    public String[] adjustPath(String oldRoot,String pathToBeAppended){
        String[] newRootAndRelativePath = new String[2];
        try{
            String relativePath="";
            String newRoot;
            String additionalFolders, matchedFolders, filenameOnly;
            int numOfMatchedFolders, numOfFoldersInNewRoot, numOfFoldersInOldRoot;
            String[] matchedAndUnmatchedPartsWithRoot = null;
            String curline = pathToBeAppended;
            newRoot = curline.substring(0, curline.lastIndexOf("/"));
            filenameOnly = curline.substring(curline.lastIndexOf("/"));
            if (newRoot.equals(oldRoot)) {
                relativePath+=filenameOnly+"\n";
            } else {
                matchedAndUnmatchedPartsWithRoot = getMatchedAndUnmatchedParts(oldRoot, newRoot);
                matchedFolders = matchedAndUnmatchedPartsWithRoot[0];
                additionalFolders = matchedAndUnmatchedPartsWithRoot[1];
                numOfMatchedFolders = GString.countOf(matchedFolders, "/") - 1;
                numOfFoldersInNewRoot = GString.countOf(newRoot, "/") - 1;
                numOfFoldersInOldRoot = GString.countOf(oldRoot, "/") - 1;
                if (numOfFoldersInNewRoot > numOfFoldersInOldRoot) {
                    if (numOfMatchedFolders == numOfFoldersInOldRoot) {
                        relativePath+="+" + additionalFolders+"\n";
                    } else if (numOfMatchedFolders < numOfFoldersInOldRoot) {
                        String line = "";
                        for (int q = 0; q < numOfFoldersInOldRoot - numOfMatchedFolders; q++) {
                            line += "-";
                        }
                        line += "+" + additionalFolders;
                        relativePath+=line+"\n";
                    }
                } else if (numOfFoldersInNewRoot == numOfFoldersInOldRoot && numOfMatchedFolders < numOfFoldersInOldRoot) {
                    String line = "";
                    for (int q = 0; q < numOfFoldersInOldRoot - numOfMatchedFolders; q++) {
                        line += "-";
                    }
                    line += "+" + additionalFolders;
                    relativePath+=line+"\n";
                } else if (numOfFoldersInNewRoot < numOfFoldersInOldRoot) {
                    String line = "";
                    for (int q = 0; q < numOfFoldersInOldRoot - numOfMatchedFolders; q++) {
                        line += "-";
                    }
                    if (numOfFoldersInNewRoot == numOfMatchedFolders) ;//do nothing in this case
                    if(numOfFoldersInNewRoot==-1)//this occurs when newRoot is blank which occurs when file is in root folder /
                    {
                        line+="+";//since newRoot is blank in this case , there's no point in adding it using "+" + newRoot
                        relativePath+=line+"\n";
                    }else {
                        line += "+" + additionalFolders;
                        relativePath+=line+"\n";
                    }                    
                }
                relativePath+=filenameOnly+"\n";
            }
            
            newRootAndRelativePath[0] = newRoot;
            newRootAndRelativePath[1] = relativePath;
        }catch(Exception e){e.printStackTrace();}
        return newRootAndRelativePath;
    }
    /**
     * this function copies every line except last non-empty line, which 
 is lastRoot of the GOptimizedPathFile into fileForAppend, deletes
 original file and renames the fileForAppend to original file name
 and loads last non-empty line starting with / into lastRoot variable

 best thing is it does all this in single line by line scan of file!!
 
 This doesn't open goif reader() by itself, so this function
 must only be called after calling the openReader() on goif
 
 it also doesn't close the bw
 
 the reason for this is, sometimes we need to process first few lines 
 before copying the content exactly, like when one file is being appended 
 to another file, first line /*Is an optimized index file * /
 must be omitted and second line must be adjusted for root relative to file 
 it is being copied to
     * 
     * @param goif
     * @param bw 
     */
    public void copyReadAhead(GOptimizedPathFile goif,BufferedWriter bw){ 
        try{               
            String readline,readAheadLine1="",readAheadLine2=null;
            while((readline = goif.readLine())!=null){                
//                if(GString.areAllCharsWhitespace(readline)||readline.isEmpty()){                    
//                    lastRoot =  readAheadLine2;
//                }
                //the test in while makes sure either null or line starting with /,- or + is stored in readAheadLine1 and readAheadLine2
                do{                    
                    readAheadLine1 = goif.readLine();
                }while(readAheadLine1!=null && readAheadLine1.charAt(0)!='/' && readAheadLine1.charAt(0)!='-' && readAheadLine1.charAt(0)!='+' );               
                if(readAheadLine2!=null){
                    bw.write(readAheadLine2);
                    bw.newLine();
                }
                if(readAheadLine1==null) {                                        
                    if(readline.charAt(0)=='/') goif.lastRoot = readline;
                    break;
                }
                do{                    
                    readAheadLine2 = goif.readLine();
                }while(readAheadLine2!=null && readAheadLine2.charAt(0)!='/' && readAheadLine2.charAt(0)!='-' && readAheadLine2.charAt(0)!='+' );
                if(readAheadLine2==null) {                    
                    bw.write(readline);
                    bw.newLine();  
                    
                    if(readline.charAt(0)=='/') goif.lastRoot =  readAheadLine1;                    
                }else{
                    bw.write(readline);
                    bw.newLine();  
                    
                    bw.write(readAheadLine1);
                    bw.newLine();                    
                }                            
            }
            //what I learnt creating this function is /n is not on the last empty line
            //its at the end of the second last line(i.e last non empty line), while null 
            //is at last empty line that appears as newline in text editors like sublime
            if(readline == null && readAheadLine2!=null){                    
                goif.lastRoot =  readAheadLine2;
            }
            bw.flush();
            //bw.close();
        }catch(Exception e){e.printStackTrace();}        
    }
    
    /**
     * file1 is reference, parts of file2 are returned!!
     */
    public static String[] getMatchedAndUnmatchedParts(String file1, String file2) {
        int count = -1;//starting from -1 to avoid counting empty part, cause splitting by / gives one extra empty part before initial / in the path!!

        String[] parents1 = file1.split("/");
        String[] parents2 = file2.split("/");
        int minCount = parents1.length < parents2.length ? parents1.length : parents2.length;

        for (int i = 0; i < minCount; i++) {
            if (parents1[i].equals(parents2[i])) {
                count++;
            } else {
                break;
            }
        }
        //GConsoleTextArea.println("count=" + count);

        String[] matchedAndUnmatchedParts = new String[2];
        matchedAndUnmatchedParts[0] = "";
        matchedAndUnmatchedParts[1] = "";
        for (int i = 0; i < count; i++) {
            matchedAndUnmatchedParts[0] += "/" + parents2[i + 1];
            //GConsoleTextArea.println(i + " " + matchedAndUnmatchedParts[0]);
        }

        for (int i = count; i < parents2.length - 1; i++) {
            matchedAndUnmatchedParts[1] += "/" + parents2[i + 1];
            //GConsoleTextArea.println(i + " " + matchedAndUnmatchedParts[1]);
        }

        return matchedAndUnmatchedParts;
    }

    /**
     * This writes the lastRoot to the file before closing, also adds newLine() 
     * then flushes bw before closing
     * 
     * make sure to call this for every openWriter()/reopenForAppending()
     * 
     * once closed, paths can't be appended directly, reopenForAppending() must
     * be called before adding more paths using addPath()/addAllPath(), however
     * since calling reopenForAppending() duplicates the file, its better to keep 
     * it open for as long as you need to add path and close once when you are sure
     * you don't need to append anymore path
     */
    public void closeWriter() {
        try {
            System.out.println("before , lastRoot="+lastRoot+" closeWriter() "+file.getName());        
            if(bw!=null && lastRoot!=null){
            bw.write(lastRoot);
            bw.newLine();

            bw.flush();
            bw.close();
            }
            System.out.println("after , lastRoot="+lastRoot+" closeWriter() "+file.getName());        
            flagReadyForAppending = false;
            bw = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    boolean isReadyForAppending(){
        return flagReadyForAppending;
    }
    
    public boolean reopenForAppending() {
        //System.out.println("reopenForAppending() "+file.getName());
        try {            
            if (bw != null) {
                closeWriter();
            }
            if(file.length()==0){
                bw = new BufferedWriter(new FileWriter(file));
                return true;
            }
            if (br != null) {
                br.close();
            }            
            this.openReader();
            String firstLine = this.readLine();
            if (!firstLine.equals("/*Is an optimized Index File*/")) {//ignore first line /*Is an optimized index file*/
                this.closeReader();
                System.out.println("returning because first line is not /*Is an optimized Index File*/");
                return false;
            }                    
            this.bw = new BufferedWriter(new FileWriter(fileForAppend));
            this.bw.write(firstLine);
            this.bw.newLine();
            
            copyReadAhead(this,this.bw);
            
            this.bw.close();
            
            //System.out.println("lastNonEmptyLineIndex="+lastNonEmptyLineIndex+"count="+count+", file.length()=" + file.length());
//            System.out.println("fileForAppend.length()=" + fileForAppend.length());
//            System.out.println("lastRoot=" + lastRoot);
//            System.out.println("lastRoot.length+fileForAppend.length()=" + (lastRoot.length() + fileForAppend.length()));

            //making this test, instead of equal length as I am removing empty lines 
            //at end of file which I expect to be less than 10 percent of total file 
            //size in worst case, in best case its only 1 byte for trailling /n at end of file
            if (fileForAppend.exists() && fileForAppend.length() > 0) {
                file.delete();
                fileForAppend.renameTo(file);
                bw = new BufferedWriter(new FileWriter(file, true));
                flagReadyForAppending = true;
                //System.out.println("opened for appending successfully length="+file.length());
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
