/*
 * Creato il 29-ago-2005
 *
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti_new
 * 
 */
package com.gtsoft.rifiuti.front;

import javax.swing.JPanel;


import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
public class maschera_dettaglio_Scarico_JPanel extends JPanel {

	private JPanel rifiuto_jPanel = null;
	private JLabel jLabel = null;
	private JLabel cer_jLabel = null;
	private JLabel descrizione_jLabel = null;
	private JLabel stato_fisico_jLabel = null;
	private JLabel destinazione_jLabel = null;
	private JPanel quantui = null;  //  @jve:decl-index=0:visual-constraint="15,211"
	/**
	 * This is the default constructor
	 */
	public maschera_dettaglio_Scarico_JPanel() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private  void initialize() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setSize(300,200);
		this.add(getRifiuto_jPanel(), null);
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getRifiuto_jPanel() {
		if (rifiuto_jPanel == null) {
			descrizione_jLabel = new JLabel();
			destinazione_jLabel = new JLabel();
			stato_fisico_jLabel = new JLabel();
			cer_jLabel = new JLabel();
			jLabel = new JLabel();
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			rifiuto_jPanel = new JPanel();
			rifiuto_jPanel.setLayout(new GridBagLayout());
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			jLabel.setText("Rifiuto");
			jLabel.setFont(new java.awt.Font("Times New Roman", java.awt.Font.BOLD, 24));
			jLabel.setForeground(java.awt.Color.orange);
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 3;
			gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
			cer_jLabel.setText("Cer");
			cer_jLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			cer_jLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 4;
			gridBagConstraints3.anchor = java.awt.GridBagConstraints.WEST;
			descrizione_jLabel.setText("Descrizione");
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 5;
			gridBagConstraints4.anchor = java.awt.GridBagConstraints.WEST;
			stato_fisico_jLabel.setText("Stato fisico");
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 6;
			gridBagConstraints5.anchor = java.awt.GridBagConstraints.WEST;
			destinazione_jLabel.setText("Destinazione");
			rifiuto_jPanel.add(jLabel, gridBagConstraints1);
			rifiuto_jPanel.add(cer_jLabel, gridBagConstraints2);
			rifiuto_jPanel.add(descrizione_jLabel, gridBagConstraints3);
			rifiuto_jPanel.add(stato_fisico_jLabel, gridBagConstraints4);
			rifiuto_jPanel.add(destinazione_jLabel, gridBagConstraints5);
		}
		return rifiuto_jPanel;
	}
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getQuantui() {
		if (quantui == null) {
			quantui = new JPanel();
			quantui.setSize(283, 36);
		}
		return quantui;
	}
 }
