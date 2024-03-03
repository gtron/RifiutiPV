/*
 * Creato il 30-giu-2005
 *
 * XXX Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package com.gtsoft.utils;

/** * @author pitcia *  * XXX Per modificare il modello associato al commento di questo tipo generato, aprire * Finestra - Preferenze - Java - Stile codice - Modelli codice */
public interface Prodotto {
    
    
	public String getNomeProdotto();
	public RifiutiDate getDataPesatura();
	public String getCodiceMateriale();
	public String getNumeroDocumento();
	public int getMovCarico();

    /**
     * 
     * @uml.property name="movScarico"
     */
    public int getMovScarico();

    /**
     * 
     * @uml.property name="movScarico"
     */
    public void setMovScarico(int movScarico);

	public int getRimanenza();
	public String getVettore();
	public String getCodiceCer();
	
	
}
