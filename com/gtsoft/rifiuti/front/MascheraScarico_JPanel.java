/*
 * Created on 28-lug-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.front;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.gtsoft.rifiuti.data.Carico;
import com.gtsoft.rifiuti.data.Movimento;
import com.gtsoft.rifiuti.data.Rifiuto;
import com.gtsoft.rifiuti.data.Vettore;
import com.gtsoft.rifiuti.front.data.SwgScarico;
import com.gtsoft.utils.FormattedDate;
import com.toedter.calendar.JDateChooser;
/**
 * @author Gtron - 
 * 
 */
public class MascheraScarico_JPanel extends JPanel {
    
    private RifiutiFrame frame ;
    
    private Vector listaDaScaricare ;
    private Carico movimentoSelezionato ;
    
    protected JMainTable swgTable = null;
    protected String[] campiJTable = null;
    
    private JLabel target_jLabel = null;
    private JTextField target_jTextField = null;
    private JLabel selezionati_jLabel = null;
    private JTextField selezionati_jTextField = null;
    private JTextField jTextField = null;
    private JLabel differenza_jLabel = null;
    private JButton jButton = null;
    private JScrollPane jScrollPane = null;
    private JButton add_jButton = null;
    private JPanel add_remove_jPanel = null;
    private JButton remove_jButton = null;
    private JButton remove_all_jButton=null;
    private JPanel rifiuto_jPanel = null;
    private JLabel cer_jLabel = null;
    private JTextField cer_jTextField = null;
    private JLabel rifiuto_jLabel = null;
    private JComboBox rifiuto_jComboBox = null;
    private JPanel movimenti_jPanel = null;
    private JPanel jPanel = null;
    //private MascheraScaricoFocusListener mascheraScaricoFocusListener=new MascheraScaricoFocusListener();
    private MascheraScaricoActionListener mascheraScaricoActionListener= new MascheraScaricoActionListener();
    private MascheraScaricoKeyListener mascheraScaricoKeyListener= new MascheraScaricoKeyListener();
    private JPanel vettore_jPanel = null;  //  @jve:decl-index=0:visual-constraint="8,188"
    private JPanel spacer_jPanel = null;
    private JComboBox vettore_jComboBox = null;
    
    private JPanel dataScarico_jPanel = null ;
    private JDateChooser dataScarico_jDateChooser = null ;
    
    private JLabel giacenzaRifiuto_jLabel = null;
    private JLabel trattatoRifiuto_jLabel = null;
    private JTextField giacenzaRifiuto_jTextField = null;
    private JTextField trattatoRifiuto_jTextField = null;
    
    private Vector vettori ;
    /**
     * This is the default constructor
     */
    public MascheraScarico_JPanel( RifiutiFrame f ) {
        super();
        this.frame = f ;
        
        campiJTable = new Carico().getColumns();
        //  swgTable = new JMainTable(f.getSwgCorrente());
        
        initialize();
        
    }
    /**
     * This method initializes this
     * 
     * @return void
     */
    private  void initialize() {
        
        target_jLabel = new JLabel();
        selezionati_jLabel = new JLabel();
        differenza_jLabel = new JLabel();
        giacenzaRifiuto_jLabel = new JLabel();
        trattatoRifiuto_jLabel = new JLabel();
        giacenzaRifiuto_jLabel.setText("Giacenza");
        trattatoRifiuto_jLabel.setText("Trattato");
        
        this.setLayout(new BorderLayout());
        this.setName("Scarico");
        this.setPreferredSize(new java.awt.Dimension(600,325));
        this.setMinimumSize(new java.awt.Dimension(100,600));
        this.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
        this.setSize(644, 403);
        target_jLabel.setText("Quantità desiderata :");
        target_jLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        selezionati_jLabel.setText("Quantità Selezionata :");
        differenza_jLabel.setText("Differenza :"); 
        differenza_jLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        differenza_jLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        trattatoRifiuto_jLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        this.add(getRifiuto_jPanel(), java.awt.BorderLayout.NORTH);
        this.add(getMovimenti_jPanel(), java.awt.BorderLayout.CENTER);
        this.add(getJPanel(), java.awt.BorderLayout.SOUTH);
        this.setBorder(BorderFactory.createEmptyBorder(15,0,0,0));

    }
    /**
     * This method initializes jTextField	
     * 	
     * @return javax.swing.JTextField	
     */    
    private JTextField getTarget_jTextField() {
        if (target_jTextField == null) {
            target_jTextField = new JTextField();
            target_jTextField.setToolTipText("Inserire il peso che si vorrebbe scaricare");
            target_jTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            target_jTextField.setEditable(false);
            target_jTextField.setEnabled(false);
            target_jTextField.setText("0");
            target_jTextField.setMinimumSize(new java.awt.Dimension(110,20));
            target_jTextField.setPreferredSize(new java.awt.Dimension(110,20));
            target_jTextField.setSize(new java.awt.Dimension(110,20));
            target_jTextField.addKeyListener(mascheraScaricoKeyListener);
            
        }
        return target_jTextField;
    }

