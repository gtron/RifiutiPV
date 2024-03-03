/*
 * Created on 23-ott-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.data;

import java.util.HashMap;
import java.util.Vector;

import com.gtsoft.rifiuti.data.sql.SQLCarico;
import com.gtsoft.rifiuti.data.sql.SQLCaricoParziale;
import com.gtsoft.utils.FormattedDate;
import com.gtsoft.utils.superdata.IDatabase;

/**
 * @author Gtron -
 *  
 */
public class CaricoParziale extends Carico implements Cloneable {

    private int idCarico;

    private Carico carico;

    public Carico getCarico() {
        return carico;
    }

    public void setCarico(Carico carico) {
        this.carico = carico;
    }

    public int getIdCarico() {
        return idCarico;
    }

    public void setIdCarico(int idCarico) {
        this.idCarico = idCarico;
    }

    public boolean isGiacenza() {
        return getDescrizione().equals(DESCRIZIONI.GIACENZA);
    }

    public CaricoParziale cloneMe() throws CloneNotSupportedException {
        CaricoParziale c = (CaricoParziale) super.clone();
        c.setId(null);
        return c;
    }
    
    public Movimento getMovimento() {
        if ( movimento == null || movimento.isScarico() ) {
            movimento = Movimento.getForCarico( this.idCarico ) ;
        }
        return movimento;
    }


    public void update() throws Exception {
        new SQLCaricoParziale().update(this);
    }

    public void create() throws Exception {
        new SQLCaricoParziale().create(this);
    }
    public void createSetID() throws Exception {
        new SQLCaricoParziale().createSetID(this);
    }
    
    public static void pushGiacenze(Vector list) throws Exception {
        new SQLCaricoParziale().pushGiacenze(list);
    }

    public static void pushScorie(Vector list) throws Exception {
        new SQLCaricoParziale().pushScorie(list);
    }

    public static Vector getSommeGiacenzePerGiorno(FormattedDate from)
            throws Exception {
        return new SQLCaricoParziale().getSommeGiacenzePerGiorno(from);
    }

    public static Vector getSommeScoriePerGiorno(FormattedDate from)
            throws Exception {
        return new SQLCaricoParziale().getSommeScoriePerGiorno(from);
    }

    public static Vector getSommePerMerce(int anno) throws Exception {
        return new SQLCaricoParziale().getSommePerMerce(anno);
    }

    public static Vector getByMerceGiorno(String merce, FormattedDate giorno,
            boolean isScoria) throws Exception {
        return new SQLCaricoParziale()
                .getByMerceGiorno(merce, giorno, isScoria);
    }

    public static Vector getByCarico(Carico c) throws Exception {
        return new SQLCaricoParziale().getByCarico(c);
    }

    public static void scarica(int id, Movimento movScarico) throws Exception {
        new SQLCaricoParziale().scarica(id, movScarico);
    }
    
    public static CaricoParziale getLast( IDatabase db ) throws Exception {
    	return new SQLCaricoParziale(db).getLast() ;
    }

    private static HashMap cache = new HashMap(9973) ; // 14983 
    public static void clearCache() {
        cache.clear() ;
    }
    public static CaricoParziale getById(int id) {
        try {
            return new SQLCaricoParziale().getById(id);
        } catch (Exception e) {
            return null;
        }
    }

    public static double getPesoForScarico(Movimento m) throws Exception {
        return new SQLCaricoParziale().getPesoForScarico(m);
    }

    public void refreshImport(IDatabase from, IDatabase to) throws Exception {
    	
        Carico c = new SQLCarico(from).getByProgressivoAndAnno(this.getNumeroProgressivo(), this.getData());

        SQLCaricoParziale sql = new SQLCaricoParziale(to);
        Rifiuto r = c.getRifiuto();
        if (r.getCodiceQuadrelliPadre() != null
                && r.getCodiceQuadrelliPadre().length() > 0) {
            c.setMerce(Rifiuto.get(r.getCodiceQuadrelliPadre()));
        }
        sql.syncFields(this);
        
        c.setNetto(this.getNetto());
        
        sql.syncFields(c);

        
        sql.updateByProgressivo(this.getNumeroProgressivo(), c.getData());
        sql.fillFromFields(this);

//        Rifiuto.clearCache();
        to.checkpoint();

    }

    public static interface DESCRIZIONI {
        public static final String GIACENZA = "###GIACENZA###";

        public static final String SCORIE = "###SCORIE###";
    }
}
