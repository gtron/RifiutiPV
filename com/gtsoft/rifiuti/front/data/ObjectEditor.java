/*
 * Created on 4-nov-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.front.data;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.gtsoft.rifiuti.data.sql.SQLRifiuto;
import com.gtsoft.utils.DbValue;
/**
 * @author Gtron - 
 * 
 */
public class ObjectEditor extends JFrame {

	private javax.swing.JPanel jContentPane = null;
	
	private IEditable editedObject = null ;

	private JPanel buttons_jPanel = null;
	private JButton save_jButton = null;
	private JButton delete_jButton = null;
	private JButton cancel_jButton = null;
	private JPanel main_jPanel = null;
	private boolean creaNuovo = false ;
	private  AbstractSwg owner = null ;
	
	/**
	 * This is the default constructor
	 */
	public ObjectEditor() {
		super();
		initialize();
	}
	
	public ObjectEditor( AbstractSwg owner , IEditable o ) {
		super();
		editedObject = o ;
		initialize();
		this.setAlwaysOnTop(true);
		this.setVisible(true);
		this.owner = owner ;
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(400, 340);
		this.setContentPane(getJContentPane());
		this.setTitle("Gestione Anagrafica");
		this.setLocationRelativeTo(null);
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
			jContentPane.add(getButtons_jPanel(), java.awt.BorderLayout.SOUTH);
			jContentPane.add(getMain_jPanel(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getButtons_jPanel() {
		if (buttons_jPanel == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			buttons_jPanel = new JPanel();
			buttons_jPanel.setLayout(flowLayout1);
			flowLayout1.setHgap(15);
			buttons_jPanel.add(getNew_jButton(), null);
			buttons_jPanel.add(getSave_jButton(), null);
			buttons_jPanel.add(getDelete_jButton(), null);
			buttons_jPanel.add(getCancel_jButton(), null);
		}
		return buttons_jPanel;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getSave_jButton() {
		if (save_jButton == null) {
			save_jButton = new JButton();
			save_jButton.setText("Salva");
			save_jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					save_clicked();
				}
			});
		}
		return save_jButton;
	}
	
	
	private JButton getDelete_jButton() {
		if (delete_jButton == null) {
			delete_jButton = new JButton();
			delete_jButton.setText("Elimina");
			delete_jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					delete_clicked();
				}
			});
		}
		return delete_jButton;
	}
	
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getCancel_jButton() {
		if (cancel_jButton == null) {
			cancel_jButton = new JButton();
			cancel_jButton.setText("Annulla");
			cancel_jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					dispose();
				}
			});
		}
		return cancel_jButton;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	
	private FieldEditor[] fields = null ;
	private JButton new_jButton = null;
	private JPanel getMain_jPanel() {
		if (main_jPanel == null) {
		    main_jPanel = new JPanel(); 
			main_jPanel.setLayout(new BoxLayout(main_jPanel, BoxLayout.Y_AXIS));
			main_jPanel.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));
			
			fields = null ;
			fields = editedObject.getEditors() ;
			for( int i = 0 ; i < fields.length ; i++ ) {
			    if ( fields[i].isVisible() ) {
				    main_jPanel.add( fields[i].getPanel() ) ;
				    main_jPanel.add( Box.createRigidArea(new Dimension(4,4)) ) ;
			    }
			}
		}
		return main_jPanel;
	}

	private void delete_clicked() {
	    this.setVisible(false);
	    
		if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,
                "Procedere all'eliminazione di questo rifiuto?")) {
			
			SQLRifiuto sql = new SQLRifiuto() ;
		    HashMap sqlFields = sql.getFields(false);
		    String name = null ;
		    
		    try {
			    for( int i = 0 ; i < fields.length ; i++ ) {
				    name = fields[i].getName();
				    ((DbValue) sqlFields.get(name) ).setValue(fields[i].getValue()) ;
				}

			    sql.delete();

			    JOptionPane.showMessageDialog(this, "Cancellazione effettuata.");
			    dispose();
			    owner.dataModified();
		    }
		    catch ( Exception e ) {
		        JOptionPane.showMessageDialog(this, "Attenzione si è verificato un'errore.",
	                    " Errore ",
	                    JOptionPane.ERROR_MESSAGE);
		    }
			
		}
		else {
			this.setVisible(true);
		}
	}
	
	private void save_clicked() {
	    
	    SQLRifiuto sql = new SQLRifiuto() ;
	    HashMap sqlFields = sql.getFields(false);
	    String name = null ;
	    
	    try {
		    for( int i = 0 ; i < fields.length ; i++ ) {
			    name = fields[i].getName();
			    ((DbValue) sqlFields.get(name) ).setValue(fields[i].getValue()) ;
			}
		    if ( creaNuovo ) {
		        sql.create();
		        creaNuovo = false ;
		    }
		    else {
		        sql.update();
		    }

		    JOptionPane.showMessageDialog(this, "Salvataggio effettuato.");
		    dispose();
		    owner.dataModified();
	    }
	    catch ( Exception e ) {
	        JOptionPane.showMessageDialog(this, "Attenzione il valore del campo " + name + " non è corretto.",
                    " Errore ",
                    JOptionPane.ERROR_MESSAGE);
	    }
	    
	}
	
	private void nuovo_clicked() {
	    for( int i = 0 ; i < fields.length ; i++ ) {
	        fields[i].reset() ;
	    }
	    creaNuovo = true ;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getNew_jButton() {
		if (new_jButton == null) {
			new_jButton = new JButton();
			new_jButton.setText("Nuovo");
			new_jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					nuovo_clicked();
				}
			});
		}
		return new_jButton;
	}
 }  //  @jve:decl-index=0:visual-constraint="10,10"
