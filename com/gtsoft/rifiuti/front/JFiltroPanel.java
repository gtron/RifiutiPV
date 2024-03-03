/*
 * Creato il 11-ago-2005
 *
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti_test
 * 
 */
package com.gtsoft.rifiuti.front;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.gtsoft.rifiuti.front.data.AbstractSwg;
import com.gtsoft.utils.filter.FilterManager;
import com.gtsoft.utils.filter.superdata.IFilter;

public class JFiltroPanel extends JPanel {


//    private FilterJTextField jTextField=null;

    private JButton applica_jButton=null;

    private JButton jButtonClosed=null;

    protected JPanel contentPanel;
    
    protected JLabelAttribute jLabel;

    private JComboBox jComboBox;
    
    protected FiltroActionListener filtroActionListener;

    protected IFilter filter;
    
    AbstractSwg swg;
    
    Vector list=null;
    
    public JFiltroPanel(String descrizione,IFilter filter,AbstractSwg swg,Vector list) {
        this.fm=swg.getFilterManager();
        this.swg=swg;
        initialize(descrizione,list,filter);
        
    }
    
    public JFiltroPanel() {
        
    }
    
    private void initialize(String descrizione,Vector list,IFilter filter) {
        this.filter =filter;
        this.list=list;
        this.setLayout(new BorderLayout());
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        getJLabel().setText(descrizione);        
        this.filtroActionListener=new FiltroActionListener();
        this.add(getContentPanel(),BorderLayout.NORTH);
        this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5), BorderFactory.createLineBorder(new java.awt.Color(51,51,51))));
    }
    
  
   public Dimension getPreferredSize(){   	
    	int x=getJLabel().getPreferredSize().width+getJComboBox().getPreferredSize().width+getApplica_jButton().getPreferredSize().width+getJButtonClosed().getPreferredSize().width+90;
    	int y=45;
    	return new Dimension(x,y);
   }
   



    public JPanel getContentPanel() {
        if(contentPanel==null) {
        	FlowLayout f=new FlowLayout();
        	f.setAlignment(FlowLayout.CENTER);
            contentPanel=new JPanel(f);
            contentPanel.add(getJLabel());
            contentPanel.add(getJComboBox());
            contentPanel.add(getApplica_jButton());
            contentPanel.add(getJButtonClosed());
         //   contentPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), BorderFactory.createLineBorder(Color.BLACK)));
        }
        return contentPanel;    
    }
    
    
    /*
    private FilterJTextField getJTextField() {
        if(jTextField==null) {
            jTextField =new FilterJTextField(6);
            jTextField.addActionListener(new FiltroActionListener());
        }
        return jTextField;
        
    }
*/
    

    protected IFilter getfilter(){
    	return filter;
    }
    
    private JLabelAttribute getJLabel() {
        if(jLabel==null)
            jLabel=new JLabelAttribute();
        return jLabel;
    }
    
    public JComboBox getJComboBox() {
        if(jComboBox==null) {
            jComboBox=new JComboBox();
            jComboBox.setMinimumSize(new Dimension(6,8));
            if ( list != null ) {
	            Iterator i= list.iterator();
	            while(i.hasNext()) {
	                jComboBox.addItem(i.next());
	            }
            }
            jComboBox.addActionListener(filtroActionListener);
            // jComboBox.setSelectedIndex(0);
           // getfilter().setComparator(getJComboBox().getSelectedItem());
            
        }
        return jComboBox;
        
    }
    
    JFiltroPanel getThis() {
        return this;
    }


    
        public JButton getApplica_jButton() {
            if(applica_jButton==null) {
                applica_jButton = new JButton("applica");
                applica_jButton.setEnabled(false);
                applica_jButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                applica_jButton.addActionListener(filtroActionListener);
            }   
            return applica_jButton;
        }
        
        public JButton getJButtonClosed() {
            if (jButtonClosed==null) {
                jButtonClosed = new JButton("elimina");
                jButtonClosed.setCursor(new Cursor(Cursor.HAND_CURSOR));
                jButtonClosed.addActionListener(filtroActionListener);          
            }
            return jButtonClosed;
        }
     
        protected void remove_jfilterPanel() {
			if(filtroApplicato==true);
			    try {
			        fm.removeFilter(getfilter());
			        swg.refreshElencoJTable();
			    } catch (Exception e1) {
			        // XXX Blocco catch generato automaticamente
			        e1.printStackTrace();
			    }
			getThis().setVisible(false);
			fm.remove_JFilterPanel(getThis());
			getfilter().setEnableItems(true);
			getApplica_jButton().setEnabled(true);
		}

		protected FilterManager fm;    
     
        private boolean filtroApplicato=false;
        protected class FiltroActionListener implements ActionListener  {          
            
            
            
            public void actionPerformed(ActionEvent e) {
            	if (e.getSource().equals(getApplica_jButton())) {                      
                    try {
                        fm.removeFilter(getfilter());
                        fm.addFilter(getfilter());
                        getApplica_jButton().setEnabled(false);
                        filtroApplicato=true;
                        swg.refreshElencoJTable();
                    } catch (Exception e1) {
                        // XXX Blocco catch generato automaticamente
                        e1.printStackTrace();
                    }
                }
                else
                    if (e.getSource().equals(getJButtonClosed())) {
                        remove_jfilterPanel();
	                     }  
                    else
                        if(e.getSource().equals(getJComboBox())) {
                                getfilter().setComparator(getJComboBox().getSelectedItem());
                                getApplica_jButton().setEnabled(true); 
                        }
            }
        }   

}