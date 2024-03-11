/*
 * Created on 21-ago-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.front.data;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.gtsoft.rifiuti.data.Carico;
import com.gtsoft.rifiuti.data.Movimento;
import com.gtsoft.rifiuti.data.Rifiuto;
import com.gtsoft.rifiuti.data.Vettore;
import com.gtsoft.rifiuti.front.Da_A_JFiltroPanel;
import com.gtsoft.rifiuti.front.Date_JFiltroPanel;
import com.gtsoft.rifiuti.front.JFiltroPanel;
import com.gtsoft.rifiuti.front.JMainTable;
import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.rifiuti.front.data.detailedPanel.Movimento_detailedPanel;
import com.gtsoft.rifiuti.superdata.IListable;
import com.gtsoft.utils.ImportManager;
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
public class SwgMovimento extends AbstractSwg implements IFiltrable{
    
    
    public String[] getSortAttributi() { 
        String[] SORTATTRIBUTI = { 
                Cost.DATA, 
                Cost.DATA_PESATURA,
                Cost.CLIENTE, 
                Cost.CONSEGNA, 
                Cost.NUMERO_PROGRESSIVO, 
                Cost.DESTINAZIONE,
                Cost.DOCUMENTO, 
                Cost.FORNITORE, 
                Cost.DESCRIZIONE, 
                Cost.MERCE, 
                Cost.NAVE,
                Cost.NUMERO_CATASTE, 
                Cost.VETTORE,
                Cost.TIPO
        }; 
        return SORTATTRIBUTI;
    }
    
    private static final class ATTRIBUTI {
        private static Hashtable ATTRIBUTI =null ;
        
        private static final List addValuesAttributo(boolean b, int i) {
            List values_attributi=new ArrayList();
            values_attributi.add(new Boolean(b));
            values_attributi.add(Integer.valueOf(i));
            return values_attributi;
        }
        
        private static final void setAttributi() {
            if(ATTRIBUTI==null)
                ATTRIBUTI=new Hashtable();
            ATTRIBUTI.put(Cost.NUMERO_PROGRESSIVO, addValuesAttributo(false, 0));
            ATTRIBUTI.put(Cost.DATA, addValuesAttributo(false, 1));
            ATTRIBUTI.put(Cost.DESCRIZIONE, addValuesAttributo(false, 2));
            ATTRIBUTI.put(Cost.DATA_PESATURA, addValuesAttributo(false, 3));
            ATTRIBUTI.put(Cost.CLIENTE, addValuesAttributo(true, 4));
            ATTRIBUTI.put(Cost.CONSEGNA, addValuesAttributo(false, 5));
            ATTRIBUTI.put(Cost.DESTINAZIONE, addValuesAttributo(true, 6));
            ATTRIBUTI.put(Cost.DOCUMENTO, addValuesAttributo(false, 7));
            ATTRIBUTI.put(Cost.FORNITORE, addValuesAttributo(true, 8));
            ATTRIBUTI.put(Cost.MERCE, addValuesAttributo(true, 9));
            ATTRIBUTI.put(Cost.NAVE, addValuesAttributo(true, 10));
            ATTRIBUTI.put(Cost.NUMERO_CATASTE, addValuesAttributo(false, 11));
            ATTRIBUTI.put(Cost.VETTORE, addValuesAttributo(true, 12));
        }
        
        private static boolean isClickable(String attributo) {
            return ((Boolean)((List)(ATTRIBUTI.get(attributo))).get(0)).booleanValue();
        }
        
        private static int getIndex(String attributo) {
            return ((Integer) ((List) ATTRIBUTI.get(attributo)).get(1)).intValue();
        }
        
    }
    
    
    public boolean attributo_isClickable(String attributo) {
        return ATTRIBUTI.isClickable(attributo);
    }    
    
    public int attributo_getIndex(String attributo) {
        return ATTRIBUTI.getIndex(attributo);
    }     
    
    protected Movimento movimento;
    public SwgMovimento ( RifiutiFrame frame , String anno ) throws Exception {
        ATTRIBUTI.setAttributi();
        this.anno = anno ;
        this.frame = frame;
        this.elementi = getElenco_for_database();
        getJMainTable();
        refreshElencoJTable();
        if(!getElementi().isEmpty()){
            setOggettoConcreto((IListable)getElementi().get(0));
        }
    }
    
    public IListable getElemento() {
        if(elemento==null)
            elemento=new Movimento();
        return elemento;
    }
    
    //Forse per SwgMovimento non serve
    public void setValues() {
        
        if (values == null)
            values = new Hashtable();
        values.put(Cost.NUMERO_PROGRESSIVO, String.valueOf(getMovimento()
                .getNumProgressivo()));
        values.put(Cost.DATA, getMovimento().getData().dmyString());
        values.put( Cost.VETTORE , getMovimento().getTrasportatore() ) ;
        
        /*
         values.put(Cost.DESCRIZIONE, getMovimento().getDescrizione());
         values.put(Cost.DATA_PESATURA, getMovimento().getDataPesatura().dmyString());
         values.put(Cost.CLIENTE, getMovimento().getCliente().getRagioneSociale());
         values.put(Cost.CONSEGNA, getMovimento().getConsegna());
         values.put(Cost.DESTINAZIONE, getMovimento().getDestinazione().getRagioneSociale());
         values.put(Cost.DOCUMENTO, getMovimento().getDocumento());
         values.put(Cost.FORNITORE, getMovimento().getFornitore().getRagioneSociale());
         */
        try {
            values.put(Cost.MERCE, getMovimento().getMerce().getNome());
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
         values.put(Cost.NAVE, getMovimento().getNave().getDescrizione());
         values.put(Cost.NUMERO_CATASTE, String.valueOf(getMovimento()
         .getNumeroCataste()));
         values.put(Cost.VETTORE, getMovimento().getVettore().getRagioneSociale());
         */
    }
    
    String anno = "0" ;
    public void setAnno( String anno ) throws Exception {
        if ( this.anno != anno ) {
            this.anno = anno ;
            setElenco( getElenco_for_database() );
        }
    }
    
    public Vector getElenco_for_database() throws Exception {
        //this.elementi = Movimento.getAll(frame.getDatabase(), anno );
        
        //this.elementi=new Vector(); //simulo un database vuoto
        return this.elementi;
    }
    
    protected JMainTable jMainTableInitialize(){
        jMainTable = new JMainTable(this);
        final JTable jTable=jMainTable.getJTable();
        jTable.getColumnModel().getColumn(0).setMinWidth(70);
        jTable.getColumnModel().getColumn(0).setMaxWidth(80);
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
        if(getMovimento()!=null)
            return new Movimento_detailedPanel(this);
        else
            return null;
    }
    
    public void setMovimento(Movimento movimento) {
        this.movimento = movimento;
    }
    
    public Movimento getMovimento() {
        return movimento;
    }
    
    private static class Cost {
        
        private static final String DATA="data";
        private static final String DATA_PESATURA="data pesatura";
        private static final String CLIENTE="cliente";
        private static final String CONSEGNA="consegna";
        private static final String NUMERO_PROGRESSIVO="numero progressivo";
        private static final String DESTINAZIONE="destinazione";
        private static final String DOCUMENTO="documento";
        private static final String FORNITORE="fornitore";
        private static final String DESCRIZIONE="descrizione";
        private static final String MERCE="merce";
        private static final String NAVE="nave";
        private static final String NUMERO_CATASTE="numeo cataste";
        private static final String VETTORE="vettore";
        private static final String TIPO="tipo";
        private static final int RIFIUTI_FILTER=0;
        private static final int DATA_FILTER=1;
        private static final int TRASPORTATORE_FILTER=2;
        private static final int PESO_FILTER=3;
        private static final int NUMERO_FILTER=4;
        private static final int TIPOMOVIMENTO_FILTER=5;
        private static final int TIPORIFIUTO_FILTER=6;
//        
//       	private static final ImageIcon PRINT_ICON=new ImageIcon(JLabel.class.getResource("/image/printer.gif"));
//       	private static final ImageIcon SCARICO_ICON=new ImageIcon(JLabel.class.getResource("/image/printer.gif"));
        
        
    }
    
    public void setOggettoConcreto(IListable listable) {
        this.movimento=(Movimento) listable;
        
    }
    
    public void refreshElenco(Vector addElements, Vector delElements ) {
        getElementi().add(addElements);
        getElementi().remove(delElements);
    }
    
    private IFilter[] filtriApplicabili;
    public IFilter[] getFilters() {
        try {
            if(filtriApplicabili==null) {
                filtriApplicabili=new IFilter[7];
                if ( ! RifiutiFrame.isRifiuto342 ) {
                	filtriApplicabili[Cost.RIFIUTI_FILTER]=new RifiutiFilter(this);
                }
                filtriApplicabili[Cost.DATA_FILTER]=new DataFilter("data",this);
                filtriApplicabili[Cost.TRASPORTATORE_FILTER]=new TrasportatoreFilter(this);
                filtriApplicabili[Cost.PESO_FILTER]=new PesoFilter("filtra per peso",this);
                filtriApplicabili[Cost.NUMERO_FILTER]= new NumeroFilter("num.progr.",this);
                filtriApplicabili[Cost.TIPOMOVIMENTO_FILTER]=new TipoMovimentoFilter(this);
                
                
                if ( ! RifiutiFrame.separateFumi && ! RifiutiFrame.isRifiuto342 )
                	filtriApplicabili[Cost.TIPORIFIUTO_FILTER]=new TipoRifiutoFilter(this);
            }
        } catch (Exception e) {
            // XXX Blocco catch generato automaticamente
            e.printStackTrace();
        }
        return filtriApplicabili;
    }
    public void refreshElencoTrasportatori() {
        ((TrasportatoreFilter) getFilters()[SwgMovimento.Cost.TRASPORTATORE_FILTER]).refreshElenco(this);
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
        private TrasportatoreFilter(AbstractSwg swg) {
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
                        done += "##" + v.getCodice();
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
                // XXX Blocco catch generato automaticamente
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
            return comparableCheck( Integer.valueOf(((Movimento) c).getNumProgressivo())) ;
        } 
    	
    	public JButton getButton(){
    		return null;
    	}
    }
    
    private class TipoMovimentoFilter extends AbstractMatchFilter {
        
        private TipoMovimentoFilter(AbstractSwg swg) {
            setComparator(comparator);
            setDescrizione("Tipo");
            try {
            		Vector l = new Vector() ;
            		l.add("Carichi");
            		l.add("Scarichi");
                setJFiltroPanel(new JFiltroPanel(getDescrizione(),this,swg, l ));
            } catch (Exception e) {
                // XXX Blocco catch generato automaticamente
                e.printStackTrace();
            }
        }
        
        public boolean check(Object obj) {
            try {
            		Movimento o = (Movimento)obj ;
            		if ( comparator.equals("Carichi") )
            			return "C".equals( o.getTipo() );
            		else
            			return ! "C".equals( o.getTipo() );
            		
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;       
        }
        public JButton getButton(){
	    		return null;
	    	}
    }
    
    private class TipoRifiutoFilter extends AbstractMatchFilter {
        
        private TipoRifiutoFilter(AbstractSwg swg) {
            setComparator(comparator);
            setDescrizione("Tipo");
            try {
            		Vector l = new Vector() ;
            		l.add("Scorie");
            		l.add("Fumi");
                setJFiltroPanel(new JFiltroPanel(getDescrizione(),this,swg, l ));
            } catch (Exception e) {
                // XXX Blocco catch generato automaticamente
                e.printStackTrace();
            }
        }
        
        public boolean check(Object obj) {
            try {
            		if ( comparator.equals("Scorie") )
            			return "S".equals( ((Movimento)obj).getMerce().getTipo() );
            		else
            			return "F".equals( ((Movimento)obj).getMerce().getTipo() );
            		
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;       
        }

    }
    
    
    public void vai_a_scarichi() {
        getFrame().setSwgCorrente(RifiutiFrame.Cost.SwgUtilizzabili.SCARICHI);
        Carico carico = ((Carico)getMovimento().getCarico());
        SwgScarico swgScarico = ((SwgScarico)(getFrame().getSwgCorrente()));
        swgScarico.aggiungi_carico(carico);
    }
    
    public void eliminaMovimento() {
        
        getFrame().setWaitCursor(true);
        
        getMovimento().delete() ;
        
        /*
         * 
         for ( Iterator i = getElementi().iterator() ; i.hasNext() ;) {
         if ( i.next().equals(getMovimento()) ) {
         getElementi().remove(getMovimento()) ;
         break ;
         }
         }
         */
        
        try {
            getFrame().refreshElenchi(true) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    
    public void stampa () {
        
        SwingWorker worker = new SwingWorker(){
            public Object construct() {
                try {
                    try {  
                        frame.stampa( ReportManager.STAMPA.ELENCO_MOVIMENTI , false);
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
    
    
    public Vector getDaStampareRegistro() {
        
        Vector daStampare = new Vector() ;
        Movimento curr ;
        
        Vector list = elementi ;
        
        if ( list == null || list.size() < 1 ) {
        	try {
        		list = Movimento.getAll(getFrame().getDatabase(), "" + ( getFrame().getAnnoAsInt() - 1 ) );
        	}
        	catch ( Exception e ) {
        		e.printStackTrace() ;
        	}
        }
        
        for ( Iterator i = list.iterator() ; i.hasNext() ;) {
            curr = (Movimento) i.next();
            if ( ! curr.isStampato() ) {
            	daStampare.add(curr);
            }
        }    
        return daStampare;
    }
    
    protected JDialog getDetailed_JDialog() {
        return null;
    }

    public void  reimportaMovimento() throws Exception {
        
        ImportManager i = new ImportManager(getFrame()) ;
        
        i.importFromAccess(getMovimento()) ;
    }

	
	
	public IFilter getFilter(int column){
		
		String column_filter=getMovimento().getColumns()[column];
		
		if(column_filter==Movimento.DATA)
			return getFilters()[Cost.DATA_FILTER];
		
		else	if(column_filter==Movimento.RIFIUTO)
					return getFilters()[Cost.RIFIUTI_FILTER];
		
		else	if(column_filter==Movimento.PESO)
					return getFilters()[Cost.PESO_FILTER];
		
		else	if(column_filter==Movimento.NUMERO)
					return getFilters()[Cost.NUMERO_FILTER];
		
		else	if(column_filter==Movimento.TIPO)
			return getFilters()[Cost.TIPOMOVIMENTO_FILTER];
			
		else 	return null;
	}
	
}




