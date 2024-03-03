/*
 * Creato il 27-ago-2005
 *
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti_new
 * 
 */
package com.gtsoft.rifiuti.front;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gtsoft.utils.FileManager;
import com.gtsoft.utils.filter.FilterManager;


public class Content_filtri_JPanel extends JPanel{
    
//    private JPanel borderLayoutPanel;
    private JPanel filterHead_JPanel;
//    private JButton buttonFiltro;
    private JPanel filtri_JPanel;
    private FilterManager fm;
    private JFiltroPanel filtro1;
    private JFiltroPanel filtro2;
    private boolean filtro1IsValid;
    private boolean filtro2IsValid;
    
    public Content_filtri_JPanel(FilterManager fm) {
        super(new BorderLayout());
        this.fm=fm;
        this.add(get_filter_head_JPanel(), BorderLayout.NORTH);
        this.add(get_filtri_JPanel(),BorderLayout.SOUTH);
        this.setBorder(BorderFactory.createEmptyBorder(2,2,2,2) );
        // this.setPreferredSize(new Dimension(300,30));
    }
    
    public JPanel get_filter_head_JPanel() {
        if(filterHead_JPanel==null) {
            filterHead_JPanel=new JPanel(new BorderLayout());
            filterHead_JPanel.add(fm.getRadioButtonJPanel(),BorderLayout.EAST); 
            
            JPanel addButton = new JPanel() ;
            addButton.setLayout(new BoxLayout(addButton, BoxLayout.LINE_AXIS)) ;
            addButton.setBorder(BorderFactory.createEmptyBorder(4,4,4,4)) ;
            addButton.add(new JLabel(" Anno :  "));
            addButton.add(getAnno_JComboBox() ) ;
            filterHead_JPanel.add( addButton , BorderLayout.WEST);
            filterHead_JPanel.setMinimumSize(new Dimension(360,13));
            //filter_JPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,0,5,5),BorderFactory.createLineBorder(Color.BLACK)));
        }
        return filterHead_JPanel;           
    }
    
    
    private static JComboBox anno_JComboBox;
	public JComboBox getAnno_JComboBox() {
		if(anno_JComboBox==null){
			anno_JComboBox= new JComboBox();
			
			Vector anni = FileManager.listDirs(RifiutiFrame.Cost.DATADIR) ;
			String s = null ;
			for ( Iterator i = anni.iterator() ; i.hasNext() ; ) {
				s = (String) i.next();

				try { 
					anno_JComboBox.addItem(new Integer(s) );
				}
				catch ( NumberFormatException n ) { }
				 
			}
		}
		anno_JComboBox.repaint() ;
		return anno_JComboBox;
	}

	public void aggiungi_filtro(JFiltroPanel filtroPanel) {
        if(!isFiltro1IsValid()) {
            setFiltro1(filtroPanel);
        	setFiltro1IsValid(true);
        	get_filtri_JPanel().add(getFiltro1());
        }
        else
            if(!isFiltro2IsValid()) {
                setFiltro2(filtroPanel);
                setFiltro2IsValid(true);
                get_filtri_JPanel().add(getFiltro2());
            }  
            get_filtri_JPanel().revalidate();
    }
    
    public void rimuovi_filtro(JPanel filtroPanel) {
        if(isFiltro1IsValid()) 
            if(filtroPanel.equals(getFiltro1())) {
                get_filtri_JPanel().remove(getFiltro1());
                setFiltro1IsValid(false);      	
            }
            else
                if(isFiltro2IsValid())
                    if(filtroPanel.equals(getFiltro2())){
                        get_filtri_JPanel().remove(getFiltro2());
                        setFiltro2IsValid(false);
                    }
                    else;
                else;
        else
            if(isFiltro2IsValid()) 
                if(filtroPanel.equals(getFiltro2())){
                    get_filtri_JPanel().remove(getFiltro2());
                    setFiltro2IsValid(false);
                }
    }
    
    
    public JFiltroPanel getFiltro1() {
        if(filtro1==null)
            filtro1=new JFiltroPanel();
        return filtro1;
    }
    public void setFiltro1(JFiltroPanel filtro1) {
        this.filtro1 = filtro1;
        this.filtro1.setVisible(true);
    }
    
    public boolean isFiltro1IsValid() {
        return filtro1IsValid;
    }
    
    
    public boolean isFiltriFull(){
    	return isFiltro1IsValid()&&isFiltro2IsValid();
    }
    
    
    public void setFiltro1IsValid(boolean filtro1IsValid) {
        this.filtro1IsValid = filtro1IsValid;
    }
    public JFiltroPanel getFiltro2() {
        if(filtro2==null)
            filtro2=new JFiltroPanel();
        return filtro2;
    }
    public void setFiltro2(JFiltroPanel filtro2) {
        this.filtro2 = filtro2;
        this.filtro2.setVisible(true);
    }
    public boolean isFiltro2IsValid() {
        return filtro2IsValid;
    }
    public void setFiltro2IsValid(boolean filtro2IsValid) {
        this.filtro2IsValid = filtro2IsValid;
    }
    
    public JPanel get_filtri_JPanel() {
        if(filtri_JPanel==null) {
        	filtri_JPanel=new JPanel(new GridLayout(0,1));        	
        }
        return filtri_JPanel;
    }

	public void setAnno_comboBox(String anno) {
		for(int i=0;i< getAnno_JComboBox().getItemCount() ;i++)
			if( ("" + getAnno_JComboBox().getItemAt(i)).equals( anno ) ){
				getAnno_JComboBox().setSelectedIndex(i);
				return;
			}
		
		getAnno_JComboBox().setSelectedIndex(getAnno_JComboBox().getItemCount() - 1 );
		
	}



}
