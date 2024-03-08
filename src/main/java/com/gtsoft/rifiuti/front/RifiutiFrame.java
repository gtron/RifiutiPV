/* * Creato il 29-giu-2005
 * 
 */
package com.gtsoft.rifiuti.front;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.UIManager;

import com.gtsoft.rifiuti.data.Carico;
import com.gtsoft.rifiuti.data.CaricoParziale;
import com.gtsoft.rifiuti.data.Movimento;
import com.gtsoft.rifiuti.data.Rifiuto;
import com.gtsoft.rifiuti.front.data.AbstractSwg;
import com.gtsoft.rifiuti.front.data.SwgMovimento;
import com.gtsoft.rifiuti.front.data.SwgRifiuto;
import com.gtsoft.rifiuti.front.data.SwgScarico;
import com.gtsoft.utils.FormattedDate;
import com.gtsoft.utils.HsqlDB;
import com.gtsoft.utils.ImportManager;
import com.gtsoft.utils.StoricoManager;
import com.gtsoft.utils.SwingWorker;
import com.gtsoft.utils.config.BackupManager;
import com.gtsoft.utils.config.Config;
import com.gtsoft.utils.config.ConfigManager;
import com.gtsoft.utils.config.PasswordDemo;
import com.gtsoft.utils.reports.ReportManager;
import com.gtsoft.utils.superdata.IDatabase;

public class RifiutiFrame extends JFrame {

	public static boolean isFumi = true;
	public static boolean separateFumi = true;
	public static boolean isRifiuto342 = false;

	//{{ FIELDS JFRAME
	private boolean isSchermoIntero = true;

	private JPanel jContentPane = null;

	private javax.swing.JMenuBar jJMenuBar = null;

	private javax.swing.JMenu fileMenu = null;

	private javax.swing.JMenu strumentiMenu = null;

	private javax.swing.JMenu helpMenu = null;

	private javax.swing.JMenuItem exitMenuItem = null;

	private javax.swing.JMenuItem aboutMenuItem = null;

	private javax.swing.JMenuItem movimentiMenuItem = null;

	private javax.swing.JMenuItem rifiutiMenuItem = null;

	//    private javax.swing.JMenuItem carichiMenuItem = null;

	private javax.swing.JMenuItem stampaMenuItem = null;

	private javax.swing.JMenuItem restoreMenuItem = null;

	//    private JPanel jPanel1 = null;
	//
	//    private JLabel jLabel = null;
	//
	//    private JTextField jTextField = null;
	//
	//    private JLabel jLabel1 = null;
	//
	//    private JTextField jTextField1 = null;
	//
	//    private JButton jButton = null;

	private static JLabel statusBar =  new JLabel();

	private JPanel jToolBar = null;

	private JMenuItem dataItem = null;

	private JMenuItem nomeItem = null;;

	private JMainTable jMainTable;
	private JPanel jPanelToolBar;

	private JButton jButtonPrint;
	private JButton jButtonMovimenti;

	private JButton rifiuti_jButton;

	private JButton registro_jButton;

	private JButton scarichi_jButton;

	private JMenuItem scarichiMenuItem;

	private JButton storico_jButton;

	private JMenuItem storicoMenuItem;

	private JMenuItem filtroMenuItem;

	private JMenuItem rifiutiFiltroMenuItem;

	//    private JMenu filtroCheckBoxMenu;

	//    private JMenuItem fornitoreFiltroMenuItem;

	private RifiutiActionListener rifiutiActionListener = new RifiutiActionListener();

	private JMenuItem dataFiltroMenuItem;

	private JSplitPane jSplitPane;


	//}}


	public static boolean read_only=false;

	private static AbstractSwg swgCorrente;
	private static AbstractSwg[] swgUtilizzabili = new AbstractSwg[5] ;

	//    private static boolean isPasswordOk=false;

	private IDatabase db;

	//    private JMenuItem exportDbMenuItem;
	//
	//    private JMenuItem importDbMenuItem;
	//
	//    private JPanel statusBar_JPanel;


	public IDatabase getDatabase() throws Exception {
		if (db == null) {
			db = new HsqlDB();
			// db = new AccessDB();
			db.start();
		}
		return db;
	}

	public RifiutiFrame() throws Exception {
		super();
	}

