/*
 * Created on 29-giu-2005
 *
 * All rights reserved 
 */
package com.gtsoft.utils;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

import com.gtsoft.rifiuti.data.sql.SQLNave;
import com.gtsoft.rifiuti.superdata.IAccessDBEnabled;
import com.gtsoft.utils.superdata.IDatabase;


/**
 * @author Gtron
 */
public class AccessDB extends IDatabase {
	
	private final static String PATH = "c:/Pesa1/" ;
//	private final static String FILE = "movimentazioni.mdb";
	
	private static String filename ;
	
	private static Connection aConn = null ;
	
	
	
	public AccessDB(String f ) {
	    filename = f ;
	}
	
	public AccessDB(String file , String par ) {
	    filename = PATH + file + ";" + par ;
	    
	}
	
	public int getDbType() {
	    return IDatabase.DBType.ACCESS ;
	}
	
	public Connection getConnection() {
		
		if ( aConn != null ) {
			return aConn ;
		}
		else {
		    try {
		    	
//		    	return new DbConnectionBroker("net.ucanaccess.jdbc.UcanaccessDriver",
//						"jdbc:ucanaccess://" + filename + ";openExclusive=false;ignoreCase=false;", user, pwd, 1, 1,
//						connectionLogFile, 1.0);		    	
		    	 // Class.forName("net.ucanaccess.jdbc.UcanaccessDriver"); /* often not required for Java 6 and later (JDBC 4.x) */
		    	 // Connection conn=DriverManager.getConnection("jdbc:ucanaccess://<mdb or accdb file path>",user, password);
		    	 // for example:
		    	 //Connection conn=DriverManager.getConnection("jdbc:ucanaccess://c:/pippo.mdb;memory=true"); 
		    	 
		    	System.out.println("Connection to file " + filename);
		    	
		    	Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		    	
				// Class.forName("net.ucanaccess.jdbc.UcanaccessDriver").newInstance();
				aConn = DriverManager.getConnection
				     ("jdbc:ucanaccess://" + filename + ";openExclusive=false;ignoreCase=false;" );
				return aConn;
		    }
		    catch ( Exception e ){
		    	e.printStackTrace();
		        JOptionPane.showMessageDialog(null, "Attenzione: Si è verificato un errore\ndurante l'accesso al Database Access",
	                    "Attenzione",
	                    JOptionPane.WARNING_MESSAGE);
		        return null ;
		    }
		}
	}

	protected IAccessDBEnabled[] sqlHandlers = null ;
	public IAccessDBEnabled[] getSqlHandlers() {
	    
	    sqlHandlers[0] = new SQLNave( this ) ;	    
	    
        return sqlHandlers;	        
	}

    /* (non-Javadoc)
     * @see com.gtsoft.utils.superdata.IDatabase#export_database()
     */
    public void export_database() throws Exception {

        
    }

    /* (non-Javadoc)
     * @see com.gtsoft.utils.superdata.IDatabase#export_database(java.lang.String)
     */
    public void export_database(String suffix) throws Exception {
        
    }
	
}
