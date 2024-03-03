package com.gtsoft.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import com.gtsoft.rifiuti.data.Carico;
import com.gtsoft.rifiuti.data.CaricoParziale;
import com.gtsoft.rifiuti.data.Cliente;
import com.gtsoft.rifiuti.data.Fornitore;
import com.gtsoft.rifiuti.data.Movimento;
import com.gtsoft.rifiuti.data.Rifiuto;
import com.gtsoft.rifiuti.data.Vettore;
import com.gtsoft.rifiuti.data.sql.SQLCarico;
import com.gtsoft.rifiuti.data.sql.SQLCaricoParziale;
import com.gtsoft.rifiuti.data.sql.SQLMovimento;
import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.utils.config.Config;
import com.gtsoft.utils.config.ConfigManager;
import com.gtsoft.utils.superdata.IDatabase;

/** 
 * @author Gtron - 
 * 
 */
public class ImportManager implements Runnable {

	AccessDB db = null ;
	HsqlDB hdb = null ;

	Carico lastImported = null ;

	private int current = 0;
	private int num = 0 ;

	private RifiutiFrame ownerFrame = null ;
	public ImportManager( RifiutiFrame f ) {
		ownerFrame = f ;
	}

	public void doImport() throws Exception {

		Vector listaMovimenti = new Vector() ; // Carico.getFromAccessStartingFrom( lastImported, db) ;

		num = listaMovimenti.size() ;
		current = 0 ;

		if ( num < 1 ) 
			throw new Exception("Illegal function usage... Call the Police!") ;

		Iterator i = listaMovimenti.iterator() ;

		Carico c = null ;
		Movimento m = null ;
		Rifiuto r = null ;
		int id = 0;

		int tot = listaMovimenti.size() ;

		while( i.hasNext() ) {
			c = (Carico) i.next() ;

			r = c.getRifiuto();
			if ( r.getCodiceQuadrelliPadre() != null && r.getCodiceQuadrelliPadre().length() > 0 ) {
				c.setMerce(Rifiuto.get(r.getCodiceQuadrelliPadre()));
			}

			m = new Movimento() ;
			m.setMerceOriginale(r.getCodiceQuadrelli());

			id = c.createTellId(hdb) ;

			if ( id >= 0 ) 
				m.setCarico( Carico.get( id ) ) ;

			m.setAnno( Integer.parseInt( c.getData().ymdString().substring(0,4) ) ) ;
			m.setData(c.getData());
			m.setTipo(Movimento.TipoMovimento.CARICO);

			m.create(hdb);

			ownerFrame.setWait( 1 + (( current * 98 ) / tot ) );
			current ++ ;
		}
		hdb.checkpoint();
	}

	public boolean check() throws Exception {

		Config dest = Config.get("destinazioni") ;
		String destinazioni = "1,5" ;
		if ( dest != null )
			destinazioni = dest.getValue() ;

		Carico lastAccess = Carico.getLast(db,destinazioni) ;
		Carico lastHsql = Carico.getLast(hdb,destinazioni) ;

		lastImported = lastHsql ;

		if ( lastAccess == null || lastHsql == null ) {
			return true ;
		}
		return (
				( lastAccess.getNumeroProgressivo().compareTo(lastHsql.getNumeroProgressivo()) > 0 ) 
				|| 
				( lastAccess.getData().after( lastHsql.getData() ) )
		);

	}


