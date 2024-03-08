/*
 * Created on 23-ott-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.data.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.gtsoft.rifiuti.data.Carico;
import com.gtsoft.rifiuti.data.CaricoParziale;
import com.gtsoft.rifiuti.data.Movimento;
import com.gtsoft.rifiuti.data.Peso;
import com.gtsoft.utils.DbValue;
import com.gtsoft.utils.FormattedDate;
import com.gtsoft.utils.superdata.IDatabase;
import com.gtsoft.utils.superdata.ISQLAdapter;

/**
 * @author Gtron - 
 * 
 */
public class SQLCaricoParziale extends ISQLAdapter {
    
    private static final int fieldsCount = 18 ;
    private static final String TABLE = "CarichiParziali" ; 
    
    public SQLCaricoParziale( IDatabase db ) {
        super(db) ;
       
        table = getTable() ;
    }
    
    public SQLCaricoParziale() {
        super() ;
        table = getTable() ;
    }
    
    public static String getTable_() {
        return TABLE ;
    }
    
    public CaricoParziale getById( int id ) throws Exception {
        
        DbValue d = new DbValue("ID" , Integer.valueOf(id) , DbValue.INTEGER ) ;
        
        return (CaricoParziale) get( d ).firstElement() ;
        
    }
    
    public Object getFromFields() {
        CaricoParziale o = new CaricoParziale();
        fillFromFields(o) ;
        return o ;
    }
        
    public void fillFromFields(CaricoParziale o ) {
        int i = 0 ;
        
        DbValue field =  (DbValue) fields.get("CLIENTE") ; 
        if ( field != null && ! field.wasNull() ){
            o.setCliente( ((String) field.getValue()) ) ;
        }
        
        field = (DbValue) fields.get("ID") ; 
        if ( field != null && ! field.wasNull() ){
            o.setId( ((Integer) field.getValue()).intValue() ) ;
        }
        
        field = (DbValue) fields.get("CARICO") ; 
        if ( field != null && ! field.wasNull() && field.getValue() != null ){
            o.setIdCarico( ((Integer) field.getValue()).intValue() ) ;
        }
        
        field = (DbValue) fields.get("DATA") ; 
        if ( field != null && ! field.wasNull() ){
            o.setData( ((FormattedDate) field.getValue()) ) ;
        }
        field = (DbValue) fields.get("ORA") ; 
        if ( field != null && ! field.wasNull() ){
            FormattedDate ora ,res ;
            
            if ( o.getData() != null ) {
	            ora = (FormattedDate) field.getValue() ;
	            res = new FormattedDate( o.getData().ymdString() + " " + ora.hmsString() ) ;
            }
            else { res = (FormattedDate) field.getValue() ; }
            
            o.setData(  res ) ;
        }
        field = (DbValue) fields.get("DATAPESATURA") ; 
        if ( field != null && ! field.wasNull() ){
            o.setDataPesatura( ((FormattedDate) field.getValue()) ) ;
        }
        field = (DbValue) fields.get("DATAFORMULARIO") ; 
        if ( field != null ){
            if ( field.wasNull() )
                o.setDataFormulario( null ) ;
            else
                o.setDataFormulario( ((FormattedDate) field.getValue()) ) ;
        }
        field = null ;
        field = (DbValue) fields.get("DATASCARICO") ; 
        if ( field != null && ! field.wasNull() ){
            o.setDataScarico( ((FormattedDate) field.getValue()) ) ;
        }
        field = null ;
        field = (DbValue) fields.get("DESCRIZIONE") ; 
        if ( field != null && ! field.wasNull() ){
            o.setDescrizione( ((String) field.getValue()) ) ;
        }
        field = (DbValue) fields.get("DESTINAZIONE") ; 
        if ( field != null && ! field.wasNull() ){
            o.setDestinazione( "" + field.getValue() ) ;
        }
        field = (DbValue) fields.get("FORNITORE") ; 
        if ( field != null && ! field.wasNull() ){
            o.setFornitore( ((String) field.getValue()) ) ;
        }
        field = (DbValue) fields.get("MERCE") ; 
        if ( field != null && ! field.wasNull() ){
            o.setMerce( ((String) field.getValue()) ) ;
        }
        field = (DbValue) fields.get("NAVE") ; 
        if ( field != null && ! field.wasNull() ){
            o.setNave( "" + field.getValue() ) ;
        }
        field = (DbValue) fields.get("NETTO") ; 
        if ( field != null && ! field.wasNull() ){
            o.setNetto( "" + field.getValue() ) ;
        }
        field = (DbValue) fields.get("CONSEGNA") ; 
        if ( field != null && ! field.wasNull() ){
            o.setConsegna( ((String) field.getValue()) ) ;
        }
        field = (DbValue) fields.get("DOCUMENTO") ; 
        if ( field != null && ! field.wasNull() ){
            o.setDocumento( ((String) field.getValue()) ) ;
        }
        field = (DbValue) fields.get("NUMEROCATASTE") ; 
        if ( field != null && ! field.wasNull() ){
            o.setNumeroCataste( ((Integer) field.getValue()).intValue() ) ;
        }
        field = (DbValue) fields.get("NUMEROPROGRESSIVO") ; 
        if ( field != null && ! field.wasNull() ){
            o.setNumeroProgressivo( "" + field.getValue() ) ;
        }
        field = (DbValue) fields.get("PESOFATTURA") ; 
        if ( field != null && ! field.wasNull() ){
            o.setPesoFattura( "" + field.getValue() ) ;
        }
        field = (DbValue) fields.get("PESOPARTENZA") ; 
        if ( field != null && ! field.wasNull() ){
            try {
                o.setPesoPartenza( "" + field.getValue() ) ;
            }
            catch( Exception e ) {
                e.printStackTrace();
                o.setPesoPartenza( new Peso( 0.0 ) );
            }
        }
        field = (DbValue) fields.get("VETTORE") ; 
        if ( field != null && ! field.wasNull() ){
            o.setVettore( "" + field.getValue() ) ;
        }
    }
    
