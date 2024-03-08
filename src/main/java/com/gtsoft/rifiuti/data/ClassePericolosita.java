/*
 * Created on 23-lug-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.data;

import com.gtsoft.rifiuti.data.sql.SQLCarico;
import com.gtsoft.utils.superdata.IDatabase;

/**
 * @author Gtron - 
 * 
 */
public class ClassePericolosita {

  private String codice ;
  private String descrizione;
  
  public String getCodice() {
      return codice;
  }
  public void setCodice(String codice) {
      this.codice = codice;
  }
  public String getDescrizione() {
      return descrizione;
  }
  public void setDescrizione(String descrizione) {
      this.descrizione = descrizione;
  }

  public static ClassePericolosita get( String cod ) {
      try {
         // return (Carico) new SQLCarico().get( o ).get(0);
      }
      catch ( Exception e ) {
          return null ;
      }
      return null ;
  }

  public void create( IDatabase db ) throws Exception {
      SQLCarico sql = new SQLCarico(db);
      sql.syncFields(this) ;
      sql.create() ;
  }



}