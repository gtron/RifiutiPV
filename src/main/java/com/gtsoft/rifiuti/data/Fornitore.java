/*
 * Created on 2-lug-2005
 *
 */
package com.gtsoft.rifiuti.data;

import java.util.HashMap;
import java.util.Vector;

import com.gtsoft.rifiuti.data.sql.SQLFornitore;
import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.rifiuti.front.data.AbstractSwg;
import com.gtsoft.rifiuti.front.data.SwgFornitore;
import com.gtsoft.utils.superdata.IDatabase;

/**
 * @author Gtron
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Fornitore extends Soggetto {
    
    public static Vector<?> getAll( IDatabase db ) {
        try {
          return new SQLFornitore(db).getAll() ;
      } catch (Exception e) { 
          e.printStackTrace();
          return null ;
      }
    }
    
    public void create( IDatabase db ) throws Exception {
        SQLFornitore sql = new SQLFornitore(db);
        sql.syncFields(this) ;
        sql.create() ;
    }
    
    private static HashMap<String, Object> cacheCodice = new HashMap<String, Object>() ;
    public static Fornitore getByCodice( String codice ) {
        if ( ! cacheCodice.containsKey(codice) ) {
            Vettore c = new Vettore() ;
            c.setCodice( codice ) ;
            Object o = new SQLFornitore().getByCodice( codice );
            cacheCodice.put(codice, o) ;
            return (Fornitore) o ;
        }
        return (Fornitore) cacheCodice.get(codice) ; 
    }
    
    public AbstractSwg getSwg(RifiutiFrame frame) throws Exception{
        if(swg==null)
            swg=new SwgFornitore(frame);
        swg.setSoggetto(this);
        return swg;
    }
}
