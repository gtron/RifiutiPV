/*
 * Created on 2-lug-2005
 *
 */
package com.gtsoft.utils.superdata;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.gtsoft.rifiuti.data.Movimento;
import com.gtsoft.utils.DbValue;
import com.gtsoft.utils.HsqlDB;

/**
 * @author Gtron
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class ISQLAdapter<T> {
	
    protected String table ;
	// private DbValue[] key ;
	protected HashMap fields ;
	protected IDatabase db ;
	protected Movimento movimento ;
	
	protected final static String FIELDSEPARATOR = ", ";
	

//    private Movimento getMovimento() {
//        return movimento;
//    }
//    private void setMovimento(Movimento movimento) {
//        this.movimento = movimento;
//    }
	public abstract Object getFromFields() ;
	public abstract HashMap getFields( int dbType , boolean reload );
	public abstract void syncFields( Object obj ) throws Exception ;
	
	public void create() throws Exception {
		
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO ") ;
		sql.append( table ); sql.append( " (" );
		sql.append( getFieldsList(true) ); sql.append( ") VALUES ( " );
		
		sql.append( getFieldsValues(true) ); sql.append( ")" );
		
        db.executeNonQuery(sql.toString()) ;
        
	}
	
	public Integer createTellID() throws Exception {
		
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO ") ;
		sql.append( table ); sql.append( " (" );
		sql.append( getFieldsList(true) ); sql.append( ") VALUES ( " );
		
		sql.append( getFieldsValues(true) ); sql.append( ")" );
		
        db.executeNonQuery(sql.toString()) ;
        
        ResultSet rs = db.executeQuery("CALL IDENTITY()") ;
        rs.next();
        return Integer.valueOf(rs.getString(1) );
        
	}
	
	public void update() throws Exception{ 
	    Set dbfields = getFields(db.getDbType(), false).entrySet();
	    
	    DbValue v = null ;
	    Vector fields = new Vector() ;
	    Vector where = new Vector(2) ;
	    
	    int f = 0 ; int w = 0 ;
	    
	    for ( Iterator i = dbfields.iterator() ; i.hasNext() ; ) {
	        v = (DbValue)((Map.Entry) i.next()).getValue() ;
	        
	        if ( v.isKey() ) {
	            where.add( v ); 
	        }
	        else {
	            fields.add( v ); 
	        }
	    }
	    
	    update ( fields, where );
	    
	}
	
	public void update( Vector fields , Vector where ) throws Exception {
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE ") ;
		sql.append( table );
		sql.append( " SET " ); 
		DbValue v = null ;
		int x = 0 ; 
		for ( Iterator i = fields.iterator(); i.hasNext(); ) {
		    v = (DbValue) i.next() ;
		    if ( x > 0 ) sql.append(", ");
		    x++;
		    sql.append( v.getFieldName() );
		    sql.append(" = ");
		    sql.append( v.toString() );
		}
		
		if ( where != null && where.size() > 0 ) {
		    sql.append( " WHERE " );
		    x = 0 ;
		    for ( Iterator i = where.iterator(); i.hasNext(); ) {
		        v = (DbValue) i.next() ;
			    if ( x > 0 ) sql.append(" AND ");
			    x++;
			    sql.append( v.getFieldName() );
			    sql.append(" = ");
			    sql.append( v.toString() );
			}		    
		}

        db.executeNonQuery(sql.toString()) ;
        
	}
	
	
	public Vector<T> getAll() throws Exception {
	    DbValue[] v = null ;
	    return get( v ) ;
	}
	
	public Vector getAll( String orderBy ) throws Exception {
	    DbValue[] v = null ;
	    return get( v , orderBy ) ;
	}
	
	public Vector get ( DbValue key ) throws Exception {
	    DbValue[] keys = new DbValue[1] ;
	    keys[0] = key ;
	    
	    Vector list = get( keys, null ) ;
	    
	   return list ;
	}
	
	public Vector get( DbValue [] keys ) throws Exception {
	    return get( keys, null ) ;
	}
	
	public Vector get( DbValue [] keys , String orderBy ) throws Exception {
	    
	    
	    StringBuilder sql = new StringBuilder();
	    sql.append("SELECT * ") ; // sql.append( getFieldsList() );
	    sql.append(" FROM ") ; sql.append( table ); 
	    
	    
	    if ( keys != null ) {
	        sql.append(" WHERE " );
	        int w = 0;
	        for ( ; w < keys.length - 1; w++ ) {
	            sql.append( "`" );
	            sql.append( keys[w].getFieldName() );
	            sql.append( "`" );
	            sql.append( " = ");
	            keys[w].setDbType(db.getDbType()) ;
	            sql.append( keys[w].toString() ) ; 
	            sql.append(" AND ");
	            //sql.append(" OR ");
	        }
	        keys[w].setDbType(db.getDbType()) ;
	        sql.append( keys[w].getFieldName()); sql.append( " = "); sql.append( keys[w].toString() ) ; 
	    }
	    
	    if ( orderBy != null && orderBy.length() > 0 ) {
	        sql.append(" ORDER BY " + orderBy ) ;
	    }
	    
	    ResultSet rs = null ;
	    
	    rs = db.executeQuery(sql.toString()) ;
	    
	    Vector list = new Vector() ;
	    
	    if (rs != null )
	        while( rs.next() ) 
	            list.add( getFromRS(rs) );
	    
	    return list ;
	    
	}
	

	
	public Vector getWithWhere( String whereClause , String order , String limit ) throws Exception {
	    
	    
	    StringBuilder sql = new StringBuilder();
	    
	    sql.append("SELECT ") ; // sql.append( getFieldsList() );
	    
	    if ( limit != null && limit.length() > 0 ) {
	        sql.append( " TOP " ) ;
	        sql.append( limit );
	    }
	    
	    sql.append(" * FROM ") ; sql.append( table ); 
	    
	    
	    if ( whereClause != null && whereClause.length() > 0 ) {
	        sql.append(" WHERE " );
	        sql.append(whereClause); 
	    }
	    
	    if ( order != null && order.length() > 0 ) {
	        sql.append( " ORDER BY " ) ;
	        sql.append( order );
	    }
	    
	    ResultSet rs = null ;
	    
	    rs = db.executeQuery(sql.toString()) ;
	    
	    Vector list = new Vector() ;
	    
	    if (rs != null )
	        while( rs.next() ) 
	            list.add( getFromRS(rs) );
	    
	    return list ;
	    
	}
	
	
	public Vector getFromRawSql( String sql ) throws Exception {
	    
	    
	    ResultSet rs = null ;
	    
	    rs = db.executeQuery(sql) ;
	    
	    Vector list = new Vector() ;
	    
	    if (rs != null )
	        while( rs.next() ) 
	            list.add( getFromRS(rs) );
	    
	    return list ;
	    
	}
	
	public Vector getWithWhere( String whereClause ) throws Exception {
	    return getWithWhere( whereClause , "" , "") ;
	}
	
	public Vector executeScalarQuery( String sql ) throws Exception {
	    
	    ResultSet rs = null ;
	    
	    rs = db.executeQuery( sql ) ;
	    
	    Vector list = new Vector() ;
	    
	    if (rs != null )
	        while( rs.next() ) 
	            list.add( rs.getObject(1) );
	    
	    return list ;
	    
	}
	
	

	

	public ISQLAdapter() {
	   db = new HsqlDB() ;
	}
	public ISQLAdapter( IDatabase d ) {
	    db = d ;
	}
			
	public String getFieldsList(boolean noKeys ) {
	    return getFieldsList( getFields( db.getDbType() , false ) , noKeys ) ;
	}
	
	public String getFieldsList( HashMap fields , boolean noKeys ) {
		
	    StringBuilder list = new StringBuilder();

	    Object[] keyset = (Object[]) fields.keySet().toArray() ;
	    	
	    DbValue v = null ;
	    int i = 0 ;
		for ( ; i < keyset.length ; i++ ) {
		    v = (DbValue) fields.get("" + keyset[i]) ;
		    
		    if ( v != null && !( noKeys & v.isKey() ) ) {
		        if ( i > 0 ) list.append( FIELDSEPARATOR ) ; 
		        list.append( v.getFieldName() ) ;
		    }
		}
		
		return list.toString();
	}
	
	public String getFieldsValues( boolean noKeys ) {
	    return getFieldsValues( getFields( db.getDbType() , false ) , noKeys ) ;
	}
	
	public String getFieldsValues( HashMap fields , boolean noKeys ) {
	    
	    StringBuilder list = new StringBuilder();

	    Object[] keyset = (Object[]) fields.keySet().toArray() ;
	    	
	    DbValue v = null ;
	    int i = 0 ;
		for ( ; i < keyset.length ; i++ ) {
		    v = (DbValue) fields.get("" + keyset[i]) ;
		    
		    if ( v != null && !( noKeys & v.isKey() ) ) {
		        if ( i > 0 ) list.append( FIELDSEPARATOR ) ; 
		        list.append( v.toString() ) ;
		    }
		}

		return list.toString();
	}
	
	public void setFields( HashMap fields ) {
        this.fields = fields ;
    }
	
	public String getTable() {
        return (table) ;
    }
    public void setTable( String t ) {
        table = t ;
    }

    public void setFieldsFormRS(ResultSet rs ) throws Exception { 
        
        fields = getFields( db.getDbType() , true ) ;
        
        Set theKeys = fields.keySet();
       	Iterator iter = theKeys.iterator();

       	Object obj = null;
       	DbValue field = null;
       	
       	while(iter.hasNext())
       	{
       	    obj = iter.next();
       	    field = (DbValue) fields.get(obj);
       	    
       	    if ( field.getType() == DbValue.DATE ) {
       	        String x = rs.getString( field.getFieldName() );
       	        field.setValue( x ) ;
       	    }
       	    else {
       	        try {
       	            String val = "" + rs.getObject( field.getFieldName() ) ;
                    
                   
                    if ( rs.wasNull()) {
                    	 field.setWasNull( true );
                    }
                    else {
                    	field.setWasNull( false );
                        field.setValue( val ) ;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
       	    }
       	}
    }
    
    public Object getFromRS( ResultSet rs ) throws Exception {
        fields = getFields( db.getDbType() , true ) ;
        
        setFieldsFormRS(rs);
        
        return getFromFields() ;
    }
    protected void delete(Vector keys) throws Exception {
    
        StringBuilder sql = new StringBuilder();
	    
	    sql.append("DELETE FROM ") ; // sql.append( getFieldsList() );
	    
	    sql.append( getTable() ); 
	    
	    sql.append(" WHERE " );
	    
	    if ( keys != null && keys.size() > 0 ) {
	       DbValue d = null ;
	       for ( Iterator i = keys.iterator() ; i.hasNext() ; ) {
	           d = (DbValue) i.next();
	           sql.append( d.getFieldName() ) ;
	           sql.append( " = ") ;
	           sql.append( d.toString() ) ;
	       }
	       
	       db.executeNonQuery(sql.toString()) ;
	    }
	    
    }
    
    public boolean checkDB( IDatabase d ) {
        return ( this.db.getDbType() == d.getDbType() ) ;
    }
    /*
     * 
    public Object get( Object o ) throws Exception {
        return get( getKey(o) ); 
    }
    public DbValue getKey( Object o ) throws Exception {
            return new DbValue( "id" , Integer.valueOf( ((Carico) o).getId() ), DbValue.INTEGER) ;
    }
    */
    
}
