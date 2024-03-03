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

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.gtsoft.rifiuti.front.data.AbstractSwg;
import com.gtsoft.utils.filter.AbstractDataFilter;
import com.gtsoft.utils.filter.superdata.IFilter;
import com.toedter.calendar.JDateChooser;

public class Date_JFiltroPanel extends JFiltroPanel {
    
    private JLabelAttribute jLabel;

    private JLabelAttribute jLabel1;
    
    private JDateChooser da_jDateChooser=null;

    private JDateChooser a_jDateChooser=null;
    
    private AbstractDataFilter dateFilter;
    
    
    public Date_JFiltroPanel(String titolo,AbstractDataFilter filter, AbstractSwg swg){
        this.fm=swg.getFilterManager();
        this.swg=swg;  
        this.dateFilter=filter;
        initialize();
    }
    
    protected IFilter getfilter(){
    	return dateFilter;
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
            contentPanel.add(get_da_JDateChooser());
            contentPanel.add(getJLabel1());
            contentPanel.add(get_a_JDateChooser());
            contentPanel.add(getApplica_jButton());
            contentPanel.add(getJButtonClosed());
        }
        return contentPanel;    
    }
    
    public Dimension getPreferredSize(){   	
    	int x=getJLabel().getPreferredSize().width+get_da_JDateChooser().getPreferredSize().width+getJLabel1().getPreferredSize().width+
    		   get_a_JDateChooser().getPreferredSize().width+getApplica_jButton().getPreferredSize().width+getJButtonClosed().getPreferredSize().width+90;
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
    
  
    public JDateChooser get_da_JDateChooser() {
        if(da_jDateChooser==null) {
            da_jDateChooser=new JDateChooser("dd/MM/yyyy",false);
            da_jDateChooser.getSpinner().addChangeListener(dateChangeListener);
        }
        return da_jDateChooser;
    }
    
    public JDateChooser get_a_JDateChooser() {
        if(a_jDateChooser==null) {
            a_jDateChooser=new JDateChooser("dd/MM/yyyy",false);
            a_jDateChooser.getSpinner().addChangeListener(dateChangeListener);
        }
        return a_jDateChooser;
    }
    private boolean filtroApplicato=false;
    private class DateFiltroActionListener extends FiltroActionListener implements ActionListener  {          
       
        public void actionPerformed(ActionEvent e) {
        	if (e.getSource().equals(getApplica_jButton())) {                      
                try {
                    dateFilter.setA(get_a_JDateChooser().getDate());
                    dateFilter.setDa(get_da_JDateChooser().getDate());
                    fm.removeFilter(getfilter());
                    fm.addFilter(getfilter());
                    filtroApplicato=true;
                    getThis().getApplica_jButton().setEnabled(false);
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
        }
                
            
   }
    
    
    private DateChangeListener dateChangeListener=new DateChangeListener();
    private class DateChangeListener implements ChangeListener {

        public void stateChanged(ChangeEvent arg0) {
            getApplica_jButton().setEnabled(true);   
            if(arg0.getSource().equals(get_da_JDateChooser().getSpinner()))
	            if(get_a_JDateChooser().getDate().before(get_da_JDateChooser().getDate()))	{	            	
            		get_a_JDateChooser().setDate(get_da_JDateChooser().getDate());
	            }
            		else;
            else
            	if(arg0.getSource().equals(get_a_JDateChooser().getSpinner()))
            		if(get_a_JDateChooser().getDate().before(get_da_JDateChooser().getDate()))
            			get_da_JDateChooser().setDate(get_a_JDateChooser().getDate());
        }
    }
    
}
