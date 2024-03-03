/*
 * Creato il 7-lug-2005
 *
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti
 * 
 */
package com.gtsoft.rifiuti.front;

import java.awt.Cursor;

import javax.swing.JLabel;

import com.gtsoft.rifiuti.front.data.AbstractSwg;

public class JLabelValore extends JLabel {
    private final static java.awt.Color COLOR_BACKGROUND = new java.awt.Color(
            250, 255, 255); 

//    private final static java.awt.Color COLOR_FOREGROUND = new java.awt.Color(
//            50, 100, 142);

    private final static java.awt.Font FONT = new java.awt.Font("Arial",
            java.awt.Font.BOLD, 12);
 
    private int index;

    private boolean clickable;

    public JLabelValore() {
//        setBackground(COLOR_BACKGROUND);
//        //setBackground(COLOR_FOREGROUND);
        setFont(FONT);

    }


    
    public JLabelValore(AbstractSwg swg,String attributo) {
        super((String) swg.getValues().get(attributo));
        //this.clickable = swg.attributo_isClickable(attributo);
        this.clickable=false;
        //setBackground(COLOR_BACKGROUND);
        //setForeground(COLOR_FOREGROUND);
        setFont(FONT);
        setHandCursor();
    }

    public JLabelValore(String valore) {
        super(valore);
        this.clickable = false;
        setBackground(COLOR_BACKGROUND);
        //setForeground(COLOR_FOREGROUND);
        setFont(FONT);
        setHandCursor();
    }
    
    private void setHandCursor() {
        if (clickable)
            this.setCursor(new Cursor(Cursor.HAND_CURSOR));

    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }
}