    public HashMap getFields( int dbType , boolean reload) {

        return getFields( reload ) ;
     
    }
    
    public HashMap getFields( boolean reload ) {

        if ( reload || fields == null) {
            
            fields = new HashMap(fieldsCount);
            
            DbValue key =  new DbValue("id", DbValue.INTEGER );
            key.setKey(true);
            fields.put("ID" , key ) ;
            fields.put("CARICO", new DbValue("Carico", DbValue.INTEGER ));
        	fields.put("CLIENTE", new DbValue("Cliente", DbValue.STRING ));
        	fields.put("DATA", new DbValue("data", DbValue.DATE ));
        	fields.put("DATAPESATURA", new DbValue("dataPesatura", DbValue.DATE ));
        	fields.put("DATASCARICO", new DbValue("DATASCARICO", DbValue.DATE ));
        	fields.put("DESCRIZIONE", new DbValue("descrizione", DbValue.STRING));
        	fields.put("DESTINAZIONE", new DbValue("destinazione", DbValue.INTEGER ));
        	fields.put("FORNITORE", new DbValue("fornitore", DbValue.OBJECT ));
        	fields.put("MERCE", new DbValue("merce", DbValue.OBJECT ));
        	fields.put("NAVE", new DbValue("NAVE", DbValue.STRING ));
        	fields.put("NETTO", new DbValue("netto", DbValue.FLOAT ));
        	fields.put("CONSEGNA", new DbValue("consegna", DbValue.STRING ));
        	fields.put("DOCUMENTO", new DbValue("documento", DbValue.STRING ));
        	fields.put("DATAFORMULARIO", new DbValue("dataformulario", DbValue.DATE ));
        	fields.put("NUMEROCATASTE", new DbValue("numeroCataste", DbValue.INTEGER ));
        	fields.put("NUMEROPROGRESSIVO", new DbValue("numeroProgressivo", DbValue.STRING ));
        	fields.put("PESOFATTURA", new DbValue("pesoFattura", DbValue.DOUBLE ));
        	fields.put("PESOPARTENZA", new DbValue("pesoPartenza", DbValue.DOUBLE ));
        	fields.put("VETTORE", new DbValue("vettore", DbValue.STRING ));
        }
        
        return fields;
    }
    

