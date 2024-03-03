package com.gtsoft.rifiuti.main;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main_prova {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ImageIcon i=createImageIcon("/image/splash.gif");
		JButton l= new JButton("cacac",i);
		//l.setText("bocca di rosa");

			
		
	
		JFrame f= new JFrame();
		f.getContentPane().add(l);
		
		/*
		b.setText("due");
		f.add(b);
		*/
		
		f.pack();
		f.setVisible(true);
	 
	 	//elaborationSplashScreen.show();
	 	
	 	
	 

	}
	
	protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = JLabel.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

}
