package com.gtsoft.rifiuti.data;

import com.gtsoft.rifiuti.data.sql.SQLNave;
import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.rifiuti.front.data.AbstractSwg;


public class Nave {

  private int id;
  private String descrizione;
  private String codice ;
  
  /**
   * Creates a new instance of Nave
   */
  public Nave() { 
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
  
  public String toString() {
      return "NAVE : "  + getDescrizione() + "(" +getId() + ")";
  }
  
  public String[] getColumns () {
      String[] list = { "Codice", "Nome" };
      return list ;
  }
  
  public String[] getValues(){
      String[] data = new String[2] ;
      data[0] = "" + getCodice();
      data[1] = getDescrizione() ;
      
      return data ;
  }
  
public String getCodice() {
    return codice;
}
public void setCodice(String codice) {
    this.codice = codice;
}

public static Nave get( String codice ) throws Exception {
    return new SQLNave().get( codice ) ;
}

public AbstractSwg getSwg(RifiutiFrame frame) throws Exception{
   return null;
}

}