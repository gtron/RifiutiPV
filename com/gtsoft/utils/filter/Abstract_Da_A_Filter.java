package com.gtsoft.utils.filter;

import java.util.Date;

import javax.swing.JButton;

import com.gtsoft.rifiuti.front.Da_A_JFiltroPanel;
import com.gtsoft.rifiuti.front.JFiltroPanel;


public abstract  class Abstract_Da_A_Filter extends AbstractMatchFilter{

    
    protected static final int DOUBLE=0;
    protected static final int INTEGER=1;
    protected static final int STRING=2;
    
    protected Comparable da;
    protected Comparable a; 
    protected int parse=STRING;

    protected JButton button;
    
    public void setA(String a) {
    	
	    	if(parse==DOUBLE)
		    			this.a= new Double(a);    
		    else 	if(parse==INTEGER)
				    	this.a= new Integer(a);
		    else	if(parse==STRING)
		    			this.a=a;
		    else
		    	this.a=a;
	 }
    
    public void setDa(String da) {
    	if(parse==DOUBLE)
			this.da=new Double(da);    
		else 	if(parse==INTEGER)
			    	this.da=new Integer(da);
		else	if(parse==STRING)
					this.da=da;
		else
			this.da=da;
    }
    
    public void setJFiltroPanel(JFiltroPanel df) throws Exception {
        if (df instanceof Da_A_JFiltroPanel) {
            super.setJFiltroPanel(df);
            return;
        }
        else throw new Exception();
    }
    
    public boolean check( Object d ) {
        return comparableCheck((Date) d) ;
    }
    
    protected boolean comparableCheck( Comparable c) {
    	  if(a.compareTo(c)>=0 && da.compareTo(c)<=0)
        	  return true;
          else
        	  return false;
    }

	public void setParse(int parse) {
		this.parse = parse;
	}
}

