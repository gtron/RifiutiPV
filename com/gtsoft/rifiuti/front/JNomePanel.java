/*
 * Creato il 7-lug-2005
 *
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti
 * 
 */
package com.gtsoft.rifiuti.front;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author pitcia
 * 
 * XXX Per modificare il modello associato al commento di questo tipo generato,
 * aprire Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class JNomePanel extends JPanel {

    private String nome;

    private JButton button;

    public JNomePanel(String nome) {
        this.nome=nome;
    	initialize();
    }

    private void initialize() {
        JLabel jNome = new JLabel(nome);
        jNome.setFont(new java.awt.Font("Microsoft Sans Serif",
                java.awt.Font.BOLD, 16));
        BorderLayout f = new BorderLayout();
        this.setLayout(f);
        this.add(jNome, BorderLayout.WEST);
//        this.setBackground(new Color(120, 120, 160));
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));       
        this.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
    }    

}