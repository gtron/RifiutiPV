/*
 * Created on 9-lug-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.front;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Vector;

import com.gtsoft.rifiuti.data.Carico;
import com.gtsoft.rifiuti.data.sql.SQLCliente;
import com.gtsoft.utils.AccessDB;
import com.gtsoft.utils.HsqlDB;

/**
 * @author Gtron - 
 * 
 */
public class Test {

    public static void main(String[] args) throws Exception  {
        
        
        //ReportManager.printPageNumber( 5 , 10 ) ;
        
        //ReportManager.printRegistro( Movimento.getAll(new RifiutiFrame().getDatabase()) ) ;
        
	    
    }
    
    public static void importMovimenti() throws Exception  {
        
         AccessDB db = new AccessDB("") ;
         HsqlDB hdb = new HsqlDB() ;
         
        
         try {
             
         Vector listaMovimenti = Carico.getAllFromAccess(db) ;
 		
 		StringBuilder str  ;
 		
 		Iterator i = listaMovimenti.iterator() ;
 		
 		Carico m = null ;
 		while( i.hasNext() ) {
 		    m = (Carico) i.next() ;
 		    
 		        m.create(hdb);
 		   
 		}
 		
 		/*
 		Statement upd = null ;
 		upd = hdb.getConnection().createStatement() ;
 		
 		
 		upd.executeUpdate("CREATE TABLE NAVI(CODICE VARCHAR(255) NOT NULL PRIMARY KEY,DESCRIZIONE VARCHAR)");
 		
 		//upd.executeUpdate("CREATE TEXT TABLE NAVI(CODICE VARCHAR(255) NOT NULL PRIMARY KEY,DESCRIZIONE VARCHAR)");
 		//upd.executeUpdate("SET TABLE NAVI SOURCE \"navi.csv;fs=|\"");
         
 		while (false && rs.next()) {
 		    
 		    str = new StringBuilder() ;
 		    
 		    upd = hdb.getConnection().createStatement() ;
 		    
 		    str.append("INSERT INTO NAVI VALUES ('") ;
 		    str.append( rs.getObject("Codice") + "' , '");
 		    str.append( rs.getObject("Descrizione") + "' )");
 		    
 		    upd.executeUpdate( str.toString() );
 	      }
 		stmt.close();
 		
 		
 		upd.executeUpdate("SHUTDOWN") ;
 		
 		upd.close() ; 
 		*/
 		
 		}
	    catch( Exception e ){
	        e.printStackTrace();
	    }
	    finally {
	       db.end() ;
	 		hdb.end();
	    }
     }
    
    public static void selectClienti() throws Exception  {
        
        AccessDB db = new AccessDB( "anagrafiche.mdb" , "user=sa;password=QuaBil;") ;
        //HsqlDB db = new HsqlDB() ;
        
        db.start(); 
	       new SQLCliente(db).getAll();
        db.end();


		
    }
    
    public static void select() throws Exception  {
        
        // AccessDB db = new AccessDB() ;
        HsqlDB db = new HsqlDB() ;
        
        db.start(); 
       // db.executeNonQuery( new SQLCarico(db).getCreateTable() ) ;
        // Object [] list = new SQLCarico(db).getAll();
        db.end();
		
		StringBuilder str  ;

		
    }
    
    public static void insertNAVI() throws Exception  {
       
        AccessDB db = new AccessDB("") ;
        HsqlDB hdb = new HsqlDB() ;
        
        Statement stmt = db.getConnection().createStatement();
        
        ResultSet rs = stmt.executeQuery("SELECT * from Navi");
		
		
		StringBuilder str  ;
		
		Statement upd = null ;
		upd = hdb.getConnection().createStatement() ;
		
		
		//upd.executeUpdate("CREATE TABLE NAVI(CODICE VARCHAR(255) NOT NULL PRIMARY KEY,DESCRIZIONE VARCHAR)");
		
		//upd.executeUpdate("CREATE TEXT TABLE NAVI(CODICE VARCHAR(255) NOT NULL PRIMARY KEY,DESCRIZIONE VARCHAR)");
		//upd.executeUpdate("SET TABLE NAVI SOURCE \"navi.csv;fs=|\"");
        
		while (false && rs.next()) {
		    
		    str = new StringBuilder() ;
		    
		    upd = hdb.getConnection().createStatement() ;
		    
		    str.append("INSERT INTO NAVI VALUES ('") ;
		    str.append( rs.getObject("Codice") + "' , '");
		    str.append( rs.getObject("Descrizione") + "' )");
		    
		    upd.executeUpdate( str.toString() );
	      }
		stmt.close();
		
		
		upd.executeUpdate("SHUTDOWN") ;
		
		upd.close() ; 
		
		db.end() ;
		hdb.end();
    }
}
