/*
 * Creato il 13-set-2005
 *
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti_new
 * 
 */
package com.gtsoft.rifiuti.front;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import com.gtsoft.rifiuti.data.Cer;


public class ComboListCellRenderer extends DefaultListCellRenderer {

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel c = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,cellHasFocus);
        if (value instanceof Cer) {
            Cer cer = (Cer) value;
            c.setToolTipText(((Cer)value).getNome());
        }
        return c;
    }
    
}