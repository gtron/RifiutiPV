/*
 * Creato il 30-ago-2005
 *
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti_new
 * 
 */
package com.gtsoft.rifiuti.front.data.detailedPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import com.gtsoft.rifiuti.data.Carico;
import com.gtsoft.rifiuti.data.Cliente;
import com.gtsoft.rifiuti.data.Fornitore;
import com.gtsoft.rifiuti.data.Movimento;
import com.gtsoft.rifiuti.data.Vettore;
import com.gtsoft.rifiuti.front.JDatiPanel;
import com.gtsoft.rifiuti.front.JLabelAttribute;
import com.gtsoft.rifiuti.front.JLabelValore;
import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.rifiuti.front.data.SwgMovimento;


public class Movimento_detailedPanel extends JPanel{
    
    private JPanel jDatiPanel;
    private SwgMovimento swg;
    
    private JTextArea txt = null ;
    
    public Movimento_detailedPanel(SwgMovimento swg) {
        super(new BorderLayout());
        this.swg=swg;
//        this.setBackground(Color.WHITE);
        try {
            this.add(swg.getJNomePanel(),BorderLayout.NORTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JPanel cont = new JPanel(new BorderLayout()) ;
        cont.add(getJDatiPanel(),BorderLayout.NORTH);
//      Lo scarico non ha bisogno del pulsante "Scarica"
        
        JPanel buttons = getButtonsJPanel();
        if ( buttons != null  ) {
            cont.add(buttons, BorderLayout.SOUTH);
        }
        
        JScrollPane scrollPane = new JScrollPane( cont );
        this.add(scrollPane,BorderLayout.CENTER);
        
        
        this.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
    }
    
    
    public JPanel getButtonsJPanel() {
        JPanel pnl = new JPanel();
        
        Movimento m = (Movimento) swg.getMovimento() ;
        
        if ( m.isStampato() ) {
            return null ;
        }
        
        
        if ( m.isCarico() && ! m.getCarico().isScaricato() ) {
            //pnl.add(getScaricaJButton());
            
            if ( m.getCarico().getNumeroProgressivo() != null && m.getCarico().getNumeroProgressivo().length() > 0 ) {
                pnl.add(getReimportaJButton());
            }
        }
        else {
            try {
	            if ( m.isParziale() && m.getMerce().isScoria() ) {
	                pnl.add(getReimportaJButton());
	            }
	            else if ( m.isScarico() ) 
	                pnl.add(getDeleteJButton(),BorderLayout.CENTER);
	            else 
	                return null ;
            }
            catch( Exception e ) {
                return null ;
            }
        }
        
        return pnl ;
    }
    
    public JPanel getJDatiPanel() {
        if(jDatiPanel==null) {
            jDatiPanel=new JPanel();
            
            LayoutManager b=new BoxLayout(jDatiPanel,BoxLayout.Y_AXIS);
            
            jDatiPanel.setLayout(b); 
            // jDatiPanel.setBackground(Color.WHITE);
            
            
            try {
                jDatiPanel.add(getHeadJPanel());
                if(swg.getMovimento().isScarico() || swg.getMovimento().isParziale() ) {
                  jDatiPanel.add(getScarichiJPanel());
                  
                  Vettore v=swg.getMovimento().getTrasportatore();
                  if(v!=null)
                      jDatiPanel.add(v.getSwg(swg.getFrame()).getJDatiPanel());
                  
                }
                else {
                    Carico c=((Carico)swg.getMovimento().getCarichi().get(0));
                    jDatiPanel.add(c.getMerce().getSwg(swg.getFrame()).getJDatiPanel());
                    
                    Fornitore f = c.getFornitore() ;
                    if ( f != null && f.getRagioneSociale() != null ) 
                        jDatiPanel.add(f.getSwg(swg.getFrame()).getJDatiPanel());
                    
                    
              
                    Vettore v = c.getVettore() ;
                    if ( v != null ) 
                        jDatiPanel.add(v.getSwg(swg.getFrame()).getJDatiPanel());
                    	
                }
                String[]  kg = {"kg"};
                String[]  kg_value = { Movimento.nformatter.format( swg.getMovimento().getPeso() ) } ;
                jDatiPanel.add(new JDatiPanel(kg ,kg_value,"Quantità"));
                if ( ! swg.getMovimento().isScarico() ) {
                    jDatiPanel.add(getFormularioPanel());
                }
                
                if ( swg.getMovimento().isStampato() ) {
	                String[]  reg = {"Data" , "Ora"};
	                String[]  reg_value = { swg.getMovimento().getDataStampaRegistro().dmyString(), 
	                        swg.getMovimento().getDataStampaRegistro().hmsString() } ;
	                jDatiPanel.add(new JDatiPanel(reg ,reg_value,"Registro"));
                }
                
                JPanel notePanel = new JPanel(new BorderLayout());
                JPanel txtNotePanel = new JPanel(new BorderLayout());
                txtNotePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                
                txt = new JTextArea(3,3) ;
                txt.setBorder(BorderFactory.createEtchedBorder());
                txt.setText(swg.getMovimento().getNote());
                
                txtNotePanel.add(txt, BorderLayout.CENTER);
                notePanel.add(txtNotePanel, BorderLayout.CENTER);
                
                JPanel btnSaveNotePanel = new JPanel();
                JButton btnSaveNote = new JButton("Salva Nota");
                btnSaveNotePanel.add(btnSaveNote);
                btnSaveNote.addActionListener(new java.awt.event.ActionListener() { 
    				public void actionPerformed(java.awt.event.ActionEvent e) {
    				    JButton btn = (JButton) e.getSource();
    				    swg.getMovimento().setNote(txt.getText());
    				    try {
    				        swg.getMovimento().update(swg.getFrame().getDatabase());
    				        JOptionPane.showMessageDialog(null, "Nota Salvata.");
    				    }
    				    catch ( Exception ec ) {
    				        JOptionPane.showMessageDialog(null, "Attenzione ! Errore durante il salvataggio della nota!",
    			                    " Errore !",
    			                    JOptionPane.ERROR_MESSAGE);
    				    }
    				}
    			});
                JButton btnCancelNote = new JButton("Elimina Nota");
                if ( txt.getText().length() > 0 )
                    btnSaveNotePanel.add(btnCancelNote);
                
                btnCancelNote.addActionListener(new java.awt.event.ActionListener() { 
    				public void actionPerformed(java.awt.event.ActionEvent e) {
    				    if ( JOptionPane.NO_OPTION ==  JOptionPane.showConfirmDialog(null, "Attenzione !\n Procedere all'eliminazione della nota?!")) 
    				        return ;
    				    JButton btn = (JButton) e.getSource();
    				    swg.getMovimento().setNote("");
    				    txt.setText("");
    				    try {
    				        swg.getMovimento().update(swg.getFrame().getDatabase());
    				    }
    				    catch ( Exception ec ) {
    				        JOptionPane.showMessageDialog(null, "Attenzione ! Errore durante il salvataggio della nota!",
    			                    " Errore !",
    			                    JOptionPane.ERROR_MESSAGE);
    				    }
    				}
    			});
                notePanel.add(btnSaveNotePanel, BorderLayout.SOUTH);
                notePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5),BorderFactory.createTitledBorder("Annotazioni")));
                jDatiPanel.add(notePanel);
                
                jDatiPanel.add(Box.createVerticalGlue());
                              
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jDatiPanel;
    }
    
    private JPanel scarichiPanel;
    
    private JPanel getScarichiPanel_old() {
        if(scarichiPanel==null) {
            JPanel gJPanel=new JPanel(new GridBagLayout());       
            GridBagConstraints gbr=new GridBagConstraints();
            gbr.ipadx=50;
            gbr.ipady=15;
            gbr.gridx=0;
            gbr.gridy=0;
            gbr.anchor=GridBagConstraints.WEST;
            Carico c=null;
            Vector carichi=swg.getMovimento().getCarichi();
            gJPanel.add(new JLabel("num."),gbr);                        
            gbr.gridx=1;
            gJPanel.add(new JLabel("descr."),gbr);
            
            gbr.gridx=2;
            gJPanel.add(new JLabel("peso"),gbr);

            for(int i=0;i<carichi.size();i++) {
                gbr.gridy=i+1;
                gbr.gridx=0;
                c=((Carico)carichi.get(i));
                gJPanel.add(new JLabel (String.valueOf(c.getNumeroProgressivo())),gbr);
                //gJPanel.setPreferredSize(new Dimension(250,200));
                gbr.gridx=1;
                gJPanel.add( new JLabel(c.getDescrizione()) ,gbr ) ;
                
                gbr.gridx=2;
                gJPanel.add( new JLabel(" kg: " + c.getNetto()),gbr);
                gbr.ipady=20;
            }
            scarichiPanel=new JPanel();
            
            scarichiPanel.add(gJPanel,BorderLayout.CENTER);
            scarichiPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0,5,0,5),BorderFactory.createTitledBorder("Carico")));
        }
        
        return scarichiPanel;
        
    }
    
    private JTable scarichiJTable;
    
    private JPanel getScarichiJPanel(){
    	if(scarichiPanel==null) {
    		scarichiPanel=new JPanel(new BorderLayout());
    		scarichiPanel.add(getScarichiJTable().getTableHeader(),BorderLayout.NORTH);
    		scarichiPanel.add(getScarichiJTable(),BorderLayout.CENTER);
    		  scarichiPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0,5,0,5),BorderFactory.createTitledBorder("Carico")));
    	}
    	
    	return scarichiPanel;       
    }
    
    
    private CaricoTableModel caricoTableModel = null;
    
    
    
    private JTable getScarichiJTable(){
    	if(scarichiJTable==null){
    		scarichiJTable=new JTable(new CaricoTableModel ( swg.getMovimento().getCarichi()));
    		scarichiJTable.setDefaultRenderer(Object.class,new DefaultTableCellRenderer(){
    			public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column)
    			{
                	JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    					c.setHorizontalAlignment(JLabel.CENTER);
    					return c;
    			}
    		});
    		scarichiJTable.setRowSelectionInterval(0,0);
    		scarichiJTable.addMouseListener(popupMouseListener);
    	}
    	return scarichiJTable;
    }
    
    private class CaricoTableModel extends AbstractTableModel{

    	private Vector  carichi;
    	private int row=0;
    	
    	private static final String COLUMN_0="Numero";
    	private static final String COLUMN_1="Descrizione";
    	private static final String COLUMN_2="Peso";
    	private static final int COLUMN_COUNT=3;
    	
    	
    	
    	private CaricoTableModel(Vector carichi){
    		setCarichi(carichi);
    	}
    	
    	
    	public Vector getCarichi() {
			if(carichi==null)
				carichi=new Vector();
    		return carichi;
		}

		public void addCarico(Carico carico){
			getCarichi().add(carico);
			row++;
			fireTableDataChanged();
		}
    	
		public void setCarichi(Vector carichi){
			this.carichi=carichi;
			row=carichi.size();
			fireTableDataChanged();
		}
    	
    	
    //metodi che implementano TableModel	
    	public int getRowCount() {
			return row;
		}

		public int getColumnCount() {
			return COLUMN_COUNT;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			Carico c=((Carico)getCarichi().get(rowIndex));
			Object obj=null;
			if(columnIndex==0)			
				obj=new Long(c.getMovimento().getNumProgressivo());
			else
				if(columnIndex==1)
					obj=c.getDescrizione();
				else
					if(columnIndex==2)
						obj= "" + c.getNetto();
			return obj;
		}
		
		public String getColumnName(int col) {
            switch (col) {
			case 0: 	return COLUMN_0;	
			case 1: 	return COLUMN_1;	
			case 2: 	return COLUMN_2;	
			default:    return COLUMN_0;   
			}
        }
		
		 

		

		
    	
    }
    
    private JButton scarica_button;
    public JButton getScaricaJButton() {
        if(scarica_button==null) {
            scarica_button=new JButton("Scarica Movimento ...");
            if(RifiutiFrame.read_only==true)
            	scarica_button.setEnabled(false);
            scarica_button.addActionListener(mov_det_actionListener);           
        }
        return scarica_button;
    }
    
    private JButton reimporta_button;
    public JButton getReimportaJButton() {
        if(reimporta_button==null) {
            reimporta_button=new JButton("Ricarica");
            reimporta_button.setToolTipText("Ricarica il carico dal DataBase Access");
            reimporta_button.setEnabled(!RifiutiFrame.read_only);
            reimporta_button.addActionListener(mov_det_actionListener);           
        }
        return reimporta_button;
    }
    
    private JButton delete_button;
    public JButton getDeleteJButton() {
        if(delete_button==null) {
            delete_button=new JButton("Elimina Movimento di Scarico");
            delete_button.addActionListener(mov_det_actionListener);
            if(RifiutiFrame.read_only==true)
                delete_button.setEnabled(false);
        }
        return delete_button;
    }
    
    private JButton toScarica_button;
    public JButton getToScaricoJButton() {
        if(toScarica_button==null) {
        	toScarica_button=new JButton("vai allo scarico...");
        	toScarica_button.addActionListener(mov_det_actionListener);
        }
        return toScarica_button;
    }
    	
    
    private JPanel getFormularioPanel() {
        JPanel p=new JPanel();
        
        if ( ! swg.getMovimento().isScarico() ) {
	        String form = swg.getMovimento().getFormulario() ;
	        if ( form == null || form.equals("null")) 
	            form = "N/A" ;
	        p.add(new JLabel( "Numero : " + form )) ;
	        
	        
	        if (  swg.getMovimento().getDataFormulario() != null ) {
	            form = swg.getMovimento().getDataFormulario().dmyString() ;
	        }
	        else
	            form = "N/A" ;
	        p.add(new JLabel( "    Data : " + form )) ;
	        
        }

        
        p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0,5,0,5),BorderFactory.createTitledBorder("Formulario")));
        return p;
    }
    //{{ gestione head_panel
    private JPanel headJPanel;
    public JPanel getHeadJPanel() {
        if(headJPanel==null) {
            headJPanel=new JPanel(new BorderLayout());
            headJPanel.setPreferredSize(new Dimension(250,60));
            JPanel gJPanel=new JPanel(new GridBagLayout());       
            GridBagConstraints gbr=new GridBagConstraints();
            gbr.gridx=0;
            gbr.gridy=0;
            gbr.anchor=GridBagConstraints.WEST;
            gJPanel.add(getCaricoJLabel(),gbr);
            gbr.gridx=1;
            gJPanel.add(getDataJLabel(),gbr);
            gbr.gridy=0;
            gbr.gridx=2;
            gbr.insets=new Insets(0,40,0,0);
            gbr.anchor=GridBagConstraints.EAST;
            gJPanel.add(getNumeroJLabel(),gbr);
            gbr.gridx=3;
            gbr.ipadx=0;
            gbr.insets=new Insets(0,0,0,0);
            gbr.anchor=GridBagConstraints.WEST;
            gJPanel.add(getNumeroValueJLabel(),gbr);
            
            if ( swg.getMovimento().isCarico() ) {
                headJPanel.setPreferredSize(new Dimension(250,80));
	            Cliente cl = swg.getMovimento().getCarico().getCliente() ;
	            if ( cl != null ) {
	                gbr.gridx=0;
	                gbr.gridy=1;
		            gbr.ipadx=2;
		            gbr.insets=new Insets(5,5,5,5);
		            gbr.anchor=GridBagConstraints.EAST;
		            gJPanel.add(new JLabelAttribute("Destinazione :"), gbr);
		            
		            gbr.gridx=1;
		            gbr.gridy=1;
		            gbr.gridwidth=4;
		            gbr.insets=new Insets(5,5,5,5);
		            gbr.anchor=GridBagConstraints.WEST;
		            gJPanel.add(new JLabelValore( cl.getRagioneSociale() ),gbr);
	            }
            }            
            
            headJPanel.add(gJPanel,BorderLayout.WEST);
            headJPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0,5,0,5),BorderFactory.createTitledBorder("Movimento")));
        }
        return headJPanel;
    }


    private JLabel getCaricoJLabel() {
        JLabel carico_JLabel= new JLabelAttribute("Data: ");
        carico_JLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        return carico_JLabel;
    }
    
    private JLabel getDataJLabel() {
        JLabel data_JLabel= new JLabelValore(swg.getMovimento().getData().dmyString());
        data_JLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        return data_JLabel;
    }
    
    private JLabel getNumeroJLabel() {
        JLabel numero_JLabel= new JLabelAttribute("Numero: ");
        numero_JLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        return numero_JLabel;
    }
    
    private JLabel getNumeroValueJLabel() {
        JLabel numeroValue_JLabel= new JLabelValore(String.valueOf(swg.getMovimento().getNumProgressivo()));
        numeroValue_JLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        return numeroValue_JLabel;
    }
    //}}
    
    private Mov_det_actionListener mov_det_actionListener=new Mov_det_actionListener();
    
    private class Mov_det_actionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
           if(e.getSource().equals(getScaricaJButton()))
               swg.vai_a_scarichi();
           else if(e.getSource().equals(get_apri_il_dettaglio_JMenuItem())){
        	   Carico c=(Carico)getCaricoTableModel().getCarichi().get(getScarichiJTable().getSelectedRow());
        	   swg.getJMainTable().selectElement(c.getMovimento());
           }
           else if(e.getSource().equals(getDeleteJButton())) {
               if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,
		               "Procedere all'eliminazione del Movimento?")) {
		           try {
		               swg.eliminaMovimento();
		               JOptionPane.showMessageDialog( null , "Movimento Eliminato",
		                       "Gestione Scarichi",
		                       JOptionPane.INFORMATION_MESSAGE);
		           } catch (Exception e1) {
		               JOptionPane.showMessageDialog( null , "Si è verificato un errore durante l'eliminazione!",
		                       "Attenzione",
		                       JOptionPane.WARNING_MESSAGE);
		               e1.printStackTrace();
		           }
		       }
           }
           else if(e.getSource().equals(getReimportaJButton())) {
               if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,
               "Procedere all'importazione del Carico?")) {
                   try {
                       swg.reimportaMovimento();
                       JOptionPane.showMessageDialog( null , "Movimento Aggiornato",
                               "Gestione Movimenti",
                               JOptionPane.INFORMATION_MESSAGE);
                   } catch (Exception e1) {
                       JOptionPane.showMessageDialog( null , "Si è verificato un errore durante l'importazione!",
                               "Attenzione",
                               JOptionPane.WARNING_MESSAGE);
                       e1.printStackTrace();
                   }
               }
           }
        }
    }
    
    
    PopupMouseListener popupMouseListener=new PopupMouseListener();
	private JPopupMenu jPopupMenu;
	private JMenuItem apri_il_dettaglio_JMenuItem;
    
    private class PopupMouseListener extends MouseAdapter{
    	public void mouseClicked(MouseEvent e){
    		if (e.getButton() == MouseEvent.BUTTON3) {
                getapriPopupMenu().show((Component) e.getSource(), e.getX(), e.getY());                        
            }		
    	}
    }

	public CaricoTableModel getCaricoTableModel() {
		if(caricoTableModel==null)
			caricoTableModel = new CaricoTableModel (swg.getMovimento().getCarichi());
		return caricoTableModel;
	}

	public void setCaricoTableModel(CaricoTableModel caricoTableModel) {
		this.caricoTableModel = caricoTableModel;
	}

	 private JPopupMenu getapriPopupMenu() {
	        if(jPopupMenu==null) {
	            jPopupMenu = new JPopupMenu();
	            jPopupMenu.add(get_apri_il_dettaglio_JMenuItem());	
	        }
	        return jPopupMenu;
	    }
	    
	    private JMenuItem get_apri_il_dettaglio_JMenuItem() {
	        if(apri_il_dettaglio_JMenuItem==null) {
	            apri_il_dettaglio_JMenuItem = new JMenuItem("vai al carico");
	            apri_il_dettaglio_JMenuItem.addActionListener(mov_det_actionListener);
	        }
	        return apri_il_dettaglio_JMenuItem;
	    }
	    
	

}