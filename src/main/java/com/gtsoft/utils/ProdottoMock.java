/*
 * Creato il 29-giu-2005
 *
 * XXX Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package com.gtsoft.utils;



/**
 * @author pitcia
 *
 * XXX Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class ProdottoMock implements Prodotto {
    
    private String nomeProdotto="ferro";
    private RifiutiDate dataPesatura=new RifiutiDate(70,12,30);
    private String codiceQuadrelli="323344";
    private String numeroDocumento="fytfyfyt";
    private int movCarico=12599;
    private int movScarico=0;
    private int rimanenza=movCarico-movScarico;
    private String vettore="nave";
    private String codiceCer="34567734";
    
//    PRIVATE STRING [] CLASSIDIPERICOLOSITA;
//    PRIVATE INT STATOFISICO;
//    PRIVATE STRING FORNITORE;
    
    
    
    

    public ProdottoMock(String nome, String data) {
        
        this.nomeProdotto=nome;
        this.dataPesatura=new RifiutiDate(data);
        
        
    }
    /**
     * 
     */
    public ProdottoMock() {
        
        // XXX Stub di costruttore generato automaticamente
    }

    /**
     * @return Restituisce il valore codiceCer.
     * 
     * @uml.property name="codiceCer"
     */
    public String getCodiceCer() {
        return codiceCer;
    }

    /**
     * @param codiceCer Il valore codiceCer da impostare.
     * 
     * @uml.property name="codiceCer"
     */
    public void setCodiceCer(String codiceCer) {
        this.codiceCer = codiceCer;
    }

    /**
     * @return Restituisce il valore codiceMateriale.
     * 
     * @uml.property name="codiceMateriale"
     */
    public String getCodiceMateriale() {
        return codiceQuadrelli;
    }

    /**
     * @param codiceMateriale Il valore codiceMateriale da impostare.
     * 
     * @uml.property name="codiceMateriale"
     */
    public void setCodiceMateriale(String codiceMateriale) {
        this.codiceQuadrelli = codiceMateriale;
    }

    /**
     * @return Restituisce il valore dataPesatura.
     * 
     * @uml.property name="dataPesatura"
     */
    public RifiutiDate getDataPesatura() {
        return this.dataPesatura;
    }

    
    public void setDataPesatura(String dataPesatura) {
        this.dataPesatura.setDate(dataPesatura);
    }

    /**
     * @return Restituisce il valore movCarico.
     * 
     * @uml.property name="movCarico"
     */
    public int getMovCarico() {
        return movCarico;
    }

    /**
     * @param movCarico Il valore movCarico da impostare.
     * 
     * @uml.property name="movCarico"
     */
    public void setMovCarico(int movCarico) {
        this.movCarico = movCarico;
    }

    /**
     * @return Restituisce il valore movScarico.
     * 
     * @uml.property name="movScarico"
     */
    public int getMovScarico() {
        return movScarico;
    }

    /**
     * @param movScarico Il valore movScarico da impostare.
     * 
     * @uml.property name="movScarico"
     */
    public void setMovScarico(int movScarico) {
        this.movScarico = movScarico;
        this.rimanenza = movCarico - movScarico;
    }

    /**
     * @return Restituisce il valore numeroDocumento.
     * 
     * @uml.property name="numeroDocumento"
     */
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * @param numeroDocumento Il valore numeroDocumento da impostare.
     * 
     * @uml.property name="numeroDocumento"
     */
    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    /**
     * @return Restituisce il valore rimanenza.
     * 
     * @uml.property name="rimanenza"
     */
    public int getRimanenza() {
        return rimanenza;
    }

    /**
     * @param rimanenza Il valore rimanenza da impostare.
     * 
     * @uml.property name="rimanenza"
     */
    public void setRimanenza(int rimanenza) {
        this.rimanenza = rimanenza;
    }

    /**
     * @return Restituisce il valore vettore.
     * 
     * @uml.property name="vettore"
     */
    public String getVettore() {
        return vettore;
    }

    /**
     * @param vettore Il valore vettore da impostare.
     * 
     * @uml.property name="vettore"
     */
    public void setVettore(String vettore) {
        this.vettore = vettore;
    }

   
    public String getNomeProdotto() {
        return this.nomeProdotto;
    }

    public String toString(){
        return dataPesatura.getString() +"                   "+nomeProdotto;
    }
    /* (non Javadoc)
     * @see com.gtsoft.rifiuti.front.Prodotto#getLabelTitle()
     */

}
	
	