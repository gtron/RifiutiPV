/*
 * Creato il 30-ago-2005
 *
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti_new
 * 
 */
package com.gtsoft.rifiuti.front.data.detailedPanel;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.gtsoft.rifiuti.data.Fornitore;
import com.gtsoft.rifiuti.data.Vettore;
import com.gtsoft.rifiuti.front.JDatiPanel;
import com.gtsoft.rifiuti.front.JLabelAttribute;
import com.gtsoft.rifiuti.front.JLabelValore;
import com.gtsoft.rifiuti.front.JNomePanel;
import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.rifiuti.front.data.SwgCarico;


public class Carico_detailedPanel extends JPanel {
    
    private JNomePanel jNomePanel;
    private JPanel jDatiPanel;
    private SwgCarico swg;
    
    public Carico_detailedPanel(SwgCarico swg) {
        super(new BorderLayout());
        this.swg=swg;
        initialize(swg);
    }
       
    private void initialize(SwgCarico swg) {
        //this.setBackground(Color.WHITE);
        setJNomePanel(swg.getTitolo(),swg.getFrame());
        try {
            this.add(getJNomePanel(),BorderLayout.NORTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JScrollPane scrollPane = new JScrollPane(getJDatiPanel());
        this.add(scrollPane,BorderLayout.CENTER);
    }

    public void setJNomePanel(String title, RifiutiFrame frame) {
        jNomePanel=new JNomePanel(title);
    }
    
    public JNomePanel getJNomePanel() throws Exception {
        if(jNomePanel==null)
            throw new Exception();
        return jNomePanel;
    }
    
    public JPanel getJDatiPanel() {
        if(jDatiPanel==null) {
            jDatiPanel=new JPanel();
            BoxLayout b=new BoxLayout(jDatiPanel,BoxLayout.Y_AXIS);
            jDatiPanel.setLayout(b); 
            // jDatiPanel.setBackground(Color.WHITE);
            try {
                jDatiPanel.add(getHeadJPanel());
          
                jDatiPanel.add(swg.getCarico().getMerce().getSwg(swg.getFrame()).getJDatiPanel());
          
                String[]  names= {"kg" , "destinazione"};
                String[]  vals = {
                        String.valueOf(swg.getCarico().getNetto()) , 
                        "" + swg.getCarico().getDestinazione().get() } ;
                jDatiPanel.add(new JDatiPanel( names ,vals,"Altri dati"));
                
                Fornitore f = swg.getCarico().getFornitore() ;
                if ( f != null ) 
                    jDatiPanel.add(f.getSwg(swg.getFrame()).getJDatiPanel());
          
                Vettore v = swg.getCarico().getVettore() ;
                if ( v != null ) 
                    jDatiPanel.add(v.getSwg(swg.getFrame()).getJDatiPanel());

          
                jDatiPanel.add(getPrintPanel());
          
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jDatiPanel;
    }
    
    private JPanel getPrintPanel() {
        JPanel p=new JPanel();
        p.add(new JLabel("Formulario : " + swg.getCarico().getDocumento() ));
        p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0,5,0,5),BorderFactory.createTitledBorder("informazioni di stampa")));
        return p;
    }
    //{{ gestione head_panel
    private JPanel headJPanel;
    public JPanel getHeadJPanel() {
        if(headJPanel==null) {
            headJPanel=new JPanel(new BorderLayout());
            JPanel gJPanel=new JPanel(new GridBagLayout());       
            GridBagConstraints gbr=new GridBagConstraints();
            gbr.gridx=0;
            gbr.gridy=0;
            gbr.anchor=GridBagConstraints.WEST;
            gJPanel.add(getCaricoJLabel(),gbr);
            gbr.gridx=1;
            gJPanel.add(getDataJLabel(),gbr);
            gbr.gridy=1;
            gbr.gridx=0;
            gJPanel.add(getNumeroJLabel(),gbr);
            gbr.gridx=1;
            gJPanel.add(getNumeroValueJLabel(),gbr);
            //headJPanel.setBackground(Color.WHITE);
            headJPanel.add(gJPanel,BorderLayout.WEST);
            JPanel emptyPanel= new JPanel();
            headJPanel.add(emptyPanel,BorderLayout.CENTER);
            headJPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0,5,0,5),BorderFactory.createTitledBorder("Carico")));
        }
        return headJPanel;
    }


    private JLabel getCaricoJLabel() {
        JLabel carico_JLabel= new JLabelAttribute("Carico del: ");
        carico_JLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        return carico_JLabel;
    }
    
    private JLabel getDataJLabel() {
        JLabel data_JLabel= new JLabelValore(swg.getCarico().getData().dmyString());
        data_JLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        return data_JLabel;
    }
    
    private JLabel getNumeroJLabel() {
        JLabel numero_JLabel= new JLabelAttribute("Numero: ");
        numero_JLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        return numero_JLabel;
    }
    
    private JLabel getNumeroValueJLabel() {
        JLabel numeroValue_JLabel= new JLabelValore( "" + swg.getCarico().getNumeroProgressivo() );
        numeroValue_JLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        return numeroValue_JLabel;
    }
    //}}
    
    
    
    
    
}

