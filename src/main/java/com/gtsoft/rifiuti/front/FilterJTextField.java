/*
 * Creato il 18-lug-2005
 * 
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti
 *  
 */
package com.gtsoft.rifiuti.front;

import java.awt.Color;

import javax.swing.JTextField;

public class FilterJTextField extends JTextField {

//    private static Color color = Color.WHITE;

    public FilterJTextField() {
//        this.setBackground(color);
    }

    public FilterJTextField(int i) {
        super(i); 
//        this.setBackground(color);
    }

    public static void setColor(Color color) {
//        FilterJTextField.color = color;
    }

}