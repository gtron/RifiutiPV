/*
 * Created on 5-set-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.utils.reports;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.view.JasperViewer;

import com.gtsoft.rifiuti.data.Movimento;
import com.gtsoft.rifiuti.data.Rifiuto;
import com.gtsoft.rifiuti.data.Vettore;
import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Gtron - 
 * 
 */
public class ReportManager {
    
    private static final int MOVIMENTIPERPAGINA = 4 ;
    
    public static void printPageNumber( int numDa, int num ) {
        
        try {
            JRDataSource data = new JRMovimentoFactory().getPageNumbers( numDa , num ) ;
            
            //JasperReport rep = JasperCompileManager.compileReport("build/report/template/primapassata.jrxml") ;
            
            String rep = "reports/primapassata.jasper" ;
            
            HashMap map = new HashMap();
            
            JasperPrint printer = JasperFillManager.fillReport( rep, map , data );
            
            JasperViewer.viewReport( printer , false ) ;
            
        }
        catch ( Exception e ) {
            e.printStackTrace() ;
        }
        
        
    }
    
    public static int estimatePages( Vector list ) {
        return ( list.size() / MOVIMENTIPERPAGINA ) ;
    }
    
    
    public static void printRegistro( Vector list , RifiutiFrame frame ) {
        
        Thread t = new Thread( new RegistroPrinter(list, frame) );
        t.start() ;
        
    }
    
    private static class RegistroPrinter implements Runnable {
        
        Vector list = null ;
        RifiutiFrame ownerFrame = null ;
        RegistroPrinter( Vector l , RifiutiFrame owner ) {
            list = l ;
            ownerFrame = owner;
        }
        
        public void run () {
            try {
                JRDataSource data = new JRMovimentoFactory().getMovimenti( list ) ;
                
                //JasperReport rep = JasperCompileManager.compileReport("build/report/template/primapassata.jrxml") ;
                
                String rep = "reports/registro.jasper" ;
                
                HashMap map = new HashMap();
                map.put("INTERNAL" , Boolean.FALSE) ;
                
                JasperPrint printer = JasperFillManager.fillReport( rep, map , data );
                
                new ReportViewer( printer , ownerFrame , list ) ;
                
            }
            catch ( Exception e ) {
                e.printStackTrace() ;
            }
        }
    }
    
    public static void printMovimenti( Vector list ) {
        
        try {
            JRDataSource data = new JRMovimentoFactory().getMovimenti( list ) ;
            //JasperReport rep = JasperCompileManager.compileReport("build/report/template/primapassata.jrxml") ;
            
            String rep = "reports/registro.jasper" ;
            
            HashMap map = new HashMap();
            map.put("INTERNAL" , Boolean.TRUE) ;
            
            JasperPrint printer = JasperFillManager.fillReport( rep, map , data );
            JasperViewer.viewReport( printer , false ) ;
            
        }
        catch ( Exception e ) {
            e.printStackTrace() ;
        }
        
    }
    
