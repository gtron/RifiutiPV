/*
 * Created on 5-ott-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.utils.config;

import java.util.Vector;

import com.gtsoft.utils.config.sql.SQLConfig;

/**
 * @author Gtron - 
 * 
 */
public class Config {
    
    private String key ;
    private String value ;

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public static Config getPassword() {
        
        try {
	        Vector l = new SQLConfig().get(SQLConfig.getKey("user")) ;
	        
	        if ( l != null && l.size() > 0 ) {
	            return (Config) l.firstElement();
	        }
        }
        catch (Exception e) {
            e.printStackTrace() ;
        }
        
        return null ;
    }
    
    /**
     * @param key Chiave per la ricerca nel Database
     * @return Oggetto Config contentente la coppia chiave/valore cercata
     */
    public static Config get( String key ) {
        
        try {
	        Vector l = new SQLConfig().get(SQLConfig.getKey(key)) ;
	        
	        if ( l != null && l.size() > 0 ) {
	            return (Config) l.firstElement();
	        }
        }
        catch (Exception e) {
            e.printStackTrace() ;
        }
        
        return null ;
    }

    public void create() throws Exception {
        SQLConfig conf = new SQLConfig();
        conf.syncFields(this);
        conf.delete();
        conf.create();
    }
    
}
