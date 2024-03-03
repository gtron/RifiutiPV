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

import com.gtsoft.rifiuti.data.Rifiuto;

/**
 * @author Gtron - 
 * 
 */
public class JRRifiuto implements JRDataSource {
    
    Vector list = null ;
    Iterator i = null ;
    Rifiuto current = null ;
    
    /**
     * 
     */
    public JRRifiuto( Vector l ) {
        super();
        
        if ( l != null )
            this.list = l ;
        else {
            this.list = new Vector() ;
        
	        
	        for(int i=0 ; i<100 ; i++ ) {
	            current = new Rifiuto() ;
	            this.list.add( current )  ;
	        }
        }
        
        
        this.i = this.list.iterator();
    }
    
    public JRRifiuto( int numDa , int num ) {
        super();
        
        this.list = new Vector() ;
        
        for(int to = numDa + num ; numDa < to ; numDa++ ) {
            current = new Rifiuto() ;
            this.list.add( current )  ;
        }
        
        this.i = this.list.iterator();
    }
    

    /* (non-Javadoc)
     * @see net.sf.jasperreports.engine.JRDataSource#next()
     */
    public boolean next() throws JRException {
        if ( i.hasNext() ) {
            current = (Rifiuto) i.next();
            return true ;
        }
        return false ;
    } 

    /* (non-Javadoc)
     * @see net.sf.jasperreports.engine.JRDataSource#getFieldValue(net.sf.jasperreports.engine.JRField)
     */
    public Object getFieldValue(JRField f) throws JRException {
                 
        if( f.getName().equals("NomeRifiuto"))
            return "" + current.getNome();
        try {
            
//            DecimalFormat df = new DecimalFormat("#0");
            
	        if( f.getName().equals("CER"))
	            return current.getCer().getNome() + " (" + current.getCer().getCodice() + ")" ;
	        if( f.getName().equals("CODICECER"))
	            return current.getCer().getCodice() ;
	        if( f.getName().equals("StatoFisico"))
	            return "" ; // current.getMerce().getStato().getDescrizione() ;
	        if( f.getName().equals("Pericolosita"))
	            return current.getListaClassiPericolosita() ;
	        if( f.getName().equals("CodQuadrelli"))
	            return current.getCodiceQuadrelli() ;
	        if( f.getName().equals("Giacenza"))
	            return current.getGiacenzaDouble();
	        if(f.getName().equals("Unita"))
	        	return current.getUnita();
	        if(f.getName().equals("Trattato"))
	        	return current.getLavoratoDouble(); 
	        if(f.getName().equals("Caricato")) {
	            return new Double(current.getLavoratoDouble().doubleValue() + current.getGiacenzaDouble().doubleValue()) ;
	        }
	            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return current ;
    }
    

}
