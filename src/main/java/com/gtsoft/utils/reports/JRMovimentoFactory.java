/*
 * Created on 5-set-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.utils.reports;

import java.util.Vector;

import net.sf.jasperreports.engine.JRDataSource;

/**
 * @author Gtron - 
 * 
 */
public class JRMovimentoFactory {
    
    public JRDataSource getTheDatasource( )
    {
        try {
            return new JRMovimento( null ) ;// Movimento.getAll( new HsqlDB() ) );
        } catch (Exception e) {
            e.printStackTrace();
            return null ;
        }
    }
    
    public JRDataSource getPageNumbers( int from , int num )
    {
        try {
            return new JRMovimento( from , num ) ;// Movimento.getAll( new HsqlDB() ) );
        } catch (Exception e) {
            e.printStackTrace();
            return null ;
        }
    }
    
    public JRDataSource getMovimenti( Vector list )
    {
        try {
            return new JRMovimento( list ) ;// Movimento.getAll( new HsqlDB() ) );
        } catch (Exception e) {
            e.printStackTrace();
            return null ;
        }
    }
}
