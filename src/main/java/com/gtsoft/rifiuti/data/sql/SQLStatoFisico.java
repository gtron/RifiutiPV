/*
 * Created on 4-lug-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.data.sql;

import java.util.HashMap;
import java.util.Vector;

import com.gtsoft.rifiuti.data.StatoFisico;
import com.gtsoft.utils.DbValue;
import com.gtsoft.utils.superdata.IDatabase;
import com.gtsoft.utils.superdata.ISQLAdapter;

/**
 * @author Gtron - 
 * 
 */
public class SQLStatoFisico extends ISQLAdapter {
    
    public void populateFields( Object o) {
        
    }
    private static final int fieldsCount = 2 ;
    private static final String TABLE = "STATI_FISICI" ; 
    
    public SQLStatoFisico() {
        super() ;
        table = TABLE ;
    }
    public SQLStatoFisico( IDatabase db ) {
        super(db) ;
        table = TABLE ;
    }
    
    public Object getFromFields() { 
        int i = 0 ;
        
        StatoFisico o = new StatoFisico() ;
        
        DbValue field =  (DbValue) fields.get("ID") ; 
        if ( field != null ){
            o.setId( ((Integer) field.getValue()).intValue() ) ;
        }
        field = (DbValue) fields.get("DESCRIZIONE") ; 
        if ( field != null ){
            o.setDescrizione( ((String) field.getValue()) ) ;
        }
        return o ;
    }
    public void syncFields( Object obj ) throws Exception {
        StatoFisico o = (StatoFisico) obj ;
        
        HashMap list = getFields(true);
        ((DbValue) list.get("ID")).setValue( o.getId() ) ;
        ((DbValue) list.get("DESCRIZIONE")).setValue( o.getDescrizione() ) ;
    }

    public HashMap getFields( int dbType , boolean reload) {

        return getFields( reload ) ;
     
    }
    
    public HashMap getFields( boolean reload ) {

        if ( ! reload && fields != null) 
            return fields ;
        else {
            
            fields = new HashMap() ;
            
            DbValue key =  new DbValue("id", DbValue.INTEGER );
            key.setKey(true);
            fields.put("ID" , key ) ;
            
        	fields.put("DESCRIZIONE" , new DbValue("Descrizione", DbValue.STRING ) );
        
        return fields;
        }
    }
    
    public String getCreateTable() {
        return "" ;
    }

    /**
     * @param codice
     * @return
     */
    public StatoFisico getByCodice(String codice) {
        StatoFisico r = null ;
        try {
	        HashMap fields = getFields(true);
	        
	        DbValue d = (DbValue) fields.get("ID") ;
	        
	        d.setValue(codice);
	        Vector v = get( d );
	        if ( v.size() > 0 )
	            r = (StatoFisico) v.get(0) ;
        
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        
        return r ;
    
    }
}