	public void init(SplashScreen splash){

		//splash.advance("");

		try {
			splash.advance("Caricamento interfaccia utente ...");
			setSwgCorrente(Cost.SwgUtilizzabili.SCARICHI);
			setSwgCorrente(Cost.SwgUtilizzabili.REGISTRO);

		} catch (Exception e) {
			System.out.println("--------------------");
			System.out.println("Error Loading SwgMovimento\n");
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Errore durante il caricamento interfaccia utente!",
					" Errore Database ",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		try {
			splash.advance("Caricamento dati ...");
			refreshElenchi() ;

			// Carica anno precedente se necessario
			JComboBox anno_JComboBox = getSwgMovimento().getFilterManager().getContent_filtri_JPanel().getAnno_JComboBox() ;
			int annoCorrente = Calendar.getInstance().get(Calendar.YEAR) ;
			if ( getSwgMovimento().getElementi().size() < 1 &&
					JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,
							"Non sono stati trovati movimenti per l'anno " + annoCorrente + ".\n\n"+
									"Si vuole caricare l'anno " + (annoCorrente - 1 ) + "?")  ) {
				anno_JComboBox.addItem(Integer.valueOf( annoCorrente - 1 ));
				setAnnoPrecedente();
			}
			else {
				anno_JComboBox.addItem(Integer.valueOf( annoCorrente ));
			}

			anno_JComboBox.setSelectedIndex(anno_JComboBox.getItemCount() - 1 );

			if ( jMainTable.getJTable().getRowCount() > 0 ) {
				int row=0;

				try {
					row=swgCorrente.getIndiceCorrente();
					swgCorrente.getJMainTable().getJTable().setRowSelectionInterval(row,row);
				}
				catch( Exception x ) { x.printStackTrace(); }
			}

		} catch (Exception e) {
			System.out.println("--------------------");
			System.out.println("Error Loading SwgMovimento\n");
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Errore durante il caricamento dei dati!",
					" Errore Database ",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		splash.advance("Avvio ...");
		try {
			initialize();
		} catch (Exception e1) {
			System.out.println("--------------------");
			System.out.println("Error Initializing RifiutiFrame\n");
			e1.printStackTrace();
		}
		splash.hide();
	}

	private void initialize() throws Exception {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(Cost.FRAME_W, Cost.FRAME_H);
		setStatusBar(statusBar, "status bar");
		addWindowListener(new MyWindowsListener());
		addWindowStateListener(new MyWindowsStateListener());
		setJMenuBar(getJJMenuBar());
		setContentPane(getJContentPane());
		showDetailSplit();

		String fumiScorieTitle = "" ;

		if ( separateFumi ) {
			fumiScorieTitle = isFumi ? " :: FUMI :: " : " :: SCORIE :: " ;
		}

		String frameTitle = Cost.FRAME_TITLE ;
		if ( isRifiuto342 ) {
			frameTitle = Cost.FRAME_TITLE_342 ;
		}

		if(read_only) {
			setTitle(fumiScorieTitle + frameTitle + " - (sola lettura)");
		} else {
			setTitle(fumiScorieTitle + frameTitle );
		}

		setExtendedState(JFrame.MAXIMIZED_BOTH);

		//checkImportFromAccess();
	}


	private void allineaAl() {

		SwingWorker worker = new SwingWorker(){
			@Override
			public Object construct() {
				try {
					/*
                     FormattedDate day = new FormattedDate() ;
                     Carico last = Carico.getLast(getDatabase()) ;
                     if ( last != null )
                     day = last.getData();

					 */

					String sDateTo = JOptionPane.showInputDialog(
							getThis(),
							"Importa dal file : \n" + ConfigManager.getAccessDBFile(getThis())  +
							"\n\nData fino alla quale aggiornare i movimenti (compresa):\n" ,
							"Importazione Movimenti" ,
							JOptionPane.PLAIN_MESSAGE
							);

					if ( sDateTo == null ) {
						JOptionPane.showMessageDialog(getThis(), "Procedura annullata." );
						return null ;
					}
					else if ( ! sDateTo.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}"))
						throw new Exception("Inserire la data nel formato dd/mm/yyyy.") ;

					FormattedDate dataTo = null ;
					try {
						dataTo = new FormattedDate( sDateTo ) ;
						dataTo = new FormattedDate( dataTo.ymdString() + " 23:59:59") ;
					}
					catch ( Exception e ) {
						throw new Exception("Attenzione ! La data inserita non è corretta.") ;
					}
					FormattedDate oggi = new FormattedDate();
					if ( oggi.dmyString().equals(dataTo.dmyString()) || dataTo.after( oggi ) )
						throw new Exception("Inserire una data precedente a quella odierna.") ;

					showElaboration();
					setWait(10);
					//FormattedDate dataTo = new FormattedDate(sDateTo) ;

					ImportManager.importTo( dataTo , ConfigManager.getAccessDBFile(getThis()) , (HsqlDB) getDatabase() ) ;

					StoricoManager.updateStorico(db) ;

					refreshElenchi(true);

					setWait(98);

					AbstractSwg swg = getSwgCorrente();
					setSwgCorrente(Cost.SwgUtilizzabili.RIFIUTI, true);
					setSwgCorrente(Cost.SwgUtilizzabili.REGISTRO, false);
					hideElaboration();
					JOptionPane.showMessageDialog(getThis(), "Allineamento effettuato con successo." );

				}
				catch ( Exception e ) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(getThis(), e.getMessage() ,
							" Errore ",
							JOptionPane.ERROR_MESSAGE);
				}
				finally {
					hideElaboration();
				}

				return null;
			}
		};

		worker.start();


	}

	public void myPack() {
		if (!isSchermoIntero) {
			pack();
		}
	}

