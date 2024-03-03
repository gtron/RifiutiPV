/*
 * Creato il 14-set-2005
 *
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti_new
 * 
 */
package com.gtsoft.utils.filter;

import javax.swing.JButton;
import javax.swing.JMenuItem;

import com.gtsoft.rifiuti.front.JFiltroPanel;
import com.gtsoft.utils.filter.superdata.IFilter;


public abstract class AbstractMatchFilter implements IFilter{

    protected Object comparator;
    private String descrizione;
    private JButton jButton;
    private JFiltroPanel jFiltroPanel;
	private JMenuItem jMenuItem;
    
  
    public abstract boolean check(Object obj);

    public void setComparator(Object obj) {
       this.comparator=obj;
        
    }

    public void setJFiltroPanel(JFiltroPanel filtroPanel) throws Exception {
        jFiltroPanel = filtroPanel;
    }
    
    public JFiltroPanel getJFilterPanel() {
        return jFiltroPanel;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public JButton getButton() {
        if(jButton==null)
            jButton=new JButton(getDescrizione());
        return jButton;
    }
    
    public JMenuItem getMenuItem(){
    	if(jMenuItem==null)
    		jMenuItem=new JMenuItem("filtra per...");
    	return jMenuItem;     	
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    
    public void setEnableItems(boolean enable){
    		
    		if(getMenuItem()!=null)
    			getMenuItem().setEnabled(enable);
    		
    		if(getButton()!=null)
    			getButton().setEnabled(enable);
    }
}
