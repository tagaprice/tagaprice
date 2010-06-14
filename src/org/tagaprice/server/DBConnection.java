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
			InputStream propStream = cl.getResourceAsStream("/jdbc.properties");
			if (propStream == null) {
				throw new FileNotFoundException("Error: Couldn't open property file 'jdbc.properties'");
			}

			prop.load(propStream);
			propStream.close();

			ds.setServerName(prop.getProperty("host", "localhost"));
			ds.setPortNumber(Integer.parseInt(prop.getProperty("port", "5432")));
			ds.setDatabaseName(prop.getProperty("db"));
			ds.setUser(prop.getProperty("user"));
			ds.setPassword(prop.getProperty("pwd"));
			ds.setSsl(prop.getProperty("ssl", "true").equals("true"));
			if (prop.containsKey("sslFactory")) {
				ds.setSslfactory(prop.getProperty("sslFactory"));
			}
			dataSource = ds;
		}
	}
	
	public void disconnect() throws SQLException {
		if (dbConn != null) {
			dbConn.close();
		}
	}
	
	public Connection getConnection() throws SQLException {
		if (dbConn == null) {
			dbConn = dataSource.getPooledConnection();
		}
		return dbConn.getConnection();
	}

	public PreparedStatement prepareStatement(String stmt) throws SQLException {
		return getConnection().prepareStatement(stmt, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	}
	
	public Statement createStatement() throws SQLException {
		return getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	}
	
	public void setAutoCommit(boolean value) throws SQLException {
		getConnection().setAutoCommit(value);
	}

	public void commit() throws SQLException {
		getConnection().commit();
	}
	
	public void rollback() throws SQLException {
		getConnection().rollback();
	}
}