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
package org.tagaprice.server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.Locale;
import org.tagaprice.shared.exception.InvalidLocaleException;

public class LocaleDAO {
	private DBConnection db;

	public LocaleDAO(DBConnection db) {
		this.db = db;
	}
	
	public boolean exists(int id) throws SQLException {
		PreparedStatement pstmt = db.prepareStatement("SELECT count(*) FROM locale WHERE locale_id = ?");
		pstmt.setInt(1, id);
		ResultSet res = pstmt.executeQuery();
		res.next();
		return res.getInt(1) == 1;
	}
	
	public void require(int id) throws InvalidLocaleException, SQLException {
		if (!exists(id)) throw new InvalidLocaleException("Locale "+id+" not found!");
	}
	
	public Locale get(int id) throws InvalidLocaleException, SQLException {
		PreparedStatement pstmt = db.prepareStatement("SELECT locale_id, fallback_id, title, localTitle FROM locale WHERE locale_id = ?");
		pstmt.setInt(1, id);
		ResultSet res = pstmt.executeQuery();
		if (!res.next()) throw new InvalidLocaleException("Locale "+id+" not found!");
		
		return new Locale(res.getInt("locale_id"), res.getInt("fallback_id"), res.getString("title"), res.getString("localtitle"));
	}
	
	public Locale get(String title) throws SQLException, InvalidLocaleException {
		PreparedStatement pstmt = db.prepareStatement("SELECT locale_id, fallback_id, title, localTitle FROM locale WHERE title = ?");
		pstmt.setString(1, title);
		ResultSet res = pstmt.executeQuery();
		if (!res.next()) throw new InvalidLocaleException("Locale '"+title+"' not found!");
		
		return new Locale(res.getInt("locale_id"), res.getInt("fallback_id"), res.getString("title"), res.getString("localtitle"));
	}
}
