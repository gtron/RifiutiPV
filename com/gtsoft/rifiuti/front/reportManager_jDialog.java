/*
 * Created on 11-set-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.front;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.filechooser.FileFilter;

import com.gtsoft.rifiuti.data.Movimento;
import com.gtsoft.rifiuti.data.Rifiuto;
import com.gtsoft.utils.SwingWorker;
import com.gtsoft.utils.reports.ReportManager;
/**
 * @author Gtron - 
 * 
 */
public class reportManager_jDialog extends JDialog {
    
    private javax.swing.JPanel jContentPane = null;
    
    private JPanel control_jPanel = null;
    private JButton close_jButton = null;
    private RifiutiFrame ownerFrame = null ;
    
//    private Vector daStampareRegistro = new Vector() ;
//    
//    private int primoDaStampare = 0 ;
    
    private JButton movimenti_stampa_jButton = null;
    private JButton movimenti_xls_jButton1 = null;
    private JButton movimenti_pdf_jButton = null;
    private JRadioButton all_jRadioButton = null;
    private JRadioButton selected_jRadioButton = null;
    private JPanel elementi_jPanel = null;
    private JPanel tipoRifiuti_jPanel = null;
    private JRadioButton tuttiRifiuti_jRadioButton = null;
    private JRadioButton soloFumi_jRadioButton = null;
    private JRadioButton soloScorie_jRadioButton = null;
    private JPanel main_jPanel = null;
    private JPanel tipoReport_jPanel = null;
    private JRadioButton movimenti_jRadioButton = null;
    private JRadioButton rifiuti_jRadioButton = null;
    
    
    private JCheckBox ignoraTraspUnico_jCheckBox = null;
    
