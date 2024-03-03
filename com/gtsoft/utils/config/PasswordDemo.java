package com.gtsoft.utils.config;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.gtsoft.rifiuti.front.RifiutiFrame;

/* PasswordDemo.java is a 1.4 application that requires no other files. */

public class PasswordDemo extends JPanel implements Runnable, ActionListener {
    private static String OK = "ok";
    private static String HELP = "help";

    private JFrame controllingFrame; //needed for dialogs
    private JPasswordField passwordField;
    private static char[] password={'p','i','n','o','c','c','h','i','o'};

    public PasswordDemo(JFrame f) {
    	this(f,null);
    }
    
    
    public PasswordDemo(JFrame f, char[] password) {
        
    	
    	setPassword(password);
    	//Use the default FlowLayout.
        controllingFrame = f;


        //Create everything.
        passwordField = new JPasswordField(10);
        passwordField.setEchoChar('*');
        passwordField.setActionCommand(OK);
        passwordField.addActionListener(this);

        JLabel label = new JLabel("inserisci la password: ");
        label.setLabelFor(passwordField);

        JComponent buttonPane = createButtonPanel();

        //Lay out everything.
        JPanel textPane = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        textPane.add(label);
        textPane.add(passwordField);

        add(textPane);
        add(buttonPane);
    }

    protected JComponent createButtonPanel() {
        JPanel p = new JPanel(new GridLayout(0,1));
        JButton okButton = new JButton("OK");
        JButton helpButton = new JButton("annulla");

        okButton.setActionCommand(OK);
        helpButton.setActionCommand(HELP);
        okButton.addActionListener(this);
        helpButton.addActionListener(this);

        p.add(okButton);
        p.add(helpButton);

        return p;
    }

    
    private static boolean isPasswordOk=false;
    
    private static void setPasswordOk(boolean b) {
		isPasswordOk=b;
		
	}

	
	public static boolean isPasswordOk() {
		return isPasswordOk;
	}
  
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (OK.equals(cmd)) { //Process the password.
            char[] input = passwordField.getPassword();
            if (isPasswordCorrect(input)) {
               try {
	               synchronized (lock) {
		               controllingFrame.dispose();	
		               setPasswordOk(true);
		               lock.notifyAll();
	               }
               } 
               catch (Exception e1) {
				e1.printStackTrace();
               }
               
            } 
            else {
            	setPasswordOk(false);
            	JOptionPane.showMessageDialog(controllingFrame,"password non valida riprovare","Error Message",JOptionPane.ERROR_MESSAGE);
            }


            for (int i = 0; i < input.length; i++) {
                input[i] = 0;
            }

            passwordField.selectAll();
            resetFocus();
        } 
        else { //ho cliccato annulla
            if ( controllingFrame instanceof RifiutiFrame ) {
                try {
                    ((RifiutiFrame) controllingFrame).getDatabase().shutdown() ;
                }
                catch (Exception ec) {
                    ec.printStackTrace();
                }
            }
            System.exit(0);
        }
    }

    /**
     * Checks the passed-in array against the correct password.
     * After this method returns, you should invoke eraseArray
     * on the passed-in array.
     */
    private static boolean isPasswordCorrect(char[] input) {
        boolean isCorrect = true;
        char[] correctPassword = password;

        if (input.length != correctPassword.length) {
            isCorrect = false;
        } else {
            for (int i = 0; i < input.length; i++) {
                if (input[i] != correctPassword[i]) {
                    isCorrect = false;
                }
            }
        }

        

        return isCorrect;
    }

    //Must be called from the event-dispatching thread.
    protected void resetFocus() {
        passwordField.requestFocusInWindow();
    }

    
    private static void createAndShowGUI() {
    	createAndShowGUI(null);
    }
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI(char[] password) {
    	
    	if(password!=null)
    		setPassword(password);
        
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
                newContentPane.resetFocus();
            }
        });

        //Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void create() {
    	createAndShowGUI();
        
    }
    
    public static void create(char[] password) {
    	createAndShowGUI(password);
    }

	public static void setPassword(char[] password) {
		if(password!=null)
			PasswordDemo.password = password;
	}

	private static Object lock=new Object();
	public void run() {			
			create();
			try {
				synchronized (lock) {
					lock.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
}
