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
 * Filename: AccountDAO.java
 * Date: 21.07.2010
*/
package org.tagaprice.server.dao.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.interfaces.IAccountDAO;
import org.tagaprice.shared.entities.Account;
import org.tagaprice.shared.entities.Entity;
import org.tagaprice.shared.exception.DAOException;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;

public class AccountDAO implements IAccountDAO {
	private DBConnection db;
	private EntityDAO entityDAO;
	private static Logger _log = Logger.getLogger(AccountDAO.class);
	
	public AccountDAO(DBConnection db) {
		entityDAO = new EntityDAO(db) {
			@Override
			protected void resolveCreator(Entity e) throws SQLException {
				e.setCreatorId(e.getId());
				e.setRevCreatorId(e.getId());
				PreparedStatement pstmt = _db.prepareStatement("INSERT INTO account (uid) VALUES (?)");
				pstmt.setLong(1, e.getId());
				pstmt.executeUpdate();
			}
		};
		this.db = db;
	}

	@Deprecated
	public void get(Account account) throws DAOException {
		// check if it's really an account
		try {
			PreparedStatement pstmt = db.prepareStatement("SELECT uid, mail FROM account WHERE uid = ?");
			pstmt.setLong(1, account.getId());
			ResultSet res;
			res = pstmt.executeQuery();
			if (res.next()) {
				//entityDAO.getById(account, account.getId());
				account.setMail(res.getString("mail"));
			}
		} catch (SQLException e) {
			String msg = "Failed to save entity. SQLException: "+e.getMessage()+".";
			_log.debug(e.getStackTrace());
			_log.error(msg+" Chaining and rethrowing.");
			throw new DAOException(msg, e);
		}  
	}

	@Deprecated
	public void save(Account a) throws SQLException, NotFoundException,
			RevisionCheckException, InvalidLocaleException {
		PreparedStatement pstmt;
		
		try {
			entityDAO.save(a);
		} catch (DAOException e) {
			//TODO change
			throw new NotFoundException(e.getMessage(), e);
		}

		/// TODO mail checking
		/// TODO if the mail address changes, a confirmation mail has to be sent 
		pstmt = db.prepareStatement("UPDATE account SET mail = ? WHERE uid = ?");
		pstmt.setString(1, a.getMail());
		pstmt.setLong(2, a.getId());
		pstmt.executeUpdate();
	}
}
