package com.gtsoft.rifiuti.front;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.gtsoft.utils.FormattedDate;
import com.gtsoft.utils.config.PasswordDemo;
import com.toedter.calendar.JDateChooser;

/* PasswordDemo.java is a 1.4 application that requires no other files. */

public class ImportDialog extends JPanel implements Runnable, ActionListener {
    
    private JFrame controllingFrame; //needed for dialogs
    private JDateChooser dataField = null;
    
    private static final String OK = "ok" ;  
    
    public ImportDialog(JFrame f, char[] password) {
        
    	//Use the default FlowLayout.
        controllingFrame = f;

        //Create everything.
        dataField = new JDateChooser("dd/MM/yyyy",false);
       

        JLabel label = new JLabel("Importare fino alla data : ");
        label.setLabelFor(dataField);

        JComponent buttonPanel = createButtonPanel();

        //Lay out everything.
        JPanel textPane = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        textPane.add(label);
        textPane.add(dataField);

        add(textPane);
        add(buttonPanel);
    }

    protected JComponent createButtonPanel() {
        JPanel p = new JPanel(new GridLayout(0,1));
        JButton okButton = new JButton("OK");
        JButton helpButton = new JButton("annulla");
        
        okButton.setActionCommand(OK) ;
        
        okButton.addActionListener(this);
        helpButton.addActionListener(this);

        p.add(okButton);
        p.add(helpButton);

        return p;
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (OK.equals(cmd)) { 
            resetFocus();
        } 
        else { //ho cliccato annulla
            this.setVisible(false);
        }
    }

    //Must be called from the event-dispatching thread.
    protected void resetFocus() {
        this.requestFocusInWindow();
    }

    
    private static void createAndShowGUI() {
    	createAndShowGUI(null);
    }
   
    private static void createAndShowGUI(FormattedDate d) {
    	
    	if(d!=null) {}
    		
        
    	//Create and set up the window.
        JFrame frame = new JFrame("Password di Accesso");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);

        //Create and set up the content pane.
        final PasswordDemo newContentPane = new PasswordDemo(frame);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        
        
        //Make sure the focus goes to the right component
        //whenever the frame is initially given the focus.
        frame.addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent e) {
                //newContentPane.resetFocus();
            }
        });

        //Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static Object lock=new Object();
	public void run() {			
			//create();
			try {
				synchronized (lock) {
					lock.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	
	private class DateChangeListener implements ChangeListener {

        public void stateChanged(ChangeEvent arg0) {
            
            String c  = "" ;
            /*
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
            			*/
        }
    }
}
