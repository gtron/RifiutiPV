/*
 * Created on 8-lug-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.data.sql;

import java.util.HashMap;
import java.util.Vector;

import com.gtsoft.rifiuti.data.StoricoMovimenti;
import com.gtsoft.utils.DbValue;
import com.gtsoft.utils.FormattedDate;
import com.gtsoft.utils.superdata.IDatabase;
import com.gtsoft.utils.superdata.ISQLAdapter;

/**
 * @author Gtron - 
 * 
 */
public class SQLStoricoMovimenti extends ISQLAdapter {
    
    private static final int fieldsCount = 5 ;
    private static final String TABLE = "STORICOMOVIMENTI" ; 
    
    public SQLStoricoMovimenti( IDatabase db ) {
        super(db) ;
       
        table = getTable() ;
    }
    
    public SQLStoricoMovimenti() {
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
    
    public void create() throws Exception {
		
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO ") ;
		sql.append( table ); sql.append( " (" );
		sql.append( getFieldsList(false) ); sql.append( ") VALUES ( " );
		
		sql.append( getFieldsValues(false) ); sql.append( ")" );
		
        db.executeNonQuery(sql.toString()) ;
        
	}
    
    public StoricoMovimenti getById( int id ) throws Exception {
        
        DbValue d = new DbValue("ID" , new Integer(id) , DbValue.INTEGER ) ;
        
        return (StoricoMovimenti) get( d ).firstElement() ;
        
    }

    public Object getFromFields() {
        StoricoMovimenti o = new StoricoMovimenti();
         fillFromFields(o) ;
         return o ;
    }
        
    public void fillFromFields(StoricoMovimenti o ) {
       
        DbValue field =  (DbValue) fields.get("DATA") ; 
        if ( field != null && ! field.wasNull() ){
            o.setGiorno( ((FormattedDate) field.getValue()) ) ;
        }
         
        field = (DbValue) fields.get("MERCE") ; 
        if ( field != null && ! field.wasNull() ){
            o.setIdMerce( (Integer) field.getValue() ) ;
        }
        
        field = (DbValue) fields.get("CARICO") ; 
        if ( field != null && ! field.wasNull() ){
            o.setCaricato( ((Integer) field.getValue()) ) ;
        }
        field = (DbValue) fields.get("SCARICO") ; 
        if ( field != null && ! field.wasNull() ){
            o.setScaricato( ((Integer) field.getValue()) ) ;
        }
        
        field = (DbValue) fields.get("GIACENZA") ; 
        if ( field != null && ! field.wasNull() ){
            o.setGiacenza( ((Integer) field.getValue()) ) ;
        }
        
        field = (DbValue) fields.get("TOTCARICATO") ; 
        if ( field != null && ! field.wasNull() ){
            o.setTotCaricato( ((Integer) field.getValue()) ) ;
        }
      
    }
    
    public HashMap getFields( int dbType , boolean reload ) {

        if ( dbType == IDatabase.DBType.ACCESS ) {
            return null ;
        }
        else {
            return getFields(reload) ;
        }
    }
    
    public HashMap getFields( boolean reload) {

        if ( reload || fields == null) {
            
            fields = new HashMap(fieldsCount);
            
            DbValue key =  new DbValue("MERCE", DbValue.INTEGER );
            key.setKey(true);
            fields.put("MERCE" , key ) ;
        	
            DbValue d =  new DbValue("DATA", DbValue.DATE );
            d.setKey(true);
            fields.put("DATA" , d ) ;
            
        	fields.put("CARICO", new DbValue("CARICO", DbValue.INTEGER ));
        	fields.put("SCARICO", new DbValue("SCARICO", DbValue.INTEGER));
        	fields.put("TOTCARICATO", new DbValue("TOTCARICATO", DbValue.INTEGER ));
        	fields.put("GIACENZA", new DbValue("GIACENZA", DbValue.INTEGER ));
        	
        }
        
        return fields;
    }
    
    public void syncFields( Object obj ) throws Exception {
        StoricoMovimenti o = (StoricoMovimenti) obj ;
        
        HashMap list = getFields( db.getDbType() , true );
        ((DbValue) list.get("MERCE")).setValue( o.getIdMerce() ) ;
        ((DbValue) list.get("DATA")).setValue( o.getGiorno() ) ;
        ((DbValue) list.get("CARICO")).setValue( o.getCaricato() ) ;
        ((DbValue) list.get("SCARICO")).setValue( o.getScaricato() ) ;
        ((DbValue) list.get("TOTCARICATO")).setValue( o.getTotCaricato() ) ;
        ((DbValue) list.get("GIACENZA")).setValue( o.getGiacenza() ) ;
    }
    
    public String getCreateTable() {
        return "CREATE CACHED TABLE " + getTable() + 
        	" (DATA DATE NOT NULL,MERCE INTEGER NOT NULL,CARICO INTEGER, " +
        	" SCARICO INTEGER, TOTCARICATO INTEGER,GIACENZA INTEGER,PRIMARY KEY(DATA,MERCE) )" ;
    }
    
    public String getTable() {
        return TABLE ;
    }
    
    public StoricoMovimenti getLast() throws Exception {
        Vector v = getWithWhere("","DATA DESC LIMIT 1", "");
        
        if ( v != null && v.size() > 0 )
            return (StoricoMovimenti) v.firstElement() ;

        return null ;
    }
    public Vector get( Integer merce , FormattedDate from , FormattedDate to ) throws Exception {
        
        if ( merce == null && from == null && to == null ) {
            throw new Exception( "Undefined Parameters") ;
        }
        
        String where = "" ;
        
        if ( merce != null ) 
            where = "MERCE = " + merce ;
        
        where += ( ( where != "" ) ? " AND " : "" ) + "DATA >= '"  +
                ( ( from != null ) ?  from.ymdString() : 
                    new FormattedDate().getAnno() +  "-01-01" ) + "' " ;
        
        if ( to != null ) 
            where += (( where != "" ) ? " AND " : "" ) + "DATA < '" + to.ymdString() + "' " ;
        
        return getWithWhere(where);
        
    }
    
    public Vector getLastStorici() throws Exception {
        
        String sql = "select s.* from storicomovimenti s left join " +
        		" (select merce, max(data) as d from storicomovimenti group by merce  ) s2 " +
        		" on s.merce = s2.merce  and s.data = s2.d " +
        		" where s2.d is not null " ;
        
        return getFromRawSql(sql) ;
        
    }
    public void flush() throws Exception {
        db.executeNonQuery("delete from " + getTable());
    }
    
    public static void purgeTable(IDatabase db) throws Exception {
    	db.executeNonQuery("DELETE FROM " + getTable_()  ) ;
    }
}