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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.gtsoft.rifiuti.front.data.AbstractSwg;
import com.gtsoft.utils.filter.Abstract_Da_A_Filter;
import com.gtsoft.utils.filter.superdata.IFilter;

public class Da_A_JFiltroPanel extends JFiltroPanel {
    
    private JLabelAttribute jLabel;

    private JLabelAttribute jLabel1;
    
    private JTextField da_jTextField=null;

    private JTextField a_jTextField=null;
    
    private Abstract_Da_A_Filter da_a_Filter;
    
    
    public Da_A_JFiltroPanel(String titolo,Abstract_Da_A_Filter filter, AbstractSwg swg){
        this.fm=swg.getFilterManager();
        this.swg=swg;  
        this.da_a_Filter=filter;
        initialize();
    }
    
    protected IFilter getfilter(){
    	return da_a_Filter;
    }


    private void initialize() {
        this.setLayout(new BorderLayout());
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));       
        this.filtroActionListener=new DateFiltroActionListener();
        this.add(getContentPanel(),BorderLayout.NORTH);
        this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5), BorderFactory.createLineBorder(new java.awt.Color(51,51,51))));
        getJLabel().setText("da"); 
        getJLabel1().setText("a");
    }


    public JPanel getContentPanel() {
        if(contentPanel==null) {
            contentPanel=new JPanel();
            contentPanel.add(getJLabel());
            contentPanel.add(get_da_JTextField());
            contentPanel.add(getJLabel1());
            contentPanel.add(get_a_JTextField());
            contentPanel.add(getApplica_jButton());
            contentPanel.add(getJButtonClosed());
        }
        return contentPanel;    
    }
    
    public Dimension getPreferredSize(){   	
    	int x=getJLabel().getPreferredSize().width+get_da_JTextField().getPreferredSize().width+getJLabel1().getPreferredSize().width+
    		   get_a_JTextField().getPreferredSize().width+getApplica_jButton().getPreferredSize().width+getJButtonClosed().getPreferredSize().width+90;
    	int y=45;
    	return new Dimension(x,y);
   }
    
    private JLabelAttribute getJLabel1() {
        if(jLabel1==null)
            jLabel1=new JLabelAttribute();
        return jLabel1;
    }

    private JLabelAttribute getJLabel() {
        if(jLabel==null)
            jLabel=new JLabelAttribute();
        return jLabel;
    }
    
  
    public JTextField get_da_JTextField() {
        if(da_jTextField==null) {
            da_jTextField=new JTextField();
            da_jTextField.setMinimumSize(new Dimension(80,20));
            da_jTextField.setPreferredSize(new Dimension(80,20));
            da_jTextField.addKeyListener(new KeyAdapter(){

				public void keyTyped(KeyEvent e) {
					getApplica_jButton().setEnabled(true);   
				}
            	
            });

        }
        return da_jTextField;
    }
    
    public JTextField get_a_JTextField() {
        if(a_jTextField==null) {
            a_jTextField=new JTextField();
            a_jTextField.setMinimumSize(new Dimension(80,20));
            a_jTextField.setPreferredSize(new Dimension(80,20));
            a_jTextField.addKeyListener(new KeyAdapter(){

				public void keyTyped(KeyEvent e) {
					getApplica_jButton().setEnabled(true);   
				}
            	
            });
        }
        return a_jTextField;
    }
    
    private boolean filtroApplicato=false;
    private class DateFiltroActionListener extends FiltroActionListener implements ActionListener  {          
       
        public void actionPerformed(ActionEvent e) {
        	if (e.getSource().equals(getApplica_jButton())) {                      
                try {
                    da_a_Filter.setA(get_a_JTextField().getText());
                    da_a_Filter.setDa(get_da_JTextField().getText());
                    fm.removeFilter(getfilter());
                    fm.addFilter(getfilter());
                    filtroApplicato=true;
                    getThis().getApplica_jButton().setEnabled(false);
                    swg.refreshElencoJTable();
                } 
            	
                catch(NumberFormatException ex){
            		JOptionPane.showMessageDialog(null,"dati inseriti non conformi con le specifiche del filtro");
            	}
                
                catch (Exception e1) {
                    // XXX Blocco catch generato automaticamente
                    e1.printStackTrace();
                }
            }
            else
                if (e.getSource().equals(getJButtonClosed())) {
                    remove_jfilterPanel();
                     }  
        }
                
            
   }
    
}

