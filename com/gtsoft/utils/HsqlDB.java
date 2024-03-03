/*
 * Created on 5-lug-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.gtsoft.rifiuti.data.sql.SQLNave;
import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.rifiuti.front.RifiutiFrame.Cost;
import com.gtsoft.utils.superdata.IDatabase;
import com.gtsoft.utils.superdata.ISQLAdapter;

/**
 * @author Gtron
 */
public class HsqlDB extends IDatabase {
	
	public final static String filename = Cost.DATABASE;
	public final static String DATADIR = Cost.DATADIR ;
	public final static String BACKUPDIR = DATADIR + "autobackup/"  ;
	
	
	private final static String username = "sa";
	private final static String password = "";
	
	public int getDbType() {
        return IDatabase.DBType.HSQL ;
    }
	
	private static Connection hsqldbConn = null ;
	
	public Connection getConnection() throws Exception {
		
		return getConnection(filename);
	}
	
	public Connection getConnection(String file) throws Exception {
		
		if ( hsqldbConn != null && ! hsqldbConn.isClosed()) {
			return hsqldbConn ;
		}
		else {
		    Class.forName("org.hsqldb.jdbcDriver").newInstance();
		    
			try {
			    hsqldbConn = DriverManager.getConnection
				     ("jdbc:hsqldb:file:" + file , username, password );
			} 
			catch (Exception e) {
				try{
					RifiutiFrame.read_only=true;	
					hsqldbConn=DriverManager.getConnection
						("jdbc:hsqldb:file:" + file + ".tmp" , username, password );
				
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(null,"Errore fatale!!! hai più di due sessioni aperte, questa verrà chiusa");
					System.exit(0);
				}
			}
			return hsqldbConn;
		}
	}
	
	
	public void importData ( IDatabase db ) throws Exception {
	    
	    ISQLAdapter[][] sqlHandlers = getAccessSqlHandlers( db ) ;
	    Vector list = null ; 
	        
	    for ( int i = 0 ; i < sqlHandlers.length  ; i++) {
	        list = sqlHandlers[i][1].getAll();
	        
	        if ( list != null ) {
	            for ( int j = 0 ; j < list.size() ; j++ ) {
	                sqlHandlers[i][0].syncFields( list.get(j) ) ; 
	                sqlHandlers[i][0].create() ;
	            }
	        }
	    }
	    
	}
	
	public void end() throws Exception {
		if ( stmt != null )
			stmt.close();
		if ( conn != null && ! conn.isClosed() ) {
		    executeNonQuery("checkpoint;");
			conn.close();
		}
	}
	      
	public void shutdown() throws Exception {
		shutdown(false);
	}
	
	public void shutdown(boolean doCompact) throws Exception {
		try {
	    	Thread.sleep(1000);
	    }
	    catch( Exception e ) {} 
		if ( getConnection() != null && ! getConnection().isClosed() ) {
			String q = "shutdown" ;
			if ( doCompact )
				q += " compact";
		    executeNonQuery(q );
		    
		    
		    if ( stmt != null )
				stmt.close();
		    conn.close();
		}
		else if ( stmt != null )
			stmt.close();
		
		stmt = null ;
		conn = null ;
		hsqldbConn = null ;
	}
	
	public void startTransaction() throws Exception {
        hsqldbConn.setAutoCommit(false);
    }
	public void commit() throws Exception {
	    hsqldbConn.commit();
        hsqldbConn.setAutoCommit(true);
	}
	