    private ButtonGroup grpTipoRifiuti = new ButtonGroup();
    private ButtonGroup grpTipoReport = new ButtonGroup();
    private ButtonGroup grpSelezionati = new ButtonGroup();
    /**
     * This is the default constructor
     * @param frame
     */
    public reportManager_jDialog(RifiutiFrame frame) {
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
        
        this.setAlwaysOnTop(true);
        
        this.setTitle("Stampa Reportistica");
        this.setSize(465, 214);
        this.setResizable(false);
        Dimension screensz = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation( 
                Math.max(0, (screensz.width - this.getWidth()) / 2) ,
                Math.max(0, (screensz.height - this.getHeight()) / 2) ) ;
        this.setContentPane(getJContentPane());
        //this.setAlwaysOnTop(true) ;
        
        
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
            jContentPane.add(getControl_jPanel(), java.awt.BorderLayout.SOUTH);
            jContentPane.add(getMain_jPanel(), java.awt.BorderLayout.CENTER);
        }
        return jContentPane;
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
            control_jPanel.add(getMovimenti_stampa_jButton(), null);
            control_jPanel.add(getMovimenti_xls_jButton1(), null);
            control_jPanel.add(getMovimenti_pdf_jButton(), null);
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
                    setVisible(false);
                }
            });
        }
        return close_jButton;
    }
    
    
    /**
     * This method initializes jButton	
     * 	
     * @return javax.swing.JButton	
     */    
    private JButton getMovimenti_stampa_jButton() {
        if (movimenti_stampa_jButton == null) {
            movimenti_stampa_jButton = new JButton();
            movimenti_stampa_jButton.setName("");
            movimenti_stampa_jButton.setText("Stampa...");
            movimenti_stampa_jButton.addActionListener(new java.awt.event.ActionListener() { 
                public void actionPerformed(java.awt.event.ActionEvent e) {    
                    stampa(null);
                }
            });
        }
        return movimenti_stampa_jButton;
    }
    /**
     * This method initializes jButton1	
     * 	
     * @return javax.swing.JButton	
     */    
    private JButton getMovimenti_xls_jButton1() {
        if (movimenti_xls_jButton1 == null) {
            movimenti_xls_jButton1 = new JButton();
            movimenti_xls_jButton1.setText("Esporta per Excel...");
            movimenti_xls_jButton1.addActionListener(new java.awt.event.ActionListener() { 
                public void actionPerformed(java.awt.event.ActionEvent e) {    
                    stampa("xls") ;
                }
            });
        }
        return movimenti_xls_jButton1;
    }
    /**
     * This method initializes jButton2	
     * 	
     * @return javax.swing.JButton	
     */    
    private JButton getMovimenti_pdf_jButton() {
        if (movimenti_pdf_jButton == null) {
            movimenti_pdf_jButton = new JButton();
            movimenti_pdf_jButton.setText("Esporta in PDF...");
            movimenti_pdf_jButton.addActionListener(new java.awt.event.ActionListener() { 
                public void actionPerformed(java.awt.event.ActionEvent e) {    
                    stampa("pdf") ;
                }
            });
        }
        return movimenti_pdf_jButton;
    }
    /**
     * This method initializes tuttiRifiuti_jRadioButton	
     * 	
     * @return javax.swing.JRadioButton	
     */    
    private JRadioButton getAll_jRadioButton() {
        if (all_jRadioButton == null) {
            all_jRadioButton = new JRadioButton("Tutti gli elementi",  false );
        }
        return all_jRadioButton;
    }
    /**
     * This method initializes soloFumi_jRadioButton	
     * 	
     * @return javax.swing.JRadioButton	
     */    
    private JRadioButton getSelected_jRadioButton() {
        if (selected_jRadioButton == null) {
            selected_jRadioButton = new JRadioButton("Solo elementi selezionati", true);
            selected_jRadioButton.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        }
        return selected_jRadioButton;
    }
    /**
     * This method initializes jPanel	
     * 	
     * @return javax.swing.JPanel	
     */    
    private JPanel getElementi_jPanel() {
        if (elementi_jPanel == null) {
            elementi_jPanel = new JPanel();
            elementi_jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Elementi da stampare : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
            elementi_jPanel.add(getSelected_jRadioButton(), null);
            elementi_jPanel.add(getAll_jRadioButton(), null);
            
            grpSelezionati.add(getSelected_jRadioButton());
            grpSelezionati.add(getAll_jRadioButton());
        }
        return elementi_jPanel;
    }
    /**
     * This method initializes jPanel	
     * 	
     * @return javax.swing.JPanel	
     */    
    private JPanel getTipoRifiuti_jPanel() {
        if (tipoRifiuti_jPanel == null) {
            tipoRifiuti_jPanel = new JPanel();
            tipoRifiuti_jPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tipo di rifiuti :", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null), javax.swing.BorderFactory.createEmptyBorder(0,0,0,0)));
            
            if ( ! RifiutiFrame.isRifiuto342 ) {
            	
	            if ( ! RifiutiFrame.separateFumi || RifiutiFrame.isFumi ) {
	            	tipoRifiuti_jPanel.add(getSoloFumi_jRadioButton(), null);
	            	
	            	if ( RifiutiFrame.isFumi )
	            		getSoloFumi_jRadioButton().setSelected(true) ;
	            	
	            	grpTipoRifiuti.add( getSoloFumi_jRadioButton());
	            }
            
           
	            if ( ! RifiutiFrame.separateFumi || ! RifiutiFrame.isFumi ) {
	            	tipoRifiuti_jPanel.add(getSoloScorie_jRadioButton(), null);
	            	
	            	if ( RifiutiFrame.separateFumi && ! RifiutiFrame.isFumi )
	            		getSoloScorie_jRadioButton().setSelected(true) ;
	            	
	            	grpTipoRifiuti.add( getSoloScorie_jRadioButton());
	            }
            }
            else {
            	getTuttiRifiuti_jRadioButton().setSelected(true);
            }
            
            if ( ! RifiutiFrame.separateFumi ) {
            	tipoRifiuti_jPanel.add(getTuttiRifiuti_jRadioButton(), null);
            	grpTipoRifiuti.add( getTuttiRifiuti_jRadioButton());
            }
            
        }
        return tipoRifiuti_jPanel;
    }
    /**
     * This method initializes tuttiRifiuti_jRadioButton	
     * 	
     * @return javax.swing.JRadioButton	
     */    
    private JRadioButton getTuttiRifiuti_jRadioButton() {
        if (tuttiRifiuti_jRadioButton == null) {
            tuttiRifiuti_jRadioButton = new JRadioButton("Tutti");
        }
        return tuttiRifiuti_jRadioButton;
    }
    /**
     * This method initializes soloFumi_jRadioButton	
     * 	
     * @return javax.swing.JRadioButton	
     */    
    private JRadioButton getSoloFumi_jRadioButton() {
        if (soloFumi_jRadioButton == null) {
            soloFumi_jRadioButton = new JRadioButton("Fumi", true);
        }
        return soloFumi_jRadioButton;
    }
    /**
     * This method initializes soloScorie_jRadioButton	
     * 	
     * @return javax.swing.JRadioButton	
     */    
    private JRadioButton getSoloScorie_jRadioButton() {
        if (soloScorie_jRadioButton == null) {
            soloScorie_jRadioButton = new JRadioButton();
            soloScorie_jRadioButton.setText("Scorie");
            soloScorie_jRadioButton.setName("tipoRifiuti");
        }
        return soloScorie_jRadioButton;
    }
    /**
     * This method initializes jPanel1	
     * 	
     * @return javax.swing.JPanel	
     */    
    private JPanel getMain_jPanel() {
        if (main_jPanel == null) {
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            main_jPanel = new JPanel();
            main_jPanel.setLayout(new GridBagLayout());
            gridBagConstraints4.gridx = 1;
            gridBagConstraints4.gridy = 0;
            gridBagConstraints1.gridx = 1;
            gridBagConstraints1.gridy = 0;
            gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints1.gridheight = 1;
            gridBagConstraints2.gridx = 1;
            gridBagConstraints2.gridy = 1;
            gridBagConstraints2.gridwidth = 1;
            gridBagConstraints2.insets = new java.awt.Insets(4,0,0,0);
            gridBagConstraints11.gridx = 0;
            gridBagConstraints11.gridy = 0;
            gridBagConstraints11.gridheight = 2;
            gridBagConstraints11.fill = java.awt.GridBagConstraints.VERTICAL;
            gridBagConstraints11.insets = new java.awt.Insets(0,0,0,4);
            gridBagConstraints11.gridwidth = 1;
            main_jPanel.add(getTipoRifiuti_jPanel(), gridBagConstraints1);
            main_jPanel.add(getElementi_jPanel(), gridBagConstraints2);
            main_jPanel.add(getTipoReport_jPanel(), gridBagConstraints11);
        }
        return main_jPanel;
    }
    /**
     * This method initializes jPanel	
     * 	
     * @return javax.swing.JPanel	
     */    
    private JPanel getTipoReport_jPanel() {
        if (tipoReport_jPanel == null) {
            tipoReport_jPanel = new JPanel();
            tipoReport_jPanel.setLayout(new BoxLayout(tipoReport_jPanel, BoxLayout.Y_AXIS));
            tipoReport_jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tipo Report:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
            tipoReport_jPanel.add(getMovimenti_jRadioButton(), null);
            tipoReport_jPanel.add(getRifiuti_jRadioButton(), null);
            
            tipoReport_jPanel.add(getIgnoraTraspUnico_jCheckBox(), null);
            
            grpTipoReport.add( getMovimenti_jRadioButton());
            grpTipoReport.add(getRifiuti_jRadioButton());
        }
        return tipoReport_jPanel;
    }
    /**
     * This method initializes jRadioButton3	
     * 	
     * @return javax.swing.JRadioButton	
     */    
    private JRadioButton getMovimenti_jRadioButton() {
        if (movimenti_jRadioButton == null) {
            movimenti_jRadioButton = new JRadioButton("Dettaglio",true);
            movimenti_jRadioButton.addActionListener(new java.awt.event.ActionListener() { 
                public void actionPerformed(java.awt.event.ActionEvent e) {    
                	getIgnoraTraspUnico_jCheckBox().setEnabled( getRifiuti_jRadioButton().isSelected() );
                }
            });
        }
        return movimenti_jRadioButton;
    }
    /**
     * This method initializes jRadioButton4	
     * 	
     * @return javax.swing.JRadioButton	
     */    
    private JRadioButton getRifiuti_jRadioButton() {
        if (rifiuti_jRadioButton == null) {
            rifiuti_jRadioButton = new JRadioButton("Sintetico");
            rifiuti_jRadioButton.addActionListener(new java.awt.event.ActionListener() { 
                public void actionPerformed(java.awt.event.ActionEvent e) {    
                	getIgnoraTraspUnico_jCheckBox().setEnabled( rifiuti_jRadioButton.isSelected() );
                }
            });
        }
        return rifiuti_jRadioButton;
    }
    
    
    
    private JCheckBox getIgnoraTraspUnico_jCheckBox() {
        if (ignoraTraspUnico_jCheckBox == null) {
        	ignoraTraspUnico_jCheckBox = new JCheckBox("Ignora Trasp.Unico");
        	ignoraTraspUnico_jCheckBox.setEnabled( getRifiuti_jRadioButton().isSelected() );
        }
        return ignoraTraspUnico_jCheckBox;
    }
    
    
    private JDialog getThis() {
        return this ;
    }
    private String currentExt ;
    private void stampa( String ext ) {
        currentExt = ext ;
        
        SwingWorker worker = new SwingWorker(){
            public Object construct() {
                readGui() ;
                String filename = null ;
                
                try {
                    if ( currentExt != null ) {
                        filename = askFilename(currentExt) ;
                    }
                    
                    Vector list = getElements( ! allElements, tipoRifiuti ) ;
                    
                    if ( list.size() < 1 ) {
                        throw new Exception("Nessun elemento da stampare");
                    }
                    ownerFrame.showElaboration();
                    ownerFrame.setWait(10);
                    setVisible(false);
                    ReportManager.stampa(tipoReport, filename, list, ignoreSingoloTrasportatore) ;
                    ownerFrame.setWait(98);
                }
                catch ( Exception e ) {
                    JOptionPane.showMessageDialog(getThis(), e.getMessage(),
                            " Attenzione ",
                            JOptionPane.WARNING_MESSAGE );
                }
                finally {
                    setVisible(false);
                    ownerFrame.hideElaboration() ;
                }
                return null;
            }
        };
        
        worker.start(); 
    }
    
    
    private Vector getElements( boolean onlySelected , int tipoRifiuti ) throws Exception {
        Vector list = ownerFrame.getSwgMovimento().getPrintElements(onlySelected) ;
        
        Vector out  ;
        if ( tipoRifiuti == RifiutiDaStampare.FUMI ) {
             out = new Vector(100) ;
            Movimento m = null ;
            for ( Iterator i = list.iterator() ; i.hasNext() ; ) {
                m = (Movimento) i.next() ;
                
                if ( ((Rifiuto) m.getMerce()).getTipo().equalsIgnoreCase("F") )
                    out.add( m );
            }
        }
        else if ( tipoRifiuti == RifiutiDaStampare.SCORIE) {
            Movimento m = null ;
            out = new Vector(100) ;
            for ( Iterator i = list.iterator() ; i.hasNext() ; ) {
                m = (Movimento) i.next() ;
                
                if ( ((Rifiuto) m.getMerce()).getTipo().equalsIgnoreCase("S") )
                    out.add( m );
            }
        }
        else 
            return list ;
        
        return out ;
    }
    
    private int tipoReport = -1 ;
    private int tipoRifiuti = -1 ;
    private boolean allElements = false ;
    private boolean ignoreSingoloTrasportatore = false ;
    
    private void readGui () {
        tipoReport = ( 
                getMovimenti_jRadioButton().isSelected() ? 
                        ReportManager.STAMPA.ELENCO_MOVIMENTI :
                            ReportManager.STAMPA.ELENCO_RIFIUTI ) ;
        
        allElements = getAll_jRadioButton().isSelected() ;
        
        if ( getIgnoraTraspUnico_jCheckBox().isEnabled() ) 
        	ignoreSingoloTrasportatore = getIgnoraTraspUnico_jCheckBox().isSelected();
        
        if ( getSoloScorie_jRadioButton().isSelected() )
            tipoRifiuti = RifiutiDaStampare.SCORIE ;
        else if ( getSoloFumi_jRadioButton().isSelected() )
            tipoRifiuti = RifiutiDaStampare.FUMI ;
        else 
            tipoRifiuti = RifiutiDaStampare.TUTTI ;
        
        
    }
    
    public static class RifiutiDaStampare {
        public static int TUTTI = 1 ;
        public static int FUMI = 2 ;
        public static int SCORIE = 3 ;
    }
    
    public String askFilename ( String ext ) throws Exception {
        
        String nomefile = "" ;
        JFileChooser fileChooser = new JFileChooser( );
        fileChooser.setApproveButtonText("Salva") ;
        
        String extension = "" ;
        if ( ext.equalsIgnoreCase("pdf")) {
            PdfFilter fileFilter = new PdfFilter() ;
            fileChooser.setDialogTitle(fileFilter.getTitle());
            fileChooser.addChoosableFileFilter(fileFilter);
            fileChooser.setFileFilter(fileFilter);
            extension = fileFilter.getExtension();
        }
        else {
            XlsFilter fileFilter = new XlsFilter() ;
            fileChooser.setDialogTitle(fileFilter.getTitle());
            fileChooser.addChoosableFileFilter(fileFilter);
            fileChooser.setFileFilter(fileFilter);
            extension = fileFilter.getExtension();
        }
        
        // Open file dialog.
        fileChooser.showOpenDialog(this);
        
        File file = fileChooser.getSelectedFile() ;
        
        if (file != null) {
            nomefile = file.getAbsolutePath() ;
            
            if ( ! nomefile.endsWith(extension) )
                nomefile += extension ;
        }
        else {
            throw new Exception("Esportazione annullata.") ;
        }
        
        return nomefile ;
        
    }
    
    public class XlsFilter extends FileFilter {
        public boolean accept(File file) {
            String filename = file.getName();
            return filename.endsWith(".xls");
        }
        public String getDescription() {
            return "Microsoft Excel  (*.xls)";
        }
        public String getExtension() {
            return ".xls" ;
        }
        public String getTitle() {
            return "Esportazione in Excel ... " ;
        }
    }
    
    public class PdfFilter extends FileFilter {
        public boolean accept(File file) {
            String filename = file.getName();
            return filename.endsWith(".pdf");
        }
        public String getDescription() {
            return "Adobe PDF  (*.pdf)";
        }
        public String getExtension() {
            return ".pdf" ;
        }
        public String getTitle() {
            return "Esportazione in PDF ... " ;
        }
    }
}  //  @jve:decl-index=0:visual-constraint="62,13"


