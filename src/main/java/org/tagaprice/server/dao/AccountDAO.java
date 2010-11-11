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
import org.tagaprice.shared.data.AccountData;
import org.tagaprice.shared.data.Entity;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;

public class AccountDAO implements DAOClass<AccountData> {
	private DBConnection db;
	private EntityDAO entityDAO;
	
	public AccountDAO(DBConnection db) {
		entityDAO = new EntityDAO(db) {
			@Override
			protected void resolveCreator(Entity e) throws SQLException {
				e._setCreatorId(e.getId());
				e._setRevCreatorId(e.getId());
				PreparedStatement pstmt = db.prepareStatement("INSERT INTO account (uid) VALUES (?)");
				pstmt.setLong(1, e.getId());
				pstmt.executeUpdate();
			}
		};
		this.db = db;
	}

	@Override
	public void get(AccountData account) throws SQLException, NotFoundException {
		// check if it's really an account
		PreparedStatement pstmt = db.prepareStatement("SELECT uid, mail FROM account WHERE uid = ?");
		pstmt.setLong(1, account.getId());
		ResultSet res = pstmt.executeQuery();
		if (res.next()) {
			account.setMail(res.getString("mail"));
			entityDAO.get(account);
		}
		else throw new NotFoundException("account not found: id "+account.getId());
	}

	@Override
	public void save(AccountData a) throws SQLException, NotFoundException,
			RevisionCheckException, InvalidLocaleException {
		PreparedStatement pstmt;
		
		entityDAO.save(a);

		/// TODO mail checking
		/// TODO if the mail address changes, a confirmation mail has to be sent 
		pstmt = db.prepareStatement("UPDATE account SET mail = ? WHERE uid = ?");
		pstmt.setString(1, a.getMail());
		pstmt.setLong(2, a.getId());
		pstmt.executeUpdate();
	}
}
