/*
 * Creato il 26-ago-2005
 *
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti_test
 * 
 */
package com.gtsoft.utils.filter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.gtsoft.rifiuti.front.Content_filtri_JPanel;
import com.gtsoft.rifiuti.front.JFiltroPanel;
import com.gtsoft.utils.filter.superdata.IFilter;
import com.gtsoft.utils.filter.superdata.IFiltrable;


public class FilterManager{
    
    private Vector unfiltered;
    private Vector filtered;
    private Vector filtriApplicati= new Vector();
    private Vector filtriApplicabili;
    private Content_filtri_JPanel content_filtri_JPanel;
    private RadioButton_JPanel radioButton_JPanel;
    private IFiltrable swg;
    
    
    public FilterManager(Vector unfiltered,IFiltrable swg) {
        this.swg=swg;
        setUnfiltered(unfiltered);
    }
    
    public void setUnfiltered(Vector unfiltered) {
        this.unfiltered = unfiltered;
        refresh_filtri(); //riapplica i filtri sui nuovi
    }
    
    private void refresh_filtri(){
    	if(!filtriApplicati.isEmpty()){
    		aggiungi_primo_filtro((IFilter)filtriApplicati.get(0));
    		for(int i=1;i<filtriApplicati.size();i++)
    			aggiungi_filtro((IFilter)filtriApplicati.get(i));
    	}
    }
    
    public Vector getFiltered() {
        if(filtered==null)   
            return unfiltered;
        else
            return filtered;
    }
    
    private void setFiltered(Vector filtered){
        this.filtered=filtered;
    }
    
    public void addFilter(IFilter f)  {
	        if(filtriApplicati.isEmpty()) 
	            aggiungi_primo_filtro(f);
            else
                aggiungi_filtro(f);  
	        filtriApplicati.add(f);
    }    
    
    
    private void aggiungi_primo_filtro(IFilter f) {
        try {
	    	setFiltered(new Vector());
	        for(int i=0;i<unfiltered.size();i++) {
	            if(f.check(unfiltered.get(i))) {
	                getFiltered().add(unfiltered.get(i));
	            }
	        }
	    }
	    catch(NullPointerException e){
	    	JOptionPane.showMessageDialog(null,"selezionare correttamente gli elementi da filtrare");
	    } 
	    catch (Exception e) {
			e.printStackTrace();
		}
	    
    }
    
    private void aggiungi_filtro(IFilter f) {
        Object[] array=getFiltered().toArray();
        try {
	        for(int i=0;i<array.length;i++) {
	            if(!f.check(getFiltered().get(i)))
	                array[i]=null;	                
	        }
	        Vector v= new Vector();
	        for(int i=0;i<array.length;i++)
	            if(array[i]!=null) 
	                v.add(array[i]);                    
	        setFiltered(v);
        }
        catch(NullPointerException e){
        	JOptionPane.showMessageDialog(null,"selezionare correttamente gli elementi da filtrare");
        } 
        catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void removeFilter(IFilter f) throws Exception {
        if(filtriApplicati.isEmpty()) 
            return;
        else
            filtriApplicati.remove(f);
        refresh_filter();
    }
    
    private void refresh_filter() {
        if(filtriApplicati.isEmpty()) 
            setFiltered(null);
        else
	        aggiungi_primo_filtro(((IFilter)filtriApplicati.get(0)));                  
        for(int i=1;i<filtriApplicati.size();i++)
            aggiungi_filtro(((IFilter)filtriApplicati.get(i)));     
    }
    
    //{{METODI PER LA GESTIONE DEL PANNELLO DEI FILTRI 
    
    public Vector getFiltriApplicabili() {
        return filtriApplicabili;
    }
    
    public void setFiltriApplicabili(Vector filtriApplicabili) {
        this.filtriApplicabili = filtriApplicabili;
    }
    
    public Content_filtri_JPanel getContent_filtri_JPanel() {
        if(content_filtri_JPanel==null)
            content_filtri_JPanel=new Content_filtri_JPanel(this);
        return content_filtri_JPanel;
    }
    
    public void add_JFilterPanel(JFiltroPanel f) {
        getContent_filtri_JPanel().aggiungi_filtro(f);
    }
    
    public void remove_JFilterPanel(JFiltroPanel f) {
        getContent_filtri_JPanel().rimuovi_filtro(f);
    }
    
    public RadioButton_JPanel getRadioButtonJPanel() {
        if (radioButton_JPanel==null) {
            String[] s=new String[getSwg().getFilters().length];
            for (int i = 0; i < getSwg().getFilters().length; i++) {
            	if ( getSwg().getFilters()[i] != null )
            		s[i] = getSwg().getFilters()[i].getDescrizione();    
            }
            radioButton_JPanel=new RadioButton_JPanel(s);
        }
        return radioButton_JPanel;
    }
    
    
    private class RadioButton_JPanel extends JPanel implements ActionListener{
        
        JButton[] radioButtons;
        String[] labels;
        
        

        public void setLabels(String[] labels) {
            this.labels = labels;
        }
        
        public RadioButton_JPanel(String[] labels) {
            super();
            setLabels(labels);
            this.add(new JLabel("Filtra per...  "));
            for(int i=0;i<getLabels().length;i++) {
                if(getButtons()[i]!=null)
                	this.add(getButtons()[i]);
            }
            this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0,0,0,5),
            		BorderFactory.createLineBorder(Color.BLACK)));
        }
        private String[] getLabels(){
            if(labels==null)
                labels=new String[1];
            return labels;            
        }
        
        private JButton[] getButtons() {
            if(radioButtons==null) {
	            radioButtons= new JButton[swg.getFilters().length];
	            for(int i=0; i< swg.getFilters().length;i++) {
	            	if ( swg.getFilters()[i] != null ) {
		                radioButtons[i]=swg.getFilters()[i].getButton();
		                if(radioButtons[i]!=null)
		                	radioButtons[i].addActionListener(this);
	            	}
	            }
            }
            return radioButtons;
        }

        public void actionPerformed(ActionEvent e) {
            for(int i=0; i< radioButtons.length;i++)
                if(e.getSource().equals(radioButtons[i])) {
                    try {
                        if(!getContent_filtri_JPanel().isFiltriFull()){
                        	add_JFilterPanel(swg.getFilters()[i].getJFilterPanel());
                    		radioButtons[i].setEnabled(false);
                        }
                        else
                        	JOptionPane.showMessageDialog(getContent_filtri_JPanel(),"non puoi applicare più di due filtri contemporaneamente");
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    return;
                }          
        }
        
    }
    
    public IFiltrable getSwg() {
        return swg;
    }
    
    private void setSwg(IFiltrable swg) {
        this.swg = swg;
    }

    public void refresh_radio_button_JPanel() {
    }
    
}
    
    
    
    


