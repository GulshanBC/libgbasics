/**
 * Written By: Gulshan Bhagat Chaurasia
 * Date Written: 9 November, 2020
 * Date Modified: 10 November, 2020
 * Last Modified: 21 March, 2021
 * 
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.ArrayList;
import ml.gulshanbc.gbasics.GFileSystem;

/**
 *
 * @author gulshan
 * 
 * usage example:
 *  GSectionFile gsf = new GSectionFile("test.gsf");
                gsf.loadSectionFile();
//                ArrayList<String> lines = gsf.getSectionLinesFor("gulshan");
//                for(String line : lines)
//                    System.out.println(line);
//                lines = gsf.getSectionLinesFor("comment");
//                for(String line : lines)
//                    System.out.println(line);
//                lines = gsf.getSectionLinesFor("likes");
//                for(String line : lines)
//                    System.out.println(line);
                gsf.addSectionLineTo("gulshan", "I am gulshan");
                gsf.addSectionLineTo("gulshan", "I am 28 years old");
                gsf.addSectionLineTo("comment", "this is comment section");
                gsf.addSectionLineTo("likes", "there are 48 likes");
                gsf.addSectionLineTo("likes", "no body likes me");
                gsf.addSectionLineTo("comment", "there are 23 comments");
                gsf.addSectionLineTo("gulshan", "I am kind");
                gsf.saveSectionFile();
                
                String el = gsf.escapeSectionLine("My name is @gulshan.\nI live in @jnk.\n\nBut I am @very happy.\nIn order to @@@remain happy, I just need source of food.123,$@%^\n&8~@~~@~@@@9(\n\n\n)~");
                System.out.println("el="+el);
                el = gsf.unescapeSectionLine(el);
                System.out.println("unel="+el);
                * 
                HashSet<String> hs = new HashSet();
                hs.add("gulshan");
                hs.add("guls");
                hs.add("gulshan");
                hs.add("guls");
                hs.add("gulshan");
                hs.add("guls");
                hs.add("gulshan");
                hs.add("guls");
                for(String str: hs)
                    System.out.println(str);
                
                ArrayList<String> lines = new ArrayList<String>();
//                lines.add("/home/gulshan/ttext.txt");
//                lines.add("/home/gulshan/run.txt");
//                lines = IndexFiles.optimizeIndexArrayList101(lines);
//                for(String line: lines)
//                    System.out.println(line);
                IndexFiles.appendToOptimizedIndexArrayList101(lines, "/*Is an optimized Index File*-/");
                IndexFiles.appendToOptimizedIndexArrayList101(lines, "/home/gulshan/ttext.txt");
                IndexFiles.appendToOptimizedIndexArrayList101(lines, "/home/gulshan/ttext.txt");
                IndexFiles.appendToOptimizedIndexArrayList101(lines, "/home/gulshan/ttext.txt");                
                IndexFiles.appendToOptimizedIndexArrayList101(lines, "/home/roma/oma/ttext.txt");
                IndexFiles.appendToOptimizedIndexArrayList101(lines, "/media/gulshan/ttext.txt");
                IndexFiles.appendToOptimizedIndexArrayList101(lines, "/home/ttext.txt");
                IndexFiles.appendToOptimizedIndexArrayList101(lines, "/*Is an optimized Index File*-/");
                IndexFiles.appendToOptimizedIndexArrayList101(lines, "");
                IndexFiles.appendToOptimizedIndexArrayList101(lines, "/home/gulshan/ttext.txt");
                IndexFiles.appendToOptimizedIndexArrayList101(lines, "/home/gulshan/ttext.txt");
                
                for(String line: lines)
                    System.out.println(line);
                
                String[] linesA = IndexFiles.parseOptimizedIndexArrayList(lines);
                for(String line: linesA)
                    System.out.println(line);
 */
