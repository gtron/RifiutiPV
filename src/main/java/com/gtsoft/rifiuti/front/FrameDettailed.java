/*
 * Creato il 16-lug-2005
 * 
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti
 *  
 */
package com.gtsoft.rifiuti.front;

import javax.swing.JFrame;

public class FrameDettailed extends JFrame { 

    public FrameDettailed(JDetailedPanel panel) {
        super();
        this.add(panel);
        this.pack();
        this.setVisible(true);
    }

}