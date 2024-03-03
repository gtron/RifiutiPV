package com.gtsoft.utils.config;


public class SWTBackupManager {

//	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="196,81"
//	private List backups_list = null;
//	private Button cancel_button = null;
//	private Button ok_button = null;
//	
//	HashMap backups = null ;  //  @jve:decl-index=0:
//	private Label label = null;
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		/* Before this is run, be sure to set up the launch configuration (Arguments->VM Arguments)
//		 * for the correct SWT library path in order to run with the SWT dlls. 
//		 * The dlls are located in the SWT plugin jar.  
//		 * For example, on Windows the Eclipse SWT 3.1 plugin jar is:
//		 *       installation_directory\plugins\org.eclipse.swt.win32_3.1.0.jar
//		 */
//		Display display = Display.getDefault();
//		SWTBackupManager thisClass = new SWTBackupManager();
//		thisClass.createSShell();
//		thisClass.sShell.open();
//
//		while (!thisClass.sShell.isDisposed()) {
//			if (!display.readAndDispatch())
//				display.sleep();
//		}
//		display.dispose();
//	}
//
//	/**
//	 * This method initializes sShell
//	 */
//	private void createSShell() {
//		GridData gridData3 = new GridData();
//		gridData3.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
//		gridData3.grabExcessHorizontalSpace = true;
//		GridData gridData2 = new GridData();
//		gridData2.horizontalAlignment = org.eclipse.swt.layout.GridData.END;
//		GridData gridData1 = new GridData();
//		gridData1.horizontalAlignment = org.eclipse.swt.layout.GridData.BEGINNING;
//		GridData gridData = new GridData();
//		gridData.horizontalSpan = 4;
//		gridData.grabExcessHorizontalSpace = true;
//		gridData.grabExcessVerticalSpace = true;
//		gridData.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
//		gridData.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
//		GridLayout gridLayout = new GridLayout();
//		gridLayout.numColumns = 4;
//		sShell = new Shell();
//		sShell.setText("Backup Manager");
//		sShell.setLayout(gridLayout);
//		sShell.setSize(new Point(312, 194));
//		label = new Label(sShell, SWT.SHADOW_OUT);
//		label.setText("Backup disponibili :");
//		Label filler2 = new Label(sShell, SWT.NONE);
//		Label filler3 = new Label(sShell, SWT.NONE);
//		Label filler6 = new Label(sShell, SWT.NONE);
//		backups_list = new List(sShell, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
//		backups_list.setLayoutData(gridData);
//		Label filler1 = new Label(sShell, SWT.NONE);
//		filler1.setLayoutData(gridData3);
//		cancel_button = new Button(sShell, SWT.NONE);
//		cancel_button.setText("Annulla");
//		cancel_button.setLayoutData(gridData2);
//		Label filler = new Label(sShell, SWT.NONE);
//		ok_button = new Button(sShell, SWT.NONE);
//		ok_button.setText("Ripristina");
//		ok_button.setLayoutData(gridData1);
//		
//		fillBackupList() ;
//		fillBackupList() ;
//		fillBackupList() ;
//		fillBackupList() ;
//		fillBackupList() ;
//		fillBackupList() ;
//		fillBackupList() ;
//		
//	}
//	
//	private void fillBackupList() {
//		backups  = FileManager.listBackups( HsqlDB.BACKUPDIR ) ;
//		
//		HashMap day = null ;
//		for( Iterator i = backups.keySet().iterator() ; i.hasNext() ; ) {
//			day = (HashMap) backups.get( i.next()) ;
//			backups_list.add( (String) day.get("comment") ) ;
//		}
//	}
//	
}
