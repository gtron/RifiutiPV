/*
 * Creato il 1-lug-2005
 *
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti
 * 
 */
package com.gtsoft.rifiuti.front;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * @author pitcia
 * 
 * XXX Per modificare il modello associato al commento di questo tipo generato,
 * aprire Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class RifiutiJLabel extends JLabel {

    public RifiutiJLabel(String title) {
        super(title);
        this.setForeground(new Color(255, 0, 0));
        this.setFont(new java.awt.Font(title, 10, 12));
    }

    public RifiutiJLabel() {
        this("");
    }
}