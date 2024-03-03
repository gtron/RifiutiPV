/*
 * Created on 8-lug-2005
 *
 *	This is not free software - All rights reserved
 */

package com.gtsoft.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author Gtron - 
 * 
 */
public class FormattedDate extends Date {
    
    private final static String dmyDATEFORMAT = "dd/MM/yyyy";
    private final static String ymdDATEFORMAT = "yyyy-MM-dd";
    private final static String hmsDATEFORMAT = "HH:mm:ss";
    private final static String yearDATEFORMAT = "yyyy";
    
    private final static String fullDATEFORMAT = "yyyy-MM-dd HH:mm:ss";
    
    protected SimpleDateFormat formatter = null ;
    
    protected static SimpleDateFormat dmyFormatter = null ;
    protected static SimpleDateFormat ymdFormatter = null ;
    protected static SimpleDateFormat hmsFormatter = null ;
    protected static SimpleDateFormat yearFormatter = null ;
    
    protected static SimpleDateFormat fullFormatter = null ;
    
    private static boolean doInit = true ; 
    
    public FormattedDate() {
        super();
        setTime( (new Date()).getTime() );
        init() ;
    }
     
    public FormattedDate( Object o ) {
        super();
        init() ;
        setTime( ((Date) o).getTime() );
    }
    
    private void init() {
       
       formatter = new SimpleDateFormat(fullDATEFORMAT);
       if ( doInit ) { 
            dmyFormatter = new SimpleDateFormat(dmyDATEFORMAT) ; 
            ymdFormatter = new SimpleDateFormat(ymdDATEFORMAT) ;
            hmsFormatter = new SimpleDateFormat(hmsDATEFORMAT) ;
            fullFormatter = new SimpleDateFormat(fullDATEFORMAT) ;
            yearFormatter = new SimpleDateFormat(yearDATEFORMAT) ;
        
            
            
            doInit = false ;
       }
    }
    
    public FormattedDate( String s , String format ) {
        init() ;
        Date d = new Date() ;
        try {
        	formatter = new SimpleDateFormat(format) ;
            d = formatter.parse(s);
            setTime( d.getTime() );
        }
        catch ( Exception e ) {
        	e.printStackTrace() ;
        }
    }
    public FormattedDate( String s ) {
       init() ;
       Date d = new Date() ;
       try {
           d = fullFormatter.parse(s);
           setTime( d.getTime() );
       }
       catch ( Exception e ) {
           try {
               d = ymdFormatter.parse(s);
               setTime( d.getTime() );
           }
           catch ( Exception ex ) {
               
	            try {
                    d = dmyFormatter.parse(s);
                       setTime( d.getTime() );
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
               }
           }
       }
       
     
    
    public String fullString() {
        return fullFormatter.format(this) ;
    }
    
    public String hmsString() {
        return hmsFormatter.format(this) ;
    }
    
    public String dmyString() {
        return dmyFormatter.format(this) ;
    }
    
    public String ymdString() {
        return ymdFormatter.format(this) ;
    }
    
    public String toString() {
        //return formatter.format(this) ;
        return formatter.format( new Date( this.getTime() ) ) ;
    }
    
    public boolean equals(Object obj){
    	if (obj instanceof FormattedDate) {
			FormattedDate f = (FormattedDate) obj;
			if(f.dmyString().equals(this.dmyString()))
				return true;
		}
    	return false;
    }
    
    
    
    public SimpleDateFormat getdmyFormatter() {
        return dmyFormatter;
    }
    
    public void setFormatter(SimpleDateFormat f) {
        this.formatter = f ;
    }
    
    public void setFormat( String format ) {
        formatter = new SimpleDateFormat(format) ;
    }

    public String getAnno() {
       return yearFormatter.format(this) ;
    }
}