	private void gestioneScorie() {
		SwingWorker worker = new SwingWorker(){
			@Override
			public Object construct() {
				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,
						"Iniziare la procedura di importazione scorie ?")) {

					try {
						showElaboration();
						setWait(10);
						//ImportManager.processScorie(getThis());
						setWait(94);
						refreshElenchi(true) ;
						setWait(98);
						AbstractSwg swg = getSwgCorrente();
						setSwgCorrente(Cost.SwgUtilizzabili.RIFIUTI, true);
						setSwgCorrente(Cost.SwgUtilizzabili.REGISTRO, false);
						JOptionPane.showMessageDialog(getThis(), "Scorie importate con successo." );
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(getThis(), "Errore durante il caricamento delle scorie!",
								" Errore Database ",
								JOptionPane.ERROR_MESSAGE);
					}

					hideElaboration();
				}
				else {
					JOptionPane.showMessageDialog(getThis(), "Procedura annullata." );
				}
				return null;
			}
		};

		worker.start();


	}

	private void chiusuraAnno() {
		//int anno = ( new GregorianCalendar().get(Calendar.YEAR) - 1 );

		int anno = getAnnoAsInt().intValue() ;

		if ( oldAnno == null ) {
			anno = anno - 1 ;
		}

		if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,
				"Iniziare la procedura di chiusura dell'anno " + anno + " ?")) {

			showElaboration();

			try {
				ImportManager.chiusuraAnno(anno, this);
				hideElaboration();
				JOptionPane.showMessageDialog(this, "Chiusura anno effettuata con successo." );
				refreshElenchi(true) ;
			} catch (Exception e) {

				try {

					hideElaboration();
					if ( e.getMessage().startsWith("StampaRifiuti") ) {
						// JOptionPane.showMessageDialog(this, e.getMessage(), " Attenzione ", JOptionPane.WARNING_MESSAGE );

						if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,
								"Attenzione! Sono presenti dei movimenti non ancora stampati sul registro.\n\n" +
										"Li si vuole stampare ora ? \n " +
										"( Attenzione : Sarà comunque necessario lanciare  "
										+ "\n nuovamente la procedura di chiusura anno )")) {

							setAnnoPrecedente();

							new stampaRegistri_jDialog( this ).setVisible(true);
						}
						else {
							JOptionPane.showMessageDialog(this, "Procedura annullata." );
							return ;
						}
					}
					else{
						e.printStackTrace();
						JOptionPane.showMessageDialog(this, "Errore durante la chiusura dell'anno!",
								" Errore Database ",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				catch ( Exception exc ) {
					exc.printStackTrace();
					JOptionPane.showMessageDialog(this, "Errore durante la chiusura dell'anno!",
							" Errore Database ",
							JOptionPane.ERROR_MESSAGE);
				}
			}


		}
		else {
			JOptionPane.showMessageDialog(this, "Procedura annullata." );
		}

	}

	private javax.swing.JPanel getJContentPane() throws Exception {
		if (jContentPane == null) {
			jContentPane = new JPanel(new BorderLayout());
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(statusBar, BorderLayout.SOUTH);
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		}
		return jContentPane;
	}



	//{{ GESTIONE SPLITPANE

	public JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setLeftComponent(getSwgCorrente().getSwg_jPanel());
			jSplitPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			jSplitPane.setBorder(BorderFactory.createEtchedBorder());
			jSplitPane.remove(0);
			jSplitPane.setPreferredSize(new Dimension(800, 500));
			jSplitPane.setOneTouchExpandable(true);
			jSplitPane.setContinuousLayout(true);
			jSplitPane.setResizeWeight(0.7);
			new RifiutiStatusBarAdapter(jSplitPane, "status bar", statusBar);
		}

		return jSplitPane;
	}

	public void showDetailSplit() {

		if(getSwgCorrente().isPrimaApertura()) {
			getJSplitPane().setDividerLocation(getSwgCorrente().getSplitLocation());

			if(getJSplitPane().getDividerLocation()+getJSplitPane().getDividerSize()+20==getWidth()) {
				getJSplitPane().setDividerLocation(0.6);
			}
			getSwgCorrente().setPrimaApertura(false);
		}
		else {
			if(getJSplitPane().getDividerLocation()+getJSplitPane().getDividerSize()+20==getWidth()) {
				getJSplitPane().setDividerLocation(0.6);
			} else {
				getJSplitPane().setDividerLocation(getJSplitPane().getDividerLocation());
			}
		}

		getJSplitPane().setRightComponent(swgCorrente.getDetailedPanel());

	}


	//}}
	private JButton getJButton(String text, String statusBar) {
		JButton jButton = new JButton(text);
		//jButton.setText(text);
		setStatusBar(jButton, statusBar);
		jButton.addActionListener(rifiutiActionListener);
		jButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return jButton;
	}

	public static JLabel getStatusBar() {
		new RifiutiStatusBarAdapter(statusBar, "status bar", statusBar);
		return statusBar;
	}

	public void setStatusBar(Component c, String text) {
		new RifiutiStatusBarAdapter(c, text, statusBar);
	}

	private JPanel getJToolBar() throws Exception {
		if(jToolBar==null) {
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			jToolBar = new JPanel(f);

			jButtonPrint = getJButton("Stampa Report", "stampa la pagina corrente [ALT+F12]");
			//  jButtonPrint.setMnemonic(KeyEvent.VK_P);
			jButtonPrint.setMnemonic(KeyEvent.VK_F12);
			//  jButtonPrint.setDisplayedMnemonicIndex(0);

			registro_jButton = getJButton("Registro [ALT+F1]", "visualizza l'elenco dei movimenti");
			registro_jButton.setEnabled(false);
			//  registro_jButton.setMnemonic(KeyEvent.VK_R);
			registro_jButton.setMnemonic(KeyEvent.VK_F1);
			//  registro_jButton.setDisplayedMnemonicIndex(0);

			rifiuti_jButton = getJButton("Anagrafica Rifiuti [ALT+F3]", "visualizza l'elenco dei rifiuti");
			//rifiuti_jButton.setMnemonic(KeyEvent.VK_A);
			rifiuti_jButton.setMnemonic(KeyEvent.VK_F3);
			//rifiuti_jButton.setDisplayedMnemonicIndex(0);

			storico_jButton = getJButton("Storico", "visualizza l'andamento delle giacenze dei rifiuti");

			scarichi_jButton = getJButton("Scarichi [ALT+F2]", "scarica uno o più carichi...");
			// scarichi_jButton.setMnemonic(KeyEvent.VK_S);
			scarichi_jButton.setMnemonic(KeyEvent.VK_F2);
			// scarichi_jButton.setDisplayedMnemonicIndex(0);

			if(read_only) {
				scarichi_jButton.setEnabled(false);
			}


			if ( separateFumi ) {
				//            	if ( isFumi ) {
				//            		setBackground(Cost.FumiApp.FRAME_BGCOLOR) ;
				//            		jToolBar.setBackground(Cost.FumiApp.FRAME_BGCOLOR) ;
				//            	}
				//            	else {
				//            		setBackground(Cost.ScorieApp.FRAME_BGCOLOR) ;
				//            		jToolBar.setBackground(Cost.ScorieApp.FRAME_BGCOLOR) ;
				//            	}
			}

			jToolBar.add(registro_jButton);

			if ( ! separateFumi || isFumi ) {
				jToolBar.add(scarichi_jButton);
			}

			jToolBar.add(rifiuti_jButton);
			jToolBar.add(jButtonPrint);
			jToolBar.setPreferredSize(new Dimension(500, 50));
			jToolBar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0), BorderFactory.createRaisedBevelBorder()));
			setStatusBar(jToolBar, "status bar");
		}
		return jToolBar;
	}

	/**
	 * This method initializes printManager_jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getPrintManager_jMenuItem() {
		if (printManager_jMenuItem == null) {
			printManager_jMenuItem = new JMenuItem();
			printManager_jMenuItem.setText("Stampa Registro");
			printManager_jMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new stampaRegistri_jDialog(getThis()).setVisible(true);
				}
			});
		}
		return printManager_jMenuItem;
	}




	private static SplashScreen splash=new SplashScreen("inizio",4,Cost.SPLASH_IMAGE);
	public static void main(String[] args) throws Exception {
		RifiutiFrame app;
		try {
			app = new RifiutiFrame();

			splash.show();

			app.prepareApplication(splash);

			if(PasswordDemo.isPasswordOk()) {
				Config dividedApps = Config.get("dividedapps");

				if ( dividedApps != null ) {
					app.separateFumi = false && Boolean.parseBoolean(dividedApps.getValue() ) ;
				} else {
					ConfigManager.put("dividedapps", Boolean.FALSE.toString() );
				}

				if ( RifiutiFrame.separateFumi ) {
					Config fumiApp = Config.get("isfumi");

					if ( fumiApp != null ) {
						app.isFumi = Boolean.parseBoolean(fumiApp.getValue() ) ;
					} else {
						ConfigManager.put("isfumi", Boolean.FALSE.toString() );
					}

				}
				else {
					Config isRifiuto342 = Config.get("isRifiuto342");

					if ( isRifiuto342 != null ) {
						app.isRifiuto342 = Boolean.parseBoolean(isRifiuto342.getValue() ) ;
					} else {
						ConfigManager.put("isRifiuto342", Boolean.FALSE.toString() );
					}
				}

				try {
					app.startApplication(splash);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void prepareApplication(SplashScreen splash) {

		try {
			splash.advance("Collegamento al database ...");
			getDatabase();
		} catch (Exception e) {
			System.out.println("--------------------");
			System.out.println("Errore nella connessione al database \n");
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Errore durante la connessione al database,\n" +
					"questo potrebbe essere già in uso.",
					" Errore Database ",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		try {
//			if ( separateFumi ) {
//				UIManager.setLookAndFeel("de.muntjak.tinylookandfeel.TinyLookAndFeel");
//			}
			splash.advance("Autenticazione Utente ...");
			ConfigManager.createPasswordGui(this);
		} catch (Exception e) {
			System.out.println("--------------------");
			System.out.println("Errore nella connessione al database \n");
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Errore durante la connessione al database,\n" +
					"questo potrebbe essere già in uso.",
					" Errore Database ",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	public void startApplication(SplashScreen splash) throws Exception {
		init(splash);

		setVisible(true);
		requestFocus();
	}

	public RifiutiFrame getThis() {
		return this;
	}

	private class RifiutiActionListener extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(rifiuti_jButton)|| e.getSource().equals(rifiutiMenuItem)) {
				if (!(swgCorrente.getClass() == SwgRifiuto.class)) {
					setSwgCorrente(Cost.SwgUtilizzabili.RIFIUTI);
				}
			}
			else if (e.getSource().equals(registro_jButton)|| e.getSource().equals(movimentiMenuItem)) {
				if (!(swgCorrente.getClass() == SwgMovimento.class)) {
					setSwgCorrente(Cost.SwgUtilizzabili.REGISTRO);
				}
			}
			else if (e.getSource().equals(scarichi_jButton)|| e.getSource().equals(scarichiMenuItem)) {
				if (!(swgCorrente.getClass() == SwgScarico.class)) {
					setSwgCorrente(Cost.SwgUtilizzabili.SCARICHI);
				}
			}
			//            else if (e.getSource().equals(storico_jButton)|| e.getSource().equals(storicoMenuItem)) {
			//                if (!(swgCorrente.getClass() == SwgStorico.class)) {
			//                    setSwgCorrente(Cost.SwgUtilizzabili.STORICO);
			//                }
			//            }
			else if (e.getSource().equals(jButtonPrint)|| e.getSource().equals(stampaMenuItem)) {
				swgCorrente.stampa();
			}
			else if ( e.getSource().equals(restoreMenuItem)) {

				new BackupManager(getThis()).setVisible(true);

				/* OLD
                if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,
                "Ripristinare il Database alla versione precedente?\n\n" +
                "E' possibile riportare il database alla situazione\n" +
                "precedente all'ultima tra le operazioni di :\n" +
                " - Allineamento\n" +
                " - Stampa del Registro\n" +
                " - Chiusura anno\n" +
                " - Ripristino DB\n\n")) {
	                try {
	                    db.restoreLast();
	                    //restoreMenuItem.setEnabled(false);
	                    refreshElenchi(true);
	                } catch (Exception e1) {

	                    e1.printStackTrace();
	                }
                }
                else {
                    JOptionPane.showMessageDialog(null,"Operazione annullata.");
                }
				 */
			}

		}


	}


	private class MyWindowsListener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			exit();
		}
	}

	private void disableButton(JButton button) {
		if(button.equals(jButtonPrint)) {
			jButtonPrint.setEnabled(false);
			if(read_only==false) {
				scarichi_jButton.setEnabled(true);
			}
			registro_jButton.setEnabled(true);
			rifiuti_jButton.setEnabled(true);
		}
		else if(button.equals(scarichi_jButton)) {
			jButtonPrint.setEnabled(true);
			scarichi_jButton.setEnabled(false);
			registro_jButton.setEnabled(true);
			rifiuti_jButton.setEnabled(true);
		}
		else if(button.equals(registro_jButton)) {
			jButtonPrint.setEnabled(true);
			if(read_only==false) {
				scarichi_jButton.setEnabled(true);
			}
			registro_jButton.setEnabled(false);
			rifiuti_jButton.setEnabled(true);
		}
		else if(button.equals(rifiuti_jButton)) {
			jButtonPrint.setEnabled(true);
			if(read_only==false) {
				scarichi_jButton.setEnabled(true);
			}
			registro_jButton.setEnabled(true);
			rifiuti_jButton.setEnabled(false);
		}
	}

	private void refreshSplitPanel() {
		if(getJSplitPane().getRightComponent()!=null) {
			getJSplitPane().remove(getJSplitPane().getRightComponent());
		}
		getJSplitPane().setLeftComponent(swgCorrente.getSwg_jPanel());
		showDetailSplit();
		myPack();
	}
	private class MyWindowsStateListener implements WindowStateListener {
		public void windowStateChanged(WindowEvent e) {
			if (e.getNewState() == MAXIMIZED_BOTH) {
				isSchermoIntero = true;
			} else {
				isSchermoIntero = false;
			}
		}

	}

	//{{ GESTIONE MENU
	private javax.swing.JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new javax.swing.JMenuBar();
			jJMenuBar.add(getFileMenu());
			jJMenuBar.add(getStrumentiMenu());
			jJMenuBar.add(getReportMenu());
			jJMenuBar.add(getHelpMenu());
		}
		return jJMenuBar;
	}

	private javax.swing.JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new javax.swing.JMenu();
			fileMenu.setText("File");
			fileMenu.add(getStampaMenuItem());
			fileMenu.add(getRestoreMenuItem());

			//fileMenu.add(getExportXLS_jMenuItem());
			fileMenu.add(getExitMenuItem());
		}
		return fileMenu;
	}

	private javax.swing.JMenuItem getStampaMenuItem() {
		if (stampaMenuItem == null) {
			stampaMenuItem = new javax.swing.JMenuItem();
			stampaMenuItem.setText("Stampa [ALT+F12]");
			stampaMenuItem.addActionListener(rifiutiActionListener);
		}
		return stampaMenuItem;
	}

	private javax.swing.JMenuItem getRestoreMenuItem() {
		if (restoreMenuItem == null) {
			restoreMenuItem = new javax.swing.JMenuItem();
			restoreMenuItem.setText("Ripristina DB");
			restoreMenuItem.addActionListener(rifiutiActionListener);

			if ( read_only ) {
				restoreMenuItem.setEnabled(false);
			}
		}
		return restoreMenuItem;
	}


	private JMenuItem exportXLS_jMenuItem = null ;
	private JMenuItem getExportXLS_jMenuItem() {
		if (exportXLS_jMenuItem == null) {
			exportXLS_jMenuItem = new JMenuItem();
			exportXLS_jMenuItem.setText("Esportazione in Excel ...");
			exportXLS_jMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {


					SwingWorker worker= new SwingWorker(){

						@Override
						public Object construct(){
							try {
								JFileChooser fileChooser = new JFileChooser( );
								fileChooser.setApproveButtonText("Salva") ;
								fileChooser.setDialogTitle("Esportazione in Excel ... ");

								fileChooser.addChoosableFileFilter(new XlsFilter());
								fileChooser.setFileFilter(new XlsFilter());

								// Open file dialog.
								fileChooser.showOpenDialog(getThis());

								File filename = fileChooser.getSelectedFile() ;
								if ( filename != null ) {
									showElaboration();
									setWait(30);

									if ( filename.getName().indexOf(".") < 0 ) {

									}

									swgCorrente.exportXLS(filename.getAbsolutePath()) ;
									setWait(100);
									hideElaboration();
									JOptionPane.showMessageDialog(null,"Esportazione avvenuta con successo.");
								}


							}
							catch(Exception ex) {
								hideElaboration();
								ex.printStackTrace();
								JOptionPane.showMessageDialog(null,"Attenzione: Si è verificato un errore.");
							}
							return null;
						}
					};
					worker.start();
				}
			});
		}
		return exportXLS_jMenuItem;
	}

	private javax.swing.JMenuItem getExitMenuItem() {
		if (exitMenuItem == null) {
			exitMenuItem = new javax.swing.JMenuItem();
			exitMenuItem.setText("Esci");
			exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					exit();
				}
			});
		}
		return exitMenuItem;
	}


	/* MENU STRUMENTI */
	private javax.swing.JMenu getStrumentiMenu() {
		if (strumentiMenu == null) {
			strumentiMenu = new javax.swing.JMenu();
			strumentiMenu.setText("Strumenti");
			strumentiMenu.add(getPrintManager_jMenuItem());
			strumentiMenu.add(getMovimentiMenuItem());
			strumentiMenu.add(getRifiutiMenuItem());

			if ( ! separateFumi || isFumi ) {
				strumentiMenu.add(getScarichiMenuItem());
			}

			//strumentiMenu.add(new JSeparator());
			//strumentiMenu.add(getGestioneScorie_jMenuItem());
			strumentiMenu.add(new JSeparator());
			strumentiMenu.add(getImportAccess_jMenuItem());
			strumentiMenu.add(getSetFileAccess_jMenuItem());
			strumentiMenu.add(new JSeparator());
			//            if ( ! separateFumi || isFumi || isRifiuto342 )
			strumentiMenu.add(getChiusuraAnno_jMenuItem());

		}
		return strumentiMenu;
	}

	//    private JMenuItem gestioneScorie_jMenuItem = null ;
	//    private JMenuItem getGestioneScorie_jMenuItem() {
	//        if (gestioneScorie_jMenuItem == null) {
	//            gestioneScorie_jMenuItem = new JMenuItem("Gestione Scorie ...");
	//            gestioneScorie_jMenuItem.addActionListener(new java.awt.event.ActionListener() {
	//                public void actionPerformed(java.awt.event.ActionEvent e) {
	//                    gestioneScorie();
	//                }
	//            });
	//            if ( read_only ) {
	//                gestioneScorie_jMenuItem.setEnabled(false) ;
	//            }
	//        }
	//        return gestioneScorie_jMenuItem;
	//    }

	private JMenuItem chiusuraAnno_jMenuItem = null ;
	private JMenuItem getChiusuraAnno_jMenuItem() {
		if (chiusuraAnno_jMenuItem == null) {
			chiusuraAnno_jMenuItem = new JMenuItem();
			chiusuraAnno_jMenuItem.setText("Chiusura anno");
			chiusuraAnno_jMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					chiusuraAnno();
				}
			});
			if ( read_only ) {
				chiusuraAnno_jMenuItem.setEnabled(false) ;
			}
		}
		return chiusuraAnno_jMenuItem;
	}

	private JMenuItem importAccess_jMenuItem = null ;
	private JMenuItem setFileAccess_jMenuItem = null ;
	private JMenuItem getImportAccess_jMenuItem() {
		if (importAccess_jMenuItem == null) {
			importAccess_jMenuItem = new JMenuItem();


			//importAccess_jMenuItem.setText("Importa Nuovi Carichi ...(" + filename + ")" );
			importAccess_jMenuItem.setText("Allinea Al ... " );
			importAccess_jMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					allineaAl();
					//checkImportFromAccess();
				}
			});
			if ( read_only ) {
				importAccess_jMenuItem.setEnabled(false) ;
			}
		}
		return importAccess_jMenuItem;
	}
	private JMenuItem getSetFileAccess_jMenuItem() {
		if (setFileAccess_jMenuItem == null) {
			setFileAccess_jMenuItem = new JMenuItem();

			setFileAccess_jMenuItem.setText("Selezione File Access ... ");
			setFileAccess_jMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						ConfigManager.getAccessDBFile(getThis(), true);
						JOptionPane.showMessageDialog(null,"Database impostato. Ora è possibile procedere con l'importazione.");
					}
					catch(Exception ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(null,"Attenzione: Il database selezionato non è corretto.");
					}
				}
			});
			if ( read_only ) {
				setFileAccess_jMenuItem.setEnabled(false);
			}
		}
		return setFileAccess_jMenuItem;
	}


	private javax.swing.JMenuItem getMovimentiMenuItem() {
		if (movimentiMenuItem == null) {
			movimentiMenuItem = new javax.swing.JMenuItem();
			movimentiMenuItem.setText("Registro [ALT+F1]");
			setStatusBar(movimentiMenuItem, "elenco di tutti i movimenti");
			movimentiMenuItem.addActionListener(rifiutiActionListener);
		}
		return movimentiMenuItem;
	}

	private javax.swing.JMenuItem getRifiutiMenuItem() {
		if (rifiutiMenuItem == null) {
			rifiutiMenuItem = new javax.swing.JMenuItem();
			rifiutiMenuItem.setText("Anagrafica Rifiuti [ALT+F3]");
			setStatusBar(rifiutiMenuItem, "elenco dei rifiuti trattati");
			rifiutiMenuItem.addActionListener(rifiutiActionListener);
		}
		return rifiutiMenuItem;
	}

	private javax.swing.JMenuItem getScarichiMenuItem() {
		if (scarichiMenuItem == null) {
			scarichiMenuItem = new javax.swing.JMenuItem();
			scarichiMenuItem.setText("Scarichi [ALT+F2]");
			if(RifiutiFrame.read_only==true) {
				scarichiMenuItem.setEnabled(false);
			}
			setStatusBar(scarichiMenuItem, "effetua scarichi");
			scarichiMenuItem.addActionListener(rifiutiActionListener);
		}
		return scarichiMenuItem;
	}


	private javax.swing.JMenu getHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new javax.swing.JMenu();
			helpMenu.setText("Aiuto");
			helpMenu.add(getAboutMenuItem());
		}
		return helpMenu;
	}

	private javax.swing.JMenuItem getAboutMenuItem() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new javax.swing.JMenuItem();
			aboutMenuItem.setText("About");
			aboutMenuItem.setMnemonic('H');
			aboutMenuItem
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new About_JDialog(null).showDialog();
				}
			});
		}
		return aboutMenuItem;
	}

	//}}

	public static class Cost {

		/*
		 * 
		 * ...
		 * 3.1.9 - BugFix : non pulivo la cache dopo il purge delle tabelle nella chiusura anno
		 */
		public final static String APP_TITLE = "Gestione Rifiuti";
		public final static String APP_TITLE_342 = "Gestione Rifiuto Fanghi Ex ENI Risorse";

		public final static String APP_VERSION = "3.1.9";

		public final static String APP_NAME = APP_TITLE + " v." + APP_VERSION ;

		public final static String FRAME_TITLE = APP_NAME ;
		public final static String FRAME_TITLE_342 = APP_TITLE_342 + " v." + APP_VERSION ;

		public final static int FRAME_W = 800;

		public final static int FRAME_H = 600;

		public static class SwgUtilizzabili {
			public final static int RIFIUTI = 0 ;
			public final static int REGISTRO = 1 ;
			public final static int SCARICHI = 2 ;
			public final static int STORICO = 3 ;
		}

		public static final String DATADIR="data/";
		public static final String DATABASE= DATADIR + "rifiuti.hsql";
		public static final String DATABASE_TMP="rifiuti";

		public static final String SPLASH_IMAGE="/image/splash.gif";
		public static final String ELABORATION_IMAGE="/image/splash.gif";

		public static final long SPLASH_DELAY = 5000;

		//        public static interface FumiApp {
		//        	public static final Color FRAME_BGCOLOR = new Color(204,51,0);
		//        	public static final Color BUTTONS_BGCOLOR = new Color(240,230,230);
		//        }
		//        public static interface ScorieApp {
		//        	public static final Color FRAME_BGCOLOR = new Color(153,153,204);
		//        	public static final Color BUTTONS_BGCOLOR = new Color(230,240,230);
		//        }

	}

	public AbstractSwg getSwgCorrente() {
		return swgCorrente;
	}

	public boolean isSchermoIntero() {
		return isSchermoIntero;
	}

	private JMenuItem printManager_jMenuItem = null;

	private static String anno =  null ;

	private Integer annoAsInt;
	private Integer oldAnno ;
	public void setAnno(String anno) throws Exception {

		try {
			annoAsInt=Integer.valueOf(anno);
			RifiutiFrame.anno=anno;
		}
		catch ( Exception e ) { }

		try {
			getDatabase().changeYear(annoAsInt);
			clearCaches();
			refreshElenchi();
		}
		catch ( Exception e ) {
			hideElaboration();
			JOptionPane.showMessageDialog(this, "Errore durante il caricamento dell'anno " + anno,
					" Errore Database ",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	public void setAnnoPrecedente() throws Exception {
		if ( oldAnno == null ) {
			oldAnno = annoAsInt ;
			annoAsInt =  Integer.valueOf( annoAsInt.intValue() - 1 ) ;
			RifiutiFrame.anno= "" + annoAsInt;

			getSwgMovimento().getFilterManager().getContent_filtri_JPanel().setAnno_comboBox(anno);

			clearCaches();
			refreshElenchi();
		}
	}
	public void setAnnoCorrente() throws Exception {

		if ( annoAsInt != null && ! annoAsInt.equals(oldAnno)) {
			annoAsInt = oldAnno ;
			anno= "" + annoAsInt;
			oldAnno = null ;

			setAnno(anno);
		}
	}

	public Integer getAnnoAsInt(){
		if (annoAsInt==null) {
			annoAsInt=Integer.valueOf(getAnno());
		}
		return annoAsInt;
	}

	public static String getAnnoStatic() {
		if ( anno != null && anno.length() == 4 )
			return anno ;
		else {
			GregorianCalendar c = new GregorianCalendar();
			return "" + c.get(Calendar.YEAR);
		}
	}

	public String getAnno() {
		return getAnnoStatic();
	}

	public void scaricaMovimento( Movimento m ) {

		SwgMovimento s = (SwgMovimento) swgUtilizzabili[Cost.SwgUtilizzabili.REGISTRO] ;
		s.addMovimento(m) ;
		swgUtilizzabili[Cost.SwgUtilizzabili.RIFIUTI] = null ;

	}

	private RifiutiContainerListener rifiutiContainerListener= new RifiutiContainerListener();

	private class RifiutiContainerListener implements ContainerListener {

		public void componentAdded(ContainerEvent e) {
			Container cont=getSwgCorrente().getFilterManager().getContent_filtri_JPanel().get_filtri_JPanel();
			Component[] a_comp=cont.getComponents();
			int[] x_sizes=new int[a_comp.length];
			for(int i=0;i<a_comp.length;i++){
				x_sizes[i]=a_comp[i].getPreferredSize().width;
			}
			Arrays.sort(x_sizes);
			int pos_x=x_sizes[x_sizes.length-1];
			if(pos_x>getJSplitPane().getDividerLocation()) {
				getJSplitPane().setDividerLocation(pos_x);
			}
		}

		public void componentRemoved(ContainerEvent e) {

		}
	}
	public void setSwgCorrente( int swg ) {
		setSwgCorrente(swg, false);
	}
	public void setSwgCorrente( int swg , boolean reload ) {

		switch ( swg ) {

		case Cost.SwgUtilizzabili.RIFIUTI :
			try {
				if ( reload || swgUtilizzabili[swg] == null ) {
					Rifiuto.clearCache() ;
					swgUtilizzabili[swg] = new SwgRifiuto(getThis());
					swgUtilizzabili[swg].getFilterManager().getContent_filtri_JPanel().get_filtri_JPanel().addContainerListener(rifiutiContainerListener);
				}
			}
			catch (Exception e1) { e1.printStackTrace(); }
			swgCorrente = swgUtilizzabili[swg] ;

			refreshSplitPanel();

			if ( rifiuti_jButton != null ) {
				disableButton(rifiuti_jButton);
			}

			selectionInitialize();

			break ;

		case Cost.SwgUtilizzabili.SCARICHI :
			try {
				if ( reload || swgUtilizzabili[swg] == null ) {

					swgUtilizzabili[swg] = new SwgScarico(getThis() , getAnno() );
					swgUtilizzabili[swg].getFilterManager().getContent_filtri_JPanel().
					get_filtri_JPanel().addContainerListener(rifiutiContainerListener);
				}

				swgCorrente = swgUtilizzabili[swg] ;
				if(swgUtilizzabili[RifiutiFrame.Cost.SwgUtilizzabili.SCARICHI]!=null && swgUtilizzabili[RifiutiFrame.Cost.SwgUtilizzabili.REGISTRO]!=null) {
					refreshElencoCarichi();
				}

				refreshSplitPanel();

				if ( scarichi_jButton!= null ) {
					disableButton(scarichi_jButton);
				}

				showDetailSplit();
				getJSplitPane().add( ((SwgScarico)swgCorrente).getDetailedPanel() , JSplitPane.RIGHT , 1 ) ;
				myPack();
			}
			catch (Exception e1) { e1.printStackTrace(); }

			break;

		case Cost.SwgUtilizzabili.REGISTRO:
			try {
				if ( reload || swgUtilizzabili[swg] == null ) {
					swgUtilizzabili[swg] = new SwgMovimento(getThis(), getAnno() );
					swgUtilizzabili[swg].getFilterManager().getContent_filtri_JPanel().
					get_filtri_JPanel().addContainerListener(rifiutiContainerListener);
				}

				swgCorrente = swgUtilizzabili[swg] ;
				refreshSplitPanel();
				if ( registro_jButton!= null ) {
					disableButton(registro_jButton);
				}
			} catch (Exception e1) { e1.printStackTrace(); }
			selectionInitialize();

			//		   case Cost.SwgUtilizzabili.STORICO:
				//	            try {
					//	                if ( reload || swgUtilizzabili[swg] == null ) {
			//	                    swgUtilizzabili[swg] = new SwgStorico(getThis(), getAnno() );
			//		            }
			//	                swgCorrente = swgUtilizzabili[swg] ;
			//	                refreshSplitPanel();
			//	            } catch (Exception e1) { e1.printStackTrace(); }
			//	            selectionInitialize();
		}


		//swgCorrente.selectFirstRecord();

		JComboBox combo=swgCorrente.getFilterManager().getContent_filtri_JPanel().getAnno_JComboBox();
		if(!getAnnoAsInt().equals(combo.getSelectedItem())){
			ActionListener[] ac=combo.getActionListeners();
			if(ac[0]==null) {
				combo.setSelectedItem(getAnnoAsInt());
			} else {
				combo.removeActionListener(ac[0]);
				combo.setSelectedItem(getAnnoAsInt());
				combo.addActionListener(ac[0]);
			}
		}
	}

	private void selectionInitialize() {
		jMainTable = swgCorrente.getJMainTable();
		if ( jMainTable.getJTable().getRowCount() > 0 ) {
			int row=0;
			try {
				row=swgCorrente.getIndiceCorrente();
				swgCorrente.getJMainTable().getJTable().setRowSelectionInterval(row,row);
			}
			catch( Exception x ) { x.printStackTrace(); }
		}
	}

	// Ricarica dati dal DB
	public void refreshElenchi() throws Exception {
		refreshElenchi(true) ;
	}

	public void setWaitCursor( boolean b) {
		if ( b ) {
			this.setCursor(new Cursor(Cursor.WAIT_CURSOR)) ;
		} else {
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)) ;
		}
	}

	public void refreshElenchi(boolean doDB) throws Exception {

		setWaitCursor(true);
		Vector movimenti = null ;

		showElaboration();
		setWait(10);
		if ( doDB ) {
			clearCaches();
			movimenti = Movimento.getAll(getDatabase(), getAnno()) ;
		} else {
			movimenti = getSwgMovimento().getElementi();
		}
		setWait(60);
		getSwgMovimento().setElenco(movimenti);

		refreshElencoCarichi();
		setWait(98);
		swgCorrente.selectFirstRecord() ;
		setWaitCursor(false);
		hideElaboration();
	}

	public void clearCaches() {
		Rifiuto.clearCache() ;
		Carico.clearCache();
		CaricoParziale.clearCache();
	}

	public void refreshElencoCarichi() {
		Vector movimenti = getSwgMovimento().getElementi() ;
		Vector elencoCarichi = new Vector();

		Iterator i=movimenti.iterator();
		Movimento m = null ;

		Carico c = null ;
		while(i.hasNext()) {
			m = (Movimento) i.next();
			if( m.isCarico() || m.isGiacenza() ) {
				if ( !m.getCarico().isScaricato()){
					c = m.getCarico() ;
					c.setMovimento(m) ;
					elencoCarichi.add(c) ;
				}
			}
		}
		swgUtilizzabili[Cost.SwgUtilizzabili.SCARICHI].setElenco(elencoCarichi) ;
		getSwgMovimento().refreshElencoTrasportatori();
	}

	public SwgMovimento getSwgMovimento() {
		return (SwgMovimento) swgUtilizzabili[Cost.SwgUtilizzabili.REGISTRO] ;
	}
	public SwgScarico getSwgScarico() {
		return (SwgScarico) swgUtilizzabili[Cost.SwgUtilizzabili.SCARICHI] ;
	}
	private void exit() {
		if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,
				"Chiudere l'applicazione ?")) {
			try {

				getDatabase().shutdown();

				if(RifiutiFrame.read_only==false) {
					db.export_database();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			System.exit(0);
		}
	}

	/**
	 * @param listaMovimenti
	 */
	public void setStampati(Vector listaMovimenti) throws Exception {
		getDatabase().backup("Stampa movimenti") ;
		Movimento m = null ;
		for ( Iterator i = listaMovimenti.iterator() ; i.hasNext() ; ) {
			m = (Movimento) i.next();
			m.setStampatoRegistro();
		}

		getSwgCorrente().refreshElencoJTable();
	}

	/**ATTENZIONE
	 * questo metodo deve essere chiamato in un thread non facente parte della coda degli eventi (utilizza SwingWorker)
	 * vedi actionPerformed di Swg_jPanel
	 * 
	 * @param percentual
	 */
	private SplashScreen elaborationSplashScreen=new SplashScreen(true,Cost.ELABORATION_IMAGE);

	private  int percentual=0;

	private Timer timer=new Timer();

	public void setWait(int percen){

		timer.cancel();

		if(percen>percentual) {
			percentual=percen;
		}

		elaborationSplashScreen.advance(percentual);
		timer=new Timer();

		timer.schedule(new TimerTask(){

			@Override
			public void run(){
				elaborationSplashScreen.advance(percentual+2);
				percentual=percentual+2;
				if(percentual>89) {
					timer.cancel();
				}
			}

		},0,Cost.SPLASH_DELAY);


	}

	public void showElaboration() {
		if(!splash.isShow()) {
			getThis().setFocusable(false);
			elaborationSplashScreen.getFrame().setModal(true);
			elaborationSplashScreen.show();
		}
	}

	public void hideElaboration() {
		getThis().setFocusable(true);
		elaborationSplashScreen.getFrame().setModal(false);
		elaborationSplashScreen.hide();
		percentual=0;
		timer.cancel();
	}



	public class XlsFilter extends javax.swing.filechooser.FileFilter {
		@Override
		public boolean accept(File file) {
			String filename = file.getName();
			return filename.endsWith(".xls");
		}
		@Override
		public String getDescription() {
			return "Microsoft Excel  (*.xls)";
		}
	}


	private JMenu report_Menu = null ;
	private JMenuItem reportManager_Menu = null ;
	private JMenuItem stampaMovimenti_Menu = null ;
	private JMenuItem exportMovimenti_Menu = null ;
	private JMenuItem stampaRifiuti_Menu = null ;
	private JMenuItem exportRifiuti_Menu = null ;

	private JMenu getReportMenu() {
		if (report_Menu == null) {
			report_Menu = new javax.swing.JMenu();
			report_Menu.setText("Report");
			report_Menu.add(getStampaMenuItem());
			report_Menu.addSeparator();
			report_Menu.add(getReportManager_Menu());
			//report_Menu.add(getExportMovimenti_Menu());
			report_Menu.addSeparator();
			report_Menu.add(getStampaMovimenti_Menu());
			report_Menu.add(getStampaRifiuti_Menu());

		}
		return report_Menu;
	}

	private JMenuItem getStampaMovimenti_Menu() {
		if (stampaMovimenti_Menu == null) {
			stampaMovimenti_Menu = new javax.swing.JMenuItem();
			stampaMovimenti_Menu.setText("Stampa Elenco Movimenti");
			stampaMovimenti_Menu.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					//new reportManager_jDialog(getThis()).setVisible(true) ;
					SwingWorker worker = new SwingWorker(){
						@Override
						public Object construct() {
							try {
								stampa( ReportManager.STAMPA.ELENCO_MOVIMENTI , false);
							}
							catch ( Exception e ) {
								if ( e.getMessage() != "" ) {
									JOptionPane.showMessageDialog(getThis(), e.getMessage() ,
											" Errore ",
											JOptionPane.ERROR_MESSAGE);
								}
							}
							finally {
								hideElaboration();
							}

							return null;
						}
					};
					worker.start();
				}
			});
		}
		return stampaMovimenti_Menu;
	}
	private javax.swing.JMenuItem getExportMovimenti_Menu() {
		if (exportMovimenti_Menu == null) {
			exportMovimenti_Menu = new JMenuItem();
			exportMovimenti_Menu.setText("Esporta Elenco Movimenti");
			exportMovimenti_Menu.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					SwingWorker worker = new SwingWorker(){
						@Override
						public Object construct() {
							try {
								stampa( ReportManager.STAMPA.ELENCO_MOVIMENTI , true);
							}
							catch ( Exception e ) {
								if ( e.getMessage() != "" ) {
									JOptionPane.showMessageDialog(getThis(), e.getMessage() ,
											" Errore ",
											JOptionPane.ERROR_MESSAGE);
								}
							}
							finally {
								hideElaboration();
							}

							return null;
						}
					};
					worker.start();
				}
			});
		}
		return exportMovimenti_Menu;
	}

	private JMenuItem getStampaRifiuti_Menu() {
		if (stampaRifiuti_Menu == null) {
			stampaRifiuti_Menu = new JMenuItem();
			stampaRifiuti_Menu.setText("Stampa Elenco Rifiuti");
			stampaRifiuti_Menu.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					SwingWorker worker = new SwingWorker(){
						@Override
						public Object construct() {
							try {
								stampa( ReportManager.STAMPA.ELENCO_RIFIUTI , false);
							}
							catch ( Exception e ) {
								if ( e.getMessage() != "" ) {
									JOptionPane.showMessageDialog(getThis(), e.getMessage() ,
											" Errore ",
											JOptionPane.ERROR_MESSAGE);
								}
							}
							finally {
								hideElaboration();
							}

							return null;
						}
					};

					worker.start();
				}
			});
		}
		return stampaRifiuti_Menu;
	}
	private JMenuItem getExportRifiuti_Menu() {
		if (exportRifiuti_Menu == null) {
			exportRifiuti_Menu = new JMenuItem();
			exportRifiuti_Menu.setText("Esporta Elenco Rifiuti");
			exportRifiuti_Menu.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					SwingWorker worker = new SwingWorker(){
						@Override
						public Object construct() {
							try {
								stampa( ReportManager.STAMPA.ELENCO_RIFIUTI , true);
							}
							catch ( Exception e ) {
								if ( e.getMessage() != "" ) {
									JOptionPane.showMessageDialog(getThis(), e.getMessage() ,
											" Errore ",
											JOptionPane.ERROR_MESSAGE);
								}
							}
							finally {
								hideElaboration();
							}

							return null;
						}
					};

					worker.start();
				}
			});
		}
		return exportRifiuti_Menu;
	}

	private JMenuItem getReportManager_Menu() {
		if (reportManager_Menu == null) {
			reportManager_Menu = new JMenuItem();
			reportManager_Menu.setText("Gestione Report");
			reportManager_Menu.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					new reportManager_jDialog(getThis()).setVisible(true);
				}
			});
		}
		return reportManager_Menu;
	}


	public void stampa( int tipo , boolean doExport ) throws Exception {
		//getSwgMovimento().getJMainTable().getJTable().selectAll();
		Vector list = getSwgMovimento().getPrintElements() ;

		String nomefile = null ;

		if ( doExport ) {
			JFileChooser fileChooser = new JFileChooser( );
			fileChooser.setApproveButtonText("Salva") ;
			fileChooser.setDialogTitle("Esportazione in Excel ... ");

			fileChooser.addChoosableFileFilter(new XlsFilter());
			fileChooser.setFileFilter(new XlsFilter());

			// Open file dialog.
			fileChooser.showOpenDialog(getThis());

			File file = fileChooser.getSelectedFile() ;

			if (file != null) {
				nomefile = file.getAbsolutePath() ;
				if ( ! nomefile.endsWith(".xls") ) {
					nomefile += ".xls" ;
				}
			}
			else {
				JOptionPane.showMessageDialog(this, "Esportazione annullata." );
				throw new Exception("") ;
			}
		}
		showElaboration();
		setWait(10);
		ReportManager.stampa(tipo, nomefile, list, false) ;
		setWait(98);
	}

}