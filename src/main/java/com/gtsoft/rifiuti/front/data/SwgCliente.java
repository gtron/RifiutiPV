/*
 * Created on 2-lug-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gtsoft.rifiuti.front.data;

import java.util.Vector;

import com.gtsoft.rifiuti.data.Cliente;
import com.gtsoft.rifiuti.front.RifiutiFrame;

/**
 * @author Gtron
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SwgCliente extends SwgSoggetto {

    public SwgCliente(RifiutiFrame frame) throws Exception {
        super(frame);
    }
    
    public Vector getElenco_for_database() throws Exception {
        if ( elementi == null )
            elementi =  Cliente.getAll(frame.getDatabase());
        return elementi;
    }
}