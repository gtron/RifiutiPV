/*
 * Creato il 31-ago-2005
 *
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti_new
 * 
 */
package com.gtsoft.rifiuti.front.data.detailedPanel;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import com.gtsoft.rifiuti.data.StoricoMovimenti;
import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.rifiuti.front.data.AbstractSwg;
import com.gtsoft.rifiuti.front.data.SwgRifiuto;
import com.gtsoft.utils.FormattedDate;
import com.gtsoft.utils.filter.AbstractDataFilter;
import com.toedter.calendar.JDateChooser;
public class Rifiuto_detailedPanel extends JPanel {

    private JPanel jDatiPanel;
    private SwgRifiuto swg;
    
    
	private JTabbedPane jTabbedPane = null;
	
	private Vector storico = null ;
	private Vector filteredStorico = null ;
	
	private JFreeChart giacenze ;
	private JFreeChart caricati ;
	
	private static TimeSeries sGiacenza = new TimeSeries("Giacenza");
	private static TimeSeries sCarichi = new TimeSeries("Carichi");
	
	boolean stopUpdate = false ;
	
    public Rifiuto_detailedPanel(SwgRifiuto swg) {
        super(new BorderLayout());
        
        this.swg=swg;
        
        setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 8));
        
        //this.setBackground(Color.WHITE);
        
        try {
        	//this.add(getJDatiPanel(),BorderLayout.CENTER );
        	//this.add(getChart_jButton(),BorderLayout.SOUTH ); 
            this.add(getJTabbedPane(),BorderLayout.CENTER );
            //this.add(getFilter_JPanel(),BorderLayout.SOUTH ); 
            //this.add(swg.getJNomePanel(),BorderLayout.NORTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // JScrollPane scrollPane = new JScrollPane(getJDatiPanel());
        
        //this.add(scrollPane,BorderLayout.CENTER);

    }
    
    private static final long serialVersionUID = 132L;
	private JRadioButton carichi_jRadioButton = null;
	private JRadioButton jRadioButton = null;
	private JPanel selectChart_jPanel = null ;
	
	private JPanel getSelectChart_jPanel() {
		if ( selectChart_jPanel == null ) {
			selectChart_jPanel = new JPanel() ;
			ButtonGroup bg = new ButtonGroup() ;
			bg.add(getCarichi_jRadioButton());
			bg.add(getJRadioButton());
			selectChart_jPanel.add(getCarichi_jRadioButton());
			
			selectChart_jPanel.add(getJRadioButton());
			selectChart_jPanel.add(new JLabel(" - ")) ;
		}
		return selectChart_jPanel ;
	}

	private JRadioButton getCarichi_jRadioButton() {
		if (carichi_jRadioButton == null) {
			carichi_jRadioButton = new JRadioButton();
			carichi_jRadioButton.setText("Caricati");
			carichi_jRadioButton.setSelected(true);
			carichi_jRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					grafici_chartPanel.setChart(caricati) ;
				}
				
			});
		}
		return carichi_jRadioButton;
	}
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setText("Giacenze");
			jRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					grafici_chartPanel.setChart(giacenze) ;
				}
				
			});
		}
		return jRadioButton;
	}
	
	JPanel grafici_jPanel = null ;
	ChartPanel grafici_chartPanel = null ;
	private JPanel getGrafici_JPanel() {
		
		if ( grafici_jPanel == null ) {
			
			grafici_chartPanel = new ChartPanel(caricati) ;
			 
			grafici_jPanel = new JPanel( new BorderLayout() ) ;
				
			grafici_jPanel.add( getFilter_JPanel(), BorderLayout.SOUTH ) ;
			
			grafici_jPanel.add( grafici_chartPanel , BorderLayout.CENTER ) ;
		}
		return grafici_jPanel ;
		
	}
	
	private void setLimits(String mese) {
		String m = mese ;
		stopUpdate = true ;
		FormattedDate d = new FormattedDate( getDataDa_jDateChooser().getDate() );
		String anno = d.getAnno() ;
		
		if ( mese.length() < 1 ) {
			getDataDa_jDateChooser().setDate( ((StoricoMovimenti)storico.firstElement()).getGiorno() ) ;
		}	
		else {
			
			if ( m.length() < 2 ) 
				m = "0" + m ;
			
			getDataDa_jDateChooser().setDate( new FormattedDate("01/"+m+"/" +anno + " 00:00:00" )) ;
		}
				
		stopUpdate = false ;
		
		if ( mese.length() < 1 ) {
			getDataA_jDateChooser().setDate( ((StoricoMovimenti)storico.lastElement()).getGiorno() ) ;
		}
		else {
			int n = Integer.valueOf(m) ;
			n++ ;
			m = "" + n ;
			if ( n < 10 ) 
				m = "0" + m ; 
			String endDay = "01/"+m+"/" + anno ;
			if ( n > 12 ) { 
				endDay = "01/01/" + ( Integer.valueOf(anno) + 1 ) ;
			}
			
			FormattedDate a = new FormattedDate( endDay + " 00:00:00" , "dd/MM/yyyy HH:mm:ss") ;
			
			getDataA_jDateChooser().setDate( new Date( a.getTime() - 86400000 ) ) ;
		}
		
	}
	
	JComboBox mesi_jComboBox = null ;
	private JComboBox getMesi_jComboBox() {
		if (mesi_jComboBox == null) {
			mesi_jComboBox = new JComboBox();
			
			mesi_jComboBox.addItem(" - - ");
			String [] mesi = {"Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno",
					"Luglio","Agosto","Settembre", "Ottobre", "Novembre", "Dicembre" };
			
			for ( int i = 0 ; i < mesi.length ; i++ ) {
				mesi_jComboBox.addItem(mesi[i]) ;
			}
			
			mesi_jComboBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int m  = mesi_jComboBox.getSelectedIndex() ;
					if ( m > 0 )
						setLimits( "" +  m ) ;
					else 
						setLimits("") ;
				}
			});
		}
		return mesi_jComboBox;
	}
	
    public AbstractSwg getSwg() {
        return swg ;
    }
    
