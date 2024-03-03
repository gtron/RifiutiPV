/*
 * Created on 4-lug-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.data.sql;

import java.util.HashMap;

import com.gtsoft.rifiuti.data.Soggetto;
import com.gtsoft.rifiuti.superdata.IAccessDBEnabled;
import com.gtsoft.utils.DbValue;
import com.gtsoft.utils.superdata.IDatabase;
import com.gtsoft.utils.superdata.ISQLAdapter;

/** 
 * @author Gtron - 
 * 
 */
public class SQLSoggetto extends ISQLAdapter implements IAccessDBEnabled {
    
    private static final int fieldsCount = 12 ;
    
    public SQLSoggetto() {
 	   super();
 	  table = getTable() ;
 	}
 	public SQLSoggetto( IDatabase d ) {
 	    super(d);
 	    table = getTable() ;
 	}
    
    public Object getFromFields( Soggetto o ) {
        
        DbValue field = (DbValue) fields.get("ID") ; 
        if ( field != null ){
            o.setId( ((Integer) field.getValue()).intValue() ) ;
        }
        field = (DbValue) fields.get("CODICE") ; 
        if ( field != null ){
            o.setCodice( ((String) field.getValue()) ) ;
        }
        field = (DbValue) fields.get("RAGIONESOCIALE") ; 
        if ( field != null ){
            o.setRagioneSociale( ((String) field.getValue()) ) ;
        }
        field = (DbValue) fields.get("CAP") ; 
        if ( field != null ){
            o.setCap( ((String) field.getValue()) ) ;
        }
        field = (DbValue) fields.get("CODICEFISCALE") ; 
        if ( field != null ){
            o.setCodiceFiscale( ((String) field.getValue()) ) ;
        }
        field = (DbValue) fields.get("EMAIL") ; 
        if ( field != null ){
            o.setEmail( ((String) field.getValue()) ) ;
        }
        field = (DbValue) fields.get("INDIRIZZO") ; 
        if ( field != null ){
            o.setIndirizzo( ((String) field.getValue()) ) ;
        }
        field = (DbValue) fields.get("LOCALITA") ; 
        if ( field != null ){
            o.setLocalita( ((String) field.getValue()) ) ;
        }
        field = (DbValue) fields.get("PROVINCIA") ; 
        if ( field != null ){
            o.setNote( ((String) field.getValue()) ) ;
        }
        field = (DbValue) fields.get("NOTE") ; 
        if ( field != null ){
            o.setPIva( ((String) field.getValue()) ) ;
        }
        field = (DbValue) fields.get("PIVA") ; 
        if ( field != null ){
            o.setProvincia( ((String) field.getValue()) ) ;
        }
        
        field = (DbValue) fields.get("TELEFONO") ; 
        if ( field != null ){
            o.setTelefono( ((String) field.getValue()) ) ;
        }
        return o ;
    }
    
    public Object getFromFields() {
        return getFromFields( new Soggetto() ) ;
    }
    

    /* (non-Javadoc)
     * @see com.gtsoft.utils.ISQLAdapter#getFields()
     */
    public HashMap getFields( int dbType, boolean reload ) {
        
        if ( dbType == IDatabase.DBType.ACCESS ) {
            return getAccessDBFields();
        }
        if (fields != null && ! reload ) 
            return fields ;
        else {
            fields = new HashMap(fieldsCount) ;
            
            DbValue id = new DbValue("ID", DbValue.INTEGER ) ;
            id.setKey(true);
        	fields.put("ID" , id );
        	
        	fields.put("CODICE" , new DbValue("CODICE" , DbValue.STRING ) );
        	fields.put("RAGIONESOCIALE" , new DbValue("RAGIONESOCIALE", DbValue.STRING ) );
        	fields.put("INDIRIZZO" , new DbValue("INDIRIZZO", DbValue.STRING ) );
        	fields.put("LOCALITA" , new DbValue("LOCALITA", DbValue.STRING ) );
        	fields.put("PROVINCIA" , new DbValue("PROVINCIA", DbValue.STRING ) );
        	fields.put("CAP" , new DbValue("CAP" , DbValue.STRING ) );
        	fields.put("TELEFONO" , new DbValue("TELEFONO", DbValue.STRING ) );
        	fields.put("CODICEFISCALE" , new DbValue("CODICEFISCALE", DbValue.STRING ) );
        	fields.put("PARTITAIVA" , new DbValue("PARTITAIVA", DbValue.STRING ) );
        	fields.put("EMAIL" , new DbValue("EMAIL", DbValue.STRING ) );
        	fields.put("NOTE" , new DbValue("NOTE", DbValue.STRING ) );
        	return fields;
        }
    }
    
