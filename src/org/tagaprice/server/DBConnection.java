/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/
package org.tagaprice.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;

import org.postgresql.ds.PGConnectionPoolDataSource;

public class DBConnection {
	// shared connection pool
	private static ConnectionPoolDataSource dataSource;
	private static boolean isTesting = false;
	private PooledConnection dbConn;
	
	/**
	 * constructor
	 * 
	 * Tries to get the DB configuration from the file jdbc.properties in the classpath.
	 * If jdbc.properties isn't found, jdbc_testing.properties is opened.  
	 * @throws FileNotFoundException if jdbc_testing.properties isn't found 
	 * @throws IOException if any other property-file related problem occurs
	 */
	public DBConnection() throws IOException, FileNotFoundException {
		_init();
	}
	
	/**
	 * initialize the static properties
	 * @throws FileNotFoundException if no property file is found
	 * @throws IOException if any other file access error occurs
	 */
	private static void _init() throws FileNotFoundException, IOException {
		if (dataSource == null)	{
			Properties prop = new Properties();

			PGConnectionPoolDataSource ds = new PGConnectionPoolDataSource();
			
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			InputStream propStream = cl.getResourceAsStream("conf/jdbc.properties");
			
			if (propStream == null) { // jdbc.properties not found => try jdbc_testing.properties
				propStream = cl.getResourceAsStream("conf/jdbc_testing.properties");
				if (propStream == null) throw new FileNotFoundException("Error: Couldn't open property file 'jdbc_testing.properties'");
			}
			prop.load(propStream);
			propStream.close();

			ds.setServerName(prop.getProperty("host", "localhost"));
			ds.setPortNumber(Integer.parseInt(prop.getProperty("port", "5432")));
			ds.setDatabaseName(prop.getProperty("db"));
			ds.setUser(prop.getProperty("user"));
			ds.setPassword(prop.getProperty("pwd"));
			dataSource = ds;
			isTesting = !prop.getProperty("testing").equals("false");
		}
	}
	
	/* (non-Javadoc)
	 * @see at.fakeroot.sepm.server.IDBConnection#disconnect()
	 */
	public void disconnect() throws SQLException {
		if (dbConn != null) {
			if (isTesting) {
				// rollback transaction
				Statement s = dbConn.getConnection().createStatement();
				s.execute("ROLLBACK");
			}
			dbConn.close();
		}
	}
	
	/* (non-Javadoc)
	 * @see at.fakeroot.sepm.server.IDBConnection#getConnection()
	 */
	public Connection getConnection() throws SQLException {
		if (dbConn == null) {
			dbConn = dataSource.getPooledConnection();
			if (isTesting) {
				// begin transaction
				Statement s = dbConn.getConnection().createStatement();
				s.execute("BEGIN");
			}
		}
		return dbConn.getConnection();
	}

	/* (non-Javadoc)
	 * @see at.fakeroot.sepm.server.IDBConnection#prepareStatement(java.lang.String)
	 */
	public PreparedStatement prepareStatement(String stmt) throws SQLException {
		return getConnection().prepareStatement(stmt, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	}
	
	/* (non-Javadoc)
	 * @see at.fakeroot.sepm.server.IDBConnection#createStatement()
	 */
	public Statement createStatement() throws SQLException {
		return getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	}
	
	/* (non-Javadoc)
	 * @see at.fakeroot.sepm.server.IDBConnection#setAutoCommit(boolean)
	 */
	public void setAutoCommit(boolean value) throws SQLException {
		if (!isTesting) getConnection().setAutoCommit(value);
	}

	/* (non-Javadoc)
	 * @see at.fakeroot.sepm.server.IDBConnection#commit()
	 */
	public void commit() throws SQLException {
		if (!isTesting) getConnection().commit();
	}
	
	/* (non-Javadoc)
	 * @see at.fakeroot.sepm.server.IDBConnection#rollback()
	 */
	public void rollback() throws SQLException {
		getConnection().rollback();
	}
	
	/* (non-Javadoc)
	 * @see at.fakeroot.sepm.server.IDBConnection#isTesting()
	 */
	public boolean isTesting() {
		return isTesting;
	}
	
	/**
	 * returns true if DBConnection is set to testing mode.
	 * 
	 * Testing mode can be enabled/disabled in jdbc.properties.
	 * If enabled, all queries are put into a transaction that is rolled back
	 * when the connection is released (using disconnect()).
	 * 
	 * This is the static version of the function which means it may have to initialize the database connection pool.
	 * That's why it's possible that this function throws Exceptions.
	 * 
	 * @return true: DBConnection is in testing mode, false otherwise
	 * @throws FileNotFoundException if the property file could not be read
	 * @throws IOException if any other file system related problem occurs
	 */
	public static boolean staticIsTesting() throws FileNotFoundException, IOException {
		_init();
		return isTesting;
	}
}