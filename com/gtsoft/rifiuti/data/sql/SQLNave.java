/*
 * Created on 4-lug-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.data.sql;

import java.util.HashMap;

import com.gtsoft.rifiuti.data.Nave;
import com.gtsoft.rifiuti.superdata.IAccessDBEnabled;
import com.gtsoft.utils.DbValue;
import com.gtsoft.utils.superdata.IDatabase;
import com.gtsoft.utils.superdata.ISQLAdapter;

/**
 * @author Gtron - 
 * 
 */
public class SQLNave extends ISQLAdapter implements IAccessDBEnabled {
    
    private static final int fieldsCount = 2 ;
    private static final String TABLE = "Navi" ; 
    
    public SQLNave( IDatabase db ) {
        
        super(db) ;
        table = TABLE ;
        
    }
    
    public SQLNave( ) {
        
        super() ;
        table = TABLE ;
        
    }
    
    public Object getFromFields() {
        int i = 0 ;
        
        Nave o = new Nave();
        
        DbValue field =  (DbValue) fields.get("ID") ; 
        if ( field != null ){
            o.setId( ((Integer) field.getValue()).intValue() ) ;
        }
        field = (DbValue) fields.get("CODICE") ; 
        if ( field != null ){
            o.setCodice( ((String) field.getValue()) ) ;
        }
        field = (DbValue) fields.get("DESCRIZIONE") ; 
        if ( field != null ){
            o.setDescrizione( ((String) field.getValue()) ) ;
        }
        return o ;
    }

    public HashMap getFields( int dbType , boolean reload) {
        if ( dbType == IDatabase.DBType.ACCESS ) {
            return getAccessDBFields() ;
        }
        else {
            return getFields(reload) ;
        }
    }
    
    public HashMap getFields( boolean reload ) {

        if ( reload ||  fields != null) 
            return fields ;
        else {
            
            fields = new HashMap() ;
            
//            DbValue key =  new DbValue("Id", DbValue.INTEGER );
//            key.setKey(true);
//            fields.put("ID" , key ) ;
            
            fields.put("CODICE" , new DbValue("Codice", DbValue.STRING ) );
        	fields.put("DESCRIZIONE" , new DbValue("Descrizione", DbValue.STRING ) );
        
        return fields;
        }
    }
    
    HashMap accessDBFields = null ;
    public HashMap getAccessDBFields() {
        
        if (accessDBFields == null) {
            
            DbValue key =  new DbValue("Codice", DbValue.STRING );
            key.setKey(true);
            accessDBFields.put("CODICE" , key ) ;
            
            accessDBFields.put("DESCRIZIONE" , new DbValue("Descrizione", DbValue.STRING ) );
            
        }
        
        return accessDBFields ;
      
        
    }
    
    public void syncFields( Object obj ) throws Exception {
        Nave o = (Nave) obj ;
        
        HashMap list = getFields(true);
        ((DbValue) list.get("ID")).setValue( o.getId() ) ;
        ((DbValue) list.get("CODICE")).setValue( o.getCodice() ) ;
        ((DbValue) list.get("DESCRIZIONE")).setValue( o.getDescrizione() ) ;
    }
    
    public String getCreateTable() {
        return "CREATE CACHED TABLE " + table + 
        	" (CODICE VARCHAR(255) NOT NULL PRIMARY KEY,DESCRIZIONE VARCHAR)" ;
    }
    
    public String getAccessDBTable() {
        return TABLE ;
    }

    public Nave get( String codice ) throws Exception {

        HashMap fields = getFields(true);
        
        DbValue d = (DbValue) fields.get("CODICE") ;
        d.setValue(codice);
        
        Nave r = null ;
        try {
            r = (Nave) get( d ).get(0) ;
        }
        catch( Exception e ) {
          //  e.printStackTrace() ;
        }
        
        return r ;
    }
}
