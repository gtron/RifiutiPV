/*
 * Created on 4-lug-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.data.sql;

import java.sql.ResultSet;
import java.util.Vector;

import com.gtsoft.rifiuti.data.Vettore;
import com.gtsoft.utils.superdata.IDatabase;

/**
 * @author Gtron - 
 * 
 */
public class SQLVettore extends SQLSoggetto {

    private static final String TABLE = "Vettori" ; 
    
    public SQLVettore( IDatabase db ) {
        super(db) ;
        table = getTable() ;
    }
    
    public SQLVettore() {
        super() ;
        table = getTable() ;
    }
    
    public Object getFromFields() {
        return getFromFields( new Vettore() ) ;
    }
    
    public String getTable() {
        return TABLE ;
    }
    
    
    public Vector getUsedInMovimenti() throws Exception {
        StringBuilder sql = new StringBuilder();
	    sql.append("SELECT  v.id, v.codice , v.ragionesociale, v.indirizzo, v.localita, v.provincia, v.cap, v.telefono, v.codicefiscale, v.partitaiva, v.email, v.note ") ; // sql.append( getFieldsList() );
	    sql.append(" FROM ") ; sql.append( table ); 
	    
	    sql.append(" v INNER JOIN "); sql.append( SQLCarico.getTable_() );
	    sql.append(" m ON m.vettore = v.codice group by v.codice, v.id, v.ragionesociale, v.indirizzo, v.localita, v.provincia, v.cap, v.telefono, v.codicefiscale, v.partitaiva, v.email, v.note ") ;
	    	    
	    ResultSet rs = null ;
	    
	    rs = db.executeQuery(sql.toString()) ;
	    
	    Vector list = new Vector() ;
	    
	    if (rs != null )
	        while( rs.next() ) 
	            list.add( getFromRS(rs) );
	    
	    return list ;
    }
 
    
}
