package ml.gulshanbc.gbasics;

import java.awt.EventQueue;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Base64;
import java.util.Iterator;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * This class provides functions that are not provided in java.io.File class
 * All the functions are static ,so no need to instantiate it for using the functions
 * @author Gulshan Bhagat Chaurasia
 * @Date Written on 21 December 2018
 * @Date Modified on 24 August 2019
 */
public class GFileSystem 
{
	public static String[] audioExtensions = {"mp3" , "aac" , "m4a" , "wav"};
	public static String[] videoExtensions = {"mp4" , "m4v" , "ogg" , "mkv" , "flv" , "avi" , "dat" , "webm" , "mov" , "3gp"};
	public static String[] documentExtensions = {"pdf" , "txt" , "java" , "cpp" , "c", "h" , "sh", "xml" , "html" , "php" , "py" , "js" , "css", "doc" , "docx" };
	public static String[] imageExtensions = {"jpg" , "jpeg" , "png" , "gif" , "bmp" };
	public static String[] zipExtensions = {"zip" , "tar" , "rar" , "gz" , "gz2" , "bz" , "bz2" };
	public static String[] pdfExtensions = {"pdf"};
	public static String[] codeExtensions = {"cpp" , "c" , "h" , "java", "py" , "sh" };
	public static String[] appExtensions = {"exe" , "apk" , "deb" , "snap" , "iso" , "jar" , "class" , "img" };

	public static int ssArray[] = {600,60,6,0};//starting time for -ss parameter of ffmpeg, 10min,1min,6sec,and 0sec 
        public static boolean flagDebug = false;
	/**
    * This function returns an array of String containing absolute path of all the drive partitions and
    * storage media connected to the computer, currently its only for Linux
    * @return String[]
    */
   public static String[] listDrivePartitions()
   {	   
	   String[] result = null;
	   String[] mediaNames = null;
	   try {
		   if( System.getProperty("os.name").equalsIgnoreCase("Linux") )
		   {
			   File mediaRoot = new File("/media/" + System.getProperty("user.name"));
			   if( mediaRoot.exists() )
			   {
				   mediaNames =  mediaRoot.list();
				   result = new String[mediaNames.length];
				   for( int i = 0 ; i < mediaNames.length ; i++ )
				   {
					   result[i] = "/media/" + System.getProperty("user.name") +"/"+ mediaNames[i];
				   }
			   }			   
		   }
	   }
	   catch(Exception e)
	   {
		   if(flagDebug) System.out.println(e.getMessage());
	   }
	   return result;
   }
   
   /**
    * This function returns an array of java.io.File, containing java.io.File for each of the drive partitions and
    * storage media connected to the computer, currently its only for Linux
    * @return File[]
    */
   public static File[] getDrivePartitions()
   {
	   File[] result = null;
	   String[] drivePartitions = listDrivePartitions();
	   result = new File[drivePartitions.length];
	   
	   for( int i = 0 ; i < drivePartitions.length ; i++ )
	   {
		   result[i] = new File(drivePartitions[i]);
	   }
	   
	   return result;
   }
   
   /**
    * This function returns an array of String containing absolute filenames for 
    * each of the files in supplied Folder(Directory) and its subfolders
    * @return String[]
    */
   public static String[] listFilesRecursively(File file)
   {
	   String[] result = null;
	   File[] subFilesAndDirectories = null;
	   ArrayList<File> directories = new ArrayList<File>();
	   ArrayList<String> files = new ArrayList<String>();
	   
	   if( file.exists() )
	   {
		   if( file.isDirectory() )
		   {
			   subFilesAndDirectories = file.listFiles();
			   for(File subfile : subFilesAndDirectories)
			   {
				   if(subfile.isDirectory()) directories.add(subfile);
				   else files.add(subfile.getAbsolutePath());
			   }
			   
			   for( int i = 0 ; i < directories.size() ; i++ )
			   {
				   File[] currentSubfilesAndDirectories = directories.get(i).listFiles();
				   if( currentSubfilesAndDirectories != null )
				   {
					   for(File subfile : currentSubfilesAndDirectories)
					   {
						   if(subfile.isDirectory()) directories.add(subfile);
						   else files.add(subfile.getAbsolutePath());
					   }
				   }
			   }
		   }
		   
		   result = new String[files.size()];
		   files.toArray(result);
	   }
	   	   
	   return result;
   }
   
   /**
    * This function returns an array of String containing absolute filenames for 
    * each of the files in supplied Folder(Directory) and its subfolders
    * @return String[]
    */
   public static String[] listFilesRecursively(File file,FilenameFilter filenameFilter)
   {
	   String[] result = null;
	   File[] subFilesAndDirectories = null;
	   ArrayList<File> directories = new ArrayList<File>();
	   ArrayList<String> files = new ArrayList<String>();
	   
	   if( file.exists() )
	   {
		   if( file.isDirectory() )
		   {
			   subFilesAndDirectories = file.listFiles();
			   for(File subfile : subFilesAndDirectories)
			   {
				   if(subfile.isDirectory()) directories.add(subfile);
				   else files.add(subfile.getAbsolutePath());
			   }
			   
			   for( int i = 0 ; i < directories.size() ; i++ )
			   {
				   File[] currentSubfilesAndDirectories = directories.get(i).listFiles();
				   if( currentSubfilesAndDirectories != null )
				   {
					   for(File subfile : currentSubfilesAndDirectories)
					   {
						   if(subfile.isDirectory()) directories.add(subfile);
						   else files.add(subfile.getAbsolutePath());
					   }
				   }
			   }
		   }
		   
		   result = new String[files.size()];
		   files.toArray(result);
	   }
	   	   
	   return result;
   }
   
   /**
    * This function returns an array of String containing absolute filenames for 
    * each of the files in supplied Folder(Directory) and its subfolders
    * @return String[]
    */
   public static String[] listFilesRecursively(File file,Thread parent)
   {	  
	   String[] result = null;
	   File[] subFilesAndDirectories = null;
	   ArrayList<File> directories = new ArrayList<File>();
	   ArrayList<String> files = new ArrayList<String>();
	   
	   if( file.exists() )
	   {
		   if( file.isDirectory() )
		   {
			   subFilesAndDirectories = file.listFiles();
			   for(File subfile : subFilesAndDirectories)
			   {
				   if(subfile.isDirectory()) directories.add(subfile);
				   else files.add(subfile.getAbsolutePath());
			   }
			   
			   for( int i = 0 ; i < directories.size() ; i++ )
			   {
				   File[] currentSubfilesAndDirectories = directories.get(i).listFiles();
				   if( currentSubfilesAndDirectories != null )
				   {
					   for(File subfile : currentSubfilesAndDirectories)
					   {
						   if(subfile.isDirectory()) directories.add(subfile);
						   else files.add(subfile.getAbsolutePath());
						   
						   try {							   						   
							   if( files.size() % 1000 == 0 ) parent.sleep(100);
						   }
						   catch(Exception e)
						   {
							   if(flagDebug) System.out.println(e.getMessage());
						   }
					   }
				   }
			   }
		   }
		   
		   result = new String[files.size()];
		   files.toArray(result);
	   }
	   	   
	   return result;
   }
   
   
   
   /**
    * This function returns an array of java.io.File containing, 
    * each of the files in supplied Folder(Directory) and its subfolders
    * @return File[]
    */
   public static File[] getFilesRecursively(File file)
   {
	   File[] result = null;
	   File[] subFilesAndDirectories = null;
	   ArrayList<File> directories = new ArrayList<File>();
	   ArrayList<File> files = new ArrayList<File>();
	   
           boolean flagSkip = false;
	   if( file.exists() )
	   {
		   if( file.isDirectory() )
		   {
                       subFilesAndDirectories = file.listFiles();
                        for(File subfile : subFilesAndDirectories)
                        {
                                if(subfile.isDirectory()) directories.add(subfile);
                                else files.add(subfile);
                        }

                        for( int i = 0 ; i < directories.size() ; i++ )
                        {                            
                            File[] currentSubfilesAndDirectories = directories.get(i).listFiles();
                            if( currentSubfilesAndDirectories != null )
                            {
                                    for(File subfile : currentSubfilesAndDirectories)
                                    {
                                            if(subfile.isDirectory()) directories.add(subfile);
                                            else files.add(subfile);
                                    }
                            }                            
                        }
		   }
		   
		   result = new File[files.size()];
		   files.toArray(result);
	   }
	   	   
	   return result;
   }
      
   /**
    * This function returns an array of java.io.File containing, 
    * each of the files in supplied Folder(Directory) and its subfolders
    * @return File[]
    */
   public static File[] getFilesRecursively(File file,String... skipContainingWords)
   {
	   File[] result = null;
	   File[] subFilesAndDirectories = null;
	   ArrayList<File> directories = new ArrayList<File>();
	   ArrayList<File> files = new ArrayList<File>();
	   
           boolean flagSkip = false;
	   if( file.exists() )
	   {
		   if( file.isDirectory() )
		   {
                       for(String word : skipContainingWords)
                       {
                           if( file.getAbsolutePath().contains(word)) flagSkip = true;
                       }
                        if(!flagSkip)   subFilesAndDirectories = file.listFiles();
                        for(File subfile : subFilesAndDirectories)
                        {
                                if(subfile.isDirectory()) directories.add(subfile);
                                else files.add(subfile);
                        }

                        for( int i = 0 ; i < directories.size() ; i++ )
                        {
                            for(String word : skipContainingWords)
                            {
                                if( directories.get(i).getAbsolutePath().contains(word)) flagSkip = true;
                            }
                            if(!flagSkip){
                                //showProgress(directories.get(i).getAbsolutePath(),"Listing files recursively of folder: ");
            
                                File[] currentSubfilesAndDirectories = directories.get(i).listFiles();
                                if( currentSubfilesAndDirectories != null )
                                {
                                        for(File subfile : currentSubfilesAndDirectories)
                                        {
                                                if(subfile.isDirectory()) directories.add(subfile);
                                                else files.add(subfile);
                                        }
                                }
                            }
                        }
		   }
		   
		   result = new File[files.size()];
		   files.toArray(result);
	   }
	   	   
	   return result;
   }
   
   
   /**
    * This function returns an array of String containing absolute path for 
    * each of the folders in supplied Folder(Directory) and its subfolders
    * @return String[]
    */
   public static String[] listSubfoldersRecursively(File file)
   {
	   String[] result = null;
	   File[] subFilesAndDirectories = null;
	   ArrayList<File> directories = new ArrayList<File>();
	   
	   if( file.exists() )
	   {
		   if( file.isDirectory() )
		   {
			   subFilesAndDirectories = file.listFiles();
			   for(File subfile : subFilesAndDirectories)
			   {
				   if(subfile.isDirectory()) directories.add(subfile);				   
			   }
			   
			   for( int i = 0 ; i < directories.size() ; i++ )
			   {
				   File[] currentSubfilesAndDirectories = directories.get(i).listFiles();
				   if( currentSubfilesAndDirectories != null )
				   {
					   for(File subfile : currentSubfilesAndDirectories)
					   {
						   if(subfile.isDirectory()) directories.add(subfile);					   
					   }
				   }
			   }
		   }
		   
		   result = new String[directories.size()];
		   for( int i = 0 ; i < directories.size() ; i++ )
		   {
			   result[i] = directories.get(i).getAbsolutePath();
		   }
	   }
	   	   
	   return result;
   }
   
