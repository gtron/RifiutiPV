 /*
 * Created on 4-lug-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.data.sql;

import com.gtsoft.rifiuti.data.Cliente;
import com.gtsoft.utils.superdata.IDatabase;

/**
 * @author Gtron - 
 * 
 */
public class SQLCliente extends SQLSoggetto  {

    private static final String TABLE = "Clienti" ; 
    
    public SQLCliente( IDatabase db ) {
        super(db) ;
        table = getTable() ;
    }
    
    public SQLCliente() {
        super() ;
        table = getTable() ;
    }
    
    public Object getFromFields() {
        return getFromFields( new Cliente() ) ;
    }
    
    public String getTable() {
        return TABLE ;
    }
    
}

