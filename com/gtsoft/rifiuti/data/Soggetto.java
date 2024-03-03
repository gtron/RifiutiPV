package com.gtsoft.rifiuti.data;

import java.util.Vector;

import com.gtsoft.rifiuti.data.sql.SQLSoggetto;
import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.rifiuti.front.data.AbstractSwg;
import com.gtsoft.rifiuti.front.data.SwgSoggetto;
import com.gtsoft.rifiuti.superdata.IListable;
import com.gtsoft.utils.superdata.IDatabase;


public class Soggetto implements IListable , Comparable {
    
    private int id ;
    private String codice;
    private String cap;
    private String codiceFiscale;
    private String email;
    private String indirizzo;
    private String localita;
    private String note;
    private String pIva;
    private String provincia;
    private String ragioneSociale;
    private String telefono;
    protected SwgSoggetto swg ;
    
    
    private static final String[] columnsToList = {"Codice Fiscale" , "Ragione Sociale"}; 
    
    /**
     * Creates a new instance of Cliente
     */
    public Soggetto() {
        this.ragioneSociale = this.getClass().getName() ;
    }
    
    public String[] getColumns() {
        return columnsToList ;
    }
    
    public String[] getValues() {
        String[] v = new String[2];
        int i = 0 ;
        columnsToList[i++] = getCodiceFiscale() ;
        columnsToList[i++] = getRagioneSociale() ;
        return v ;
    }
    
    public Vector getVValues(){
        Vector data = new Vector(2) ;
        
        data.add( getCodiceFiscale() );
        data.add( getRagioneSociale() );
        
        return data;
    }
    
    
    /**
     * Gets the current value of cap
     * @return Current value of cap
     */
    public String getCap() {
        return cap;
    }
    
    /**
     * Sets the value of cap
     * @param cap New value for cap
     */
    public void setCap(String cap) {
        this.cap=cap;
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
     * Gets the current value of codiceFiscale
     * @return Current value of codiceFiscale
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }
    
    /**
     * Sets the value of codiceFiscale
     * @param codiceFiscale New value for codiceFiscale
     */
    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale=codiceFiscale;
    }
    
    /**
     * Gets the current value of email
     * @return Current value of email
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Sets the value of email
     * @param email New value for email
     */
    public void setEmail(String email) {
        this.email=email;
    }
    
    /**
     * Gets the current value of indirizzo
     * @return Current value of indirizzo
     */
    public String getIndirizzo() {
        return indirizzo;
    }
    
    /**
     * Sets the value of indirizzo
     * @param indirizzo New value for indirizzo
     */
    public void setIndirizzo(String indirizzo) {
        this.indirizzo=indirizzo;
    }
    
    /**
     * Gets the current value of localita
     * @return Current value of localita
     */
    public String getLocalita() {
        return localita;
    }
    
    /**
     * Sets the value of localita
     * @param localita New value for localita
     */
    public void setLocalita(String localita) {
        this.localita=localita;
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
     * Gets the current value of pIva
     * @return Current value of pIva
     */
    public String getPIva() {
        return pIva;
    }
    
    /**
     * Sets the value of pIva
     * @param pIva New value for pIva
     */
    public void setPIva(String pIva) {
        this.pIva=pIva;
    }
    
    /**
     * Gets the current value of provincia
     * @return Current value of provincia
     */
    public String getProvincia() {
        return provincia;
    }
    
    /**
     * Sets the value of provincia
     * @param provincia New value for provincia
     */
    public void setProvincia(String provincia) {
        this.provincia=provincia;
    }
    
    /**
     * Gets the current value of ragioneSociale
     * @return Current value of ragioneSociale
     */
    public String getRagioneSociale() {
        return ragioneSociale;
    }
    
    /**
     * Sets the value of ragioneSociale
     * @param ragioneSociale New value for ragioneSociale
     */
    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale=ragioneSociale;
    }
    
    /**
     * Gets the current value of telefono
     * @return Current value of telefono
     */
    public String getTelefono() {
        return telefono;
    }
    
    /**
     * Sets the value of telefono
     * @param telefono New value for telefono
     */
    public void setTelefono(String telefono) {
        this.telefono=telefono;
    }
    
    /**
     * @return Restituisce il valore di id.
     */
    public int getId() {
        return id;
    }
    /**
     * @param id Imposta il valore per id .
     */
    public void setId(int id) {
        this.id = id;
    }
    
    public String toString() {
        return getRagioneSociale() + " (" + getCodice() + ")";
    }
    
    public AbstractSwg getSwg(RifiutiFrame frame) throws Exception{
        if(swg==null)
            swg=new SwgSoggetto(frame);
        swg.setSoggetto(this);
        return swg;
    }
    
    
    public static Vector getAll( IDatabase db ) {
        try {
            return new SQLSoggetto(db).getAll() ;
        } catch (Exception e) { 
            e.printStackTrace();
            return null ;
        }
    }
    
    public boolean equals(Object obj){
        if(obj==null)
            return false;
        
        if (obj instanceof Soggetto){
            
            if(this.getRagioneSociale()==null || ((Soggetto) obj).getRagioneSociale()==null)
                return false;
            
            if(this.getRagioneSociale().equals(((Soggetto) obj).getRagioneSociale()))
                return true;
        }
        
        return false;		
    }
    
    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object v) {
        return codice.compareTo( ((Vettore) v).getCodice() ) ;
    }
}