    private JTextField getSelezionati_jTextField() {
        if (selezionati_jTextField == null) {
            selezionati_jTextField = new JTextField();
            selezionati_jTextField.setToolTipText("Peso complessivo degli elementi selezionati nell'elenco");
            selezionati_jTextField.setEditable(false);
            selezionati_jTextField.setText("0");
            selezionati_jTextField.setMinimumSize(new java.awt.Dimension(110,20));
            selezionati_jTextField.setPreferredSize(new java.awt.Dimension(110,20));
            selezionati_jTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        }
        return selezionati_jTextField;
    }
 
    private JTextField getJTextField() {
        if (jTextField == null) {
            jTextField = new JTextField();
            jTextField.setEditable(false);
            jTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            jTextField.setToolTipText("");			
            jTextField.setText("0");
            jTextField.setMinimumSize(new java.awt.Dimension(110,20));
            jTextField.setPreferredSize(new java.awt.Dimension(110,20));
            
        }
        return jTextField;
    }

    private JButton getScarica_JButton() {
        if (jButton == null) {
            jButton = new JButton();
            jButton.setEnabled(false);
            jButton.setText("Scarica");
            jButton.addActionListener(mascheraScaricoActionListener);
        }
        return jButton;
    }
  
    public JMainTable getJTable() {
        if (swgTable == null) {
            swgTable = new JMainTable( frame.getSwgCorrente(), false);
            swgTable.getTableSorter().getTableModel().addTableModelListener(movimento_tableModelListener);
            //swgTable = new JTable();
        }
        return swgTable;
    }
    
    private JButton getAdd_jButton() {
        if (add_jButton == null) {
            add_jButton = new JButton();
            add_jButton.setText("Aggiungi");
            add_jButton.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
            add_jButton.addActionListener(mascheraScaricoActionListener);
        }
        return add_jButton;
    }
   
    private JPanel getAdd_remove_jPanel() {
        if (add_remove_jPanel == null) {
            FlowLayout flowLayout1 = new FlowLayout();
            add_remove_jPanel = new JPanel();
            add_remove_jPanel.setLayout(flowLayout1);
            add_remove_jPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
            flowLayout1.setAlignment(java.awt.FlowLayout.RIGHT);
            add_remove_jPanel.add(getAdd_jButton(), null);
            add_remove_jPanel.add(getRemove_jButton(), null);
            add_remove_jPanel.add(getRemove_all_jButton(),null);
        }
        return add_remove_jPanel;
    }
   
    private JButton getRemove_jButton() {
        if (remove_jButton == null) {
            remove_jButton = new JButton();
            remove_jButton.setText("Rimuovi");
            remove_jButton.setEnabled(false);
            remove_jButton.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
            remove_jButton.addActionListener(mascheraScaricoActionListener);
        }
        return remove_jButton;
    }
    
    private JButton getRemove_all_jButton() {
        if (remove_all_jButton == null) {
            remove_all_jButton = new JButton();
            remove_all_jButton.setText("Rimuovi tutto");
            remove_all_jButton.setEnabled(false);
            remove_all_jButton.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
            remove_all_jButton.addActionListener(mascheraScaricoActionListener);
        }
        return remove_all_jButton;
    }

