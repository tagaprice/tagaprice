package org.tagaprice.server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;

import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.postgres.UnitDAO;
import org.tagaprice.shared.Entity;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyDefinition;
import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.Unit;
import org.tagaprice.shared.exception.DAOException;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;

public class PropertyDAO implements DAOClass<Entity> {
	private DBConnection db;

	public PropertyDAO(DBConnection db) {
		this.db = db;
	}
	
	/**
	 * Get the properties of an entity
	 */
	@Override
	public void get(Entity entity) throws SQLException, NotFoundException {
		UnitDAO unitDAO = new UnitDAO(db);
		
		//Get Property Data

		String sql = "SELECT eprop_id, ep.value, pr.name, er.title, ep.unit_id " +
				"FROM entityproperty ep " +
				"INNER JOIN entity e ON (e.ent_id = prop_id) " +
				"INNER JOIN propertyrevision pr ON (pr.prop_id = e.ent_id) " +
				"INNER JOIN entityRevision er ON (er.ent_id = ep.prop_id) " +
				"WHERE ep.ent_id = ? AND (max_rev >= ? OR max_rev IS NULL)";
		
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setLong(1, entity.getId());
		pstmt.setInt(2, entity.getRev());
		ResultSet res = pstmt.executeQuery();
		
		SearchResult<PropertyData> props = new SearchResult<PropertyData>();
		
		while(res.next()) {
			Unit u = null;
			if (res.getString("unit_id") != null) {
				try {
					u = unitDAO.getById(res.getLong("unit_id"));
				} catch (DAOException e) {
					//TODO handle
				}
			}
			props.add(new PropertyData(
					res.getLong("eprop_id"),
					res.getString("name"), 
					res.getString("title"), 
					res.getString("value"), 
					u));
		}
		entity.setProperties(props);
	}

	/**
	 * 
	 */
	@Override
	public void save(Entity entity) throws SQLException, NotFoundException,
			InvalidLocaleException {
		PropertyDefinitionDAO propDefDAO = new PropertyDefinitionDAO(db);
		
		// get the entity's properties from the database
		Entity oldEntity = new Entity(entity.getId()) {
			private static final long serialVersionUID = 1L;

			@Override
			public String getSerializeName() { return null; }
		};
		get(oldEntity);
		
		// store them in a HashMap to speed up finding them by id
		HashMap<Long, PropertyData> oldProps = new HashMap<Long, PropertyData>();
		HashMap<Long, PropertyData> newProps = new HashMap<Long, PropertyData>();
		
		Iterator<PropertyData> it = entity.getProperties().iterator();
		while (it.hasNext()) {
			PropertyData item = it.next();
			newProps.put(item.getId(), item);
		}
		
		it = oldEntity.getProperties().iterator();
		while (it.hasNext()) {
			PropertyData item = it.next();

			// check if the new propList contains the item and mark it deleted otherwise
			if (!newProps.containsKey(item.getId())) {
				PreparedStatement stmt = db.prepareStatement("UPDATE entityProperty SET max_rev = ? WHERE eprop_id = ?");
				stmt.setInt(1, entity.getRev()-1);
				stmt.setLong(2, item.getId());
			}
			else oldProps.put(item.getId(), item);
		}
		
		// find properties that were deleted
		it = entity.getProperties().iterator();
		while (it.hasNext()) {
			PropertyData item = it.next();
			
			// first check if the item changed (if it did, mark the old property deleted and set the
			// eprop_id to null to trigger an INSERT in the next if)
			if (item.hasId() && oldProps.containsKey(item.getId())) {
				// property's still there => check if it changed
				if (!oldProps.get(item.getId()).equals(item)) {
					PreparedStatement stmt = db.prepareStatement("UPDATE entityProperty set max_rev = ? WHERE eprop_id = ?");
					stmt.setInt(1, entity.getRev()-1);
					stmt.setLong(2, item.getId());
					stmt.executeUpdate();
					
					item._setId(null);
				}
			}
			
			// add new properties
			if (!item.hasId()) {
				PropertyDefinition propDef = propDefDAO.get(item.getName(), entity.getLocaleId()); 
				// new property, save it
				PreparedStatement pstmt = db.prepareStatement("INSERT INTO entityProperty (prop_id, ent_id, value, unit_id, min_rev) VALUES (?,?,?,?,?)");
				pstmt.setLong(1, propDef.getId());
				pstmt.setLong(2, entity.getId());
				pstmt.setString(3, item.getValue());
				
				if (item.hasUnit()) {
					pstmt.setLong(4, item.getUnit().getId());
				}
				else pstmt.setNull(4, Types.BIGINT);
				
				pstmt.setInt(5, entity.getRev());
				pstmt.executeUpdate();
				
				Statement stmt = db.createStatement();
				ResultSet res = stmt.executeQuery("SELECT currval('entityproperty_eprop_id_seq');");
				res.next();
				item._setId(res.getLong(1));
				
			}
		}
	}
}
