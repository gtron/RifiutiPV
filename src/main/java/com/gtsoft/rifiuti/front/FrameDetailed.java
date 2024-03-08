/*
 * Creato il 16-lug-2005
 * 
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti
 *  
 */
package com.gtsoft.rifiuti.front;

import javax.swing.JDialog;

public class FrameDetailed extends JDialog { 

    public FrameDetailed(JDetailedPanel panel) {
        super();
        this.setModal(true);
        this.add(panel);
        this.pack();
        this.setVisible(true);
    }

}