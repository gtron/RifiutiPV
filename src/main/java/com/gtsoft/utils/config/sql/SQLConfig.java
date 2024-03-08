/*
 * Created on 5-ott-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.utils.config.sql;

import java.util.HashMap;
import java.util.Vector;

import com.gtsoft.utils.DbValue;
import com.gtsoft.utils.config.Config;
import com.gtsoft.utils.superdata.ISQLAdapter;

/**
 * @author Gtron - 
 * 
 */
public class SQLConfig extends ISQLAdapter {

    private static final int fieldsCount = 2 ;
    private static final String TABLE = " CONFIG " ;
    
    public SQLConfig() {
        super() ;
        table = getTable();
    }
    /* (non-Javadoc)
     * @see com.gtsoft.utils.superdata.ISQLAdapter#getFromFields()
     */
    public Object getFromFields() {
        Config o = new Config();
        
        DbValue field =  (DbValue) fields.get("KEY") ; 
        if ( field != null && ! field.wasNull() ){
            o.setKey( ((String) field.getValue()) ) ;
        }
        
        field = (DbValue) fields.get("VALUE") ; 
        if ( field != null && ! field.wasNull() ){
            o.setValue( (String) field.getValue() ) ;
        }
        return o;
    }

    /* (non-Javadoc)
     * @see com.gtsoft.utils.superdata.ISQLAdapter#getFields(int)
     */
    public HashMap getFields(int dbType, boolean reload) {
        if ( reload || fields == null) {
            
            fields = new HashMap(fieldsCount);
            
           fields.put("KEY", new DbValue("KEY", DbValue.STRING));
           fields.put("VALUE", new DbValue("VALUE", DbValue.STRING));
        }
        
        return fields;
    }

    /* (non-Javadoc)
     * @see com.gtsoft.utils.superdata.ISQLAdapter#syncFields(java.lang.Object)
     */
    public void syncFields(Object obj) throws Exception {
        Config o = (Config) obj ;
        
        HashMap list = getFields( db.getDbType() , true );
        ((DbValue) list.get("KEY")).setValue( o.getKey() ) ;
        ((DbValue) list.get("VALUE")).setValue( o.getValue() ) ;
    }
    
    public static DbValue getKey(String k)  { 
        try {
            return new DbValue("KEY",k,DbValue.STRING) ;
        } catch (Exception e) {
            return null ;
        }
    }
    
    public String getTable() {
        return TABLE ;
    }
    /**
     * @throws Exception
     * 
     */
    public void delete() throws Exception {
        HashMap list = getFields( db.getDbType(), false );
        Vector v = new Vector() ;
        v.add( list.get("KEY") );
        
        delete(v);
    }
}
