package org.tagaprice.server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;

import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.Entity;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.Unit;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;

public class PropertyDAO implements DAOClass<Entity> {
	private static PropertyDAO instance = null;
	private DBConnection db;
	private UnitDAO unitDAO;

	private PropertyDAO(DBConnection db) {
		this.db = db;
	}
	
	public static PropertyDAO getInstance(DBConnection db) {
		if (instance == null) {
			instance = new PropertyDAO(db);
			instance.unitDAO = UnitDAO.getInstance(db);
		}
		return instance;
	}
	/**
	 * Get the properties of an entity
	 */
	@Override
	public void get(Entity entity) throws SQLException, NotFoundException {
		//Get Property Data

		String sql = "SELECT ep.value, pr.name, er.title, ep.unit_id " +
				"FROM entityproperty ep " +
				"INNER JOIN entity e ON (e.ent_id = prop_id) " +
				"INNER JOIN propertyrevision pr ON (pr.prop_id = e.ent_id) " +
				"INNER JOIN entityRevision er ON (er.ent_id = ep.prop_id) " +
				"WHERE (ep.ent_id = ?) ";
		
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setLong(1, entity.getId());
		ResultSet res = pstmt.executeQuery();
		
		SearchResult<PropertyData> sr = new SearchResult<PropertyData>();
		
		while(res.next()){
			Unit u = null;
			if (res.getString("unit_id") != null) {
				u = new Unit(res.getLong("unit_id"));
				unitDAO.get(u);
			}
			sr.add(new PropertyData(
					res.getString("name"), 
					res.getString("title"), 
					res.getString("value"), 
					u));
		}
		entity.setProperties(sr);
	}

	/**
	 * 
	 */
	@Override
	public void save(Entity entity) throws SQLException, NotFoundException,
			RevisionCheckException, InvalidLocaleException {
		ArrayList<Long> removedProps = new ArrayList<Long>();
		// copy the property list (because we modify it)
		ArrayList<PropertyData> props = new ArrayList<PropertyData>(entity.getProperties());

		// check if properties still exist
		String sql = "SELECT eprop_id, name, value FROM entityproperty ep INNER JOIN entity e ON (e.ent_id = prop_id) INNER JOIN propertyrevision pr ON (ep.prop_id = pr.prop_id AND e.current_revision = pr.rev) " +
				"WHERE e.ent_id = ? AND max_rev IS NULL";
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setLong(1, entity.getId());
		ResultSet res = pstmt.executeQuery();
		
		while (res.next()) {
			// TODO find a faster approach for updating the properties
			String name = res.getString("name");
			String value = res.getString("value");
			Iterator<PropertyData> it = props.iterator();
			boolean found = false;
			while (!found && it.hasNext()) {
				PropertyData p = it.next();
				if (p.getName().equals(name) && p.getValue().equals(value)) {
					it.remove();
					found = true;
				}
			}
			if (!found) {
				// flag the property as "removed"
				removedProps.add(res.getLong("eprop_id"));
			}
		}
		
		// remove deleted properties
		pstmt = db.prepareStatement("UPDATE entityProperty set max_rev = ? WHERE eprop_id = ?");
		Iterator<Long> it = removedProps.iterator();
		while (it.hasNext()) {
			pstmt.setInt(1, entity.getRev());
			pstmt.setLong(2, it.next());
			pstmt.executeUpdate();
		}
		
		// add new properties (those left in props)
		sql = "INSERT INTO entityProperty (ent_id, prop_id, value, unit_id, min_rev) " +
				"VALUES (?, ?, ?, ?, ?)";
		Iterator<PropertyData> it2 = props.iterator();
		while (it2.hasNext()) {
			PropertyData p = it2.next();
			
			PreparedStatement pstmt2 = db.prepareStatement("SELECT prop_id FROM propertyrevision INNER JOIN entity ON (prop_id = ent_id) WHERE name = ? AND locale_id = ?");
			pstmt2.setString(1, p.getName());
			pstmt2.setInt(2, entity.getLocaleId());
			res = pstmt2.executeQuery();
			if (!res.next()) throw new NotFoundException("PropertyDefinition not found: "+p.getName()+" (locale: "+entity.getLocaleId()+")");
			
			pstmt = db.prepareStatement(sql);
			pstmt.setLong(1, entity.getId());
			pstmt.setLong(2, res.getLong("prop_id"));
			pstmt.setString(3, p.getValue());
			if (p.getUnit() != null) pstmt.setLong(4, p.getUnit().getId());
			else pstmt.setNull(4, Types.BIGINT);
			pstmt.setInt(5, entity.getRev());
			pstmt.executeUpdate();
		}
	}
}
