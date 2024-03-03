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

import com.gtsoft.rifiuti.data.Fornitore;
import com.gtsoft.rifiuti.front.RifiutiFrame;

public class SwgFornitore extends SwgSoggetto {

    public SwgFornitore(RifiutiFrame frame) throws Exception {
        super(frame);
    }
    
    public Vector getElenco_for_database() throws Exception {
        elementi =  Fornitore.getAll(frame.getDatabase());
        return elementi;
    }
}