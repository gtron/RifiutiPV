package com.gtsoft.rifiuti.data;

import java.util.HashMap;
import java.util.Vector;

import com.gtsoft.rifiuti.data.sql.SQLStatoFisico;
import com.gtsoft.utils.superdata.IDatabase;

public class StatoFisico {

  private int id;
  private String descrizione; 

  /**
   * Creates a new instance of StatoFisico
   */
  public StatoFisico() {
  }

  /**
   * Gets the current value of id
   * @return Current value of id
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the value of id
   * @param id New value for id
   */
  public void setId(int id) {
    this.id=id;
  }

  /**
   * Gets the current value of descrizione
   * @return Current value of descrizione
   */
  public String getDescrizione() {
    return descrizione;
  }

  /**
   * Sets the value of descrizione
   * @param descrizione New value for descrizione
   */
  public void setDescrizione(String descrizione) {
    this.descrizione=descrizione;
  }

/**
 * @param codiceStatoFisico
 * @return
 */

  private static HashMap cache = new HashMap() ;
  public static StatoFisico get(String codice) {
      if ( ! cache.containsKey(codice) ) {
          Object o = new SQLStatoFisico().getByCodice(codice);
          cache.put(codice, o) ;
          return (StatoFisico) o ;
      }
      return (StatoFisico) cache.get(codice) ; 
  }
  
  public static Vector getAll(IDatabase db){
      try {
     return new SQLStatoFisico(db).getAll();
      }catch ( Exception e) {
          return new Vector(1) ;
      }
  }
}