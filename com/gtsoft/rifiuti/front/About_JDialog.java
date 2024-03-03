package com.gtsoft.rifiuti.front;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class About_JDialog extends JDialog{
	
	private JLabel image=null;
	private JTextPane text_JTextPane=null;
	private String[] arrayText=null;
	private String text=null;
	
	/**
	 * path del file contenente l'immagine
	 */
	private final String IMAGEPATH="/image/splash.gif";
	
	/**
	 * corpo del testo da inserire
	 */
	private final String TEXT = RifiutiFrame.Cost.APP_NAME +
								"\n Software concesso in licenza \n " +
							    "da Nexus s.r.l.  " +
							    "a Portovesme s.r.l.\n" + 
							    "Autori: G. Mereu, A. Aresu"  ;
	

	/**
	 * spazio a fronte e a piè del frame prima che inizi il testo
	 */
	private final int HGAP=40;
	
	private final long PERIOD=1000;
	
	
	private final int MAX_ROWS_VISIBLE=5;
	
	private String imagePath=IMAGEPATH;
	
	public About_JDialog(Frame owner){
		super(owner);
		//this.setSize(new Dimension(355,200));
		this.setTitle("About");
		this.setAlwaysOnTop(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		Container c=getContentPane();
		c.add(getImage(),BorderLayout.CENTER);
		c.add(getText_JTextPane(),BorderLayout.SOUTH);
	}
	
	private JTextPane getText_JTextPane(){
		if(text_JTextPane==null){
			text_JTextPane=new JTextPane();
			text_JTextPane.setEditable(false);
//			text_JTextPane.setBackground(new Color(240,240,240));

			SimpleAttributeSet attributeSet = new SimpleAttributeSet();
			StyleConstants.setAlignment(attributeSet, StyleConstants.ALIGN_CENTER); //allineamento
			StyleConstants.setBold(attributeSet,true); //vuoi grassetto?
			StyleConstants.setSpaceAbove(attributeSet,4); //spazio tra le righe
			StyleConstants.setFontSize(attributeSet,12);
			
			//StyleConstants.setForeground(attributeSet,Color.WHITE);
			text_JTextPane.setParagraphAttributes(attributeSet, false);
			text_JTextPane.setText(TEXT) ; //getText());
		}
		return text_JTextPane;
	}
	
	
	
	
	
	
	
	public void setArrayText(String text){
		
		while(text.startsWith("\n"))
			text=text.substring(2);
		
		while(text.endsWith("\n"))
			text=text.substring(0,text.length()-2);
		
		arrayText=text.split("\n");
			
	}
	
	public String[] getArrayText(){
		if(arrayText==null)
			setArrayText(TEXT);
		return arrayText;
	}
	
	public String getText(){
		if(text==null){
			setArrayText(TEXT);
			text="";
			for (int i=0;i<Math.min(MAX_ROWS_VISIBLE,getArrayText().length);i++)
				text=text.concat(arrayText[i]+"\n");
				
		}
		return text;
	}
	
	public void refreshText(int step) throws Exception{
		if(getArrayText().length-MAX_ROWS_VISIBLE>0 && step>getArrayText().length-MAX_ROWS_VISIBLE)
			throw new Exception("valore di step maggiore del consentito");
		else{
			String text="";
			for(int i=step;i<step+MAX_ROWS_VISIBLE;i++)
				if(i<getArrayText().length)
					text=text.concat(getArrayText()[i]+"\n");
			setText(text);
		}
	}
	
	public void setText(String text){
		this.text=text;
		getText_JTextPane().setText(getText());
	}
	
	public JLabel getImage(){
		if(image==null){
			image=new JLabel(new ImageIcon(getClass().getResource(imagePath)));
			image.setLayout(new BorderLayout(HGAP,10));
			setText(getText());
			
			JPanel emptyNorth_JPanel= new JPanel();
			JLabel emptyNorth_JLabel= new JLabel();
			
			
			emptyNorth_JPanel.add(emptyNorth_JLabel);
			emptyNorth_JPanel.setOpaque(false);
			
			JPanel emptySouth_JPanel= new JPanel();
			emptySouth_JPanel.setMinimumSize(new Dimension(40,30));
			emptySouth_JPanel.setPreferredSize(new Dimension(40,30));
			emptyNorth_JPanel.setPreferredSize(new Dimension(40,50));
			emptyNorth_JLabel.setMinimumSize(new Dimension(60,50));
			
			emptySouth_JPanel.setOpaque(false);
			
			
			//image.add(getText_JTextPane(),BorderLayout.CENTER);
			//image.add(emptyNorth_JPanel,BorderLayout.NORTH);
			//image.add(emptySouth_JPanel,BorderLayout.SOUTH);
		}
		return image;
	}
	
	public void setIMagePath(String imagePath){
		this.imagePath=imagePath;
		getImage().setIcon(new ImageIcon(getClass().getResource(imagePath)));
	}
	
	
	public void showDialog(){
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		final Timer t=new Timer();
		t.schedule(new TimerTask(){
			
			private int step=0;
			public void run() {
				if(step>getArrayText().length-5-1){
					try {
						refreshText(0);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					t.cancel();
				}
				try {
					refreshText(step);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				step++;	
			}
		
		},PERIOD,PERIOD);
		
	}
	
	
	/**
	 * main di test
	 * @param args
	 */
	public static void main(String[] args) {
		new About_JDialog(null).showDialog();
		
	}
}