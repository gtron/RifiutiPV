package com.gtsoft.rifiuti.front.data.detailedPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gtsoft.rifiuti.data.Carico;

public class CaricoDetailed_jDialog extends JDialog {

	private JPanel jContentPane = null;
	private JLabel nProg_jLabel = null;
	private JLabel documento_jLabel = null;
	private JLabel data_jLabel = null;
	private JLabel dataPesatura_jLabel = null;
	private JLabel merce_jLabel = null;
	private JLabel isScarico_jLabel = null;
	private JLabel dataScarico_jLabel = null;
	private JLabel destinazione_jLabel = null;
	private JLabel pesoPartenza_jLabel = null;
	private JLabel pesoNetto_jLabel = null;
	private JLabel pFattura_jLabel = null;
//	private JLabel codForn_jLabel = null;
	private JLabel codForn_jLabel1 = null;
	private JLabel forn_jLabel = null;
	private JLabel codCliente_jLabel = null;
	private JLabel cliente_jLabel = null;
//	private JLabel codTras_jLabel = null;
	private JLabel codTras_jLabel1 = null;
	private JLabel trasport_jLabel = null;
//	private JLabel cod_trasjLabel;
	
	private Carico carico;
	/**
	 * This is the default constructor
	 */
	public CaricoDetailed_jDialog(Carico carico, JFrame frame) {
		super(frame);
		setCarico(carico);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setModal(true);
		this.setContentPane(getJContentPane());
		this.requestFocus();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
			gridBagConstraints71.gridx = 1;
			gridBagConstraints71.gridwidth = 3;
			gridBagConstraints71.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints71.gridy = 6;
			trasport_jLabel=createLabel("traportatore",getCarico().getVettore().getRagioneSociale());
			GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
			gridBagConstraints61.gridx = 0;
			gridBagConstraints61.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints61.gridy = 6;
			codTras_jLabel1=createLabel("cod.",getCarico().getVettore().getCodice());
			
			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.gridx = 1;
			gridBagConstraints41.gridwidth = 4;
			gridBagConstraints41.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints41.gridy = 5;
			cliente_jLabel = createLabel("cliente",getCarico().getCliente().getRagioneSociale());
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.gridx = 0;
			gridBagConstraints31.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints31.gridy = 5;
			codCliente_jLabel =createLabel("cod.",getCarico().getCodiceCliente());
			
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridx = 1;
			gridBagConstraints21.gridwidth = 3;
			gridBagConstraints21.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints21.gridy = 4;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 0;
			gridBagConstraints12.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints12.gridy = 4;
			codForn_jLabel1 = createLabel("cod.",getCarico().getCodiceFornitore());
			
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.gridy = 4;
			forn_jLabel = createLabel("fornitore",getCarico().getFornitore().getRagioneSociale());
			
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 4;
			gridBagConstraints10.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints10.gridy = 3;
			pFattura_jLabel = createLabel("p.fattura",String.valueOf(getCarico().getPesoFattura().getPeso()));
			
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = 1;
			gridBagConstraints9.gridy = 3;
			pesoNetto_jLabel = createLabel("p.netto",String.valueOf(getCarico().getNetto()));
			
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints8.gridy = 3;
			pesoPartenza_jLabel = createLabel("p.partenza",String.valueOf(getCarico().getPesoPartenza().getPeso()));
			
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 4;
			gridBagConstraints7.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints7.gridy = 2;
			destinazione_jLabel = createLabel("destin.",String.valueOf(getCarico().getDestinazione().get()));
			
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 1;
			gridBagConstraints6.gridy = 2;
			dataScarico_jLabel = createLabel("data di scarico",getCarico().getDataScarico().dmyString());
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints5.gridy = 2;
			isScarico_jLabel = createLabel("scaric.?","SI");
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridwidth = 3;
			gridBagConstraints4.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints4.gridy = 1;
			try {
				merce_jLabel = createLabel("merce",getCarico().getMerce().getNome());
			} catch (Exception e) {
				e.printStackTrace();
			}
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 4;
			gridBagConstraints3.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints3.gridy = 1;
			dataPesatura_jLabel = createLabel("data pesatura",getCarico().getDataPesatura().dmyString());
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 4;
			gridBagConstraints2.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints2.gridy = 0;
			data_jLabel = createLabel("data",getCarico().getData().dmyString());
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridwidth = 2;
			gridBagConstraints1.gridy = 0;
			documento_jLabel = createLabel("documento",getCarico().getDocumento());
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints.gridy = 0;
			nProg_jLabel = createLabel("n.prog.",String.valueOf(getCarico().getNumeroProgressivo()));
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(nProg_jLabel, gridBagConstraints);
			jContentPane.add(documento_jLabel, gridBagConstraints1);
			jContentPane.add(data_jLabel, gridBagConstraints2);
			jContentPane.add(dataPesatura_jLabel, gridBagConstraints3);
			jContentPane.add(merce_jLabel, gridBagConstraints4);
			jContentPane.add(isScarico_jLabel, gridBagConstraints5);
			jContentPane.add(dataScarico_jLabel, gridBagConstraints6);
			jContentPane.add(destinazione_jLabel, gridBagConstraints7);
			jContentPane.add(pesoPartenza_jLabel, gridBagConstraints8);
			jContentPane.add(pesoNetto_jLabel, gridBagConstraints9);
			jContentPane.add(pFattura_jLabel, gridBagConstraints10);
			jContentPane.add(codForn_jLabel1, gridBagConstraints12);
			jContentPane.add(forn_jLabel, gridBagConstraints21);
			jContentPane.add(codCliente_jLabel, gridBagConstraints31);
			jContentPane.add(cliente_jLabel, gridBagConstraints41);
			jContentPane.add(codTras_jLabel1, gridBagConstraints61);
			jContentPane.add(trasport_jLabel, gridBagConstraints71);
			//jContentPane.add(forn_jLabel, gridBagConstraints11);
		}
		return jContentPane;
	}
	
	
	private JLabel createLabel(String title,String value){
		JLabel label = new JLabel();
		int differenza = Double.valueOf((title.length() - value.length())*1.5).intValue();
		if(differenza>0){
			String aggiungi_spazi="";
			for(int i=0;i<differenza/2;i++)
				aggiungi_spazi=aggiungi_spazi+" ";
		value=aggiungi_spazi + value + aggiungi_spazi;
		}
		label.setText(value);
		label.setBorder(javax.swing.BorderFactory.createTitledBorder(null, title, javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.CENTER, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), new java.awt.Color(51,51,51)));
		return label;
	}
	
	public static void main(String[] args) {
		create(new Carico(),new JFrame());		
	}
	
	public static void create(Carico c,JFrame f){
		JDialog d= new CaricoDetailed_jDialog(c,f);
		d.pack();
		d.setLocationRelativeTo(null);
		d.setVisible(true);
	}

	public Carico getCarico() {
		return carico;
	}

	public void setCarico(Carico carico) {
		this.carico = carico;
	}

}