   /**
    * This function returns an array of String containing absolute path for 
    * each of the folders in supplied Folder(Directory) and its subfolders
    * @return File[]
    */
   public static File[] getSubfoldersRecursively(File file)
   {
	   File[] result = null;
	   File[] subFilesAndDirectories = null;
	   ArrayList<File> directories = new ArrayList<File>();
	   
	   if( file.exists() )
	   {
		   if( file.isDirectory() )
		   {
			   subFilesAndDirectories = file.listFiles();
			   for(File subfile : subFilesAndDirectories)
			   {
				   if(subfile.isDirectory()) directories.add(subfile);				   
			   }
			   
			   for( int i = 0 ; i < directories.size() ; i++ )
			   {
				   File[] currentSubfilesAndDirectories = directories.get(i).listFiles();
				   if( currentSubfilesAndDirectories != null )
				   {
					   for(File subfile : currentSubfilesAndDirectories)
					   {
						   if(subfile.isDirectory()) directories.add(subfile);					   
					   }
				   }
			   }
		   }
		   
		   result = new File[directories.size()];
		   directories.toArray(result);		   
	   }
	   	   
	   return result;
   }
   
   public static void deleteFolderRecursively(File folder,String confirmFolderName)
    {
        if(folder.exists() && folder.isDirectory() && folder.getName().equals(confirmFolderName))
        {
            if(flagDebug) System.out.println("Deleteting all files in the folder recursively: "+folder.getAbsolutePath());                       
            File[] subfolders = GFileSystem.getSubfoldersRecursively(folder);
            for( int i = subfolders.length-1 ; i >= 0 ; i--)
            {              
                File[] files = subfolders[i].listFiles();
                for(File file : files)
                    if(file.isFile() && file.getAbsolutePath().contains(confirmFolderName)) file.delete();//if(flagDebug) System.out.println(file.getAbsolutePath());
                
                //if( subfolders[i].getAbsolutePath().contains(confirmFolderName)) if(flagDebug) System.out.println(subfolders[i]);
                if( subfolders[i].getAbsolutePath().contains(confirmFolderName)) subfolders[i].delete();
            }
            //if( folder.getName().equals(confirmFolderName) ) if(flagDebug) System.out.println(folder.getAbsolutePath());
            if( folder.getName().equals(confirmFolderName) ) 
            {
                File[] files = folder.listFiles();
                for(File file : files)
                {
                    if(file.isFile() && file.getAbsolutePath().contains(confirmFolderName)) 
                    {
                        file.delete();                        
                    }//if(flagDebug) System.out.println(file.getAbsolutePath());                
                }
                folder.delete();
            }
        }
    }
   
   public static void printSystemProperties()
   {	   
	   Properties properties = System.getProperties();
	   properties.list(System.out);
   }
      
   private static final DecimalFormat format = new DecimalFormat("#.##");
   private static final long GiB = 1024 * 1024 * 1024;
   private static final long MiB = 1024 * 1024;
   private static final long KiB = 1024;
   
   /**
    * This function returns size of the file in string format, in GiB , MiB , KiB or B as required
    * @param file
    * @return String
    */
   public static String getFileSize(File file) 
   {
		try
		{
			final double length = file.length();	    
	        //throw new IllegalArgumentException("Expected a file");	    	
		
			if (length > GiB) {
			    return format.format(length / GiB) + " GiB";
			}
		    if (length > MiB) {
		        return format.format(length / MiB) + " MiB";
		    }
		    if (length > KiB) {
		        return format.format(length / KiB) + " KiB";
		    }		   
		    return format.format(length) + " B"; 
		}
	    catch(Exception e)
	    {
			if(flagDebug) System.out.println(e.getMessage());
	    }
	    return "";
	}
   
   /**
	 * This function takes string containing filename with path and returns only the filename without path 
	 * @param filenamewithpath
	 * @return String
	 */
	public static String getFilename(String filenamewithpath)
	{
		String filename = "";
		if(filenamewithpath.contains("/"))
		{
			filename = filenamewithpath.substring(filenamewithpath.lastIndexOf("/")+1);
		}
		else filename = filenamewithpath;
		
		return filename;
	}
	
	 /**
	 * This function takes string containing filename with path and returns only the path without filename 
	 * @param filenamewithpath
	 * @return String
	 */
	public static String getPath(String filenamewithpath)
	{
		String path = "";
		if(filenamewithpath.contains("/") && filenamewithpath.lastIndexOf("/")!=filenamewithpath.length())
		{
			path = filenamewithpath.substring(0,filenamewithpath.lastIndexOf("/"));
		}
				
		return path;
	}
	
	/**
	 * This function takes string containing filename with path and returns only the extension without "." 
	 * also, if length of extension is 0 or more than 10 , it returns "unknown"
	 * @param filenamewithpath
	 * @return String
	 */
	public static String getType(String filenamewithpath)
	{
		String extension = "unknown";
		if(filenamewithpath.contains("."))
		{			
			extension = filenamewithpath.substring(filenamewithpath.lastIndexOf(".")+1);
		}
		if( extension.length() > 10 ) return "unknown";
		return extension;
	}
	
	/**
	 * This function takes string containing filename and drops the extension and 
	 * returns the filename only without .
	 * @param filenamewithpath
	 * @return String
	 */
	public static String dropExtension(String filenamewithpath)
	{		
		if(filenamewithpath.contains("."))
		{			
			filenamewithpath = filenamewithpath.substring(0,filenamewithpath.lastIndexOf("."));
		}		
		return filenamewithpath;
	}
	/**
	 * checks if file is audio 
	 * @param filename
	 * @return boolean
	 */
	public static boolean isAudio(String filename)
	{
		boolean result = false;
		
		for( String ext : audioExtensions)
		{			
			if( getType(filename).equalsIgnoreCase(ext) )
			{				
				result = true;
				break;
			}			
		}
		return result;
	}
	
	/**
	 * checks if file is video 
	 * @param filename
	 * @return boolean
	 */
	public static boolean isVideo(String filename)
	{
		boolean result = false;
		
		for( String ext : videoExtensions)
		{			
			if( getType(filename).equalsIgnoreCase(ext) )
			{				
				result = true;
				break;
			}			
		}
		return result;
	}
	
	/**
	 * checks if file is image
	 * @param filename
	 * @return boolean
	 */
	public static boolean isImage(String filename)
	{
		boolean result = false;
		
		for( String ext : imageExtensions)
		{			
			if( getType(filename).equalsIgnoreCase(ext) )
			{				
				result = true;
				break;
			}			
		}
		return result;
	}
	
	/**
	 * checks if file is document
	 * @param filename
	 * @return boolean
	 */
	public static boolean isDocument(String filename)
	{
		boolean result = false;
		
		for( String ext : documentExtensions)
		{			
			if( getType(filename).equalsIgnoreCase(ext) )
			{				
				result = true;
				break;
			}			
		}
		return result;
	}
	
	/**
	 * checks if file is document
	 * @param filename
	 * @return boolean
	 */
	public static boolean isApp(String filename)
	{
		boolean result = false;
		
		for( String ext : appExtensions)
		{			
			if( getType(filename).equalsIgnoreCase(ext) )
			{				
				result = true;
				break;
			}			
		}
		return result;
	}
	
	/**
	 * checks if file is document
	 * @param filename
	 * @return boolean
	 */
	public static boolean isZip(String filename)
	{
		boolean result = false;
		
		for( String ext : zipExtensions)
		{			
			if( getType(filename).equalsIgnoreCase(ext) )
			{				
				result = true;
				break;
			}			
		}
		return result;
	}
	
	/**
	 * checks if file is document
	 * @param filename
	 * @return boolean
	 */
	public static boolean isPdf(String filename)
	{
		boolean result = false;
		
		for( String ext : pdfExtensions)
		{			
			if( getType(filename).equalsIgnoreCase(ext) )
			{				
				result = true;
				break;
			}			
		}
		return result;
	}
	
	/**
	 * checks if file is document
	 * @param filename
	 * @return boolean
	 */
	public static boolean isCode(String filename)
	{
		boolean result = false;
		
		for( String ext : codeExtensions)
		{			
			if( getType(filename).equalsIgnoreCase(ext) )
			{				
				result = true;
				break;
			}			
		}
		return result;
	}
	
	/**
	 * This returns by appending data:image/jpeg;base64, to the beginning ofbase64 code
	 * used as src value in html img tag
	 * */
	public static String convertImageFileToBase64(String filePath)
	{				
		return "data:image/jpeg;base64,"+convertFileToBase64(filePath);
	}
	
	/**
	 * Converts supplied file to base64 and returns as String 
	 * **/
	public static String  convertFileToBase64(String filePath)
	{
		String result = "";
		try {			
			byte[] fileContent = null;
			FileInputStream fis = new FileInputStream(new File(filePath));
			if( fis != null)
			{
				//fileContent = fis.readAllBytes();
				result = Base64.getEncoder().encodeToString(fileContent);				
			}
		}catch(Exception e) {if(flagDebug) System.out.println(e.getMessage());}
		return result;
	}
	
	/**
	 * This function is used to extract image from video using ffmpeg ,to be used as thumbnail
	 * 
	 * @param filenameWithPath
	 */
	public static void extractImageUsingFFmpeg(String filenameWithPath)
	{
		extractImageUsingFFmpeg(filenameWithPath,1,128,72);
	}

