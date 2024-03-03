/*
 * Created on 28-lug-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.front.data;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import com.gtsoft.rifiuti.data.Carico;
import com.gtsoft.rifiuti.data.CaricoParziale;
import com.gtsoft.rifiuti.data.Movimento;
import com.gtsoft.rifiuti.data.Rifiuto;
import com.gtsoft.rifiuti.data.Vettore;
import com.gtsoft.rifiuti.front.JDetailedPanel;
import com.gtsoft.rifiuti.front.JNomePanel;
import com.gtsoft.rifiuti.front.MascheraScarico_JPanel;
import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.rifiuti.superdata.IListable;
import com.gtsoft.utils.FormattedDate;
import com.gtsoft.utils.filter.superdata.IFilter;

/**
 * @author Gtron - 
 * 
 */

public class SwgScarico extends SwgCarico {

    private MascheraScarico_JPanel mascheraScarico ;
    private ScaricaMouseListener scaricaMouseListener= new ScaricaMouseListener();
    
    public SwgScarico(RifiutiFrame frame , String anno ) throws Exception {
        super(frame);
        getJMainTable().getJTable().addMouseListener(scaricaMouseListener);
        getJMainTable().getJTable().setSelectionMode( ListSelectionModel.SINGLE_SELECTION); 
    }
    
    public void setJNomePanel() {
        nomeJPanel = new JNomePanel(this.getTitolo());
    }
    
    public JPanel getDetailedPanel(){
        return getDetailPanel();
    }
    public JDetailedPanel getDetailPanel() {
        //new FrameDettailed(movimento.getSwg(frame).getDetailedPanel());
        
        panelDtg = new JDetailedPanel( getMascheraScarico_JPanel() , getJNomePanel() );
        
        return panelDtg ;
    }
    
    public JNomePanel getJNomePanel () {
        setJNomePanel() ;
        return nomeJPanel ;
    }
    
    public MascheraScarico_JPanel getMascheraScarico_JPanel() {
        if(mascheraScarico==null)
            mascheraScarico = new MascheraScarico_JPanel( getFrame() ) ;
        return mascheraScarico;
    }    
    
