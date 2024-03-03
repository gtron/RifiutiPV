/*
 * Creato il 7-lug-2005
 * 
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti
 *  
 */
package com.gtsoft.rifiuti.front;

import javax.swing.JLabel;

public class JLabelAttribute extends JLabel {
 
//    private final static java.awt.Color COLOR_BACKGROUND = new java.awt.Color(
//            240, 255, 255);
//
//    private final static java.awt.Color COLOR_FOREGROUND = new java.awt.Color(
//            0, 0, 0);

    private final static java.awt.Font FONT = new java.awt.Font(
            "Arial", java.awt.Font.PLAIN, 12);

    public JLabelAttribute() {
//        setBackground(COLOR_BACKGROUND);
//        setBackground(COLOR_FOREGROUND);
        setFont(FONT);

    }

    /**
     * @param string
     */
    public JLabelAttribute(String string) {
        super(string);
//        setBackground(COLOR_BACKGROUND);
//        setBackground(COLOR_FOREGROUND);
        setFont(FONT);
    }
}