	public void rollback() {
	    try {
	        hsqldbConn.rollback();
	        hsqldbConn.setAutoCommit(true);
	    }
	    catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public ISQLAdapter[][] getAccessSqlHandlers( IDatabase db ) {
	    
	    ISQLAdapter[][] sqlHandlers = new ISQLAdapter[1][2] ;
	    
	    int i = 0 ;
	    sqlHandlers[i][0] = new SQLNave( this ) ;	
	    sqlHandlers[i][1] = new SQLNave( db ) ;
	    
	    i++ ;
	    
	    return sqlHandlers ;
	    
	}
 

//	private void duplicate_database(){
//		duplicate_database(Cost.DATABASE);
//	}

	private void duplicate_database(String suffix){
		try {
			FileManager.copy(Cost.DATABASE+".data",suffix + ".tmp" +".data");
			FileManager.copy(Cost.DATABASE+".script",suffix + ".tmp" +".script"); 
			FileManager.copy(Cost.DATABASE+".properties",suffix + ".tmp" +".properties");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void export_database() throws Exception {
		export_database(Cost.DATABASE);	
	}

	public void export_database(String suffix) throws Exception {
		
		if( conn != null && !conn.isClosed()){
			end();
			duplicate_database(suffix);		
			conn=null;
			start();
		}	
		else 
			duplicate_database(suffix);		
	}

    /**
     * 
     */
    public void checkpoint() {
        try {
            executeNonQuery("checkpoint;");
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void backup(String comment) {

        try {
            FormattedDate day = new FormattedDate();
            day.setFormat("yyyy-MM-dd-HH-mm-ss");
            
            String fileDb = Cost.DATABASE.replaceAll("data/", "");
                        
            FileManager.createDir(BACKUPDIR);
            FileManager.createFile(BACKUPDIR + day + "_" + fileDb + "_"+ comment + ".txt", false) ;
            
            String fileBackup = BACKUPDIR + day + "_" + fileDb + ".backup" ;
            String fileScript = BACKUPDIR + day + "_" + fileDb + ".script" ;
            String fileProp = BACKUPDIR + day + "_" + fileDb + ".properties" ;
            
            FileManager.copy(Cost.DATABASE+".backup", fileBackup);
            FileManager.copy(Cost.DATABASE+".properties", fileProp);
            FileManager.copy(Cost.DATABASE+".script", fileScript);
            
            //Runtime.getRuntime().exec("data\\createRestore.bat \"" + comment.replace(' ','_') + "\" " + day ) ;
            // creare il bat per il ripristino
            // poi fare l'undo che lancia il bat e riapre la conn al db
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void restore(String filename) throws Exception {
        
        
        String fileDb = Cost.DATABASE.replaceAll("data/", "");
                
        
        String[] tok = filename.split("_");
        String day = "";
        
        if ( tok.length == 2  ){
            day = tok[0] ;
        }
        else {
            throw new Exception("Filename errato.") ;
        }
        
        
//        FileManager.createFile(dest + day + "_" + fileDb + "_"+ comment + ".txt") ;
        
        this.shutdown() ;

        this.backup("Restore_al_" + day );

        String fileBackup =  BACKUPDIR + day + "_" + fileDb + ".backup" ;
        String fileScript = BACKUPDIR + day + "_" + fileDb + ".script" ;
        String fileProp = BACKUPDIR + day + "_" + fileDb + ".properties" ;
        
        FileManager.createFile(Cost.DATABASE+".backup", true) ;
        FileManager.createFile(Cost.DATABASE+".properties", true) ;
        FileManager.createFile(Cost.DATABASE+".data", true) ;
        
        FileManager.copy( fileBackup ,Cost.DATABASE+".backup");
        FileManager.copy( fileProp, Cost.DATABASE+".properties");
        FileManager.copy( fileScript, Cost.DATABASE+".script");
        
        FileManager.createFile(Cost.DATABASE+".data", true) ;
        
        Properties dbProps = new Properties();
        FileInputStream in = new FileInputStream(Cost.DATABASE+".properties");
        dbProps.load(in);
        in.close();
        
        dbProps.put("modified", "yes");
        
        FileOutputStream out = new FileOutputStream(Cost.DATABASE+".properties");
        dbProps.store(out, "Modified");
        out.close();
    }
    
    public void backupYear( String anno ) throws Exception {

    	String otherYearFileDb = Cost.DATABASE.replaceAll("data/", "data/" + anno + "/");
        
    	String newDataDir = DATADIR + anno + "/" ;
    	if ( !  FileManager.existsDir(newDataDir) ) {
    		FileManager.createDir(newDataDir);
        
            String destFileBackup =  otherYearFileDb + ".backup" ;
            String destFileScript = otherYearFileDb + ".script" ;
            String destFileProp = otherYearFileDb + ".properties" ;
            
			FileManager.copy( Cost.DATABASE+".backup" , destFileBackup );
	        FileManager.copy( Cost.DATABASE+".properties" , destFileProp );
	        FileManager.copy( Cost.DATABASE+".script" , destFileScript );
        
    	}
    	else { 
    		throw new Exception("Year is already done !") ;
    	}

    }
    
    public void changeYear(int anno) throws Exception {
        
    	Vector anniPresenti = FileManager.listDirs(DATADIR);
    	
    	if ( anniPresenti != null && anniPresenti.size() > 0 ) {
    		boolean found = false ;
    		String name = null ;
    		for ( Iterator i = anniPresenti.iterator() ; i.hasNext() ; ) {
    			name = (String) i.next() ;
    			if ( name.equals( "" + anno ) ) { 
    				found = true ;
    				break ;
    			}
    		}
    		
//    		String fileBackup =  Cost.DATABASE + ".backup" ;
//            String fileScript = Cost.DATABASE + ".script" ;
//            String fileProp = Cost.DATABASE + ".properties" ;
            
            String otherYearFileDb = Cost.DATABASE.replaceAll("data/", "data/" + anno + "/");
            
            this.shutdown() ;
    		
    		if ( found ) {
    			// sto andando indietro in un anno passato
    			this.getConnection(otherYearFileDb);
    		}
    		else {
    			if ( anno == Calendar.getInstance().get(Calendar.YEAR)) {
    				this.getConnection();
    			}
    			else { 
    				throw new Exception ("Anno non trovato:" + anno);
    			}
    		}
    	}
    }
    
    public void restoreLast() throws Exception {
        Vector files = FileManager.listFiles( BACKUPDIR ) ;
        
        if ( files.size() > 0 ) {
            restore(((File)files.lastElement()).getName()) ;
        }
    }
}
