/*
 * Created on 21-ago-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.data;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.gtsoft.rifiuti.data.sql.SQLMovimento;
import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.rifiuti.front.data.AbstractSwg;
import com.gtsoft.rifiuti.front.data.SwgMovimento;
import com.gtsoft.rifiuti.superdata.IListable;
import com.gtsoft.utils.FormattedDate;
import com.gtsoft.utils.superdata.IDatabase;


/**
 * @author Gtron - 
 * 
 */
public class Movimento implements IListable {

    public static final String DATA="data";
    public static final String NUMERO="Numero";
    public static final String TIPO="Tipo";
    public static final String PESO="Peso (Kg)";
    public static final String RIFIUTO="Rifiuto";
    public static final String TRASPORTATORE="Trasportatore";
    
    public static final String STAMPATO="";
    public static final String SCARICATO="";
    
    private int numProgressivo ;
    private int anno = 0 ;
    private FormattedDate data = new FormattedDate() ;
    private int tipo ;
    private double pesoTotale = -1 ;
    
    private String idTrasportatore ;
    private Vettore trasportatore = null ;    
    private Vector carichi = null ;
    private CaricoParziale caricoParziale = null ;
    private int idCaricoParziale = -1 ;
    
    private FormattedDate dataStampaRegistro = null ;
    private String merceOriginale = null ;
    private String note = null ;
    
    //private int idFormulario ;
    
    private SwgMovimento swg = null;
    
    public static DecimalFormat nformatter = new DecimalFormat("##,###,###");
      
      
    public int getAnno() {
        if ( anno < 1 ) {
            anno = Integer.valueOf( new FormattedDate().ymdString().substring(0,4) ) ;
        }
        return anno;
    }
    public void setAnno(int anno) {
        this.anno = anno;
    }
    public double getPesoTotale() {
        return pesoTotale;
    }
    public void setPesoTotale(double pesoTotale) {
        this.pesoTotale = pesoTotale;
    }
    public Vettore getTrasportatore() {
        if ( isScarico() ) {
	        if ( trasportatore == null && idTrasportatore != null && ! idTrasportatore.equals(""))
	            trasportatore = Vettore.getById(idTrasportatore);
	        return trasportatore;
        }
        else if ( isParziale() ) {
            CaricoParziale cp = getCaricoParziale() ;
            if ( cp != null )
                trasportatore = cp.getVettore() ;
            
            return trasportatore;
        }
        else 
            return getCarico().getVettore();
    }
    public void setTrasportatore(Vettore trasportatore) {
        idTrasportatore = "" + trasportatore.getId() ;
        this.trasportatore = trasportatore;
    }
    public String getIdTrasportatore() {
        return idTrasportatore;
    }
    public void setIdTrasportatore(String id) {
        this.idTrasportatore = id;
    }
    public Vector getCarichi() {
        if ( carichi == null ) {
        	if ( isParziale() ) {
                return carichi ;
        	}
        	else if ( isScarico() ) {
                carichi = new SQLMovimento().getCarichi( numProgressivo , anno ) ;
        	}

        }
        return carichi;
    }
    public void setCarichi(Vector carichi) {
        this.carichi = carichi;
    }
    public void setCarico( int carico ) {
        carichi = new Vector(1) ;
        Carico c = Carico.get( carico )  ;
        if ( c != null ) {
            //c.setMovimento(this) ;
            carichi.add( c ) ;
        }
    }
    public void setCarico( Carico carico ) {
        carichi = new Vector(1) ;
        carichi.add( carico ) ;
    }
    
    public int getIdCaricoParziale() {
        return idCaricoParziale;
    }
    public void setIdCaricoParziale(int idCaricoParziale) {
        this.idCaricoParziale = idCaricoParziale;
    }
    
