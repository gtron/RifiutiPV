/*
 * Created on 21-apr-2006
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.rifiuti.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.gtsoft.rifiuti.data.sql.SQLStoricoMovimenti;
import com.gtsoft.rifiuti.front.RifiutiFrame;
import com.gtsoft.rifiuti.front.data.AbstractSwg;
import com.gtsoft.rifiuti.superdata.IListable;
import com.gtsoft.utils.FormattedDate;
import com.gtsoft.utils.superdata.IDatabase;
/**
 * @author Gtron - 
 * 
 */
public class StoricoMovimenti implements IListable  {

    private FormattedDate giorno ;
    private Integer idMerce ;
    private Rifiuto rifiuto ;
    private Integer caricato ;
    private Integer scaricato ;
    private Integer giacenza ;
    private Integer totCaricato ;
    
    public Integer getCaricato() {
        return caricato;
    }
    public void setCaricato(Integer caricato) {
        this.caricato = caricato;
    }
    public Integer getGiacenza() {
        return giacenza;
    }
    public void setGiacenza(Integer giacenza) {
        this.giacenza = giacenza;
    }
    public FormattedDate getGiorno() {
        return giorno;
    }
    public void setGiorno(FormattedDate giorno) {
        this.giorno = giorno;
    }
    public Integer getIdMerce() {
        return idMerce;
    }
    public void setIdMerce(Integer idMerce) {
        this.idMerce = idMerce;
    }
    public Integer getScaricato() {
        return scaricato;
    }
    public void setScaricato(Integer scaricato) {
        this.scaricato = scaricato;
    }
    public Integer getTotCaricato() {
        return totCaricato;
    }
    public void setTotCaricato(Integer c) {
        this.totCaricato = c;
    }
    
    public Rifiuto getRifiuto() throws Exception {
        if ( rifiuto == null || rifiuto.getId() != idMerce.intValue() ) {
           rifiuto = Rifiuto.get(idMerce.toString()) ;
        }
        return rifiuto ;
    }
    
    public StoricoMovimenti() {
        caricato = scaricato = giacenza = Integer.valueOf(0);
    }
    public Integer addGiacenza( int val ) {
        
        giacenza = Integer.valueOf( giacenza.intValue() + val ) ; 
            
        return giacenza ;
    }
    
    public Integer addScarico( Double val ) {
        
        scaricato = Integer.valueOf( scaricato.intValue() + val.intValue() ) ; 
            
        return scaricato ;
    }
    public Integer addCarico( Double val ) {
        
        caricato = Integer.valueOf( caricato.intValue() + val.intValue() ) ; 
            
        return caricato ;
    }
    
    
    
    
    
    public HashMap getGiacenzaAttuale() {
        return null ;
    }
    
    public static StoricoMovimenti getLast( IDatabase db ) {
        try {
            return new SQLStoricoMovimenti(db).getLast() ;
        } catch (Exception e) {
            return null ;
        }
    }
    
    public static Vector getLastStorici( IDatabase db ) {
        try {
            return new SQLStoricoMovimenti(db).getLastStorici() ;
        } catch (Exception e) {
            return null ;
        }
    }
    
    
    public static Vector get( Integer merce , FormattedDate from , FormattedDate to, IDatabase db ) {
        try {
            return new SQLStoricoMovimenti(db).get(merce, from, to) ;
        } catch (Exception e) {
            e.printStackTrace();
            return null ;
        }
    }
    
    public static Vector getAll( String anno, IDatabase db) {
        return get( null , 
                new FormattedDate("01-01-" + anno + "00:00:00"), 
                new FormattedDate("31-12-" + anno + "23:59:59"), db );
    }
    
//    public static void main(String[] args) throws Exception {
//        
//        HsqlDB db = new HsqlDB() ;
//        
//        /* build 
//        Vector list = Movimento.getAll(db, "2006");
//        Collections.reverse(list);
//        HashMap storico = StoricoMovimenti.buildStorico(list, null);
//        
//        HashMap rifiuti = null ;
//        for ( Iterator i = storico.values().iterator() ; i.hasNext() ;) {
//            rifiuti = (HashMap) i.next();
//            
//            for ( Iterator r = rifiuti.values().iterator() ; r.hasNext() ;) {
//                ((StoricoMovimenti) r.next()).create(db);
//            }
//        }
//        */
//        
//        Vector list = get(Integer.valueOf(176), null, null, db) ;
//        
//        JFrame f = new JFrame() ;
//        
//        JFreeChart chart = buildChartGiacenze(list) ;
//
//        ChartPanel pnl = new ChartPanel(chart);
//        
//        f.add(pnl);
//        f.pack();
//        f.setVisible(true);
//        
//        db.shutdown();
//        
//    }
    
    
    
    
    // DA RIVEDERE ?
    public static HashMap _buildDay_ ( Vector list , FormattedDate day , HashMap giornoPrecedente ) throws Exception {
        
        HashMap out = new HashMap();
        
        Movimento m = null ;
        StoricoMovimenti s = null ;
        Integer idMerce = Integer.valueOf(m.getMerce().getCodiceQuadrelli())  ;
        
        for ( Iterator i = list.iterator() ; i.hasNext() ; ) {
            m = (Movimento) i.next() ;
            
            if ( out.containsKey( idMerce )) {
               s = (StoricoMovimenti) out.get(idMerce) ; 
            }
            else {
	            s = new StoricoMovimenti() ;
	            s.setGiorno(day);
	            s.setIdMerce(idMerce) ;
	            out.put(idMerce, s) ;
	            
	            if ( giornoPrecedente!= null && giornoPrecedente.containsKey(idMerce)) {
	                s.setGiacenza( ((StoricoMovimenti) giornoPrecedente.get(idMerce)).getGiacenza() ) ;
	            }
            }
            
            if ( m.isScarico() || m.isParziale() ) {
                s.addScarico(m.getPeso()) ;
            }
            else {
                s.addCarico(m.getPeso()) ;
            }        
        }
        
        return out ;
    }
    
    public void create( IDatabase db ) throws Exception {
        SQLStoricoMovimenti sql = new SQLStoricoMovimenti(db);

        sql.syncFields(this) ;
        sql.create() ;
    }
    
    public static void flush( IDatabase db ) throws Exception {
        new SQLStoricoMovimenti(db).flush() ;
    }

    private static final String[] columns = { 
            "DATA",
            "CARICATO",
            "TOT.CARICATO",
            "SCARICATO",
            "GIACENZA"
            };
    public String[] getColumns () { 
        return columns ;
    }

    public String[] getValues() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    private Vector vValues = null ;
    public Vector getVValues() throws Exception {
        
        vValues = new Vector(5);
        
        vValues.add(getGiorno().dmyString());
        vValues.add(getCaricato());
        vValues.add(getTotCaricato());
        vValues.add(getScaricato());
        vValues.add(getGiacenza());
        
        return vValues;
    }

    public AbstractSwg getSwg(RifiutiFrame frame) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
}
