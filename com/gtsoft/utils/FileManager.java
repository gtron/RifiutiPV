/*
 * FileManager.java
 *
 * Created on 10. duben 2002, 12:41
 */

package com.gtsoft.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author  ghort
 * @version 
 */
public class FileManager {
    
	public static void copy(java.io.File destination, java.io.File source) throws Exception {
		if (source.isDirectory()) {
			if (!destination.isDirectory()) {
				throw new Exception("Destination '"+destination.getName()+"' is not directory.");
			}
			copyDirectory(destination,source);
		} else {
			if (destination.isDirectory()) {
				destination=new java.io.File(destination,source.getName());
			}
			copyFile(destination,source);
		}
	}
	
	protected static void copyDirectory(java.io.File destination, java.io.File source) throws Exception {
		java.io.File[] list=source.listFiles();
		for (int i=0;i<list.length;i++) {
			java.io.File dest=new java.io.File(destination,list[i].getName());
			if (list[i].isDirectory()) {
				dest.mkdir();
				copyDirectory(dest,list[i]);
			} else {
				copyFile(dest,list[i]);
			}
		}
	}
	
	protected static void copyFile(java.io.File destination, java.io.File source) throws Exception {
		try {
			java.io.FileInputStream inStream=new java.io.FileInputStream(source);
			java.io.FileOutputStream outStream=new java.io.FileOutputStream(destination);

			int len;
			byte[] buf=new byte[2048];
			 
			while ((len=inStream.read(buf))!=-1) {
				outStream.write(buf,0,len);
			}
		} catch (Exception e) {
			throw new Exception("Can't copy file "+source+" -> "+destination+".",e);
		}
	}
	
	public static void copy(String input, String output) throws Exception{
		File in=new File(input);
		File out=new File(output);
		
		FileManager.copy(out,in);
		
	}
	
	public static void createDir( String dir ) {
	    File directory = new File( dir );
	     if ( ! directory.exists() ) {
	        directory.mkdir();
	        return;
	     }
	}
	
	public static boolean existsDir( String dir ) {
	    File directory = new File( dir );
	    return directory.exists() ;
	}
	
	public static File createFile( String filename , boolean overwrite ) {
	    File f = new File( filename );
	    
	    if ( overwrite && f.exists() ) {
	        f.delete();
	    }	    
	    if ( ! f.exists() || overwrite )
	        try {
	            f.createNewFile() ;
	            return f ;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    
	    return null ;
	}
	
	public static Vector listFiles( String dir ) {
	    Vector list = new Vector() ;
	    File f =  new File ( dir ) ;
	    
	    if ( f.isDirectory() ) {
	        File[] backupFiles = f.listFiles( new FileFilter() {
                public boolean accept(File f) {
                    boolean b = f.getName().endsWith(".backup") ;
                    return  b ;
                	}
            });
	        
	        for ( int i = 0 ; i < backupFiles.length ; i++ ) {
	            list.add( backupFiles[i]) ;
	        }
	    }
	        
	    return list;
	}
	
	public static Vector listDirs( String parent ) {
	    Vector list = new Vector() ;
	    File f =  new File ( parent ) ;
	    
	    if ( f.isDirectory() ) {
	        File[] backupFiles = f.listFiles( new FileFilter() {
                public boolean accept(File f) {
                   return ( f.isDirectory() && 
                   	! f.getName().equals(".") &&
                   	! f.getName().equals("..") ); }
            });
	        
	        for ( int i = 0 ; i < backupFiles.length ; i++ ) {
	            list.add( backupFiles[i].getName()) ;
	        }
	    }
	        
	    return list;
	}
	
	
	public static HashMap listBackups( String dir ) {
		HashMap list = new HashMap() ;
		File f =  new File ( dir ) ;

        
		FormattedDate backupDay = null ;
		
		
		
	    if ( f.isDirectory() ) {
	    	File[] backupFiles = f.listFiles(new FileFilter() {
                public boolean accept(File f) {
                    return f.isFile() ; } 
                });
	    	
	        File curr = null ;
	        HashMap day = null ;
	        String filename = null ;
	        
	        for ( int i = 0 ; i < backupFiles.length ; i++ ) {
	        	
	        	curr = backupFiles[i] ;
	        	filename = curr.getName() ;
	        	
	        	backupDay = new FormattedDate( filename.split("_")[0] , "yyyy-MM-dd-HH-mm-ss" ) ;
	        	
	        	if ( list.containsKey(backupDay) ) {
	        		day = (HashMap) list.get(backupDay) ;
	        	}
	        	else {
	        		day = new HashMap(3);
	        		list.put( backupDay, day ) ;
	        	}
	        	
	        	
	        	if ( filename.endsWith(".backup")) {
	        		day.put("backup",filename);
	        	}
	        	else if ( filename.endsWith(".txt")) {
	        	
		        	String comment = " - " ;
	        		String line = null ;
	        		if ( curr.length() > 0 ) {
	        			try {
	        				BufferedReader fr = new BufferedReader( new FileReader ( curr ) ) ;
	        				while ( ((line = fr.readLine()) != null) ) {
	        					comment += line;
	        				}
	        			}
	        			catch ( Exception e ) { comment = " N/A " ; } 
	        		}
	        		else {
	        			comment = filename.substring( filename.indexOf('_',20) +1  , filename.lastIndexOf('.')) ;
	        			comment = comment.replaceAll("_", " ");
	        		}
	        		day.put("comment", backupDay.dmyString() + " " + backupDay.hmsString() +  " : " + comment);	        		
	        	}
	        	
	        }
	    }
	        
	    return list;
	}
}
