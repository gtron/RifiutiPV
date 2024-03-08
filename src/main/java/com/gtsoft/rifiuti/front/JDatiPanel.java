/*
 * Creato il 7-lug-2005
 * 
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti
 *  
 */
package com.gtsoft.rifiuti.front;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import com.gtsoft.rifiuti.front.data.AbstractSwg;

public class JDatiPanel extends JPanel {

    JLabelAttribute[] jLabelAttributi; 

    JLabelValore[] jLabelValori;

    AbstractSwg swg = null;

 //   DatiMouseListener datiMouseListener = new DatiMouseListener();

    //QUANDO CAMBI UNA COSA LA DEVI CAMBIARE IN DUE COSTRUTTORI..... DA SISTEMARE
    public JDatiPanel(AbstractSwg swg) {
        this.swg = swg;
        int size = swg.getSortAttributi().length;
        jLabelValori = new JLabelValore[size];
        jLabelAttributi = new JLabelAttribute[size];
        BoxLayout b = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(b);
        this.addMouseListener(new MouseHandle());
        // this.setBackground(Color.WHITE);
        for (int i = 0; i < size; i++) {
            String attributo = (String) swg.getSortAttributi()[i];    
            String v_attributo = attributo.toUpperCase().charAt(0) + attributo.substring(1,attributo.length());
            jLabelAttributi[i] = new JLabelAttribute(v_attributo + ": ");
            jLabelValori[i] = new JLabelValore(swg,attributo);
       //     jLabelValori[i].addMouseListener(datiMouseListener);
            jLabelValori[i].setIndex(swg.attributo_getIndex(attributo));
            FlowLayout f = new FlowLayout();
            f.setAlignment(FlowLayout.LEFT);
            JPanel pfl = new JPanel(f);
           // pfl.setBackground(Color.WHITE);
            pfl.add(jLabelAttributi[i]);
            pfl.add(jLabelValori[i]);
            this.add(pfl);
        }
        this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5),BorderFactory.createTitledBorder(swg.getTitolo())));
       // this.addMouseListener(datiMouseListener);
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public JDatiPanel(String[] attributi, String[] values, String titolo) {
        int size = attributi.length;
        jLabelValori = new JLabelValore[size];
        jLabelAttributi = new JLabelAttribute[size];
        BoxLayout b = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(b);
        // this.setBackground(Color.WHITE);
        for (int i = 0; i < size; i++) {
            String attributo = attributi[i];    
            String v_attributo = attributo.toUpperCase().charAt(0) + attributo.substring(1,attributo.length());
            jLabelAttributi[i] = new JLabelAttribute(v_attributo + ": ");
            jLabelValori[i] = new JLabelValore(values[i]);
      //      jLabelValori[i].addMouseListener(datiMouseListener);
            FlowLayout f = new FlowLayout();
            f.setAlignment(FlowLayout.LEFT);
            JPanel pfl = new JPanel(f);
            //pfl.setBackground(Color.WHITE);
            pfl.add(jLabelAttributi[i]);
            pfl.add(jLabelValori[i]);
            this.add(pfl);
        }
        this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5),BorderFactory.createTitledBorder(titolo)));
        //this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0),BorderFactory.createTitledBorder(titolo)));
    //    this.addMouseListener(datiMouseListener);
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    
    private JPanel getThis() {
        return this;
    }
    
    
    public JLabelValore[] getJLabelValori() {
        return jLabelValori;
    }
    
    private JPopupMenu jPopupMenu;

    private JMenuItem apri_il_dettaglio_JMenuItem;

    private JMenuItem filtra_JMenuItem;

    private JPopupMenu getapriPopupMenu() {
        if(jPopupMenu==null) {
            jPopupMenu = new JPopupMenu();
            jPopupMenu.add(get_apri_il_dettaglio_JMenuItem());
            // jPopupMenu.add(get_filtra_JMenuItem());
        }
        return jPopupMenu;
    }
    
    private JMenuItem get_apri_il_dettaglio_JMenuItem() {
        if(apri_il_dettaglio_JMenuItem==null) {
            apri_il_dettaglio_JMenuItem = new JMenuItem("apri il dettaglio");
            apri_il_dettaglio_JMenuItem.addActionListener(new DatiActionListener());
        }
        return apri_il_dettaglio_JMenuItem;
    }
    
    private JMenuItem get_filtra_JMenuItem() {
        if(filtra_JMenuItem==null) {
            filtra_JMenuItem = new JMenuItem("filtra");
            filtra_JMenuItem.addActionListener(new DatiActionListener());
        }
        return filtra_JMenuItem;
    }
    
    

  
   private class MouseHandle extends MouseAdapter { 
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(getThis())) { 
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        getapriPopupMenu().show((Component) e.getSource(), e.getX(), e.getY());                        
                    }
            }
        }
   }

    private class DatiActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if(e.getSource().equals(get_apri_il_dettaglio_JMenuItem()))
                swg.apri_il_dettaglio();
            else 
                if(e.getSource().equals(get_filtra_JMenuItem()))
                    ;//swg.filtra_per_te_stesso();
        }

    }

    
 
}