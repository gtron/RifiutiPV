package com.gtsoft.rifiuti.front.data.detailedPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gtsoft.rifiuti.data.Soggetto;

public class Soggetto_detailed_Dialog extends JDialog {

	private JPanel jContentPane = null;
	private JPanel title_JPanel;
	private Soggetto soggetto;
	private JLabel title_JLabel;
	private JPanel dati_JPanel;
	private JPanel codFisc_JPanel;
	private JPanel pIva_JPanel;
	private JPanel tel_JPanel;
	private JPanel email_JPanel;
	private JPanel indirizzo_JPanel;
	private JPanel via_JPanel;
	private JPanel localita_JPanel;
	private JPanel provincia_JPanel;
	private JPanel cod_JPanel;

	/**
	 * This is the default constructor 
	 */
	public Soggetto_detailed_Dialog(Soggetto soggetto, JFrame owner) {
		super(owner);
		setSoggetto(soggetto);
		initialize();
		pack();
		centerOnOwner(owner);
		setVisible(true);
		setResizable(false);
	}
	
	public void centerOnOwner(Frame owner){
	    Rectangle rectOwner, rectDialog;

	    rectOwner = owner.getBounds();
	    rectDialog = this.getBounds();
	    setLocation(rectOwner.x + rectOwner.width /2 - rectDialog.width /2,
	    rectOwner.y + rectOwner.height/2 - rectDialog.height /2);
	    }

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setModal(true);
		this.setTitle("Dettaglio ... ");
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getTitle_JPanel(),BorderLayout.NORTH);
			jContentPane.add(getDati_JPanel(),BorderLayout.CENTER);
		}
		return jContentPane;
	}
	
	private JPanel getDati_JPanel() {
		if(dati_JPanel==null){
			dati_JPanel=new JPanel(new GridBagLayout());
			
			GridBagConstraints contraints=new GridBagConstraints();
			
			contraints.insets=new Insets(10,10,0,10);
			contraints.gridx=0;
			contraints.gridy=0;
			contraints.anchor=GridBagConstraints.WEST;
			dati_JPanel.add(getCod_JPanel(),contraints);
			
			contraints.gridx=0;
			contraints.gridy=1;
			contraints.anchor=GridBagConstraints.WEST;
			contraints.insets=new Insets(0,10,10,10);
			dati_JPanel.add(getCodFisc_JPanel(),contraints);
			
			
			contraints.gridx=1;
			contraints.gridy=1;
			contraints.anchor=GridBagConstraints.EAST;
			dati_JPanel.add(getPIva_JPanel(),contraints);
			
			
			/*
			contraints.gridx=0;
			contraints.gridy=1;
			contraints.gridwidth=2;
			contraints.gridheight=2;
			contraints.fill=GridBagConstraints.HORIZONTAL;
			contraints.anchor=GridBagConstraints.WEST;
			dati_JPanel.add(getIndirizzo_JPanel(),contraints);
			*/
			
			
			contraints.gridx=0;
			contraints.gridy=2;
			contraints.gridwidth=2;
			contraints.anchor=GridBagConstraints.WEST;
			contraints.insets=new Insets(10,10,0,10);
			dati_JPanel.add(getVia_JPanel(),contraints);
			
			contraints.gridx=0;
			contraints.gridy=3;
			contraints.gridwidth=1;
			contraints.insets=new Insets(0,10,10,10);
			contraints.anchor=GridBagConstraints.WEST;
			dati_JPanel.add(getLocalita_JPanel(),contraints);
			
			contraints.gridx=1;
			contraints.gridy=3;
			contraints.anchor=GridBagConstraints.EAST;
			dati_JPanel.add(getProvincia_JPanel(),contraints);
			
			
			contraints.gridx=0;
			contraints.gridy=4;

			contraints.gridheight=1;
			contraints.fill=GridBagConstraints.NONE;
			contraints.anchor=GridBagConstraints.WEST;
			contraints.insets=new Insets(10,10,10,10);
			dati_JPanel.add(getTel_JPanel(),contraints);
			
			contraints.gridx=1;
			contraints.gridy=4;
			contraints.anchor=GridBagConstraints.EAST;
			dati_JPanel.add(getEmail_JPanel(),contraints);
		}
		
		return dati_JPanel;
	}

	private JPanel getCod_JPanel() {
		if(cod_JPanel==null){
			cod_JPanel=new JPanel();
			cod_JPanel.add(createAttribute_JLabel("Codice: "));
			cod_JPanel.add(createValue_JLabel(getSoggetto().getCodice()));
		}
		return cod_JPanel;
	}

	private JPanel getEmail_JPanel() {
		if(email_JPanel==null){
			email_JPanel=new JPanel();
			email_JPanel.add(createAttribute_JLabel("Email: "));
			email_JPanel.add(createValue_JLabel(getSoggetto().getEmail()));
		}
		return email_JPanel;
	}

	private JPanel getIndirizzo_JPanel() {
		if(indirizzo_JPanel==null){
			indirizzo_JPanel=new JPanel(new GridBagLayout());
			
			GridBagConstraints contraints=new GridBagConstraints();
			

			contraints.gridx=0;
			contraints.gridy=0;
			contraints.gridwidth=2;
			indirizzo_JPanel.add(getVia_JPanel(),contraints);
			
			
			contraints.gridx=0;
			contraints.gridy=1;
			contraints.gridwidth=1;
			indirizzo_JPanel.add(getLocalita_JPanel(),contraints);
			
			contraints.gridx=1;
			contraints.gridy=1;
			indirizzo_JPanel.add(getProvincia_JPanel(),contraints);
			
			indirizzo_JPanel.setBorder(BorderFactory.createTitledBorder("indirizzo"));
		}
		
		return indirizzo_JPanel;
	}
	
	
	
	

	private JPanel getProvincia_JPanel() {
		if(provincia_JPanel==null){
			provincia_JPanel=new JPanel();
			provincia_JPanel.add(createAttribute_JLabel("provincia: "));
			provincia_JPanel.add(createValue_JLabel(getSoggetto().getProvincia()));
		}
		return provincia_JPanel;
	}

	private JPanel getLocalita_JPanel() {
		if(localita_JPanel==null){
			localita_JPanel=new JPanel();
			localita_JPanel.add(createAttribute_JLabel("localita: "));
			localita_JPanel.add(createValue_JLabel(getSoggetto().getLocalita()));
		}
		return localita_JPanel;
	}

	private JPanel getVia_JPanel() {
		if(via_JPanel==null){
			via_JPanel=new JPanel();
			via_JPanel.add(createAttribute_JLabel("via e numero: "));
			via_JPanel.add(createValue_JLabel(getSoggetto().getIndirizzo()));
		}
		return via_JPanel;
	}

	private JPanel getTel_JPanel() {
		if(tel_JPanel==null){
			tel_JPanel=new JPanel();
			tel_JPanel.add(createAttribute_JLabel("telefono: "));
			tel_JPanel.add(createValue_JLabel(getSoggetto().getTelefono()));
		}
		return tel_JPanel;
	}

	private Component getPIva_JPanel() {
		if(pIva_JPanel==null){
			pIva_JPanel=new JPanel();
			pIva_JPanel.add(createAttribute_JLabel("Partita iva: "));
			pIva_JPanel.add(createValue_JLabel(getSoggetto().getPIva()));
		}
		return pIva_JPanel;
	}

	private JPanel getCodFisc_JPanel() {
		if(codFisc_JPanel==null){
			codFisc_JPanel=new JPanel();
			codFisc_JPanel.add(createAttribute_JLabel("Codice fiscale: "));
			codFisc_JPanel.add(createValue_JLabel(getSoggetto().getCodiceFiscale()));
		}
		return codFisc_JPanel;
	}
	
	

	private JLabel createValue_JLabel(String value) {
		JLabel label=new JLabel();
		
		if(value==null)
			label.setText("non disponibile");
		else
			label.setText(value);
		
		return label;
	}

	private JLabel createAttribute_JLabel(String attribute) {
		JLabel label=new JLabel();
		label.setText(attribute);
		return label;
	}

	private JPanel getTitle_JPanel(){
		if(title_JPanel==null){
			title_JPanel=new JPanel();
//			title_JPanel.setBackground(new Color(120, 120, 160));
			title_JPanel.add(getTitle_JLabel());
		}
		return title_JPanel;
	}

	private JLabel getTitle_JLabel() {
		if(title_JLabel==null){
			title_JLabel=new JLabel(getSoggetto().getRagioneSociale());
		}
		return title_JLabel;
	}

	public Soggetto getSoggetto() {
		if(soggetto==null){
			soggetto=new Soggetto();
		}
		return soggetto;
	}

	public void setSoggetto(Soggetto soggetto) {
		this.soggetto = soggetto;
	}

}
