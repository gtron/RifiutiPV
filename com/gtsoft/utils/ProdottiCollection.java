/*
 * Creato il 1-lug-2005
 *
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti
 * 
 */
package com.gtsoft.utils;

import java.util.Collection;
import java.util.Date;

/**
 * @author pitcia
 *
 * XXX Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public interface ProdottiCollection extends Collection {
    
  public ProdottiCollection ordinaPerNome();
  public ProdottiCollection ordinaPerData();
  public ProdottiCollection splitData(Date dpar,Date darr);
  public ProdottiCollection getInData(Date d);

}