public class GSectionFile {
    private HashMap<String,String> sectionNamesAndCorrespondingCodes = null;
    private HashMap<String,ArrayList<String>> sectionCodesAndCorrespondingLines = null;
    //private HashMap<String,HashSet<String>> sectionCodesAndCorrespondingUniqueLines = null;
    String lastSectionCode=null;
    String filePath;
    boolean flagUniqueLinesOnly = false;
    long fileLength = 0;
    
    /**
     * This controls the number of sections possible in a single GSectionFile
     * if its aaa, its length is 3, i.e. 26*26*26 = 17576 unique sections of lines!!
     * am sure you won't need that many section in a single file!!
     * in case you happen to need more than that , just add one more a and so on
     * as per your need!!
     */
    static String BASE_SECTION_CODE = "aaa";
    
    public GSectionFile(String filePath){
        System.out.println("GSectionFile created for "+filePath);
        this.filePath = filePath;
        sectionNamesAndCorrespondingCodes = new HashMap();
        sectionCodesAndCorrespondingLines = new HashMap();   
        lastSectionCode = BASE_SECTION_CODE;
    }
    
    public GSectionFile(String filePath,boolean _flagUniqueLinesOnly){
        flagUniqueLinesOnly = _flagUniqueLinesOnly;
        System.out.println("GSectionFile created for "+filePath);
        if(flagUniqueLinesOnly) System.out.println("add Unique lines only enabled!!");
        this.filePath = filePath;
        sectionNamesAndCorrespondingCodes = new HashMap();                
        sectionCodesAndCorrespondingLines = new HashMap(); 
        lastSectionCode = BASE_SECTION_CODE;
    }
    
    public long getFileLength()
    {
        return fileLength;
    }
    
    /**
     * As supplied by the caller!!
     */
    public String getFilePath()
    {
        return filePath;
    }
    public void loadSectionFile(){
        System.out.println("loadSectionFile called for "+filePath);
        try{
            File file = new File(filePath);            
            if(file.exists()){
            fileLength = file.length();
            BufferedReader br = new BufferedReader( new FileReader(file));
            String readLine = br.readLine();
            if(readLine == null) return;
            String sectionCode,sectionName,sectionLine;
            lastSectionCode = "";
            if(readLine.equals("/*Is a SectionFile*/")){
                //reading sectionCode and sectionName pair
                while(!((readLine=br.readLine()).equals("")) ){
                    sectionCode = readLine.substring(0,BASE_SECTION_CODE.length());
                    sectionName = readLine.substring(BASE_SECTION_CODE.length()+1);
                    //System.out.println("-"+sectionName+"-");
                    
                    if(lastSectionCode.compareTo(sectionCode)<0) lastSectionCode = sectionCode;
                    //sectionCodesSortedSet.add(sectionCode);
                    //System.out.println("Added to hashmap :"+sectionName+" = "+sectionCode);
                    sectionNamesAndCorrespondingCodes.put(sectionName, sectionCode);
                }
                System.out.println("sectionNamesAndCorrespondingCodes.size="+sectionNamesAndCorrespondingCodes.size());
                
                //I was making mistake while reading from file
                //while saving have saved empty line between header and sectionLines
                //as separator
                //but was not removing it before determining code and line pair
                //which caused exception and thus incomplete loading!!
                //
                //have enclosed them in try-catch block
                //empty lines throws exception, thus get removed!!
                //
                //reading sectionCode and sectionLine pair
                while((readLine=br.readLine())!= null ){   
                    if(readLine.isEmpty()) continue;
                    try{
                    sectionCode = readLine.substring(0,BASE_SECTION_CODE.length());
                    try{                        
                        sectionLine = readLine.substring(BASE_SECTION_CODE.length()+1);                        
                    }catch(Exception e){sectionLine="";} 
                    addSectionLine(sectionCode,unescapeSectionLine(sectionLine));
                    }catch(Exception e){}
                }                
            }else System.out.println("Is not a section file");
            
            br.close();
            }
        }catch(Exception e){e.printStackTrace();}
    }
    
