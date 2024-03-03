package com.gtsoft.rifiuti.data;

public class Peso implements java.io.Serializable {

  private double peso;
  private String unita;
  
  private final static String UNITA = "Kg" ;
  /**
   * Creates a new instance of Peso
   */
  public Peso( double peso , String unita ) {
      this.peso = peso ;
      this.unita = unita ;
  }
  
  public Peso ( double peso ) {
      this.peso = peso ;
      unita = UNITA ;
  }
  
  public Peso ( String peso ) throws Exception {
      this.peso = new Float(peso).floatValue() ;
      unita = UNITA ;
  } 

  /**
   * Gets the current value of peso
   * @return Current value of peso
   */
  public double getPeso() {
    return peso;
  }

  /**
   * Sets the value of peso
   * @param peso New value for peso
   */
  public void setPeso(double peso) {
    this.peso=peso;
  }

  /**
   * Gets the current value of unita
   * @return Current value of unita
   */
  public String getUnita() {
    if ( unita == "") unita = UNITA ;
    return unita;
  }

  /**
   * Sets the value of unita
   * @param unita New value for unita
   */
  public void setUnita(String unita) {
    this.unita=unita;
  }
  
  public String toString() {
      return "" + getPeso() + " " + getUnita() ;
  }
}