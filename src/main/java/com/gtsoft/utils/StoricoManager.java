/*
 * Created on 1-giu-2006
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.gtsoft.rifiuti.data.Movimento;
import com.gtsoft.rifiuti.data.StoricoMovimenti;
import com.gtsoft.utils.superdata.IDatabase;

/**
 * @author Gtron - 
 * 
 */
public class StoricoManager {
    
    public static void updateStorico(IDatabase db ) throws Exception {
        
        //StoricoMovimenti last = StoricoMovimenti.getLast(db) ;
    	StoricoMovimenti.flush(db) ;
        
        FormattedDate firstDay = null ;
        FormattedDate lastDay = null ;
                
        Vector lastDays = StoricoMovimenti.getLastStorici(db) ;
       
        if ( lastDays == null )
            lastDays = new Vector();

        HashMap storiciPrecedenti = new HashMap(lastDays.size()) ;
        StoricoMovimenti s = null ;
        for ( Iterator i = lastDays.iterator() ; i.hasNext() ; ) {
            s = (StoricoMovimenti) i.next() ;
            storiciPrecedenti.put(s.getIdMerce() , s) ;
        }
        
        Vector movimenti = Movimento.get( firstDay, null, db) ;
        
        HashMap storico = buildStorico( movimenti, storiciPrecedenti );
        store( storico , db );
    }
    
    
    public static HashMap buildStorico( Vector list , HashMap storiciPrecedenti ) throws Exception {
        
        HashMap giorni = new HashMap() ;
        
        Movimento cMov = null ;
        FormattedDate cDay = null ;

        HashMap rifiuti = null ;
        Integer codQuad = null ;
        
        StoricoMovimenti cStorico = null ;
        StoricoMovimenti cGiacenza = null ;
        StoricoMovimenti cStoricoTotale = null ;
        StoricoMovimenti cGiacenzaTotale = null ;
        
        if ( storiciPrecedenti == null ) {
            storiciPrecedenti = new HashMap() ;
        }
       
        for ( Iterator i = list.iterator() ; i.hasNext() ; ) {
            cMov = (Movimento) i.next() ;
            codQuad = Integer.valueOf( cMov.getMerce().getCodiceQuadrelli() );
            
            // trovo l'hash del giorno
            if ( cDay == null || !cDay.equals(cMov.getData()) ) {

                cDay = cMov.getData() ;
                
                if ( giorni.containsKey(cDay.dmyString())) {
                    rifiuti = (HashMap) giorni.get(cDay.dmyString()) ;
                }
                else {
                    rifiuti = new HashMap() ;
                    giorni.put(cDay.dmyString(), rifiuti) ;
                }
            }
            
            // trovo lo storico del rifiuto
            if ( rifiuti.containsKey( codQuad ) ) {
                cStorico = (StoricoMovimenti) rifiuti.get( codQuad ); 
            }
            else {
                cStorico = new StoricoMovimenti() ;
                cStorico.setGiorno(cDay);
                cStorico.setIdMerce(codQuad) ;
                
                rifiuti.put( codQuad , cStorico);
            }
            

            // trovo la sommaGiornaliera
            if ( rifiuti.containsKey( "0" ) ) {
            	cStoricoTotale = (StoricoMovimenti) rifiuti.get( "0" ); 
            }
            else {
            	cStoricoTotale = new StoricoMovimenti() ;
            	cStoricoTotale.setIdMerce(Integer.valueOf(0)) ;
            	cStoricoTotale.setGiorno(cDay);
            	
            	rifiuti.put( "0" , cStoricoTotale);
            }
            
            
            // trovo la giacenza del rifiuto
            if ( storiciPrecedenti.containsKey( codQuad ) ) {
                cGiacenza = (StoricoMovimenti) storiciPrecedenti.get( codQuad ); 
            }
            else {
                cGiacenza = new StoricoMovimenti() ;
                storiciPrecedenti.put( codQuad , cGiacenza);
            }
            
//          trovo la giacenza totale
            if ( storiciPrecedenti.containsKey( "0" ) ) {
                cGiacenzaTotale = (StoricoMovimenti) storiciPrecedenti.get( "0" ); 
            }
            else {
            	cGiacenzaTotale = new StoricoMovimenti() ;
                storiciPrecedenti.put( "0" , cGiacenzaTotale);
            }
            
            
            if ( cMov.isScarico() || cMov.isParziale() ) {
                cStorico.addScarico(cMov.getPeso()) ;
                cStoricoTotale.addScarico(cMov.getPeso()) ;
                cGiacenza.addGiacenza( -1 * cMov.getPeso().intValue() ) ;
                
                cGiacenzaTotale.addGiacenza( -1 * cMov.getPeso().intValue() ) ;
            }
            else {
                cStorico.addCarico(cMov.getPeso()) ;
                cGiacenza.addGiacenza(cMov.getPeso().intValue()) ;
                cGiacenza.addCarico(cMov.getPeso()) ;
                
                cStoricoTotale.addCarico(cMov.getPeso()) ;
                
                cGiacenzaTotale.addGiacenza(cMov.getPeso().intValue()) ;
                cGiacenzaTotale.addCarico(cMov.getPeso()) ;
            }
            
            cStorico.setGiacenza(cGiacenza.getGiacenza()) ;
            cStorico.setTotCaricato(cGiacenza.getCaricato()) ;
            
            cStoricoTotale.setGiacenza(cGiacenzaTotale.getGiacenza()) ;
            cStoricoTotale.setTotCaricato(cGiacenzaTotale.getCaricato()) ;
        }
        
        return giorni ;
        
    }
    
    
    private static void store( HashMap storico, IDatabase db ) throws Exception { 
        HashMap rifiuti = null ;
        for ( Iterator i = storico.values().iterator() ; i.hasNext() ;) {
            rifiuti = (HashMap) i.next();
            
            for ( Iterator r = rifiuti.values().iterator() ; r.hasNext() ;) {
                ((StoricoMovimenti) r.next()).create(db);
            }
        }
    }
}