    public FormattedDate getData() {
        return data;
    }
    public void setData(FormattedDate data) {
        this.data = data;
        this.anno = Integer.valueOf(data.getAnno()) ;
    }
    public FormattedDate getDataStampaRegistro() {
        return dataStampaRegistro;
    }
    public void setDataStampaRegistro(FormattedDate dataStampaRegistro) {
        this.dataStampaRegistro = dataStampaRegistro;
    }
    public int getNumProgressivo() {
        return numProgressivo;
    }
    public void setNumProgressivo(int numProgressivo) {
        this.numProgressivo = numProgressivo;
    }
    public SwgMovimento getSwg() {
        return swg;
    }
    public void setSwg(SwgMovimento swg) {
        this.swg = swg;
    }
    public String getTipo() {
        return TipoMovimento.readTipo(tipo) ;
    }
    public int getTipoAsInt() {
        return tipo  ;
    }
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    public boolean isScarico() {
        return TipoMovimento.isScarico(tipo) ;
    }
    public boolean isParziale() {
        return TipoMovimento.isParziale(tipo) ;
    }
    public boolean isScorie() {
        return TipoMovimento.isScorie(tipo) ;
    }
    public boolean isGiacenza() {
        return TipoMovimento.isGiacenza(tipo) ;
    }
    
    public boolean isCarico() {
        return TipoMovimento.isCarico(tipo) ;
    }
    public boolean isStampato() {
       return ( getDataStampaRegistro() != null ) ;
    }
    
    public Rifiuto getMerce() throws Exception {
        Vector carichi = getCarichi() ;
        if ( carichi != null && carichi.size() > 0 )
            return ((Carico)carichi.firstElement()).getMerce() ;
        else 
            return new Rifiuto() ;
    }
    
    public String getMerceOriginale() {
        return merceOriginale ;
    }
    public void setMerceOriginale(String s) {
        merceOriginale = s ;
    }
    
    public String getIntermediario() {
        try {
	        if ( merceOriginale != null && merceOriginale.length() > 0 )
	            return Rifiuto.get(merceOriginale).getIntermediario();
	        else 
	            return getMerce().getIntermediario();
        }
        catch ( Exception e ) {
            return "-";
        }
    }
    
    public String getNote() {
        return note ;
    }
    public void setNote(String s) {
        note = s ;
    }
    
    public String getFormulario() {
        
        if ( isScarico() ) {
            return "" ;
        }
        else if ( isParziale() && getCaricoParziale() != null ) {
            return getCaricoParziale().getDocumento();
        }
        else {
            return getCarico().getDocumento() ;
        }
    }
    
    public FormattedDate getDataFormulario() {
        
        if ( isScarico() )
            return null ;
        
        FormattedDate d = null ;
        
        if ( isCarico() ) {
            d = getCarico().getDataFormulario() ;
        }
        if ( isParziale() && idCaricoParziale > 0 ) {
            d = getCaricoParziale().getDataFormulario();
        }
        /*
         * 
        if ( d == null )
            d = getData();
        */
        
        return d;
    }
    
    public Carico getCarico() {
        try {
	        return (Carico)getCarichi().firstElement() ;
        }
        catch (Exception e) {
            return null ;
        }
    }
   
    public CaricoParziale getCaricoParziale() {
        if ( caricoParziale == null ) {
	        try {
	            caricoParziale = CaricoParziale.getById(idCaricoParziale) ;
	        }
	        catch (Exception e) {
	           e.printStackTrace();
	        }
        }
        return caricoParziale ;
    }
    
