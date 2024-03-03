/*
 * Created on 6-lug-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.superdata;

import java.util.Vector;

import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.rifiuti.front.data.AbstractSwg;

/**
 * @author Gtron - 
 * 
 */
public interface IListable {
    
    /**
     * @return Array dei nomi delle colonne da visualizzare nella JTable 
     */
    public String[] getColumns() ;
    
    /**
     * @return Array dei valori da visualizzare nella JTable 
     * @throws Exception
     */
    public String[] getValues() throws Exception ;
    public Vector getVValues() throws Exception ;
    
    public AbstractSwg getSwg(RifiutiFrame frame) throws Exception;

}
