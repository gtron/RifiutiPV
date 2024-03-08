/*
 * Creato il 7-lug-2005
 * 
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti
 *  
 */
package com.gtsoft.rifiuti.front.data;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.gtsoft.rifiuti.front.Date_JFiltroPanel;
import com.gtsoft.rifiuti.front.JDatiPanel;
import com.gtsoft.rifiuti.front.JDetailedPanel;
import com.gtsoft.rifiuti.front.JMainTable;
import com.gtsoft.rifiuti.front.JNomePanel;
import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.rifiuti.front.Swg_jPanel;
import com.gtsoft.rifiuti.superdata.IListable;
import com.gtsoft.utils.filter.FilterManager;
import com.gtsoft.utils.filter.superdata.IFilter;

public abstract class AbstractSwg  {

    protected JDetailedPanel panelDtg;
    protected JDatiPanel datiJPanel;
    protected JNomePanel nomeJPanel;

    
    public Vector getPrintElements() {
        return getPrintElements( !getJMainTable().getSelectedItems().isEmpty() ) ;	
    }
    
    public Vector getPrintElements( boolean onlySelected) {
        Vector elenco_da_stampare=new Vector();
        	if(onlySelected)
        		elenco_da_stampare=getJMainTable().getSelectedItems();
        	else{
        	    // elenco_da_stampare=getFilterManager().getFiltered();
        	    getJMainTable().getJTable().clearSelection();
        	    getJMainTable().getJTable().selectAll();
        	    elenco_da_stampare=getJMainTable().getSelectedItems();
        	}
        return elenco_da_stampare;	
    }
    
    public abstract void stampa();
    
    
    protected IListable elemento;
    protected RifiutiFrame frame = null;

    protected JMainTable jMainTable = null;

    protected Date_JFiltroPanel jFiltroPanel;

//    private JPanel listJPanel = null;

    protected Hashtable values = null;

//    private JPanel contentButtonPanel;
//
//    private int indexgridXContraints;

    private Swg_jPanel swg_jPanel;

    // public abstract Vector getElenco_for_database() throws Exception;

    public abstract String[] getSortAttributi();

    public abstract void setValues();
    
    public void refreshElencoJTable() {
        getJMainTable().setElenco(getFilterManager().getFiltered());
    }
    
    public abstract FilterManager getFilterManager();
    
    public void setElenco(Vector elementi){
        this.elementi=elementi;
        getFilterManager().setUnfiltered(this.elementi);
        refreshElencoJTable();
    }
    
    protected AbstractSwg getThis() {
        return this;
    }

    public IListable getElemento() {
        return elemento;
    }

    public Swg_jPanel getSwg_jPanel() {
        if(swg_jPanel==null) {
            swg_jPanel=new Swg_jPanel(this);
            swg_jPanel.getContent_filter_JPanel().getAnno_JComboBox().setSelectedItem(frame.getAnno());
        }
        return swg_jPanel;
    }

   public void setJDatiPanel() {
        setValues();
        datiJPanel = new JDatiPanel(this);
    }

    public JDatiPanel getJDatiPanel(){
        if(datiJPanel==null)
            setJDatiPanel();
        return datiJPanel;
    }
    
    public JDatiPanel refreshJDatiPanel(){
            setJDatiPanel();
        return datiJPanel;
    }


    protected Vector elementi = new Vector();
    
	private JButton chiudi_dettaglio_button;

    public Vector getElementi() {
        return elementi;
    }

    public void setJNomePanel() {
        nomeJPanel = new JNomePanel(this.getTitolo());
        nomeJPanel.add(get_chiudi_dettaglio_JButton(),BorderLayout.EAST);
    }
    
    public JNomePanel getJNomePanel(){
    	if(nomeJPanel==null){
    		setJNomePanel();
    	}
    	return nomeJPanel;
    }
    
    public JButton get_chiudi_dettaglio_JButton(){
    	if(chiudi_dettaglio_button==null){
    		chiudi_dettaglio_button=new JButton("chiudi");
    		chiudi_dettaglio_button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    		chiudi_dettaglio_button.addActionListener(new NomeActionListener());
    	}
    	return chiudi_dettaglio_button;
    }

    public JPanel getDetailedPanel() {
        setPanelDtg();
        return panelDtg;
    }

    public void setPanelDtg() {
        setJNomePanel();
        setJDatiPanel();
        panelDtg = new JDetailedPanel();
        panelDtg.setLayout(new BorderLayout());
        panelDtg.add(nomeJPanel, BorderLayout.NORTH);
        panelDtg.add(datiJPanel, BorderLayout.CENTER);
    }


    public String getTitolo() {
        if (this.getClass() == SwgCarico.class)
            return "Movimento";
        else if (this.getClass() == SwgCliente.class)
            return "Cliente";
        else if (this.getClass() == SwgFornitore.class)
            return "Fornitore";
        else if (this.getClass() == SwgRifiuto.class)
            return "Rifiuto";
        else if (this.getClass() == SwgSoggetto.class)
            return "Soggetto";
        else if (this.getClass() == SwgVettore.class)
            return "Trasportatore";
        else if (this.getClass() == SwgScarico.class)
            return "Scarica Rifiuti";
        else 
            return "movimento"; 
        

    }

    public String toString() {
        return this.getTitolo();
    }

    
    public Hashtable getValues() {
        return values;
    }

    

    public RifiutiFrame getFrame() {
        return frame;
    }
    
    
    
    public abstract boolean attributo_isClickable(String attributo);   
    
    public abstract int attributo_getIndex(String attributo);

