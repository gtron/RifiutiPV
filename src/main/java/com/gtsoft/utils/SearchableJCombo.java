package com.gtsoft.utils;
/*
 // * Created on 4-mar-2006
  // *
   // *	This is not free software - All rights reserved
    // */



import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

/**
 * @author Gtron - 
 * 
 */
public class SearchableJCombo extends JComboBox {
    
    ComboBoxModel model;
    JTextComponent editor;

    AutoCompleter ac;
    Vector originalList;
    
    SearchableJCombo() {
        super();
        model = super.getModel();
        editor = (JTextComponent) super.getEditor().getEditorComponent();
        ac = new AutoCompleter(this);
        
        int num = getItemCount();
        originalList = new Vector(num) ;
        for ( int i = 0 ; i < num ; i++ ) {
            originalList.add(getItemAt(i)) ;
        }
    }
    
    private void resetList() {
        removeAllItems() ;
        for ( Iterator i = originalList.iterator() ; i.hasNext();  ) {
            addItem(i.next());
        }
    }
    
    
    public static void main(String[] args) {
        
        JFrame f = new JFrame("test"); 
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(100, 100);
        
        
        SearchableJCombo t = new SearchableJCombo() ;
        
        t.addItem("aabc");
        t.addItem("abcc");
        t.setEditable(true);
        
        f.add(t);
        
        f.setVisible(true);
    }
    
}


class AutoCompleter extends KeyAdapter {
    JComboBox comboBox;
    JTextField editor;
    
    public AutoCompleter(JComboBox comboBox) {
        this.comboBox = comboBox;
        editor = (JTextField) comboBox.getEditor().getEditorComponent();
        editor.addKeyListener(this);
    }
    
    public void keyReleased(KeyEvent e) {
        char ch = e.getKeyChar();
        if (ch == KeyEvent.VK_ENTER) {
            String item = editor.getText();
            editor.moveCaretPosition(item.length());
        }
        if (ch == KeyEvent.CHAR_UNDEFINED || Character.isISOControl(ch)) return;
        
        String str = editor.getText();
        if (str.length() == 0) return;
        
        int pos = editor.getCaretPosition();
        str = str.substring(0,pos);
        boolean isFound = false;
        comboBox.setPopupVisible(true);
        
        for (int k = 0; k < comboBox.getItemCount(); k++) {
            String item = comboBox.getItemAt(k).toString();
            if (item.toLowerCase().matches(str.toLowerCase())) {
                editor.setText(item);
                editor.setCaretPosition(item.length());
                editor.moveCaretPosition(pos);
                comboBox.setSelectedIndex(k);
                isFound = true;
                break;
            }
        }
        
        if (! isFound ){
            pos = str.length()-1;
            str = str.substring(0, pos);
            editor.setText(str);}
    }} 



