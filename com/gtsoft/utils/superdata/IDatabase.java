/* 
 * Created on 29-giu-2005
 */
package com.gtsoft.utils.superdata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Gtron
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class IDatabase {

	protected Connection conn = null ;
	protected Statement stmt = null ;


	protected static final int ONLYONCE = 0 ;
	protected static final int KEEPALIVE = 1 ;

	public abstract Connection getConnection() throws Exception  ;

	public ResultSet executeQuery(String sql) throws Exception {

		ResultSet rs;
		try {
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(sql);

			return rs ;
		}
		catch ( Exception ex ){
			System.out.println("Errore in ExecuteQuery \n QUERY : \n" + sql + "\n\n");
			ex.printStackTrace();
			throw ex ;
		}
	}

	public int executeNonQuery(String sql) throws Exception {

		int rs;
		try {
			if( conn == null) start();

			Statement stmt = getConnection().createStatement();
			rs = stmt.executeUpdate(sql);
			stmt.close();

			return rs ;
		}
		catch ( Exception ex ){
			System.out.println("Errore in ExecuteNonQuery \n SQL : \n" + sql + "\n\n");
			ex.printStackTrace() ;
			throw new Exception("Errore in ExecuteNonQuery \n SQL : \n" + sql + "\n\n", ex) ;
		}
	}

	// public ResultSet doSelect( String table , DbValue[] fields , DbValue[] keys )

	public ResultSet executeSelect( String table, String[] fields, String[] whereFields , String[] whereValues ) throws Exception {

		if( conn == null) start();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ") ;
		if ( fields != null ) {
			int i = 0;
			for ( ; i < fields.length - 1; i++ ) {
				sql.append( fields[i] ); sql.append(",");
			}
			sql.append( fields[i] );
		}
		else {
			sql.append("*") ;
		}

		sql.append(" FROM ") ; sql.append( table );

		if ( whereFields != null ) {
			int w = 0;
			for ( ; w < whereFields.length - 1; w++ ) {
				sql.append( whereFields[w] );
				sql.append( " = ? "); sql.append(" AND ");
			}
			sql.append( whereFields[w] ); sql.append( " = ? ");
		}


		stmt = conn.prepareStatement( sql.toString());

		if( whereValues == null || whereValues.length != whereFields.length ) {
			throw new Exception( "Attenzione il numero dei parametri per la clausola WHERE non combacia con il numero dei relativi valori");
		}
		else {
			for ( int l = 0 ; l < whereValues.length ; l++ ){
				((PreparedStatement) stmt).setObject(l, whereValues[l] );
			}
		}

		return ((PreparedStatement) stmt).executeQuery(); 

	}

	public void start() throws Exception {
		if ( conn == null )
			conn = getConnection();
	}


	public void end() throws Exception {
		if ( stmt != null )
			stmt.close();
		if ( conn != null && ! conn.isClosed() ) {
			// executeNonQuery("shutdown;");
			conn.close();
		}
	}

	public void shutdown() throws Exception {
		end() ;
	}
	public void shutdown( boolean doCompact ) throws Exception {
		end() ;
	}


	public boolean isDbType( int type ) {
		return ( getDbType() ==  type );
	}

	public abstract int getDbType() ; 

	public static final class DBType {
		public final static int HSQL = 1 ;
		public final static int ACCESS = 2 ;
	}

	public abstract void export_database() throws Exception;

	public abstract void export_database(String suffix) throws Exception;

	public void startTransaction() throws Exception {
		conn.setAutoCommit(false);
	}
	public void commit() throws Exception {
		conn.commit();
		conn.setAutoCommit(true);
	}

	public void rollback() {
		try {
			conn.rollback();
			conn.setAutoCommit(true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	 public void checkpoint() {

	}

	public void backup(String comment) {
		System.out.println("IDatabase.backup : Implement this function in your database layer !") ;
	}

	public void changeYear(int year) throws Exception {
		System.out.println("IDatabase.changeYear : Implement this function in your database layer !") ;
	}

	public void backupYear( String anno ) throws Exception { 
		System.out.println("IDatabase.backupYear : Implement this function in your database layer !") ;
	}

	public void restore(String filename) throws Exception {
		System.out.println("--------------------");
		System.out.println("IDatabase.restore(String) : This function has to be implemented by derived Classes");
		return  ;
	}

	public void restoreLast() throws Exception {
		System.out.println("--------------------");
		System.out.println("IDatabase.restoreLast() : This function has to be implemented by derived Classes");
		return  ;
	}
}
