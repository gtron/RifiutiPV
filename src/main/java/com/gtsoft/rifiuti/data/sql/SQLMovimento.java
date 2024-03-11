/*
 * Created on 21-ago-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.data.sql;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.gtsoft.rifiuti.data.Carico;
import com.gtsoft.rifiuti.data.CaricoParziale;
import com.gtsoft.rifiuti.data.Movimento;
import com.gtsoft.utils.DbValue;
import com.gtsoft.utils.FormattedDate;
import com.gtsoft.utils.superdata.IDatabase;
import com.gtsoft.utils.superdata.ISQLAdapter;


/**
 * @author Gtron - 
 * 
 */
public class SQLMovimento extends ISQLAdapter {
    
    private static final int fieldsCount = 9 ;
    private static final String TABLE = "Movimenti" ; 
    private static final String TABLE_SCARICHI = "Scarichi" ;
    
    private boolean autoGetCarico = true ;
    
    private boolean isAutoGetCarico() {
        return autoGetCarico;
    }
    private void setAutoGetCarico(boolean autoGetCarico) {
        this.autoGetCarico = autoGetCarico;
    }
    public SQLMovimento( IDatabase db ) {
        super(db) ;
        table = getTable() ;
    }
    
    public SQLMovimento() {
        super() ;
        table = getTable() ;
    }
    
    public Object getFromFields() { 
        Movimento o = new Movimento();
        
        DbValue field =  (DbValue) fields.get("NUMPROGRESSIVO") ; 
        if ( field != null && ! field.wasNull() ){
            o.setNumProgressivo( ((Integer) field.getValue()).intValue() ) ;
        }
        field = (DbValue) fields.get("DATA") ; 
        if ( field != null && ! field.wasNull() ){
            o.setData( ((FormattedDate) field.getValue()) ) ;
        }
        field = (DbValue) fields.get("DATAREGISTRO") ; 
        if ( field != null && ! field.wasNull() ){
            o.setDataStampaRegistro( ((FormattedDate) field.getValue()) ) ;
        }
        field = (DbValue) fields.get("ANNO") ; 
        if ( field != null && ! field.wasNull() ){
            o.setAnno( ((Integer) field.getValue()).intValue() ) ;
        }
        field = (DbValue) fields.get("TRASPORTATORE") ; 
        if ( field != null && ! field.wasNull() ){
            o.setIdTrasportatore( "" + field.getValue() ) ;
        }
        field = (DbValue) fields.get("TIPO") ; 
        if ( field != null && ! field.wasNull() ){
            o.setTipo( ((Integer) field.getValue()).intValue() ) ; 
        }
        field = (DbValue) fields.get("PARZIALE") ; 
        if ( field != null && ! field.wasNull() ){
            o.setIdCaricoParziale( ((Integer) field.getValue()).intValue() ) ; 
        }
        
        field = (DbValue) fields.get("CARICO") ;
        if ( field != null && ! field.wasNull()  ){
            o.setCarico( ((Integer) field.getValue()).intValue() ) ;
        }
        
        field = (DbValue) fields.get("MERCE") ;
        if ( field != null && ! field.wasNull()  ){
            o.setMerceOriginale( (String) field.getValue() ) ;
        }
        field = (DbValue) fields.get("NOTE") ;
        if ( field != null && ! field.wasNull()  ){
            o.setNote( (String) field.getValue() ) ;
        }
        
        
        /* 
         * 
         
         field = (DbValue) fields.get("TRASPORTATORE") ; 
         if ( field != null && ! field.wasNull() ){
         o.setFornitore( ((String) field.getValue()) ) ;
         }
         field = (DbValue) fields.get("REGISTRO") ; 
         if ( field != null && ! field.wasNull() ){
         o.setMerce( ((String) field.getValue()) ) ;
         }
         field = (DbValue) fields.get("FORMULARIO") ; 
         if ( field != null && ! field.wasNull() ){
         o.setNave( "" + field.getValue() ) ;
         }
         */
        
        return o ;
    }
    
