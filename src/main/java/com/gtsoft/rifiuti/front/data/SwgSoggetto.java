/*
 * Creato il 8-lug-2005
 * 
 * Autore: Lomy
 * 
 * nome del progetto:rifiuti
 *  
 */
package com.gtsoft.rifiuti.front.data;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.JDialog;

import com.gtsoft.rifiuti.data.Soggetto;
import com.gtsoft.rifiuti.front.JMainTable;
import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.rifiuti.front.data.detailedPanel.Soggetto_detailed_Dialog;
import com.gtsoft.rifiuti.superdata.IListable;
import com.gtsoft.utils.filter.FilterManager;
import com.gtsoft.utils.filter.superdata.IFilter;
import com.gtsoft.utils.filter.superdata.IFiltrable;

public class SwgSoggetto extends AbstractSwg implements IFiltrable{



    private final static String RAGIONE_SOCIALE="Ragione Sociale";
    private final static String CAP="CAP";
    private final static String CODICE="Codice";
    private final static String CODICE_FISCALE="Codice Fiscale";
    private final static String EMAIL="eMail";
    private final static String INDIRIZZO="Indirizzo";
    private final static String LOCALITA="Località";
    private final static String PIVA="Partita Iva";
    private final static String PROVINCIA="Provincia";
    private final static String TELEFONO="Telefono";
    private final static String NOTE="Note";
    
    private final static String[] SORTATTRIBUTI = { 
            										//CODICE,
            										CODICE_FISCALE,
            										//PIVA,
            										RAGIONE_SOCIALE,
            										//INDIRIZZO,
            										//LOCALITA,
            										//CAP,
            										//PROVINCIA,
            										//TELEFONO,
            										//EMAIL,
            										//NOTE          
    };
    private static final class ATTRIBUTI {
        private static Hashtable ATTRIBUTI =null ;

        private static final List addValuesAttributo(boolean b, int i) {
            List values_attributi=new ArrayList();
            values_attributi.add(new Boolean(b));
            values_attributi.add(Integer.valueOf(i));
            return values_attributi;
        }

        private static final void setAttributi() {
            if(ATTRIBUTI==null)
                ATTRIBUTI=new Hashtable();
            ATTRIBUTI.put(CODICE, addValuesAttributo(false, 0));
            ATTRIBUTI.put(CODICE_FISCALE, addValuesAttributo(false, 1));
            ATTRIBUTI.put(PIVA, addValuesAttributo(false, 2));
            ATTRIBUTI.put(RAGIONE_SOCIALE, addValuesAttributo(false, 3));
            ATTRIBUTI.put(INDIRIZZO, addValuesAttributo(false, 4));
            ATTRIBUTI.put(LOCALITA, addValuesAttributo(false, 5));
            ATTRIBUTI.put(CAP, addValuesAttributo(true, 6));
            ATTRIBUTI.put(PROVINCIA, addValuesAttributo(false, 7));
            ATTRIBUTI.put(TELEFONO, addValuesAttributo(false, 8));
            ATTRIBUTI.put(EMAIL, addValuesAttributo(false, 9));
        }
        
        private static boolean isClickable(String attributo) {
            return ((Boolean)((List)(ATTRIBUTI.get(attributo))).get(0)).booleanValue();
        }
        
        private static int getIndex(String attributo) {
            try {
                return ((Integer) ((List) ATTRIBUTI.get(attributo)).get(1)).intValue();
            } catch (RuntimeException e) {
                // XXX Blocco catch generato automaticamente
                e.printStackTrace();
            }
            return 1;
        }
        
    }
    
    public String[] getSortAttributi() {
        return SORTATTRIBUTI;
    }
    
    
    public boolean attributo_isClickable(String attributo) {
        try {
            return ATTRIBUTI.isClickable(attributo);
        } catch (RuntimeException e) {
            // XXX Blocco catch generato automaticamente
            e.printStackTrace();
        }
        return false;
    }    
    
    public int attributo_getIndex(String attributo) {
        return ATTRIBUTI.getIndex(attributo);
    }     

    
    protected Soggetto soggetto;
    
    public SwgSoggetto(RifiutiFrame frame) throws Exception {
        ATTRIBUTI.setAttributi();
        this.frame = frame;
        /*
        this.elementi = getElenco_for_database();
        getJMainTable();
        refreshElencoJTable(); 
        setOggettoConcreto(((IListable)getElementi().get(0)));
        */
        setOggettoConcreto(new Soggetto());
    }
    
    public void setValues() {
        if (values == null)
            values = new Hashtable();
        values.put(RAGIONE_SOCIALE,"" + soggetto.getRagioneSociale());
        values.put(CAP,"" + soggetto.getCap());
        values.put(CODICE,"" + soggetto.getCodice()); 
        values.put(CODICE_FISCALE,"" + soggetto.getCodiceFiscale());
        values.put(EMAIL,"" + soggetto.getEmail()); 
        values.put(INDIRIZZO,"" + soggetto.getIndirizzo());
        values.put(LOCALITA,"" + soggetto.getLocalita()); 
        values.put(NOTE,"" + soggetto.getNote()); 
        values.put(PIVA,"" + soggetto.getPIva());
        values.put(PROVINCIA,"" + soggetto.getProvincia()); 
        values.put(TELEFONO,"" + soggetto.getTelefono());
    }
    
   

    public Vector getElenco_for_database() throws Exception {
        elementi =  Soggetto.getAll(frame.getDatabase());
        return elementi;
    }
    protected JMainTable jMainTableInitialize(){
        jMainTable = new JMainTable(this);
        return jMainTable;   
    }

    
    public IListable getElemento() {
        if(elemento==null)
            elemento=new Soggetto();
        return elemento;
    }
    
    public void setSoggetto(Soggetto soggetto) {
        this.soggetto = soggetto;
    }
    
    public Soggetto getSoggetto() {
        if (soggetto==null) 
            soggetto=new Soggetto();
        return soggetto;
    }
    
    public void setOggettoConcreto(IListable listable) {
        this.soggetto=(Soggetto)listable;
        
    }

    private FilterManager filterManager;
    public FilterManager getFilterManager() {
       if(filterManager==null)
           filterManager=new FilterManager(getElementi(),this);
       return filterManager;
    }


    public IFilter[] getFilters() {
        return null;
    }


    protected JDialog getDetailed_JDialog() {
        return new Soggetto_detailed_Dialog( getSoggetto() , getFrame() );
    }


	public void stampa() {
		
	}

	public IFilter getFilter(int column) {
		return null;
	}
}