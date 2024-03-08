/*
 * Created on 4-lug-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.data.sql;

import com.gtsoft.rifiuti.data.Fornitore;
import com.gtsoft.utils.superdata.IDatabase;

/**
 * @author Gtron - 
 * 
 */
public class SQLFornitore extends SQLSoggetto {

    private static final String TABLE = "Fornitori" ; 
    
    public SQLFornitore( IDatabase db ) {
        super(db) ;
        table = getTable() ;
    }
    
    public SQLFornitore() {
        super() ;
        table = getTable() ;
    }
    
    public Object getFromFields() {
        return getFromFields( new Fornitore() ) ;
    }
    
    public String getTable() {
        return TABLE ;
    }
    
 
}