/*
 * Creato il 6-lug-2005
 *
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti
 * 
 */
package com.gtsoft.rifiuti.front.data;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import com.gtsoft.rifiuti.data.Cer;
import com.gtsoft.rifiuti.data.Rifiuto;
import com.gtsoft.rifiuti.front.Da_A_JFiltroPanel;
import com.gtsoft.rifiuti.front.JDatiPanel;
import com.gtsoft.rifiuti.front.JFiltroPanel;
import com.gtsoft.rifiuti.front.JMainTable;
import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.rifiuti.front.data.detailedPanel.Rifiuto_detailedPanel;
import com.gtsoft.rifiuti.superdata.IListable;
import com.gtsoft.utils.filter.AbstractMatchFilter;
import com.gtsoft.utils.filter.Abstract_Da_A_Filter;
import com.gtsoft.utils.filter.FilterManager;
import com.gtsoft.utils.filter.superdata.IFilter;
import com.gtsoft.utils.filter.superdata.IFiltrable;
import com.gtsoft.utils.reports.ReportManager;

public class SwgRifiuto extends AbstractSwg implements IFiltrable{
    
    
	private static final class Cost { 
		
		private static final String CODICE_QUADRELLI="Codice Quadrelli";
	    private static final String NOME="Nome";
//	    private static final String UNITA="Unità";
	    private static final String CODICE_CER="Codice Cer";
	    private static final String STATO="Stato";
	    private static final String CLASSE_PERICOLOSITA="Classe di pericolosita";
	    private static final String RECUPERO="Recupero";
	    private static final String GIACENZA="Giacenza (Kg)";
	    
	    final static String[] SORTATTRIBUTI = { 
	        	CODICE_QUADRELLI,
	    		CODICE_CER,	 
	            NOME,
	            GIACENZA,
	            STATO, 
				CLASSE_PERICOLOSITA , 
				RECUPERO
	     };
	    
	    /**
	     * fitri disponibili per questo tipo
	     */
	    private static final int CER_FILTER=0;
	    private static final int GIACENZA_FILTER=1;
	    private static final int RIFIUTO_FILTER=2;
	    private static final int TRATTATO_FILTER=3;
	    private static final int TIPORIFIUTO_FILTER=4;
	}
    
    
    private Rifiuto rifiuto;
    
    
    
    //ho tolto la costruzione di un rifiuto se ho null perche mi serve sapere quando ho un rifiuto incosistente
    public Rifiuto getRifiuto() {
        return rifiuto;
    }

    public void setValues() {
        if (values == null)
            values = new Hashtable();
        
        if(getRifiuto().getCodiceQuadrelli()!=null)
        	values.put(Cost.CODICE_QUADRELLI,getRifiuto().getCodiceQuadrelli());
        
        if(getRifiuto().getNome()!=null)
        	values.put(Cost.NOME, getRifiuto().getNome());
        
        if(getRifiuto().getCodiceCer()!=null)
        	values.put(Cost.CODICE_CER, getRifiuto().getCodiceCer());
        
        if(getRifiuto().getGiacenza()!=null)
        	values.put(Cost.GIACENZA, ""+ getRifiuto().getGiacenza() + " " + getRifiuto().getUnita());
        
        if(getRifiuto().getStato()!=null)
        	if(getRifiuto().getStato().getDescrizione()!=null)
        	values.put(Cost.STATO, getRifiuto().getStato().getDescrizione());
        
        if(getRifiuto().getListaClassiPericolosita()!=null)
        	values.put(Cost.CLASSE_PERICOLOSITA, getRifiuto().getListaClassiPericolosita());
        
        /*
         * if(getRifiuto().getRecupero()!=null)
        	if(getRifiuto().getRecupero().getCodice()!=null)
        		values.put(Cost.RECUPERO, getRifiuto().getRecupero().getCodice());
        */
        try {
            values.put(Cost.RECUPERO, getRifiuto().getCodiceRecupero() );
        }
        catch ( Exception e ) {
            values.put(Cost.RECUPERO, "" );
        }
    }
    
    public void setJDatiPanel() {
        setValues();
        datiJPanel = new JDatiPanel(this);
        datiJPanel.getJLabelValori()[0].setToolTipText(getRifiuto().getCer().getNome());
        datiJPanel.getJLabelValori()[1].setToolTipText(getRifiuto().getStato().getDescrizione());
        //datiJPanel.getJLabelValori()[5].setToolTipText(getRifiuto().getRecupero().getDescrizione());
    }
 