    public void syncFields( Object obj ) throws Exception {
        
        HashMap list = getFields( db.getDbType() , true);
        
        Carico o = (Carico) obj ;
        
        if ( CaricoParziale.class.isInstance(obj) ) {
            CaricoParziale cp = (CaricoParziale) obj ;
            ((DbValue) list.get("CARICO")).setValue( cp.getIdCarico() ) ;
        }
        
        
        ((DbValue) list.get("ID")).setValue( o.getId() ) ;
        ((DbValue) list.get("CLIENTE")).setValue( o.getCodiceCliente() ) ;
        ((DbValue) list.get("DATA")).setValue( o.getData() ) ;
        ((DbValue) list.get("DATAPESATURA")).setValue( o.getDataPesatura() ) ;
        ((DbValue) list.get("DATAFORMULARIO")).setValue( o.getDataFormulario() ) ;
        ((DbValue) list.get("DESCRIZIONE")).setValue( o.getDescrizione() ) ;
        ((DbValue) list.get("DESTINAZIONE")).setValue( o.getDestinazione().get() ) ;
        ((DbValue) list.get("FORNITORE")).setValue( o.getCodiceFornitore() ) ;
        ((DbValue) list.get("MERCE")).setValue( o.getCodiceMerce() ) ;
        ((DbValue) list.get("NAVE")).setValue( o.getCodiceNave() ) ;
        ((DbValue) list.get("NETTO")).setValue( o.getNetto() ) ;
        ((DbValue) list.get("CONSEGNA")).setValue( o.getConsegna() ) ;
        ((DbValue) list.get("DOCUMENTO")).setValue( o.getDocumento() ) ;
        ((DbValue) list.get("NUMEROCATASTE")).setValue( "" + o.getNumeroCataste() ) ;
        ((DbValue) list.get("NUMEROPROGRESSIVO")).setValue("" +  o.getNumeroProgressivo() ) ;
        ((DbValue) list.get("PESOFATTURA")).setValue( o.getPesoFattura().getPeso() ) ;
        ((DbValue) list.get("PESOPARTENZA")).setValue( o.getPesoPartenza().getPeso() ) ;
        ((DbValue) list.get("VETTORE")).setValue( o.getCodiceVettore() ) ;
        ((DbValue) list.get("DATASCARICO")).setValue( o.getDataScarico() ) ;

    }
    
   
    public String getTable() {
        return TABLE ;
    }

    public void pushGiacenze(Vector list ) throws Exception {
        push( list, CaricoParziale.DESCRIZIONI.GIACENZA ) ;
    }
    public void pushScorie(Vector list ) throws Exception {
        push( list, null ) ;
    }
    /**
     * Restituisce i CParz creati
     */
    public void push(Vector list , String descrizione ) throws Exception {
        
        // Vector carichiParziali = new Vector(list.size()) ;
        
                
        String sqlPrepare = " INSERT INTO " + getTable() +
        	" ( CARICO, CLIENTE, DATA, DATAPESATURA, DATAFORMULARIO, DESCRIZIONE," +
        	"DESTINAZIONE,FORNITORE,MERCE,NAVE,NETTO,CONSEGNA,DOCUMENTO,NUMEROCATASTE," +
        	"NUMEROPROGRESSIVO,PESOFATTURA,PESOPARTENZA,VETTORE ) VALUES " +
        	"( ?,?,?,?,?,?, ?,?,?,?,?,?,?,?,  ?,?,?,? )" ;
        
        PreparedStatement p = null ;
       // PreparedStatement k = db.getConnection().prepareStatement("select * from " + getTable() + " where id = IDENTITY(); ") ;
        
        Carico c = null ;

        for ( Iterator i = list.iterator() ; i.hasNext() ; ) {
            
            c = (Carico) i.next() ;
	        p = db.getConnection().prepareStatement(sqlPrepare);
	        
	        p.setInt(1, 0 );
	        p.setString(2, c.getCodiceCliente() ) ;
	        p.setString(3, c.getData().toString() ) ;
	        if ( c.getDataPesatura() != null )
	            p.setString(4, c.getDataPesatura().toString() ) ;
	        if ( c.getDataFormulario() != null )
	            p.setString(5, c.getDataFormulario().ymdString() ) ;
	        
	        if ( descrizione != null )
	            p.setString(6 , descrizione );
	        else 
	            p.setString(6 , "" + c.getDescrizione());
	        
	        p.setInt(7, c.getDestinazione().get() ) ;
	        p.setString(8, c.getCodiceFornitore());
	        p.setString(9, c.getCodiceMerce() ) ;
	        p.setString(10, c.getCodiceNave() );
	        p.setDouble(11, c.getNetto() );
	        p.setString(12, c.getConsegna());
	        p.setString(13, c.getDocumento() );
	        p.setInt(14, c.getNumeroCataste() );
	        p.setString(15, c.getNumeroProgressivo()) ;
	        if ( c.getPesoFattura() != null )
	            p.setDouble( 16, c.getPesoFattura().getPeso() );
	        if ( c.getPesoPartenza() != null )
	            p.setDouble(17, c.getPesoPartenza().getPeso());
	        p.setString(18, c.getCodiceVettore());
	        
	        p.execute();
	        
	        // list.add( getFromRS( k.executeQuery() ) ) ;
        }
        
        // return list ;
        
    }
    
