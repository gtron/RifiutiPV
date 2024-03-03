/*
 * Created on 6-lug-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.superdata;

import java.util.HashMap;

/**
 * @author Gtron - 
 * 
 */
public interface IAccessDBEnabled {
     
    HashMap getAccessDBFields() ;
    
    String getAccessDBTable();
}
