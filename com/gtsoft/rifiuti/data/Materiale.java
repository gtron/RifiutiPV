package com.gtsoft.rifiuti.data;


public class Materiale { //implements IListable  {
 
  private String codice;
  private String descrizione;
  private String descrizione2;
  private String descrizione3;
  private String note;
  private double valoreConversione;

  /**
   * Creates a new instance of Materiale
   */
  public Materiale() {
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

  /**
   * Gets the current value of descrizione2
   * @return Current value of descrizione2
   */
  public String getDescrizione2() {
    return descrizione2;
  }

  /**
   * Sets the value of descrizione2
   * @param descrizione2 New value for descrizione2
   */
  public void setDescrizione2(String descrizione2) {
    this.descrizione2=descrizione2;
  }

  /**
   * Gets the current value of descrizione3
   * @return Current value of descrizione3
   */
  public String getDescrizione3() {
    return descrizione3;
  }

  /**
   * Sets the value of descrizione3
   * @param descrizione3 New value for descrizione3
   */
  public void setDescrizione3(String descrizione3) {
    this.descrizione3=descrizione3;
  }

  /**
   * Gets the current value of note
   * @return Current value of note
   */
  public String getNote() {
    return note;
  }

  /**
   * Sets the value of note
   * @param note New value for note
   */
  public void setNote(String note) {
    this.note=note;
  }

  /**
   * Gets the current value of valoreConversione
   * @return Current value of valoreConversione
   */
  public double getValoreConversione() {
    return valoreConversione;
  }

  /**
   * Sets the value of valoreConversione
   * @param valoreConversione New value for valoreConversione
   */
  public void setValoreConversione(double valoreConversione) {
    this.valoreConversione=valoreConversione;
  }

  /* (non Javadoc)
   * @see com.gtsoft.rifiuti.superdata.IListable#getColumns()
   */
  public String[] getColumns() {
      // XXX Stub di metodo generato automaticamente
      return null;
  }

  /* (non Javadoc)
   * @see com.gtsoft.rifiuti.superdata.IListable#getValues()
   */
  public String[] getValues() {
      // XXX Stub di metodo generato automaticamente
      return null;
  }

}