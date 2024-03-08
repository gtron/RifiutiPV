/*
 * Created on 10-set-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.front;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * @author Gtron - 
 * 
 */
public class PrintManager_JPanel extends JPanel {

	private JPanel prepare_jPanel = null;
	private JPanel registro_jPanel = null;
	private JLabel msgInAttesa_jLabel = null;
	private JLabel numInAttesa_jLabel = null;
	private JLabel msgPrevisti_jLabel = null;
	private JLabel numPrevisti_jLabel = null;
	private JLabel msgDisponibili_jLabel = null;
	private JTextField jTextField = null;
	private JLabel msgDaStampare_jLabel = null;
	private JTextField jTextField1 = null;
	private JButton aggiorna_jButton = null;
	private JButton preparazione_jButton = null;
	private JLabel spacer_jLabel = null;
	
//	private static RifiutiFrame frame ;

	/**
	 * This is the default constructor
	 */
	public PrintManager_JPanel() {
		super();
		initialize();
	}
	public PrintManager_JPanel( RifiutiFrame f ) {
	    super();
//	    frame = f ;
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private  void initialize() {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		this.setSize(614, 322);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new java.awt.Insets(5,5,5,5);
		
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridy = 1;
		gridBagConstraints2.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints2.insets = new java.awt.Insets(5,5,5,5);
		this.add(getPrepare_jPanel(), gridBagConstraints);
		this.add(getRegistro_jPanel(), gridBagConstraints2);
		
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getPrepare_jPanel() {
		if (prepare_jPanel == null) {
			spacer_jLabel = new JLabel();
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			msgInAttesa_jLabel = new JLabel();
			
			numInAttesa_jLabel = new JLabel();
			msgPrevisti_jLabel = new JLabel();
			numPrevisti_jLabel = new JLabel();
			msgDisponibili_jLabel = new JLabel();
			msgDaStampare_jLabel = new JLabel();
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			prepare_jPanel = new JPanel();
			prepare_jPanel.setLayout(new GridBagLayout());
			prepare_jPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createTitledBorder(null, "Preparazione Pagine", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null), javax.swing.BorderFactory.createEmptyBorder(4,4,4,4)));
			msgInAttesa_jLabel.setText("Movimenti in attesa di essere stampati :");
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.insets = new java.awt.Insets(5,5,5,5);
			gridBagConstraints21.gridx = 1;
			gridBagConstraints21.gridy = 0;
			numInAttesa_jLabel.setText("0");
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 1;
			msgPrevisti_jLabel.setText("Numero previsto di fogli necessari :");
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.gridy = 1;
			numPrevisti_jLabel.setText("0");
			gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.insets = new java.awt.Insets(0,5,5,5);
			gridBagConstraints4.insets = new java.awt.Insets(0,0,5,0);
			gridBagConstraints4.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints21.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 2;
			gridBagConstraints5.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints5.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.insets = new java.awt.Insets(0,5,5,5);
			msgDisponibili_jLabel.setText("Numero di fogli già stampati :");
			gridBagConstraints6.gridx = 1;
			gridBagConstraints6.gridy = 2;
			gridBagConstraints6.weightx = 1.0;
			gridBagConstraints6.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints6.insets = new java.awt.Insets(0,0,5,0);
			gridBagConstraints6.anchor = java.awt.GridBagConstraints.CENTER;
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.gridy = 3;
			msgDaStampare_jLabel.setText("Numero di fogli da stampare :");
			gridBagConstraints7.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints7.insets = new java.awt.Insets(0,5,5,5);
			gridBagConstraints11.gridx = 3;
			gridBagConstraints11.gridy = 3;
			gridBagConstraints11.insets = new java.awt.Insets(0,5,5,5);
			gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints22.gridx = 3;
			gridBagConstraints22.gridy = 4;
			gridBagConstraints22.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints22.insets = new java.awt.Insets(0,5,5,5);
			gridBagConstraints31.gridx = 1;
			gridBagConstraints31.gridy = 4;
			spacer_jLabel.setText("        ");
			prepare_jPanel.add(getAggiorna_jButton(), gridBagConstraints11);
			prepare_jPanel.add(getPreparazione_jButton(), gridBagConstraints22);
			prepare_jPanel.add(numInAttesa_jLabel, gridBagConstraints21);
			prepare_jPanel.add(msgDaStampare_jLabel, gridBagConstraints7);
			prepare_jPanel.add(numPrevisti_jLabel, gridBagConstraints4);
			prepare_jPanel.add(getJTextField1(), gridBagConstraints8);
			prepare_jPanel.add(getJTextField(), gridBagConstraints6);
			prepare_jPanel.add(spacer_jLabel, gridBagConstraints31);
			gridBagConstraints8.gridx = 1;
			gridBagConstraints8.gridy = 3;
			gridBagConstraints8.weightx = 1.0;
			gridBagConstraints8.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints8.insets = new java.awt.Insets(0,0,5,0);
			gridBagConstraints8.anchor = java.awt.GridBagConstraints.CENTER;
			prepare_jPanel.add(msgInAttesa_jLabel, gridBagConstraints1);
			prepare_jPanel.add(msgPrevisti_jLabel, gridBagConstraints3);
			prepare_jPanel.add(msgDisponibili_jLabel, gridBagConstraints5);
		}
		return prepare_jPanel;
	}
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getRegistro_jPanel() {
		if (registro_jPanel == null) {
			registro_jPanel = new JPanel();
			registro_jPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createTitledBorder(null, " Stampa Registro ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null), javax.swing.BorderFactory.createEmptyBorder(4,4,4,4)));
		}
		return registro_jPanel;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
		}
		return jTextField;
	}
	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
		}
		return jTextField1;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getAggiorna_jButton() {
		if (aggiorna_jButton == null) {
			aggiorna_jButton = new JButton();
			aggiorna_jButton.setText("Aggiorna");
			aggiorna_jButton.setPreferredSize(new Dimension(85,20));
			aggiorna_jButton.setSize(new Dimension( 85,20 ));

			aggiorna_jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
				    
				}
			});
		}
		return aggiorna_jButton;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getPreparazione_jButton() {
		if (preparazione_jButton == null) {
			preparazione_jButton = new JButton();
			preparazione_jButton.setText("Stampa");
			preparazione_jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return preparazione_jButton;
	}

	
}  //  @jve:decl-index=0:visual-constraint="10,10"
