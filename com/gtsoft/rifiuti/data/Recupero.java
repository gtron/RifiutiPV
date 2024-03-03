package com.gtsoft.rifiuti.data;

public class Recupero  {

  private String codice;
  private String descrizione;
 
  /**
   * Creates a new instance of Recupero
   */
  public Recupero() {
  }

  /**
   * Gets the current value of codice
   * @return Current value of codice
   */
  public String getCodice() {
    return codice;
  }

  /**
   * Sets the value of codice
   * @param codice New value for codice
   */
  public void setCodice(String codice) {
    this.codice=codice;
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

}