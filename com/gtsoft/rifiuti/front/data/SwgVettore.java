/*
 * Creato il 8-lug-2005
 * 
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti
 *  
 */
package com.gtsoft.rifiuti.front.data;

import java.util.Vector;

import com.gtsoft.rifiuti.data.Vettore;
import com.gtsoft.rifiuti.front.RifiutiFrame;

public class SwgVettore extends SwgSoggetto {

    public SwgVettore(RifiutiFrame frame) throws Exception {
        super(frame);
    }
    
    public Vector getElenco_for_database() throws Exception {
        elementi =  Vettore.getAll(frame.getDatabase());
        return elementi;
    }
    
}