    private JPanel getRifiuto_jPanel() {
        if (rifiuto_jPanel == null) {
            
            giacenzaRifiuto_jLabel = new JLabel();
            trattatoRifiuto_jLabel = new JLabel();
            giacenzaRifiuto_jLabel.setText("Giacenza");
            trattatoRifiuto_jLabel.setText("Trattato");
            
            GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
            jLabel = new JLabel();
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridheight = 5;
            gridBagConstraints.gridy = 2;
            GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
            cer_jLabel = new JLabel();
            rifiuto_jLabel = new JLabel();
            GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints110 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
            rifiuto_jPanel = new JPanel();
            rifiuto_jPanel.setLayout(new GridBagLayout());
            rifiuto_jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Rifiuto da Scaricare :", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), new java.awt.Color(51,51,51)));
            gridBagConstraints10.gridx = 0;
            gridBagConstraints10.gridy = 0;
            cer_jLabel.setText("Codice Quadrelli :");
            gridBagConstraints10.insets = new java.awt.Insets(0,5,5,5);
            gridBagConstraints10.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints12.gridx = 1;
            gridBagConstraints12.gridy = 0;
            gridBagConstraints12.weightx = 1.0;
            //gridBagConstraints12.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints12.fill = java.awt.GridBagConstraints.NONE;
            gridBagConstraints12.insets = new java.awt.Insets(5,0,5,5);
            gridBagConstraints12.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints12.gridwidth = 2;
            gridBagConstraints13.gridx = 0;
            gridBagConstraints13.gridy = 1;
            rifiuto_jLabel.setText("Rifiuto :");
            gridBagConstraints13.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints13.insets = new java.awt.Insets(5,5,5,5);
            gridBagConstraints14.gridx = 1;
            gridBagConstraints14.gridy = 1;
            gridBagConstraints14.weightx = 1.0;
            gridBagConstraints14.anchor = java.awt.GridBagConstraints.WEST;
            //gridBagConstraints14.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints14.fill = java.awt.GridBagConstraints.NONE;
            gridBagConstraints14.insets = new java.awt.Insets(5,0,5,5);
            gridBagConstraints14.gridwidth = 2;
            rifiuto_jPanel.add(rifiuto_jLabel, gridBagConstraints13);
            rifiuto_jPanel.add(cer_jLabel, gridBagConstraints10);
            gridBagConstraints11.gridx = 1;
            gridBagConstraints11.gridy = 0;
            gridBagConstraints11.weightx = 1.0;
            gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints15.gridx = 0;
            gridBagConstraints15.gridy = 4;
            gridBagConstraints15.gridwidth = 1;
            gridBagConstraints15.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints15.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints15.insets = new java.awt.Insets(4,4,4,4);
            gridBagConstraints16.gridx = 1;
            gridBagConstraints16.gridy = 4;
            gridBagConstraints16.weightx = 1.0;
            gridBagConstraints16.anchor = java.awt.GridBagConstraints.WEST;
            //gridBagConstraints16.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints16.fill = java.awt.GridBagConstraints.NONE;
            gridBagConstraints16.insets = new java.awt.Insets(0,0,0,5);
            gridBagConstraints17.gridx = 0;
            gridBagConstraints17.gridy = 5;
            gridBagConstraints17.insets = new java.awt.Insets(4,4,4,4);
            gridBagConstraints17.anchor=GridBagConstraints.WEST;
            gridBagConstraints18.gridx = 1;
            gridBagConstraints18.gridy = 5;
            gridBagConstraints18.weightx = 1.0;
            //			gridBagConstraints18.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints18.fill = java.awt.GridBagConstraints.NONE;
            gridBagConstraints18.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints18.insets = new java.awt.Insets(0,0,0,5);
            gridBagConstraints19.gridx = 0;
            gridBagConstraints19.gridy = 6;
            gridBagConstraints19.anchor = java.awt.GridBagConstraints.WEST;
            //			gridBagConstraints19.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints19.fill = java.awt.GridBagConstraints.NONE;
            gridBagConstraints19.insets = new java.awt.Insets(4,4,4,4);
            gridBagConstraints20.gridx = 1;
            gridBagConstraints20.gridy = 6;
            gridBagConstraints20.weightx = 1.0;
            gridBagConstraints20.fill = java.awt.GridBagConstraints.NONE;
            gridBagConstraints20.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints20.insets = new java.awt.Insets(0,0,0,5);
            gridBagConstraints2.gridx = 0;
            gridBagConstraints2.gridy = 2;
            gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints2.insets = new java.awt.Insets(4,4,4,4);
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.gridy = 3;
            gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints1.insets = new java.awt.Insets(4,4,4,4);
            gridBagConstraints21.gridx = 1;
            gridBagConstraints21.gridy = 2;
            gridBagConstraints21.weightx = 1.0;
            gridBagConstraints21.fill = java.awt.GridBagConstraints.NONE;
            gridBagConstraints21.insets = new java.awt.Insets(0,0,0,5);
            gridBagConstraints21.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints3.gridx = 1;
            gridBagConstraints3.gridy = 3;
            gridBagConstraints3.weightx = 1.0;
            gridBagConstraints3.fill = java.awt.GridBagConstraints.NONE;
            gridBagConstraints3.insets = new java.awt.Insets(0,0,0,4);
            gridBagConstraints3.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints110.gridx = 0;
            gridBagConstraints110.gridy = 7;
            gridBagConstraints110.insets = new java.awt.Insets(4,4,4,4);
            gridBagConstraints110.anchor = java.awt.GridBagConstraints.WEST;
            jLabel.setText("Giacenza Prevista :");
            gridBagConstraints22.gridx = 1;
            gridBagConstraints22.gridy = 7;
            gridBagConstraints22.weightx = 1.0;
            gridBagConstraints22.fill = java.awt.GridBagConstraints.NONE;
            gridBagConstraints22.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints22.insets = new java.awt.Insets(0,0,0,5);
            rifiuto_jPanel.add(getCer_jTextField(), gridBagConstraints12);
            
            
            rifiuto_jPanel.add(getRifiuto_jComboBox(), gridBagConstraints14);
            rifiuto_jPanel.add(target_jLabel, gridBagConstraints15);
            rifiuto_jPanel.add(getTarget_jTextField(), gridBagConstraints16);
            rifiuto_jPanel.add(selezionati_jLabel, gridBagConstraints17);
            rifiuto_jPanel.add(getSelezionati_jTextField(), gridBagConstraints18);
            rifiuto_jPanel.add(differenza_jLabel, gridBagConstraints19);
            rifiuto_jPanel.add(getJTextField(), gridBagConstraints20);
            rifiuto_jPanel.add(getMovimentazione_jPanel(), gridBagConstraints);
            rifiuto_jPanel.add(giacenzaRifiuto_jLabel, gridBagConstraints2);
            rifiuto_jPanel.add(trattatoRifiuto_jLabel, gridBagConstraints1);
            rifiuto_jPanel.add(getGiacenzaRifiuto_jTextField(), gridBagConstraints21);
            rifiuto_jPanel.add(getTrattatoRifiuto_jTextField(), gridBagConstraints3);
            rifiuto_jPanel.add(jLabel, gridBagConstraints110);
            rifiuto_jPanel.add(getGiacenzaPrevista_jTextField(), gridBagConstraints22);
        }
        return rifiuto_jPanel;
    }
    
