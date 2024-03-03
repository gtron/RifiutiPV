/*
 * Creato il 2-lug-2005
 * 
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti
 *  
 */
package com.gtsoft.rifiuti.front;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;

public class RifiutiStatusBarAdapter implements MouseMotionListener {

    private Component component;

    private String helpText="status bar";
 
    private JLabel statusBar;

    public RifiutiStatusBarAdapter(Component component, String helpText,
            JLabel statusBar) { 
        this.component = component;
        this.helpText = helpText;
        this.statusBar = statusBar;
        statusBar.setText(helpText);
        component.addMouseMotionListener(this);
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        if ( helpText != null && statusBar != null ) 
            statusBar.setText(helpText);
    }

}