    public Vector getSommeGiacenzePerGiorno(FormattedDate from ) throws Exception {
        return getSommePerGiorno( from, false ) ;
    }
    public Vector getSommeScoriePerGiorno(FormattedDate from ) throws Exception {
        return getSommePerGiorno( from, true ) ;
    }
    
    public Vector getSommePerGiorno( FormattedDate from , boolean isScoria ) throws Exception {
        
        Vector list = null ;
        String where = " WHERE " ;
        
        if ( isScoria ) {
            where += " destinazione = '4' ";
        }
        else {
            where += " descrizione = '" + CaricoParziale.DESCRIZIONI.GIACENZA + "'";
        }
        
        if ( from != null ) {
            where += " AND left(data,10 ) = '" + from.ymdString()+ "'" ;
        }
                
        
        String sql = " select 0 as id, 0 as carico, '' as codice , merce,  sum( cast(netto as double) ) as netto , " +
        		" left( data, 10 ) as data , left( data, 10 ) as datapesatura , null as datascarico , " +
        		" '' as dataformulario, '' as descrizione, destinazione, null as fornitore, " +
        		"'' as nave, '' as consegna, '' as documento, 0 as numerocataste, " +
        		" 0 as numeroprogressivo, 0 as pesofattura, 0 as pesopartenza, null as vettore, " +
        		" '' as cliente from " + getTable() + where + 
        		" group by merce, left( data, 10 ), destinazione order by data " ;
        
        ResultSet rs = db.executeQuery( sql );
        
        if (rs != null ) {
            list = new Vector();
	        while( rs.next() ) 
	            list.add( getFromRS(rs) );
        }
        
        return list ;
    }
    
    public double getLavoratoRifiuto( String codiceRifiuto , int anno  ) {
        
       Double d = null ;
       
       String sql = " SELECT SUM( CAST( NETTO AS DOUBLE )) FROM " + getTable() + 
       		" cp INNER JOIN " + new SQLCarico().getTable() + " c ON " +
       		" cp.carico = c.id WHERE c.datascarico is null AND " +
       		" cp.MERCE = '" + codiceRifiuto + "' AND cp.DATASCARICO between '"+anno+"-01-01' and '"+(anno+1)+"-01-01' " ;  
           
       try {
	       	d = Double.valueOf( "0" + executeScalarQuery(sql).firstElement());
	    } catch (Exception e) {
	        d = Double.valueOf(0) ;
	    }
	    
	    return d.longValue() ;
       
       
    }
    
    public Vector getSommePerMerce( int anno ) throws Exception {
        
        Vector list = null ;
        String whereGiorno = " where left(data,10 ) >= '" + anno + "-01-01'" ;
        
        String sql = " select 0 as id, 0 as carico, '' as codice , merce,  sum( cast(netto as double) ) as netto , " +
        		" left( data, 10 ) as data , '' as datapesatura , null as datascarico , " +
        		" '' as dataformulario, '' as descrizione, destinazione, null as fornitore, " +
        		"'' as nave, '' as consegna, '' as documento, 0 as numerocataste, " +
        		" 0 as numeroprogressivo, 0 as pesofattura, 0 as pesopartenza, null as vettore, " +
        		" '' as cliente from " + getTable() + whereGiorno +
        		" group by merce, destinazione order by data " ;
        
        ResultSet rs = db.executeQuery( sql );
        
        if (rs != null ) {
            list = new Vector();
	        while( rs.next() ) 
	            list.add( getFromRS(rs) );
        }
        
        return list ;
    }
    