    public void loadSectionFileHeaders(){
        System.out.println("loadSectionFile called for "+filePath);
        try{
            File file = new File(filePath);            
            if(file.exists()){
            fileLength = file.length();
            BufferedReader br = new BufferedReader( new FileReader(file));
            String readLine = br.readLine();
            if(readLine == null) return;
            String sectionCode,sectionName,sectionLine;
            lastSectionCode = "";
            if(readLine.equals("/*Is a SectionFile*/")){
                //reading sectionCode and sectionName pair
                while(!((readLine=br.readLine()).equals("")) ){
                    sectionCode = readLine.substring(0,BASE_SECTION_CODE.length());
                    sectionName = readLine.substring(BASE_SECTION_CODE.length()+1);
                    //System.out.println("-"+sectionName+"-");
                    
                    if(lastSectionCode.compareTo(sectionCode)<0) lastSectionCode = sectionCode;
                    //sectionCodesSortedSet.add(sectionCode);
                    //System.out.println("Added to hashmap :"+sectionName+" = "+sectionCode);
                    sectionNamesAndCorrespondingCodes.put(sectionName, sectionCode);
                }
                System.out.println("sectionNamesAndCorrespondingCodes.size="+sectionNamesAndCorrespondingCodes.size());                                                
            }else System.out.println("Is not a section file");
            
            br.close();
            }
        }catch(Exception e){e.printStackTrace();}
    }
    
    /**
     * this doesn't replace the section lines ArrayList, it only
     * adds the content of supplied ArrayList to the assigned ArrayList!!
     */
    public void addSectionLinesTo(String sectionName,ArrayList<String> sectionLines){
        for(String line : sectionLines)
            addSectionLineTo(sectionName,line);
    }
    
    public void addSectionLineTo(String sectionName,String sectionLine){        
        String sectionCode = sectionNamesAndCorrespondingCodes.get(sectionName);
        if(sectionCode == null || sectionCode.length()<BASE_SECTION_CODE.length()){
            //System.out.println("section code is null");
            if(lastSectionCode==null || lastSectionCode.length()<BASE_SECTION_CODE.length()){
                //System.out.println("last section code is null");
                sectionCode=BASE_SECTION_CODE;
                sectionNamesAndCorrespondingCodes.put(sectionName, sectionCode);
                lastSectionCode = sectionCode;
            }
            else{ 
                //System.out.println("last section code is not null");
                sectionCode=incrementSectionCode(lastSectionCode);                
                sectionNamesAndCorrespondingCodes.put(sectionName, sectionCode);
                lastSectionCode = sectionCode;
            }
        }
        //else System.out.println("section code is not null");
        //System.out.println("adding \""+sectionLine+"\" to section "+sectionCode);
                
        addSectionLine(sectionCode,sectionLine);
    }
    
    public static String incrementSectionCode(String sectionCode)
    {       
        char[] digits = sectionCode.toCharArray();
        for( int i = digits.length - 1; i >= 0 ; i-- ){
            if(digits[i]<'z'){
            	digits[i]=(char)(digits[i]+1);
            	break;
            }else{
            	digits[i]='a';            	
            }            
        }
        return new String(digits);
    }
    
    public void addSectionLine(String sectionCode, String sectionLine){        
        if(flagUniqueLinesOnly){
            addSectionUniqueLine(sectionCode,sectionLine);
        }else{
            ArrayList<String> sectionLines = sectionCodesAndCorrespondingLines.get(sectionCode);
            if(sectionLines==null) {
                sectionLines = new ArrayList<String>();
                sectionCodesAndCorrespondingLines.put(sectionCode, sectionLines);
            }
            sectionLines.add(sectionLine);
            //System.out.println(sectionCode+" has sectionLines="+sectionLines.size());
        }
    }
    
