/*
 * Created on 2-lug-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gtsoft.rifiuti.data;

import java.util.HashMap;
import java.util.Vector;

import com.gtsoft.rifiuti.data.sql.SQLVettore;
import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.rifiuti.front.data.AbstractSwg;
import com.gtsoft.rifiuti.front.data.SwgVettore;
import com.gtsoft.utils.superdata.IDatabase;

/**
 * @author Gtron
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Vettore extends Soggetto {

    public static Vector getAll( IDatabase db ) {
        try {
          return new SQLVettore(db).getAll() ;
      } catch (Exception e) { 
          e.printStackTrace();
          return null ;
      }
    }
    
    public static Vector getUsedInMovimenti( IDatabase db ) {
        try {
            Vector v = new SQLVettore(db).getUsedInMovimenti() ;
            v.insertElementAt("",0);
          return v;
      } catch (Exception e) { 
          e.printStackTrace();
          return null ;
      }
    }
    
    public void create( IDatabase db ) throws Exception {
        SQLVettore sql = new SQLVettore(db);
        sql.syncFields(this) ;
        sql.create() ;
    }
    
    private static HashMap cacheCodice = new HashMap() ;
    public static Vettore getByCodice( String codice ) {
        if ( ! cacheCodice.containsKey(codice.toUpperCase()) ) {
            SQLVettore sql = new SQLVettore() ;
            Object o = sql.getByCodice( codice );
            if ( o == null )
                o = sql.getByCodice( codice.toUpperCase() );
            cacheCodice.put(codice.toUpperCase(), o) ;
            return (Vettore) o ;
        }
        return (Vettore) cacheCodice.get(codice.toUpperCase()) ; 
    }
    
    private static HashMap cacheId = new HashMap() ;
    public static Vettore getById(String idTrasportatore) {
        if ( ! cacheId.containsKey(idTrasportatore) ) {
            Object o = new SQLVettore().getById( idTrasportatore );
            cacheId.put(idTrasportatore, o) ;
            return (Vettore) o ;
        }
        return (Vettore) cacheId.get(idTrasportatore) ; 
    }
    
    public AbstractSwg getSwg(RifiutiFrame frame) throws Exception{
        if(swg==null)
            swg=new SwgVettore(frame);
        swg.setSoggetto(this);
        return swg;
    }
}
