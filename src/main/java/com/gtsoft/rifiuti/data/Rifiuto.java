package com.gtsoft.rifiuti.data;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.gtsoft.rifiuti.data.sql.SQLRifiuto;
import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.rifiuti.front.data.AbstractSwg;
import com.gtsoft.rifiuti.front.data.FieldEditor;
import com.gtsoft.rifiuti.front.data.IEditable;
import com.gtsoft.rifiuti.front.data.SwgRifiuto;
import com.gtsoft.rifiuti.superdata.IListable;
import com.gtsoft.utils.HsqlDB;
import com.gtsoft.utils.superdata.IDatabase;

public class Rifiuto implements IListable, IEditable {

	/**
	 * valori di columns
	 */
	public static final String CER="Cer";
	public static final String QUADRELLI ="Quad.";
	public static final String NOME="Nome";
	public static final String GIACENZA="Giacenza (Kg)";
	public static final String TRATTATO="Trattato (Kg)";


	private int id;
	private String codiceQuadrelli;
	private String nome;
	private String unita; 
	private String codiceCer;
	private Cer cer ;
	private StatoFisico stato = null ;
	private String codiceStatoFisico;
	private Pericolosita[] classiPericolosita;
	private String listaClassiPericolosita;
	private Recupero recupero=new Recupero();
	private String codiceRecupero;
	private String descrizioneProduttore ;
	private String tipo;
	private String codiceQuadrelliPadre ;
	private String intermediario ;

	private SwgRifiuto swg ;

	private static HashMap cache = new HashMap(151) ;

	private static HashMap giacenze = new HashMap() ;
	private static HashMap lavorato = new HashMap() ;