    public void addSectionUniqueLine(String sectionCode, String sectionLine){        
        ArrayList<String> sectionLines = sectionCodesAndCorrespondingLines.get(sectionCode);
        HashSet<String> uniqueSectionLines = new HashSet<String>();
        if(sectionLines==null) {
            sectionLines = new ArrayList<String>();
            sectionCodesAndCorrespondingLines.put(sectionCode, sectionLines);
        }
        uniqueSectionLines.addAll(sectionLines);
        if(uniqueSectionLines.add(sectionLine))
             sectionLines.add(sectionLine);
        else System.out.println("line being added already exists in the section");
        //System.out.println(sectionCode+" has sectionLines="+sectionLines.size());
    }
    
    public String escapeSectionLine(String sectionLine){
        if(sectionLine.contains("@")){
            sectionLine=sectionLine.replaceAll("@", "~@");
        }
        if(sectionLine.contains("\n")){
            sectionLine=sectionLine.replaceAll("\n", "@");
        }
        return sectionLine;
    }
    
    public String unescapeSectionLine(String sectionLine){
        if(sectionLine.contains("~@")){//it means the line already had @ before escaping , thus requires special handling
            //sectionLine=sectionLine.replaceAll("@", "~@");
            StringBuilder sb = new StringBuilder();
            int processingLength = 0;
            if(sectionLine.length()>2){
                int i=0;
                processingLength = sectionLine.length();                
                for( i = 0 ; i < processingLength-1 ; i++ ){  
                    if(sectionLine.charAt(i)=='~'&& sectionLine.charAt(i+1)=='@'){
                        sb.append("@");
                        i++;
                    }else if(sectionLine.charAt(i)=='@'){
                        sb.append("\n");
                    }else sb.append(sectionLine.charAt(i));
                }
                if(i==processingLength-1) sb.append(sectionLine.charAt(processingLength-1));
                
                sectionLine = sb.toString();
            }else if(sectionLine.length()==2){
                if(sectionLine.contains("~@")) sectionLine = sectionLine.replace("~@", "@");
                else if(sectionLine.contains("@")) sectionLine = sectionLine.replaceAll("@", "\n");
            }            
        }
        else if(sectionLine.contains("@")){
            sectionLine=sectionLine.replaceAll("@", "\n");
        }
        return sectionLine;
    }
    
    public void removeSectionNamed(String sectionName){
        String sectionCode = sectionNamesAndCorrespondingCodes.get(sectionName);
        if(sectionCode!=null){
            sectionNamesAndCorrespondingCodes.remove(sectionName, sectionCode);        
            sectionCodesAndCorrespondingLines.remove(sectionCode);
        }
    }
    
    public void removeSection(String sectionCode){
        sectionCodesAndCorrespondingLines.remove(sectionCode);
    }
    
    public void removeSectionLineFrom(String sectionName,String sectionLine){        
        String sectionCode = sectionNamesAndCorrespondingCodes.get(sectionName);
        if(sectionCode!=null){
            System.out.println("Removing "+sectionLine+" from "+sectionName);            
            removeSectionLine(sectionCode,sectionLine);
        }
    }
    
    public void removeSectionLine(String sectionCode, String sectionLine){
        ArrayList<String> sectionLines = sectionCodesAndCorrespondingLines.get(sectionCode);
        if(sectionLines!=null) {
            sectionLines.remove(sectionLine);
        }
    }
    
    public HashMap<String,String> getSectionNamesAndCodesMap()
    {
        return sectionNamesAndCorrespondingCodes;
    }
    
    public Set<String> getSectionNames()
    {
        return sectionNamesAndCorrespondingCodes.keySet();
    }
    
    /**In case of unique, lines it also returns ArrayList by filling it with 
     * unique contents from HashSet!!
     */
    public ArrayList<String> getSectionLinesFor(String sectionName){
        //System.out.println("sectionNamesAndCorrespondingCodes="+sectionNamesAndCorrespondingCodes.size());
        String sectionCode = sectionNamesAndCorrespondingCodes.get(sectionName);        
        if(sectionCode == null || sectionCode.length()<BASE_SECTION_CODE.length()){
            return null;
        }
        //else System.out.println("section code is not null");
        
        return getSectionLines(sectionCode);        
    }
    
