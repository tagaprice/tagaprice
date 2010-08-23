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
package org.tagaprice.server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.AccountData;
import org.tagaprice.shared.Entity;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;

public class AccountDAO implements DAOClass<AccountData> {
	private DBConnection db;
	private EntityDAO entityDAO;
	private static AccountDAO instance = null;
	
	private AccountDAO(DBConnection db) {
		entityDAO = new EntityDAO(db) {
			protected void resolveCreator(Entity e) {
				e._setCreatorId(e.getId());
				e._setRevCreatorId(e.getId());
			}
		};
		this.db = db;
	}

	public static AccountDAO getInstance(DBConnection db) {
		if (instance == null) {
			instance = new AccountDAO(db);
		}
		return instance;
	}

	@Override
	public void get(AccountData entity) throws SQLException, NotFoundException {
		// check if it's really an account
		PreparedStatement pstmt = db.prepareStatement("SELECT uid FROM account WHERE uid = ?");
		pstmt.setLong(1, entity.getId());
		ResultSet res = pstmt.executeQuery();
		if (res.next()) {
			entityDAO.get(entity);
		}
		else throw new NotFoundException("account not found: id "+entity.getId());
	}

	@Override
	public void save(AccountData a) throws SQLException, NotFoundException,
			RevisionCheckException, InvalidLocaleException {
		PreparedStatement pstmt;
		
		if (a.getId() == null) {
			// create a new account
			entityDAO.save(a);

			pstmt = db.prepareStatement("INSERT INTO account (uid) VALUES (?)");
			pstmt.setLong(1, a.getId());
			pstmt.executeUpdate();
			if (a.getCreatorId() == null) {
				a._setCreatorId(a.getId());
				a._setRevCreatorId(a.getId());
			}
		}
		else { // positive account ID => save an already existing account
			// first check if there really is an account with that ID
			pstmt = db.prepareStatement("SELECT uid FROM account WHERE uid = ?");
			pstmt.setLong(1, a.getId());
			ResultSet res = pstmt.executeQuery();

			if (!res.next()) throw new NotFoundException("Account not found (id="+a.getId()+")");
			
			// account already exists => save changes
			entityDAO.save(a);
		}
	}

}