    public void setElenco(Vector elementi) {
        Collections.reverse(elementi) ;
        super.setElenco(elementi);
        try {
            if ( mascheraScarico.getRifiuto_jComboBox().getSelectedIndex() == 0 )
                mascheraScarico.getRifiuto_jComboBox().setSelectedIndex(1);
            mascheraScarico.getRifiuto_jComboBox().setSelectedIndex(0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private IFilter rifiuto;
    
    public IFilter getRifiutofilter() {
        if(rifiuto==null)
            rifiuto=new RifiutiFilter();
        return rifiuto;
    }
    
    public void filterFoRifiuto(Rifiuto rifiuto) {
        getRifiutofilter().setComparator(rifiuto);
        getFilterManager().addFilter(getRifiutofilter());
    }
    
    private class ScaricaMouseListener extends MouseAdapter{
    	public void mousePressed(MouseEvent e){
    		if(e.getButton() == MouseEvent.BUTTON1 &&e.getClickCount() == 2){
    			aggiungi_carichi();
    		}
    	}
    }
    
    public void aggiungi_carico(Carico c) {
        try {
            seleziona_combo_box_item(c.getMerce());
            filtra(c.getMerce());
            Vector v=new Vector();
            v.add(c);
            getMascheraScarico_JPanel().getJTable().addItems(v);
            if ( ! c.getMovimento().isParziale() )
                getJMainTable().removeItems(v);
            
            getMascheraScarico_JPanel().refresh_giacenza();
        } 
        catch (Exception e) {
            // XXX Blocco catch generato automaticamente
            e.printStackTrace();
        }       
    }
    public Carico getPrimoCarico() { 
        return (Carico)jMainTable.getElenco().get(0) ;
    }
    public void aggiungi_carichi() {
     
        try {
            int[] selectedRows=jMainTable.getJTable().getSelectedRows();
            int[] modelIndex = jMainTable.getTableSorter().getIndexModel();   
	        if(getMascheraScarico_JPanel().isISEmptyTable()) {
		        Vector elementi_selezionati=new Vector();
		        Rifiuto r;
		        Carico caricoSelezionato = ((Carico)jMainTable.getElenco().get(modelIndex[selectedRows[0]])) ;
		        r = caricoSelezionato.getMerce();
		        getRifiutofilter().setComparator(r);
		        for (int i=0;i<selectedRows.length;i++) {
		        	elementi_selezionati.add((Carico)jMainTable.getElenco().get(modelIndex[selectedRows[i]]));
		        	if(!getRifiutofilter().check(elementi_selezionati.get(i))) {
		        	    JOptionPane.showMessageDialog(getFrame(),"Attenzione : Sono stati sezionati rifiuti incompatibili");
		        	    return;
		        	} 
		        }
	        	seleziona_combo_box_item(r);
	        	filtra(r);
	        	getJMainTable().removeItems(elementi_selezionati);
	        	if ( caricoSelezionato.getMovimento().isGiacenza() ) {
	        	    elementi_selezionati = CaricoParziale.getByCarico(caricoSelezionato) ;
	        	}
	        	getMascheraScarico_JPanel().getJTable().addItems(elementi_selezionati);
	            
	            getMascheraScarico_JPanel().refresh_giacenza();
	        }
	        else {
	            Object c = getMascheraScarico_JPanel().getJTable().getElenco().firstElement();
	            if ( c instanceof CaricoParziale ) {
	                JOptionPane.showMessageDialog(getFrame(),"Attenzione : Si sta scaricando un carico parziale.\nPer scaricare altri carichi effettuare due scarichi separati.");
	            }
	            else {
		            addSelectedItemsAtJTableRight();  
		        	getMascheraScarico_JPanel().refresh_giacenza();
	            }
	        }
        } 
        catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(getFrame(),"Selezionare almeno un rifiuto");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
   }
        
    
    public void seleziona_combo_box_item(Rifiuto r){
        //getMascheraScarico_JPanel().getRifiuto_jComboBox().removeActionListener()
        getMascheraScarico_JPanel().getRifiuto_jComboBox().setSelectedItem(r);
        getMascheraScarico_JPanel().getCer_jTextField().setText(r.getCodiceQuadrelli());
    }
    
    public void filtra(Rifiuto r) {
        try {
            getFilterManager().removeFilter(getRifiutofilter());
            getRifiutofilter().setComparator(r);
            getFilterManager().addFilter(getRifiutofilter());
            refreshElencoJTable();
        } catch (Exception e) {
            // XXX Blocco catch generato automaticamente
            e.printStackTrace();
        }
    }
    

    /**
     *prende gli elementi selezionati dalla tabella di sinistra e gli inserisce nella tabella di destra,
     *cancella gli elementi selezionati dalla tabella di sinistra
     */
    public void addSelectedItemsAtJTableRight() {
        getMascheraScarico_JPanel().getJTable().addSelectedItem(getJMainTable()); 
        getJMainTable().removeSelectedItem();
    }
    
    public void removeSelectedItemsAtJTableRight() {
        getJMainTable().addSelectedItem(getMascheraScarico_JPanel().getJTable());
        getMascheraScarico_JPanel().getJTable().removeSelectedItem();
    }
    
    public void addItemToTableRight(int i) {
        IListable element=(IListable) getJMainTable().getElenco().get(i);
        getMascheraScarico_JPanel().getJTable().addElement(element);    
    }
    
    public void removeItemToTableLeft(int i) {
        IListable element=(IListable) getJMainTable().getElenco().get(i);
        getJMainTable().removeElement(element);
    }
    
    public void removeAllItemsAtJTableRight() {
        
        Vector elenco=getMascheraScarico_JPanel().getJTable().getElenco();
        getJMainTable().addItems(elenco);
        getMascheraScarico_JPanel().getJTable().removeAllItems();
    }
    
    public void scaricamovimenti( Vettore vettore, FormattedDate dataScarico ) throws Exception {
        Vector elenco=getMascheraScarico_JPanel().getJTable().getElenco();
        
        Movimento scarico = new Movimento();
        scarico.setData( dataScarico ) ;
        
        Object c = elenco.get(0) ;
        
        if ( c instanceof CaricoParziale ) {
            CaricoParziale cp = ((CaricoParziale)c) ;
            
            scarico.setTipo( Movimento.TipoMovimento.SCARICOPARZIALE ) ;
            
            Vector v = new Vector(1) ;
            
            if ( cp.isGiacenza() ) {
                String s = "" ;
                Integer p = null ;
                while ( p == null  ) { 
                    s = (String)JOptionPane.showInputDialog(
                        frame,"Inserire il peso da scaricare ( p < giacenza )",
                        "Scarico di Giacenza",
                        JOptionPane.PLAIN_MESSAGE,
                        null, null, "" + new Double( cp.getNetto() ).intValue() );
                    
                    if ( s == null || s.length() < 1 ) throw new Exception("CANCEL") ;
                    
                    try { 
                        p = new Integer( s ) ;
                    }
                    catch ( Exception e ) {
                        JOptionPane.showMessageDialog(frame,
                                "Attenzione : il numero inserito non è corretto",
                                "Errore",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    
                    double diff = cp.getNetto() - p.intValue() ; 
                    if ( diff > 0 ) {
                        
                        cp.setNetto( p.doubleValue() ) ;
                        cp.update() ;
                            
                        CaricoParziale ncp = cp.cloneMe() ;
                        ncp.setNetto( diff ) ;
                        ncp.create() ;
                        
                        v.add(cp.getCarico());
                        scarico.setCarichi(v) ;
                        
                       
                    }
                    else {
                        if ( JOptionPane.YES_OPTION == 
                            JOptionPane.showConfirmDialog(null, 
                                    "Attenzione! Si sta terminando la giacenza.\n" +
                                    "Procedere ?")) {

	                        v.add(cp.getCarico());
	                        scarico.setCarichi(v) ;
                        }
                        else {
                            return ;
                        }
                    }
                }
                
                
	            
            }
            else {
	            v.add(cp.getCarico());
	            scarico.setCarichi(v) ;
            }
            
            cp.getMerce().clearGiacenza();
            
        }
        else {
            scarico.setTipo( Movimento.TipoMovimento.SCARICO ) ;
//          scarico.setTrasportatore( vettore );
            scarico.setCarichi(elenco) ;
        }

        CaricoParziale cTmp = null ;
        try {
            scarico.create( frame.getDatabase() );
            
            if ( scarico.isParziale() ) {
                if ( scarico.getIdCaricoParziale() > 0 ) {
    	            CaricoParziale.scarica( scarico.getIdCaricoParziale(),  scarico) ;
    	        }
                else {
                    for ( Iterator i = elenco.iterator() ; i.hasNext() ; ) {
                        cTmp = (CaricoParziale) i.next() ;
                        CaricoParziale.scarica( cTmp.getId().intValue(),  scarico ) ;
                    }
                    
                    Vector cResidui = CaricoParziale.getByMerceGiorno(scarico.getMerce().getCodiceQuadrelli() , null , false );
                    if ( cResidui.size() < 1 ) {
                        cTmp.getCarico().scarica(scarico.getData()) ;
                    }
                }
            }
                
        }
        catch ( Exception ex ) { 
            ex.printStackTrace();
        } 
        
        frame.scaricaMovimento(scarico) ;
        
        if ( scarico.isParziale() ) {
            frame.refreshElenchi(true);
        }
    }
    
    public int getSplitLocation(){
		getFrame().getJSplitPane().setDividerLocation(0.6);
		return getFrame().getJSplitPane().getDividerLocation();
	}
    
}

