/*
 * Created on 3-lug-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gtsoft.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** 
 * @author andrea
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RifiutiDate extends Date {
	
    private static DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
    
	/**
	 * @param i
	 * @param j
	 * @param k
	 */
	public RifiutiDate(int i, int j, int k) {
	    
	}

	/**
	 * @param data
	 */
	public RifiutiDate(String data) {
		this.setDate(data);
		
	}

	/**
	 * 
	 */
	public RifiutiDate() {
		
	}

	public  void setDate(String date){
		
	    
	    /*
		this.setDate(Integer.parseInt(date.substring(0,2)));
		this.setMonth(Integer.parseInt(date.substring(3,5)));
		this.setYear(Integer.parseInt(date.substring(6,8)));
		*/
	
        try {
            this.setTime( f.parse( date ).getTime() ) ;
        } catch (ParseException e) {
            this.setTime(1);
        }
    
		
	}
	
	
	private String parseAtDate(int day){
		String sDay;
		if(day>0&&day<10)
			sDay="0"+String.valueOf(day);
		else
			sDay=String.valueOf(day);
		return sDay;
		
	}
	
	public String getString(){
		
	    return f.format(this);
	    
	}

	public static void main(String[] args) {
		RifiutiDate pre=new RifiutiDate();
		pre.setDate("12/07/70");
		RifiutiDate post=new RifiutiDate("12/07/69");
		System.out.println(pre.compareTo(new RifiutiDate("12/07/70")));
	}

}
