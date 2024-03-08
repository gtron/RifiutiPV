/*
 * Created on 3-lug-2005
 */
package com.gtsoft.utils;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.gtsoft.utils.superdata.IDatabase;

/**
 * @author Gtron
 */
public class DbValue {
	 
    public static final int NULL = 0 ;
	public static final int INTEGER = 1 ;
	public static final int LONG = 2 ;
	public static final int FLOAT = 3 ;
	public static final int DOUBLE = 4 ;
	public static final int STRING = 10 ;
	public static final int DATE = 50 ;
	public static final int DATETIME = 60 ;
	public static final int OBJECT = 99 ;
	
	private static final String DATEFORMAT = "yyyyMMdd" ;
//	private static final String DATETIMEFORMAT = "yyyyMMdd HH:mm:ss" ;
	
	private int type ;
	private Object value = null ;
	private String strValue ;
	private boolean isKey = false ;
	private boolean wasNull = false;
	private String fieldname ;
	
	private SimpleDateFormat formatter = null ;
	private String dateFormat = "" ;
	private TimeZone timezone ;
    private int dbType;
	

	public DbValue( String fieldname , Object val , int type ) throws Exception {
		this.fieldname = fieldname ;
		this.type = type ;
		setValue(val);
		
	}
	public DbValue( String fieldname ,  int type ) {
		//this.fieldname = "`" + fieldname + "`" ;
	    this.fieldname =  fieldname  ;
		this.type =type ;
	}
	/*
	public DbValue( String fieldname ) {
		this.fieldname = fieldname ;
		value = null;
		strValue = "" ;
		type = NUMBER ;
	}
	public DbValue( String fieldname , int i ) {
		this.fieldname = fieldname ;
		value = Integer.valueOf(i) ;
		strValue = value.toString() ;
		type = NUMBER ;
	}
	public DbValue( String fieldname , long i ) {
		this.fieldname = fieldname ;
		value = new Long(i) ;
		strValue = value.toString() ;		
		type = NUMBER ;
	}
	public DbValue( String fieldname , String s ) {
		this.fieldname = fieldname ;
		setValue(s);
	}
	public DbValue( String fieldname , Date d ) {
		this.fieldname = fieldname ;
		value = d ;
		type = DATE ;
	}
	public DbValue( String fieldname , Date d , String format ) {
		this.fieldname = fieldname ;
		dateFormat = format ;
		
		value = d ;
		type = DATE ;
	}
	public DbValue( String fieldname , Date d , String format , String locale ) {
		this.fieldname = fieldname ;
		timezone = TimeZone.getTimeZone(locale) ;
		dateFormat = DATEFORMAT ;
		value = d ;
		type = DATE ;
	}
	*/
	public String toString() {
		switch ( type ) {
			case DATE :
			    if ( value == null  || wasNull ) return " null " ;
				formatter = new SimpleDateFormat( dateFormat ) ;
				formatter.setTimeZone( timezone );
				
				if ( this.dbType == IDatabase.DBType.ACCESS )
				    return  "#" + value.toString() + "#" ;
				else
				    return  "'" + value.toString() + "'" ; 
				
			case STRING :
			    if ( value == null  || wasNull ) return "''" ;
			    return "'" + strValue + "'";
			
			case OBJECT :
			    if ( value == null  || wasNull ) return " null " ;
			    return (String) strValue ;
			    
			default :
			    if ( value == null  || wasNull ) return "null" ;
				return strValue ; 
		}
	}
	
	public Object getValue() {
	    return value ;
	}
	public void setValue( Object val ) throws Exception {
	    if ( val != null ) {
	        switch ( type ) { 
	        case DATE :
	            value = new FormattedDate( val );
	            formatter = new SimpleDateFormat( getDateFormat() ) ;
	            formatter.setTimeZone( timezone );
	            strValue = "'" + value.toString() + "'" ; 
	            break ;
	            
	        case INTEGER :
	            value = (Integer) val ;
	            strValue = ((Integer) value).toString() ;
	            break;
	            
	        case LONG :
	            value = (Long) val ;
	            strValue = ((Long) value).toString() ;
	            break;
	            
	        case DOUBLE :
	            value = (Double) val ;
	            strValue = ((Double) value).toString() ;
	            break;
	            
	        case FLOAT :
	            value = (Double) val ;
	            strValue = ((Double) value).toString() ;
	            break;
	            
	        case OBJECT :
	            value = val ;
	            strValue = value.toString();
	            break ;
	            
	        default :
	            value = ((String) val).trim() ;
	        strValue = getSanitizedString() ;
	        value = strValue ; 
	        }
	    }
	    else
	        setWasNull(true);
	}
	
	public void setValue( String val ) throws Exception {
	   
	    if ( val != null && val.length() > 0 ) {
	        
	        switch ( type ) {
	        case DATE :
	            value = new FormattedDate( val );
	            strValue = "'" + ((FormattedDate) value).ymdString() + "'" ;
	            break ;
	            
	        case INTEGER :
	            value =  Integer.valueOf( val );
	            strValue = ((Integer) value).toString() ;
	            break;
	            
	        case LONG :
	            value = new Long( val );
	            strValue = ((Long) value).toString() ;
	            break;
	            
	        case DOUBLE :
	            value = Double.valueOf( val );
	            strValue = ((Double) value).toString() ;
	            break;
	            
	        case FLOAT :
	            value = new Float( val );
	            strValue = ((Float) value).toString() ;
	            break;
	            
	        case OBJECT :
	            value = val ; 
	            strValue = val.toString() ;  
	            break ;
	            
	        default :
	            value = ((String) val).trim() ;
	        strValue = getSanitizedString() ;
	        value = strValue ; 
	        }
	    }
        else { 
            value = null ;
            strValue = "''" ;
        }
	}

	public void setValue( int val ) throws Exception {
	    setValue( Integer.valueOf( val ) ) ;
	}
	public void setValue( double val ) throws Exception {
	    setValue( Double.valueOf( val ) ) ;
	}

	public String getFieldName( ) {
		return fieldname ;
	}

	private String getSanitizedString() {
		String s = "" + (String) value ;
		s.trim();
		s = s.replaceAll("'", "''"); 
		return s ;
	}
    /**
     * @return Restituisce il valore di isKey.
     */
    public boolean isKey() {
        return isKey;
    }
    /**
     * @param isKey Imposta il valore per isKey .
     */
    public void setKey(boolean isKey) {
        this.isKey = isKey;
    }
    
    private String getDateFormat() {
        if ( dateFormat.equals("") )
            dateFormat = DATEFORMAT ;
        
        return dateFormat ;
    }
    
    public int getType() {
        return type ;
    }
    public boolean wasNull() {
        return wasNull ;
    }
    public void setWasNull(boolean wasNull) {
        this.wasNull = wasNull;
    }
    /**
     * @param dbType
     */
    public void setDbType(int dbType) {
        this.dbType = dbType ;
    }
}
