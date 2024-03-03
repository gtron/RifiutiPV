package com.gtsoft.rifiuti.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.gtsoft.rifiuti.data.sql.SQLCarico;
import com.gtsoft.rifiuti.data.sql.SQLCaricoParziale;
import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.rifiuti.front.data.AbstractSwg;
import com.gtsoft.rifiuti.front.data.SwgCarico;
import com.gtsoft.rifiuti.superdata.IListable;
import com.gtsoft.utils.FormattedDate;
import com.gtsoft.utils.superdata.IDatabase;
import com.gtsoft.utils.superdata.ISQLAdapter;

public class Carico implements IListable  {
    
    
	
	public final static String DATA="Data";
	public final static String RIFIUTO="Rifiuto";
	public final static String GIACENZA="Giacenza (Kg)";
	public final static String CODICEVETTOREGIACENZA="-";
	public final static String NUMERO="Numero";
	
	
	
	private Integer id ;
    private Cliente cliente= null;
    private String codiceCliente= null;
    private FormattedDate data= null;
    private FormattedDate dataPesatura= null;
    private FormattedDate dataScarico= null;
    private FormattedDate dataFormulario= null;
    private String descrizione= null;
    private Destinazione destinazione= null;
    private Fornitore fornitore= null;
    private String codiceFornitore = null;
    private Rifiuto merce= null;
    private String codiceMerce= null;
    private Nave nave= null;
    private String codiceNave= null;
    private double netto = 0 ;
    private String consegna= null;
    private String documento= null;
    private int numeroCataste = 0 ;
    private String numeroProgressivo = null ;
    private Peso pesoFattura= null;
    private Peso pesoPartenza= null;
    private Vettore vettore= null;
    private String codiceVettore  = null; 
    private SwgCarico swg = null;
   
    
    
    protected Movimento movimento ;
    public Movimento getMovimento() {
        if ( movimento == null ) {
            movimento = Movimento.getForCarico( this.id.intValue() ) ;
        }
        return movimento;
    }
    public void setMovimento(Movimento movimento) {
        this.movimento = movimento;
    }
    
    /**
     * Gets the current value of data
     * @return Current value of data
     */
    public FormattedDate getData() {
        return data;
    }
    
    public Date getDate() {
        return data;
    }
    
    
    /**
     * Sets the value of data
     * @param data New value for data
     */
    public void setData(FormattedDate data) {
        this.data=data;
    }
    
    /**
     * Gets the current value of dataPesatura
     * @return Current value of dataPesatura
     */
    public FormattedDate getDataPesatura() {
        return dataPesatura;
    }
    
    /**
     * Sets the value of dataPesatura
     * @param dataPesatura New value for dataPesatura
     */
    public void setDataPesatura(FormattedDate dataPesatura) {
        this.dataPesatura=dataPesatura;
    }
    
