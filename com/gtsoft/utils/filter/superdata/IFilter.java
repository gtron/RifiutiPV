/* Creato il 27-ago-2005
 *
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti_new
 * 
 */
package com.gtsoft.utils.filter.superdata;

import javax.swing.JButton;
import javax.swing.JMenuItem;

import com.gtsoft.rifiuti.front.JFiltroPanel;


public interface IFilter {
    public boolean check(Object obj) throws Exception;
    public void setComparator(Object obj);
    public JFiltroPanel getJFilterPanel();
    public String getDescrizione();
    public JButton getButton();
    public JMenuItem getMenuItem();
    public void setEnableItems(boolean enable);
    
}
