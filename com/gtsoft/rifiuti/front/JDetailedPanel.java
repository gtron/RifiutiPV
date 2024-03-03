package com.gtsoft.rifiuti.front;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * @author pitcia
 * 
 * XXX Per modificare il modello associato al commento di questo tipo generato,
 * aprire Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class JDetailedPanel extends JPanel {

    JPanel datiJPanel;
    JNomePanel nomeJPanel;

    public JDetailedPanel() {
        super();
    }

    public JDetailedPanel(JPanel datiJPanel, JNomePanel nomeJPanel) {
        super(new BorderLayout());
        this.datiJPanel = datiJPanel;
        this.nomeJPanel = nomeJPanel;
        this.add(nomeJPanel, BorderLayout.NORTH);
        this.add(datiJPanel, BorderLayout.CENTER);
        //this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.setPreferredSize(new Dimension(300,400));
    }

    public JPanel getDatiJPanel() {
        return datiJPanel;
    }

    /**
     * @return Restituisce il valore nomeJPanel.
     */
    public JPanel getNomeJPanel() {
        return nomeJPanel;
    }
}