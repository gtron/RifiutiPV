/*
 * Created on 11-set-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.front;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.gtsoft.rifiuti.data.Movimento;
import com.gtsoft.utils.reports.ReportManager;
/**
 * @author Gtron - 
 * 
 */
public class stampaRegistri_jDialog extends JDialog {

	private javax.swing.JPanel jContentPane = null;

	private JTabbedPane jTabbedPane = null;
	private JPanel prepare_jPanel = null;
	private JPanel cover_jPanel = null;
	private JPanel print_jPanel1 = null;
	
	private JLabel msgPrepare_jLabel = null;
	private JTextField prepare_Da_jTextField = null;
	private JLabel prepare_num_jLabel = null;
	private JTextField prepare_num_jTextField = null;
	private JPanel control_jPanel = null;
	private JButton close_jButton = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JButton print_jButton = null;
	private JPanel dettagliDiStampa_jPanel = null;
	private JPanel jPanel3 = null;
	private JButton printRegistro_jButton = null;
	private JButton testPrint_jButton = null;
	private JLabel numStart_jLabel = null;
	private JTextField numStart_jTextField = null;
	private JLabel numEnd_jLabel = null;
	private JTextField numEnd_jTextField = null;
	
	private RifiutiFrame ownerFrame = null ;
	
	private Vector daStampareRegistro = new Vector() ;
	
	private int primoDaStampare = 0 ;
	
	private JButton cover_jButton = null;
	
	private boolean buttonClicked = false ;
	
	/**
	 * This is the default constructor
	 * @param frame
	 */
	public stampaRegistri_jDialog(RifiutiFrame frame) {
		super();
		
		ownerFrame = frame ;
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("Stampa Registri");
		this.addWindowListener(new MyWindowsListener());
		
		setModal(true);
		this.setSize(400, 220);
		Dimension screensz = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation( 
		        Math.max(0, (screensz.width - this.getWidth()) / 2) ,
		        Math.max(0, (screensz.height - this.getHeight()) / 2) ) ;
		this.setContentPane(getJContentPane());
		//this.setAlwaysOnTop(true) ;
		
		
		daStampareRegistro = ownerFrame.getSwgMovimento().getDaStampareRegistro();
	    
		Movimento primo, ultimo ;
	    primo = ultimo = null ;
	    if ( daStampareRegistro.size() > 0  ) {
	        primo = (Movimento) daStampareRegistro.lastElement() ;
	        ultimo = (Movimento) daStampareRegistro.firstElement() ;
	    }
	    
	    if ( primo != null ) {
	        numStart_jTextField.setText("" + primo.getNumProgressivo()) ;
	        primoDaStampare = primo.getNumProgressivo();
	    }
	    if ( ultimo != null )
	        numEnd_jTextField.setText("" + ultimo.getNumProgressivo()) ;
	}
	
