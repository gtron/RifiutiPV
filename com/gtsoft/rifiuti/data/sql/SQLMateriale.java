/*
 * Created on 4-lug-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.data.sql;

import java.util.HashMap;

import com.gtsoft.rifiuti.data.Materiale;
import com.gtsoft.rifiuti.superdata.IAccessDBEnabled;
import com.gtsoft.utils.DbValue;
import com.gtsoft.utils.superdata.IDatabase;
import com.gtsoft.utils.superdata.ISQLAdapter;

/**
 * @author Gtron - 
 * 
 */
public class SQLMateriale extends ISQLAdapter implements IAccessDBEnabled {

    
    public void populateFields( Object o) {
        
    }
    
    private static final String TABLE = "Materiali" ; 
    private static final int fieldsCount = 6 ;
    
    public SQLMateriale( IDatabase db ) {
        super(db) ;
        table = TABLE ;
    }
    
    public Object getFromFields() {
        int i = 0 ;
        
        Materiale o = new Materiale();
        
        DbValue field =  (DbValue) fields.get("CODICE") ; 
        if ( field != null ){
            o.setCodice( ((String) field.getValue()) ) ;
        }
        field = (DbValue) fields.get("DESCRIZIONE") ; 
        if ( field != null ){
            o.setDescrizione( ((String) field.getValue()) ) ;
        }
        field = (DbValue) fields.get("DESCRIZIONE2") ; 
        if ( field != null ){
            o.setDescrizione2( ((String) field.getValue()) ) ;
        }
        field = (DbValue) fields.get("DESCRIZIONE3") ; 
        if ( field != null ){
            o.setDescrizione3( ((String) field.getValue()) ) ;
        }
        field = (DbValue) fields.get("VALOREDICONVERSIONE") ; 
        if ( field != null ){
            o.setValoreConversione( ((Float) field.getValue()).doubleValue() ) ;
        }
        return o ;
    }
    
    public void syncFields( Object obj ) throws Exception {
        Materiale o = (Materiale) obj ;
        
        HashMap list = getFields(true);
        ((DbValue) list.get("CODICE")).setValue( o.getCodice() ) ;
        ((DbValue) list.get("DESCRIZIONE")).setValue( o.getDescrizione() ) ;
        ((DbValue) list.get("DESCRIZIONE2")).setValue( o.getDescrizione2() ) ;
        ((DbValue) list.get("DESCRIZIONE3")).setValue( o.getDescrizione3() ) ;
        ((DbValue) list.get("NOTE")).setValue( o.getNote() ) ;
        ((DbValue) list.get("VALOREDICONVERSIONE")).setValue( o.getValoreConversione() ) ;
    }
    
    public HashMap getFields( int dbType, boolean reload) {

        if ( dbType == IDatabase.DBType.ACCESS ) {
            return getAccessDBFields() ;
        }
        else {
           return getFields( reload ) ;
        }
    }
        
        public HashMap getFields( boolean reload ) {

            if ( reload ||fields == null) {
            fields = new HashMap() ;
            
            DbValue key =  new DbValue("Codice", DbValue.INTEGER );
            key.setKey(true);
            fields.put("CODICE" , key ) ;
            
        	fields.put("DESCRIZIONE" , new DbValue("Descrizione", DbValue.STRING ) );
        	fields.put("DESCRIZIONE2" , new DbValue("DESCRIZIONE2", DbValue.STRING ) );
        	fields.put("DESCRIZIONE3" , new DbValue("DESCRIZIONE3", DbValue.STRING ) );
        	fields.put("NOTE" , new DbValue("NOTE", DbValue.STRING ) );
        	fields.put("VALOREDICONVERSIONE" , new DbValue("VALOREDICONVERSIONE", DbValue.FLOAT ) );
        }
        return fields ;
    }
    
    private HashMap accessDBFields = null ;
    public HashMap getAccessDBFields() {
        
        if (accessDBFields == null) {
            accessDBFields = new HashMap() ;
            
            DbValue key =  new DbValue("Codice", DbValue.INTEGER );
            key.setKey(true);
            accessDBFields.put("CODICE" , key ) ;
            
            accessDBFields.put("DESCRIZIONE" , new DbValue("DESCRIZIONE", DbValue.STRING ) );
            accessDBFields.put("DESCRIZIONE2" , new DbValue("DESCRIZIONE2", DbValue.STRING ) );
            accessDBFields.put("DESCRIZIONE3" , new DbValue("DESCRIZIONE3", DbValue.STRING ) );
            accessDBFields.put("NOTE" , new DbValue("NOTE", DbValue.STRING ) );
            accessDBFields.put("VALOREDICONVERSIONE" , new DbValue("VALOREDICONVERSIONE", DbValue.STRING ) );
        }
        return accessDBFields;
    }
    public String getAccessDBTable() {
        return TABLE;
    }
    public String getCreateTable() {
        return "";
    }
 
}
