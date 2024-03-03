/*
 * Creato il 27-ago-2005
 *
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti_new
 * 
 */
package com.gtsoft.utils.filter;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;

import com.gtsoft.rifiuti.front.Date_JFiltroPanel;
import com.gtsoft.rifiuti.front.JFiltroPanel;


public abstract  class AbstractDataFilter extends AbstractMatchFilter{

    protected static SimpleDateFormat dayFormatter = new SimpleDateFormat("ddMMyyyy") ;
    
    protected Date da;
    protected Date a; 

    protected JButton button;
    
    public void setA(Date a) {
        this.a = a;
    }
    
    public void setDa(Date da) {
        this.da = da;
    }
    
    public void setJFiltroPanel(JFiltroPanel df) throws Exception {
        if (df instanceof Date_JFiltroPanel) {
            super.setJFiltroPanel(df);
            return;
        }
        else throw new Exception();
    }
    
    public boolean check( Object d ) {
        return dataCheck((Date) d) ;
    }
    public boolean dataCheck( Date d ) {
        
        String data = dayFormatter.format(d);
        try {
            if( data.equals( dayFormatter.format(da) ) || data.equals(dayFormatter.format(a)) ) 
                return true;
            if ( d.after(da) && d.before(a)) 
                return true;
        }
        catch(Exception e) {}
        
        return false;
     }

}