    public JMainTable getJMainTable() {
        if(jMainTable==null) {
           jMainTableInitialize();
           frame.setStatusBar(jMainTable.getJTable().getTableHeader(),"Cliccare per ordinare la tabella secondo questo campo");
           frame.setStatusBar(jMainTable.getJTable(),"Cliccare per accedere al dettaglio");
           jMainTable.getJTable().getSelectionModel().addListSelectionListener(swgListSelectionListener);
           jMainTable.getJTable().addMouseListener(rifiutiMouseListener);
        }
        return jMainTable;
    }  
    
    
    protected abstract JMainTable jMainTableInitialize();
    
    
//    private int index=0;

    protected JPanel radioButton_JPanel;
    
    protected IListable elementoCorrente;
    
    public abstract void setOggettoConcreto(IListable listable);
   
    protected IListable getListableAtIndex(int row) {
		int[] modelIndex = getJMainTable().getTableSorter().getIndexModel();
		return (IListable) getJMainTable().getElenco().get(modelIndex[row]);
	}


	protected SwgListSelectionListener swgListSelectionListener=new SwgListSelectionListener();
    
    private int indiceCorrente;
	
	protected class SwgListSelectionListener implements ListSelectionListener{

        public void valueChanged(ListSelectionEvent arg0) {
        	
        	if(!getThis().getClass().equals(SwgScarico.class))
	        	SwingUtilities.invokeLater(new Runnable() {
					public void run() {
			           if(isMousereleased()) {
		        	   		int maxIndex=getJMainTable().getJTable().getSelectionModel().getMaxSelectionIndex();
		        	   		int minIndex=getJMainTable().getJTable().getSelectionModel().getMinSelectionIndex();
		        	   		if(maxIndex==minIndex)
				        	   try {
				                    int row = getJMainTable().getJTable().getSelectedRow();
				                    if ( row >= 0 ) {
					                    elementoCorrente=getListableAtIndex(row);
					                    setIndiceCorrente(row);
					                    setOggettoConcreto(elementoCorrente);
					                    frame.showDetailSplit();
				                    }
				                } catch ( Exception e) {
				                    e.printStackTrace();
				                }
			           }
					}
	        	});
        }
    }
	
	
	private boolean mousereleased=true;
	
	private RifiutiMouseListener rifiutiMouseListener=new RifiutiMouseListener();
	
	protected class RifiutiMouseListener implements MouseListener{

		public void mouseClicked(MouseEvent e) {
			
			
		}

		public void mousePressed(MouseEvent e) {
			setMousereleased(false);
			
		}

		public void mouseReleased(MouseEvent e) {
			setMousereleased(true);
			
		}

		public void mouseEntered(MouseEvent e) {
			
		}

		public void mouseExited(MouseEvent e) {
			
		}
		
	}

	public void apri_il_dettaglio() {
		this.getDetailed_JDialog();
		
	}
	
	protected abstract JDialog getDetailed_JDialog(); 
	public void refreshElencoJTable(Vector elenco_filtrato){
    	getJMainTable().setElenco(elenco_filtrato);
    	getJMainTable().revalidate();
    }
	
	private int splitLocation=-1;
	
	private boolean primaApertura=true;

	public void setDivideLocation(int dividerLocation) {
		splitLocation=dividerLocation;
		
	}
	
	public int getSplitLocation(){
		if(splitLocation==-1)
			return getFrame().getJSplitPane().getDividerLocation();
		else
			return splitLocation;
	}
	
	private class NomeActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(get_chiudi_dettaglio_JButton())){
                    setDivideLocation(getFrame().getJSplitPane().getDividerLocation());
            		getFrame().getJSplitPane().remove(getFrame().getJSplitPane().getRightComponent());
            		getJMainTable().getJTable().removeRowSelectionInterval(0,getJMainTable().getJTable().getRowCount()-1);
                    setPrimaApertura(true);
                }
        }

    }

	public boolean isPrimaApertura() {
		return primaApertura;
	}

	public void setPrimaApertura(boolean primaApertura) {
		this.primaApertura = primaApertura;
	}

	public int getIndiceCorrente() {
		return indiceCorrente;
	}

	private void setIndiceCorrente(int indiceCorrente) {
		this.indiceCorrente = indiceCorrente;
	}

	public boolean isMousereleased() {
		return mousereleased;
	}

	public void setMousereleased(boolean mouserelased) {
		this.mousereleased = mouserelased;
	}

    /**
     * 
     */
    public void selectFirstRecord() {
                
	    if ( getJMainTable().getJTable().getRowCount() > 0 ) {
	        int row=0;
	    
	        try {
	            row= getIndiceCorrente();
	            getJMainTable().getJTable().setRowSelectionInterval(row,row);
	        }
	        catch( Exception x ) { x.printStackTrace(); }
	    }
    }

    /**
     * @throws Exception
     * 
     */
    public void exportXLS( String file) throws Exception {
        JOptionPane.showMessageDialog(null,"Operazione non consentita.");        
    }
	
	public abstract IFilter getFilter(int column);
	
	public void add_filter(int column) {		
		try {
            if(!getFilterManager().getContent_filtri_JPanel().isFiltriFull()){
            	if(getFilter(column)!=null){
            		getFilterManager().add_JFilterPanel(getFilter(column).getJFilterPanel());
            		getFilter(column).setEnableItems(false);
            	}
            }
            else
            	JOptionPane.showMessageDialog(getFilterManager().getContent_filtri_JPanel(),"non puoi applicare più di due filtri contemporaneamente");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
	}

    /**
     * 
     */
    public void dataModified() {
       System.out.println("--------------------");
       System.out.println("Implement this method in your swg !");
    }
}
