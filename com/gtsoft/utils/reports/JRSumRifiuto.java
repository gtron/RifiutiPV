/*
 * Created on 5-set-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.utils.reports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import com.gtsoft.rifiuti.data.Movimento;
import com.gtsoft.rifiuti.data.Rifiuto;

/**
 * @author Gtron - 
 * 
 */
public class JRSumRifiuto implements JRDataSource {
    
    Iterator i = null ;
    
    HashMap list = null ;
    ArrayList orderedKeys = null ;
    
    HashMap current = null ;
    
    /**
     * Prende un vettore di movimenti e restituisce un hasmap 
     * con le somme dei pesi di giacenza e trattato per rifiuto
     */
    public JRSumRifiuto( Vector l ) throws Exception {
        super();
        
        HashMap listCod = new HashMap() ;
        list = new HashMap() ;
        HashMap values = null; 
        
        Rifiuto r = null ;
        Movimento m = null ;
        Double cTrattato, cCaricato ;
        
        for ( Iterator i = l.iterator() ; i.hasNext() ; ) {
            m = (Movimento) i.next() ;
            r = m.getMerce() ;
            
            if ( listCod.containsKey(r.getCodiceQuadrelli())) {
                values = (HashMap) listCod.get( r.getCodiceQuadrelli() );
                cCaricato = (Double) values.get(Fields.CARICATO );
                cTrattato = (Double) values.get(Fields.TRATTATO );
                
                if ( cCaricato == null ) {
                    cCaricato = new Double(0) ;
                }
                if ( cTrattato == null ) {
                    cTrattato = new Double(0) ;
                }
            } 
            else {
                values = new HashMap() ;
              
                cTrattato = new Double(0) ;
                cCaricato = new Double(0) ;
                
                values.put(Fields.RIFIUTO , r );
                
                listCod.put(r.getCodiceQuadrelli(), values) ;
                list.put(r.getNome(), values) ;
            }
            
            if ( m.isScarico() || m.isParziale() ) {
                cTrattato = new Double( cTrattato.doubleValue() + m.getPeso().doubleValue() ) ;
            }
            else {
                cCaricato = new Double( cCaricato.doubleValue() + m.getPeso().doubleValue() ) ;
            }
            
            values.put(Fields.CARICATO, cCaricato ) ;
            values.put(Fields.TRATTATO, cTrattato ) ;
        }
        
        for ( Iterator iRifiuti = list.values().iterator() ; iRifiuti.hasNext() ; ) {
            
            values = (HashMap) iRifiuti.next() ;
            cCaricato = (Double) values.get(Fields.CARICATO );
            cTrattato = (Double) values.get(Fields.TRATTATO );
            
            if ( cCaricato == null ) {
                cCaricato = new Double(0) ;
            }
            if ( cTrattato == null ) {
                cTrattato = new Double(0) ;
            }
            
            /*
            double sum = cCaricato.doubleValue() + cTrattato.doubleValue() ;
            
            if ( sum > 0 )
                values.put(Fields.GIACENZA, new Double(cCaricato.doubleValue() - cTrattato.doubleValue()) ) ;
            else
	            iRifiuti.remove() ;
	            */
            values.put(Fields.GIACENZA, new Double(cCaricato.doubleValue() - cTrattato.doubleValue()) ) ;
        }
        
        // sorting
        Set keys = list.keySet() ;
        orderedKeys = new ArrayList();
        orderedKeys.addAll( keys ) ;
        
        Collections.sort(orderedKeys);
        
        
        this.i = orderedKeys.iterator();
    }  

    /* (non-Javadoc)
     * @see net.sf.jasperreports.engine.JRDataSource#next()
     */
    public boolean next() throws JRException {
        if ( i.hasNext() ) {
            current = (HashMap) list.get( i.next() );
            return true ;
        }
        return false ;
    } 

    /* (non-Javadoc)
     * @see net.sf.jasperreports.engine.JRDataSource#getFieldValue(net.sf.jasperreports.engine.JRField)
     */
    public Object getFieldValue(JRField f) throws JRException {
                 
        try {
            
            if( f.getName().equals(Fields.RIFIUTO))
	            return current.get(Fields.RIFIUTO) ;
            if( f.getName().equals(Fields.CARICATO))
	            return current.get(Fields.CARICATO) ;
            if( f.getName().equals(Fields.TRATTATO))
	            return current.get(Fields.TRATTATO) ;
            if( f.getName().equals(Fields.GIACENZA))
	            return current.get(Fields.GIACENZA) ;
	            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "N/A" ;
    }
    
    private static interface Fields {
        static final String RIFIUTO = "Rifiuto" ;
        //static final String CER = "CER" ;
        //static final String NOME = "NOME" ;
        static final String CARICATO = "Caricato" ;
        static final String TRATTATO = "Trattato" ;
        static final String GIACENZA = "Giacenza" ;
    }
}