    public JTextField getGiacenzaRifiuto_jTextField() {
        if (giacenzaRifiuto_jTextField == null) {
            giacenzaRifiuto_jTextField = new JTextField();
            giacenzaRifiuto_jTextField.setMinimumSize(new java.awt.Dimension(110,20));
            giacenzaRifiuto_jTextField.setPreferredSize(new java.awt.Dimension(110,20));
            giacenzaRifiuto_jTextField.setEditable(false);
            giacenzaRifiuto_jTextField.setText("0");
            giacenzaRifiuto_jTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            
        }
        return giacenzaRifiuto_jTextField;
    }
    public JTextField getTrattatoRifiuto_jTextField() {
        if (trattatoRifiuto_jTextField == null) {
            
            trattatoRifiuto_jTextField = new JTextField();
            trattatoRifiuto_jTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            trattatoRifiuto_jTextField.setMinimumSize(new java.awt.Dimension(110,20));
            trattatoRifiuto_jTextField.setPreferredSize(new java.awt.Dimension(110,20));
            trattatoRifiuto_jTextField.setEditable(false);
            trattatoRifiuto_jTextField.setText("0");
            
        }
        return trattatoRifiuto_jTextField;
    }

    public JTextField getCer_jTextField() {
        if (cer_jTextField == null) {
            cer_jTextField = new JTextField();
            cer_jTextField.setMinimumSize(new java.awt.Dimension(110,20));
            cer_jTextField.setPreferredSize(new java.awt.Dimension(110,20));
            cer_jTextField.setToolTipText("inserisci qui il codice quadrelli del rifiuto da scaricare");
            cer_jTextField.setVisible(true);
            // cer_jTextField.addActionListener(mascheraScaricoActionListener);
            //cer_jTextField.addFocusListener(mascheraScaricoFocusListener);
            cer_jTextField.addKeyListener(mascheraScaricoKeyListener);
            
        }
        return cer_jTextField;
    }

    public JComboBox getRifiuto_jComboBox() {
        if (rifiuto_jComboBox == null) {
            rifiuto_jComboBox = new JComboBox();
            rifiuto_jComboBox.addItem("nessuna selezione");
            try {
                Vector list = Rifiuto.getAll( frame.getDatabase() );
                Rifiuto r = null ;
                
                for ( int i = 0 ; i < list.size() ; i++ ){
                    r = ((Rifiuto)list.get(i)) ; 
                    rifiuto_jComboBox.addItem(r) ;
                }
                
                
            }
            catch ( Exception e ) {
                e.printStackTrace();
            }
            
            rifiuto_jComboBox.setRenderer(new ComboListCellRenderer() );
            rifiuto_jComboBox.setToolTipText("Selezionare il rifiuto da scaricare");
            
            rifiuto_jComboBox.addActionListener(mascheraScaricoActionListener);
        }
        
        return rifiuto_jComboBox;
    }

