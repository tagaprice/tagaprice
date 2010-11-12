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
package org.tagaprice.server.dao.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.log4j.Logger;
import org.tagaprice.server.dao.EntityDAO;
import org.tagaprice.server.dao.interfaces.IUnitDAO;
import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.Unit;
import org.tagaprice.shared.exception.DAOException;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;

public class UnitDAO implements IUnitDAO{
	private DBConnection _db;
	private EntityDAO _entityDAO;
	private static Logger log = Logger.getLogger(UnitDAO.class);
	
	public UnitDAO(DBConnection db) {
		_entityDAO = new EntityDAO(db);
		this._db = db;
	}
	
	
	@Override
	public SearchResult<Unit> getSimilar(long unitId) throws DAOException {
		log.debug("id:"+unitId);
		try {
			SearchResult<Unit> rc = new SearchResult<Unit>();
			long siId = unitId;

			// get base ID for unit (e.g. meter for milimeters)
			PreparedStatement pstmt = _db.prepareStatement("SELECT base_id FROM unit where unit_id = ? AND base_id IS NOT NULL");
			pstmt.setLong(1, unitId);
			ResultSet res = pstmt.executeQuery();
			if (res.next()) {
				siId = res.getLong("fallback_unit");
			}
			
			// get all units with the given fallback_unit (or unit_id)
			String sql = "SELECT unit_id, base_id, factor FROM unit u " +
			" WHERE unit_id = ? OR base_id = ?";
			
			pstmt = _db.prepareStatement(sql);
			pstmt.setLong(1, siId);	
			pstmt.setLong(2, siId);
			res = pstmt.executeQuery();
			
			while (res.next()) {
				rc.add(getUnit(res));
			}

			return rc;
		} catch (SQLException e) {
			String msg = "Failed to get similar units from database. SQLException: "+e.getMessage()+".";
			log.error(msg + " Chaining and rethrowing.");
			log.debug(e.getStackTrace());
			throw new DAOException(msg, e);
		}
	}
	
	@Override
	public boolean save(Unit unit) throws DAOException {
		log.debug("Unit:"+unit);
		Long id = unit.getId();
		PreparedStatement pstmt;

		try {
			if (id == null) {
				_entityDAO.save(unit);
				//Create unit
				pstmt = _db.prepareStatement("INSERT INTO unit (unit_id) VALUES (?)");
				pstmt.setLong(1, unit.getId());
				pstmt.executeUpdate();

				//Create unit_revistion
				pstmt = _db.prepareStatement("INSERT INTO unitrevision (unit_id, rev, factor, base_id) VALUES (?,?,?,?)");
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
			} else { //TODO this branch doesn't do anything but check if unit is there !?
				// first check if the entity is a unit
				pstmt = _db.prepareStatement("SELECT unit_id FROM unitrevision WHERE unit_id = ?");
				pstmt.setLong(1, unit.getId());
				ResultSet res = pstmt.executeQuery();
				if (!res.next()) {
					throw new DAOException("Unit not found! (id="+unit.getId()+")");
				}

				// check if we have to update the current unit data
				getById(unit.getId()); //TODO useless code + db access ?
			}
			return true;
		} catch (SQLException e) {
			String msg = "Failed to save unit to database. SQLException: "+e.getMessage()+".";
			log.error(msg + " Chaining and rethrowing.");
			log.debug(e.getStackTrace());
			throw new DAOException(msg, e);
		}
	}

	@Override
	public Unit getById(long id) throws DAOException {
		log.debug("id:"+id);
		String sql = "SELECT base_id, factor " +
		"FROM entity e INNER JOIN unitrevision r ON (e.ent_id = r.unit_id AND e.current_revision = r.rev) " +
		"WHERE unit_id = ?";
		PreparedStatement pstmt;

		try {
			pstmt = _db.prepareStatement(sql);
			pstmt.setLong(1, id);
			ResultSet res = pstmt.executeQuery();

			if (!res.next()) return null;
			Unit unit = new Unit(id);
			unit._setStandardId(res.getLong("base_id"));
			unit._setFactor(res.getDouble("factor"));
			return _entityDAO.getById(unit, id);
		} catch (SQLException e) {
			String msg = "Failed to retrieve unit from database. SQLException: "+e.getMessage()+".";
			log.error(msg + " Chaining and rethrowing.");
			log.debug(e.getStackTrace());
			throw new DAOException(msg, e);
		} 
	}
	
	
	private Unit getUnit(ResultSet res) throws DAOException, SQLException {
		long id = res.getLong("unit_id");
		Unit unit = new Unit(id, 0, null, 0, res.getLong("base_id"), res.getDouble("factor"));
		_entityDAO.getById(unit,id);
		return unit;
	}


	
}
