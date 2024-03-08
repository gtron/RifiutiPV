package com.gtsoft.utils.config;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class ChangePW_jDialog extends JDialog {

	private JPanel jContentPane = null;
	private JPanel grid_jPanel = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JPasswordField inserisci_jPasswordField = null;
	private JLabel jLabel2 = null;
	private JPasswordField conferma_jPasswordField = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JPasswordField vecchiaPw_jPasswordField = null;
	/**
	 * This is the default constructor
	 */
	public ChangePW_jDialog() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setModal(true);
		this.setSize(new java.awt.Dimension(340,164));
		this.setMinimumSize(new java.awt.Dimension(292,164));
		this.setResizable(false);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 3;
			gridBagConstraints4.gridy = 3;
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getGrid_jPanel(), java.awt.BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes grid_jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getGrid_jPanel() {
		if (grid_jPanel == null) {
			GridBagConstraints gridBagConstraints81 = new GridBagConstraints();
			gridBagConstraints81.fill = java.awt.GridBagConstraints.NONE;
			gridBagConstraints81.gridy = 0;
			gridBagConstraints81.weightx = 1.0;
			gridBagConstraints81.gridwidth = 2;
			gridBagConstraints81.insets = new java.awt.Insets(5,5,5,5);
			gridBagConstraints81.gridx = 3;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 2;
			gridBagConstraints8.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints8.insets = new java.awt.Insets(5,5,5,5);
			gridBagConstraints8.gridy = 2;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 4;
			gridBagConstraints7.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints7.insets = new java.awt.Insets(5,5,5,5);
			gridBagConstraints7.gridy = 3;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 3;
			gridBagConstraints6.insets = new java.awt.Insets(5,5,5,5);
			gridBagConstraints6.gridy = 3;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 2;
			gridBagConstraints5.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints5.insets = new java.awt.Insets(5,5,5,5);
			gridBagConstraints5.gridy = 0;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 2;
			gridBagConstraints3.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints3.insets = new java.awt.Insets(5,5,5,5);
			gridBagConstraints3.gridy = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = java.awt.GridBagConstraints.NONE;
			gridBagConstraints2.gridx = 3;
			gridBagConstraints2.gridy = 2;
			gridBagConstraints2.gridwidth = 3;
			gridBagConstraints2.anchor = java.awt.GridBagConstraints.CENTER;
			gridBagConstraints2.insets = new java.awt.Insets(5,5,5,5);
			gridBagConstraints2.weightx = 1.0;
			jLabel2 = new JLabel();
			jLabel2.setText("conferma la password:");
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = java.awt.GridBagConstraints.NONE;
			gridBagConstraints1.gridx = 3;
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.gridwidth = 2;
			gridBagConstraints1.anchor = java.awt.GridBagConstraints.CENTER;
			gridBagConstraints1.insets = new java.awt.Insets(5,5,5,5);
			gridBagConstraints1.weightx = 1.0;
			jLabel1 = new JLabel();
			jLabel1.setText("inserisci la nuova password:");
			jLabel = new JLabel();
			jLabel.setText("inserisci la password");
			grid_jPanel = new JPanel();
			grid_jPanel.setLayout(new GridBagLayout());
			grid_jPanel.add(jLabel, gridBagConstraints5);
			grid_jPanel.add(jLabel1, gridBagConstraints3);
			grid_jPanel.add(getNuovaPw_JPassword(), gridBagConstraints1);
			grid_jPanel.add(jLabel2, gridBagConstraints8);
			grid_jPanel.add(getConfermaPw_JPassword(), gridBagConstraints2);
			grid_jPanel.add(getJButton2(), gridBagConstraints6);
			grid_jPanel.add(getJButton1(), gridBagConstraints7);
			grid_jPanel.add(getVecchiaPw_jPasswordField(), gridBagConstraints81);
		}
		return grid_jPanel;
	}

	/**
	 * This method initializes jPasswordField	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getNuovaPw_JPassword() {
		if (inserisci_jPasswordField == null) {
			inserisci_jPasswordField = new JPasswordField();
			inserisci_jPasswordField.setPreferredSize(new java.awt.Dimension(132,23));
		}
		return inserisci_jPasswordField;
	}

	/**
	 * This method initializes jPasswordField1	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getConfermaPw_JPassword() {
		if (conferma_jPasswordField == null) {
			conferma_jPasswordField = new JPasswordField();			
			conferma_jPasswordField.setPreferredSize(new java.awt.Dimension(132,23));
		}
		return conferma_jPasswordField;
	}

	
	private ChangePW_jDialog getThis(){
		return this;
	}
	
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("invia");
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("annulla");
		}
		return jButton1;
	}

	/**
	 * This method initializes vecchiaPw_jPasswordField	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getVecchiaPw_jPasswordField() {
		if (vecchiaPw_jPasswordField == null) {
			vecchiaPw_jPasswordField = new JPasswordField();
			vecchiaPw_jPasswordField.setPreferredSize(new java.awt.Dimension(132,23));
		}
		return vecchiaPw_jPasswordField;
	}

	public static void main(String[] args) {
		ChangePW_jDialog p=new ChangePW_jDialog();
		p.setVisible(true);
		p.requestFocus();
		System.exit(0);
	}


}  //  @jve:decl-index=0:visual-constraint="147,18"