    private JPanel getMovimenti_jPanel() {
        if (movimenti_jPanel == null) {
            movimenti_jPanel = new JPanel();
            movimenti_jPanel.setLayout(new BorderLayout());
            movimenti_jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Movimenti Selezionati :", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
            movimenti_jPanel.add(getJTable(), java.awt.BorderLayout.CENTER);
            movimenti_jPanel.add(getAdd_remove_jPanel(), java.awt.BorderLayout.SOUTH);
        }
        return movimenti_jPanel;
    }
 
    private JPanel getJPanel() {
        if (jPanel == null) {
            jPanel = new JPanel();
            jPanel.setLayout(new BorderLayout());
            jPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5,5,5,5));
            //jPanel.add(getVettore_jPanel(), java.awt.BorderLayout.NORTH);
            
            jPanel.add(getDataScarico_jPanel(), java.awt.BorderLayout.NORTH);
            jPanel.add(getScarica_JButton(), java.awt.BorderLayout.SOUTH);
            jPanel.add(getSpacer_jPanel(), java.awt.BorderLayout.CENTER);
        }
        return jPanel;
    }
    
    
    //{{ METODI E CLASSI PER LA GESTIONE DEGLI EVENTI
    private Rifiuto refresh_rifiuti_comboBox_item() {
        for(int i=1;i<getRifiuto_jComboBox().getItemCount();i++) 
            if(((Rifiuto) getRifiuto_jComboBox().getItemAt(i)).getCodiceQuadrelli().equals(getCer_jTextField().getText())) {
                getRifiuto_jComboBox().setSelectedIndex(i);
                return (Rifiuto)getRifiuto_jComboBox().getSelectedItem();
            }
        return null;
    }
    
    
    //metodo di controllo troppo pesante?
    private boolean isCodiceCerValido() {
        for(int i=1;i<getRifiuto_jComboBox().getItemCount();i++) 
            if(((Rifiuto) getRifiuto_jComboBox().getItemAt(i)).getCodiceQuadrelli().equals(getCer_jTextField().getText())) {
                return true;
            }
        return false;
    }
    
    private int calcolo_giacenza() {
        int giacenza=0;
        for(int row=0;row<getJTable().getMyTableModel().getRowCount();row++) {
            String value=(String)getJTable().getMyTableModel().getValueAt(row,3);
            giacenza=giacenza+Integer.parseInt(value);
        }
        return giacenza ;
    }
    
    
    
    private void reset_mascheraScarico() {
        getJTable().getTableSorter().getTableModel().removeTableModelListener(movimento_tableModelListener);
        getJTable().removeAllItems();
        set_enable_buttons(false);
        setISEmptyTable(true);
        
        frame.refreshElencoCarichi();
        
        getJTable().getTableSorter().getTableModel().addTableModelListener(movimento_tableModelListener);
        getTarget_jTextField().setText("0");
        getSelezionati_jTextField().setText("0");
        getJTextField().setText("0");
        getVettore_jComboBox().setSelectedIndex(0);
        getGiacenzaRifiuto_jTextField().setText("0");
        getTrattatoRifiuto_jTextField().setText("0");
    }
    
    
    private class MascheraScaricoKeyListener extends KeyAdapter {
        public void keyReleased( KeyEvent e) {	    	
            if(e.getSource().equals(getCer_jTextField())) {
                if ( getCer_jTextField().getText().length() < 1 ) {
                        reset_mascheraScarico();
                }
                if(isCodiceCerValido()) {
                    Rifiuto c=refresh_rifiuti_comboBox_item();
                    swg.filtra(c);
                    refresh_giacenza();
                    getCer_jTextField().setBackground(Color.WHITE);
                }
                else
                    getCer_jTextField().setBackground(Color.RED);
            }
            else 
                if(e.getSource().equals(getTarget_jTextField()))
                    try {
                        if(Double.parseDouble(getTarget_jTextField().getText())>0){
                            getMovimentazioneInDifetto_jButton().setEnabled(true);
                            getMovimentazioneInEccesso_jButton().setEnabled(true);
                        }
                        else {
                            getMovimentazioneInDifetto_jButton().setEnabled(false);
                            getMovimentazioneInEccesso_jButton().setEnabled(false);
                        }   
                    } 
            catch (NumberFormatException ex) {
                getMovimentazioneInDifetto_jButton().setEnabled(false);
                getMovimentazioneInEccesso_jButton().setEnabled(false);
            }  
        }
        
    }
    
    private SwgScarico swg=null;
    
    private class MascheraScaricoActionListener implements ActionListener{
        
        public void actionPerformed(ActionEvent e) {
            
            if(swg==null)
                swg= (SwgScarico) frame.getSwgScarico();
            
            if(e.getSource().equals(getRifiuto_jComboBox())) {
                if(getRifiuto_jComboBox().getSelectedIndex()==0) {
                    try {
                        swg.getFilterManager().removeFilter(swg.getRifiutofilter());
                        swg.refreshElencoJTable();
                        getCer_jTextField().setText("");      
                        getGiacenzaRifiuto_jTextField().setText("0") ;
                        getTrattatoRifiuto_jTextField().setText("0");
                    } 
                    catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                else {
                    Rifiuto c=(Rifiuto)getRifiuto_jComboBox().getSelectedItem();
                    swg.seleziona_combo_box_item(c);
                    swg.filtra(c);
                    getTarget_jTextField().setEnabled(true);
                    getTarget_jTextField().setEditable(true);
                    getGiacenzaRifiuto_jTextField().setText(c.getGiacenza()) ;
                    getTrattatoRifiuto_jTextField().setText(c.getLavorato());
                }
                getJTable().removeAllItems();
                refresh_giacenza();
                
            }
            
            else if(e.getSource().equals(getAdd_jButton())) {
                    swg.aggiungi_carichi();          
                }
            else if(e.getSource().equals(getRemove_jButton())) {
                    swg.removeSelectedItemsAtJTableRight();
                    refresh_giacenza();
                    if ( getJTable().getElenco().size() < 1 )
                   		removeAll_clicked() ;
                }
            else if(e.getSource().equals(getRemove_all_jButton())) {
                            removeAll_clicked();
	            }
            else if(e.getSource().equals(getScarica_JButton())) {
                Vettore vettore = null ; // getSelectedVettore() ;
                if ( true || vettore != null) {
                    try {
//                        try { 
//                            ((HsqlDB) frame.getDatabase()).backup( "Scarico n." + (
//                                    ((Movimento)frame.getSwgMovimento().getElementi().get(0)).getNumProgressivo()
//                                    + 1 ) );
//                        }
//                        catch (Exception ex) {
//                            ex.printStackTrace() ;
//                        }
                        
                        swg.scaricamovimenti(vettore, getDataScarico() );
                        reset_mascheraScarico();
                        
                        
                        
                        JOptionPane.showMessageDialog(frame, "Scarico Effettuato",
                                "Gestione Scarichi",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                    catch ( Exception er ) {
                        if ( er.getMessage() != null && er.getMessage().equals("CANCEL") ) {
                            JOptionPane.showMessageDialog(frame, "Scarico Annullato",
                                    "Gestione Scarichi",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                        
                        else {
                            er.printStackTrace();
                            JOptionPane.showMessageDialog(frame,
                                    "Attenzione : Si è verificato un errore ",
                                    "Errore",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                else {
                    JOptionPane.showMessageDialog(frame, "Attenzione: non è stato selezionato nessun trasportatore!",
                            "Attenzione",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
            else if(e.getSource().equals(getMovimentazioneInDifetto_jButton())) {
                removeAll_clicked();
                
                int target = Integer.parseInt( getTarget_jTextField().getText() );
                int i=0;
                Movimento primoMov = swg.getPrimoCarico().getMovimento() ;
                if ( primoMov.isGiacenza() ) {
                    swg.getJMainTable().getJTable().setRowSelectionInterval(0,0);
                    swg.aggiungi_carichi();
                    refresh_giacenza();
                }
                else {
                    while( swg.getJMainTable().getElenco().size() > 0 && target > Double.parseDouble(getSelezionati_jTextField().getText())+
	                        ((Carico)swg.getJMainTable().getElenco().get(i)).getNetto()) {
	                    swg.getJMainTable().getJTable().setRowSelectionInterval(0,0);
	                    swg.aggiungi_carichi();
	                    refresh_giacenza();
	                }
                }
                //getTarget_jTextField().setText("" + target) ;
                
            }
            else if(e.getSource().equals(getMovimentazioneInEccesso_jButton())) {
                removeAll_clicked();
                
                int target = Integer.parseInt( getTarget_jTextField().getText() );

                Movimento primoMov = swg.getPrimoCarico().getMovimento() ;
                if ( primoMov.isGiacenza() ) {
                    swg.getJMainTable().getJTable().setRowSelectionInterval(0,0);
                    swg.aggiungi_carichi();
                    refresh_giacenza();	               
                }
                else {
                    while( swg.getJMainTable().getElenco().size() > 0 && target > Double.parseDouble(getSelezionati_jTextField().getText())) {
                        swg.getJMainTable().getJTable().setRowSelectionInterval(0,0);
                        swg.aggiungi_carichi();
                        refresh_giacenza();
                    }
                }
                
                getTarget_jTextField().setText("" + target) ;
            }
                                    
        }

    }
    //}}
    private void removeAll_clicked() {
	    Object c = getRifiuto_jComboBox().getSelectedItem();
	    if (c != null && c instanceof Rifiuto ) { 
	        swg.seleziona_combo_box_item((Rifiuto) c);
	        swg.filtra((Rifiuto) c);
	    }
        swg.refreshElencoJTable();
        getJTable().removeAllItems();
        refresh_giacenza();
	}
    
    private JPanel getDataScarico_jPanel() {
        if (dataScarico_jPanel == null) {
            dataScarico_jPanel = new JPanel();
            dataScarico_jPanel.setBorder(
                    javax.swing.BorderFactory.createCompoundBorder(
                            javax.swing.BorderFactory.createTitledBorder(null, "DataScarico :", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null), 
                            javax.swing.BorderFactory.createEmptyBorder(2,2,2,2)));
            dataScarico_jPanel.add(getDataScarico_jDateChooser(), null);
        }
        return dataScarico_jPanel;
    }
    
    public JDateChooser getDataScarico_jDateChooser() {
        if(dataScarico_jDateChooser==null) {
            dataScarico_jDateChooser=new JDateChooser("dd/MM/yyyy",false);
            //dataScarico_jDateChooser.getSpinner().addChangeListener(new DateChangeListener());
        }
        return dataScarico_jDateChooser;
    }
    public FormattedDate getDataScarico() {
        return new FormattedDate( getDataScarico_jDateChooser().getDate()) ;
    }
    
    private JPanel getVettore_jPanel() {
        if (vettore_jPanel == null) {
            vettore_jPanel = new JPanel();
            vettore_jPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createTitledBorder(null, "Trasporatore :", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null), javax.swing.BorderFactory.createEmptyBorder(5,5,5,5)));
            vettore_jPanel.add(getVettore_jComboBox(), null);
        }
        return vettore_jPanel;
    }
    private JPanel getSpacer_jPanel() {
        if (spacer_jPanel == null) {
            spacer_jPanel = new JPanel();
        }
        return spacer_jPanel;
    }   
    public JComboBox getVettore_jComboBox() {
        if (vettore_jComboBox == null) {
            vettore_jComboBox = new JComboBox();
            vettore_jComboBox.addItem("");
            try {
                vettori = Vettore.getAll( frame.getDatabase() );
                Vettore c = null ;
                
                for ( int i = 0 ; i < vettori.size() ; i++ ){
                    c = ((Vettore)vettori.get(i)) ; 
                    vettore_jComboBox.addItem(c) ;
                }
                
            }
            catch ( Exception e ) {
                e.printStackTrace();
            }
            
            vettore_jComboBox.addActionListener(mascheraScaricoActionListener);
        }
        return vettore_jComboBox;
    }
    
    public Vettore getSelectedVettore() {
        try {
            int idx = vettore_jComboBox.getSelectedIndex() - 1 ; 
            if ( idx > 0 ) 
                return (Vettore) vettori.get(idx) ;
            else 
                return null ;
        }
        catch( Exception e ) {
            return null ;
        }
    }
    
    private void set_enable_buttons(boolean isActive){
        getRemove_all_jButton().setEnabled(isActive);
        getRemove_jButton().setEnabled(isActive);
        getScarica_JButton().setEnabled(isActive);
        
    }
    
    
    public void refresh_giacenza() {
        int giacenza_selezionata=calcolo_giacenza();
        
        
        int rimanenza= Integer.parseInt(getTarget_jTextField().getText()) - giacenza_selezionata;                          
        getSelezionati_jTextField().setText(String.valueOf(giacenza_selezionata));
        getJTextField().setText(String.valueOf(rimanenza));
        try { 
            String giac = getGiacenzaRifiuto_jTextField().getText() ;
            if ( giac != null && giac.length() > 0 ) {
		        int giacenzaRifiuto = Integer.parseInt( giac.replaceAll("\\.", "") );
		        getGiacenzaPrevista_jTextField().setText("" + ( giacenzaRifiuto - giacenza_selezionata ) ) ;
            }
        }
        catch ( Exception e ) {
            getGiacenzaPrevista_jTextField().setText( "0" ) ;
        }
    }
    
    private SwgScarico getSwg() {
        if(swg==null)
            if ( frame.getSwgCorrente() instanceof SwgScarico ) 
                swg= (SwgScarico) frame.getSwgCorrente();
        return swg;
    }
    
    private Movimento_tableListener movimento_tableModelListener= new Movimento_tableListener();
    
    private class Movimento_tableListener implements TableModelListener{
        
        public void tableChanged(TableModelEvent e) {
            getSwg();			
            if(getJTable().getJTable().getRowCount()>0) {
                set_enable_buttons(true);
                setISEmptyTable(false);
            }
            
            else{
                set_enable_buttons(false);
                setISEmptyTable(true);
                
            }	
        }
        
    }
    
    
    
    private boolean iSEmptyTable=true;
    
    private JPanel movimentazione_jPanel = null;
    
    private JButton movimentazioneInEccesso_jButton = null;
    
    private JButton movimentazioneInDifetto_jButton = null;
    
    private JPanel labelJPanel;
    
    
	private JLabel jLabel = null;
	private JTextField giacenzaPrevista_jTextField = null;
    public boolean isISEmptyTable() {
        return iSEmptyTable;
    }
    public void setISEmptyTable(boolean emptyTable) {
        iSEmptyTable = emptyTable;
    }

    private JPanel getMovimentazione_jPanel() {
        if (movimentazione_jPanel == null) {
            BorderLayout borderLayout1 = new BorderLayout();
            movimentazione_jPanel = new JPanel();
            movimentazione_jPanel.setLayout(borderLayout1);
            movimentazione_jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
            movimentazione_jPanel.add(new JLabel("Movimentazione automatica"),BorderLayout.NORTH);
            movimentazione_jPanel.setToolTipText("seleziona automaticamente i carichi da scaricare");
            borderLayout1.setVgap(5);
            movimentazione_jPanel.add(getLabelJPanel(),BorderLayout.NORTH);
            movimentazione_jPanel.add(getMovimentazioneInEccesso_jButton(), java.awt.BorderLayout.CENTER);
            movimentazione_jPanel.add(getMovimentazioneInDifetto_jButton(), java.awt.BorderLayout.SOUTH);
        }
        return movimentazione_jPanel;
    }
    
    
    private JPanel getLabelJPanel(){
        if(labelJPanel==null){
            BorderLayout borderLayout = new BorderLayout();
            labelJPanel=new JPanel(borderLayout);
            labelJPanel.add(new JLabel("Movimentazione"),BorderLayout.NORTH);
            labelJPanel.add(new JLabel("    automatica"),BorderLayout.SOUTH);
            
        }
        return labelJPanel;
        
    }

    private JButton getMovimentazioneInEccesso_jButton() {
        if (movimentazioneInEccesso_jButton == null) {
            movimentazioneInEccesso_jButton = new JButton();
            movimentazioneInEccesso_jButton.setText("per eccesso");
            movimentazioneInEccesso_jButton.setEnabled(false);
            movimentazioneInEccesso_jButton.addActionListener(mascheraScaricoActionListener);
        }
        return movimentazioneInEccesso_jButton;
    }

    private JButton getMovimentazioneInDifetto_jButton() {
        if (movimentazioneInDifetto_jButton == null) {
            movimentazioneInDifetto_jButton = new JButton();
            movimentazioneInDifetto_jButton.setText("per difetto");
            movimentazioneInDifetto_jButton.setEnabled(false);
            movimentazioneInDifetto_jButton.addActionListener(mascheraScaricoActionListener);
        }
        return movimentazioneInDifetto_jButton;
    }
    
	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getGiacenzaPrevista_jTextField() {
		if (giacenzaPrevista_jTextField == null) {
			giacenzaPrevista_jTextField = new JTextField();
			giacenzaPrevista_jTextField.setEditable(false);
			giacenzaPrevista_jTextField.setMinimumSize(new java.awt.Dimension(110,20));
			giacenzaPrevista_jTextField.setPreferredSize(new java.awt.Dimension(110,20));
			giacenzaPrevista_jTextField.setSize(new java.awt.Dimension(110,20));
			giacenzaPrevista_jTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		}
		return giacenzaPrevista_jTextField;
	}
 }