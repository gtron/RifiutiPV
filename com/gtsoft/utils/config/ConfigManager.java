package com.gtsoft.utils.config;

import java.awt.Frame;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.utils.HsqlDB;

public class ConfigManager {
	
	private ChangePW_jDialog changePW_jDialog;
	private JDialog selectAccessDb_JDialog;
	private File selectedFile;
	private JFileChooser jFileChooser;
	
	
	public ChangePW_jDialog getChangePw_JDialog(){
		if(changePW_jDialog==null)
			changePW_jDialog=new ChangePW_jDialog();
		return changePW_jDialog;
	}

	private JDialog getSelectAccessDb_JDialog(Frame owner){
		if(selectAccessDb_JDialog==null){
			selectAccessDb_JDialog=new JDialog(owner);
			selectAccessDb_JDialog.setTitle("Scegli il database da caricare");
			selectAccessDb_JDialog.setModal(true);
			selectAccessDb_JDialog.setSize(600,400);
		}
		return selectAccessDb_JDialog;
	}
	
	
	/**
	 * da usare per creare il filechooser.
	 * ritorna true se ho scelto il file o false se ho scelto annulla
	 */
	public boolean create_JFileChooser(Frame owner,String text){
		int r=getJFileChooser().showDialog(getSelectAccessDb_JDialog(owner),text);
		if(r==JFileChooser.APPROVE_OPTION){
			setSelectFile(getJFileChooser().getSelectedFile());
			return true;
		}
		else
			if(r==JFileChooser.CANCEL_OPTION)
				return false;
		return false;
	}
	
	
	private JFileChooser getJFileChooser(){
		if(jFileChooser==null)
			jFileChooser=new JFileChooser();
			jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jFileChooser.addChoosableFileFilter(new AccessFileFilter());
			jFileChooser.setAcceptAllFileFilterUsed(false);
			
		return jFileChooser;
		
	}
	
	
	public static void main(String[] args) {
		
		
		if ( args.length < 1 ) {
//			JFrame f= new JFrame();
//			f.setSize(300,300);
//			f.setVisible(true);
//			ConfigManager configManager=new ConfigManager();
//			configManager.create_JFileChooser(f,"Config");
			return ;
		}
		
		for ( String s : args ) {
			System.out.println( s ) ; 
		}
		
		Boolean b = null ;
		
		if ( args.length == 4 ) {
			
			
			HsqlDB db = new HsqlDB() ;
			if ( args[0] == "--divide" ) {
				b = new Boolean(args[1]) ;
				put("dividedapps", b.toString() ) ;
			}
			if ( args[2] == "--type" ) {
				b = new Boolean(args[3]) ;
				put("isfumi", b.toString() ) ;
			}
			
			
			
			
			try {
				db.shutdown() ;
			}
			catch (Exception e ) {
				e.printStackTrace() ;
			}
		}
		
		
	}

	/**
	 * ritorna l'ultimo file selezionato dal filechooser.... 
	 * null se non ne � mai stato selezionato uno
	 * @return
	 */
	public File getSelectFile() {
		return selectedFile;
	}

	private void setSelectFile(File selectFile) {
		this.selectedFile = selectFile;
	}

	private class AccessFileFilter extends FileFilter {
	
		public boolean accept(File f) {
			if (f.isDirectory()) {
	            return true;
	        }
	
	        String extension = getExtension(f);
	        if (extension != null) {
	            if (extension.equals("accdb"))
	            	return true;
	        }
            return false;
		}
		
		private String getExtension(File f) {
	        String ext = null;
	        String s = f.getName();
	        int i = s.lastIndexOf('.');

	        if (i > 0 &&  i < s.length() - 1) {
	            ext = s.substring(i+1).toLowerCase();
	        }
	        return ext;
	    }

		public String getDescription() {
			return "Database di Microsoft Access (*.accdb)";
		}
	}

	//private static char[] PASSWORD={'p','o','r','t','o','v','e','s','m','e'}; 
	
	public static void createPasswordGui(RifiutiFrame f ){
	    
		Thread pwThread=new Thread(new PasswordDemo(null, getPassword(f))); 
	    pwThread.start();
	    try {
			pwThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
		
	public static char[] getPassword( RifiutiFrame f ){
	    
	    String pwd = "" + Config.getPassword().getValue() ;
	    
	    return pwd.toCharArray() ;
	}
	
	public static String getAccessDBFile( JFrame frame ) throws Exception {
	    return getAccessDBFile( frame , false) ;
	}
	public static String getAccessDBFile( JFrame frame , boolean force ) throws Exception {
	    
	    Config cfile = null ;
	    if ( ! force )
	        cfile = Config.get("accessdb") ;
	    
	    if ( cfile != null ) 
	        return cfile.getValue() ;
	    else {
	        ConfigManager cf = new ConfigManager() ;
	        boolean b = cf.create_JFileChooser(frame,"Selezione Database per Importazione");
	        
	        String file = "";
	        if ( b ) {
	            file = cf.getSelectFile().getAbsolutePath();
	            ConfigManager.put("accessdb", file ) ;
	        }
	        
	        return file ;
	    }
	}
	
	/**
     * @param string
     * @param file
     */
    public static void put(String key, String value) {
        Config c = new Config() ;
        c.setKey(key);
        c.setValue(value);
        
        try {
            c.create();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
  
}
	