	public Pericolosita[] getClassiPericolosita() {
		return classiPericolosita;
	}
	public String getCodiceQuadrelliPadre() {
		return codiceQuadrelliPadre;
	}
	public void setCodiceQuadrelliPadre(String codiceQuadrelliPadre) {
		this.codiceQuadrelliPadre = codiceQuadrelliPadre;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public boolean isScoria() {
		return( "S".equals(tipo) ) ;
	}

	public void setClassiPericolosita(Pericolosita[] classiPericolosita) {
		this.classiPericolosita = classiPericolosita;
	}
	public String getCodiceRecupero() {
		return codiceRecupero;
	}
	public void setCodiceRecupero(String codiceRecupero) {
		this.codiceRecupero = codiceRecupero;
	}
	public String getCodiceStatoFisico() {
		return codiceStatoFisico;
	}
	public String getDescrizioneProduttore() {
		if ( descrizioneProduttore == null )
			return "" ;

		return descrizioneProduttore;
	}
	public void setDescrizioneProduttore(String s ) {
		descrizioneProduttore = s ;
	}
	public void setCodiceStatoFisico(String codiceStatoFisico) {
		this.codiceStatoFisico = codiceStatoFisico;
	}
	public String getListaClassiPericolosita() {
		return "" + listaClassiPericolosita;
	}
	public String getCodiciClassiPericolosita() {
		StringBuilder sb = new StringBuilder();
		for ( int i = 0; i< listaClassiPericolosita.length() ; i++ ) {
			if ( i > 0)sb.append(",") ;
			sb.append(listaClassiPericolosita);
		}
		return sb.toString();
	}
	public void setListaClassiPericolosita(String listaClassiPericolosita) {
		this.listaClassiPericolosita = listaClassiPericolosita;
	}
	/**
	 * Creates a new instance of Rifiuto
	 */
	public Rifiuto() {
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

	public void setId(String id) throws Exception {
		setId( Integer.valueOf( id ) ) ;
	}
	/**
	 * Gets the current value of codiceQuadrelli
	 * @return Current value of codiceQuadrelli
	 */
	public String getCodiceQuadrelli() {
		return codiceQuadrelli;
	}

	/**
	 * Sets the value of codiceQuadrelli
	 * @param codiceQuadrelli New value for codiceQuadrelli
	 */
	public void setCodiceQuadrelli(String codiceQuadrelli) {
		this.codiceQuadrelli=codiceQuadrelli;
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

	/**
	 * Gets the current value of unita
	 * @return Current value of unita
	 */
	public String getUnita() {
		return unita;
	}

	/**
	 * Sets the value of unita
	 * @param unita New value for unita
	 */
	public void setUnita(String unita) {
		this.unita=unita;
	}

	public String getIntermediario() {
		return intermediario;
	}
	public void setIntermediario(String intermediario) {
		this.intermediario=intermediario;
	}

	/**
	 * Gets the current value of codiceCer
	 * @return Current value of codiceCer
	 */
	public String getCodiceCer() {
		return codiceCer;
	}
	public Cer getCer() {
		if ( cer == null ) {
			try {
				cer = Cer.get(getCodiceCer());
			}
			catch ( Exception e ) {
				cer = new Cer() ;
				e.printStackTrace();
			}
		}
		return cer ;
	}

	/**
	 * Sets the value of codiceCer
	 * @param codiceCer New value for codiceCer
	 */
	public void setCodiceCer(String codiceCer) {
		this.codiceCer=codiceCer;
	}

	/**
	 * Gets the current value of stato
	 * @return Current value of stato
	 */
	public StatoFisico getStato() {

		if ( stato == null )
			stato = StatoFisico.get(codiceStatoFisico) ;
		return stato;
	}

	/**
	 * Sets the value of stato
	 * @param stato New value for stato
	 */
	public void setStato(StatoFisico stato) {
		this.stato=stato;
	}

	/**
	 * Gets the current value of classePericolosita
	 * @return Current value of classePericolosita
	 */
	public Pericolosita[] getClassePericolosita() {
		return classiPericolosita;
	}

	/**
	 * Sets the value of classePericolosita
	 * @param classePericolosita New value for classePericolosita
	 */
	public void setClassePericolosita(Pericolosita[] classePericolosita) {
		this.classiPericolosita=classePericolosita;
	}

	/**
	 * Gets the current value of recupero
	 * @return Current value of recupero
	 */
	public Recupero getRecupero() {
		return recupero;
	}

	/**
	 * Sets the value of recupero
	 * @param recupero New value for recupero
	 */
	public void setRecupero(Recupero recupero) {
		this.recupero=recupero;
	}

	public String[] getColumns () {
		String[] list = {  
				CER,
				QUADRELLI,
				NOME, 
				GIACENZA, 
				TRATTATO };
		return list ;
	}

	public String[] getValues(){
		String[] data = new String[2] ;
		int i = 0 ;
		data[i++] = "" + getCer().getCodice() + " - " +  getCer().getNome();
		data[i++] = "" + getNome() ;
		// data[2] = "0" ; // "" + getNetto() ;

		return data ;
	}

	public Vector getVValues(){
		Vector data = new Vector(5) ;
		data.add( getCer().getCodice() ) ; // + " - " +  getCer().getNome() );
		data.add( getCodiceQuadrelli() );
		data.add( getNome()  );
		data.add( "" + getGiacenza()  );
		data.add( "" + getLavorato()  );
		// data[2] = "0" ; // "" + getNetto() ;

		return data ;
	}


	public static Rifiuto get( String codice ) throws Exception {
		if ( cache.containsKey(codice) ) {
			return (Rifiuto) cache.get(codice) ;
		}
		Rifiuto r = new SQLRifiuto().get( codice );
		cache.put(codice, r) ;
		return r ;
	}
	public static void clearCache() {
		cache.clear() ;
		giacenze.clear();
		lavorato.clear();
	}

	public static Vector getAll( IDatabase db ) {
		try {
			return new SQLRifiuto(db).getAll() ;
		} catch (Exception e) { 
			e.printStackTrace();
			return null ;
		}
	}
	public static String getCodiciQuadrelli()  {

		StringBuilder sb = new StringBuilder() ;
		try {
			Vector listRifiuti = new SQLRifiuto().getAll();
			Rifiuto r = null ;
			for ( Iterator i = listRifiuti.iterator() ; i.hasNext() ; ) {
				r = (Rifiuto) i.next() ;
				if ( r != null ) {

					sb.append("'") ;
					sb.append( r.getCodiceQuadrelli() ) ;
					sb.append("'") ;

					if ( i.hasNext() ) sb.append(",") ;
				}
			}
		}
		catch( Exception e ){
			e.printStackTrace();
		}

		return sb.toString() ;
	}

	public AbstractSwg getSwg(RifiutiFrame frame) throws Exception{
		if(swg==null)
			swg=new SwgRifiuto(frame);
		swg.setRifiuto(this);
		return swg;
	}

	public void clearGiacenza() {
		giacenze.remove(""+this.id) ;
		lavorato.remove(""+this.id) ;
	}
	public String getGiacenza() {
		DecimalFormat df = new DecimalFormat(); 
		return df.format(getGiacenzaDouble()) ;
	}

	public Double getGiacenzaDouble(){
		Double g = null ;
		if ( giacenze.containsKey( "" + this.id + "" + RifiutiFrame.getAnnoStatic() ) ) {
			g = ((Double)giacenze.get( "" + this.id + "" + RifiutiFrame.getAnnoStatic() )) ;
		}
		else {
			g = Double.valueOf( Carico.getGiacenzaRifiuto( this , RifiutiFrame.getAnnoStatic() ) );
			giacenze.put( "" + this.id + "" + RifiutiFrame.getAnnoStatic()  , g ) ;
		}
		return g;
	}
	public void aggiornaGiacenza( double q ) {
		Double l = (Double) giacenze.get("" + this.id ) ;

		if ( l != null ) {
			giacenze.put( "" + this.id , Double.valueOf( l.doubleValue() - q )  ) ;
			aggiornaLavorato(q);
		}

	}
	public String getLavorato() {
		DecimalFormat df = new DecimalFormat(); 

		return df.format(getLavoratoDouble()) ;
	}

	public Double getLavoratoDouble(){
		Double g = null ;

		if ( lavorato.containsKey( "" + this.id) ) {
			g = ((Double)lavorato.get( "" + this.id )) ;
		}
		else {
			g = Double.valueOf( Carico.getLavoratoRifiuto( this , RifiutiFrame.getAnnoStatic() ) );
			lavorato.put( "" + this.id , g ) ;
		}
		return g;
	}

	public void aggiornaLavorato( double q ) {
		Double l = (Double) lavorato.get("" + this.id ) ;

		lavorato.put( "" + this.id , Double.valueOf( l.doubleValue() + q ) ) ;

	}

	public String toString() {
		return getNome();
	}

	public boolean equals(Object obj){
		if (obj instanceof Rifiuto) {
			Rifiuto r = (Rifiuto) obj;
			if(this.getNome()==null ||r.getNome()==null)
				return false;
			if(r.getNome().equals(this.getNome()))
				return true;
		}
		return false;
	}


	// (TO DO) trasformabile in static implementando la sola modifica dei valori 
	private FieldEditor[] editors = null ;
	public FieldEditor[] getEditors() {


		editors = new FieldEditor[12] ; 

		int i = 0 ;
		editors[i++] = new FieldEditor("ID", "", "" + getId() , 3 ) ;
		editors[i-1].setVisible(false);

		Vector cerList = Cer.getAll(new HsqlDB()) ;
		String [] codiciCer = new String[cerList.size()] ;
		int itIdx = 0 ;
		for ( Iterator it = cerList.iterator() ; it.hasNext() ;)
			codiciCer[itIdx++] = ((Cer)it.next()).getCodice();

		editors[i++] = new FieldEditor("CODICECER", "Codice Cer", getCodiceCer(), codiciCer , codiciCer ) ;
		editors[i++] = new FieldEditor("TIPO", "Tipo (F = Fumi, S = Scorie)", getTipo(), 2 ) ;

		editors[i++] = new FieldEditor("CODICEQUADRELLI", "Codice Quadrelli", getCodiceQuadrelli() , 5) ;
		editors[i++] = new FieldEditor("CODICEQUADRELLIPADRE", "Codice Quadrelli di Riferimento", getCodiceQuadrelliPadre() , 5) ;

		editors[i++] = new FieldEditor("NOME", "Nome", getNome() , 26 ) ;
		editors[i++] = new FieldEditor("PRODUTTORE", "Produttore", getDescrizioneProduttore() , 26 ) ;
		editors[i++] = new FieldEditor("INTERMEDIARIO", "Intermediario", getIntermediario() , 26) ;

		Vector listStati = StatoFisico.getAll(new HsqlDB()) ;
		String [] codiciStato = new String[cerList.size()] ;
		String [] idStati = new String[cerList.size()] ;
		itIdx = 0 ;
		StatoFisico f = null ;
		for ( Iterator it = listStati.iterator() ; it.hasNext() ;) {
			f = (StatoFisico)it.next() ;
			codiciStato[itIdx] =  f.getDescrizione() ;
			idStati [itIdx++] = "" + f.getId();
		} 

		editors[i++] = new FieldEditor("STATOFISICO", "Stato Fisico", getStato().getId() + " - " + getStato().getDescrizione()  , codiciStato , idStati ) ;
		editors[i++] = new FieldEditor("CLASSIPERICOLOSITA", "Classi di Pericolosità", getListaClassiPericolosita() , 12) ;
		editors[i++] = new FieldEditor("CODICIRECUPERO", "Codici di Recupero", getCodiceRecupero() , 12 ) ;
		editors[i++] = new FieldEditor("UNITA", "Unita", getUnita() , 5) ;

		return editors ;
	}

}
