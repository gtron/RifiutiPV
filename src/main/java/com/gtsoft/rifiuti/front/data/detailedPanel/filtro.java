/*
 * Created on 29-giu-2006
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.front.data.detailedPanel;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
/**
 * @author Gtron - 
 * 
 */
public class filtro extends JPanel {

	private JPanel buttons_jPanel = null;
	private JPanel main_jPanel = null;
	private JLabel jLabel = null;
	private JTextField from_jTextField = null;
	private JLabel to_jLabel = null;
	private JTextField to_jTextField = null;
	private JToggleButton applica_jToggleButton = null;
	/**
	 * This is the default constructor
	 */
	public filtro() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private  void initialize() {
		this.setLayout(new BorderLayout());
		this.add(getButtons_jPanel(), java.awt.BorderLayout.EAST);
		this.add(getMain_jPanel(), java.awt.BorderLayout.CENTER);
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getButtons_jPanel() {
		if (buttons_jPanel == null) {
			buttons_jPanel = new JPanel();
			buttons_jPanel.add(getApplica_jToggleButton(), null);
		}
		return buttons_jPanel;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getMain_jPanel() {
		if (main_jPanel == null) {
			to_jLabel = new JLabel();
			jLabel = new JLabel();
			main_jPanel = new JPanel();
			jLabel.setText("dal ");
			to_jLabel.setText(" al ");
			main_jPanel.add(jLabel, null);
			main_jPanel.add(getFrom_jTextField(), null);
			main_jPanel.add(to_jLabel, null);
			main_jPanel.add(getTo_jTextField(), null);
		}
		return main_jPanel;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getFrom_jTextField() {
		if (from_jTextField == null) {
			from_jTextField = new JTextField();
			from_jTextField.setPreferredSize(new java.awt.Dimension(70,20));
			from_jTextField.setText("");
		}
		return from_jTextField;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTo_jTextField() {
		if (to_jTextField == null) {
			to_jTextField = new JTextField();
			to_jTextField.setPreferredSize(new java.awt.Dimension(70,20));
		}
		return to_jTextField;
	}
	/**
	 * This method initializes jToggleButton	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */    
	private JToggleButton getApplica_jToggleButton() {
		if (applica_jToggleButton == null) {
			applica_jToggleButton = new JToggleButton();
			applica_jToggleButton.setText("applica");
			applica_jToggleButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					if ( applica_jToggleButton.isSelected() ) {
					    
					}
					else {
					    
					}
				}
			});
		}
		return applica_jToggleButton;
	}
    }  //  @jve:decl-index=0:visual-constraint="10,10"