    public Vector getElenco_for_database() throws Exception {
        this.elementi = Rifiuto.getAll(frame.getDatabase());
        Rifiuto r = new Rifiuto() ;
        r.setCodiceQuadrelli("0");
        r.setCodiceCer("0");
        r.setNome("Complessivo Rifiuti");
        this.elementi.add(0, r );
        return this.elementi;
    }


    protected JMainTable jMainTableInitialize(){
        jMainTable = new JMainTable(this);
        jMainTable.getJTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jMainTable.getJTable().getColumnModel().getColumn(0).setMaxWidth(65);
        jMainTable.getJTable().getColumnModel().getColumn(1).setMaxWidth(45);
        jMainTable.getJTable().getColumnModel().getColumn(3).setMaxWidth(110);
        jMainTable.getJTable().getColumnModel().getColumn(3).setMinWidth(110);
        jMainTable.getJTable().getColumnModel().getColumn(4).setMaxWidth(110);
        jMainTable.getJTable().getColumnModel().getColumn(4).setMinWidth(110);
        jMainTable.getJTable().setDefaultRenderer(Object.class,new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column) {
                JLabel l=(JLabel)super.getTableCellRendererComponent(table ,value ,isSelected,hasFocus,row,column);
                	if( column>=3)
                	    l.setHorizontalAlignment(JLabel.RIGHT);
//                	else if ( column == 2)
//                	    l.setHorizontalAlignment(JLabel.CENTER);
                	else
                	    l.setHorizontalAlignment(JLabel.LEFT);
                return l;	
            }
            																					
        });
          return jMainTable;   
    }
    
    public IListable getElemento() {
        if(elemento==null)
            elemento=new Rifiuto();
        return elemento;
    }
    
    public void setRifiuto(Rifiuto rifiuto){
        this.rifiuto=rifiuto;
    }

    public JPanel getDetailedPanel() {
    	if(getRifiuto()!=null) {
    	    JPanel pnl = new JPanel(new BorderLayout()) ;
    	    JPanel pnl2 = new JPanel(new BorderLayout());
    	    
    	    pnl2.add( new Rifiuto_detailedPanel(this) , BorderLayout.NORTH );
//    	    pnl2.add( getGrafico_JPanel() , BorderLayout.CENTER );
    	    
    	    pnl.add(pnl2, BorderLayout.CENTER) ;
    	    
    		return pnl ;
    	}
    	else 
    		return null;
    }
    
    
    public void edit_clicked() {
        ObjectEditor oe = new ObjectEditor( this , rifiuto );
        oe.setSize(400, 390);
    }

    public void setOggettoConcreto(IListable listable) {
        rifiuto=(Rifiuto)listable;
        
    }



    private FilterManager filterManager;
    public FilterManager getFilterManager() {
        if(filterManager==null)
            filterManager=new FilterManager(getElementi(),this);
        return filterManager;
    }

    
    private IFilter[] filtriApplicabili;
    public IFilter[] getFilters() {
        if(filtriApplicabili==null) {
            filtriApplicabili=new IFilter[5];
            filtriApplicabili[Cost.CER_FILTER]=new CerFilter(null,"codice cer",this);
            filtriApplicabili[Cost.GIACENZA_FILTER]=new GiacenzaFilter(null,"giacenza",this);
            filtriApplicabili[Cost.TRATTATO_FILTER]=new TrattatoFilter(null,"trattato",this);
            filtriApplicabili[Cost.RIFIUTO_FILTER]=new RifiutiFilter(this);
            filtriApplicabili[Cost.TIPORIFIUTO_FILTER]=new TipoRifiutoFilter(this);
        }
        return filtriApplicabili;
    }
    
    private class CerFilter extends AbstractMatchFilter{
        
        private CerFilter(Object comparator, String descrizione,AbstractSwg swg) {
            setComparator(comparator);
            setDescrizione(descrizione);
            try {
                setJFiltroPanel(new JFiltroPanel(descrizione,this,swg,Cer.getAll(getFrame().getDatabase())));
            } catch (Exception e) {
                // XXX Blocco catch generato automaticamente
                e.printStackTrace();
            }    
        }
        
        public boolean check(Object obj) {
            try {
                return ((Cer)comparator).getCodice().equals((((Rifiuto)obj).getCer().getCodice()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
        
    }

	private class GiacenzaFilter extends Abstract_Da_A_Filter{
	        
        private GiacenzaFilter(Object comparator, String descrizione,AbstractSwg swg) {
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
           return comparableCheck(((Rifiuto)obj).getGiacenzaDouble());
        }
        
        public JButton getButton(){
        	return null;
        }
	}
	
	private class TrattatoFilter extends Abstract_Da_A_Filter{
        
        private TrattatoFilter(Object comparator, String descrizione,AbstractSwg swg) {
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
           return comparableCheck(((Rifiuto)obj).getLavoratoDouble());
        }
        
        public JButton getButton(){
        	return null;
        }
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
                return comparator.equals((((Rifiuto)obj)));
            } catch (Exception e) {
                // XXX Blocco catch generato automaticamente
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
            			return "S".equals( ((Rifiuto)obj).getTipo() );
            		else
            			return "F".equals( ((Rifiuto)obj).getTipo() );
            		
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;       
        }        
    }

    protected JDialog getDetailed_JDialog() {
        // XXX Stub di metodo generato automaticamente
        return null;
    }

    public JDatiPanel getJDatiPanel(){
        if(datiJPanel==null){
        	datiJPanel=super.getJDatiPanel(); 
        	MouseListener m=datiJPanel.getMouseListeners()[0];
        	datiJPanel.removeMouseListener(m);
        }
        return datiJPanel;
    }

	public void stampa() {
		ReportManager.printRifiuti( getPrintElements() ) ;
		
	}

	public IFilter getFilter(int column) {
		String column_filter=getRifiuto().getColumns()[column];
		
		if(column_filter==Rifiuto.CER)
			return getFilters()[Cost.CER_FILTER];
		
		else	if(column_filter==Rifiuto.GIACENZA)
					return getFilters()[Cost.GIACENZA_FILTER];
		
		else	if(column_filter==Rifiuto.NOME)
					return getFilters()[Cost.RIFIUTO_FILTER];
		
		else	if(column_filter==Rifiuto.TRATTATO)
					return getFilters()[Cost.TRATTATO_FILTER];
			
		else 	return null;
		
		
	}
	
	public void dataModified() {
		frame.setSwgCorrente(RifiutiFrame.Cost.SwgUtilizzabili.RIFIUTI, true);
	}
	
//	public JPanel getGrafico_JPanel() {
//        
//        Rifiuto r = (Rifiuto) getRifiuto();
//       
//        
//        try {
//	        Vector list = StoricoMovimenti.get(new Integer(r.getCodiceQuadrelli()), null, null, getFrame().getDatabase() ) ;
//	        
//	        JFreeChart chart = StoricoMovimenti.buildChartGiacenze(list) ;
//	        
//	        return new ChartPanel(chart);
//        }
//        catch ( Exception e ) {
//            e.printStackTrace() ;
//        }
//        	
//       return null ;
//        
//    }
    
	public String[] getSortAttributi() {
        return Cost.SORTATTRIBUTI;
    }
    
    

    public boolean attributo_isClickable(String attributo) {
        return ATTRIBUTI.isClickable(attributo);
    }    
    
    public int attributo_getIndex(String attributo) {
        return ATTRIBUTI.getIndex(attributo);
    }     

    public SwgRifiuto(RifiutiFrame frame) throws Exception {
        ATTRIBUTI.setAttributi();
        this.frame = frame;
        this.elementi = getElenco_for_database();
        getJMainTable();
        refreshElencoJTable();
        if(!getElementi().isEmpty())
        	setOggettoConcreto((IListable)getElementi().get(0));
        setDivideLocation(450) ;
        
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
            ATTRIBUTI.put(Cost.CODICE_QUADRELLI, addValuesAttributo(false, 0));
            ATTRIBUTI.put(Cost.NOME, addValuesAttributo(false, 1));
            ATTRIBUTI.put(Cost.CODICE_CER, addValuesAttributo(true, 2));
            ATTRIBUTI.put(Cost.STATO, addValuesAttributo(false, 3));
            ATTRIBUTI.put(Cost.CLASSE_PERICOLOSITA, addValuesAttributo(false, 4));
            ATTRIBUTI.put(Cost.RECUPERO, addValuesAttributo(false, 5));
            ATTRIBUTI.put(Cost.GIACENZA, addValuesAttributo(false, 6));
        }
        
        private static boolean isClickable(String attributo) {
            return ((Boolean)((List)(ATTRIBUTI.get(attributo))).get(0)).booleanValue();
        }
        
        private static int getIndex(String attributo) {
            return ((Integer) ((List) ATTRIBUTI.get(attributo)).get(1)).intValue();
        }
        
    }

    
    
}