	public void centerOnOwner(Frame owner){
	    Rectangle rectOwner, rectDialog;

	    rectOwner = owner.getBounds();
	    rectDialog = this.getBounds();
	    setLocation(rectOwner.x + rectOwner.width /2 - rectDialog.width /2,
	    rectOwner.y + rectOwner.height/2 - rectDialog.height /2);
	    }
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(8,4,4,4));
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getControl_jPanel(), java.awt.BorderLayout.SOUTH);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */    
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab(" Preparazione Pagine ", null, getPrepare_jPanel(), null);
			jTabbedPane.addTab(" Copertina", null, getCover_jPanel(), null);
			jTabbedPane.addTab(" Stampa Registro ", null, getPrint_jPanel1(), null);
			
		}
		return jTabbedPane;
	}
				
			
			

	private JPanel getPrepare_jPanel() {
	    
	    if ( prepare_jPanel == null ) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			prepare_jPanel = new JPanel() ;	
			prepare_jPanel.setLayout(new GridBagLayout());
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
			prepare_jPanel.add(getJPanel(), gridBagConstraints1);
			prepare_jPanel.add(getJPanel1(), gridBagConstraints2);
	    }
	    return prepare_jPanel ;
	}
	
	
	private JPanel getCover_jPanel() {
	    
	    if ( cover_jPanel == null ) {
			
	        GridBagConstraints gbc = new GridBagConstraints();
	        cover_jPanel = new JPanel(new BorderLayout()) ;	
			
			JPanel container = new JPanel();
			container.setLayout(new GridBagLayout());
			gbc.gridx = 0;
			gbc.gridy = 0;
			
			
			container.add(getCover_jButton(), gbc);
			
			cover_jPanel.add(container);
			
	    }
	    return cover_jPanel ;
	}
	
	
	
	
	private JPanel getPrint_jPanel1() {
		if (print_jPanel1 == null) {
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			print_jPanel1 = new JPanel();
			print_jPanel1.setLayout(new GridBagLayout());
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.gridy = 0;
			gridBagConstraints21.gridx = 0;
			gridBagConstraints21.gridy = 1;
			print_jPanel1.add(getJPanel2(), gridBagConstraints11);
			print_jPanel1.add(getJPanel3(), gridBagConstraints21);
		}
		return print_jPanel1;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JLabel getMsgPrepare_jLabel() {
		if (msgPrepare_jLabel == null) {
			msgPrepare_jLabel = new JLabel();
			msgPrepare_jLabel.setText("Numero progressivo di partenza : ");
			msgPrepare_jLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		}
		return msgPrepare_jLabel;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getPrepare_Da_jTextField() {
		if (prepare_Da_jTextField == null) {
			prepare_Da_jTextField = new JTextField();
			prepare_Da_jTextField.setPreferredSize(new java.awt.Dimension(80,20));
			prepare_Da_jTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		}
		return prepare_Da_jTextField;
	}
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JLabel getPrepareNumJLabel() {
		if (prepare_num_jLabel == null) {
			prepare_num_jLabel = new JLabel();
			prepare_num_jLabel.setText("Numero di pagine da stampare : ");
			prepare_num_jLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		}
		return prepare_num_jLabel;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getPrepare_num_jTextField() {
		if (prepare_num_jTextField == null) {
			prepare_num_jTextField = new JTextField();
			prepare_num_jTextField.setPreferredSize(new java.awt.Dimension(80,20));
			prepare_num_jTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		}
		return prepare_num_jTextField;
	}
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getControl_jPanel() {
		if (control_jPanel == null) {
			FlowLayout flowLayout6 = new FlowLayout();
			control_jPanel = new JPanel();
			control_jPanel.setLayout(flowLayout6);
			flowLayout6.setAlignment(java.awt.FlowLayout.RIGHT);
			control_jPanel.add(getClose_jButton(), null);
		}
		return control_jPanel;
	}
	/**
	 * This method initializes cover_jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getClose_jButton() {
		if (close_jButton == null) {
			close_jButton = new JButton();
			close_jButton.setText("Chiudi");
			close_jButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			close_jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
	            		ownerFrame.setAnnoCorrente() ;
	            		setVisible(false);
	            		dispose();
	            	}
	            	catch ( Exception ex ) {
	            		ex.printStackTrace() ;
	            	}
				}
			});
		}
		return close_jButton;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints33 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			gridBagConstraints30.gridx = 0;
			gridBagConstraints30.gridy = 0;
			gridBagConstraints30.insets = new java.awt.Insets(5,5,5,5);
			gridBagConstraints30.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints31.gridx = 0;
			gridBagConstraints31.gridy = 1;
			gridBagConstraints31.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints31.insets = new java.awt.Insets(5,5,5,5);
			gridBagConstraints32.gridx = 1;
			gridBagConstraints32.gridy = 1;
			gridBagConstraints32.weightx = 1.0;
			gridBagConstraints32.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints32.ipady = 0;
			gridBagConstraints32.ipadx = 2;
			gridBagConstraints33.gridx = 1;
			gridBagConstraints33.gridy = 0;
			gridBagConstraints33.weightx = 1.0;
			gridBagConstraints33.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints33.ipady = 0;
			gridBagConstraints33.ipadx = 2;
			gridBagConstraints33.insets = new java.awt.Insets(0,0,0,0);
			jPanel.add(getMsgPrepare_jLabel(), gridBagConstraints30);
			jPanel.add(getPrepareNumJLabel(), gridBagConstraints31);
			jPanel.add(getPrepare_num_jTextField(), gridBagConstraints32);
			jPanel.add(getPrepare_Da_jTextField(), gridBagConstraints33);
		}
		return jPanel;
	}
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			FlowLayout flowLayout5 = new FlowLayout();
			jPanel1 = new JPanel();
			jPanel1.setLayout(flowLayout5);
			flowLayout5.setAlignment(java.awt.FlowLayout.RIGHT);
			flowLayout5.setHgap(0);
			jPanel1.add(getPrint_jButton(), null);
		}
		return jPanel1;
	}
	/**
	 * This method initializes cover_jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getPrint_jButton() {
		if (print_jButton == null) {
			print_jButton = new JButton();
			print_jButton.setText("Stampa");
			print_jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
				    try {
				        print_jButton.setEnabled(false) ;
				        ReportManager.printPageNumber( 
				           Integer.valueOf( prepare_Da_jTextField.getText() ),
				           Integer.valueOf( prepare_num_jTextField.getText() )
				           );
				        setVisible(false);
				    }
				    catch (Exception ex) {
				        setVisible(false) ;
				        JOptionPane.showMessageDialog( getOwner() , "Attenzione: i valori inseriti non sono corretti !",
                                "Attenzione",
                                JOptionPane.WARNING_MESSAGE);
				        setVisible(true) ;
                    }
				}
			});
		}
		return print_jButton;
	}
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel2() {
		if (dettagliDiStampa_jPanel == null) {
			numStart_jLabel = new JLabel();
			numEnd_jLabel = new JLabel();
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			dettagliDiStampa_jPanel = new JPanel();
			dettagliDiStampa_jPanel.setLayout(new GridBagLayout());
			numStart_jLabel.setText("Numero progressivo di partenza");
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.insets = new java.awt.Insets(5,5,5,5);
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.gridy = 0;
			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints4.insets = new java.awt.Insets(5,0,5,5);
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 1;
			numEnd_jLabel.setText("Numero progressivo finale");
			gridBagConstraints6.gridx = 1;
			gridBagConstraints6.gridy = 1;
			gridBagConstraints6.weightx = 1.0;
			gridBagConstraints6.fill = java.awt.GridBagConstraints.NONE;
			gridBagConstraints6.insets = new java.awt.Insets(5,0,5,5);
			gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.insets = new java.awt.Insets(5,5,5,5);
			dettagliDiStampa_jPanel.add(numStart_jLabel, gridBagConstraints3);
			dettagliDiStampa_jPanel.add(getNumStart_jTextField(), gridBagConstraints4);
			dettagliDiStampa_jPanel.add(numEnd_jLabel, gridBagConstraints5);
			dettagliDiStampa_jPanel.add(getNumEnd_jTextField(), gridBagConstraints6);
		}
		return dettagliDiStampa_jPanel;
	}
	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.add(getTestPrint_jButton(), null);
			jPanel3.add(getPrintRegistro_jButton(), null);
		}
		return jPanel3;
	}
	/**
	 * This method initializes cover_jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getPrintRegistro_jButton() {
		if (printRegistro_jButton == null) {
			printRegistro_jButton = new JButton();
			printRegistro_jButton.setText("Stampa Registro");
			printRegistro_jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
				    print_Clicked();
				}
			});
		}
		return printRegistro_jButton;
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getTestPrint_jButton() {
		if (testPrint_jButton == null) {
			testPrint_jButton = new JButton();
			testPrint_jButton.setText("Stampa di Prova");
			testPrint_jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
				    testPrint_Clicked();
				    setVisible(false);
				}
			});
		}
		return testPrint_jButton;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getNumStart_jTextField() {
		if (numStart_jTextField == null) {
			numStart_jTextField = new JTextField();
			numStart_jTextField.setPreferredSize(new java.awt.Dimension(40,20));
		}
		return numStart_jTextField;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getNumEnd_jTextField() {
		if (numEnd_jTextField == null) {
			numEnd_jTextField = new JTextField();
			numEnd_jTextField.setPreferredSize(new java.awt.Dimension(40,20));
		}
		return numEnd_jTextField;
	}

	private void testPrint_Clicked() {
		buttonClicked = true ;
	    try {
	    	Integer start , end ;
		    Vector list = getDaStampare(false) ;
		    if ( list != null && list.size() > 0)
		        ReportManager.printMovimenti(list) ;
		    else
		        errorUIData("Nessun movimento da stampare");
		    
		}
	    catch( Exception e ) {
	        errorUIData(e.getMessage());
	    }
	}
	
	private void print_Clicked() {
		buttonClicked = true ;
	    Integer start , end ;
	    
	    try {
		    Vector list = getDaStampare(true) ;
		    if ( list != null && list.size() > 0) {
		        ReportManager.printRegistro(list, ownerFrame ) ;
		        setVisible(false);
		    }
		    else
		        errorUIData("Nessun movimento da stampare");
	    }
	    catch( Exception e ) {
	        errorUIData(e.getMessage());
	        setVisible(false);
	    }
	   }
	
	private void checkUI_PrintData(Integer start , Integer end, boolean checkPrimo) throws Exception {
	    
	    if ( start.intValue() > end.intValue() ) {
	        throw new Exception("Il numero progressivo di partenza è maggiore di quello finale.") ;
	    }
	    if ( checkPrimo && start.intValue() > primoDaStampare ) {
	        getNumStart_jTextField().setText("" + primoDaStampare ) ;
	        throw new Exception("I movimenti precedenti a quello specificato " +
	        		" come iniziale non sono ancora stati stampati.") ;
	    }
	}
	private void errorUIData(String e) {
	    String err = "I dati inseriti non sono corretti." ;
	    if ( e != null && e != "" )
	        err = e ;
	    
	    JOptionPane.showMessageDialog(this, err ,
                " Attenzione ",
                JOptionPane.WARNING_MESSAGE );
	}
	
	private Vector getDaStampare(boolean checkRange) throws Exception {
	    
	    Integer start = null ;
	    Integer end = null ;
	    try {
	        start = Integer.valueOf( numStart_jTextField.getText() ) ;
	        end = Integer.valueOf( numEnd_jTextField.getText() ) ;
	    }
	    catch ( Exception e ) {
	        errorUIData("I dati inseriti non sono corretti") ;
	    }
        
	    checkUI_PrintData(start, end, checkRange);
        
        int from, to ; from = start.intValue(); to = end.intValue();
	    
	    Vector v = new Vector( to - from ) ;
	    Movimento m = null ;
	    
	    Vector daStampare = daStampareRegistro ;
	    
	    if ( ! checkRange ) {
	        daStampare = ownerFrame.getSwgMovimento().getElementi() ;
	    }
	    
	    for ( Iterator i = daStampare.iterator() ; i.hasNext() ;) {
	        m = (Movimento) i.next();
	       if ( m != null && m.getNumProgressivo() <= to ) {
	           if ( m.getNumProgressivo() >= from ) {
	               v.add(m);
	           }
	           else
	               break;

	       }
	    }
	    
	    Collections.reverse(v) ;
	    return v ;
	    
	}
	
	/**
	 * This method initializes cover_jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getCover_jButton() {
		if (cover_jButton == null) {
			cover_jButton = new JButton();
			cover_jButton.setText("Stampa Copertina");
			cover_jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					printCover_clicked();
				}
			});
		}
		return cover_jButton;
	}

	private void printCover_clicked() {
		buttonClicked = true ;
	    ReportManager.printCover();
	    setVisible(false);
	}
	
	private class MyWindowsListener extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
//        	Gt - Disabilitato xke mi sembra inutile 
//        	e causa un refresh dell'anno sbagliato se non ho selezionato l'anno attuale
//            if ( ! buttonClicked ) {
//            	try {
//            		ownerFrame.setAnnoCorrente() ;
//            	}
//            	catch ( Exception ex ) {
//            		ex.printStackTrace() ;
//            	}
//            }
        }
    }
}  //  @jve:decl-index=0:visual-constraint="10,10"