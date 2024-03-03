/*
 * Creato il 1-set-2005
 *
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti_new
 * 
 */
package com.gtsoft.rifiuti.front;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;


public class SplashScreen {
	private JDialog frame = new JDialog();
	private JProgressBar progress;
	private int steps;
	private boolean isPercentual;
	private String message;
	private int max_count_percentual=100;
	
	private JProgressBar getProgressBar(){
		if(progress==null){
			progress=new JProgressBar();
		}
		
		return progress;
	}
	
	public SplashScreen(String message, int steps, String imagepath) {
	    
		//finestra senza barra del titolo e bordo
		setSteps(steps);
		setMessage(message);
		frame.setUndecorated(true);
		JComponent c = (JComponent)frame.getContentPane();
		c.setLayout(new BorderLayout());
		getProgressBar().setMinimum(0);
		getProgressBar().setValue(0);
		getProgressBar().setOrientation(JProgressBar.HORIZONTAL);
		getProgressBar().setString(message);
		getProgressBar().setStringPainted(true);
		getProgressBar().setBorder(new CompoundBorder(
			new EmptyBorder(4, 4, 4, 4),
			getProgressBar().getBorder()));
		c.add(getProgressBar(), BorderLayout.SOUTH);
		JLabel splash = new JLabel(new ImageIcon(getClass().getResource(
			imagepath)));
		splash.setBorder(new CompoundBorder(
			new EmptyBorder(4, 4, 4, 4),
			new EtchedBorder()));
		c.add(splash, BorderLayout.CENTER);
//		c.setBackground(Color.WHITE);
		c.setBorder(new LineBorder(Color.BLACK, 1));
	}
	
	public SplashScreen(boolean isPercentual, String imagepath){
		this("%",100,imagepath);
	}
	
	/** AWT-Safe */
	public void show() {
		max_count_percentual=100;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame.pack();
				//al centro dello schermo
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
		setIsShow(true);
	}
	
	
	public void show(int max_count_percentual){
		this.max_count_percentual=max_count_percentual;
		show();
	}
	
	public JDialog getFrame(){
		return frame;
	}
	
	
	/** AWT-Safe */
	public void hide() {
		if(isShow()){
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					frame.dispose();
				}
			});
			
			setIsShow(false);
		}
	}

	/** AWT-Safe */
	public void advance(final String message) {
		if(isShow()){
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					//imposta la stringa message se diversa da null
					getProgressBar().setString(message == null ? "" : message);
					//avanza di 1
					getProgressBar().setValue(getProgressBar().getValue() + 1);
				}
			});
		}
	}
	
	
	private int percentual;
	private boolean isShow=false;
	
	public void advance(int in_percentual) {
		if(isShow()){
			this.percentual=in_percentual;
			if(percentual>=0 && percentual<=100  ){
				SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					getProgressBar().setString(percentual + getMessage());
					getProgressBar().setValue(percentual);
				}
			});
			}
		}
	}
	
	public void advance_relative(int step){
		if(max_count_percentual % step==0){
			int percentual=(step/max_count_percentual)*100;
			advance(percentual);
		}
	}
	
	public void advance(String message, int in_percentual){
		setMessage(message);
		advance(in_percentual);
	}

	public int getSteps() {
		
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
		getProgressBar().setMaximum(steps);
	}
	
		public void reset(){
			getProgressBar().setValue(0);
		}



		public boolean isPercentual() {
			return isPercentual;
		}



		public void setPercentual(boolean isPercentual) {
			this.isPercentual = isPercentual;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		private void setIsShow(boolean isShow){
			this.isShow=isShow;
		}
		
		public boolean isShow() {
			return isShow;
		}

		public void setMax_count_percentual(int max_count_percentual) {
			this.max_count_percentual = max_count_percentual;
		}
}
