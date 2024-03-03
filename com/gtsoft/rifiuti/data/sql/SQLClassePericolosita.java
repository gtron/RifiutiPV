/*
 * Created on 23-lug-2005
 *
 *	Implementa l'accesso al Database per il recupero del codice CER del rifiuto
 *	
 *	La tabella dovrebbe essere caricata all'avvio dal file .script perciò le modifiche
 *	potrebbero essere fatte al volo su quel file 
 *	
 *	STATUS : BETA 
 *	Gtron - 23/07/2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.data.sql;

import java.util.HashMap;

import com.gtsoft.rifiuti.data.ClassePericolosita;
import com.gtsoft.rifiuti.data.Carico;
import com.gtsoft.utils.DbValue;
import com.gtsoft.utils.superdata.IDatabase;
import com.gtsoft.utils.superdata.ISQLAdapter;

/**
 * @author Gtron - 
 * 
 */
public class SQLClassePericolosita extends ISQLAdapter {
    
    private static final int fieldsCount = 2 ;
    private static final String TABLE = "CLASSI_PERICOLOSITA" ; 
    
    public SQLClassePericolosita( IDatabase db ) {
        super(db) ;
       
        table = getTable() ;
    }
    
    public SQLClassePericolosita() {
        super() ;
        table = getTable() ;
    }

    
    public Object getFromFields() {
        int i = 0 ;
        
        ClassePericolosita o = new ClassePericolosita() ;
        
        DbValue field =  (DbValue) fields.get("CODICE") ; 
        if ( field != null && ! field.wasNull() ){
            o.setCodice( ((String) field.getValue()) ) ;
        }
        field = (DbValue) fields.get("DESCRIZIONE") ; 
        if ( field != null && ! field.wasNull() ){
            o.setDescrizione( ((String) field.getValue()) ) ;
        }
        return o ;
    }
    
    public HashMap getFields( int dbType , boolean reload) {

        return getFields( reload ) ;
     
    }
    
    public HashMap getFields( boolean reload ) {

        if ( reload || fields == null) {
            
            fields = new HashMap(fieldsCount);
            
            DbValue key =  new DbValue("CODICE", DbValue.INTEGER );
            key.setKey(true);
            fields.put("CODICE" , key ) ;
        	fields.put("DESCRIZIONE", new DbValue("descrizione", DbValue.STRING));
        	
        }
        
        return fields;
    }


    public void syncFields( Object obj ) throws Exception {
        Carico o = (Carico) obj ;
        
        HashMap list = getFields(true);
        ((DbValue) list.get("CODICE")).setValue( o.getId() ) ;
        ((DbValue) list.get("DESCRIZIONE")).setValue( o.getDescrizione() ) ;
    }
    
    public String getCreateTable() {
        return "CREATE MEMORY TABLE " + table + 
        	" (CODICE VARCHAR(10) NOT NULL PRIMARY KEY,DESCRIZIONE VARCHAR)" ;
    }
    
    public String getTable() {
            return TABLE ;
    }

}
