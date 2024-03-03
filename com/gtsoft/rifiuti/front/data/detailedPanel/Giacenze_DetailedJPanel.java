/*
 * Created on 26-set-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.front.data.detailedPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * @author Gtron - 
 * 
 */
public class Giacenze_DetailedJPanel extends JPanel {

	private JLabel rifiuto_jLabel = null;
	private JLabel jLabel = null;
	/**
	 * This is the default constructor
	 */
	public Giacenze_DetailedJPanel() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
	    rifiuto_jLabel = new JLabel();
		jLabel = new JLabel();
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		this.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sommario Giacenze", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null), javax.swing.BorderFactory.createEmptyBorder(5,5,5,5)));
		this.setSize(300,200);
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 0;
		rifiuto_jLabel.setText("Rifiuto :");
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridy = 1;
		jLabel.setText("Quantità lavorata");
		this.add(rifiuto_jLabel, gridBagConstraints1);
		this.add(jLabel, gridBagConstraints2);
	}
}