    public Vector getByMerceGiorno( String merce , FormattedDate d , boolean isScoria ) throws Exception {
        
        Vector list = new Vector() ;
        String sqlGiorno = "" ;
        if ( d != null )
            sqlGiorno = " and left(data,10 ) = '" + d.ymdString() + "' ";
        
        String sqlScorie = " and descrizione <> '" + CaricoParziale.DESCRIZIONI.GIACENZA + "'";
        if ( ! isScoria ) 
            sqlScorie = " and descrizione = '" + CaricoParziale.DESCRIZIONI.GIACENZA + "'";
        
        String sql = " select * from " + getTable() + " where " +
        		" merce = '" + merce + "' " + sqlGiorno + sqlScorie +
        		" and datascarico is null order by data , numeroprogressivo " ;
        
        ResultSet rs = db.executeQuery( sql );
        
        if (rs != null )
	        while( rs.next() ) 
	            list.add( getFromRS(rs) );
        
        return list ;
    }
    
    public Vector getByCarico( Carico c ) throws Exception {
        
        Vector list = new Vector() ;
        
        String sql = " select * from " + getTable() + " where " +
        		" carico = " + c.getId() +
        		" and datascarico is null order by data , numeroprogressivo " ;
        
        ResultSet rs = db.executeQuery( sql );
        
        CaricoParziale tmpC = null ;
        if (rs != null )
	        while( rs.next() ) {
	            tmpC = (CaricoParziale) getFromRS(rs) ;
	            tmpC.setMovimento(c.getMovimento()) ;
	            tmpC.setCarico(c) ;
	            list.add( tmpC );
	        }
        
        return list ;
    }
    
    public CaricoParziale getLast( ) {
        
        Vector list = null ;
        
        try {
            list = getWithWhere( null , " data desc , numeroprogressivo desc " , "1") ;
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        
        if ( list != null && list.size() > 0 ) {
            return (CaricoParziale) list.firstElement() ;
        }
        else {
            return null ;
        }
        
    }

    /**
     * @param parziale
     * @throws Exception
     */
    public void update(CaricoParziale p) throws Exception {
     
        syncFields(p) ;
        update() ;
        
    }
    
    public void create(CaricoParziale p) throws Exception {
        
           syncFields(p) ;
           create();
           
       }
    
    public void createSetID(CaricoParziale p) throws Exception {
        
        syncFields(p) ;
        p.setId( createTellID().intValue() ) ;
        
    }
    public int scarica( int id , Movimento movScarico ) throws Exception {
        String sql = " update  " + getTable() + 
    	" set datascarico = '" + movScarico.getData().fullString() + "', " +
    			" scarico = " + movScarico.getNumProgressivo() + "  where id = " + id ; 
    
        return db.executeNonQuery(sql) ;
    }
    
    public double getPesoForScarico( Movimento m ) throws Exception {
        String sql = " select sum( cast(netto as double) ) as s from " + getTable() + 
        		" where scarico = " + m.getNumProgressivo() ; 
    
        ResultSet rs = db.executeQuery(sql) ;
        rs.next() ;
        
        return rs.getDouble(1) ;
        
    }
    
    
    public void updateByProgressivo(String numeroProgressivo, FormattedDate data) throws Exception {
        
        DbValue[] w = new DbValue[2] ;
        
        HashMap fields = getFields( db.getDbType(), false);
        
        w[0] = (DbValue) fields.get("NUMEROPROGRESSIVO");
        w[1] = (DbValue) fields.get("DATA");
        
        w[0].setValue(numeroProgressivo);
        w[1].setValue(data);
        
        Vector wV = new Vector(2) ; 
        wV.add( w[0] );
        wV.add( w[1] );
        
        Vector f = new Vector(fields.size()) ;
        DbValue curr = null ;
        for ( Iterator i = fields.keySet().iterator() ; i.hasNext();  ) {
            curr = (DbValue) fields.get( i.next() ) ;
            if ( ! curr.isKey() )
                f.add( curr );
        }
        
        update( f, wV);
        
    }
    
    public static void purgeTable(IDatabase db) throws Exception {
    	CaricoParziale.clearCache();
    	db.executeNonQuery("DELETE FROM " + getTable_()  ) ;
    	db.executeNonQuery("ALTER TABLE " + getTable_() + " ALTER COLUMN ID RESTART WITH 1;"  ) ;
    }
}