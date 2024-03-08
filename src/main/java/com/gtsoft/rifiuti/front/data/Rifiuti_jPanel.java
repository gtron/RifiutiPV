/*
 * Created on 17-mag-2006
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.front.data;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.gtsoft.rifiuti.front.JMainTable;
import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.rifiuti.superdata.IListable;
/**
 * @author Gtron - 
 * 
 */
public class Rifiuti_jPanel extends JPanel {

    protected IListable elemento;
    protected RifiutiFrame frame = null;

    protected JMainTable jMainTable = null;
    
	private JSplitPane main_jSplitPane = null;
	private JSplitPane top_jSplitPane = null;
	/**
	 * This method initializes 
	 * 
	 */
	public Rifiuti_jPanel() {
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
	    jMainTableInitialize();
        this.add(getMain_jSplitPane(), null);
			
	}
	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */    
	private JSplitPane getMain_jSplitPane() {
		if (main_jSplitPane == null) {
			main_jSplitPane = new JSplitPane();
			main_jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			main_jSplitPane.setTopComponent(getTop_jSplitPane());
		}
		return main_jSplitPane;
	}
	/**
	 * This method initializes jSplitPane1	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */    
	private JSplitPane getTop_jSplitPane() {
		if (top_jSplitPane == null) {
			top_jSplitPane = new JSplitPane();
			//top_jSplitPane.setLeftComponent();
		}
		return top_jSplitPane;
	}
 
	
	protected JMainTable jMainTableInitialize(){
        jMainTable = new JMainTable(); 
        jMainTable.getJTable().getColumnModel().getColumn(0).setMaxWidth(65);
        jMainTable.getJTable().getColumnModel().getColumn(1).setMaxWidth(45);
        jMainTable.getJTable().getColumnModel().getColumn(3).setMaxWidth(110);
        jMainTable.getJTable().getColumnModel().getColumn(3).setMinWidth(110);
        jMainTable.getJTable().getColumnModel().getColumn(4).setMaxWidth(110);
        jMainTable.getJTable().getColumnModel().getColumn(4).setMinWidth(110);
        jMainTable.getJTable().setDefaultRenderer(Object.class,new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column) {
                JLabel l=(JLabel)super.getTableCellRendererComponent(table ,value ,isSelected,hasFocus,row,column);
                	if( column>=3)
                	    l.setHorizontalAlignment(JLabel.RIGHT);
                	else if ( column == 2)
                	    l.setHorizontalAlignment(JLabel.CENTER);
                	else
                	    l.setHorizontalAlignment(JLabel.LEFT);
                return l;	
            }
            																					
        });
          return jMainTable;   
    }

}
