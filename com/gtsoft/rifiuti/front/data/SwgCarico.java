/*
 * Creato il 8-lug-2005
 * 
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti
 *  
 */
package com.gtsoft.rifiuti.front.data;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.gtsoft.rifiuti.data.Carico;
import com.gtsoft.rifiuti.data.Cer;
import com.gtsoft.rifiuti.data.Cliente;
import com.gtsoft.rifiuti.data.Fornitore;
import com.gtsoft.rifiuti.data.Rifiuto;
import com.gtsoft.rifiuti.front.Da_A_JFiltroPanel;
import com.gtsoft.rifiuti.front.Date_JFiltroPanel;
import com.gtsoft.rifiuti.front.JFiltroPanel;
import com.gtsoft.rifiuti.front.JMainTable;
import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.rifiuti.front.data.detailedPanel.CaricoDetailed_jDialog;
import com.gtsoft.rifiuti.superdata.IListable;
import com.gtsoft.utils.filter.AbstractDataFilter;
import com.gtsoft.utils.filter.AbstractMatchFilter;
import com.gtsoft.utils.filter.Abstract_Da_A_Filter;
import com.gtsoft.utils.filter.FilterManager;
import com.gtsoft.utils.filter.superdata.IFilter;
import com.gtsoft.utils.filter.superdata.IFiltrable;

public class SwgCarico extends AbstractSwg implements IFiltrable {
    
    
	private static class Cost{

		private static final String DATA="Data";
	    private static final String DATA_PESATURA="Data pesatura";
	    private static final String CLIENTE="Cliente";
	    private static final String CONSEGNA="Consegna";
	    private static final String NUMERO_PROGRESSIVO="N.Progressivo";
	    private static final String DESTINAZIONE="Destinazione";
	    private static final String DOCUMENTO="Documento";
	    private static final String FORNITORE="Fornitore";
	    private static final String DESCRIZIONE="Descrizione";
	    private static final String MERCE="Merce";
	    private static final String NAVE="Nave";
	    private static final String NUMERO_CATASTE="N.Cataste";
	    private static final String VETTORE="Vettore";   
	    
	    private final static String[] SORTATTRIBUTI = { 
											            DATA, 
											            DATA_PESATURA,
											            CLIENTE, 
											            CONSEGNA, 
											            NUMERO_PROGRESSIVO, 
											            DESTINAZIONE,
											            DOCUMENTO, 
											            FORNITORE, 
											            DESCRIZIONE, 
											            MERCE, 
											            NAVE,
											            NUMERO_CATASTE, 
											            VETTORE 
	            };
	    
	    private static final int RIFIUTO_FILTER=0;
	    private static final int NUMERO_FILTER=1;
	    private static final int GIACENZA_FILTER=2;
	    private static final int DATA_FILTER=3;
	}
    
    private static final class ATTRIBUTI {
        private static Hashtable ATTRIBUTI =null ;

