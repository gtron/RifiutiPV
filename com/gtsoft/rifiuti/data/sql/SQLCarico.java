/*
 * Created on 8-lug-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.data.sql;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.gtsoft.rifiuti.data.Carico;
import com.gtsoft.rifiuti.data.Peso;
import com.gtsoft.rifiuti.data.Rifiuto;
import com.gtsoft.rifiuti.superdata.IAccessDBEnabled;
import com.gtsoft.utils.DbValue;
import com.gtsoft.utils.FormattedDate;
import com.gtsoft.utils.superdata.IDatabase;
import com.gtsoft.utils.superdata.ISQLAdapter;

/**
 * @author Gtron - 
 * 
 */
public class SQLCarico extends ISQLAdapter implements IAccessDBEnabled {
    
    private static final int fieldsCount = 17 ;
    private static final String TABLE = "Carichi" ; 
    
    public SQLCarico( IDatabase db ) {
        super(db) ;
       
        table = getTable() ;
    }
    
    public SQLCarico() {
        super() ;
        table = getTable() ;
    }
    
    public static String getTable_() {
        return TABLE ;
    }
    
    public int createWithIdentity() {
        
        try {
            super.create();
            Vector r = executeScalarQuery("CALL IDENTITY()") ;
            return ((Integer) r.firstElement()).intValue() ;
        }
        catch ( Exception e ) {
            return -1 ;
        }
          
    }
    
    public Carico getById( int id ) throws Exception {
        
        DbValue d = new DbValue("ID" , new Integer(id) , DbValue.INTEGER ) ;
        
        return (Carico) get( d ).firstElement() ;
        
    }

    public Object getFromFields() {
        Carico o = new Carico();
         fillFromFields(o) ;
         return o ;
    }
        