	public void run() {

		try {

			String filename = ConfigManager.getAccessDBFile(ownerFrame);

			db = new AccessDB(filename) ;
			hdb = new HsqlDB() ;

			if ( check () ) {

				SwingWorker worker = new SwingWorker(){
					public Object construct() {
						try {

							int option = JOptionPane.showConfirmDialog(null,"Sono presenti nuovi carichi.\nSi vuole importarli?");
							if (JOptionPane.YES_OPTION == option) {
								ownerFrame.setWaitCursor(true);
								ownerFrame.showElaboration();
								ownerFrame.setWait(1);
								doImport() ;
								ownerFrame.setWait(99);
								ownerFrame.refreshElenchi(true);
								Rifiuto.clearCache();
								ownerFrame.hideElaboration();
								JOptionPane.showMessageDialog(null,"Carichi importati con successo.");

							}

						} catch (Exception e1) {
							e1.printStackTrace();
							ownerFrame.hideElaboration();
							ownerFrame.setWaitCursor(false);
						}
						return null;
					}
				};

				worker.start();

				//if (JOptionPane.CANCEL_OPTION == option)
				//  System.exit(0);
			}
			else {
				JOptionPane.showMessageDialog(null,"Non sono presenti carichi da importare.");
			}

		}
		catch ( Exception e ) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Attenzione: Si è verificato un errore\ndurante l'importazione !",
					"Attenzione",
					JOptionPane.WARNING_MESSAGE);
		}
		finally {
			try {
				db.end() ;
			}
			catch ( Exception e ) {
				e.printStackTrace();
			}
		}
	}

	public int getProgress() {
		return Math.round( current / num ) ;
	}

	public static void importTo( FormattedDate dateTo , String dbfilename ) throws Exception {
		HsqlDB hdb = new HsqlDB() ;
		importTo( dateTo , dbfilename , hdb );
	}

	public static void importTo( FormattedDate dateTo , String dbfilename , HsqlDB hdb ) throws Exception {

		AccessDB adb = new AccessDB( dbfilename ) ;

		adb.getConnection().createStatement();

		Config dest = Config.get("destinazioni") ;
		String destinazioni = "1,5" ;
		if ( dest != null )
			destinazioni = dest.getValue() ;
		
		Carico lastHsql = Carico.getLast(hdb, destinazioni ) ;
		CaricoParziale lastParzialeHsql = CaricoParziale.getLast(hdb) ;
		
		FormattedDate giornoTmp = null ;
		
		if ( RifiutiFrame.isRifiuto342 || ( ! RifiutiFrame.isFumi ) ) {
			if ( lastParzialeHsql != null )
				giornoTmp = new FormattedDate( lastParzialeHsql.getData().dmyString() + " 00:00:00") ;
		}
		else { 
			if ( lastHsql != null )
				giornoTmp = new FormattedDate( lastHsql.getData().dmyString() + " 00:00:00") ;
		}
		
		if ( giornoTmp == null ) {
			giornoTmp = new FormattedDate( new FormattedDate().getAnno() + "-01-01 00:00:00") ;
		}
		else {
			giornoTmp = new FormattedDate( new Date(86400000 + giornoTmp.getTime()) ) ;
		}

		hdb.checkpoint() ;
		hdb.backup("Allineamento al " + dateTo.ymdString() );

		while ( ! giornoTmp.after(dateTo) ) {

			if ( RifiutiFrame.isRifiuto342 ) {
				ImportManager.processRifiuto342( giornoTmp, dbfilename, hdb, adb);
			}
			else {
				ImportManager.processScorie( giornoTmp, dbfilename, hdb, adb);
				
				
				
				Vector listaMovimenti = Carico.getFromAccess( giornoTmp,(lastHsql != null)?lastHsql.getNumeroProgressivo(): null , 
						destinazioni, adb) ;
	
				ImportManager.importListaCarichi( listaMovimenti , hdb ) ;
			}
			
			giornoTmp = new FormattedDate( new Date(86400000 + giornoTmp.getTime()) ) ;
			lastHsql = Carico.getLast(hdb, destinazioni) ;

		}

		hdb.checkpoint() ;

	}


	public static void importListaCarichi( Vector listaMovimenti , HsqlDB db ) {


//		int num , current ;

		try {

			Iterator i = listaMovimenti.iterator() ;

			Carico c = null ;
			Movimento m = null ;
			Rifiuto r = null ;
			int id = 0;


			while( i.hasNext() ) {
				c = (Carico) i.next() ;

				r = c.getRifiuto();
				if ( r.getCodiceQuadrelliPadre() != null && r.getCodiceQuadrelliPadre().length() > 0 ) {
					c.setMerce(Rifiuto.get(r.getCodiceQuadrelliPadre()));
				}

				m = new Movimento() ;
				m.setMerceOriginale(r.getCodiceQuadrelli());

				id = c.createTellId(db) ;

				if ( id >= 0 ) 
					m.setCarico( Carico.get( id ) ) ;

				m.setAnno( Integer.parseInt( c.getData().ymdString().substring(0,4) ) ) ;
				m.setData(c.getData());
				m.setTipo(Movimento.TipoMovimento.CARICO);

				m.create(db);

			}
			// db.checkpoint();
		}
		catch ( Exception e  ) {
			e.printStackTrace() ;
		}
	}

	public static void importClienti() { 
		AccessDB adb = null ;
		HsqlDB hdb = null ;      
//		int current ;

		try {
			adb = new AccessDB("") ;
			hdb = new HsqlDB() ;

			Vector lista = Cliente.getAll(adb) ;
//			current = 0 ;

			Iterator i = lista.iterator() ;

			Cliente c = null ;
			while( i.hasNext() ) {
				c = (Cliente) i.next() ;
				c.create(hdb);
//				current ++ ;
			}
		}
		catch( Exception e ){
			e.printStackTrace();
		}
		finally {
			try {
				adb.end() ; hdb.end();
			}
			catch( Exception e ){
				e.printStackTrace();
			}
		}
	}

	public static void importFornitori() { 
		AccessDB adb = null ;
		HsqlDB hdb = null ;      
//		int current ;

		try {
			adb = new AccessDB("c:/Pesa1/Movimentazioni v1.1a.mdb") ;
			hdb = new HsqlDB() ;

			Vector lista = Fornitore.getAll(adb) ;

//			current = 0 ;

			Iterator i = lista.iterator() ;

			Fornitore c = null ;
			while( i.hasNext() ) {
				c = (Fornitore) i.next() ;
				c.create(hdb);
//				current ++ ;
			}
		}
		catch( Exception e ){
			e.printStackTrace();
		}
		finally {
			try {
				adb.end() ; hdb.end();
			}
			catch( Exception e ){
				e.printStackTrace();
			}
		}
	}


	public static void importVettori() { 
		AccessDB adb = null ;
		HsqlDB hdb = null ;      
		int current ;

		try {
			adb = new AccessDB("") ;
			hdb = new HsqlDB() ;

			Vector lista = Vettore.getAll(adb) ;
			
			current = 0 ;

			Iterator i = lista.iterator() ;

			Vettore c = null ;
			while( i.hasNext() ) {
				c = (Vettore) i.next() ;
				c.create(hdb);
				current ++ ;
			}
		}
		catch( Exception e ){
			e.printStackTrace();
		}
		finally {
			try {
				adb.end() ; hdb.end();
			}
			catch( Exception e ){
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param movimento
	 */
	public void importFromAccess(Movimento m) throws Exception {

		AccessDB adb = null ;      

		adb = new AccessDB(ConfigManager.getAccessDBFile(ownerFrame)) ;

		if ( m.isParziale() ) {
			if ( m.getMerce().isScoria() ) {
				CaricoParziale cp = m.getCaricoParziale() ;

				cp.refreshImport(adb, ownerFrame.getDatabase() );
			}
		}
		else {
			Carico c = m.getCarico();
			c.refreshImport( adb, ownerFrame.getDatabase() ) ;
			m.setCarico( Carico.getByProgressivo(c.getNumeroProgressivo(), c.getData() ) ) ;

		}

		ownerFrame.refreshElenchi();
	} 

	
	public static int processScorie_wrong( RifiutiFrame frame ) throws Exception {

		AccessDB adb = new AccessDB(ConfigManager.getAccessDBFile(frame)) ;
		IDatabase hdb = frame.getDatabase() ;

		SQLCarico aSql = new SQLCarico(adb);
		SQLCarico hSql = new SQLCarico(hdb);
 
		Carico aLastScoria = aSql.getLastScorie();
		Carico hLastScoria = hSql.getLastScorie();

		FormattedDate giorno = null ;
		if ( hLastScoria != null )
			giorno = hLastScoria.getData() ;
		if ( hLastScoria == null || aLastScoria.after(hLastScoria) ) {

			Vector listScorie = null ; // Carico.getScorieFromAccessStartingFrom( hLastScoria , adb ) ;
//			Vector carichiParziali = null ;
			if ( listScorie != null ) {
				CaricoParziale.pushScorie( listScorie ) ; 
			}

			Vector sommeCarichi = CaricoParziale.getSommeScoriePerGiorno( giorno ) ;
			Vector scarichiGiorno = null ;
			CaricoParziale cpSommaGiorno = null ;
			Carico c = null ;
			CaricoParziale scGiorno = null ;

			Movimento m = null ;
			int idCarico = -1 ;

			for ( Iterator i = sommeCarichi.iterator() ; i.hasNext() ; ) {
				cpSommaGiorno = (CaricoParziale) i.next() ;

				c = (Carico) cpSommaGiorno ;

				m = new Movimento() ;

				idCarico = c.createTellId(hdb) ;
				c = null ;
				c = Carico.get( idCarico ) ;

				m.setCarico( c ) ;
				m.setAnno( Integer.parseInt( c.getData().ymdString().substring(0,4) ) ) ;
				m.setData( c.getData());
				m.setTipo(Movimento.TipoMovimento.SCORIE);

				m.create(hdb) ;

				scarichiGiorno = CaricoParziale.getByMerceGiorno( c.getCodiceMerce(), c.getData() , true ) ;
				for ( Iterator cg = scarichiGiorno.iterator() ; cg.hasNext() ; ) {
					m = new Movimento() ;

					scGiorno = (CaricoParziale) cg.next() ;
					scGiorno.setIdCarico(idCarico) ;
					scGiorno.update();

					m.setCarico( c ) ;

					m.setAnno( Integer.parseInt( c.getData().ymdString().substring(0,4) ) ) ;
					m.setData(c.getData());
					m.setTipo(Movimento.TipoMovimento.SCARICOPARZIALE);
					m.setIdCaricoParziale( scGiorno.getId().intValue() ) ;


					m.create(hdb);

				}
				c.scarica( new FormattedDate() , false );
			}
		}
		hdb.checkpoint() ;
		return 0 ;
	}

	public static int processScorie( FormattedDate giorno , String adbFile , HsqlDB hdb, AccessDB adb ) throws Exception {

		try {

//			SQLCarico aSql = new SQLCarico(adb);
//			SQLCarico hSql = new SQLCarico(hdb);

//			Carico aLastScoria = aSql.getLastScorie();
//			Carico hLastScoria = hSql.getLastScorie();

			{

				Vector listScorie = Carico.getScorieFromAccess( giorno , adb) ;
//				Vector carichiParziali = null ; 
				if ( listScorie != null ) {
					CaricoParziale.pushScorie( listScorie ) ; 
				}

				Vector sommeCarichi = CaricoParziale.getSommeScoriePerGiorno( giorno ) ;
				Vector scarichiGiorno = null ;
				CaricoParziale cpSommaGiorno = null ;
				Carico c = null ;
				CaricoParziale scGiorno = null ;

				Movimento m = null ;
				int idCarico = -1 ;

				for ( Iterator i = sommeCarichi.iterator() ; i.hasNext() ; ) {
					cpSommaGiorno = (CaricoParziale) i.next() ;

					c = (Carico) cpSommaGiorno ;

					m = new Movimento() ;

					idCarico = c.createTellId(hdb) ;
					c = null ;
					c = Carico.get( idCarico ) ;

					m.setCarico( c ) ;
					m.setAnno( Integer.parseInt( c.getData().ymdString().substring(0,4) ) ) ;
					m.setData( c.getData());
					m.setTipo(Movimento.TipoMovimento.SCORIE);

					m.create(hdb) ;

					scarichiGiorno = CaricoParziale.getByMerceGiorno( c.getCodiceMerce(), c.getData() , true ) ;
					for ( Iterator cg = scarichiGiorno.iterator() ; cg.hasNext() ; ) {
						m = new Movimento() ;

						scGiorno = (CaricoParziale) cg.next() ;
						scGiorno.setIdCarico(idCarico) ;
						scGiorno.update();

						m.setCarico( c ) ;

						m.setAnno( Integer.parseInt( c.getData().ymdString().substring(0,4) ) ) ;
						m.setData(c.getData());
						m.setTipo(Movimento.TipoMovimento.SCARICOPARZIALE);
						m.setIdCaricoParziale( scGiorno.getId().intValue() ) ;


						m.create(hdb);

					}
					c.scarica( new FormattedDate() , false );
				}
			}

		}
		catch ( Exception e ) {
			e.printStackTrace(); 
		}
		return 0 ;
	}

	public static int processRifiuto342( FormattedDate giorno , String adbFile , HsqlDB hdb, AccessDB adb ) throws Exception {

		String codRifiuto = "342" ;
		boolean overGiacenza = false ;
		
		try {
			Vector listScarichi = Carico.getRifiutoFromAccess( codRifiuto , giorno , adb) ;

			Movimento scarico = null ;
//			int idCarico = -1 ;
			
			Vector list = Carico.getCarichiDaScaricare( codRifiuto, Integer.parseInt( giorno.getAnno()) ) ;
			
			if ( list == null || list.size() < 1 ) {
				throw new Exception ( "Non è stato trovato alcun carico di giacenza") ;
			}
			
			Carico cGiacenza = (Carico) list.firstElement() ;
			
			list = CaricoParziale.getByCarico(cGiacenza) ;
			if ( list == null || list.size() < 1 ) {
				throw new Exception ( "Non è stato trovato alcun carico parziale di giacenza") ;
			}
			
			CaricoParziale cpGiacenza = (CaricoParziale) list.firstElement() ;
			
//			Movimento mGiacenza = Movimento.getForCarico(cGiacenza.getId().intValue());
			
			Carico c = null ;
//			Vector v = null ;
			CaricoParziale ncp = cpGiacenza ;
			
			for ( Iterator i = listScarichi.iterator() ; i.hasNext() ; ) {
				
				c = (Carico) i.next() ;
				
				cpGiacenza = ncp ;
				
				double diff = cpGiacenza.getNetto() - c.getNetto() ;
				
				if ( diff < 0 ) {
					if (overGiacenza) {
						JOptionPane.showMessageDialog(null, 
								"Attenzione ! \n\nGiacenza insufficiente per lo scarico automatico.\n" +
								"Si prega di procedere manualmente.", 
								"Giacenza Insufficiente", JOptionPane.ERROR_MESSAGE) ;
						return 0 ;
					}
				}
				else if ( diff > 0 ) {
                    
					cpGiacenza.setNetto( c.getNetto() ) ;
					cpGiacenza.setVettore(c.getCodiceVettore());
					cpGiacenza.setData(c.getData());
					cpGiacenza.setDocumento(c.getDocumento());
					cpGiacenza.setDataFormulario(c.getDataFormulario());
                    cpGiacenza.update() ;
                    
                	ncp = cpGiacenza.cloneMe() ;
                    ncp.setNetto( diff ) ;
                    ncp.createSetID() ;
                    
                }
                else {
                    if ( JOptionPane.YES_OPTION != 
                        JOptionPane.showConfirmDialog(null, 
                                "Attenzione! Si sta terminando la giacenza.\n" +
                                "Procedere ?")) {
                        return 0 ;
                    }
                }
				
				scarico = new Movimento() ;

				scarico.setCarico( cGiacenza ) ;
				scarico.setAnno( Integer.parseInt( c.getData().ymdString().substring(0,4) ) ) ;
				scarico.setData( c.getData() );
				scarico.setTipo(Movimento.TipoMovimento.SCARICOPARZIALE);
				
				scarico.setIdCaricoParziale(cpGiacenza.getId().intValue()) ;
                scarico.create( hdb );
                
                CaricoParziale.scarica( cpGiacenza.getId(),  scarico) ;
    	        
                if ( diff == 0 ) {
                    c.scarica(scarico.getData()) ;
                }
                
			}
		}
		catch ( Exception e ) {
			e.printStackTrace(); 
		}
		return 0 ;
	}

	public static void chiusuraAnno( int anno , RifiutiFrame frame) throws Exception {

		String strAnno = "" + anno ;

		IDatabase hdb = frame.getDatabase() ;

		int numDaStampare = Movimento.countDaStampare();

		if ( numDaStampare > 0 ) {
			//throw new Exception( "Attenzione! Sono presenti " + numDaStampare + " movimenti non stampati.") ;
			throw new Exception( "StampaRifiuti") ;
		}

		hdb.backup("Prima della Chiusura Anno " + strAnno );

		hdb.backupYear( strAnno );

		Vector rifiuti = null ;
		Rifiuto r = null ;
//		if ( ! RifiutiFrame.separateFumi || RifiutiFrame.isFumi || RifiutiFrame.isRifiuto342 ) {
			rifiuti = Rifiuto.getAll(hdb) ;
			for ( Iterator i = rifiuti.iterator() ; i.hasNext() ; ) {
				r = (Rifiuto) i.next() ;
				r.getGiacenzaDouble() ;
			}
//		}

		// Purge
		SQLMovimento.purgeTable(hdb);
		SQLCarico.purgeTable(hdb);
		SQLCaricoParziale.purgeTable(hdb);

//		if ( ! RifiutiFrame.separateFumi || RifiutiFrame.isFumi  || RifiutiFrame.isRifiuto342  ) {
			Carico c = null ;
			Movimento m = null ;
			CaricoParziale cp = null ;
			double giacenza ;
			int idCarico = 0 ;
			for ( Iterator i = rifiuti.iterator() ; i.hasNext() ; ) {
				r = (Rifiuto) i.next() ;

				giacenza = r.getGiacenzaDouble().doubleValue() ;

				if ( giacenza > 0 ) {
					c = new Carico() ;
					c.setMerce(r);

					FormattedDate data = new FormattedDate( new FormattedDate().getAnno() + "-01-01 00:00:00" ) ;
					c.setData( data ); // La data del carico di giacenza è sempre il primo gennaio
					c.setNetto( giacenza );

					idCarico = c.createTellId(hdb) ;
					c = null ;
					c = Carico.get( idCarico ) ;

					cp = new CaricoParziale() ;
					cp.setCarico(c);
					cp.setIdCarico(c.getId().intValue()) ;
					cp.setMerce(r);
					cp.setVettore(Carico.CODICEVETTOREGIACENZA);
					cp.setData( new FormattedDate()); // La data del carico di giacenza è quella di creazione
					cp.setNetto(giacenza);
					cp.setDescrizione(CaricoParziale.DESCRIZIONI.GIACENZA);

					cp.create();

					m = new Movimento() ;
					m.setCarico( c ) ;
					m.setAnno( Integer.parseInt(new FormattedDate().getAnno()) ) ;
					m.setData( c.getData());
					m.setTipo(Movimento.TipoMovimento.GIACENZA );

					m.create(hdb) ;
				}
			}
//		}

		hdb.shutdown(true) ;
		hdb.start() ;

		StoricoManager.updateStorico(hdb);

		hdb.backup("Dopo Chiusura Anno " + strAnno );
		
//		 Carica anno precedente se necessario
        JComboBox anno_JComboBox = frame.getSwgMovimento().getFilterManager().getContent_filtri_JPanel().getAnno_JComboBox() ;
        int annoCorrente = Calendar.getInstance().get(Calendar.YEAR) ;

        anno_JComboBox.addItem(new Integer( annoCorrente ));
        
        frame.setAnno("" + annoCorrente) ;
        anno_JComboBox.setSelectedIndex(anno_JComboBox.getItemCount() - 1 );
	}
}