/*
 * Created on 4-lug-2005
 *
 *	Implementa l'accesso al Database per il recupero del codice CER del rifiuto
 *	
 *	La tabella dovrebbe essere caricata all'avvio dal file .script perciò le modifiche
 *	potrebbero essere fatte al volo su quel file 
 *	
 *	STATUS : WORKING 
 *	Gtron - 23/07/2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.data.sql;

import java.util.HashMap;
import java.util.Vector;

import com.gtsoft.rifiuti.data.Cer;
import com.gtsoft.utils.DbValue;
import com.gtsoft.utils.superdata.IDatabase;
import com.gtsoft.utils.superdata.ISQLAdapter;

/**
 * @author Gtron - 
 * 
 */
public class SQLCer extends ISQLAdapter {

	private static final int fieldsCount = 2 ;
	private static final String TABLE = "CER" ; 

	public SQLCer( IDatabase db ) {
		super(db) ;
		table = TABLE ;
	}

	public SQLCer( ) {
		super() ;
		table = TABLE ;
	}

	public Object getFromFields() { 
		int i = 0 ;

		Cer o = new Cer() ;

		DbValue field =  (DbValue) fields.get("CODICE") ; 
		if ( field != null ){
			o.setCodice( field.getValue().toString() ) ;
		}
		field = (DbValue) fields.get("DESCRIZIONE") ; 
		if ( field != null ){
			o.setNome( ((String) field.getValue()) ) ;
		}

		return o ;
	}
	public void syncFields( Object obj ) throws Exception {
		Cer o = (Cer) obj ;

		HashMap list = getFields(true);
		((DbValue) list.get("CODICE")).setValue( o.getCodice() ) ;
		((DbValue) list.get("DESCRIZIONE")).setValue( o.getNome() ) ;
	}

	public HashMap getFields( int dbType , boolean reload) {

		return getFields( reload ) ;

	}

	public HashMap getFields( boolean reload ) {

		if ( ! reload && fields != null) 
			return fields ;

		fields = new HashMap() ;

		DbValue key =  new DbValue("Codice", DbValue.STRING );
		key.setKey(true);
		fields.put("CODICE" , key ) ;

		fields.put("DESCRIZIONE" , new DbValue("Descrizione", DbValue.STRING ) );

		return fields;

	}

	public String getCreateTable() {
		return "CREATE CACHED TABLE " + table + 
		" (CODICE VARCHAR(255) NOT NULL PRIMARY KEY,DESCRIZIONE VARCHAR)" ;
	}

	public Cer get( String codice ) throws Exception {

		HashMap fields = getFields(false);

		DbValue d = (DbValue) fields.get("CODICE") ;
		d.setValue(codice);

		Cer r = null ;
		try {
			Vector v = get( d ) ;
			if ( v.size() > 0 ) 
				r = (Cer) v.get(0) ;
		}
		catch( Exception e ) {
			e.printStackTrace() ;
		}

		return r ;
	}

}
