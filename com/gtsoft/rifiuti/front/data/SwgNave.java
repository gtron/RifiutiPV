/*
 * Creato il 8-lug-2005
 * 
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti
 *  
 */
package com.gtsoft.rifiuti.front.data;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JDialog;

import com.gtsoft.rifiuti.data.Nave;
import com.gtsoft.rifiuti.front.JMainTable;
import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.rifiuti.superdata.IListable;
import com.gtsoft.utils.filter.FilterManager;
import com.gtsoft.utils.filter.superdata.IFilter;

public class SwgNave extends AbstractSwg {
    
    private Nave nave;

    private final static Hashtable ATTRIBUTI = new Hashtable();

    /*
     * final static boolean[] clickabile={false}; final static String[]
     * attributi={"Descrizione"};
     */

    private static final void setAttributi() {
        ATTRIBUTI.put("descrizione", new Boolean(false));
    }

    public SwgNave(RifiutiFrame frame) throws Exception {
        setAttributi();
        this.frame = frame;
        this.nave = new Nave();
        String[] campiJTable = nave.getColumns();
       
        this.elementi = getElenco_for_database();
        tableMouseClicked = new RifiutiMouseClicked();
    }

    public Vector getElenco_for_database() throws Exception {
        if (this.elementi == null) {
            //this.elementi = Nave.getAll(frame.getDatabase());
            buildJMainTable();
        }
        return this.elementi;
    }

    protected void buildJMainTable() {
        Nave n = null;
        if (elementi != null)
            for (int i = 0; i < elementi.size(); i++) {
                n = (Nave) elementi.get(i);
               // getSwgTable().addElementToTableModel(nave.getValues());
            }
    }

    public void setValues() {
        
    }
    JMainTable jTable = null ;
    public JMainTable getJMainTable() {
        
        /*jTable = this.getSwgTable();
        MouseAdapter m = new RifiutiMouseClicked();
        jTable.getTable().addMouseListener(m);
        frame.setStatusBar(jTable.getTable().getTableHeader(),
                "Cliccare per ordinare la tabella secondo questo campo");
        frame.setStatusBar(jTable.getTable(),
                "Cliccare per accedere al dettaglio");
        jFiltroPanel = new Date_JFiltroPanel(this);*/
        return jTable ;
    }

    private static RifiutiMouseClicked tableMouseClicked = null;

    private class RifiutiMouseClicked extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
           
        }

    }

    public Hashtable getAttributi() {
        return ATTRIBUTI;
    }

    public void setNave(Nave nave) {
        this.nave=nave;
        
    }

    /* (non-Javadoc)
     * @see com.gtsoft.rifiuti.front.data.AbstractSwg#getSortAttributi()
     */
    public String[] getSortAttributi() {
        return null;
    }

    /* (non-Javadoc)
     * @see com.gtsoft.rifiuti.front.data.AbstractSwg#getFilterManager()
     */
    public FilterManager getFilterManager() {
        return null;
    }

    /* (non-Javadoc)
     * @see com.gtsoft.rifiuti.front.data.AbstractSwg#attributo_isClickable(java.lang.String)
     */
    public boolean attributo_isClickable(String attributo) {
        return false;
    }

    /* (non-Javadoc)
     * @see com.gtsoft.rifiuti.front.data.AbstractSwg#attributo_getIndex(java.lang.String)
     */
    public int attributo_getIndex(String attributo) {
        return 0;
    }

    /* (non-Javadoc)
     * @see com.gtsoft.rifiuti.front.data.AbstractSwg#jMainTableInitialize()
     */
    protected JMainTable jMainTableInitialize() {
        return null;
    }

    /* (non-Javadoc)
     * @see com.gtsoft.rifiuti.front.data.AbstractSwg#setOggettoConcreto(com.gtsoft.rifiuti.superdata.IListable)
     */
    public void setOggettoConcreto(IListable listable) {        
    }

    /* (non-Javadoc)
     * @see com.gtsoft.rifiuti.front.data.AbstractSwg#stampa()
     */
    public void stampa() {       
    }

    /* (non-Javadoc)
     * @see com.gtsoft.rifiuti.front.data.AbstractSwg#getDetailed_JDialog()
     */
    protected JDialog getDetailed_JDialog() {
        return null;
    }

    public IFilter getFilter(int column) {
		return null;
	}

}