//    public Rifiuto_detailedPanel(SwgRifiuto swg) {
//        super(new BorderLayout());
//        this.swg=swg;
//        this.setBackground(Color.WHITE);
//        try {
//            this.add(swg.getJNomePanel(),BorderLayout.NORTH);
//        } catch (Exception e) {
//            // XXX Blocco catch generato automaticamente
//            e.printStackTrace();
//        }
//        JScrollPane scrollPane = new JScrollPane(getJDatiPanel());
//        this.add(scrollPane,BorderLayout.CENTER);
//    }
 
    
    public JPanel getJDatiPanel() {
        if(jDatiPanel==null) {
        	String tipoRifiuto = "Rifiuto" ;
            if ( swg.getRifiuto().isScoria() )
                tipoRifiuto = "Scorie" ;

            jDatiPanel= new JPanel(new BorderLayout());
            swg.refreshJDatiPanel().setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
            jDatiPanel.add(swg.getJDatiPanel(),BorderLayout.CENTER);
            //jDatiPanel.add(new JPanel(),BorderLayout.CENTER);
            JPanel p = new JPanel();
            p.add(getEditJButton());
            jDatiPanel.add( p , BorderLayout.SOUTH) ;
            
            
            jDatiPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(20, 5, 200, 5),BorderFactory.createTitledBorder(tipoRifiuto)));
        }
        return jDatiPanel;
    }
    
    public static JFreeChart getChartGiacenze(Vector list) throws Exception {
        
        if ( list == null || list.size() < 1 ) 
            return null ;
        
//        Rifiuto rifiuto = ((StoricoMovimenti)list.firstElement()).getRifiuto() ;
        
        
//      create the dataset...
        
        
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        
        dataset.addSeries( getSerieGiacenze(list) );
        //dataset.setDomainIsPointsInTime(true);
        
//        String chartTitle = "Giacenze del rifiuto : " + rifiuto.getNome() ; 
//        DateAxis xAxis = new DateAxis("Giorno");
        NumberAxis yAxis = new NumberAxis("");

        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

//        StandardXYItemRenderer renderer = new StandardXYItemRenderer(
//            StandardXYItemRenderer.LINES + StandardXYItemRenderer.SHAPES );
//        renderer.setShapesFilled(true);
//        
//        renderer.setSeriesStroke(1, new BasicStroke(1.5f));
//        renderer.setSeriesStroke(2, new BasicStroke(1.5f));
        
        
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                null,
                null , // domain axis label
                "Kg", // range axis label
                dataset,
                false,
                true,
                true
                );
        
        XYPlot plot = chart.getXYPlot();
        XYItemRenderer renderer = plot.getRenderer();
        if (renderer instanceof StandardXYItemRenderer) {
	        StandardXYItemRenderer rr = (StandardXYItemRenderer) renderer;
	        rr.setShapesFilled(true);
	        rr.setSeriesStroke(0, new BasicStroke(2f));
        }
        if (renderer instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer rrr = (XYLineAndShapeRenderer) renderer;
            rrr.setBaseShapesVisible(true);
            rrr.setBaseShapesFilled(true);
        }

        return chart;
    }
    
    private static TimeSeries getSerieCarichi(Vector list) {
    	StoricoMovimenti s = null ;
    	sCarichi.setNotify(false);
    	sCarichi.clear();
        for( Iterator i = list.iterator() ; i.hasNext();  ) {
        	s = (StoricoMovimenti) i.next() ;
        	
        	if ( !i.hasNext() )
        		sCarichi.setNotify(true);
        	
            sCarichi.addOrUpdate( new Day(s.getGiorno()), s.getTotCaricato() );
        }
        
	    return sCarichi ;
    }
    
    private static TimeSeries getSerieGiacenze(Vector list) {
	    StoricoMovimenti s = null ;
	    sGiacenza.setNotify(false);
	    sGiacenza.clear();
	    for( Iterator i = list.iterator() ; i.hasNext();  ) {
	    	s = (StoricoMovimenti) i.next() ;
	    	
	    	if ( !i.hasNext() )
	    		sGiacenza.setNotify(true);
	    	
	        sGiacenza.addOrUpdate( new Day(s.getGiorno()), s.getGiacenza());
	        
	    }
	    
	    return sGiacenza ;
    }
    
    public static JFreeChart getChartCarichi(Vector list) throws Exception {
        
        if ( list == null || list.size() < 1 ) 
            return null ;
        
//        String series1 = "Carichi";
        
//        Rifiuto rifiuto = ((StoricoMovimenti)list.firstElement()).getRifiuto() ;
        
        
//      create the dataset...
        
        
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(getSerieCarichi(list));
        //dataset.setDomainIsPointsInTime(true);
        
//        String chartTitle = "Carichi del rifiuto : " + rifiuto.getNome() ; 
//        DateAxis xAxis = new DateAxis("Giorno");
        NumberAxis yAxis = new NumberAxis("");

        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

//        StandardXYItemRenderer renderer = new StandardXYItemRenderer(
//            StandardXYItemRenderer.LINES + StandardXYItemRenderer.SHAPES );
//        renderer.setShapesFilled(true);
//        
//        renderer.setSeriesStroke(1, new BasicStroke(1.5f));
//        renderer.setSeriesStroke(2, new BasicStroke(1.5f));
        
        
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                null, // chartTitle
                null , // domain axis label
                "Kg", // range axis label
                dataset,
                false,
                true,
                true
                );
        
        XYPlot plot = chart.getXYPlot();
        XYItemRenderer renderer = plot.getRenderer();

        if (renderer instanceof StandardXYItemRenderer) {
	        StandardXYItemRenderer rr = (StandardXYItemRenderer) renderer;
	        rr.setShapesFilled(true);
	        rr.setSeriesStroke(0, new BasicStroke(2f));
        }
        if (renderer instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer rrr = (XYLineAndShapeRenderer) renderer;
            rrr.setBaseShapesVisible(true);
            rrr.setBaseShapesFilled(true);
        }

        return chart ;
    }
    
	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 * @throws Exception
	 */    
	private JTabbedPane getJTabbedPane() throws Exception {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			
			if ( ! swg.getRifiuto().getCodiceQuadrelli().equals("0") ) {
				jTabbedPane.add("Scheda", getJDatiPanel());
            }
			int anno = Integer.valueOf( RifiutiFrame.getAnnoStatic()) ;
			FormattedDate from = new FormattedDate( "" + anno + "-01-01" ) ; 
			FormattedDate to = new FormattedDate( "" + (anno + 1) + "-01-01" ) ;
			
            storico = StoricoMovimenti.get( 
			        Integer.valueOf( swg.getRifiuto().getCodiceQuadrelli() ),
			        from, to, getSwg().getFrame().getDatabase() ) ;
            
            caricati = getChartCarichi(storico);
            giacenze = getChartGiacenze(storico);		    				 
				
			applicaFiltro(false, true);
				
			jTabbedPane.add("Grafici", getGrafici_JPanel());
		}
		return jTabbedPane;
	}
	
	
	private class DataFilter extends AbstractDataFilter{
        
        DataFilter(String descrizione ) {
            setDescrizione(descrizione);
        }
        
        public boolean check( Object c ) {
            return dataCheck(((StoricoMovimenti) c).getGiorno()) ;
        } 
        
    }
	
	private JPanel main_jPanel = null;
	private JPanel mesi_jPanel = null;
	private JPanel filtri_jPanel = null;
	private JLabel jLabel = null;
	private JLabel to_jLabel = null;
	
  
	private JPanel getFilter_JPanel() {
		if (filtri_jPanel == null) {
			filtri_jPanel = new JPanel(new BorderLayout());
			
			to_jLabel = new JLabel();
			jLabel = new JLabel();
			main_jPanel = new JPanel();
			jLabel.setText("dal ");
			to_jLabel.setText(" al ");
			
			main_jPanel.add( getSelectChart_jPanel() ) ;
			
			main_jPanel.add(jLabel, null);
			main_jPanel.add(getDataDa_jDateChooser(), null);
			main_jPanel.add(to_jLabel, null);
			main_jPanel.add(getDataA_jDateChooser(), null);
			
			//mesi_jPanel = new PulsantiMesi_jPanel(getDataDa_jDateChooser(), getDataA_jDateChooser());
			
			mesi_jPanel = new JPanel() ;
			mesi_jPanel.add( new JLabel(" Selezione rapida : ") ) ;
			mesi_jPanel.add( getMesi_jComboBox() ) ;
			
			filtri_jPanel.add( main_jPanel, BorderLayout.NORTH ) ;
			filtri_jPanel.add( mesi_jPanel, BorderLayout.CENTER ) ;
		}
		return filtri_jPanel;
	}
	
	private void applicaFiltro( boolean applica , boolean updateFilterDates ) {
	    try {
	        if ( applica && ! stopUpdate ) {
	        	FormattedDate[] date = parseFilterPanel() ;
		        filteredStorico = new Vector() ;
		        StoricoMovimenti s = null ;
		        boolean doneDa = false ;
		        for ( Iterator i = storico.iterator() ; i.hasNext() ; ) {
		             s = (StoricoMovimenti) i.next() ;
		             
		             if ( s.getGiorno().compareTo(date[0]) >= 0 && s.getGiorno().compareTo(date[1]) <= 0) {
		                 filteredStorico.add(s) ;
		             }
		             
		             if ( updateFilterDates  && ! doneDa ) {
		            	 	getDataDa_jDateChooser().setDate(s.getGiorno()) ;
		             }
		             if ( updateFilterDates  && ! i.hasNext() ) {
		            	 	getDataA_jDateChooser().setDate(s.getGiorno()) ;
		             }
		        }
		        
		        refreshGrafici() ;
		    }
		    else {
		        filteredStorico = storico ;
		        
		        if ( updateFilterDates  && storico != null && storico.size() > 0 ) {
            	 		getDataDa_jDateChooser().setDate(((StoricoMovimenti)storico.get(0)).getGiorno()) ;
            	 		getDataA_jDateChooser().setDate(((StoricoMovimenti)storico.lastElement()).getGiorno()) ;
		        }
		    }
	    }
	    catch ( Exception e ) {
	    	e.printStackTrace() ;
	       JOptionPane.showMessageDialog(this, e.getMessage()) ;
	    }
	}
	
	private void refreshGrafici() throws Exception {
	    
	    getSerieCarichi(filteredStorico) ;
	    getSerieGiacenze(filteredStorico) ;
	}
	
	private FormattedDate[] parseFilterPanel() throws Exception {
	    
	   FormattedDate[] res = new FormattedDate[2] ;
	    
	   try {
	       res[0] = new FormattedDate( getDataDa_jDateChooser().getDate() ) ;
	   }
	   catch ( Exception e ) {
		   e.printStackTrace() ;
	       throw new Exception("Attenzione la data iniziale inserita non e' corretta !") ;
	   }
	   
	   try {
	       res[1] = new FormattedDate( getDataA_jDateChooser().getDate() ) ;
	       res[1].setTime( res[1].getTime() + 86399000 );
	   }
	   catch ( Exception e ) {
		   e.printStackTrace() ;
	       throw new Exception("Attenzione la data finale inserita non e' corretta !") ;
	   }
	   
	   return res ;
	}
	
	JDateChooser dataDa_jDateChooser = null ;
	public JDateChooser getDataDa_jDateChooser() {
        if(dataDa_jDateChooser==null) {
        	dataDa_jDateChooser=new JDateChooser();
        	dataDa_jDateChooser.setDateFormatString("dd. MMMM yyyy");// "dd/MM/yyyy");
//        	dataDa_jDateChooser.getSpinner().addChangeListener(new ChangeListener() {
//
//				public void stateChanged(ChangeEvent e) {
//					applicaFiltro(true,false);
//				}
//    			
//    		}) ;
        }
        return dataDa_jDateChooser;
    }
	JDateChooser dataA_jDateChooser = null ;
	public JDateChooser getDataA_jDateChooser() {
        if(dataA_jDateChooser==null) {
        	dataA_jDateChooser=new JDateChooser();
        	dataA_jDateChooser.setDateFormatString("dd. MMMM yyyy");// "dd/MM/yyyy");
        	
//        		dataA_jDateChooser.getSpinner().addChangeListener(new ChangeListener() {
//
//					public void stateChanged(ChangeEvent e) {
//						applicaFiltro(true,false);
//					}
//        			
//        		}) ;
        }
        return dataA_jDateChooser;
    }
	
	
	private JButton editJButton = null ;
    private JButton getEditJButton() {
        if ( editJButton == null ) {
            editJButton  = new JButton("Modifica") ;
            editJButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) { 
				    swg.edit_clicked();
				}
			});
        }
        
        return editJButton ;
    }

 }
