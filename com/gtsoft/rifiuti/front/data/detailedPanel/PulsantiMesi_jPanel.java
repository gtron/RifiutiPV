package com.gtsoft.rifiuti.front.data.detailedPanel;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.gtsoft.utils.FormattedDate;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;

public class PulsantiMesi_jPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton gen_jButton = null;
	private JButton feb_jButton = null;
	private JButton mar_jButton = null;
	private JButton apr_jButton = null;
	private JButton mag_jButton = null;
	private JButton giu_jButton = null;
	private JButton lug_jButton = null;
	private JButton ago_jButton = null;
	private JButton set_jButton = null;
	private JButton ott_jButton = null;
	private JButton nov_jButton = null;
	private JButton dic_jButton = null;
	/**
	 * This is the default constructor
	 */
	public PulsantiMesi_jPanel() {
		super();
		initialize();
	}
	
	JDateChooser da_dataChooser , a_dataChooser ;
	private JComboBox mesi_jComboBox = null;
	
	public PulsantiMesi_jPanel( JDateChooser da_dataChooser ,JDateChooser a_dataChooser  ) {
		super();
		this.da_dataChooser = da_dataChooser ;
		this.a_dataChooser = a_dataChooser ;
		initialize();
	}
	private void setLimits(String m) {
		FormattedDate d = new FormattedDate( da_dataChooser.getDate() );
		
		da_dataChooser.setDate( new FormattedDate("01/"+m+"/" +d.getAnno() )) ;
		
		int n = Integer.parseInt(m) ;
		n++ ;
		m = "" + n ;
		if ( n < 10 ) 
			m = "0" + m ; 
		
		FormattedDate a = new FormattedDate("01/"+m+"/" +d.getAnno() ) ;
		
		a_dataChooser.setDate( new Date( a.getTime() - 86000000 ) ) ;
		
		
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(2);
		gridLayout.setHgap(2);
		gridLayout.setVgap(2);
		gridLayout.setColumns(6);
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.gridx = 1;
		gridBagConstraints3.gridy = 1;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		this.setLayout(gridLayout);
		this.setSize(613, 100);
		this.add(getGen_jButton(), null);
		this.add(getFeb_jButton(), null);
		this.add(getMar_jButton(), null);
		this.add(getApr_jButton(), null);
		this.add(getMag_jButton(), null);
		this.add(getGiu_jButton(), null);
		this.add(getLug_jButton(), null);
		this.add(getAgo_jButton(), null);
		this.add(getSet_jButton(), null);
		this.add(getOtt_jButton(), null);
		this.add(getNov_jButton(), null);
		this.add(getDic_jButton(), null);
		this.add(getMesi_jComboBox(), null);
	}

	/**
	 * This method initializes gen_jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getGen_jButton() {
		if (gen_jButton == null) {
			gen_jButton = new JButton();
			gen_jButton.setText("Gennaio");
			gen_jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setLimits("01") ;
				}
			});
		}
		return gen_jButton;
	}

	/**
	 * This method initializes feb_jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getFeb_jButton() {
		if (feb_jButton == null) {
			feb_jButton = new JButton();
			feb_jButton.setText("Febbraio");
			feb_jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setLimits("02") ;
				}
			});
		}
		return feb_jButton;
	}

	/**
	 * This method initializes mar_jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getMar_jButton() {
		if (mar_jButton == null) {
			mar_jButton = new JButton();
			mar_jButton.setText("Marzo");
			mar_jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setLimits("03") ;
				}
			});
		}
		return mar_jButton;
	}

	/**
	 * This method initializes apr_jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getApr_jButton() {
		if (apr_jButton == null) {
			apr_jButton = new JButton();
			apr_jButton.setText("Aprile");
			apr_jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setLimits("04") ;
				}
			});
		}
		return apr_jButton;
	}

	/**
	 * This method initializes mag_jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getMag_jButton() {
		if (mag_jButton == null) {
			mag_jButton = new JButton();
			mag_jButton.setText("Maggio");
			mag_jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setLimits("05") ;
				}
			});
		}
		return mag_jButton;
	}

	/**
	 * This method initializes giu_jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getGiu_jButton() {
		if (giu_jButton == null) {
			giu_jButton = new JButton();
			giu_jButton.setText("Giugno");
			giu_jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setLimits("06") ;
				}
			});
		}
		return giu_jButton;
	}

	/**
	 * This method initializes lug_jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getLug_jButton() {
		if (lug_jButton == null) {
			lug_jButton = new JButton();
			lug_jButton.setText("Luglio");
			lug_jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setLimits("07") ;
				}
			});
		}
		return lug_jButton;
	}

	/**
	 * This method initializes ago_jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAgo_jButton() {
		if (ago_jButton == null) {
			ago_jButton = new JButton();
			ago_jButton.setText("Agosto");
			ago_jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setLimits("08") ;
				}
			});
		}
		return ago_jButton;
	}

	/**
	 * This method initializes set_jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSet_jButton() {
		if (set_jButton == null) {
			set_jButton = new JButton();
			set_jButton.setText("Settembre");
			set_jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setLimits("09") ;
				}
			});
		}
		return set_jButton;
	}

	/**
	 * This method initializes ott_jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOtt_jButton() {
		if (ott_jButton == null) {
			ott_jButton = new JButton();
			ott_jButton.setText("Ottobre");
			ott_jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setLimits("10") ;
				}
			});
		}
		return ott_jButton;
	}

	/**
	 * This method initializes nov_jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getNov_jButton() {
		if (nov_jButton == null) {
			nov_jButton = new JButton();
			nov_jButton.setText("Novembre");
			nov_jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setLimits("11") ;
				}
			});
		}
		return nov_jButton;
	}

	/**
	 * This method initializes dic_jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getDic_jButton() {
		if (dic_jButton == null) {
			dic_jButton = new JButton();
			dic_jButton.setText("Dicembre");
			dic_jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setLimits("12") ;
				}
			});
		}
		return dic_jButton;
	}
	/**
	 * This method initializes mesi_jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getMesi_jComboBox() {
		if (mesi_jComboBox == null) {
			mesi_jComboBox = new JComboBox();
			mesi_jComboBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return mesi_jComboBox;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
