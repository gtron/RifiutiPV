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
public class JRRifiutoFactory {
    
    public JRDataSource getTheDatasource( )
    {
        try {
            return new JRRifiuto( null ) ;// Movimento.getAll( new HsqlDB() ) );
        } catch (Exception e) { 
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null ;
        }
    }
    
    public JRDataSource getPageNumbers( int from , int num )
    {
        try {
            return new JRRifiuto( from , num ) ;// Movimento.getAll( new HsqlDB() ) );
        } catch (Exception e) { 
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null ;
        }
    }
    
    public JRDataSource getRifiuti( Vector list )
    {
        try {
            return new JRRifiuto( list ) ;// Movimento.getAll( new HsqlDB() ) );
        } catch (Exception e) { 
            e.printStackTrace();
            return null ;
        }
    }
    public JRDataSource getSumRifiuti( Vector list )
    {
        try {
            return new JRSumRifiuto ( list ) ;
        } catch (Exception e) { 
            e.printStackTrace();
            return null ;
        }
    }
    public JRDataSource getSumTrasportatore( Vector list )
    {
        try {
            return new JRSumTrasportatore ( list ) ;
        } catch (Exception e) { 
            e.printStackTrace();
            return null ;
        }
    }
}
