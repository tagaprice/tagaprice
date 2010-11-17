/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: tagaprice
 * Filename: LocaleDAO.java
 * Date: 15.06.2010
*/
package org.tagaprice.server.dao.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.interfaces.ILocaleDAO;
import org.tagaprice.shared.entities.Locale;
import org.tagaprice.shared.exception.DAOException;
import org.tagaprice.shared.exception.InvalidLocaleException;
/**
 * A DAO Class for Loading and Storing Locales into Postgres DB. 
 * @author tagaprice, edited by Martin Weik 20101112
 */
public class LocaleDAO implements ILocaleDAO {
	private static Logger _log = Logger.getLogger(LocaleDAO.class);
	private DBConnection db;

	public LocaleDAO(DBConnection db) {
		this.db = db;
	}
	
	/**
	 * Replaced by getById
	 * @param id
	 * @return a Locale for this id
	 * @throws SQLException
	 */
	@Deprecated
	public boolean exists(int id) throws SQLException {
		/*PreparedStatement pstmt = db.prepareStatement("SELECT count(*) FROM locale WHERE locale_id = ?");
		pstmt.setInt(1, id);
		ResultSet res = pstmt.executeQuery();
		res.next();
		return res.getInt(1) == 1;
		*/
		Locale locale = null;
		try {
			locale = this.getById(id);
		} catch (DAOException e) {
			throw new SQLException(e);
		}
		return (locale != null);
	}
	/**
	 * Deprecated
	 * @param id
	 * @throws InvalidLocaleException
	 * @throws SQLException
	 */
	@Deprecated
	public void require(int id) throws InvalidLocaleException, SQLException { //TODO delete this method, replace usages with "!exists(id)"
		if (!exists(id)) throw new InvalidLocaleException("Locale "+id+" not found!");
	}
	/**
	 * Replaced by getById
	 * @param id
	 * @return a Locale for this id
	 * @throws InvalidLocaleException
	 * @throws SQLException
	 */
	@Deprecated
	public Locale get(int id) throws InvalidLocaleException, SQLException {
		try {
			Locale locale = this.getById(id);
			if(locale == null) {
				throw new InvalidLocaleException("Invalid Locale");
			} else {
				return locale;
			}
		} catch (DAOException e) {
			throw new SQLException(e);
		}
		
	}
	@Deprecated
	public Locale get(String title) throws SQLException, InvalidLocaleException {
		Locale locale = null;
		try {
			locale = this.getByFullLanguageName(title);
		} catch (DAOException e) {
			throw new SQLException(e);
		}
		if(locale == null) {
			throw new InvalidLocaleException("Locale " + title + " not found");
		}
		return locale;
	}

	@Override
	public Locale getById(int id) throws DAOException {
		PreparedStatement sqlStatement;
		Locale locale = null;
		try {
			String statementString = "SELECT locale_id, fallback_id, title, localTitle FROM locale WHERE locale_id = ?";
			sqlStatement = db.prepareStatement(statementString);
			sqlStatement.setInt(1, id);
			LocaleDAO._log.debug(statementString + ", id(int): " + id);	
			ResultSet res = sqlStatement.executeQuery();
			if (res.next()) {
				locale = new Locale(res.getInt("locale_id"), res.getInt("fallback_id"), res.getString("title"), res.getString("localtitle"));
				//old code: removed by Martin Weik, 20101112
				//throw new InvalidLocaleException("Locale "+id+" not found!");
			}
		} catch (SQLException e) {
			String message = "Failed to get Locale. SQLException: " + e.getMessage() + ".";
			LocaleDAO._log.error(message + "Chaining and rethrowing.");
			LocaleDAO._log.debug(e.getStackTrace());
			throw new DAOException(message, e);
		}
		return locale;		
	}

	@Override
	public Locale getByFullLanguageName(String name) throws DAOException {
		Locale locale = null;
		try {
			String statementString = "SELECT locale_id, fallback_id, title, localTitle FROM locale WHERE title = ?";
			PreparedStatement sqlStatement = db.prepareStatement(statementString);
			sqlStatement.setString(1, name);
			LocaleDAO._log.debug(statementString + ", name(String): " + name);
			ResultSet res = sqlStatement.executeQuery();
			if (res.next()) {
				locale = new Locale(res.getInt("locale_id"), res.getInt("fallback_id"), res.getString("title"), res.getString("localtitle"));	
				//removed by Martin Weik 20101112
				//throw new InvalidLocaleException("Locale '" + name + "' not found!");
			} 
		} catch (SQLException e) {
			String message = "Failed to get Locale. SQLException: " + e.getMessage() + ".";
			LocaleDAO._log.error(message + "Chaining and rethrowing.");
			LocaleDAO._log.debug(e.getStackTrace());
			throw new DAOException(message, e);
		} 		
		return locale;
	}

	@Override
	public Locale getByCountryAndLanguageCode(String country, String language)
			throws DAOException {
		throw new Error("This method is not implemented");
		// TODO Auto-generated method stub
	}

	@Override
	public Locale getByLanguageCode(String language) throws DAOException {
		// TODO Auto-generated method stub
		throw new Error("This method is not implemented");
	}

	@Override
	public void save(Locale locale) throws DAOException {
		// TODO Auto-generated method stub
		throw new Error("This method is not implemented");
	}
}