    public void syncFields( Object obj ) throws Exception {
        Soggetto o = (Soggetto) obj ;
        
        HashMap list = getFields( db.getDbType() , false);
        ((DbValue) list.get("ID")).setValue( o.getId() ) ;
        ((DbValue) list.get("CODICE")).setValue( o.getCodice() ) ;
        ((DbValue) list.get("RAGIONESOCIALE")).setValue( o.getRagioneSociale() ) ;
        ((DbValue) list.get("INDIRIZZO")).setValue( o.getIndirizzo() ) ;
        ((DbValue) list.get("LOCALITA")).setValue( o.getLocalita() ) ;
        ((DbValue) list.get("PROVINCIA")).setValue( o.getProvincia() ) ;
        ((DbValue) list.get("CAP")).setValue( o.getCap() ) ;
        ((DbValue) list.get("TELEFONO")).setValue( o.getTelefono() ) ;
        ((DbValue) list.get("CODICEFISCALE")).setValue( o.getCodiceFiscale() ) ;
        ((DbValue) list.get("PARTITAIVA")).setValue( o.getPIva() ) ;
        ((DbValue) list.get("EMAIL")).setValue( o.getEmail() ) ;
        ((DbValue) list.get("NOTE")).setValue( o.getNote() ) ;
        
        
    }
    
    public String getAccessDBTable() {
        return "";
    }
    
    private HashMap accessDbFields = null ;
    public HashMap getAccessDBFields() {
        
        if (accessDbFields != null) 
            return accessDbFields ;
        else {
            accessDbFields = new HashMap(fieldsCount) ;
            
            DbValue id = new DbValue("CODICE", DbValue.STRING ) ;
            id.setKey(true);
            accessDbFields.put("CODICE" , id );

            accessDbFields.put("RAGIONESOCIALE" , new DbValue("Ragione Sociale", DbValue.STRING ) );
            accessDbFields.put("INDIRIZZO" , new DbValue("INDIRIZZO", DbValue.STRING ) );
            accessDbFields.put("LOCALITA" , new DbValue("Località", DbValue.STRING ) );
            accessDbFields.put("PROVINCIA" , new DbValue("PROVINCIA", DbValue.STRING ) );
            accessDbFields.put("CAP" , new DbValue("CAP" , DbValue.STRING ) );
            accessDbFields.put("TELEFONO" , new DbValue("TELEFONO", DbValue.STRING ) );
            accessDbFields.put("CODICEFISCALE" , new DbValue("Codice Fiscale", DbValue.STRING ) );
            accessDbFields.put("PARTITAIVA" , new DbValue("Partita Iva", DbValue.STRING ) );
            accessDbFields.put("EMAIL" , new DbValue("E-MAIL", DbValue.STRING ) );
            accessDbFields.put("NOTE" , new DbValue("NOTE", DbValue.STRING ) );
        	 
        	
        }
        return accessDbFields;
    }
    
    public String getCreateTable( ) {
        return "CREATE CACHED TABLE " + table + 
        	" ( ID INTEGER NOT NULL PRIMARY KEY," +
        	" CODICE VARCHAR, CAP VARCHAR, CODICEFISCALE VARCHAR, " +
        	" EMAIL VARCHAR, INDIRIZZO VARCHAR, LOCALITA VARCHAR, PROVINCIA VARCHAR " +
        	" NOTE VARCHAR, PIVA VARCHAR, RAGIONESOCIALE VARCHAR, TELEFONO VARCHAR  " +
        	" )" ;
    }
    
    
    public Soggetto getByCodice( String codice ) {
        try {
            return (Soggetto) super.get( new DbValue("CODICE" , codice , DbValue.STRING )).firstElement() ;
        } catch (Exception e) {
            return null ;
        }
    }
    
    public Soggetto getById( String id ) {
        try {
            return (Soggetto) super.get( new DbValue("id" , id , DbValue.STRING )).firstElement() ;
        } catch (Exception e) {
            return null ;
        }
    }
    
    
}