    public void fillFromFields(Carico o ) {
        int i = 0 ;
        
        DbValue field =  (DbValue) fields.get("CLIENTE") ; 
        if ( field != null && ! field.wasNull() ){
            o.setCliente( ((String) field.getValue()) ) ;
        }
        
        field = (DbValue) fields.get("ID") ; 
        if ( field != null && ! field.wasNull() ){
            o.setId( ((Integer) field.getValue()).intValue() ) ;
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
	            if ( ora != null)
	            	res = new FormattedDate( o.getData().ymdString() + " " + ora.hmsString() ) ;
	            else
	            	res = o.getData() ;
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
        
        // prendo il formulario dal campo note per le scorie
        field = (DbValue) fields.get("NOTE") ; 
        if ( field != null && ! field.wasNull() && o.getDestinazione().get() == 4 ){
            o.setDocumento( "" + field.getValue() ) ;
            o.setDataFormulario(o.getDataPesatura()) ;
        }
        
    }
    
    public HashMap getFields( int dbType , boolean reload ) {

        if ( dbType == IDatabase.DBType.ACCESS ) {
            return getAccessDBFields() ;
        }
        else {
            return getFields(reload) ;
        }
    }
    
    public HashMap getFields( boolean reload) {

        if ( reload || fields == null) {
            
            fields = new HashMap(fieldsCount);
            
            DbValue key =  new DbValue("id", DbValue.INTEGER );
            key.setKey(true);
            fields.put("ID" , key ) ;
        	
        	fields.put("CLIENTE", new DbValue("Cliente", DbValue.OBJECT ));
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
    
    public HashMap getAccessDBFields() {
       
        if (fields == null) {
            
            fields = new HashMap(fieldsCount);
            
            DbValue key =  new DbValue("data", DbValue.DATE );
            key.setKey(true);
            fields.put("DATA" , key ) ;
            
            DbValue num =  new DbValue("numero Progressivo", DbValue.DOUBLE );
            key.setKey(true);
            fields.put("NUMEROPROGRESSIVO" , num ) ;
            
            fields.put("DATAPESATURA", new DbValue("dataPesatura", DbValue.DATE ));
            fields.put("ORA", new DbValue("ora", DbValue.DATE ));
        	fields.put("DATAFORMULARIO", new DbValue("Data_Em_Form", DbValue.DATE ));
        	fields.put("NETTO", new DbValue("netto", DbValue.DOUBLE ));
        	fields.put("CLIENTE", new DbValue("Cliente", DbValue.OBJECT ));
        	fields.put("FORNITORE", new DbValue("fornitore", DbValue.OBJECT ));
        	fields.put("VETTORE", new DbValue("vettore", DbValue.STRING ));
        	fields.put("DESTINAZIONE", new DbValue("destinazione", DbValue.INTEGER ));
        	fields.put("DESCRIZIONE", new DbValue("descrizione", DbValue.STRING));
        	fields.put("MERCE", new DbValue("merce", DbValue.OBJECT ));
        	fields.put("NAVE", new DbValue("nave", DbValue.STRING ));
        	fields.put("CONSEGNA", new DbValue("num consegna", DbValue.STRING ));
        	fields.put("DOCUMENTO", new DbValue("num documento", DbValue.STRING ));
        	fields.put("PESOFATTURA", new DbValue("peso Fattura", DbValue.DOUBLE ));
        	fields.put("NUMEROCATASTE", new DbValue("numero Cataste", DbValue.INTEGER ));
        	fields.put("PESOPARTENZA", new DbValue("peso Partenza", DbValue.DOUBLE ));
        	fields.put("NOTE", new DbValue("NOTE", DbValue.STRING ));

        }
        
        return fields ;
    }

    public void syncFields( Object obj ) throws Exception {
        Carico o = (Carico) obj ;
        
        HashMap list = getFields( db.getDbType() , true );
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
    
    public String getCreateTable() {
        return "CREATE CACHED TABLE " + table + 
        	" (CODICE VARCHAR(255) , DATA VARCHAR , DATAPESATURA VARCHAR, " +
        	" DESCRIZIONE VARCHAR, DESTINAZIONE VARCHAR, FORNITORE VARCHAR, " +
        	" MERCE VARCHAR, NAVE VARCHAR, NETTO VARCHAR, CONSEGNA VARCHAR, " +
        	" DOCUMENTO VARCHAR, NUMEROCATASTE VARCHAR, NUMEROPROGRESSIVO VARCHAR, " +
        	"PESOFATTURA VARCHAR, PESOPARTENZA VARCHAR, VETTORE VARCHAR )" ;
    }
    
    public String getTable() {
        if ( db.getDbType() == IDatabase.DBType.ACCESS  ) 
            return getAccessDBTable() ;
        else
            return TABLE ;
    }
    public String getAccessDBTable() {
        return "`ARCHIVIO CORRETTO`" ;
    }

    
    public Vector getAllFromAccess() throws Exception {
        String whereTipologia = " destinazione in ( 1,5 ) ";
        // '102','103','104','111','117','119','156','158','162','163','164','165','166','167','168','169','171','172','175','176','177','183','184','185','187','188','191','192','193','196','197','200','201','202','203','204','205','214','215','217','240','274','316','328','332','337','338','450','452','453','454','456'
        String whereCodiceQ = " merce in ( " + Rifiuto.getCodiciQuadrelli() + ") " ;
        return getWithWhere( whereTipologia + " AND " + whereCodiceQ , "data" , "") ;  
    }
    
    // Do not use
    public Vector getFromAccessStartingFrom( Carico from ) throws Exception {
        return null ; // getFromAccess( from , null ) ;
    }
    
    
    public Vector getFromAccessStartingFromTo_deprecated( Carico from , FormattedDate to ) throws Exception {
        
        String dateTo = "" ;
        if ( to != null ) {
            dateTo = " AND data < #" + to.ymdString() + "# " ;
        }
        String whereTipologia = " destinazione in ( 1,5 ) ";
        // '102','103','104','111','117','119','156','158','162','163','164','165','166','167','168','169','171','172','175','176','177','183','184','185','187','188','191','192','193','196','197','200','201','202','203','204','205','214','215','217','240','274','316','328','332','337','338','450','452','453','454','456'
        String whereCodiceQ = " AND merce in ( " + Rifiuto.getCodiciQuadrelli() + ") " ;
        String whereCarico = "" ;
        if ( from != null ) 
            whereCarico = " AND ( ( data >= #" + from.getData().ymdString() + "# and `numero progressivo` > " + from.getNumeroProgressivo() + " ) AND data  #" + from.getData().ymdString() + "# )" ;
        else 
            whereCarico = " AND `numero progressivo` > 0 " ; // escludi giacenze iniziali
        
        return getWithWhere( whereTipologia + whereCodiceQ + whereCarico + dateTo , "data , merce, `numero progressivo` asc", "") ;
        
    }

    public Vector getFromAccess( FormattedDate day, String fromProgressivo , String destinazioni ) throws Exception {
        
    	
        String whereTipologia = " destinazione in ("+destinazioni+") ";
        // '102','103','104','111','117','119','156','158','162','163','164','165','166','167','168','169','171','172','175','176','177','183','184','185','187','188','191','192','193','196','197','200','201','202','203','204','205','214','215','217','240','274','316','328','332','337','338','450','452','453','454','456'
        String whereCodiceQ = " AND merce in ( " + Rifiuto.getCodiciQuadrelli() + ") " ;
        String whereCarico = "" ;
        whereCarico = " AND data = '" + day.ymdString() + "' " ; // and `numero progressivo` > "+ fromProgressivo +" " ;
        /* 
         * OLD CODE quando andavo per progressivo ma forse va rimesso il check del progressivo ?
         *
        if ( fromProgressivo != null ) 
        	whereCarico = " AND data = #" + day.ymdString() + "# " ; // and `numero progressivo` > "+ fromProgressivo +" " ;            
        else  {
            day = new FormattedDate() ;
            whereCarico = " AND data > #" + day.getAnno() + "-01-01#" ; // escludi giacenze iniziali
        }*/
        
        return getWithWhere( whereTipologia + whereCodiceQ + whereCarico , "data , merce, `numero progressivo` asc", "") ;
        
    }
    
    /*
     
     public Vector getScorieFromAccessStartingFrom( Carico from ) throws Exception {
        return getScorieFromAccessStartingFromTo( from , null ) ;
    }
    */
    
    public Vector getScorieFromAccess( FormattedDate day ) throws Exception {
        
        String whereTipologia = " destinazione = 4 ";
        
        String whereCodiceQ = " AND merce in ( " + Rifiuto.getCodiciQuadrelli() + ") " ;
        
        String whereCarico = "" ;
        if ( day != null ) 
            whereCarico = " AND  data = #" + day.ymdString() + "# and `numero progressivo` > 386782 " ;
        else 
            whereCarico = " AND `numero progressivo` > 0 AND data > #01-01-" + new FormattedDate().ymdString().substring(0,4) + "# " ; // escludi giacenze iniziali
        
        return getWithWhere( whereTipologia + whereCarico + whereCodiceQ , "data asc , `numero progressivo` asc ", null ) ;
        
    }
    
    public Vector getRifiutoFromAccess( String codQuadrelli, FormattedDate day ) throws Exception {
        
        String whereTipologia = " destinazione = 4 ";
        
        String whereCodiceQ = " AND merce = '" + codQuadrelli + "' " ;
        
        String whereCarico = "" ;
        if ( day != null ) 
            whereCarico = " AND  data = #" + day.ymdString() + "# " ;
        else 
            whereCarico = " AND `numero progressivo` > 0 AND data > #01-01-" + new FormattedDate().ymdString().substring(0,4) + "# " ; // escludi giacenze iniziali
        
        return getWithWhere( whereTipologia + whereCarico + whereCodiceQ , "data asc , `numero progressivo` asc ", null ) ;
        
    }
    
    public Vector getLast( String destinazioni ) {

    	
        Vector list = null ;
           
        try {
            String whereTipologia = " destinazione in ( '" + destinazioni.replaceAll(",", "','") + "' ) ";
            // '102','103','104','111','117','119','156','158','162','163','164','165','166','167','168','169','171','172','175','176','177','183','184','185','187','188','191','192','193','196','197','200','201','202','203','204','205','214','215','217','240','274','316','328','332','337','338','450','452','453','454','456'
            String whereCodiceQ = " AND merce in ( " + Rifiuto.getCodiciQuadrelli() + ") " ;
            
            String where = whereTipologia + whereCodiceQ ;
            if ( db.getDbType() == IDatabase.DBType.ACCESS )
                list = getWithWhere( where, "data desc , `numero progressivo` desc " , "1") ;
            else 
                list = getWithWhere( where  , " data desc , numeroprogressivo desc " , "1") ;
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        return list ;
    }
    
    public Carico getLastScorie( ) {
        
        Vector list = null ;
        Carico c = null ;
        
        try {
            String where = " destinazione = 4 ";
            // Aggiungere anche se non è stata scaricata ?
            if ( db.getDbType() == IDatabase.DBType.ACCESS )
                list = getWithWhere( where, " data desc , `numero progressivo` desc " , "1") ;
            else 
                list = getWithWhere( where , " data desc , numeroprogressivo desc " , "1") ;
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        
        if ( list != null && list.size() > 0 ) {
            return (Carico) list.firstElement() ;
        }
        else {
            return null ;
        }
        
    }
    
    public Vector getFromAccess( String codMovimento ) {
        
        Vector list = null ;
        
        if ( this.db.getDbType() == IDatabase.DBType.ACCESS ) {
            try {
                 list = getWithWhere(" `Codice Quadrelli` IN (" + codMovimento + ")") ;
            }
            catch ( Exception e ) {
                e.printStackTrace();
            }
        }
        return list ;
    }

    public double getLavoratoRifiuto( String codiceRifiuto , int anno  ) {
        
       Double d = null ;
       
       String sql = " SELECT SUM( CAST( NETTO AS DOUBLE )) FROM " + getTable() + " WHERE " +
       		" MERCE = '" + codiceRifiuto + "' AND DATASCARICO between '"+anno+"-01-01' and '"+(anno+1)+"-01-01' " ;  
           
       try {
	       	d = new Double( "0" + executeScalarQuery(sql).firstElement());
	    } catch (Exception e) {
	        d = new Double(0) ;
	    }
	    
	    return d.longValue() ;
       
       
    }
    
    public double getGiacenzaRifiuto( String codiceRifiuto  , int anno  ) {
        
       Double d = null ;
       
       String sql = " SELECT SUM( CAST( NETTO AS DOUBLE )) FROM " + getTable() + " WHERE " +
       		" MERCE = '" + codiceRifiuto + "' AND DATASCARICO IS NULL " +
       				" AND DATA between '"+anno+"-01-01' and '"+(anno+1)+"-01-01' " ;  
           
       try {
	       	d = new Double( "0" + executeScalarQuery(sql).firstElement());
	    } catch (Exception e) {
	        d = new Double(0) ;
	    }
	    
	    return d.longValue() ;
       
       
    }
    
    public Vector getCarichiDaScaricare( String codiceRifiuto, int a ) {
        
        Vector list = new Vector() ;
        
        String whereRifiuto = "" ;
        String anno = "" + a ;
        String annoSucc = "" + ( a + 1 ) ;
        if ( codiceRifiuto != null && codiceRifiuto.length() > 0 ) {
            whereRifiuto = " AND MERCE = '" + codiceRifiuto + "' " ;
        }
        
        try {
            list = getWithWhere( " DATASCARICO IS NULL " + whereRifiuto + " AND DATA BETWEEN '" + anno + "-01-01' AND '" + annoSucc + "-01-01'") ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list ;
        
    }

    public Vector filterRifiuto(String rifiuto) throws Exception {
        return get(new DbValue("Merce",rifiuto,DbValue.STRING));
        
    }
    
    public Vector filterRifiuto(String rifiuto[]) throws Exception {
        DbValue[] db= new DbValue[rifiuto.length];
        for(int i=0;i<rifiuto.length;i++)
        	db[i]=new DbValue("Merce",rifiuto[i],DbValue.STRING);
        return get(db);
        
    }
    
    public void scarica( Carico c , FormattedDate data ) throws Exception {
        
        String sql = " update  " + getTable() + 
        	" set datascarico = '" + data.fullString() + "' where id = " + c.getId() ; 
        
        int x = db.executeNonQuery(sql) ;
        
        if ( x < 0 )
            throw new SQLException("Errore nell'update del carico") ;
        else {
            c.setDataScarico(data) ;
        }
    }

    /**
     * @param numeroProgressivo
     * @param data
     * @return
     */
    public Carico getByProgressivoAndAnno(String numeroProgressivo, FormattedDate data) throws Exception {
        
        DbValue[] w = new DbValue[2] ;
        
        HashMap fields = getFields( db.getDbType(), false);
        
        String fieldNum = ((DbValue) fields.get("NUMEROPROGRESSIVO")).getFieldName() ;
        String fieldData = ((DbValue) fields.get("DATA")).getFieldName() ; 
        
        boolean isAccessDB = false ;
        if (db.getDbType() == IDatabase.DBType.ACCESS )
            isAccessDB = true ;	
        
        String whereAnno = fieldData + " BETWEEN " +
        	( isAccessDB ? "#" : "'" ) +
        	data.getAnno() + "-01-01 00:00:00" +
        	( isAccessDB ? "#" : "'" ) +
        	" AND " +
        	( isAccessDB ? "#" : "'" ) +
        	data.getAnno() + "-12-31 23:59:59" +
        	( isAccessDB ? "#" : "'" ) ;
        
        if ( isAccessDB ) 
            fieldNum = "`" + fieldNum + "`" ;
        
        Vector v = getWithWhere( fieldNum + " = " + numeroProgressivo + " AND " + whereAnno );
        
        if ( v != null && v.size() > 0 )
            return (Carico) v.firstElement() ;
        
        return null ;
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
    	Carico.clearCache();
    	db.executeNonQuery("DELETE FROM " + getTable_()  ) ;
    	db.executeNonQuery("ALTER TABLE " + getTable_() + " ALTER COLUMN ID RESTART WITH 1;"  ) ;
    }
}