    /**In case of unique, lines it also returns ArrayList by filling it with 
     * unique contents from HashSet!!
     */
    public ArrayList<String> getSectionLines(String sectionCode){
        //System.out.println("getSectionLines on"+sectionCode);  
        
        ArrayList<String> sectionLines = sectionCodesAndCorrespondingLines.get(sectionCode);
        if(sectionLines == null){
            sectionCodesAndCorrespondingLines.put(sectionCode, new ArrayList<String>());
        }
        return sectionCodesAndCorrespondingLines.get(sectionCode);
    }
        
    public void saveSectionFile(){
        System.out.println("saveSectionFile called for "+filePath);
        try{
            File fileParents = new File(GFileSystem.getPath(filePath));
            if(!fileParents.exists()) fileParents.mkdirs();
            
            BufferedWriter bw = new BufferedWriter( new FileWriter(filePath));            
            bw.write("/*Is a SectionFile*/");
            bw.newLine();
            
            Set<String> sectionNames = sectionNamesAndCorrespondingCodes.keySet();
            System.out.println("sectionNames.size="+sectionNames.size());
            Iterator it = sectionNames.iterator();
            String sectionName,sectionCode;
            while(it.hasNext()){
                sectionName = (String)it.next();
                sectionCode = sectionNamesAndCorrespondingCodes.get(sectionName);
                if(sectionCode!=null && sectionCode.length()==BASE_SECTION_CODE.length()){
                    bw.write(sectionCode+" "+sectionName);
                    bw.newLine();
                }else{
                    System.out.println(sectionName+" doesn't have section code!!");
                }
            }
            //adding extra emptly line after header as separator between header and sections!!
            bw.newLine();
            
            Set<String> sectionCodes;
            
            sectionCodes = sectionCodesAndCorrespondingLines.keySet();
            it = sectionCodes.iterator();
            ArrayList<String> sectionLines;
            while(it.hasNext()){
                sectionCode = (String)it.next();
                sectionLines = sectionCodesAndCorrespondingLines.get(sectionCode);

                for( String line : sectionLines ){
                    bw.write(sectionCode+" "+escapeSectionLine(line));
                    bw.newLine();
                }
            }                                               
            
            bw.flush();
            bw.close();
        }catch(Exception e){e.printStackTrace();}
    }
    
    public void saveSectionFileAs(String filePath){
        System.out.println("saveSectionFile called for "+filePath);
        try{
            File fileParents = new File(GFileSystem.getPath(filePath));
            if(!fileParents.exists()) fileParents.mkdirs();
            
            BufferedWriter bw = new BufferedWriter( new FileWriter(filePath));            
            bw.write("/*Is a SectionFile*/");
            bw.newLine();
            
            Set<String> sectionNames = sectionNamesAndCorrespondingCodes.keySet();
            System.out.println("sectionNames.size="+sectionNames.size());
            Iterator it = sectionNames.iterator();
            String sectionName,sectionCode;
            while(it.hasNext()){
                sectionName = (String)it.next();
                sectionCode = sectionNamesAndCorrespondingCodes.get(sectionName);
                
                bw.write(sectionCode+" "+sectionName);
                bw.newLine();
            }
            //adding extra emptly line after header as separator between header and sections!!
            bw.newLine();
                        
            Set<String> sectionCodes = sectionCodesAndCorrespondingLines.keySet();            
            it = sectionCodes.iterator();
            ArrayList<String> sectionLines;
            while(it.hasNext()){
                sectionCode = (String)it.next();
                sectionLines = sectionCodesAndCorrespondingLines.get(sectionCode);

                for( String line : sectionLines ){
                    bw.write(sectionCode+" "+escapeSectionLine(line));
                    bw.newLine();
                }
            }
            
            bw.flush();
            bw.close();
        }catch(Exception e){e.printStackTrace();}
    }
}