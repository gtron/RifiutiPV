/*
 * Created on 6-nov-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.front.data;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Gtron - 
 * 
 */
public class FieldEditor {
    
    private String name = null ;
    
    private JLabel label = null ;
    private JTextField txtfield = null ;
    private JComboBox combofield = null ;
    
    private JPanel panel = null ;
    private boolean visible = true ;
    
    public JComboBox getCombofield() {
        return combofield;
    }
    public JLabel getLabel() {
        return label;
    }
    public JTextField getTxtfield() {
        return txtfield;
    }
    public FieldEditor(String name, String label, String value , int size ) {
        this.name = name ;
        this.label = new JLabel(label + " : ") ;
        txtfield = new JTextField(size);
        if ( value == null ) value = "" ;
        txtfield.setText(value);
        txtfield.setMaximumSize(new Dimension(280,24));
        txtfield.setMargin(new Insets(1,4,1,4));
    }
    
    private String [] values = null ;
    public FieldEditor(String name, String label, String value , String[] list , String[] values ) {
        this.values = values ;
        this.name = name ;
        this.label = new JLabel(label + " : ") ;
        combofield = new JComboBox(list);
        combofield.setMaximumSize(new Dimension(280,28));
        combofield.setSelectedItem(value);
    }
    
    public JPanel getPanel() {
        return getPanel(false) ;
    }
    public JPanel getPanel( boolean refresh ) {
        if ( refresh || panel == null ) {
            panel = new JPanel() ;
            panel.setLayout( new BoxLayout(panel, BoxLayout.LINE_AXIS));
            panel.add(label);
            label.setAlignmentX(JLabel.RIGHT_ALIGNMENT) ;
            
            panel.add(Box.createHorizontalGlue()) ;
            
            if ( txtfield != null ) {
                panel.add(txtfield);
            }
            else 
                panel.add(combofield);
        }
        return panel ;
    }
    public String getValue() {
        if ( txtfield != null ) 
            return txtfield.getText();
        else {
            return values[ combofield.getSelectedIndex() ] ;
        }
    }
    public String getName() {
        return name ;
    }
    public boolean isVisible() {
        return visible ;
    }
    public void setVisible( boolean b ) {
        visible = b ; 
    }
    public void reset() {
        if ( txtfield != null ) 
            txtfield.setText("");
        else {
            combofield.setSelectedIndex(0);
        }
    }
}
