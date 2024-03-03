/*
 * Created on 20-lug-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.data;


/**
 * @author Gtron - 
 * 
 */
public class Destinazione {
    
    private int tipo ; 
    
    public Destinazione( String d ) {
        try {
            tipo = Integer.parseInt(d) ;
        }
        catch ( Exception e ) {
            tipo = 0 ;
        }
    }
    
    public Destinazione( int d ) {
        tipo = d ;
    }
    
    public int get() { 
        return tipo ;
    }
    
    public String toString() {
        return Cost.CODICE[tipo];
    }

    private static class Cost {
        private final static String[] CODICE = { "" ,
                "RICEVIMENTI M.PRIME VIA NAVE",
                "SPEDIZIONE PRODOTTI" , 
                "MOVIMENTAZIONI INTERNE",
                "TRASPORTI  A/DA DISCARICHE", 
                "RICEV. M.PRIME VIA CAMION",
                "RICEVIM. MATER. MAG. SCORTE",
                "INVENTARIO INIZIALE",
                "RETTIFICHE" };
                
        }
}
