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
 * Filename: UnitDAO.java
 * Date: 11.06.2010
*/
package org.tagaprice.server.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.Unit;
import org.tagaprice.shared.exception.NotFoundException;

public class UnitDAO {
	private static UnitDAO instance;
	private DBConnection db;
	
	private UnitDAO(DBConnection db) {
		this.db = db;
	}
	
	public static UnitDAO getInstance() {
		if (instance == null)
			try {
				instance = new UnitDAO(new DBConnection());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return instance;
	}
	
	public Unit get(long id) throws NotFoundException {
		Unit rc = null;
		String sql = "SELECT unit_id, r.rev, u.locale_id, r.title, fallback_unit, factor" +
				" FROM unit u INNER JOIN entity e ON (unit_id = ent_id)" +
				" INNER JOIN entityRevision r ON (r.ent_id = e.ent_id AND r.rev = e.current_revision)" +
				" WHERE unit_id = ?";
		
		try {
			PreparedStatement pstmt = db.prepareStatement(sql);
			pstmt.setLong(1, id);
			ResultSet res = pstmt.executeQuery();
			
			if (!res.next()) throw new NotFoundException("Unit "+id+" not found");
			
			rc = _getUnit(res);
		} catch (SQLException e) {
			throw new NotFoundException("Query Error", e);
		}
		
		return rc;
	}
	
	public SearchResult<Unit> getSimilar(long id) throws NotFoundException {
		
		try {
			SearchResult<Unit> rc = new SearchResult<Unit>();
			long siId = id;

			// check if this Unit has a fallback ID (and use it as siId instead)
			PreparedStatement pstmt = db.prepareStatement("SELECT fallback_unit FROM unit where unit_id = ? AND fallback_unit IS NOT NULL");
			pstmt.setLong(1, id);
			ResultSet res = pstmt.executeQuery();
			if (res.next()) {
				siId = res.getLong("fallback_unit");
			}
			
			// get all units with the given fallback_unit (or unit_id)
			String sql = "SELECT unit_id, r.rev, u.locale_id, r.title, fallback_unit, factor" +
			" FROM unit u INNER JOIN entity e ON (unit_id = ent_id)" +
			" INNER JOIN entityRevision r ON (r.ent_id = e.ent_id AND r.rev = e.current_revision)" +
			" WHERE unit_id = ? OR fallback_unit = ?";
			
			pstmt = db.prepareStatement(sql);
			pstmt.setLong(1, siId);
			pstmt.setLong(2, siId);
			res = pstmt.executeQuery();
			
			while (res.next()) {
				rc.add(_getUnit(res));
			}

			return rc;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new NotFoundException("Query Error", e);
		}
	}
	
	public SearchResult<Unit> getSimilar(Unit unit) throws NotFoundException {
		return getSimilar(unit.getId());
	}
	
	private Unit _getUnit(ResultSet res) throws SQLException {
		return new Unit(res.getLong("unit_id"), res.getInt("rev"), res.getString("title"), res.getInt("locale_id"), res.getLong("fallback_unit"), res.getDouble("factor"));
	}
}
