package com.gtsoft.utils.config;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.utils.FileManager;
import com.gtsoft.utils.FormattedDate;
import com.gtsoft.utils.HsqlDB;
import com.gtsoft.utils.SwingWorker;

public class BackupManager extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JPanel buttons_jPanel = null;

	private JButton cancel_jButton = null;

	private JButton restore_jButton = null;

	private JPanel list_jPanel = null;

	private JList jList = null;
	
	HashMap backups = null ;  //  @jve:decl-index=0:
	Vector backupKeys = null ;
	RifiutiFrame ownerFrame = null ;
	/**
	 * @param owner
	 */
	public BackupManager(RifiutiFrame owner) {
		super(owner);
		ownerFrame = owner ;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(450, 300);
		this.setTitle("Backup Manager");
		this.setLocationRelativeTo(null);
		this.setContentPane(getJContentPane());

		fillBackupList();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(4);
			borderLayout.setVgap(4);
			jContentPane = new JPanel();
			jContentPane.setLayout(borderLayout);
			jContentPane.setName("");
			jContentPane.add(getButtons_jPanel(), BorderLayout.SOUTH);
			jContentPane.add(getList_jPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes buttons_jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getButtons_jPanel() {
		if (buttons_jPanel == null) {
			buttons_jPanel = new JPanel();
			buttons_jPanel.setLayout(new FlowLayout());
			buttons_jPanel.add(getCancel_jButton(), null);
			buttons_jPanel.add(getRestore_jButton(), null);
		}
		return buttons_jPanel;
	}

	/**
	 * This method initializes cancel_jButton	
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
	 * This method initializes restore_jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getRestore_jButton() {
		if (restore_jButton == null) {
			restore_jButton = new JButton();
			restore_jButton.setText("Ripristina");
			restore_jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
					SwingWorker worker = new SwingWorker(){
                        public Object construct() {
                            try {  
                            	
            					FormattedDate key = (FormattedDate) backupKeys.get(jList.getSelectedIndex()) ;
            					try {
            						ownerFrame.showElaboration() ;
            						ownerFrame.setWait(10) ;
            						((HsqlDB) ownerFrame.getDatabase()).restore( 
            								(String) ((HashMap)backups.get(key)).get("backup"));
            						ownerFrame.setWait(60) ;
            						ownerFrame.refreshElenchi(true);
            						ownerFrame.setWait(90) ;
            						
            						JOptionPane.showMessageDialog(ownerFrame, "Ripristino effettuato con successo !",
            			                    " Ripristino Database ",
            			                    JOptionPane.INFORMATION_MESSAGE);
            					}
            					catch ( Exception exc ) {
            						JOptionPane.showMessageDialog(ownerFrame, "Errore durante il ripristino del database !",
            			                    " Errore Database ",
            			                    JOptionPane.ERROR_MESSAGE);
            					}
                            }
                            catch ( Exception e ) {
                                if ( e.getMessage() != "" ) {
                                    JOptionPane.showMessageDialog(ownerFrame, e.getMessage() ,
                                            " Errore ",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            finally {
                            	ownerFrame.hideElaboration();
                            }
                            
                            return null;
                        }
                    };
                    
                    worker.start(); 
                    
				}
			});
		}
		return restore_jButton;
	}

	/**
	 * This method initializes list_jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getList_jPanel() {
		if (list_jPanel == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;
			list_jPanel = new JPanel();
			list_jPanel.setLayout(new GridBagLayout());
			list_jPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4), BorderFactory.createTitledBorder(null, "Backup Disponibili", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51))));
			
			JScrollPane scrollPane = new JScrollPane(getJList());
			
			list_jPanel.add(scrollPane, gridBagConstraints);
		}
		return list_jPanel;
	}

	/**
	 * This method initializes jList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJList() {
		if (jList == null) {
			jList = new JList();
		}
		return jList;
	}
	
	private void fillBackupList() {
		backups  = FileManager.listBackups( HsqlDB.BACKUPDIR ) ;
		
		Vector backups_list = new Vector(backups.size()) ;
		
//		for ( int x = 0 ; x < backups.size(); x++ ) 
//			backups_list.add("") ;
		
		backupKeys = new Vector(backups.size()) ;
		Set keys = backups.keySet() ;
		
		
		for( Iterator i = keys.iterator() ; i.hasNext() ; ) {
			backupKeys.add( i.next() ) ;
		}
		
		Collections.sort(backupKeys);

		HashMap day = null ;
		//int pos = backupKeys.size() ;
		
		for( Iterator i = backupKeys.iterator() ; i.hasNext() ; ) {
			day = (HashMap) backups.get( i.next() ) ;
			//backups_list.set( --pos , (String) day.get("comment") ) ;
			backups_list.add((String) day.get("comment") ) ;
		}
		
		getJList().setListData(backups_list);
	}

	public static void main(String[] args) { 
		new BackupManager(null).setVisible(true);
	}
}
