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
import org.tagaprice.server.utilities.PropertiesFileLoader;

/**
 * Wrapper around a database connection.
 * This class cares about setting up the connection using the jdbc.properties file and pools it, once created.
 * TODO refactor/check transaction-management in this class
 */
public class DBConnection {
	// shared connection pool
	private static ConnectionPoolDataSource s_dataSource;
	private PooledConnection _dbConn;
	protected int _transactionDepth = 0;

	/**
	 * Constructor that tries to get the DB configuration from the file jdbc.properties in the classpath.
	 * 
	 * @throws FileNotFoundException
	 *             if jdbc_testing.properties isn't found
	 * @throws IOException
	 *             if any other property-file related problem occurs
	 */
	public DBConnection() throws IOException, FileNotFoundException {

		// init static fields
		if (DBConnection.s_dataSource == null) {

			PGConnectionPoolDataSource ds = new PGConnectionPoolDataSource();
			applyProperties(ds, "jdbc.properties");
			DBConnection.s_dataSource = ds;
		}
	}


	/**
	 * Disconnect from the database (close the connection).
	 * 
	 * @throws SQLException
	 *             if a database access error occurs.
	 */
	public void disconnect() throws SQLException {
		if (_dbConn != null) {
			_dbConn.close();
		}
	}

	/**
	 * Gets a preconfigured connection to the database.
	 * 
	 * @return the cached connection, or a new connection from the pool
	 * @throws SQLException
	 *             if a database access error occurs.
	 */
	public Connection getConnection() throws SQLException {
		if (_dbConn == null) {
			_dbConn = DBConnection.s_dataSource.getPooledConnection();
		}
		return _dbConn.getConnection();
	}

	/**
	 * Gets a {@link PreparedStatement} from the preconfigured connection.
	 * 
	 * @param stmt
	 *            the statement to prepare
	 * @return a {@link PreparedStatement} generated from the preconfigured connection.
	 * @throws SQLException
	 *             if a database access error occurs, this method is called on a closed connection or the given
	 *             parameters are not ResultSet constants indicating type and concurrency
	 */
	public PreparedStatement prepareStatement(String stmt) throws SQLException {
		return getConnection().prepareStatement(stmt, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	}

	/**
	 * Gets a {@link Statement} from the preconfigured connection.
	 * 
	 * @return a {@link Statement} from the preconfigured connection.
	 * @throws SQLException
	 *             if a database access error occurs, this method is called on a closed connection or the given
	 *             parameters are not ResultSet constants indicating type and concurrency
	 */
	public Statement createStatement() throws SQLException {
		return getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	}

	// doesn't work as I expect it...
	/*
	 * public void setAutoCommit(boolean value) throws SQLException {
	 * getConnection().setAutoCommit(value);
	 * }
	 */

	/**
	 * TODO refactor this, this may be buggy...(see {@link DBConnection#forceRollback()}, {@link DBConnection#commit()} and {@link DBConnection#rollback()})
	 * 
	 * Starts a new transaction on the database if not already antoher transaction is active.
	 * If one or more "inner" transactions are already in progress, this call will start an "inner transaction"; an actual
	 * commit will only occur, when all "inner transaction" are commited. (see DBConnection
	 * 
	 * @throws SQLException
	 *             if a database access error occurs
	 */
	public void begin() throws SQLException {
		if (_transactionDepth == 0) {
			Statement stmt = getConnection().createStatement();
			stmt.execute("BEGIN");
		}
		_transactionDepth++;
	}

	/**
	 * TODO refactor this, this may be buggy...(see {@link DBConnection#forceRollback()}, {@link DBConnection#rollback()} and {@link DBConnection#begin()})
	 * 
	 * Commits a previously started transaction. If one or more "inner" transactions are already in progress, this will commit
	 * the "innermost" transaction.
	 * 
	 * @throws SQLException
	 *             if a database access error occurs of the transaction cannot be commited (e.g. ROLLBACK)
	 */
	public void commit() throws SQLException {
		if (_transactionDepth > 0) {
			_transactionDepth--;
			if (_transactionDepth == 0) {
				Statement stmt = getConnection().createStatement();
				stmt.execute("COMMIT");
			}
		}
	}

	/**
	 * TODO refactor this, this IS be buggy... (see {@link DBConnection#forceRollback()}, {@link DBConnection#commit()} and {@link DBConnection#begin()})
	 * 
	 * Calls a ROLLBACK on the database.
	 * 
	 * @throws SQLException if a database access error occurs, or no transaction is in progress
	 */
	public void rollback() throws SQLException {
		if (_transactionDepth > 0) {
			_transactionDepth--;
			if (_transactionDepth == 0) {
				Statement stmt = getConnection().createStatement();
				stmt.execute("ROLLBACK");
			}
		}
	}

	/**
	 * TODO this needs refactoring, see {@link DBConnection#rollback() and others}
	 * 
	 * Executes a ROLLBACK, ignoring "inner transactions"
	 * @throws SQLException if a database access error occurs, or no transaction is in progress
	 */
	public void forceRollback() throws SQLException {
		_transactionDepth = 1;
		rollback();
	}


	/**
	 * Opens a ressource file as {@link InputStream}.
	 * @param fileName path to the ressource to load
	 * @return {@link InputStream} to the given ressource
	 * @throws FileNotFoundException if the given ressource could not be found
	 */
	protected InputStream loadResourceFile(String fileName) throws FileNotFoundException {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		InputStream is = cl.getResourceAsStream(fileName);

		if (is == null) {
			throw new FileNotFoundException("Error: Couldn't open property file '" + fileName + "'");
		}

		return is;
	}


	private void applyProperties(PGConnectionPoolDataSource ds, String fileName) throws IOException {
		Properties prop = new Properties();
		InputStream propStream = PropertiesFileLoader.loadResourceFile(fileName);

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
	}
}
