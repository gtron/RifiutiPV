package com.gtsoft.rifiuti.data;

public class Pericolosita implements java.io.Serializable {

  private String classe;
  private String nome;

  /**
   * Creates a new instance of Pericolosita
   */
  public Pericolosita() { 
  }

  /**
   * Gets the current value of classe
   * @return Current value of classe
   */
  public String getClasse() {
    return classe;
  }

  /**
   * Sets the value of classe
   * @param classe New value for classe
   */
  public void setClasse(String classe) {
    this.classe=classe;
  }

  /**
   * Gets the current value of nome
   * @return Current value of nome
   */
  public String getNome() {
    return nome;
  }

  /**
   * Sets the value of nome
   * @param nome New value for nome
   */
  public void setNome(String nome) {
    this.nome=nome;
  }

}