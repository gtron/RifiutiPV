/*
 * Created on 21-ago-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.front.data;

import java.awt.Component;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.gtsoft.rifiuti.data.Movimento;
import com.gtsoft.rifiuti.data.Rifiuto;
import com.gtsoft.rifiuti.data.StoricoMovimenti;
import com.gtsoft.rifiuti.data.Vettore;
import com.gtsoft.rifiuti.front.Da_A_JFiltroPanel;
import com.gtsoft.rifiuti.front.Date_JFiltroPanel;
import com.gtsoft.rifiuti.front.JFiltroPanel;
import com.gtsoft.rifiuti.front.JMainTable;
import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.rifiuti.superdata.IListable;
import com.gtsoft.utils.SwingWorker;
import com.gtsoft.utils.filter.AbstractDataFilter;
import com.gtsoft.utils.filter.AbstractMatchFilter;
import com.gtsoft.utils.filter.Abstract_Da_A_Filter;
import com.gtsoft.utils.filter.FilterManager;
import com.gtsoft.utils.filter.superdata.IFilter;
import com.gtsoft.utils.filter.superdata.IFiltrable;
import com.gtsoft.utils.reports.ReportManager;


/**
 * @author Gtron - 
 * 
 */
public class SwgStorico extends AbstractSwg implements IFiltrable{
    
    
    public String[] getSortAttributi() { 
        return null;
    }
    
    public boolean attributo_isClickable(String attributo) {
        return false ;
    }    
    
    public int attributo_getIndex(String attributo) {
        return 0 ;
    } 
    public void setValues() {
    }
    
    public SwgStorico ( RifiutiFrame frame , String anno ) throws Exception {
        this.frame = frame;
        this.elementi = StoricoMovimenti.getAll(anno, frame.getDatabase());
        getJMainTable();
        refreshElencoJTable();
        if(!getElementi().isEmpty()){
            setOggettoConcreto((IListable)getElementi().get(0));
        }
    }
    
