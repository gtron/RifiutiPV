package com.gtsoft.rifiuti.data;

import java.util.HashMap;
import java.util.Vector;

import com.gtsoft.rifiuti.data.sql.SQLCer;
import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.rifiuti.front.data.AbstractSwg;
import com.gtsoft.rifiuti.superdata.IListable;
import com.gtsoft.utils.superdata.IDatabase;

public class Cer implements IListable{

  private String codice;
  private String nome; 
 
  /**
   * Creates a new instance of Cer
   */
  public Cer() {
      this.codice = "0" ;
      this.nome = "N/A";
  }

  /**
   * Gets the current value of codice
   * @return Current value of codice
   */
  public String getCodice() {
    return codice;
  }

  /**
   * Sets the value of codice
   * @param codice New value for codice
   */
  public void setCodice(String codice) {
    this.codice=codice;
  }

  /**
   * Gets the current value of nome
   * @return Current value of nome
   */
  public String getNome() {
    return nome;
  }

  /**
   * Sets the value of nome
   * @param nome New value for nome
   */
  public void setNome(String nome) {
    this.nome=nome;
  }
  
  
  
  
  public String toString(){
      String nome=getNome();
      if(nome.length()>35)
          nome=nome.subSequence(0,35)+"...";
      return getCodice() + " - " + nome;
  }

  private static HashMap cache = new HashMap(20) ;
  public static Cer get( String codice ) throws Exception {
      if ( ! cache.containsKey(codice) ) {
          cache.put( codice, new SQLCer().get( codice ) );
      }
      return (Cer) cache.get(codice) ;
  }
  
  public static Vector getAll( IDatabase db ) {
      try {
        return new SQLCer(db).getAll() ;
    } catch (Exception e) { 
        e.printStackTrace();
        return null ;
    }
  }

public String[] getColumns() {
    String[] list = { 
            "id",
            "nome"
            };
    return list ;
}

public String[] getValues() {
    String[] data = new String[getColumns().length] ;
    data[0] = getCodice() ;
    data[1] = getNome();  
    return data ;
}

public Vector getVValues() {
    Vector data = new Vector ( getColumns().length ) ;
    
    data.add( getCodice() );
    data.add( getNome() );  
    return data ;
}

public AbstractSwg getSwg(RifiutiFrame frame) throws Exception {
    return null;
}
public boolean equals(Object c){
	if (c instanceof Cer) 
		return this.getCodice().equals(((Cer) c).getCodice());
	return false;
	}
}

