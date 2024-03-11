/*
 * Created on 10-ott-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.utils.reports;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.gtsoft.rifiuti.front.RifiutiFrame;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JRViewer;


/**
 * @author Gtron - 
 * 
 */
public class ReportViewer extends javax.swing.JFrame {
    private JRViewer viewer = null;
    private javax.swing.JPanel pnlMain;
    private RifiutiFrame ownerFrame ;
    private Vector listaMovimenti = null ;
    public ReportViewer( JasperPrint jasperPrint , RifiutiFrame ownerFrame , Vector listaMovimenti ) {
        
        this.ownerFrame = ownerFrame ;
        this.listaMovimenti = listaMovimenti ;
        
        initComponents();
        
        this.viewer = new JRViewer(jasperPrint);
        this.pnlMain.add(this.viewer, BorderLayout.CENTER);
        this.setVisible(true);
    }
    
    private void initComponents() {//GEN-BEGIN:initComponents
		pnlMain = new javax.swing.JPanel();

		setTitle("Visualizzazione Registro");
		setIconImage(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/jricon.GIF")).getImage());
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				exitForm();
			}
		});

		pnlMain.setLayout(new java.awt.BorderLayout());

		getContentPane().add(pnlMain, java.awt.BorderLayout.CENTER);

		pack();
		
		Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
		java.awt.Dimension screenSize = toolkit.getScreenSize();
		int screenResolution = toolkit.getScreenResolution();
		float zoom = 0.8f; // ((float) screenResolution) / JRViewer.REPORT_RESOLUTION;
		
		int height = (int) (550 * zoom);
		if (height > screenSize.getHeight())
		{
			height = (int) screenSize.getHeight();
		}		
		int width = (int) (750 * zoom);
		if (width > screenSize.getWidth())
		{
			width = (int) screenSize.getWidth();
		}
		
		java.awt.Dimension dimension = new java.awt.Dimension(width, height);
		setSize(dimension);
		setLocation((screenSize.width-width)/2,(screenSize.height-height)/2);
	}

	/** Exit the Application */
	void exitForm() {//GEN-FIRST:event_exitForm
		
		this.setVisible(false);
//		this.viewer.clear();
//		this.viewer = null;
		this.getContentPane().removeAll();
		this.dispose();
		
		if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,
		"La stampa è andata a buon fine ?")) {
		    try {
                ownerFrame.setStampati( listaMovimenti ) ;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Si è verificato un errore durante l'aggiornamento dei movimenti!",
                        " Errore Database ",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
		}
//		try {
//			ownerFrame.setAnnoCorrente();
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Si è verificato un errore durante l'aggiornamento dei movimenti!",
//                    " Errore Database ",
//                    JOptionPane.ERROR_MESSAGE);
//            e.printStackTrace();
//        }
		
	}
}
