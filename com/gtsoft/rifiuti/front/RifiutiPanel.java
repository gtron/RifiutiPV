/*
 * Creato il 30-giu-2005
 *
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti
 * 
 */
package com.gtsoft.rifiuti.front;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import com.gtsoft.utils.Prodotto;

/**
 * @author pitcia
 * 
 * XXX Per modificare il modello associato al commento di questo tipo generato,
 * aprire Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class RifiutiPanel extends JPanel {

    private JPanel jPanel = null;

    private JPanel jPanel1 = null;

    private JPanel jPanel2 = null;

    private JPanel jPanel3 = null;

    private JPanel jPanel4 = null;

    private JPanel jPanel5 = null;

    private JPanel jPanel6 = null;

    private JPanel jPanel7 = null;

    private JPanel jPanel8 = null;

    private JLabel jLabel = null;

    private JLabel jLabel1 = null;

    private JLabel jLabel2 = null;

    private JLabel jLabel3 = null;

    private JLabel jLabel4 = null;

    private JLabel jLabel5 = null;

    private JLabel jLabel6 = null;

    private JLabel jLabel7 = null;

    private JLabel jLabel9 = null;

    private JLabel jLabel10 = null;

    private JLabel jLabel11 = null;

    private JLabel jLabel12 = null;

    private JLabel jLabel13 = null;

    private JTextField jTextField = null;

    private JLabel jLabel14 = null;

    private JLabel jLabel16 = null;

    private JButton jButton = null;

    private JLabel jLabel15 = null;

    private RifiutiFrame rifiutiFrame = null;

    /**
     * 
     * @uml.property name="prodotto"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private Prodotto prodotto = null;

    /**
     * This is the default constructor
     */
    public RifiutiPanel() {
        super();
        initialize(null, null);
    }

    public RifiutiPanel(Prodotto prodotto, RifiutiFrame rifiutiFrame) {
        super();
        initialize(prodotto, rifiutiFrame);
    }

    private void initialize(Prodotto prodotto, RifiutiFrame rifiutiFrame) {
        this.prodotto = prodotto;
        jLabel15 = new JLabel();
        this.setLayout(new BorderLayout());
        this.setSize(300, 200);
        this.rifiutiFrame = rifiutiFrame;
        jLabel15.setText(prodotto.getNomeProdotto());
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel15.setFont(new java.awt.Font("MS Sans Serif",
                java.awt.Font.PLAIN, 18));
        this.add(getJPanel(), java.awt.BorderLayout.CENTER);
        this.add(jLabel15, java.awt.BorderLayout.NORTH);
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    getClosedPopupMenu(e).show(e.getComponent(), e.getX(),
                            e.getY());
                }
            }
        });

    }

    private JPopupMenu getClosedPopupMenu(
            final java.awt.event.MouseEvent closedEvent) {
        JMenuItem closeItem = new JMenuItem("chiudi");
        JPopupMenu jPopupMenu = new JPopupMenu();
        jPopupMenu.add(closeItem);
        closeItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                getParent().remove(closedEvent.getComponent());
            }
        });
        return jPopupMenu;
    }

    /**
     * 
     * @uml.property name="jPanel"
     */
    private JPanel getJPanel() {
        if (jPanel == null) {
            GridBagConstraints gridBagConstraints27 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
            jPanel = new JPanel();
            jPanel.setLayout(new GridBagLayout());
            gridBagConstraints20.gridx = 0;
            gridBagConstraints20.gridy = 1;
            gridBagConstraints20.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints21.gridx = 0;
            gridBagConstraints21.gridy = 2;
            gridBagConstraints21.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints22.gridx = 0;
            gridBagConstraints22.gridy = 3;
            gridBagConstraints22.anchor = java.awt.GridBagConstraints.SOUTHWEST;
            gridBagConstraints23.gridx = 1;
            gridBagConstraints23.gridy = 0;
            gridBagConstraints23.anchor = java.awt.GridBagConstraints.NORTHEAST;
            gridBagConstraints24.gridx = 1;
            gridBagConstraints24.gridy = 1;
            gridBagConstraints24.anchor = java.awt.GridBagConstraints.EAST;
            gridBagConstraints25.gridx = 1;
            gridBagConstraints25.gridy = 3;
            gridBagConstraints25.anchor = java.awt.GridBagConstraints.SOUTHEAST;
            gridBagConstraints26.gridx = 1;
            gridBagConstraints26.gridy = 2;
            gridBagConstraints26.anchor = java.awt.GridBagConstraints.EAST;
            gridBagConstraints27.anchor = java.awt.GridBagConstraints.NORTHWEST;
            gridBagConstraints27.insets = new java.awt.Insets(0, 0, 0, 100);
//            jPanel.setForeground(java.awt.Color.cyan);
            jPanel
                    .setComponentOrientation(java.awt.ComponentOrientation.UNKNOWN);
            jPanel.setBorder(javax.swing.BorderFactory
                    .createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jPanel.add(getJPanel1(), gridBagConstraints27);
            jPanel.add(getJPanel2(), gridBagConstraints20);
            jPanel.add(getJPanel3(), gridBagConstraints21);
            jPanel.add(getJPanel4(), gridBagConstraints22);
            jPanel.add(getJPanel5(), gridBagConstraints23);
            jPanel.add(getJPanel6(), gridBagConstraints24);
            jPanel.add(getJPanel7(), gridBagConstraints26);
            jPanel.add(getJPanel8(), gridBagConstraints25);
        }
        return jPanel;
    }

    /**
     * This method initializes jPanel1
     * 
     * @return javax.swing.JPanel
     * 
     * @uml.property name="jPanel1"
     */
    private JPanel getJPanel1() {
        if (jPanel1 == null) {
            jLabel1 = new RifiutiJLabel();
            jLabel = new JLabel();
            jPanel1 = new JPanel();
            jLabel.setText("Data di pesatura");
            jLabel1.setText(prodotto.getDataPesatura().getString());
            jPanel1
                    .setComponentOrientation(java.awt.ComponentOrientation.LEFT_TO_RIGHT);
            jPanel1.add(jLabel, null);
            jPanel1.add(jLabel1, null);
        }
        return jPanel1;
    }

    /**
     * This method initializes jPanel2
     * 
     * @return javax.swing.JPanel
     * 
     * @uml.property name="jPanel2"
     */
    private JPanel getJPanel2() {
        if (jPanel2 == null) {
            jLabel3 = new RifiutiJLabel(prodotto.getCodiceMateriale());
            jLabel2 = new JLabel("codice del materiale");
            jPanel2 = new JPanel();
            jPanel2.add(jLabel2, null);
            jPanel2.add(jLabel3, null);
        }
        return jPanel2;
    }

    /**
     * This method initializes jPanel3
     * 
     * @return javax.swing.JPanel
     * 
     * @uml.property name="jPanel3"
     */
    private JPanel getJPanel3() {
        if (jPanel3 == null) {
            jLabel6 = new RifiutiJLabel(prodotto.getCodiceCer());
            jLabel4 = new JLabel("codice C.E.R.");
            jPanel3 = new JPanel();
            jPanel3.add(jLabel4, null);
            jPanel3.add(jLabel6, null);
        }
        return jPanel3;
    }

    /**
     * This method initializes jPanel4
     * 
     * @return javax.swing.JPanel
     * 
     * @uml.property name="jPanel4"
     */
    private JPanel getJPanel4() {
        if (jPanel4 == null) {
            jLabel7 = new RifiutiJLabel(prodotto.getVettore());
            jLabel5 = new JLabel("vettore");
            jPanel4 = new JPanel();
            jPanel4.add(jLabel5, null);
            jPanel4.add(jLabel7, null);
        }
        return jPanel4;
    }

    /**
     * This method initializes jPanel5
     * 
     * @return javax.swing.JPanel
     * 
     * @uml.property name="jPanel5"
     */
    private JPanel getJPanel5() {
        if (jPanel5 == null) {
            jLabel12 = new RifiutiJLabel();
            jLabel9 = new JLabel();
            jPanel5 = new JPanel();
            jLabel9.setText("numero del documento");
            jLabel12.setText(prodotto.getNumeroDocumento());
            jPanel5
                    .setComponentOrientation(java.awt.ComponentOrientation.UNKNOWN);
            jPanel5.add(jLabel9, null);
            jPanel5.add(jLabel12, null);
        }
        return jPanel5;
    }

    /**
     * This method initializes jPanel6
     * 
     * @return javax.swing.JPanel
     * 
     * @uml.property name="jPanel6"
     */
    private JPanel getJPanel6() {
        if (jPanel6 == null) {
            jLabel11 = new RifiutiJLabel();
            jLabel10 = new JLabel();
            jPanel6 = new JPanel();
            jLabel10.setText("peso al bilancere");
            jLabel11.setText(String.valueOf(prodotto.getMovCarico()));
            jPanel6.add(jLabel10, null);
            jPanel6.add(jLabel11, null);
        }
        return jPanel6;
    }

    /**
     * This method initializes jPanel7
     * 
     * @return javax.swing.JPanel
     * 
     * @uml.property name="jPanel7"
     */
    private JPanel getJPanel7() {
        if (jPanel7 == null) {
            jLabel13 = new JLabel();
            jPanel7 = new JPanel();
            jPanel7.setLayout(new BoxLayout(jPanel7, BoxLayout.X_AXIS));
            jLabel13.setText("peso di scarico");
            jPanel7.add(jLabel13, null);
            jPanel7.add(getJTextField(), null);
        }
        return jPanel7;
    }

    /**
     * This method initializes jPanel8
     * 
     * @return javax.swing.JPanel
     * 
     * @uml.property name="jPanel8"
     */
    private JPanel getJPanel8() {
        if (jPanel8 == null) {
            jLabel16 = new RifiutiJLabel();
            jLabel14 = new JLabel();
            jPanel8 = new JPanel();
            jLabel14.setText("peso residuo");
            jLabel16.setText(String.valueOf(prodotto.getRimanenza()));
            jPanel8.add(jLabel14, null);
            jPanel8.add(jLabel16, null);
            jPanel8.add(getJButton(), null);
        }
        return jPanel8;
    }

    /**
     * This method initializes jTextField
     * 
     * @return javax.swing.JTextField
     * 
     * @uml.property name="jTextField"
     */
    private JTextField getJTextField() {
        if (jTextField == null) {
            jTextField = new JTextField();
            jTextField.setPreferredSize(new Dimension(90, 15));
            new RifiutiStatusBarAdapter(jTextField,
                    "inserisci i kg di rifiuti da scaricare", RifiutiFrame
                            .getStatusBar());
        }
        return jTextField;
    }

    private JButton getJButton() {
        if (jButton == null) {
            jButton = new JButton();
            jButton.setText("scarica");
            jButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    try {
                        int pesScarico = Integer.parseInt(jTextField.getText());
                        prodotto.setMovScarico(pesScarico);
                        jLabel16.setText(String
                                .valueOf(prodotto.getRimanenza()));
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null,
                                "valore immesso non corretto");

                    }
                }
            });
        }
        return jButton;
    }

}