/*
 * Created on 2-lug-2005
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gtsoft.rifiuti.data;

import java.util.HashMap;
import java.util.Vector;

import com.gtsoft.rifiuti.data.sql.SQLCliente;
import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.rifiuti.front.data.AbstractSwg;
import com.gtsoft.rifiuti.front.data.SwgCliente;
import com.gtsoft.utils.superdata.IDatabase;

/**
 * @author Gtron
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Cliente extends Soggetto {
   
    public static Vector getAll( IDatabase db ) {
        try {
          return new SQLCliente(db).getAll() ;
      } catch (Exception e) { 
          e.printStackTrace();
          return null ;
      }
    }
    
    public void create( IDatabase db ) throws Exception {
        SQLCliente sql = new SQLCliente(db);
        sql.syncFields(this) ;
        sql.create() ;
    }

    
    private static HashMap cacheCodice = new HashMap() ;
    public static Cliente getByCodice( String codice ) {
        if ( ! cacheCodice.containsKey(codice) ) {
           Object o = new SQLCliente().getByCodice( codice );
            cacheCodice.put(codice, o) ;
            return (Cliente) o ;
        }
        return (Cliente) cacheCodice.get(codice) ; 
    }
    
    
    public AbstractSwg getSwg(RifiutiFrame frame) throws Exception{
        if(swg==null)
            swg=new SwgCliente(frame);
        swg.setOggettoConcreto(this);
        return swg;
    }
    
}