        private static final List addValuesAttributo(boolean b, int i) {
            List values_attributi=new ArrayList();
            values_attributi.add(new Boolean(b));
            values_attributi.add(new Integer(i));
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
    
    public String[] getSortAttributi() {
        return Cost.SORTATTRIBUTI;
    }
    
    
    public boolean attributo_isClickable(String attributo) {
        return ATTRIBUTI.isClickable(attributo);
    }    
    
    public int attributo_getIndex(String attributo) {
        return ATTRIBUTI.getIndex(attributo);
    }     
    
    protected Carico carico;
    
    public SwgCarico(RifiutiFrame frame) throws Exception {
        ATTRIBUTI.setAttributi();
        this.frame = frame;
        getJMainTable();
        refreshElencoJTable();
        if(!getElementi().isEmpty())
        	setOggettoConcreto((IListable)getElementi().get(0));
    }
    

    public IListable getElemento() {
        if(elemento==null)
            elemento=new Carico();
        return elemento;
    }
    
    public void setValues() {
        if (values == null)
            values = new Hashtable();
        values.put(Cost.NUMERO_PROGRESSIVO, String.valueOf(carico
                .getNumeroProgressivo()));
        values.put(Cost.DATA, getCarico().getData().dmyString());
        values.put(Cost.DESCRIZIONE, getCarico().getDescrizione());
        values.put(Cost.DATA_PESATURA, getCarico().getDataPesatura().dmyString());
        values.put(Cost.CLIENTE, getCarico().getCliente().getRagioneSociale());
        values.put(Cost.CONSEGNA, getCarico().getConsegna());
        values.put(Cost.DESTINAZIONE, "" + getCarico().getDestinazione() );
        values.put(Cost.DOCUMENTO, getCarico().getDocumento());
        values.put(Cost.FORNITORE, getCarico().getFornitore().getRagioneSociale());
        try {
            values.put(Cost.MERCE, getCarico().getMerce().getNome());
        } catch (Exception e) {
            // XXX Blocco catch generato automaticamente
            e.printStackTrace();
        }
        values.put(Cost.NAVE, getCarico().getNave().getDescrizione());
        values.put(Cost.NUMERO_CATASTE, String.valueOf(getCarico()
                .getNumeroCataste()));
        values.put(Cost.VETTORE, getCarico().getVettore().getRagioneSociale());
    }

    public JDialog getDetailed_JDialog() {
        return new CaricoDetailed_jDialog(this.getCarico(),getSwg().getFrame());
    }
    
    protected JMainTable jMainTableInitialize(){
        jMainTable = new JMainTable(this);
        jMainTable.setColumnSort(1);
        jMainTable.getJTable().getColumnModel().getColumn(0).setMaxWidth(80);
        jMainTable.getJTable().getColumnModel().getColumn(1).setMaxWidth(90);
        jMainTable.getJTable().getColumnModel().getColumn(3).setMaxWidth(90); 
        jMainTable.getJTable().setDefaultRenderer(Object.class,new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column) {
                JLabel l=(JLabel)super.getTableCellRendererComponent(table ,value ,isSelected,hasFocus,row,column);
                	if( column==3)
                	    l.setHorizontalAlignment(JLabel.RIGHT);
                	else
                	    l.setHorizontalAlignment(JLabel.CENTER);
                return l;	
            }
            																					
        });
        return jMainTable;   
    }
    
    public void getFrameDettailed(int index) throws Exception {
        

    } 
       
    public void setCarico(Carico carico) {
        this.carico = carico;
    }

    public void setOggettoConcreto(IListable elemento) {
        this.carico=(Carico) elemento;
    }
    
    public Carico getCarico() {
        if (carico==null) 
            carico=new Carico();
        return carico;
    }
    
          
        

        private void popola_comboBox_fornitore(JFiltroPanel filtroCorrente_JPanel) {
            try {
            	Vector list = Fornitore.getAll(getFrame().getDatabase() );
            	Fornitore f = null ;
              	
            	for ( int i = 0 ; i < list.size() ; i++ ){
            	     f= ((Fornitore)list.get(i)) ; 
            	    filtroCorrente_JPanel.getJComboBox().addItem(f) ;
            	}
            
            }
            catch ( Exception ex ) {
                ex.printStackTrace();
            }
        }

        private void popola_comboBox_cliente(JFiltroPanel filtroCorrente_JPanel) {
            try {
            	Vector list = Cliente.getAll(getFrame().getDatabase() );
            	Cliente c = null ;
              	
            	for ( int i = 0 ; i < list.size() ; i++ ){
            	     c= ((Cliente)list.get(i)) ; 
            	    filtroCorrente_JPanel.getJComboBox().addItem(c) ;
            	}
            
            }
            catch ( Exception ex ) {
                ex.printStackTrace();
            }
        }
//}}       
        private SwgCarico getSwg() {
            return this;
        }

        private FilterManager filterManager;
        public FilterManager getFilterManager() {
            if(filterManager==null)
                filterManager=new FilterManager(getElementi(),this);
            return filterManager;
        }
        
