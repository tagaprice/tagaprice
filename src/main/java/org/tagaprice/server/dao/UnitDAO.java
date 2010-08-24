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
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;

public class UnitDAO implements DAOClass<Unit> {
	private static UnitDAO instance;
	private DBConnection db;
	private EntityDAO entityDAO;
	
	private UnitDAO(DBConnection db) {
		entityDAO = new EntityDAO(db);
		this.db = db;
	}
	
	public static UnitDAO getInstance() throws FileNotFoundException, IOException {
		return getInstance(new DBConnection());
	}
	
	public static UnitDAO getInstance(DBConnection db) {
		if (instance == null) instance = new UnitDAO(db);
		return instance;
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
	
	@Override
	public void save(Unit unit) throws SQLException, NotFoundException, RevisionCheckException, InvalidLocaleException {
		Long id = unit.getId();
		PreparedStatement pstmt;
		
		if (id == null) {
			// create new unit
			entityDAO.save(unit);
			pstmt = db.prepareStatement("INSERT INTO unit (unit_id, fallback_unit, factor) VALUES (?,?,?)");
			pstmt.setLong(1, unit.getId());
			pstmt.setLong(2, unit.getStandardId());
			pstmt.setDouble(3, unit.getFactor());
			pstmt.executeUpdate();
		}
		else {
			// first check if the entity is a unit
			pstmt = db.prepareStatement("SELECT unit_id FROM unit WHERE unit_id = ?");
			pstmt.setLong(1, unit.getId());
			ResultSet res = pstmt.executeQuery();
			if (!res.next()) throw new NotFoundException("Unit not found! (id="+unit.getId()+")");
			
			// check if we have to update the current unit data
			Unit refUnit = new Unit();
			refUnit._setId(unit.getId());
			get(refUnit);
		}
	}
	
	private Unit _getUnit(ResultSet res) throws SQLException, NotFoundException {
		Unit rc = new Unit(res.getLong("unit_id"), 0, null, 0, res.getLong("fallback_unit"), res.getDouble("factor"));
		
		entityDAO.get(rc);
		
		return rc;
	}

	@Override
	public void get(Unit unit) throws SQLException, NotFoundException {
		String sql = "SELECT base_id, factor " +
				"FROM entity e INNER JOIN unitrevision r ON (e.ent_id = r.unit_id AND e.current_revision = r.rev) " +
				"WHERE unit_id = ?";
		
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setLong(1, unit.getId());
		ResultSet res = pstmt.executeQuery();
		
		if (!res.next()) throw new NotFoundException("Unit "+unit.getId()+" not found");
		
		unit._setStandardId(res.getLong("fallback_unit"));
		unit._setFactor(res.getDouble("factor"));
				
		entityDAO.get(unit);

	}
}