    public Double getPeso() {
        if ( pesoTotale < 0  && isParziale()) {
            if ( idCaricoParziale >= 0  )
                pesoTotale = getCaricoParziale().getNetto() ;
            else {               
                try {
                    pesoTotale = CaricoParziale.getPesoForScarico(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else {
	        if ( pesoTotale < 0  && getCarichi() != null ) {
	            pesoTotale = 0 ;
	            for ( Iterator i = carichi.iterator() ; i.hasNext() ; ) {
	                pesoTotale += ((Carico)i.next() ).getNetto() ;
	            }
	        }
        }
        return Double.valueOf( pesoTotale ) ;
    }
    
    private static final String[] columns = { 
            DATA,
            NUMERO,
            TIPO,
            PESO,
            /*STAMPATO,
            SCARICATO,*/
            RIFIUTO,
            TRASPORTATORE
            };
    public String[] getColumns () { 
        return columns ;
    }
      
      public String[] getValues() throws Exception {
          String[] data = new String[getColumns().length] ;
          data[0] = getData().dmyString() ;
          data[1] = "" + getNumProgressivo();
          data[2] =getTipo();
          data[3] = "" + getPeso() ;
         
          if(isStampato())
        	  data[4]="Si";
          else
        	  data[4]="No";
          
          if(isScarico())
        	  data[5]="";
          else if(getCarico().isScaricato())
        	  data[5]="Si";
          else
        	  data[5]="No";
          
          data[5] = getMerce().getNome() ;
          
          
          return data ;
      }
   
      private static Vector vValues = null ;
      public Vector getVValues() {
          if ( vValues == null ) {
              vValues = new Vector(getColumns().length );
          }
          
          vValues.clear();
          vValues.add( getData().dmyString() );
          vValues.add( "" + getNumProgressivo()) ;
          vValues.add( getTipo() ) ;
          
          vValues.add( "" + nformatter.format( getPeso() )) ;
          
          /*if(isStampato())
        	  vValues.add("Si");
          else
        	  vValues.add("No");
          
          
          if(isScarico())
        	  vValues.add("No");
          else 	if(getCarico().isScaricato())
        		  vValues.add("Si");
          else	vValues.add("No");*/
          
          try {
	          if ( getMerce() != null )
	              vValues.add( getMerce().getNome() ) ;
	          else
	              vValues.add( " - " ) ;
	          
	          if ( getTrasportatore() != null )
	              vValues.add( getTrasportatore().getRagioneSociale() ) ;
	          else 
	              vValues.add( "" ) ;

          }
          catch( Exception e ) {
              vValues.add( " - - " ) ;
          }
          return vValues ;
      }     
      
      
      private static HashMap cache = new HashMap(14983); // 24989
      public static Vector getAll( IDatabase db , String anno ) throws Exception {
    	  
    	  Vector list = new SQLMovimento(db).getAllByAnno( anno );
    	  Movimento m = null ;
    	  cache.clear();
    	  for ( Iterator i = list.iterator() ; i.hasNext() ; ) { 
    		m = (Movimento) i.next() ;
    		cache.put("" + m.getAnno() + m.getNumProgressivo(), m) ;
    	  }
    	  
          return list ;
      }

      public static void clearCache() {
          cache.clear() ;
      }

      public void create( IDatabase db ) throws Exception {
          SQLMovimento sql = new SQLMovimento(db);
          sql.syncFields(this) ;
          int num = sql.create( this ) ;
          this.numProgressivo = num ;
          
          cache.put("" + this.getAnno() + this.getNumProgressivo(), this ) ;
      }
      
      public void update ( IDatabase db ) throws Exception {
          SQLMovimento sql = new SQLMovimento(db);
          sql.syncFields(this) ;
          sql.update();
          
          cache.put("" + this.getAnno() + this.getNumProgressivo(), this ) ;
      }
      
      public AbstractSwg getSwg(RifiutiFrame frame) throws Exception{
          if(swg==null)
              swg=new SwgMovimento(frame, frame.getAnno());
          
          swg.setMovimento(this);
          
          return swg;
      }
      
      	
      /*public static Vector getCarichiDaScaricare(IDatabase db) throws Exception {
         return new SQLMovimento().getAllCarichiDaScaricare(db);
       }*/

//      public static Vector getMovimentiDaScaricare( Rifiuto o ) {
//          return new SQLCarico().getMovimentiDaScaricare( Rifiuto.getCodiciQuadrelli()) ;
//      }
//
//      public static Vector filterForRifiuto(String rifiuto) throws Exception {
//          return new SQLCarico().filterRifiuto(rifiuto);
//      }
//      
//      public static Vector filterForRifiuto(String[] rifiuto) throws Exception {
//          Vector v=new SQLCarico().filterRifiuto(rifiuto);
//          return v;
//      }
      
      
      
      public static class TipoMovimento {
          public static final int CARICO = 0 ;
          public static final int SCARICO = 1 ;
          public static final int SCORIE = 2 ;
          public static final int SCARICOPARZIALE = 3 ;
          public static final int GIACENZA = 4 ;
          
          public static final String STR_CARICO = "C" ;
          public static final String STR_SCARICO = "S" ;
          public static final String STR_SCORIE = "C" ;
          public static final String STR_PARZIALE = "S" ;
          public static final String STR_GIACENZA = "C" ;
          
          public static String readTipo( int t ) {
              switch ( t ) {
                  case SCARICOPARZIALE :
                      return STR_PARZIALE ;
                  case SCARICO :
                      return STR_SCARICO ;
                  case SCORIE :
                      return STR_SCORIE ;
                  case GIACENZA :
                      return STR_GIACENZA ;
                  default :
                      return STR_CARICO ;
              }
          }
          
          public static boolean isCarico( int t ) {
              return (t == CARICO) ;
          }
          public static boolean isScarico( int t ) {
              return (t == SCARICO) ;
          }
          public static boolean isParziale( int t ) {
              return ( t == SCARICOPARZIALE ) ;
          }
          public static boolean isScorie( int t ) {
              return ( t == SCORIE ) ;
          }
          public static boolean isGiacenza( int t ) {
              return ( t == GIACENZA ) ;
          }
      }


    
    public static Movimento getForCarico( int c ) {
        try {
            return new SQLMovimento().getForCarico(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }

    
    public void delete() { 
        
        new SQLMovimento().delete(this) ;
	        
        for ( Iterator i = getCarichi().iterator() ; i.hasNext() ;) {
            ((Carico) i.next()).setDataScarico(null);
        }
        cache.remove("" + this.getAnno() + this.getNumProgressivo()) ;
    }

    public boolean equals(Object obj){
    	
    	if (obj instanceof Movimento) {
			Movimento m = (Movimento) obj;
		
			if(m.getNumProgressivo()==getNumProgressivo())
				return true;
		}
    	
    	return false;
    }
    
    public void setStampatoRegistro() throws Exception {
		this.dataStampaRegistro = new FormattedDate();
		new SQLMovimento().setStampatoRegistro(this) ;
    }
    /**
     * @return
     */
    public static int countDaStampare() throws Exception {
        return new SQLMovimento().countDaStampare() ;
    }
    /**
     * @return
     */
    public String getNumeriProgressiviScaricati() {
        
        SQLMovimento sql = new SQLMovimento() ; //
        
        try {
	        if ( isScarico() ) {
	            Vector l = getCarichi() ;
	            String out = "" ;
	        	for (Iterator iter = l.iterator(); iter.hasNext();) {
	                Carico cc = (Carico) iter.next();
	                out += "" + cc.getMovimento().getNumProgressivo() + (iter.hasNext() ? ", " : "" ) ;
	            }
	        	return out ; 
	        }
	        else if ( isParziale() ) {
	                return sql.getNumProgressivoCarico(getCarico().getId().intValue() , getAnno()) ;
	        }
	        else { 
	            return "" ;
	        }
        } catch (Exception e1) {
            e1.printStackTrace();
            return "-" ;
        }
    }
    
    
    public static Vector get( FormattedDate from , FormattedDate to , IDatabase db ) {
        try {
            return new SQLMovimento( db).get( from,to );
        } catch (Exception e) {
            e.printStackTrace();
            return null ;
        }
    }
    public static Movimento getLast( IDatabase db ) throws Exception {
    	return new SQLMovimento(db).getLast() ;
    }
      
}