	/**
	 * This function is used to extract image from video using ffmpeg ,to be used as thumbnail
	 * 
	 * @param filenameWithPath	 
	 * @param frames			no of frames to extract
	 * @param width				width of extracted image
	 * @param height			height of extracted image
	 */
	public static void extractImageUsingFFmpeg(String filenameWithPath,int frames,int width,int height)
	{
		try		
		{
			if( new File(System.getProperty("user.home")+"/.dfcache/" + GFileSystem.dropExtension(GFileSystem.getFilename(filenameWithPath))+".jpeg").exists()) return;
			for( int i = 0 ; i < ssArray.length ;i++)
			{
			//add these to the beginning of array if you want to display a separate terminal
			//for displaying output and errors of called program 
			//"gnome-terminal", "-t" ,"Downloading-Video-From-YouTube' ","--",
			String[] commandWithArgs = {"ffmpeg","-y","-ss",String.valueOf(ssArray[i]),"-i",filenameWithPath, 
					"-frames:v",String.valueOf(frames),"-s",String.valueOf(width)+"x"+String.valueOf(height),
					"-f","image2",System.getProperty("user.home")+"/.dfcache/" + GFileSystem.dropExtension(GFileSystem.getFilename(filenameWithPath))+".jpeg"};
						
			Process ffmpegProcess = Runtime.getRuntime().exec(commandWithArgs);
			
			//for some terminal programs like ffmpeg its compulsory to read output and error of the program
			//otherwise , it crashes and can't be used in java
			
			//this fetches the output of the process run using Runtime.getRuntime().exec();
			//the output of the running process is read in java as input and thus InputStream is used
			BufferedReader is = new BufferedReader(new InputStreamReader(ffmpegProcess.getInputStream()));			
			String line = "";
			while ((line = is.readLine()) != null)
				if(flagDebug) System.out.println(line);			
			
			//this fetches the error of the process run using Runtime.getRuntime().exec();
			//the error of the running process is read in java as input and thus InputStream is used			
			BufferedReader es = new BufferedReader(new InputStreamReader(ffmpegProcess.getErrorStream()));			
			while ((line = es.readLine()) != null)
			  if(flagDebug) System.out.println(line);
			
			ffmpegProcess.destroy();
			if( new File(System.getProperty("user.home")+"/.dfcache/" + GFileSystem.dropExtension(GFileSystem.getFilename(filenameWithPath))+".jpeg").exists()) break;				
			}
		}
		catch(Exception e)
		{
			if(flagDebug) System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Add newLine '\n' before every '<' in any file
	 * Its useful to display each tag in html or xml file in new line
	 * @param filename
	 */
	public static void addNewLine(String filename)
   {
	   try {
	   File file = new File(filename);
	   File file2 = new File(GFileSystem.dropExtension(file.getAbsolutePath())+"_with_newLine."+GFileSystem.getType(file.getAbsolutePath()));
	   if( file.exists() )
	   {
		   FileReader fileReader = new FileReader(file);
		   FileWriter fileWriter = new FileWriter(GFileSystem.dropExtension(file.getAbsolutePath())+"_with_newLine."+GFileSystem.getType(file.getAbsolutePath()));
		   int ch = 0;
		   ch = fileReader.read();
		   fileWriter.write(ch);//this prevent adding new line at start of file	
		   while(true )
		   {
			  ch = fileReader.read();
			  if(ch == -1) break;
			  if( ((char)ch) == '<' )
			  {
				  fileWriter.write('\n');
			  }
			  fileWriter.write(ch);			  			  		 
		   }
		   fileWriter.flush();
		   fileWriter.close();
		   
		   fileReader.close();
		   	
		   if( file2.exists() )
		   {
		   file.delete();
		   file2.renameTo(file);
		   }
	   }
	   else if(flagDebug) System.out.println(filename+ " file doesn't exist!!");
	   }
	   catch(Exception e)
	   {
		   if(flagDebug) System.out.println(e.getMessage());
	   }
   }
	/**
	 * useful in listing subfolders to be passed as arguments for -sourcepath to javadoc
	 * @param rootFolder
	 */
	public static void displayFoldersContainingJavaSourceFiles(String rootFolder)
	{
		File file = new File("rootFolder");
	  String[] folders = GFileSystem.listSubfoldersRecursively(file);
	  String[] files = null;
	  boolean flag_contains_java_file = false;    	  
	  for( String folder : folders )
	  {
		  flag_contains_java_file = false;
		  file = new File(folder);
		  files = file.list();
		  for( String filename : files)
		  {
			 if( filename.contains(".java") && !filename.contains("module-info")) 
			 {
			 flag_contains_java_file = true;
			 break;
			 }
	  }
	  folder = folder.replace("rootFolder", ".");
	  
	  if( flag_contains_java_file ) 
		  {
		  System.out.print(folder+"/*.java ");    			  
			  }
	  }   
	}
	
	public static void replaceWordsInFilename(String folderPath, String replaceThis, String withThis )
	{
		try {
			File folder = new File(folderPath);
			File newFile = null;
			
			String filename = "";
			if( folder.exists() && folder.isDirectory())
			{
				File files[] = folder.listFiles();
				
				for( File file : files )
				{
					if( file.isFile() )
					{
						filename = GFileSystem.getFilename(file.getAbsolutePath());
						filename = filename.replaceAll(replaceThis, withThis);
						filename = folderPath+"/"+filename;
						newFile = new File(filename);
						if( newFile.exists() ) 
						{
							if(flagDebug) System.out.println("Can't rename "+GFileSystem.getFilename(file.getAbsolutePath())+" to "+filename+", A file with new filename already exist!!");
						}
						else
						{
							//if(flagDebug) System.out.println(newFile.getAbsolutePath());
							file.renameTo(newFile);
						}
					}
				}
			}
			
		}catch(Exception e) {if(flagDebug) System.out.println(e.getMessage());}
	}
	
	public static String getExtension(String baseString)
	{
		if( baseString.contains(".") )
		{
			return baseString.substring( baseString.indexOf(".") , baseString.length() );
		}
		else return "";
	}
	
	public static String getExtensionWithoutDot(String baseString)
	{
		if( baseString.contains(".") )
		{
			return baseString.substring( baseString.lastIndexOf(".")+1 , baseString.length() );
		}
		else return "";
	}
	
	public static String getFilenameWithoutExtension(String baseString)
	{
		if( baseString.contains(".") )
		{
			return baseString.substring( 0 , baseString.indexOf("."));
		}
		else return baseString;
	}
	
	/*--------------------Functions for Handling Contents of Files----------------------*/
	
	
	public static void readTxtFile(String filename)
	{
		long startTimeInMilis = System.currentTimeMillis();
		boolean continueProcessing = true;
		while(continueProcessing)
		{
			try{
			String offsetS = "0";
			String numOfLinesS = "100";
			BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
			if(flagDebug) System.out.println("Enter offset from first line and number of lines to read from file:");
			offsetS = br.readLine();
			numOfLinesS = br.readLine();
			
			readTxtFile(filename,Integer.parseInt(offsetS),Integer.parseInt(numOfLinesS));
			
			if(flagDebug) System.out.println("Wanna continue?y/n");
			offsetS = br.readLine();
			if( offsetS.contains("n") || offsetS.contains("N") ) 
			{
				br.close();
				continueProcessing = false;
			}
			}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}			
		}
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));
	}
		
	public static void readTxtFile(String filename,int offset,int numOfLines)
	{		
		long startTimeInMilis = System.currentTimeMillis();
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(filename));
		    
			int linecount = 0;
		    String line = "";
		    while((line=br.readLine())!=null)
			{	
		    	linecount++;
		    	if( numOfLines != -1 )
		    	{
			    	if(linecount>offset && linecount <= offset+numOfLines)
			    	{
			    		if(flagDebug) System.out.println(line);
			    	}
			    	else if( linecount > offset+numOfLines)
			    	{
			    		break;
			    	}
		    	}
		    	else //when numOfLines == -1	, read all lines and add to vector
		    	{
		    		if(linecount>offset)
			    	{
		    			if(flagDebug) System.out.println(line);
			    	}
		    	}		    		
			}
		    br.close();		    
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));		
	}	
	
	/**
	 * Reads a text file(txt,html,css,java,...) and loads each line into the ArrayList<String> and returns the vector
	 * offset specifies number of lines to be skipped from beginning, numOfLines specifies
	 * number of lines to be read and loaded into vector from offset position
	 * if numOfLines==-1, all the lines from offset to end of file is read into vector
	 * @param filename
	 * @param offset
	 * @param numOfLines
	 * @return ArrayList<String>
	 */
        public static ArrayList<String> loadTxtFileIntoArrayList(URL filename,int offset,int numOfLines)
        {
            try{
            return loadTxtFileIntoArrayList(filename.toURI().getPath(),offset, numOfLines);	                     
            }catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}   
            return null;
        }
	public static ArrayList<String> loadTxtFileIntoArrayList(String filename,int offset,int numOfLines)
	{		
		long startTimeInMilis = System.currentTimeMillis();
		ArrayList<String> linesOfFile = new ArrayList<String>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
		    
			int linecount = 0;
		    String line = "";
		    while((line=br.readLine())!=null)
			{	
		    	linecount++;
		    	if( numOfLines != -1 )
		    	{
			    	if(linecount>offset && linecount <= offset+numOfLines)
			    	{
			    		linesOfFile.add(line);
			    	}
			    	else if( linecount > offset+numOfLines)
			    	{
			    		break;
			    	}
		    	}
		    	else //when numOfLines == -1	, read all lines and add to vector
		    	{
		    		if(linecount>offset)
			    	{
			    		linesOfFile.add(line);
			    	}
		    	}
		    	if(linecount%100000==0) if(flagDebug) System.out.println("please wait... "+linecount+" lines loaded in the vector!!!! still loading.....");		    	
			}
		    br.close();		    
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));
		return linesOfFile;
	}

        public static ArrayList<String> loadTxtFileIntoArrayList(InputStream inputStream,int offset,int numOfLines)
	{		
		long startTimeInMilis = System.currentTimeMillis();
		ArrayList<String> linesOfFile = new ArrayList<String>();
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		    
			int linecount = 0;
		    String line = "";
		    while((line=br.readLine())!=null)
			{	
		    	linecount++;
		    	if( numOfLines != -1 )
		    	{
			    	if(linecount>offset && linecount <= offset+numOfLines)
			    	{
			    		linesOfFile.add(line);
			    	}
			    	else if( linecount > offset+numOfLines)
			    	{
			    		break;
			    	}
		    	}
		    	else //when numOfLines == -1	, read all lines and add to vector
		    	{
		    		if(linecount>offset)
			    	{
			    		linesOfFile.add(line);
			    	}
		    	}
		    	if(linecount%100000==0) if(flagDebug) System.out.println("please wait... "+linecount+" lines loaded in the vector!!!! still loading.....");		    	
			}
		    br.close();		    
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));
		return linesOfFile;
	}

	/**
	 * Reads a xml file and loads contents of specified tag line into 
	 * the ArrayList<String> and returns the vector
	 * offset specifies number of tag contents to be skipped from beginning, numOfLines specifies
	 * number of lines to be read and loaded into vector from offset position
	 * if numOfLines==-1, all the lines from offset to end of file is read into vector
	 * @param filename
	 * @param offset
	 * @param numOfLines
	 * @return ArrayList<String>
	 */
	public static ArrayList<String> loadContentByTagXmlFileIntoArrayList(String filename,String TAGNAME,int offset,int numOfLines)
	{
		long startTimeInMilis = System.currentTimeMillis();
		ArrayList<String> linesOfFile = new ArrayList<String>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(filename));
		    
		    String line = "";
		    int linecount=0;
		    while(true)
			{			    	
		    	linecount++;
		    	try{
		    	line = getContentByTag(TAGNAME,br);
		    	}
		    	catch(EOFException e)
		    	{
		    		break;
		    	}
		    	if( numOfLines != -1 )
		    	{
			    	if(linecount>offset && linecount <= offset+numOfLines)
			    	{			    		
			    		linesOfFile.add(line);
			    	}
			    	else if( linecount > offset+numOfLines)
			    	{
			    		break;
			    	}
		    	}
		    	else //when numOfLines == -1	, read all lines and add to vector
		    	{
		    		if(linecount>offset)
			    	{
			    		linesOfFile.add(line);
			    	}
		    	}
		    	if(linecount%100000==0) if(flagDebug) System.out.println("please wait... "+linecount+" tag contents fetched from file!!!! still loading.....");		    	
			}
		    if(flagDebug) System.out.println("Done!!! "+linecount+" tag contents processed!!!!.....");
		    br.close();		    
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));
		return linesOfFile;
	}
	
	/**
	 * Counts Number of lines in a file for supplied BufferedReader
	 * @param br
	 * @return
	 */
	public static int countNumberOfLines(BufferedReader br)
	{
		int numOfLines = 0;
		try{
			while(br.readLine()!=null)
			{
				numOfLines++;
				if(numOfLines%100000==0) if(flagDebug) System.out.println("Don't worry, have already counted "+numOfLines+" lines, still counting...");
			}
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		return numOfLines;
	}
		
	/**
	 * This function exists to find begin tags that have attributes and white spaces;
	 * like <  title id=10 >;
	 * it can also determine normal tags like <title>
	 * Its a recursive function which finds first occurrence of tag in the line
	 * @param tagName
	 * @param line
	 * @return
	 */
	public static String determineBeginTag(String tagName,String line)
	{
		String BEGIN_TAG = "";		
		if( line.contains(tagName) && line.contains("<") && line.contains(">"))				 
		{
		int beginIndex = 0;
		int endIndex = 0;
		int tagNameIndex = 0;
		String strBetweenLTandTagName = "";
		String tempstr = "";
		
		try{	
			tagNameIndex = line.indexOf(tagName,beginIndex);
			endIndex = line.indexOf(">",tagNameIndex)+1;
			tempstr = line.substring(0, tagNameIndex);
			beginIndex = tempstr.lastIndexOf("<");
			
			//if(flagDebug) System.out.println("beginIndex="+beginIndex+" tagNameIndex="+tagNameIndex+" endIndex="+endIndex);
			if( beginIndex <= tagNameIndex ) 
			{
				strBetweenLTandTagName = line.substring(beginIndex+1, tagNameIndex);
				if( strBetweenLTandTagName.length()==0 || GString.areAllCharsWhitespace(strBetweenLTandTagName) )
				{
					BEGIN_TAG = line.substring(beginIndex, endIndex);							    
				}
				else//control reaches here when there was tagName but it was not valid tag, like "<page>title is<title>content</title></page>"
				{
					BEGIN_TAG = determineBeginTag(tagName,line.substring(tagNameIndex+tagName.length()));
				}
			}			
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		}
		return BEGIN_TAG;
	}
	
	/**
	 * This function exists to determine end tags with whitespaces;
	 * like <  /  title >;
	 * it can also determine normal tags like </title>
	 * It is a recursive function which finds last occurrence of end tag in the line 
	 * @param tagName
	 * @param line
	 * @return
	 */
	public static String determineEndTag(String tagName,String line)
	{		
		String END_TAG = "";
		//String beginTag = determineBeginTag(tagName,line);		
		//if( beginTag.length() > 0 ) line = line.substring(beginTag.indexOf(beginTag)+beginTag.length(), line.length());
		//else return END_TAG;
		if( line.contains(tagName) && line.contains("<") && line.contains("/") && line.contains(">"))				 
		{
		//if(flagDebug) System.out.println("line without beginTag : "+line);
		int beginIndex = 0;
		int endIndex = 0;
		int slashIndex = 0;
		int tagNameIndex = 0;
		String strBetweenSlashandTagName = "";
		String strBetweenLTandSlash = "";
		String tempstr = "";
				
		try{	
			
			tagNameIndex = line.lastIndexOf(tagName);
			if(tagNameIndex == -1) return "";//only give up when there is no tagName left in the line
			
			endIndex = line.indexOf(">",tagNameIndex)+1;
			tempstr = line.substring(0,tagNameIndex);
			beginIndex = tempstr.lastIndexOf("<");
			slashIndex = tempstr.lastIndexOf("/");
			
			//if(flagDebug) System.out.println("beginIndex="+beginIndex+" slashIndex="+slashIndex+" tagNameIndex="+tagNameIndex+" endIndex="+endIndex);
			
			if( beginIndex <= slashIndex)
			{				
				strBetweenLTandSlash = line.substring(beginIndex+1, slashIndex);
				if( strBetweenLTandSlash.length()==0 || GString.areAllCharsWhitespace(strBetweenLTandSlash) )
				{	
					if( slashIndex <= tagNameIndex ) 
					{
						strBetweenSlashandTagName = line.substring(slashIndex+1, tagNameIndex);
						if( strBetweenSlashandTagName.length()==0 || GString.areAllCharsWhitespace(strBetweenSlashandTagName) )
						{							
							END_TAG = line.substring(beginIndex, endIndex);	
							return END_TAG;
						}
						//else END_TAG = determineEndTag(tagName,line.substring(0,tagNameIndex));//if current tagName was not END_TAG, find it in line.substring( 0 , index of current tagName)
					}
					//else END_TAG = determineEndTag(tagName,line.substring(0,tagNameIndex));//do this for all failed cases
				}
				//END_TAG = determineEndTag(tagName,line.substring(0,tagNameIndex));
			}
			END_TAG = determineEndTag(tagName,line.substring(0,tagNameIndex));
		}catch(Exception e){/*if(flagDebug) System.out.println(e.getMessage());*/}
		}
		return END_TAG;
	}
	
	
	/**
	 * Seeks for a tag by tagName and returns only the "contents" of the first occurrence of the tag;
	 * tagName is "title" for TAG <title>contents...</title>
	 * If both BEGIN_TAG and END_TAG are on the same line, getContentByTag(tagName,line) is called;
	 * else getMultilineContentBetweenTag(BEGIN_TAG,END_TAG,line,br) is called; 
	 * @param tagName
	 * @param br
	 * @return
	 */
	public static String getContentByTag(String tagName,BufferedReader br) throws EOFException
	{
		StringBuilder tagContent = new StringBuilder();
		String BEGIN_TAG = "";
		String END_TAG = "</"+tagName+">";
		String line = "";
		try{	
			
			while(true)	    
		    {
				line=br.readLine();
				if(line==null) throw new EOFException();
				if( line.contains(tagName) && line.contains("<") && line.contains(">"))				 
				{					
					BEGIN_TAG = determineBeginTag(tagName,line);					
					//if(flagDebug) System.out.println("line is " + line+"\nBEGIN AND END TAGS ARE : "+BEGIN_TAG+ " " +END_TAG);
					if( BEGIN_TAG.length() < 3 ) continue;
					else// beginTagFound = true;
					{
						String tempLine = line.substring(line.indexOf(BEGIN_TAG)+BEGIN_TAG.length(), line.length());						
						END_TAG = determineEndTag(tagName,tempLine);
						if(END_TAG.length() < 4) 
						{
							//flow reached here, means BEGIN_TAG is present in the line , but END_TAG is not!!
							//so, we call getMultilineContentBetweenTag(BEGIN_TAG,"",line,br); to begin determining content within TAG tagName 
							tagContent.append( getMultilineContentBetweenTag(BEGIN_TAG,tagName,line,br) );
						}
						else if( line.contains(END_TAG))//flow reached here,it means both BEGIN_TAG AND END_TAG are present in same line
						{
							tagContent.append( getContentBetweenTag(BEGIN_TAG,END_TAG,line));
						}						
						break;
					}										
				}				
				//else if(flagDebug) System.out.println("line doesn't contain " +BEGIN_TAG);
		    }
			//if(br.readLine()==null) throw new EOFException();
		}catch( EOFException e){throw new EOFException();}
		catch(java.lang.IllegalStateException e){throw new EOFException();}
		catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		
		return tagContent.toString();
	}
    
	/**
	 * Seeks for a tag by tagName and returns the "tag with contents" of the first occurrence of the tag;
	 * tagName is "title" for TAG <title>contents...</title>
	 * If both BEGIN_TAG and END_TAG are on the same line, getContentByTag(tagName,line) is called;
	 * else getMultilineContentBetweenTag(BEGIN_TAG,END_TAG,line,br) is called; 
	 * @param tagName
	 * @param br
	 * @return
	 */
	public static String getElementByTag(String tagName,BufferedReader br) throws EOFException
	{
		StringBuilder tagContent = new StringBuilder();
		String BEGIN_TAG = "";
		String END_TAG = "</"+tagName+">";
		String line = "";
		try{		
			while(true)	    
		    {
				line=br.readLine();
				if(line==null) throw new EOFException();
				if( line.contains(tagName) && line.contains("<") && line.contains(">"))				 
				{					
					BEGIN_TAG = determineBeginTag(tagName,line);					
					//if(flagDebug) System.out.println("line is " + line+"\nBEGIN AND END TAGS ARE : "+BEGIN_TAG+ " " +END_TAG);
					if( BEGIN_TAG.length() < 3 ) continue;
					else// beginTagFound = true;
					{
						String tempLine = line.substring(line.indexOf(BEGIN_TAG)+BEGIN_TAG.length(), line.length());						
						END_TAG = determineEndTag(tagName,tempLine);
						if(END_TAG.length() < 4) 
						{
							//flow reached here, means BEGIN_TAG is present in the line , but END_TAG is not!!
							//so, we call getMultilineContentBetweenTag(BEGIN_TAG,"",line,br); to begin determining content within TAG tagName 
							tagContent.append( getMultilineContentWithTag(BEGIN_TAG,tagName,line,br) );												
						}
						else if( line.contains(END_TAG))//flow reached here,it means both BEGIN_TAG AND END_TAG are present in same line
						{
							tagContent.append( getContentWithTag(BEGIN_TAG,END_TAG,line));													
						}							
						break;
					}										
				}				
				//else if(flagDebug) System.out.println("line doesn't contain " +BEGIN_TAG);
		    }
			//if(br.readLine()==null) throw new EOFException();
		}
		catch( EOFException e){throw new EOFException();}
		catch(java.lang.IllegalStateException e){throw new EOFException();}
		catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		
		return tagContent.toString();
	}

	public static String getMultilineContentBetweenTag(String BEGIN_TAG,String tagName,String lineWithBEGIN_TAG,BufferedReader br)
	{
		StringBuilder tagContent = new StringBuilder();
		String END_TAG = "";
		if( lineWithBEGIN_TAG.contains(BEGIN_TAG) )
		{
			tagContent.append(lineWithBEGIN_TAG.substring(lineWithBEGIN_TAG.indexOf(BEGIN_TAG)+BEGIN_TAG.length(), lineWithBEGIN_TAG.length()));
		}
		try{
		String line = "";
		while((line=br.readLine())!=null)	    
	    {	 
			END_TAG = determineEndTag(tagName,line);
	    	
			if( END_TAG.length()>3 && line.contains(END_TAG))
    		{
				tagContent.append("\n");
	    		tagContent.append(line.substring(0, line.indexOf(END_TAG)));
	    		break;
    		}
	    	else 
	    	{
	    		tagContent.append("\n");
	    		tagContent.append(line);
	    	}
	    }
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		return tagContent.toString();
	}
	
	public static String getMultilineContentWithTag(String BEGIN_TAG,String tagName,String lineWithBEGIN_TAG,BufferedReader br)
	{
		StringBuilder tagContent = new StringBuilder();
		String END_TAG = "";
		if( lineWithBEGIN_TAG.contains(BEGIN_TAG) )
		{
			tagContent.append(lineWithBEGIN_TAG);
		}
		try{
		String line = "";
		while((line=br.readLine())!=null)	    
	    {	 
			END_TAG = determineEndTag(tagName,line);
	    	
			if( END_TAG.length()>3 && line.contains(END_TAG))
    		{
				tagContent.append("\n");
	    		tagContent.append(line.substring(0, line.indexOf(END_TAG)+END_TAG.length()));
	    		break;
    		}
	    	else 
	    	{
	    		tagContent.append("\n");
	    		tagContent.append(line);
	    	}
	    }
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		return tagContent.toString();
	}
	
	public static String getContentBetweenTag(String BEGIN_TAG,String END_TAG,String line)
	{
		String tagContent = "";
		try {
		int beginIndex = line.indexOf(BEGIN_TAG)+BEGIN_TAG.length();
		int endIndex = line.indexOf(END_TAG);
		
		tagContent = line.substring(beginIndex, endIndex);
		}catch(Exception e) {}
		return tagContent;
	}
	
	public static String getContentWithTag(String BEGIN_TAG,String END_TAG,String line)
	{
		String tagContent = "";
		int beginIndex = line.indexOf(BEGIN_TAG);
		int endIndex = line.indexOf(END_TAG)+END_TAG.length();
		
		tagContent = line.substring(beginIndex, endIndex);
		return tagContent;
	}
        
        /**
         * Added on 12 October, 2020
         * this will return the parts after the 1st tag(i.e. remaining part after 1st tag)!! very useful when single line has multiple same tags!!         
         * @param END_TAG
         * @param line
         * @return 
         */
        public static String getContentAfterTag(String END_TAG,String line)
	{
		String contentAfterTag = "";
		int endIndex = line.indexOf(END_TAG)+END_TAG.length();
		if( endIndex < 0 ) return "";
                if( line.length() < END_TAG.length() ) return "";
		contentAfterTag = line.substring(endIndex);
		return contentAfterTag;
	}
        
        public static void saveLinesToATxtFile(String filename , ArrayList<String> lines)
	{
		long startTimeInMilis = System.currentTimeMillis();
		
		try{			
			BufferedWriter bw = new BufferedWriter(new FileWriter(filename));		    			
						
			int linecount = 0;
			for( String line : lines)
			{
				linecount++;
				
				bw.write(line);
				bw.newLine();
				
				if(linecount%100000==0) if(flagDebug) System.out.println("please wait... "+linecount+" lines processed!!!!.....");
			}
			if(flagDebug) System.out.println("Done!!! "+linecount+" lines processed!!!!.....");
			bw.flush();
			bw.close();						
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());e.printStackTrace();}
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));		
	}
	
        public static void saveTextToATxtFile(String filename , String lines)
	{
            long startTimeInMilis = System.currentTimeMillis();

            try{			
                BufferedWriter bw = new BufferedWriter(new FileWriter(filename));		    			

                int linecount = 0;

                bw.write(lines);
                bw.newLine();


                bw.flush();
                bw.close();						
            }catch(Exception e){if(flagDebug) System.out.println(e.getMessage());e.printStackTrace();}
            long endTimeInMilis = System.currentTimeMillis();
            if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));		
	}

	public static void saveLinesToATxtFile(String filename , String[] lines)
	{
		long startTimeInMilis = System.currentTimeMillis();
		
		try{			
			BufferedWriter bw = new BufferedWriter(new FileWriter(filename));		    			
						
			int linecount = 0;
			for( String line : lines)
			{
				linecount++;
				
				bw.write(line);
				bw.newLine();
				
				if(linecount%100000==0) if(flagDebug) System.out.println("please wait... "+linecount+" lines processed!!!!.....");
			}
			if(flagDebug) System.out.println("Done!!! "+linecount+" lines processed!!!!.....");
			bw.flush();
			bw.close();						
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());e.printStackTrace();}
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));		
	}
	
	public static void appendLinesToATxtFile(String filename , String[] lines)
	{
		long startTimeInMilis = System.currentTimeMillis();
		
		try{			
			BufferedWriter bw = new BufferedWriter(new FileWriter(filename,true));		    			
						
			int linecount = 0;
			for( String line : lines)
			{
				linecount++;
								
				bw.write(line);
				bw.newLine();
				
				if(linecount%100000==0) if(flagDebug) System.out.println("please wait... "+linecount+" lines processed!!!!.....");
			}
			//if(flagDebug) System.out.println("Done!!! "+linecount+" lines processed!!!!.....");
			bw.flush();
			bw.close();						
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		long endTimeInMilis = System.currentTimeMillis();
		//if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));		
	}
	
	
	
	public static void saveBySortingInSeparateFile()
	{
		long startTimeInMilis = System.currentTimeMillis();
		
		try{
			String sourceFilename = "/home/gulshan/Documents/ToshibaPenDrive/enwiktionary-latest-pages-entitles.txt";
			String destFilename="/home/gulshan/Documents/ToshibaPenDrive/SortedFiles/enwiktionary-latest-pages-entitles-sorted.txt";
			
			//to delete exiting content of the file before appending in the loop below
			FileWriter fr = new FileWriter(destFilename);
			fr.close();
			
			 for(char ch = 'a'; ch <= 'z' ; ch++)
			 {
				 if(flagDebug) System.out.println("Sorting lines with "+ch+" at first position!!");
				 appendLinesToATxtFile(destFilename,sortLinesOfAFileWithSubtringAt(sourceFilename,String.valueOf(ch),0));
			 }
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));	
	}
	
	public static String[] sortLinesOfAFileWithSubtringAt(String sourceFilename,String substring,int index)
	{
		String[] sortedLinesArray = null;
		SortedSet<String> sortedLines = new TreeSet<String>();
		
		long startTimeInMilis = System.currentTimeMillis();
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(sourceFilename));
		    
			int linecount = 0;
		    String line = "";
		    while((line=br.readLine())!=null)
			{	
		    	linecount++;
		    	if( line.indexOf(substring)==index )
		    	{
		    		sortedLines.add(line);	
		    	}
		    	if(linecount%100000==0) if(flagDebug) System.out.println("please wait... "+linecount+" lines processed!!!!.....");
			}
		    if(flagDebug) System.out.println("please wait... "+linecount+" lines processed!!!!.....");
		    br.close();	
		    
		    sortedLinesArray = new String[sortedLines.size()];
			int i = 0;
			Iterator<String> it = sortedLines.iterator();
			while( it.hasNext())
			{
				sortedLinesArray[i++] = it.next();	
				if(i%100000==0) if(flagDebug) System.out.println("please wait... "+i+" lines added to array!!!!.....");
			}
			if(flagDebug) System.out.println("Done!!! "+i+" lines added to array!!!!.....");			
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));	
		return sortedLinesArray;
	}
	
	
	public static String[] sortLinesOfAFile(String sourceFilename)
	{
		String[] sortedLinesArray = null;
		SortedSet<String> sortedLines = new TreeSet<String>();
		
		long startTimeInMilis = System.currentTimeMillis();
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(sourceFilename));
		    
			int linecount = 0;
		    String line = "";
		    while((line=br.readLine())!=null)
			{	
		    	linecount++;
		    	sortedLines.add(line);	
		    	if(linecount%100000==0) if(flagDebug) System.out.println("please wait... "+linecount+" lines processed!!!!.....");
			}
		    if(flagDebug) System.out.println("please wait... "+linecount+" lines processed!!!!.....");
		    br.close();	
		    sortedLinesArray = new String[sortedLines.size()];
			int i = 0;
			Iterator<String> it = sortedLines.iterator();
			while( it.hasNext())
			{
				sortedLinesArray[i++] = it.next();	
				if(i%100000==0) if(flagDebug) System.out.println("please wait... "+i+" lines added to array!!!!.....");
			}
			if(flagDebug) System.out.println("Done!!! "+i+" lines added to array!!!!.....");			
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));	
		return sortedLinesArray;
	}
			
	public static void searchInTxtFile(String filename)
	{
		long startTimeInMilis = System.currentTimeMillis();
		
		boolean continueProcessing = true;
		while(continueProcessing)
		{
			try{
			String lineToBeSearched = "0";			
			BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
			if(flagDebug) System.out.println("Enter line/string to be searched in file:");
			lineToBeSearched = br.readLine();
			
			searchInTxtFile(filename,lineToBeSearched);
			
			if(flagDebug) System.out.println("Wanna continue?y/n");
			lineToBeSearched = br.readLine();
			if( lineToBeSearched.contains("n") || lineToBeSearched.contains("N") ) 
			{
				br.close();
				continueProcessing = false;
			}
			}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}			
		}
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));
	}
	
	public static void searchInTxtFile(String filename,String lineToBeSearched)
	{		
		long startTimeInMilis = System.currentTimeMillis();
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(filename));
		    
			int linecount = 0;
			int exactMatchCount = 0;
			int containsCount = 0;
		    String line = "";
		    String linelowercase = "";
		    while((line=br.readLine())!=null)
			{	
		    	linecount++;
		    	linelowercase = line.toLowerCase();
		    	lineToBeSearched = lineToBeSearched.toLowerCase();
		    	if( linelowercase.equals(lineToBeSearched))
		    	{
		    		exactMatchCount++;
		    		if(flagDebug) System.out.println(linecount+": "+line);
		    	}
		    	else if( linelowercase.contains(lineToBeSearched) )
		    	{
		    		containsCount++;
		    		if(flagDebug) System.out.println(linecount+": "+line);
		    	}
			}
		    if(flagDebug) System.out.println(exactMatchCount+" words/expression match exactly(ignoring case) " + lineToBeSearched);
		    if(flagDebug) System.out.println(containsCount+" words/expression contains(ignoring case) " + lineToBeSearched);
		    br.close();		    
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));		
	}
	
	/**
         * Added this function on 12 October, 2020
         * @param filename 
         */
        public static String findFirstTag(String line)
        {
            boolean tagStarted = false;
            String foundTag = "";
            for( int i = 0 ; i < line.length()-1 ; i++ )
            {
                if( line.charAt(i) == '<' && line.charAt(i+1) != '/')
                {
                    tagStarted = true;
                    continue;
                }
                if(tagStarted)
                {
                    if( line.charAt(i) != ' ' && line.charAt(i) != '>' && line.charAt(i) != '"' && line.charAt(i) != '\'')
                    {
                        foundTag+= line.charAt(i);
                    }
                    else{
                        break;
                    }
                }
            }
            return foundTag;
        }
	
        /**
         * Added this function on 12 October, 2020
         * @param filename 
         */
        public static HashSet<String> listAllTagsTxtFile(String filename)
	{
		long startTimeInMilis = System.currentTimeMillis();
		boolean continueProcessing = true;
                
                HashSet<String> uniqueTags = new HashSet<String>();
		
                try{
                    BufferedReader br2 = new BufferedReader(new FileReader(filename));

                    String foundTag = "";
                    int linecount=0;
                    String readLine;
                    while( (readLine = br2.readLine())!=null )
                    {			    	
                        linecount++;                        
                        foundTag = GFileSystem.findFirstTag(readLine);
                        uniqueTags.add(foundTag);                        
                        
                        
                        if(linecount%1000==0) System.out.println("please wait... "+linecount+" tag fetched from file!!!! still loading.....");		    	
                    }
                
                br2.close();

                for( String tag : uniqueTags )
                    System.out.println(tag);
                System.out.println("System.out.println(uniqueTags.size());"+uniqueTags.size());
                        
                }catch(Exception e){e.printStackTrace();}			

		long endTimeInMilis = System.currentTimeMillis();
		System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));
                return uniqueTags;
	}
	
	
	/**
	 * This function reads lines from sourceFilename and writes only those lines containing
	 * valid English chars as determined by the function GString.containsOnlyValidEnglishChars(String) into destFilename
	 * @param sourceFilename
	 * @param destFilename
	 */
	public static void filterEnglishWordsAndExpressions(String sourceFilename,String destFilename)
	{
		long startTimeInMilis = System.currentTimeMillis();
		
		try{
			BufferedReader br = new BufferedReader( new FileReader(sourceFilename));
			BufferedWriter bw = new BufferedWriter( new FileWriter(destFilename));
			
			String line = "";
			int linecount = 0;
			while((line=br.readLine())!=null)
			{
				linecount++;
				if(GString.containsOnlyValidEnglishChars(line))
				{
					bw.write(line);
					bw.newLine();
				}
				if(linecount%100000==0) if(flagDebug) System.out.println("please wait... "+linecount+" lines processed!!!!.....");
			}
			if(flagDebug) System.out.println("Done!!! "+linecount+" lines processed!!!!.....");
			bw.flush();
			bw.close();
			
			br.close();
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));		
	}
	
	public static void viewContentByTagTxtFile(String filename)
	{
		long startTimeInMilis = System.currentTimeMillis();
		boolean continueProcessing = true;
		while(continueProcessing)
		{
			try{
				String TAGNAME="page";
				String offsetS = "0";
				String numOfLinesS = "100";
				BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
				if(flagDebug) System.out.println("Enter name of tag whose content is to be read;like title for <title>tag-content</title> :");
				TAGNAME = br.readLine();
				if(flagDebug) System.out.println("Enter offset from first line :");
				offsetS = br.readLine();
				if(flagDebug) System.out.println("Enter number of tag-contents to read from file:");
				numOfLinesS = br.readLine();
				
				int numOfLines = Integer.parseInt(numOfLinesS);
				int offset = Integer.parseInt(offsetS);
				
				BufferedReader br2 = new BufferedReader(new FileReader(filename));
			    
				String line = "";
			    int linecount=0;
			    while(true)
				{			    	
			    	linecount++;
			    	try{
			    	line = GFileSystem.getContentByTag(TAGNAME,br2);
			    	}catch(EOFException e)
			    	{
			    		break;
			    	}
			    	if( numOfLines != -1 )
			    	{
				    	if(linecount>offset && linecount <= offset+numOfLines)
				    	{			    		
				    		if(flagDebug) System.out.println(line);
				    	}
				    	else if( linecount > offset+numOfLines)
				    	{
				    		break;
				    	}
			    	}
			    	else //when numOfLines == -1	, read all lines and add to vector
			    	{
			    		if(linecount>offset)
				    	{
			    			if(flagDebug) System.out.println(line);
				    	}
			    	}
			    	if(linecount%1000==0) if(flagDebug) System.out.println("please wait... "+linecount+" tag fetched from file!!!! still loading.....");		    	
				}
				
			if(flagDebug) System.out.println("Wanna continue?y/n");
			offsetS = br.readLine();
			if( offsetS.contains("n") || offsetS.contains("N") ) 
			{
				br.close();
				br2.close();
				continueProcessing = false;
			}
			}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}			
		}
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));
	}
        
        /**
         * returns 1 on successful move, returns -1 when dest already has the file with same name
         * and thus doesn't overwrite it!!
         * @param sourceFile
         * @param destFile
         * @return 
         */
        public static int moveFile(File sourceFile, File destFile) {
        try {
            if (sourceFile.exists()) {
                if(flagDebug) System.out.println("Souce file exists!!");
                if (!destFile.exists()) {
                    if(flagDebug) System.out.println("Dest file doesn't exist!!");
                    if(flagDebug) System.out.println("Moving file to " + destFile.getAbsolutePath());
                    try {
                        destFile.getParentFile().mkdirs();
                    } catch (Exception e) {
                        StackTraceElement[] stes= e.getStackTrace();            for( Object ste: stes)                if(flagDebug) System.out.println(ste);
                    }
                    destFile.createNewFile();
                    //----------------------- place below code in outside if  block if you want dest file to be overwritten if it already exists!!
                    BufferedInputStream br = new BufferedInputStream(new FileInputStream(sourceFile));
                    BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(destFile));
                    byte buffer[] = new byte[4096];
                    int numOfByteRead = 0;
                    while ((numOfByteRead = br.read(buffer)) >0) {
                        bw.write(buffer,0,numOfByteRead);                    
                    }
                    bw.flush();
                    bw.close();

                    br.close();

                    if (sourceFile.delete()) {
                        if(flagDebug) System.out.println("SourceFile deleted successfully");
                    } else {
                        if(flagDebug) System.out.println("Couldn't delete sourcefile");
                    }
                    return 1;
                    //-----------------------
                }else return -1;                
            } else {
                if(flagDebug) System.out.println("Souce file doesn't exist!!");
            }
        } catch (Exception e) {
            StackTraceElement[] stes= e.getStackTrace();            for( Object ste: stes)                if(flagDebug) System.out.println(ste);
        }
        return 0;
    }
       
    /**
    * returns 1 on successful move, returns -1 when dest already has the file with same name
    * and thus doesn't overwrite it!!
    * @param sourceFile
    * @param destFile
    * @return 
    */
    public static int copyFile(File sourceFile, File destFile) 
    {
        try {
            if (sourceFile.exists()) {
                if(flagDebug) System.out.println("Souce file exists!!");
                if (!destFile.exists()) {
                    if(flagDebug) System.out.println("Dest file doesn't exist!!");
                    if(flagDebug) System.out.println("Moving file to " + destFile.getAbsolutePath());
                    try {
                        destFile.getParentFile().mkdirs();
                    } catch (Exception e) {
                        StackTraceElement[] stes= e.getStackTrace();            for( Object ste: stes)                if(flagDebug) System.out.println(ste);
                    }
                    destFile.createNewFile();
                    
                    //----------------------- place below code in above if  block if you don't want dest file to be overwritten if it already exists!!
                    BufferedInputStream br = new BufferedInputStream(new FileInputStream(sourceFile));
                    BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(destFile));
                    byte buffer[] = new byte[4096];
                    int numOfByteRead = 0;
                    while ((numOfByteRead = br.read(buffer)) >0) {
                        bw.write(buffer,0,numOfByteRead);                    
                    }
                    bw.flush();
                    bw.close();

                    br.close(); 
                    
                    return 1;
                    //-----------------------
                }else return -1;                
            } else {
                if(flagDebug) System.out.println("Souce file doesn't exist!!");
            }
        } catch (Exception e) {
            StackTraceElement[] stes= e.getStackTrace();            for( Object ste: stes)                if(flagDebug) System.out.println(ste);
        }
        return 0;
    }
    
    public static void copyStream(InputStream sourceStream, File destFile) 
    {
        try {
            if (sourceStream!=null) {
                if(flagDebug) System.out.println("Souce file exists!!");
                if (!destFile.exists()) {
                    if(flagDebug) System.out.println("Dest file doesn't exist!!");
                    if(flagDebug) System.out.println("Moving file to " + destFile.getAbsolutePath());
                    try {
                        destFile.getParentFile().mkdirs();
                    } catch (Exception e) {
                        StackTraceElement[] stes= e.getStackTrace();            for( Object ste: stes)                if(flagDebug) System.out.println(ste);
                    }
                    destFile.createNewFile();
                }
                //----------------------- place below code in above if  block if you don't want dest file to be overwritten if it already exists!!
                BufferedInputStream br = new BufferedInputStream(sourceStream);
                BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(destFile));
                byte buffer[] = new byte[4096];
                int numOfByteRead = 0;
                while ((numOfByteRead = br.read(buffer)) >0) {
                    bw.write(buffer,0,numOfByteRead);                    
                }
                bw.flush();
                bw.close();

                br.close();                
                //-----------------------
            } else {
                if(flagDebug) System.out.println("Souce file doesn't exist!!");
            }
        } catch (Exception e) {
            StackTraceElement[] stes= e.getStackTrace();            for( Object ste: stes)                if(flagDebug) System.out.println(ste);
        }
    }
    
    public static void copyStream(InputStream sourceStream, File destFile,boolean flagOverwrite) {
        try {
            if (sourceStream != null) {
                //GConsoleTextArea.println("Souce file exists!!");
                if (!destFile.exists() || flagOverwrite) {
                    //GConsoleTextArea.println("Dest file doesn't exist!!");
                    //GConsoleTextArea.println("Moving file to " + destFile.getAbsolutePath());
                    try {
                        destFile.getParentFile().mkdirs();
                    } catch (Exception e) {
                        StackTraceElement[] stes = e.getStackTrace();
                        for (Object ste : stes) {
//                            GConsoleTextArea.println(ste);
                        }
                    }
                    destFile.createNewFile();
                    //----------------------- place below code in above if  block if you don't want dest file to be overwritten if it already exists!!
                    BufferedInputStream br = new BufferedInputStream(sourceStream);
                    BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(destFile));
                    byte buffer[] = new byte[4096];
                    int numOfByteRead = 0;
                    while ((numOfByteRead = br.read(buffer)) > 0) {
                        bw.write(buffer, 0, numOfByteRead);
                    }
                    bw.flush();
                    bw.close();

                    br.close();
                    //-----------------------
                }                
            } else {
//                GConsoleTextArea.println("Souce file doesn't exist!!");
            }
        } catch (Exception e) {
            StackTraceElement[] stes = e.getStackTrace();
            for (Object ste : stes) {
//                GConsoleTextArea.println(ste);
            }
        }
    }


	//------------->Funtions that rely on Apache Common Compress<------------------

	/*
	public static void viewContentByTagBzip2File(String filename)
	{
		long startTimeInMilis = System.currentTimeMillis();
		boolean continueProcessing = true;
		while(continueProcessing)
		{
			long startTimeInMilis2 = 0;//System.currentTimeMillis();
			try{
				String TAGNAME="page";
				String offsetS = "0";
				String numOfLinesS = "100";
				String skiplinecountS ="0";
				BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
				if(flagDebug) System.out.println("Enter name of tag whose content is to be read;like title for <title>tag-content</title> :");
				TAGNAME = br.readLine();
				if(flagDebug) System.out.println("Enter offset from first tag :");
				offsetS = br.readLine();
				if(flagDebug) System.out.println("Enter number of tag-contents to read from file:");
				numOfLinesS = br.readLine();
				if(flagDebug) System.out.println("Enter number of lines to skip from file:");
				skiplinecountS = br.readLine();
				
				int numOfLines = Integer.parseInt(numOfLinesS);
				int offset = Integer.parseInt(offsetS);
				int skiplinecount = Integer.parseInt(skiplinecountS);
				
				FileInputStream fin = new FileInputStream(filename);
			    BufferedInputStream bis = new BufferedInputStream(fin);
			    MultiStreamBZip2InputStream input = new MultiStreamBZip2InputStream(bis);//this was used in place of below line to add support for multistream bzip2 file
			    //CompressorInputStream input = new CompressorStreamFactory().createCompressorInputStream(bis);
			    BufferedReader br2 = new BufferedReader(new InputStreamReader(input));
			    
			    startTimeInMilis2 = System.currentTimeMillis();
			    
			    //br2.skip(654620037);
				String line = "";
			    int linecount=0;
			    			    
			    while(linecount<skiplinecount)
			    {
			    	linecount++;
			    	br2.readLine();
			    }
			    linecount=0;
			    while(true)
				{			    	
			    	linecount++;
			    	try{
			    	line = getContentByTag(TAGNAME,br2);
			    	}
			    	catch(EOFException e)
			    	{
			    		break;
			    	}
			    	//if(flagDebug) System.out.println("title : "+getContentBetweenTag(determineBeginTag("title",line),determineEndTag("title",line),line));
			    	if( numOfLines != -1 )
			    	{
				    	if(linecount>offset && linecount <= offset+numOfLines)
				    	{		
				    		//line = applyFormatting(line);
				    		if(flagDebug) System.out.println(line);
				    	}
				    	else if( linecount > offset+numOfLines)
				    	{
				    		break;
				    	}
			    	}
			    	else //when numOfLines == -1	, read all lines and add to vector
			    	{
			    		if(linecount>offset)
				    	{
			    			//line = applyFormatting(line);
			    			if(flagDebug) System.out.println(line);
				    	}
			    	}
			    	if(linecount%1000==0) if(flagDebug) System.out.println("please wait... "+linecount+" tag fetched from file!!!! still loading.....");		    	
				}
				
			    long endTimeInMilis2 = System.currentTimeMillis();
				if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis2-startTimeInMilis2));    
				if(flagDebug) System.out.println("Wanna continue?y/n");
				offsetS = br.readLine();
				if( offsetS.contains("n") || offsetS.contains("N") ) 
				{
					br.close();
					br2.close();
					continueProcessing = false;
				}
			}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}			
		}
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));
	}
	
	public static void readBzip2File(String filename)
	{
		long startTimeInMilis = System.currentTimeMillis();
		boolean continueProcessing = true;
		while(continueProcessing)
		{
			try{
			String offsetS = "0";
			String numOfLinesS = "100";
			BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
			if(flagDebug) System.out.println("Enter offset from first line and number of lines to read from file:");
			offsetS = br.readLine();
			numOfLinesS = br.readLine();
			
			readBzip2File(filename,Integer.parseInt(offsetS),Integer.parseInt(numOfLinesS));
			
			if(flagDebug) System.out.println("Wanna continue?y/n");
			offsetS = br.readLine();
			if( offsetS.contains("n") || offsetS.contains("N") ) 
			{
				br.close();
				continueProcessing = false;
			}
			}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}			
		}
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));
	}
	public static void readBzip2File(String filename,int offset,int numOfLines)
	{
		long startTimeInMilis = System.currentTimeMillis();
		
		try{
			FileInputStream fin = new FileInputStream(filename);
		    BufferedInputStream bis = new BufferedInputStream(fin);
		    MultiStreamBZip2InputStream multiinput = new MultiStreamBZip2InputStream(bis);//this was used in place of below line to add support for multistream bzip2 file
		    //CompressorInputStream input = new CompressorStreamFactory().createCompressorInputStream(multiinput);		    
		    BufferedReader br = new BufferedReader(new InputStreamReader(multiinput));
		    
		    int linecount = 0;
		    String line = "";
		    while((line=br.readLine())!=null)
			{	
		    	linecount++;
		    	if( numOfLines != -1 )
		    	{
			    	if(linecount>offset && linecount <= offset+numOfLines)
			    	{
			    		if(flagDebug) System.out.println(line);
			    	}
			    	else if( linecount > offset+numOfLines)
			    	{
			    		break;
			    	}
		    	}
		    	else //when numOfLines == -1	, read all lines and add to vector
		    	{
		    		if(linecount>offset)
			    	{
		    			if(flagDebug) System.out.println(line);
			    	}
		    	}
			}
		    br.close();
		    multiinput.close();
		    bis.close();
		    fin.close();
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));
	}
	
		
	/**
	 * Reads a bzip2 compressed text file(txt,html,css,java,...) and loads each line into 
	 * the ArrayList<String> and returns the vector
	 * offset specifies number of lines to be skipped from beginning, numOfLines specifies
	 * number of lines to be read and loaded into vector from offset position
	 * if numOfLines==-1, all the lines from offset to end of file is read into vector
	 * @param filename
	 * @param offset
	 * @param numOfLines
	 * @return ArrayList<String>
	 *
	public static ArrayList<String> loadBzip2FileIntoArrayList(String filename,int offset,int numOfLines)
	{
		long startTimeInMilis = System.currentTimeMillis();
		ArrayList<String> linesOfFile = new ArrayList<String>();
		try{
			FileInputStream fin = new FileInputStream(filename);
		    BufferedInputStream bis = new BufferedInputStream(fin);
		    MultiStreamBZip2InputStream input = new MultiStreamBZip2InputStream(bis);//this was used in place of below line to add support for multistream bzip2 file
		    //CompressorInputStream input = new CompressorStreamFactory().createCompressorInputStream(bis);
		    BufferedReader br = new BufferedReader(new InputStreamReader(input));
		    
		    String line = "";
		    int linecount=0;
		    while((line=br.readLine())!=null)
			{			    	
		    	linecount++;
		    	if( numOfLines != -1 )
		    	{
			    	if(linecount>offset && linecount <= offset+numOfLines)
			    	{
			    		linesOfFile.add(line);
			    	}
			    	else if( linecount > offset+numOfLines)
			    	{
			    		break;
			    	}
		    	}
		    	else //when numOfLines == -1	, read all lines and add to vector
		    	{
		    		if(linecount>offset)
			    	{
			    		linesOfFile.add(line);
			    	}
		    	}
		    	if(linecount%100000==0) if(flagDebug) System.out.println("please wait... "+linecount+" lines loaded in the vector!!!! still loading.....");		    	
			}
		    if(flagDebug) System.out.println("Done!!! "+linecount+" lines loaded in the vector!!!!.....");
		    br.close();
		    input.close();
		    bis.close();
		    fin.close();
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));
		return linesOfFile;
	}
	
	/**
	 * Reads a bzip2 compressed xml file and loads contents of specified tag line into 
	 * the ArrayList<String> and returns the vector
	 * offset specifies number of tag contents to be skipped from beginning, numOfLines specifies
	 * number of lines to be read and loaded into vector from offset position
	 * if numOfLines==-1, all the lines from offset to end of file is read into vector
	 * @param filename
	 * @param offset
	 * @param numOfLines
	 * @return ArrayList<String>
	 *
	public static ArrayList<String> loadContentByTagBzip2FileIntoArrayList(String filename,String TAGNAME,int offset,int numOfLines)
	{
		long startTimeInMilis = System.currentTimeMillis();
		ArrayList<String> linesOfFile = new ArrayList<String>();
		try{
			FileInputStream fin = new FileInputStream(filename);
		    BufferedInputStream bis = new BufferedInputStream(fin);
		    MultiStreamBZip2InputStream input = new MultiStreamBZip2InputStream(bis);//this was used in place of below line to add support for multistream bzip2 file
		    //CompressorInputStream input = new CompressorStreamFactory().createCompressorInputStream(bis);
		    BufferedReader br = new BufferedReader(new InputStreamReader(input));
		    
		    String line = "";
		    int linecount=0;
		    while(true)
			{			    	
		    	linecount++;
		    	try{
		    	line = getContentByTag(TAGNAME,br);
		    	}
		    	catch(EOFException e)
		    	{
		    		break;
		    	}		    	
		    	if( numOfLines != -1 )
		    	{
			    	if(linecount>offset && linecount <= offset+numOfLines)
			    	{			    		
			    		linesOfFile.add(line);
			    	}
			    	else if( linecount > offset+numOfLines)
			    	{
			    		break;
			    	}
		    	}
		    	else //when numOfLines == -1	, read all lines and add to vector
		    	{
		    		if(linecount>offset)
			    	{
			    		linesOfFile.add(line);
			    	}
		    	}
		    	if(linecount%100000==0) if(flagDebug) System.out.println("please wait... "+linecount+" tag fetched from file!!!! still loading.....");		    	
			}
		    if(flagDebug) System.out.println("Done!!! "+linecount+" tag contents processed!!!!.....");
		    br.close();
		    input.close();
		    bis.close();
		    fin.close();
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));
		return linesOfFile;
	}
	
	public static void saveLinesToABzip2File(String filename , String[] lines)
	{
		long startTimeInMilis = System.currentTimeMillis();
		
		try{			
			FileOutputStream fout = new FileOutputStream(filename+".bz2");
		    BufferedOutputStream bos = new BufferedOutputStream(fout);
		    CompressorOutputStream output = new CompressorStreamFactory().createCompressorOutputStream(CompressorStreamFactory.BZIP2,bos);
		    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(output));		    			
						
			int linecount = 0;
			for( String line : lines)
			{
				linecount++;
				
				bw.write(line);
				bw.newLine();
				
				if(linecount%100000==0) if(flagDebug) System.out.println("please wait... "+linecount+" lines processed!!!!.....");
			}
			System.out.print(filename);
			if(flagDebug) System.out.println("Done!!! "+linecount+" lines processed!!!!.....");
			bw.flush();
			bw.close();						
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));		
	}
	
	public static void appendLinesToABzip2File(String filename , String[] lines)
	{
		long startTimeInMilis = System.currentTimeMillis();
		
		try{			
			FileOutputStream fout = new FileOutputStream(filename+".bz2",true);
		    BufferedOutputStream bos = new BufferedOutputStream(fout);
		    CompressorOutputStream output = new CompressorStreamFactory().createCompressorOutputStream(CompressorStreamFactory.BZIP2,bos);
		    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(output));		    			
						
			//int linecount = 0;
			for( String line : lines)
			{
				//linecount++;
				
				bw.write(line);
				bw.newLine();
				
				//if(linecount%100000==0) if(flagDebug) System.out.println("please wait... "+linecount+" lines processed!!!!.....");
			}
			//if(flagDebug) System.out.println("Done!!! "+linecount+" lines processed!!!!.....");
			bw.flush();
			bw.close();						
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		long endTimeInMilis = System.currentTimeMillis();
		//if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));		
	}
	
	public static String[] sortLinesOfABzip2FileWithSubtringAt(String sourceFilename,String substring,int index)
	{
		String[] sortedLinesArray = null;
		SortedSet<String> sortedLines = new TreeSet<String>();
		
		long startTimeInMilis = System.currentTimeMillis();
		
		try{
			FileInputStream fin = new FileInputStream(sourceFilename);
		    BufferedInputStream bis = new BufferedInputStream(fin);
		    MultiStreamBZip2InputStream input = new MultiStreamBZip2InputStream(bis);//this was used in place of below line to add support for multistream bzip2 file
		    //CompressorInputStream input = new CompressorStreamFactory().createCompressorInputStream(bis);
		    BufferedReader br = new BufferedReader(new InputStreamReader(input));
		    
			int linecount = 0;
		    String line = "";
		    while((line=br.readLine())!=null)
			{	
		    	linecount++;
		    	if( line.indexOf(substring)==index )
		    	{
		    		sortedLines.add(line);	
		    	}
		    	if(linecount%100000==0) if(flagDebug) System.out.println("please wait... "+linecount+" lines processed!!!!.....");
			}
		    if(flagDebug) System.out.println("please wait... "+linecount+" lines processed!!!!.....");
		    br.close();
		    
		    sortedLinesArray = new String[sortedLines.size()];
			int i = 0;
			Iterator<String> it = sortedLines.iterator();
			while( it.hasNext())
			{
				sortedLinesArray[i++] = it.next();	
				if(i%100000==0) if(flagDebug) System.out.println("please wait... "+i+" lines added to array!!!!.....");
			}
			if(flagDebug) System.out.println("Done!!! "+i+" lines added to array!!!!.....");
			
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));	
		return sortedLinesArray;
	}
	
	public static String[] sortLinesOfABzip2File(String sourceFilename)
	{
		String[] sortedLinesArray = null;
		SortedSet<String> sortedLines = new TreeSet<String>();
		
		long startTimeInMilis = System.currentTimeMillis();
		
		try{
			FileInputStream fin = new FileInputStream(sourceFilename);
		    BufferedInputStream bis = new BufferedInputStream(fin);
		    MultiStreamBZip2InputStream input = new MultiStreamBZip2InputStream(bis);//this was used in place of below line to add support for multistream bzip2 file
		    //CompressorInputStream input = new CompressorStreamFactory().createCompressorInputStream(bis);
		    BufferedReader br = new BufferedReader(new InputStreamReader(input));
		    
			int linecount = 0;
		    String line = "";
		    while((line=br.readLine())!=null)
			{	
		    	linecount++;
		    	sortedLines.add(line);	
		    	if(linecount%100000==0) if(flagDebug) System.out.println("please wait... "+linecount+" lines processed!!!!.....");
			}
		    if(flagDebug) System.out.println("please wait... "+linecount+" lines processed!!!!.....");
		    br.close();	
		    
		    sortedLinesArray = new String[sortedLines.size()];
			int i = 0;
			Iterator<String> it = sortedLines.iterator();
			while( it.hasNext())
			{
				sortedLinesArray[i++] = it.next();	
				if(i%100000==0) if(flagDebug) System.out.println("please wait... "+i+" lines added to array!!!!.....");
			}
			if(flagDebug) System.out.println("Done!!! "+i+" lines added to array!!!!.....");			
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));	
		return sortedLinesArray;
	}
	
	public static void compressTxtFileBzip2(String sourceFilename)
	{
		long startTimeInMilis = System.currentTimeMillis();
		
		try{
			BufferedReader br = new BufferedReader( new FileReader(sourceFilename));
			
			FileOutputStream fout = new FileOutputStream(sourceFilename+".bz2");
		    BufferedOutputStream bos = new BufferedOutputStream(fout);
		    CompressorOutputStream output = new CompressorStreamFactory().createCompressorOutputStream(CompressorStreamFactory.BZIP2,bos);
		    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(output));		    			
			
			String line = "";
			int linecount = 0;
			while((line=br.readLine())!=null)
			{
				linecount++;
				
				bw.write(line);
				bw.newLine();
				
				if(linecount%100000==0) if(flagDebug) System.out.println("please wait... "+linecount+" lines processed!!!!.....");
			}
			if(flagDebug) System.out.println("Done!!! "+linecount+" lines processed!!!!.....");
			bw.flush();
			bw.close();
			
			br.close();
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));		
	}
	
	public static void decompressBzip2TxtFile(String sourceFilename)
	{
		long startTimeInMilis = System.currentTimeMillis();
		
		try{
			FileInputStream fin = new FileInputStream(sourceFilename);
		    BufferedInputStream bis = new BufferedInputStream(fin);
		    MultiStreamBZip2InputStream input = new MultiStreamBZip2InputStream(bis);//this was used in place of below line to add support for multistream bzip2 file
		    //CompressorInputStream input = new CompressorStreamFactory().createCompressorInputStream(bis);
		    BufferedReader br = new BufferedReader(new InputStreamReader(input));
		    
		    String destFilename = "";
		    if( sourceFilename.contains(".bz2") )
		    	destFilename = sourceFilename.substring(0,sourceFilename.indexOf(".bz2"));
		    else destFilename = sourceFilename+".txt";
			BufferedWriter bw = new BufferedWriter(new FileWriter(destFilename));		    			
			
			String line = "";
			int linecount = 0;
			while((line=br.readLine())!=null)
			{
				linecount++;
				
				bw.write(line);
				bw.newLine();
				
				if(linecount%100000==0) if(flagDebug) System.out.println("please wait... "+linecount+" lines processed!!!!.....");
			}
			if(flagDebug) System.out.println("Done!!! "+linecount+" lines processed!!!!.....");
			bw.flush();
			bw.close();
			
			br.close();
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));		
	}
	
	/**
	 * This function counts total Elements by tagName in a Bzip2 file
	 * @param sourceFilename
	 * @param tagName
	 *
	public static int countTagElementsBzip2(String sourceFilename,String tagName)
	{
		long startTimeInMilis = System.currentTimeMillis();
		int tagcount = 0;
		try{
			FileInputStream fin = new FileInputStream(sourceFilename);
		    BufferedInputStream bis = new BufferedInputStream(fin);
		    MultiStreamBZip2InputStream input = new MultiStreamBZip2InputStream(bis);//this was used in place of below line to add support for multistream bzip2 file
		    //CompressorInputStream input = new CompressorStreamFactory().createCompressorInputStream(bis);
		    BufferedReader br = new BufferedReader(new InputStreamReader(input));
		   
			boolean continueProcessing = true;
			while(continueProcessing)
			{
				tagcount++;
				try{
					GFileSystem.getElementByTag(tagName,br);
				}catch(EOFException e){continueProcessing=false;}
								
				if(tagcount%1000==0) if(flagDebug) System.out.println("please wait... "+tagcount+" tags processed!!!!.....");
			}			
			if(flagDebug) System.out.println("Done!!! "+tagcount+" tags processed!!!!.....");
			
			br.close();
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));		
		return tagcount;
	}
	
	/**
	 * This function filters page tags whose title tag contains at least one valid English chars
	 * @param sourceFilename
	 * @param destFilename
	 *
	public static void filterContainingEnglishWordsAndExpressionsBzip2(String sourceFilename,String destFilename)
	{
		long startTimeInMilis = System.currentTimeMillis();
		
		try{
			FileInputStream fin = new FileInputStream(sourceFilename);
		    BufferedInputStream bis = new BufferedInputStream(fin);
		    MultiStreamBZip2InputStream input = new MultiStreamBZip2InputStream(bis);//this was used in place of below line to add support for multistream bzip2 file
		    //CompressorInputStream input = new CompressorStreamFactory().createCompressorInputStream(bis);
		    BufferedReader br = new BufferedReader(new InputStreamReader(input));
		    
			FileOutputStream fout = new FileOutputStream(destFilename);
		    BufferedOutputStream bos = new BufferedOutputStream(fout);
		    CompressorOutputStream output = new CompressorStreamFactory().createCompressorOutputStream(CompressorStreamFactory.BZIP2,bos);
		    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(output));		    			
			
		    String page = "";
			String line = "";
			int linecount = 0;
			//while((line=br.readLine())!=null)
			bw.write("<wiktionary>");
			bw.newLine();
			boolean continueProcessing = true;
			while(continueProcessing)
			{
				linecount++;
				try{
				page = GFileSystem.getElementByTag("page",br);
				}catch(EOFException e){continueProcessing=false;}
				
				line = GFileSystem.getContentBetweenTag(GFileSystem.determineBeginTag("title",page),GFileSystem.determineEndTag("title",page),page);
				if(GString.containsAtleastOneEnglishAlphabet(line))
				{
					bw.write(page);
					bw.newLine();
				}
				if(linecount%1000==0) if(flagDebug) System.out.println("please wait... "+linecount+" tags processed!!!!.....");
			}
			bw.write("</wiktionary>");
			bw.newLine();
			if(flagDebug) System.out.println("Done!!! "+linecount+" tags processed!!!!.....");
			bw.flush();
			bw.close();
			
			br.close();
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));		
	}
	
	/**
	 * This function filters lines containing only valid English chars
	 * @param sourceFilename
	 * @param destFilename
	 *
	public static void filterOnlyEnglishWordsAndExpressionsBzip2(String sourceFilename,String destFilename)
	{
		long startTimeInMilis = System.currentTimeMillis();
		
		try{
			FileInputStream fin = new FileInputStream(sourceFilename);
		    BufferedInputStream bis = new BufferedInputStream(fin);
		    MultiStreamBZip2InputStream input = new MultiStreamBZip2InputStream(bis);//this was used in place of below line to add support for multistream bzip2 file
		    //CompressorInputStream input = new CompressorStreamFactory().createCompressorInputStream(bis);
		    BufferedReader br = new BufferedReader(new InputStreamReader(input));
		    
			FileOutputStream fout = new FileOutputStream(destFilename);
		    BufferedOutputStream bos = new BufferedOutputStream(fout);
		    CompressorOutputStream output = new CompressorStreamFactory().createCompressorOutputStream(CompressorStreamFactory.BZIP2,bos);
		    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(output));		    			
			
		    String page = "";
			String line = "";
			int linecount = 0;
			//while((line=br.readLine())!=null)
			bw.write("<wiktionary>");
			bw.newLine();
			boolean continueProcessing = true;
			while(continueProcessing)
			{
				linecount++;
				try{
				page = GFileSystem.getElementByTag("page",br);
				}catch(EOFException e){continueProcessing=false;}
				
				line = GFileSystem.getContentBetweenTag(GFileSystem.determineBeginTag("title",page),GFileSystem.determineEndTag("title",page),page);
				if(GString.containsOnlyValidEnglishChars(line))
				{
					bw.write(page);
					bw.newLine();
				}
				if(linecount%1000==0) if(flagDebug) System.out.println("please wait... "+linecount+" tags processed!!!!.....");
			}
			bw.write("</wiktionary>");
			bw.newLine();
			if(flagDebug) System.out.println("Done!!! "+linecount+" tags processed!!!!.....");
			bw.flush();
			bw.close();
			
			br.close();
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));		
	}
	public static void searchInBzip2File(String filename)
	{
		long startTimeInMilis = System.currentTimeMillis();
		boolean continueProcessing = true;
		while(continueProcessing)
		{
			try{
			String lineToBeSearched = "0";			
			BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
			if(flagDebug) System.out.println("Enter line/string to be searched in file:");
			lineToBeSearched = br.readLine();
			
			searchInBzip2File(filename,lineToBeSearched);
			
			if(flagDebug) System.out.println("Wanna continue?y/n");
			lineToBeSearched = br.readLine();
			if( lineToBeSearched.contains("n") || lineToBeSearched.contains("N") ) 
			{
				br.close();
				continueProcessing = false;
			}
			}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}			
		}
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));
	}
	public static void searchInBzip2File(String filename,String lineToBeSearched)
	{		
		long startTimeInMilis = System.currentTimeMillis();
		
		try{
			FileInputStream fin = new FileInputStream(filename);
		    BufferedInputStream bis = new BufferedInputStream(fin);
		    MultiStreamBZip2InputStream input = new MultiStreamBZip2InputStream(bis);//this was used in place of below line to add support for multistream bzip2 file
		    //CompressorInputStream input = new CompressorStreamFactory().createCompressorInputStream(bis);
		    BufferedReader br = new BufferedReader(new InputStreamReader(input));
		    
			int linecount = 0;
			int exactMatchCount = 0;
			int containsCount = 0;
		    String line = "";
		    String linelowercase = "";
		    while((line=br.readLine())!=null)
			{	
		    	linecount++;
		    	linelowercase = line.toLowerCase();
		    	lineToBeSearched = lineToBeSearched.toLowerCase();
		    	if( linelowercase.equals(lineToBeSearched))
		    	{
		    		exactMatchCount++;
		    		if(flagDebug) System.out.println(linecount+": "+line);
		    	}
		    	else if( linelowercase.contains(lineToBeSearched) )
		    	{
		    		containsCount++;
		    		if(flagDebug) System.out.println(linecount+": "+line);
		    	}
			}
		    if(flagDebug) System.out.println(exactMatchCount+" words/expression match exactly(ignoring case) " + lineToBeSearched);
		    if(flagDebug) System.out.println(containsCount+" words/expression contains(ignoring case) " + lineToBeSearched);
		    br.close();		    
		}catch(Exception e){if(flagDebug) System.out.println(e.getMessage());}
		long endTimeInMilis = System.currentTimeMillis();
		if(flagDebug) System.out.println("Time elapsed: "+(endTimeInMilis-startTimeInMilis));		
	}
	*/
}