    public HashMap getFields( int dbType , boolean reload) {

        return getFields( reload ) ;
     
    }
    
    public HashMap getFields( boolean reload ) {

        if ( reload || fields == null) {
            
            fields = new HashMap(fieldsCount);
            
            DbValue key =  new DbValue("NUMPROGRESSIVO", DbValue.INTEGER );
            key.setKey(true);
            fields.put("NUMPROGRESSIVO" , key ) ;
            
            key =  new DbValue("ANNO", DbValue.INTEGER );
            key.setKey(true);
            fields.put("ANNO" , key ) ;
            
            fields.put("DATA", new DbValue("DATA", DbValue.DATE ));
            fields.put("TIPO", new DbValue("TIPO", DbValue.INTEGER ));
            fields.put("CARICO", new DbValue("CARICO", DbValue.INTEGER ));
            fields.put("PARZIALE", new DbValue("PARZIALE", DbValue.INTEGER ));
            fields.put("TRASPORTATORE", new DbValue("TRASPORTATORE", DbValue.INTEGER ));
            fields.put("DATAREGISTRO", new DbValue("DATAREGISTRO", DbValue.DATE ));
            // fields.put("FORMULARIO", new DbValue("FORMULARIO", DbValue.STRING ));
            
            fields.put("MERCE", new DbValue("MERCE", DbValue.STRING ));
            fields.put("NOTE", new DbValue("NOTE", DbValue.STRING ));
            
        }
        
        return fields;
    }
    
    
    public void syncFields( Object obj ) throws Exception {
        Movimento o = (Movimento) obj ;
        
        HashMap list = getFields(true);
        ((DbValue) list.get("NUMPROGRESSIVO")).setValue( o.getNumProgressivo() ) ;
        ((DbValue) list.get("ANNO")).setValue( o.getAnno() ) ;
        ((DbValue) list.get("DATA")).setValue( o.getData() ) ;
        ((DbValue) list.get("TIPO")).setValue( o.getTipoAsInt() ) ;
        ((DbValue) list.get("DATAREGISTRO")).setValue( o.getDataStampaRegistro() ) ;
        ((DbValue) list.get("PARZIALE")).setValue( o.getIdCaricoParziale() ) ;
        
        ((DbValue) list.get("MERCE")).setValue( o.getMerceOriginale() ) ;
        ((DbValue) list.get("NOTE")).setValue( o.getNote() ) ;
        
        if ( ! o.isScarico() ) {
            ((DbValue) list.get("CARICO")).setValue( ((Carico) o.getCarichi().firstElement()).getId() ) ;
        }
        
        
        // ((DbValue) list.get("TRASPORTATORE")).setValue( o.getTrasportatore().getId() ) ;
         /* 
         ((DbValue) list.get("")).setValue( 
         ((DbValue) list.get("FORMULARIO")).setValue( 
         */
        
    }
    
    public Vector getAllByAnno(String anno ) throws Exception {
        
        StringBuilder sql = new StringBuilder() ;
        SQLCarico sqlCarico = new SQLCarico(db) ;
        
        Integer.valueOf(anno) ; // check for type consistency
        
        sql.append("SELECT * FROM "); sql.append(getTable());
       // sql.append(" m left JOIN "); sql.append( sqlCarico.getTable() ) ;
        //sql.append( " c ON m.carico = c.id " );
//        int annoSucc = Integer.valueOf(anno) + 1;  
        //sql.append( " WHERE data BETWEEN '" + anno + "-01-01' AND '" + anno + "-12-31' ");
        sql.append( " WHERE anno = " + anno );
        sql.append( " order by numprogressivo desc" );
        
        ResultSet rs = db.executeQuery(sql.toString());
        
        Vector list = new Vector() ;
        Carico c = null;
        Movimento m = null ;
        
        setAutoGetCarico( false );
        
        if ( rs != null ) 
            while( rs.next() ) {
                
                m = (Movimento) getFromRS(rs);
                /*
                 * setAutoGetCarico(false);
                 * Non funge
                 if ( ! m.isScarico() ) 
                    m.setCarico( (Carico) sqlCarico.getFromRS(rs) ); 
                    */
                
                list.add(m);
                
            }
        
        return list ;
                
    }
    
