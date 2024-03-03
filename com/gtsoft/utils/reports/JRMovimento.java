/*
 * Created on 5-set-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.utils.reports;

import java.util.Iterator;
import java.util.Vector;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import com.gtsoft.rifiuti.data.Carico;
import com.gtsoft.rifiuti.data.Movimento;

/**
 * @author Gtron - 
 * 
 */
public class JRMovimento implements JRDataSource {
    
    Vector list = null ;
    Iterator i = null ;
    Movimento current = null ;
    
    /**
     * 
     */
    public JRMovimento( Vector l ) {
        super();
        
        if ( l != null )
            this.list = l ;
        else {
            this.list = new Vector() ;
        
	        
	        for(int i=0 ; i<100 ; i++ ) {
	            current = new Movimento() ;
	            current.setAnno( i ) ;
	            this.list.add( current )  ;
	        }
        }
        
        
        this.i = this.list.iterator();
    }
    
    public JRMovimento( int numDa , int num ) {
        super();
        
        this.list = new Vector() ;
        
        for(int to = numDa + num ; numDa < to ; numDa++ ) {
            current = new Movimento() ;
            current.setAnno( numDa ) ;
            this.list.add( current )  ;
        }
        
        this.i = this.list.iterator();
    }
    

    /* (non-Javadoc)
     * @see net.sf.jasperreports.engine.JRDataSource#next()
     */
    public boolean next() throws JRException {
        if ( i.hasNext() ) {
            current = (Movimento) i.next();
            return true ;
        }
        return false ;
    } 

    /* (non-Javadoc)
     * @see net.sf.jasperreports.engine.JRDataSource#getFieldValue(net.sf.jasperreports.engine.JRField)
     */
    public Object getFieldValue(JRField f) throws JRException {
        
        Carico c = current.getCarico(); 
        
        if( f.getName().equals("Anno"))
            return "" + current.getAnno();
        if( f.getName().equals("Numero"))
            return "" + current.getNumProgressivo();
        if( f.getName().equals("Data"))
            return "" + current.getData().dmyString();
        if( f.getName().equals("CaricoScarico")) {
            if ( current.isScarico() || current.isParziale() )
                return "Scarico";
            else 
                return "Carico" ;
        }
        if( f.getName().equals("Formulario")) {
            
            if ( ! current.isScarico() ) {
	            if ( c != null && c.getDocumento() != null )
	                return c.getDocumento() ;
	            else if ( current.getCaricoParziale() != null )
	                return current.getCaricoParziale().getDocumento() ;
	            else
	                return "-";
            }
        }
        if( f.getName().equals("Carichi")) {
            try {
                return "" + current.getNumeriProgressiviScaricati() ;
            } catch (Exception e1) {
                e1.printStackTrace();
                return "-" ;
            }
        }
        if( f.getName().equals("NomeRifiuto")) {
            try {
                return "" + current.getMerce().getNome();
            } catch (Exception e) {
                return "-" ;
            }   
        }
        
        if( f.getName().equals("Peso"))
            return ( "" + current.getPesoTotale() ).replaceAll("\\.0" , "");
        
        try {
            if( f.getName().equals("Unita"))
                return current.getMerce().getUnita() ;
	        if( f.getName().equals("CER"))
	            return current.getMerce().getCer().getNome() + " (" + current.getMerce().getCer().getCodice() + ")" ;
	        if( f.getName().equals("StatoFisico"))
	            return current.getMerce().getStato().getDescrizione() ;
	        if( f.getName().equals("Pericolosita"))
	            return current.getMerce().getListaClassiPericolosita() ;
	        if( f.getName().equals("Destinazione")) {
	            if ( c != null ) {
	                return "" + c.getDestinazione().toString() ; // + " (" + current.getMerce().getCodiceRecupero() + ")" ;
	            }
	            else return "";
	        }
	            
        }
        catch (Exception e) {
            
        }
        
        if( f.getName().equals("Fornitore") ) {
            //StringBuilder str = new StringBuilder();
            //str.append( "Luogo di produzione e attività di provenienza del rifiuto:\n") ;
            //str.append( c.getCliente().getRagioneSociale() );
            
            //return str.toString() ;
            return c.getRifiuto().getDescrizioneProduttore() ;
        }
        
        if( f.getName().equals("Trasportatore") && c.getVettore() != null ) {
            StringBuilder str = new StringBuilder();
            //str.append( "Luogo di produzione e attività di provenienza del rifiuto:\n") ;
            str.append( c.getVettore().getRagioneSociale() );
            
            return str.toString() ;
        }
        
        if( f.getName().equals("Note")) {
            try {
	            String d = c.getMerce().getNome() ;
	            
	            if ( d != null )  
	                return "Note : \n" + d ;
	            else 
	                return "" ;
            }
            catch (Exception e) {
                return "-" ;
            }
        }
        
        if (  f.getName().equals("M")) {
            return current ;
        }
        
        return "N/A" ;
    }
    

}
