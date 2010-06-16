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
	private EntityDAO entityDAO;
	
	private UnitDAO(DBConnection db) {
		this.db = db;
		entityDAO = new EntityDAO(db);
	}
	
	public static UnitDAO getInstance() throws FileNotFoundException, IOException {
		return getInstance(new DBConnection());
	}
	
	public static UnitDAO getInstance(DBConnection db) {
		if (instance == null) 
			instance = new UnitDAO(db);
		return instance;
	}
	
	public Unit get(long id) throws NotFoundException, SQLException {
		Unit rc = null;
		String sql = "SELECT unit_id, fallback_unit, factor" +
		" FROM unit u" +
		" WHERE unit_id = ?";
		
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setLong(1, id);
		ResultSet res = pstmt.executeQuery();
		
		if (!res.next()) throw new NotFoundException("Unit "+id+" not found");
		
		rc = _getUnit(res);
		
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
			String sql = "SELECT unit_id, fallback_unit, factor FROM unit u " +
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
			throw new NotFoundException("Query Error", e);
		}
	}
	
	public SearchResult<Unit> getSimilar(Unit unit) throws NotFoundException {
		return getSimilar(unit.getId());
	}
	
	private Unit _getUnit(ResultSet res) throws SQLException, NotFoundException {
		Unit rc =  new Unit(res.getLong("unit_id"), 0, res.getLong("fallback_unit"), res.getDouble("factor"));
		
		entityDAO.get(rc);
		
		return rc;
	}
}