    public String getTable() {
        return TABLE ;
    }
    
    public static String getTable_() {
        return TABLE ;
    }
    
    public Vector getCarichi ( int scarico , int anno ) {
        
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * ") ; // sql.append( getFieldsList() );
        sql.append(" FROM ") ; sql.append( TABLE_SCARICHI ); 
        
        sql.append(" WHERE SCARICO = " );
        sql.append( scarico );
        
        sql.append(" AND ANNO = " );
        sql.append( anno );
        
        sql.append(" ORDER BY IDCARICO" );
        
        ResultSet rs = null ;
        
        Vector list = new Vector() ;
        
        try {
            rs = db.executeQuery(sql.toString()) ;
            
            if (rs != null )
                while( rs.next() ) 
                    list.add( Carico.get( rs.getInt("IDCARICO") ) ); 
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        
        return list ;
        
    }
    
    public void create()  {
        create( new Movimento() ) ;
    }
    
    public Vector get( FormattedDate from , FormattedDate to ) throws Exception {
                
        String where = "" ;
        
        if ( from != null ) 
            where += ( where != "" ) ? " AND " : "" + "DATA >= '" + from.ymdString() + "' " ;
        
        if ( to != null ) 
            where += ( where != "" ) ? " AND " : "" + "DATA < '" + to.ymdString() + "' " ;
        
        return getWithWhere(where);
        
    }
    
    public synchronized int create( Movimento m ) {
        
        StringBuilder sql = new StringBuilder();
        int num = -2 ;
        
        try {
	        
            db.startTransaction();
	        
	        sql.append("INSERT INTO ") ;
	        sql.append( getTable() );
	        sql.append( " VALUES ( " );
	        
	        sql.append( " NEXT VALUE FOR prossimo_movimento " ); sql.append( "," ); 
	        sql.append( fields.get("ANNO") ); sql.append( "," ); 
	        sql.append( fields.get("DATA") ); sql.append( "," ); 
	        sql.append( fields.get("TIPO") ); sql.append( "," ); 
	        sql.append( fields.get("CARICO") ); sql.append( "," );
	        sql.append( fields.get("TRASPORTATORE") ); 
	        
	        sql.append( ",null,null," );
	        
	        sql.append( fields.get("PARZIALE") );sql.append( "," );
	        sql.append( fields.get("MERCE") );sql.append( "," );
	        sql.append( fields.get("NOTE") ); 
	        
	        sql.append( ");" );
	        
	        
	        db.executeNonQuery(sql.toString()) ;
	        
	        
	        if ( m.isScarico() || m.isParziale() ) {
	            
	            Movimento newMov = (Movimento) getWithWhere( 
		                " numprogressivo = ( SELECT MAX(NUMPROGRESSIVO) FROM " + 
		                getTable() + " WHERE ANNO = " + fields.get("ANNO")  + ") and anno = " + 
		                fields.get("ANNO") ).firstElement();
		        
		        m.setNumProgressivo( newMov.getNumProgressivo() ) ; 
		        num = newMov.getNumProgressivo();
		        if ( m.isScarico() ) {
		            db.executeNonQuery( getScarichiSQL( m , newMov , (DbValue) fields.get("DATA") ) ) ;
		        }
		        
	        }
	        
	        
	        db.commit();
	        
	        
        }
        catch( Exception e ) {
            e.printStackTrace();
            db.rollback();
            num = -1 ;
        }
        
        return num ;
        
    }
    
    private String getScarichiSQL( Movimento m , Movimento newMov , DbValue data ) throws Exception {
        
        StringBuilder sql  = new StringBuilder();
        String strData = data.toString() ;
        
        Iterator i = m.getCarichi().iterator() ;
        Carico c = null ;
        
        while ( i.hasNext() ) {
            c = (Carico) i.next() ;
            sql.append("INSERT INTO SCARICHI VALUES ( ") ;
            
            sql.append( newMov.getNumProgressivo() ) ; sql.append( "," ) ;
            sql.append( newMov.getAnno() ) ; sql.append( ",'" ) ;
            sql.append( c.getNumeroProgressivo() ) ; sql.append( "'," ) ;
            sql.append( Integer.valueOf( c.getData().ymdString().substring(0,4) ) ) ; sql.append( "," ) ;
            sql.append( c.getId() ) ; sql.append( ") ;\n" ) ;
            
            /*sql.append( "update carichi set datascarico = " ); sql.append( strData ) ; 
            sql.append( " where id = " ) ; sql.append( c.getId() ); sql.append( ";\n" ) ;*/
            
            c.scarica( m.getData() ) ;
            
        }
        
        return sql.toString() ;
    }
    
    public Movimento getForCarico(int id) throws Exception {
        return (Movimento) getWithWhere(" CARICO = " + id ).firstElement();
    }
    /**
     * @param m
     */
    public void delete(Movimento m) {
        StringBuilder sql = new StringBuilder();
        
        SQLCarico sqlCarico = new SQLCarico();
        
        try {
	        sql.append("SET AUTOCOMMIT FALSE;") ;
	        
	        sql.append("update ") ;
	        sql.append( sqlCarico.getTable() ); sql.append( " SET DATASCARICO = null " );
	        sql.append( " WHERE ID IN ( SELECT IDCARICO FROM SCARICHI WHERE SCARICO = " );
	        sql.append( m.getNumProgressivo() ) ; sql.append( " AND ANNO = " ) ;
	        sql.append( m.getAnno() ) ; sql.append( " ) ; " ) ;
	        
	        db.executeNonQuery(sql.toString()) ;
	        
	        sql = new StringBuilder();
	        sql.append("DELETE FROM SCARICHI WHERE SCARICO = " );
	        sql.append( m.getNumProgressivo() ) ; sql.append( " AND ANNO = " ) ;
	        sql.append( m.getAnno() ) ;
	        
	        db.executeNonQuery(sql.toString()) ;
	        
	        sql = new StringBuilder();
	        sql.append("UPDATE SCARICHI SET scarico = scarico - 1 WHERE SCARICO > " );
	        sql.append( m.getNumProgressivo() ) ; sql.append( " AND ANNO = " ) ;
	        sql.append( m.getAnno() ) ;
	        
	        db.executeNonQuery(sql.toString()) ;
	        
	        /*sql = new StringBuilder();
	        
	        sql.append("update ") ;
	        sql.append( SQLCaricoParziale.getTable_() ); 
	        sql.append( " SET SCARICO = SCARICO - 1 WHERE SCARICO > " );
	        sql.append( m.getNumProgressivo() ) ; sql.append( " and  left( datascarico, 4 ) = '" ) ;
	        sql.append( m.getAnno() ) ;sql.append( "'" ) ;
	        
	        db.executeNonQuery(sql.toString()) ;
	        
	        sql = new StringBuilder();
			*/
			
	        sql.append("DELETE FROM ");
	        sql.append( getTable()) ;
	        sql.append(" WHERE NUMPROGRESSIVO = " );
	        sql.append( m.getNumProgressivo() ) ; sql.append( " AND ANNO = " ) ;
	        sql.append( m.getAnno() ) ;
	        
	        db.executeNonQuery(sql.toString()) ;
	        
	        sql = new StringBuilder();
	        sql.append("UPDATE ");
	        sql.append( getTable()) ;
	        sql.append(" SET NUMPROGRESSIVO = NUMPROGRESSIVO - 1 WHERE NUMPROGRESSIVO > " );
	        sql.append( m.getNumProgressivo() ) ; sql.append( " AND ANNO = " ) ;
	        sql.append( m.getAnno() ) ;
	        
	        db.executeNonQuery(sql.toString()) ;
	        
	        sql = new StringBuilder();
	        sql.append("SELECT MAX(NUMPROGRESSIVO) as X FROM ");
	        sql.append( getTable()) ;
	        sql.append( " WHERE ANNO = " ) ;
	        sql.append( m.getAnno() ) ;
	        
	        ResultSet rs = db.executeQuery(sql.toString()) ;
	        
	        int num = 0 ;
	        if ( rs != null && rs.next())
	            num = rs.getInt("X");

	        sql = new StringBuilder();
	        sql.append("ALTER SEQUENCE prossimo_movimento RESTART WITH ");
	        sql.append( num + 1 ) ;
	        
	        db.executeNonQuery(sql.toString()) ;
	        
	        db.executeNonQuery("COMMIT;CHECKPOINT;") ;
	        
        }
        catch( Exception e ) {
            e.printStackTrace();
            try {
                db.executeNonQuery("ROLLBACK ; CHECKPOINT;") ;
            }
            catch( Exception ex ) {
                ex.printStackTrace();
            }
        }
    }
    /**
     * 
     */
    public Movimento getPrimoMovimentoDaStampare( String anno ) {
        Vector l = null ;
        try {
            l = getWithWhere(" numProgressivo = min(numProgressivo) " +
            		" and dataregistro is null and anno = " + anno );
        } catch (Exception e) { }
        
        if ( l != null && l.size() > 0 )
            return (Movimento) l.firstElement();
        else
            return null ;
    }
    /**
     * @param movimento
     */
    public void setStampatoRegistro(Movimento m) throws Exception {
        String sql = "UPDATE " + getTable() + " SET dataregistro = '" + m.getDataStampaRegistro() + 
        	"' WHERE NUMPROGRESSIVO = " + m.getNumProgressivo() + " AND ANNO = " + m.getAnno() ;
        
        db.executeNonQuery(sql) ;
    }
    /**
     * @return
     */
    public int countDaStampare() throws Exception {
        Integer num = Integer.valueOf(0);
        
        String sql = "select count(*) from " + getTable() + " where dataregistro is null " ;
        ResultSet rs = db.executeQuery(sql);
        
        if ( rs != null ) {
            rs.next();
            num = Integer.valueOf("" + rs.getObject(1));
            
        }
        if ( num != null )
            return num.intValue() ;
        else 
            return 0 ;
    }
    
    public String getNumProgressivoCarico( int numCarico , int anno ) throws Exception {
        String num = null;
        
        String sql = "select numprogressivo from " + TABLE + " where tipo in (2,4) and anno = " + anno + " and carico = " + numCarico ;
        ResultSet rs = db.executeQuery(sql);
        
        if ( rs != null ) {
            rs.next();
            num = rs.getString(1) ;
            
        }
        return num ;
    }
    
    public Movimento getLast( ) {
        
        Vector list = null ;
        
        try {
            list = getWithWhere( null , " data desc , numProgressivo desc " , "1") ;
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        
        if ( list != null && list.size() > 0 ) {
            return (Movimento) list.firstElement() ;
        }
        else {
            return null ;
        }
        
    }

    public static void purgeTable(IDatabase db) throws Exception {
    	Movimento.clearCache();
    	db.executeNonQuery("DELETE FROM " + getTable_()  ) ;
    	db.executeNonQuery("DELETE FROM " + TABLE_SCARICHI  ) ;
    	
    	db.executeNonQuery("alter sequence prossimo_movimento restart with 1 ;") ;
    }
}