    public FormattedDate getDataFormulario() {
        return dataFormulario;
    }
    public void setDataFormulario(FormattedDate dataFormulario) {
        this.dataFormulario = dataFormulario;
    }
    public FormattedDate getDataScarico() {
        return dataScarico;
    }
    public void setDataScarico(FormattedDate dataScarico) {
        this.dataScarico = dataScarico;
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
     * Gets the current value of netto
     * @return Current value of netto
     */
    public double getNetto() {
        return netto;
    }
    
    /**
     * Sets the value of netto
     * @param netto New value for netto
     */
    public void setNetto(double netto) {
        this.netto=netto;
    }
    
    public void setNetto(String netto) {
        this.netto= new Double( netto ).doubleValue();
    }
    
    /**
     * Gets the current value of consegna
     * @return Current value of consegna
     */
    public String getConsegna() {
        return consegna;
    }
    
    /**
     * Sets the value of consegna
     * @param consegna New value for consegna
     */
    public void setConsegna(String consegna) {
        this.consegna=consegna;
    }
    
    /**
     * Gets the current value of documento
     * @return Current value of documento
     */
    public String getDocumento() {
        return documento;
    }
    
    /**
     * Sets the value of documento
     * @param documento New value for documento
     */
    public void setDocumento(String documento) {
        this.documento=documento;
    }
    
    /**
     * Gets the current value of numeroCatastale
     * @return Current value of numeroCatastale
     */
    public int getNumeroCataste() {
        return numeroCataste;
    }
    
    /**
     * Sets the value of numeroCatastale
     * @param numeroCatastale New value for numeroCatastale
     */
    public void setNumeroCataste(int numeroCataste) {
        this.numeroCataste=numeroCataste;
    }
    
    /**
     * Gets the current value of numeroProgressivo
     * @return Current value of numeroProgressivo
     */
    public String getNumeroProgressivo() {
        return numeroProgressivo;
    }
    
    /**
     * Sets the value of numeroProgressivo
     * @param numeroProgressivo New value for numeroProgressivo
     */
    public void setNumeroProgressivo(String numeroProgressivo) {
        this.numeroProgressivo=numeroProgressivo;
    }
    
    /**
     * Gets the current value of pesoFattura
     * @return Current value of pesoFattura
     */
    public Peso getPesoFattura() {
        if ( pesoFattura == null ) pesoFattura = new Peso(0.0);
        return pesoFattura;
    }
    
    /**
     * Sets the value of pesoFattura
     * @param pesoFattura New value for pesoFattura
     */
    public void setPesoFattura(Peso pesoFattura) {
        this.pesoFattura=pesoFattura;
    }
    public void setPesoFattura( String p ) {
        try {
            this.pesoFattura = new Peso( p );
        }
        catch (Exception e ) {
            this.pesoFattura = null ;
        }
    }
    
    /**
     * Gets the current value of pesoPartenza
     * @return Current value of pesoPartenza
     */
    public Peso getPesoPartenza() {
        if ( pesoPartenza == null ) pesoPartenza = new Peso(0.0);
        return pesoPartenza;
    }
    
    /**
     * Sets the value of pesoPartenza
     * @param pesoPartenza New value for pesoPartenza
     */
    public void setPesoPartenza(Peso pesoPartenza) {
        this.pesoPartenza=pesoPartenza;
    }
    public void setPesoPartenza( String p ) throws Exception {
        this.pesoPartenza = new Peso(p);
    }
    
    
    //{{ CLIENTE 
    public Cliente getCliente() {
        if ( cliente == null ){
            this.cliente = (Cliente) Cliente.getByCodice(codiceCliente);
        }    
        return cliente;
    }
    
    public String getCodiceCliente() {
        return codiceCliente ;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente=cliente;
    }  
    public void setCliente( String codiceCliente ) {
        if ( this.codiceCliente != codiceCliente ) {
            this.codiceCliente = codiceCliente ;
            this.cliente = null ;
        }
    }
    
    //}}
    
    //{{ DESTINAZIONE  
    public Destinazione getDestinazione() {
        if (destinazione == null) {
            this.destinazione = new Destinazione(0) ;
        }
        return destinazione;
    }
    
    /**
     * Sets the value of destinazione
     * @param destinazione New value for destinazione
     */
    public void setDestinazione(Destinazione destinazione) {
        this.destinazione=destinazione;
    }
    public void setDestinazione(String d) {
        this.destinazione = new Destinazione(d) ;
    }
    //}}
    
    
    //{{ FORNITORE
    public Fornitore getFornitore() {
        if (fornitore == null) {
            this.fornitore = (Fornitore) Fornitore.getByCodice(codiceFornitore) ;
        }
        return fornitore;
    }
    public String getCodiceFornitore() {
        return codiceFornitore;
    }
    public void setFornitore(Fornitore fornitore) {
        this.fornitore=fornitore;
    }
    public void setFornitore(String codFornitore) {
        if ( this.codiceFornitore != codFornitore ) {
            this.codiceFornitore = codFornitore;
            this.fornitore = null ;
        }
    }
    //}}
    
    //{{ RIFIUTO   
    public Rifiuto getMerce() throws Exception {
        if ( merce == null ){
            this.merce = (Rifiuto) Rifiuto.get( codiceMerce ) ;
        }
        return merce; 
    }
    public String getCodiceMerce() {
        return codiceMerce;
    }
    
    
    /**
     * Sets the value of merce
     * @param merce New value for merce
     */
    public void setMerce(Rifiuto merce) {
        this.merce=merce;
        this.codiceMerce = merce.getCodiceQuadrelli() ;
    }
    
    public void setMerce(String codiceMerce) {
        this.codiceMerce = codiceMerce ;
        this.merce = null ;
    }
    //}}
    
    
    //{{ NAVE   
    /**
     * Gets the current value of nave
     * @return Current value of nave
     */
    public Nave getNave() {
        if ( nave == null ){
            try { 
                nave= (Nave) Nave.get( getCodiceNave() ) ;
            }
            catch ( Exception e ) {
                e.printStackTrace();
            }
            
            if ( nave == null ) {
                nave = new Nave() ;
                nave.setDescrizione("N/A");
            }
            
        }
        return nave;
    }
    public String getCodiceNave() {
        return codiceNave;
    }
    /**
     * Sets the value of nave
     * @param nave New value for nave
     */
    public void setNave(Nave nave ) {
        this.nave=nave;
    }
    public void setNave(String codNave ) {
        this.codiceNave = codNave;
        this.nave = null ;
    }
    //}}
    
    
    //{{ VETTORE 
    /**
     * Gets the current value of vettore
     * @return Current value of vettore
     */
    public Vettore getVettore() {
        
        if ( vettore == null ) {
            vettore = (Vettore) Vettore.getByCodice(codiceVettore);
            //vettore = new Vettore() ;
        }
        return vettore;
    }
    public String getCodiceVettore() {
        return codiceVettore;
    }
    
    /**
     * Sets the value of vettore
     * @param vettore New value for vettore
     */
    public void setVettore(Vettore vettore) {
        this.vettore=vettore;
    }
    public void setVettore(String idVettore) {
        this.codiceVettore = idVettore ;
        this.vettore= null;
    }
    
    //}}
    
    private static final String[] columns = { 
            DATA,
            NUMERO,
            RIFIUTO,
            GIACENZA 
            };
    public String[] getColumns () { 
        return columns ;
    }

    public String[] getValues() throws Exception {
        String[] data = new String[getColumns().length] ;
        data[0] = getData().dmyString() ;
        data[2] = getMerce().getNome() ;
        data[1] = "" + getNumeroProgressivo();
        data[3] = "" + getNetto() ;
        
        return data ;
    }
    
    private static Vector vValues = null ;
    public Vector getVValues(){
        
        if ( vValues == null ) {
            vValues = new Vector(getColumns().length );
        }
        
        vValues.clear();
        if ( getData() != null )
            vValues.add( getData().dmyString() );
        else 
            vValues.add( "" );
        
        vValues.add( "" + getMovimento().getNumProgressivo() );
        try {
            if ( getMerce() != null ) {
                vValues.add( getMerce().getNome() );
            } else {
                vValues.add( "N/A" );
            }
        } catch (Exception e) {
            vValues.add( "N/A" );
        }
        
        vValues.add( "" + new Double( getNetto() ).intValue());
        
        return vValues ;
    }
    
    
    /**
     * @return Restituisce il valore di id.
     */
    public Integer getId() {
        return id;
    }
    /**
     * @param id Imposta il valore per id .
     */
    public void setId(int id) {
        this.id = new Integer(id);
    }
    
    public void setId(Integer id) {
        this.id = id ;
    }
    
    
    public static Vector getAll( IDatabase db ) throws Exception {
        return new SQLCarico(db).getAll() ;
    }
    public static Vector getAllFromAccess(IDatabase db) throws Exception {
        return new SQLCarico(db).getAllFromAccess() ;
    }
    
    /*
     public static Vector getFromAccessStartingFrom( Carico from , IDatabase db ) throws Exception {
        return new SQLCarico(db).getFromAccess( from ) ;
    }
    
    public static Vector getFromAccessStartingFromTo_deprecated( Carico from , FormattedDate to, IDatabase db ) throws Exception {
        //return new SQLCarico(db).getFromAccessStartingFromTo( from, to ) ;
        throw new Exception( "Don't use getFromAccessStartingFromTo() !") ;
    }
    */
    
    public static Vector getFromAccess( FormattedDate day , String fromProgressivo,String destinazioni, IDatabase db  ) throws Exception {
        return new SQLCarico(db).getFromAccess(day, fromProgressivo, destinazioni ) ;
    }
    
    /*
     * 
     public static Vector getScorieFromAccessStartingFrom( Carico from , IDatabase db ) throws Exception {
        return new SQLCarico(db).getScorieFromAccessStartingFrom( from ) ;
    }*/
    
    public static Vector getScorieFromAccess( FormattedDate giorno , IDatabase db ) throws Exception {
        return new SQLCarico(db).getScorieFromAccess( giorno ) ;
    }
    
    public static Vector getRifiutoFromAccess( String codQuadrelli, FormattedDate giorno , IDatabase db ) throws Exception {
        return new SQLCarico(db).getRifiutoFromAccess( codQuadrelli, giorno ) ;
    }
    
    
    public static Carico getLast( IDatabase db , String destinazioni ) throws Exception {
        Vector list = new SQLCarico(db).getLast(destinazioni) ;
        
        if ( list != null && list.size() > 0 ) {
            return (Carico) list.firstElement() ;
        }
        else 
            return null ;
    }
    
    public static Carico getLastScorie( IDatabase db ) throws Exception {
        
        return new SQLCarico(db).getLastScorie() ;
        
    }
    
    
//    public static Carico get( Carico o ) {
//        try {
//            // return (Carico) new SQLCarico().get( o ).get(0);
//        }
//        catch ( Exception e ) {
//            return null ;
//        }
//        return null ;
//    }
    
    private static HashMap cache = new HashMap(9973) ; // 14983 
    public static void clearCache() {
        cache.clear() ;
    }
    public static Carico get( int id ) {
        Integer k = new Integer(id) ;
        if ( ! cache.containsKey(k) ) {
	        try {
	           Carico c = new SQLCarico().getById( id ) ;
	           cache.put( k , c ) ;
	           return c ;
	        }
	        catch ( Exception e ) {
	            return null ;
	        }
        }
        
        return (Carico) cache.get(k) ;
    }
    
    public void scarica( FormattedDate data ) throws Exception {
        scarica( data , true ) ;
    }
    public void scarica( FormattedDate data , boolean doGiacenze ) throws Exception {
        
        if ( doGiacenze && this.getMerce() != null ) 
            this.getMerce().aggiornaGiacenza( this.netto );
        
        new SQLCarico().scarica( this, data ) ;
    }
    
    public void refreshImport( IDatabase from, IDatabase to ) throws Exception {
        Carico c = new SQLCarico(from).getByProgressivoAndAnno( this.getNumeroProgressivo() , this.getData() ) ;
        
        SQLCarico sql = new SQLCarico(to);
        Rifiuto r = c.getRifiuto();
        if ( r.getCodiceQuadrelliPadre() != null && r.getCodiceQuadrelliPadre().length() > 0 ) {
            c.setMerce(Rifiuto.get(r.getCodiceQuadrelliPadre()));
        }
        sql.syncFields(c) ;
        
        Movimento m = getMovimento() ;
        m.setMerceOriginale(r.getCodiceQuadrelli());
        
        m.update(to);
        
        sql.updateByProgressivo(this.getNumeroProgressivo() , c.getData()) ;
        sql.fillFromFields(this) ;
        
        Rifiuto.clearCache() ;
        to.checkpoint();
        
    }
    //  public static Carico getByOldNumProgressivo( int num ) {
    //      
    //      try {
    //         return new SQLCarico().getByOldNumProgressivo( num ) ; 
    //      }
    //      catch ( Exception e ) {
    //          return null ;
    //      }
    //  }
    
    
    private static Vector cacheSQL = new Vector(2);
    public static ISQLAdapter getSQL(IDatabase db) {
        ISQLAdapter o = null ;
        for ( Iterator i = cacheSQL.iterator() ; i.hasNext() ; ) {
            o = (ISQLAdapter) i.next() ;
            if ( o.checkDB(db) ) {
                cacheSQL.remove(o);
                break ;
            }
        }
        if ( o == null) { 
            o = new SQLCarico(db);
        }
        return o ;
    }
    public static void freeSQL( ISQLAdapter o ) {
        cacheSQL.add(o) ;
    }
    
    public void create( IDatabase db ) throws Exception {
        SQLCarico sql = new SQLCarico(db);
        sql.syncFields(this) ;
        sql.create() ;
    }
    
    public int createTellId( IDatabase db ) throws Exception {
        SQLCarico sql = new SQLCarico(db);
        sql.syncFields(this) ;
        return sql.createWithIdentity() ;
    }
    
    public AbstractSwg getSwg(RifiutiFrame frame) throws Exception{
        if(swg==null)
            swg=new SwgCarico(frame);
        swg.setCarico(this); // era setMovimento(this) ... ma credo sia giusto cosi' ... (G.)
        return swg;
    }
    
    
    public static double getGiacenzaRifiuto( Rifiuto o , String a) {
    	int anno = Integer.parseInt(a) ;
        double giacenza = new SQLCarico().getGiacenzaRifiuto( o.getCodiceQuadrelli(), anno ) ;
        giacenza -= new SQLCaricoParziale().getLavoratoRifiuto( o.getCodiceQuadrelli() , anno ) ;
        return giacenza ;
    }
    public static double getLavoratoRifiuto( Rifiuto o , String a) {
    	int anno = Integer.parseInt(a) ;
        double lavorato = new SQLCarico().getLavoratoRifiuto( o.getCodiceQuadrelli() , anno ) ;
        lavorato += new SQLCaricoParziale().getLavoratoRifiuto( o.getCodiceQuadrelli() , anno ) ;
        return  lavorato ;
    }
    
    public static Vector getCarichiDaScaricare( int anno ) {
        return new SQLCarico().getCarichiDaScaricare( null , anno ) ;
    }
    
    public static Vector getCarichiDaScaricare( String codRifiuto, int anno ) {
        return new SQLCarico().getCarichiDaScaricare( codRifiuto , anno ) ;
    }
    
    public static Carico getByProgressivo( String prog, FormattedDate data ) throws Exception {
        
        return new SQLCarico().getByProgressivoAndAnno( prog, data ) ;
    }
    
    public static Vector filterForRifiuto(String rifiuto) throws Exception {
        return new SQLCarico().filterRifiuto(rifiuto);
    }
    
    public static Vector filterForRifiuto(String[] rifiuto) throws Exception {
        Vector v=new SQLCarico().filterRifiuto(rifiuto);
        return v;
    }
    
    public Rifiuto getRifiuto() {
        if ( merce == null ) {
            try {
                merce = Rifiuto.get(codiceMerce) ;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            
        return merce;
    }
    
    public boolean equals(Object obj){
        if (obj instanceof Carico) 
            if(((Carico)obj).getId()==this.getId())
                return true;
        return false;
    }
    
    public boolean isScaricato() {
        return ( dataScarico != null ) ;
    }


    public boolean after(Carico c ) {
        
        if ( c.getData().after( getData() ) )
            return false ;
        
	    if ( Double.parseDouble(c.getNumeroProgressivo()) > Double.parseDouble(getNumeroProgressivo()) ) 
            return false ;
        
        return true ;
    }
    
}