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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.data.Unit;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;

public class UnitDAO implements DAOClass<Unit> {
	private DBConnection db;
	private EntityDAO entityDAO;
	
	public UnitDAO(DBConnection db) {
		entityDAO = new EntityDAO(db);
		this.db = db;
	}
	
	public SearchResult<Unit> getSimilar(long id) throws NotFoundException {
		
		try {
			SearchResult<Unit> rc = new SearchResult<Unit>();
			long siId = id;

			// check if this Unit has a fallback ID (and use it as siId instead)
			PreparedStatement pstmt = db.prepareStatement("SELECT base_id FROM unit where unit_id = ? AND base_id IS NOT NULL");
			pstmt.setLong(1, id);
			ResultSet res = pstmt.executeQuery();
			if (res.next()) {
				siId = res.getLong("fallback_unit");
			}
			
			// get all units with the given fallback_unit (or unit_id)
			String sql = "SELECT unit_id, base_id, factor FROM unit u " +
			" WHERE unit_id = ? OR base_id = ?";
			
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
			
			//Create unit
			pstmt = db.prepareStatement("INSERT INTO unit (unit_id) VALUES (?)");
			pstmt.setLong(1, unit.getId());
			pstmt.executeUpdate();
			
			//Create unit_revistion
			pstmt = db.prepareStatement("INSERT INTO unitrevision (unit_id, rev, factor, base_id) VALUES (?,?,?,?)");
			pstmt.setLong(1, unit.getId());
			pstmt.setLong(2, unit.getRev());
			
			
			//Base ID is null. This Unit is the base unit (eg. L (liter) is base for liter))
			if(unit.getStandardId()==null){
				pstmt.setNull(3, Types.DOUBLE);
				pstmt.setNull(4, Types.BIGINT); //pstmt.setLong(4yp, unit.getId());
			}
			else {
				pstmt.setDouble(3, unit.getFactor());
				pstmt.setLong(4, unit.getStandardId());
			}
						
			
			pstmt.executeUpdate();
		}
		else {
			// first check if the entity is a unit
			pstmt = db.prepareStatement("SELECT unit_id FROM unitrevision WHERE unit_id = ?");
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
		Unit rc = new Unit(res.getLong("unit_id"), 0, null, 0, res.getLong("base_id"), res.getDouble("factor"));
		
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
		
		unit._setStandardId(res.getLong("base_id"));
		unit._setFactor(res.getDouble("factor"));
				
		entityDAO.get(unit);

	}
}
