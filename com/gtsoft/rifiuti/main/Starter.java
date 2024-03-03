/*
 * Created on 8-lug-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.main;

import java.util.Vector;

import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.utils.AccessDB;
import com.gtsoft.utils.FormattedDate;
import com.gtsoft.utils.HsqlDB;
import com.gtsoft.utils.ImportManager;
import com.gtsoft.utils.StoricoManager;

/**
 * @author Gtron - 
 * 
 */
public class Starter {

    public static void main(String[] args) {
    	
        if ( args.length > 0 ) {
            
            Vector vArgs = new Vector();
            for( int i = 0 ; i<args.length ; i++) vArgs.add(args[i]) ;
            
            try {
                if ( vArgs.contains("-h") || vArgs.contains("--help") || vArgs.contains("/?") ) {
                   throw new Exception("HLP") ;
                }
                else if ( vArgs.contains("-sql") ) {
                    int paramPosition = vArgs.indexOf("-sql") ;
                    if ( vArgs.size() > paramPosition ) {
                        int argumentPosition = paramPosition + 1 ;
                        if ( vArgs.size() >= argumentPosition ) {
                            String argument = (String) vArgs.get(argumentPosition) ;
                            if ( ! argument.startsWith("-") ) {
                                ExecuteSqlFile( argument ) ;
                            }
                            else throw new Exception("HLP") ;
                        }
                        else throw new Exception("HLP") ;
                    }
                    else throw new Exception("HLP") ;
                }
                else if ( vArgs.contains("-i") ) {
                    AccessDB dbA = null ; // new AccessDB() ;
                    HsqlDB dbH = new HsqlDB();
                    
                    dbH.importData( dbA ) ;
                    
                }
                else if ( vArgs.contains("-iD") ) {
                    int idx = vArgs.indexOf("-iD") ;
                    String sDateTo = (String) vArgs.get( idx + 1 ) ;
                    String dbfilename = (String) vArgs.get(idx + 2 ) ;
                    
                    if ( sDateTo == null || ! sDateTo.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")) {
                        throw new Exception("Inserire la data nel formato dd/mm/yyyy.") ; 
                    }
                    FormattedDate dateTo = new FormattedDate(sDateTo) ;    
                    
                    ImportManager.importTo( dateTo , dbfilename ) ;
                    
                    
                }
                else if ( vArgs.contains("-buildStorico") ) {
                	HsqlDB db = new HsqlDB() ;
                    StoricoManager.updateStorico(db) ;
                    db.shutdown();
                }
            } catch ( Exception e ) {
                if ( e.getMessage() != null && e.getMessage().equals("HLP") ) 
                    ShowHelp(vArgs) ;
                else { 
                    e.printStackTrace();
                    System.out.println("\n\n\n-----------------------------------------------------------");
                    ShowHelp(vArgs);
                }
            }
        }
        else {
            //StartApp();
        }
            
    }
    
    private static void StartApp() throws Exception {
        //Starter Front End
        RifiutiFrame app = new RifiutiFrame();
        //app.start();
   }
    
   private static void ShowHelp( Vector args ) {
       System.out.println( args.get(0) + ":\n\n\tPer eseguire l'applicazione lanciarla senza parametri." );
   }
   
   private static void ExecuteSqlFile( String filename ) {
       
   }
}