        protected IFilter[] filtriApplicabili;
        public IFilter[] getFilters() {
            if(filtriApplicabili==null) {
            filtriApplicabili=new IFilter[4];
            filtriApplicabili[Cost.RIFIUTO_FILTER]=new RifiutiFilter(null,"rifiuto",this);
            filtriApplicabili[Cost.DATA_FILTER]=new DataFilter(this);
            filtriApplicabili[Cost.GIACENZA_FILTER]=new GiacenzaFilter("",this);
            filtriApplicabili[Cost.NUMERO_FILTER]=new NumeroFilter("",this);
            }
            return filtriApplicabili;
        }

       
        protected class RifiutiFilter extends AbstractMatchFilter{
           
            public RifiutiFilter(){};
        	
        	public RifiutiFilter(Object comparator, String descrizione,AbstractSwg swg) {
                setComparator(comparator);
                setDescrizione(descrizione);
                try {
                    setJFiltroPanel(new JFiltroPanel(descrizione,this,swg,Rifiuto.getAll(getFrame().getDatabase())));
                } catch (Exception e) {
                    // XXX Blocco catch generato automaticamente
                    e.printStackTrace();
                }    
            }
            
            public boolean check(Object obj) {     
                try {
                    return ((Rifiuto)comparator).getNome().equals((((Carico)obj).getMerce().getNome()));
                } catch (Exception e) {
                    // XXX Blocco catch generato automaticamente
                    e.printStackTrace();
                }
                return false;
            }

           
        }

        protected class CerFilter extends AbstractMatchFilter{
            
            
            public CerFilter(Object comparator, String descrizione,AbstractSwg swg) {
                setComparator(comparator);
                setDescrizione(descrizione);
                try {
                    setJFiltroPanel(new JFiltroPanel(descrizione,this,swg,Cer.getAll(getFrame().getDatabase())));
                } catch (Exception e) {
                    // XXX Blocco catch generato automaticamente
                    e.printStackTrace();
                }    
            }
            
            public CerFilter() {
                
            }
            public boolean check(Object obj) {
                try {
                    return ((Cer)comparator).getCodice().equals((((Carico)obj).getMerce().getCer().getCodice()));
                } catch (Exception e) {
                    // XXX Blocco catch generato automaticamente
                    e.printStackTrace();
                }
                return false;
            }
        
        }
        
        private class DataFilter extends AbstractDataFilter{
            
            private DataFilter(AbstractSwg swg) {
                try {
                    setDescrizione("data");
                    setJFiltroPanel(new Date_JFiltroPanel("filtra per data",this,swg));
                } catch (Exception e) {
                    // XXX Blocco catch generato automaticamente
                    e.printStackTrace();
                }
            }
            public boolean check( Object c ) {
                return dataCheck(((Carico) c).getData()) ;
            } 
        }
           
        private class GiacenzaFilter extends Abstract_Da_A_Filter{
	        
            private GiacenzaFilter(String descrizione,AbstractSwg swg) {
                setParse(Abstract_Da_A_Filter.DOUBLE);
                setDescrizione(descrizione);
                try {
                	setJFiltroPanel(new Da_A_JFiltroPanel(descrizione,this,swg));
                } catch (Exception e) {
                    // XXX Blocco catch generato automaticamente
                    e.printStackTrace();
                }    
            }
            
            public boolean check(Object obj) {
               return comparableCheck( new Double(((Carico)obj).getNetto()));
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
                return comparableCheck( new Integer(((Carico) c).getNumeroProgressivo())) ;
            } 
        	
        	public JButton getButton(){
        		return null;
        	}
        }
        
        
            

		public void stampa() {
		    JOptionPane.showMessageDialog(null, "Operazione al momento non consentita.");
			//ReportManager.printMovimenti(getPrintElements());
			
		}

		
		
		public IFilter getFilter(int column) {
			
			String column_filter=getCarico().getColumns()[column];
			
			if(column_filter==Carico.DATA)
				return getFilters()[Cost.DATA_FILTER];
			
			else	if(column_filter==Carico.RIFIUTO)
						return getFilters()[Cost.RIFIUTO_FILTER];
			
			else	if(column_filter==Carico.GIACENZA)
						return getFilters()[Cost.GIACENZA_FILTER];
			
			else	if(column_filter==Carico.NUMERO)
						return getFilters()[Cost.NUMERO_FILTER];
				
			else 	return null;
		}
        

}