    public static void exportMovimenti( Vector list , String filename ) throws Exception {
        JRDataSource data = new JRMovimentoFactory().getMovimenti( list ) ;
        
        String rep = "reports/expMov.jasper" ;
        
        HashMap map = new HashMap();
        
        JasperPrint printer = JasperFillManager.fillReport( rep, map , data );
        
        //Creazione dell'xls
        JRXlsExporter exporter = new JRXlsExporter();
        
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, printer);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filename );
        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
        // exporter.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.FALSE );
        exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.FALSE );
        
        exporter.exportReport();
        
        /*
         * TO DO 
         *  - togliere bold
         *  - fornitore -> cliente 
         *  - larghezze colonne
         *  - aggiungere trasportatore
         * 
         */
        
    }
    
    public static void printRifiuti_old(Vector list){
        try {
            JRDataSource data = new JRRifiutoFactory().getRifiuti( list ) ;
            
            JasperReport rep = JasperCompileManager.compileReport("build/report/template/report_rifiuti.jrxml") ;
            
            //   String rep = "report_rifiuti.jasper" ;
            
            HashMap map = new HashMap();
            
            JasperPrint printer = JasperFillManager.fillReport( rep, map , data );
            
            JasperViewer.viewReport( printer , false ) ;
            
        }
        catch ( Exception e ) {
            e.printStackTrace() ;
        }
    }
    
    public static void printRifiuti(Vector list){
        try {
            JRDataSource data = new JRRifiutoFactory().getRifiuti( list ) ;
            //JasperReport rep = JasperCompileManager.compileReport("build/report/template/primapassata.jrxml") ;
            
            String rep = "reports/report_rifiuti.jasper" ;
            
            HashMap map = new HashMap();
            
            JasperPrint printer = JasperFillManager.fillReport( rep, map , data );
            JasperViewer.viewReport( printer , false ) ;
            
        }
        catch ( Exception e ) {
            e.printStackTrace() ;
        }
    }
    
    public static void printSumMovimenti( Vector list ) {
        
        try {
            JRDataSource data = new JRRifiutoFactory().getSumRifiuti( list ) ;
            
            String rep = "reports/SinteticoRifiuti.jasper" ;
            
            HashMap map = new HashMap();
            
            JasperPrint printer = JasperFillManager.fillReport( rep, map , data );
            JasperViewer.viewReport( printer , false ) ;
            
        }
        catch ( Exception e ) {
            e.printStackTrace() ;
        }
        
    }
    
    public static void printMovimentiTrasportatore( Vector list ) {
        
        try {
            JRDataSource data = new JRRifiutoFactory().getSumRifiuti( list ) ;
            
            String rep = "reports/MovimentiTrasportatore.jasper" ;
            
            HashMap map = new HashMap();
            if ( list != null && list.size() > 0 ) {
                Vettore vett = ((Movimento) list.firstElement()).getTrasportatore() ;
                
                if ( vett != null )
                    map.put("Trasportatore", vett.getRagioneSociale() );
            }
            
            JasperPrint printer = JasperFillManager.fillReport( rep, map , data );
            
            JasperViewer.viewReport( printer , false ) ;
            
        }
        catch ( Exception e ) {
            e.printStackTrace() ;
        }
        
    }
    
    
    public static void printCover( ) {
        
        try {
            
            //JasperReport rep = JasperCompileManager.compileReport("build/report/template/primapassata.jrxml") ;
            
            String rep = "reports/copertina_registro.jasper" ;
            
            HashMap map = new HashMap();
            
            JasperPrint printer = JasperFillManager.fillReport( rep, map , new JREmptyDataSource() );
            JasperViewer.viewReport( printer , false ) ;
            
        }
        catch ( Exception e ) {
            e.printStackTrace() ;
        }
        
    }
    
    
    private static void stampaMovimenti( Vector list, String filename ) throws Exception {
        
        JRDataSource data = new JRMovimentoFactory().getMovimenti( list ) ;
        //JasperReport rep = JasperCompileManager.compileReport("build/report/template/primapassata.jrxml") ;
        
        String rep = "reports/stampa_movimenti.jasper" ;
        String repExp = "reports/stampa_movimenti_xls.jasper";
        
        HashMap map = new HashMap();
        
        if ( list != null && list.size() > 0 ) {
            
            Vettore vett = findVettoreUnico(list);
            String nomeReport = null;
            
            if ( vett != null ){
                map.put("Trasportatore", vett.getRagioneSociale() );
                rep = "reports/movimenti_trasportatore.jasper" ;
                repExp = "reports/movimenti_trasportatore_xls.jasper";
                
                int end = (vett.getRagioneSociale().length() > 30) ? 30 : vett.getRagioneSociale().length() ;
                nomeReport = vett.getRagioneSociale().substring(0, end);
                
                
                /*
                 * 
                Movimento m = null ;
                for ( Iterator i = list.iterator() ; i.hasNext() ; ) {
                    m = (Movimento) i.next() ;
                    if ( m.isScarico() )
                        i.remove() ;
                }
                */
            }
            else {
            	Rifiuto rifiuto = findRifiutoUnico(list);
            	
            	if ( rifiuto != null && ! RifiutiFrame.isRifiuto342 ){
                    map.put("Rifiuto", rifiuto );
                    rep = "reports/movimenti_rifiutounico.jasper" ;
                    repExp = "reports/movimenti_rifiutounico_xls.jasper";
                    
                    int end = (rifiuto.getNome().length() > 30) ? 30 : rifiuto.getNome().length() ;
                    nomeReport = rifiuto.getNome().substring(0, end);

                }
            }
            
            map.put( "last" , list.get(0)) ;
            
            JasperPrint printer = null ;
            
            if ( filename != null ) {
                JRAbstractExporter exporter ;
               
                if ( filename.endsWith(".pdf") ) {
                    
                    printer = JasperFillManager.fillReport( rep, map , data );
                    exporter = new JRPdfExporter();
                    exporter.setParameter(JRPdfExporterParameter.IS_ENCRYPTED, Boolean.TRUE);
                    exporter.setParameter(JRPdfExporterParameter.OWNER_PASSWORD, "carloforte");
                    exporter.setParameter(JRPdfExporterParameter.PERMISSIONS, 
                            Integer.valueOf( PdfWriter.AllowPrinting | 
                                    PdfWriter.AllowModifyAnnotations |
                                    PdfWriter.AllowCopy ));
                }
                else {
                    
                    printer = JasperFillManager.fillReport( repExp, map , data );
                    if ( nomeReport != null )
                        printer.setName( nomeReport ) ;
                    
	                exporter = new JRXlsExporter();
	                exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
//	                exporter.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE );
	                exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.FALSE );
                }
                
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, printer);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filename );
                
                exporter.exportReport();
                
            }
            else {
                printer = JasperFillManager.fillReport( rep, map , data );
                JasperViewer.viewReport( printer , false ) ;
            }
        }
    }
    
    private static Vettore findVettoreUnico(Vector list){
        
        Vettore vett = null ;
        int oldId = -1 ;
        
        for ( Iterator i = list.iterator() ; i.hasNext() ;) {
            vett = ((Movimento) i.next()).getTrasportatore() ;
           
            if ( vett != null ) {
                if ( oldId > 0 && oldId != vett.getId() ) {
                    vett = null ;
                    break ;
                }
                else if ( oldId < 0 )
                    oldId = vett.getId() ;
            }
            else {
                oldId = 0 ;
            }
        }
        
        return vett ;
    }
    
    private static Rifiuto findRifiutoUnico(Vector list) throws Exception {
        
    	Rifiuto r = null ;
        int oldId = -1 ;
        
        for ( Iterator i = list.iterator() ; i.hasNext() ;) {
            r = ((Movimento) i.next()).getMerce() ;
           
            if ( r != null ) {
                if ( oldId > 0 && oldId != r.getId() ) {
                    r = null ;
                    break ;
                }
                else if ( oldId < 0 )
                    oldId = r.getId() ;
            }
            else {
                oldId = -1 ;
            }
        }
        
        return r ;
    }
    
    private static void stampaRifiuti( Vector list, String filename,  boolean ignoreSingoloTrasportatore ) throws Exception {
        
    	JRRifiutoFactory jrFactory = new JRRifiutoFactory() ;
        JRDataSource data = jrFactory.getSumRifiuti( list ) ;
        
        String rep = "reports/stampa_rifiuti.jasper" ;
        String repExp = "reports/stampa_rifiuti_xls.jasper";
        
        HashMap map = new HashMap();
        
        if ( list != null && list.size() > 0 ) {
            
            Vettore vett = null ;
            
            if( ! ignoreSingoloTrasportatore )
            	vett = findVettoreUnico(list);
            
            if ( vett != null)
                map.put("Trasportatore", vett.getRagioneSociale() );
            else {
            	Rifiuto rif = findRifiutoUnico(list);
                
                if ( rif != null && ! RifiutiFrame.isRifiuto342 ) {
                    map.put("Rifiuto", rif );
                    rep = "reports/sintetico_rifiutounico.jasper" ;
                    repExp = rep ;
                    data = jrFactory.getSumTrasportatore( list ) ;
                }
            }
            
            map.put( "last" , list.get(0)) ;
            JasperPrint printer = null ;
            
            if ( filename != null ) {
                JRAbstractExporter exporter ;
               
                if ( filename.endsWith(".pdf") ) {
                    
                    printer = JasperFillManager.fillReport( rep, map , data );
                    exporter = new JRPdfExporter();
                    exporter.setParameter(JRPdfExporterParameter.IS_ENCRYPTED, Boolean.TRUE);
                    exporter.setParameter(JRPdfExporterParameter.OWNER_PASSWORD, "carloforte");
                    exporter.setParameter(JRPdfExporterParameter.PERMISSIONS, PdfWriter.AllowPrinting | PdfWriter.AllowModifyAnnotations | PdfWriter.AllowCopy );
                }
                else {
                    printer = JasperFillManager.fillReport( repExp, map , data );
	                exporter = new JRXlsExporter();
	                exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
//	                exporter.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE );
	                exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.FALSE );
                }
                
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, printer);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filename );
                
                exporter.exportReport();
                
            }
            else {
                printer = JasperFillManager.fillReport( rep, map , data );
                JasperViewer.viewReport( printer , false ) ;
            }
            
        }
    }
    
    public static void stampa ( int tipo , String nomeFile , Vector list , boolean ignoreSingoloTrasportatore ) throws Exception {
    
        switch ( tipo ) {
        case STAMPA.ELENCO_MOVIMENTI :
            stampaMovimenti( list , nomeFile ) ;
        	break;
        
        case STAMPA.ELENCO_RIFIUTI :
            stampaRifiuti( list , nomeFile ,ignoreSingoloTrasportatore );
        	break;
        }
        
    }
    
    public static interface STAMPA {
        static int ELENCO_MOVIMENTI = 0 ;
        static int ELENCO_RIFIUTI = 1 ;
    }
    
    public class XlsFilter extends javax.swing.filechooser.FileFilter {
        public boolean accept(File file) {
            String filename = file.getName();
            return filename.endsWith(".xls");
        }
        public String getDescription() {
            return "Microsoft Excel  (*.xls)";
        }
    }

}