    public IListable getElemento() {
        if(elemento==null)
            elemento=new StoricoMovimenti();
        return elemento;
    }
   
    
    protected JMainTable jMainTableInitialize(){
        jMainTable = new JMainTable(this);
        final JTable jTable=jMainTable.getJTable();
        jTable.getColumnModel().getColumn(0).setMinWidth(70);
        jTable.getColumnModel().getColumn(0).setMaxWidth(70);
        jTable.getColumnModel().getColumn(1).setMinWidth(50);
        jTable.getColumnModel().getColumn(1).setMaxWidth(50);
        jTable.getColumnModel().getColumn(2).setMinWidth(26);
        jTable.getColumnModel().getColumn(2).setMaxWidth(26);
        jTable.getColumnModel().getColumn(3).setMinWidth(70);
        jTable.getColumnModel().getColumn(3).setMaxWidth(120);
        jTable.getColumnModel().getColumn(4).setMinWidth(100);
        jTable.getColumnModel().getColumn(5).setMinWidth(20);
        

        /*jTable.getColumnModel().getColumn(4).setMaxWidth(35);
        jTable.getColumnModel().getColumn(5).setMaxWidth(35);*/
        
        jTable.setDefaultRenderer(Object.class,new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column) {
                JLabel l=(JLabel)super.getTableCellRendererComponent(table ,value ,isSelected,hasFocus,row,column);
                l.setIcon(null);
                l.setToolTipText(null);
                
                
                if(column==jTable.convertColumnIndexToView(2)){
                	if(value.toString().equals("S"))
                		l.setToolTipText("Scarico");
                	else
                		l.setToolTipText("Carico");
                }
                
                if( column==jTable.convertColumnIndexToView(3))
               	    l.setHorizontalAlignment(JLabel.RIGHT);
                else if( column==jTable.convertColumnIndexToView(5))
               	    l.setHorizontalAlignment(JLabel.LEFT);
               	else
               	    l.setHorizontalAlignment(JLabel.CENTER);
               	

               /* 
               	if(column==jTable.convertColumnIndexToView(4) ){
               		l.setText(null);
               		if(value.toString().equals("Si")){
               			l.setIcon(Cost.PRINT_ICON);
               			l.setToolTipText("Movimento stampato");
               		}	
	               	else{
	               		l.setIcon(null);
	               		l.setToolTipText(null);
	               	}
               	}	
               		
               	
               	if(column==jTable.convertColumnIndexToView(5) ){
               		l.setText(null);
               		if(value.toString().equals("Si")){
               			l.setIcon(Cost.SCARICO_ICON);
               			l.setToolTipText("Movimento scaricato");
               		}	
	               	else
	               		l.setIcon(null);
               	}
               	*/
               	return l;
            }
            																					
        });
        return jMainTable;   
    }
    
    
    
    public JPanel getDetailedPanel() {
        return null;
    }

    private static class Cost {
        private static final int RIFIUTI_FILTER=0;
        private static final int DATA_FILTER=1;
        private static final int PESO_FILTER=2;
        private static final int NUMERO_FILTER=3;
//       	private static final ImageIcon PRINT_ICON=new ImageIcon(JLabel.class.getResource("/image/printer.gif"));
//       	private static final ImageIcon SCARICO_ICON=new ImageIcon(JLabel.class.getResource("/image/printer.gif"));
    }
    
    public void setOggettoConcreto(IListable listable) {
    }
    
    private IFilter[] filtriApplicabili = null ;
    public IFilter[] getFilters() {
        try {
            if(filtriApplicabili==null) {
                filtriApplicabili=new IFilter[5];
                filtriApplicabili[Cost.RIFIUTI_FILTER]=new RifiutiFilter(this);
                filtriApplicabili[Cost.DATA_FILTER]=new DataFilter("data",this);
                filtriApplicabili[Cost.PESO_FILTER]=new PesoFilter("filtra per peso",this);
                filtriApplicabili[Cost.NUMERO_FILTER]= new NumeroFilter("num.progr.",this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filtriApplicabili;
    }
    
    private FilterManager filterManager;
    public FilterManager getFilterManager() {
        if(filterManager==null)
            filterManager=new FilterManager(getElementi(),this);
        return filterManager;
    }
    
    
    private class RifiutiFilter extends AbstractMatchFilter {
        
        /**
         * @param comparator -> oggetto da comparare
         * @param descrizone -> stringa che apare sul bottone e sul pannello del filtro
         * @param fm-> filter manager a cui � associato il filtro
         * @param swg-> swg a cui � associato il filtro
         * @param list-> vector di object che popolano il combobox
         *
         */ 
        private RifiutiFilter(AbstractSwg swg) {
            setComparator(comparator);
            setDescrizione("rifiuto");
            try {
                setJFiltroPanel(new JFiltroPanel(getDescrizione(),this,swg,Rifiuto.getAll(getFrame().getDatabase())));
            } catch (Exception e) {
                // XXX Blocco catch generato automaticamente
                e.printStackTrace();
            }
        }
        
        public boolean check(Object obj) {
            try {
                return comparator.equals((((Movimento)obj).getMerce()));
            } catch (Exception e) {
                // XXX Blocco catch generato automaticamente
                e.printStackTrace();
            }
            return false;       
        }
        
        
        
    }
    
    private class TrasportatoreFilter extends AbstractMatchFilter {
        
        /**
         * @param comparator -> oggetto da comparare
         * @param descrizone -> stringa che apare sul bottone e sul pannello del filtro
         * @param fm-> filter manager a cui � associato il filtro
         * @param swg-> swg a cui � associato il filtro
         * @param list-> vector di object che popolano il combobox
         *
         */ 
        TrasportatoreFilter(AbstractSwg swg) {
            setComparator(comparator);
            setDescrizione("trasportatore");
            try {
                setJFiltroPanel(new JFiltroPanel(getDescrizione(),this,swg,Vettore.getUsedInMovimenti(getFrame().getDatabase())));
            } catch (Exception e) {
                // XXX Blocco catch generato automaticamente
                e.printStackTrace();
            }
        }
        
        public void refreshElenco(AbstractSwg swg) {
            
            Vector listT = new Vector() ;
            String done = "" ;
            Vettore v = null ; 
            try {
                for ( Iterator i = getElementi().iterator() ; i.hasNext() ; ) {
                    v = ((Movimento) i.next()).getTrasportatore();
                    if ( v != null && done.indexOf(v.getCodice()) < 1 ) {
                        done += "-" + v.getCodice();
                        listT.add(v) ;
                    }
                }
                Collections.sort(listT);
                listT.insertElementAt("",0);
                
                //setJFiltroPanel(new JFiltroPanel(getDescrizione(),this,swg,Vettore.getUsedInMovimenti(getFrame().getDatabase())));
                setJFiltroPanel(new JFiltroPanel(getDescrizione(),this,swg,listT));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public boolean check(Object obj) {
            if ( obj == null ) return false ;
            try {
                return comparator.equals((((Movimento)obj).getTrasportatore()));
            } catch (Exception e) {
                // XXX Blocco catch generato automaticamente
                e.printStackTrace();
            }
            return false;       
        }
    }
    
    
    private class DataFilter extends AbstractDataFilter{
        
        private DataFilter(String descrizione, AbstractSwg swg) {
            try {
                setDescrizione(descrizione);
                setJFiltroPanel(new Date_JFiltroPanel(descrizione,this,swg));
            } catch (Exception e) {
                // XXX Blocco catch generato automaticamente
                e.printStackTrace();
            }
        }
        
        public boolean check( Object c ) {
            return dataCheck(((Movimento) c).getData()) ;
        } 
        
    }
    
    private class PesoFilter extends Abstract_Da_A_Filter{
    	
    	private PesoFilter(String descrizione, AbstractSwg swg) {
            try {
            	setParse(Abstract_Da_A_Filter.DOUBLE);
                setDescrizione(descrizione);
                setJFiltroPanel(new Da_A_JFiltroPanel(descrizione,this,swg));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    	
    	public boolean check( Object c ) {
            return comparableCheck(((Movimento) c).getPeso()) ;
        } 
    	
    	public JButton getButton(){
    		return null;
    	}
    }
    
    private class NumeroFilter extends Abstract_Da_A_Filter{
    	
    	private NumeroFilter(String descrizione, AbstractSwg swg) {
            try {
            	setParse(Abstract_Da_A_Filter.INTEGER);
                setDescrizione(descrizione);
                setJFiltroPanel(new Da_A_JFiltroPanel(descrizione,this,swg));
            } catch (Exception e) {
                // XXX Blocco catch generato automaticamente
                e.printStackTrace();
            }
        }
    	
    	public boolean check( Object c ) {
            return comparableCheck( new Integer(((Movimento) c).getNumProgressivo())) ;
        } 
    	
    	public JButton getButton(){
    		return null;
    	}
    }
    
    
   
    public void stampa () {
        
        SwingWorker worker = new SwingWorker(){
            public Object construct() {
                try {
                    try {  
                        // frame.stampa( ReportManager.STAMPA.ELENCO_MOVIMENTI , false);
    		        }
    		        catch ( Exception ex ) {
    		            if ( ex.getMessage() != "" ) {
        		            JOptionPane.showMessageDialog(frame, ex.getMessage() ,
        		                    " Errore ",
        		                    JOptionPane.ERROR_MESSAGE); 
    		            }
    		        }
    		        finally {
    		            frame.hideElaboration();
    		        }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                frame.hideElaboration();
                frame.setWaitCursor(false);
                return null;
            }
        };
        
        worker.start();
        
        
    }
    
    public void exportXLS ( String file ) throws Exception {
       
        ReportManager.exportMovimenti( getPrintElements() , file );
    }
    
    
    public void addMovimento( Movimento m ) {
        this.elementi.add(0,m);
        
        getFilterManager().setUnfiltered(elementi);
        refreshElencoJTable();
    }
    
    
    protected JDialog getDetailed_JDialog() {
        return null;
    }

	public IFilter getFilter(int column){
		
		String column_filter=getElemento().getColumns()[column];
		
		if(column_filter==Movimento.DATA)
			return getFilters()[Cost.DATA_FILTER];
		
		else	if(column_filter==Movimento.RIFIUTO)
					return getFilters()[Cost.RIFIUTI_FILTER];
		
		else	if(column_filter==Movimento.PESO)
					return getFilters()[Cost.PESO_FILTER];
		
		else	if(column_filter==Movimento.NUMERO)
					return getFilters()[Cost.NUMERO_FILTER];
			
		else 	return null;
	}
	
}




