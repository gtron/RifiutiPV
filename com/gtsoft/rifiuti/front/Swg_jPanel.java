/*
 * Creato il 14-ago-2005
 *
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti_test
 * 
 */
package com.gtsoft.rifiuti.front;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.gtsoft.rifiuti.front.data.AbstractSwg;
import com.gtsoft.utils.SwingWorker;


public class Swg_jPanel extends JPanel {

    private AbstractSwg swg;
    private Content_filtri_JPanel content_filter_JPanel;

    public Swg_jPanel(AbstractSwg swg) {
        super(new BorderLayout());
        this.swg=swg;
        //listJPanel.setPreferredSize(new Dimension(300,600));
        add(swg.getJMainTable(), BorderLayout.CENTER);
        add(getContent_filter_JPanel(), BorderLayout.NORTH);
    }

    public Content_filtri_JPanel getContent_filter_JPanel() {
        if(content_filter_JPanel==null){
            content_filter_JPanel=swg.getFilterManager().getContent_filtri_JPanel();
        	content_filter_JPanel.getAnno_JComboBox().addActionListener(swg_panelActionListener);
        }
        return content_filter_JPanel;
        
    }
    
    
    
    private Swg_panelActionListener swg_panelActionListener=new Swg_panelActionListener();
    private class Swg_panelActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(getContent_filter_JPanel().getAnno_JComboBox())) {
			    final JComboBox a = (JComboBox)e.getSource();
				 
			    
			    final SwingWorker worker = new SwingWorker() {
			        public Object construct() {
			        	try {
			        		RifiutiFrame f = swg.getFrame() ;
			        		String annoCorrente = f.getAnno() ;
			        		
			        		if ( ! annoCorrente.equals( "" + a.getSelectedItem() ) ) {
			        			f.showElaboration() ;
			        			f.setWait(30);
			        			f.setAnno( "" + a.getSelectedItem() );
			        			f.setWait(90);
			        			f.hideElaboration() ;
			        		}
                		} catch (Exception e1) {
                            e1.printStackTrace();
                		}
			            return null;
			        }
			    };
			    
			    worker.start();
                    
                
                }
			}
    	
    }
    
    

}
