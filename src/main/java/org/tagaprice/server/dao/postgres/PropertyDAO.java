package org.tagaprice.server.dao.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.interfaces.IPropertyDAO;
import org.tagaprice.shared.SerializableArrayList;
import org.tagaprice.shared.entities.Entity;
import org.tagaprice.shared.entities.Property;
import org.tagaprice.shared.entities.PropertyTypeDefinition;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.exception.DAOException;
import org.tagaprice.shared.exception.NotFoundException;

public class PropertyDAO implements IPropertyDAO {
	private DBConnection _db;
	private static Logger _log = Logger.getLogger(PropertyDAO.class);

	public PropertyDAO(DBConnection db) {
		this._db = db;
	}

	@Override
	public <T extends Entity> T setProperties(T entity) throws DAOException {
		_log.debug("entity:"+entity);
		UnitDAO unitDAO = new UnitDAO(_db);

		//Get Property Data

		String sql = "SELECT eprop_id, ep.value, pr.name, er.title, ep.unit_id " +
		"FROM entityproperty ep " +
		"INNER JOIN entity e ON (e.ent_id = prop_id) " +
		"INNER JOIN propertyrevision pr ON (pr.prop_id = e.ent_id) " +
		"INNER JOIN entityRevision er ON (er.ent_id = ep.prop_id) " +
		"WHERE ep.ent_id = ? AND (max_rev >= ? OR max_rev IS NULL)";

		try {
			PreparedStatement pstmt = _db.prepareStatement(sql);
			pstmt.setLong(1, entity.getId());
			pstmt.setInt(2, entity.getRev());
			ResultSet res = pstmt.executeQuery();

			SerializableArrayList<Property> props = new SerializableArrayList<Property>();

			while(res.next()) {
				Unit u = null;
				if (res.getString("unit_id") != null) {
					try {
						u = unitDAO.getById(res.getLong("unit_id"));
					} catch (DAOException e) {
						//TODO handle
					}
				}
				props.add(new Property(
						res.getLong("eprop_id"),
						res.getString("name"), 
						res.getString("title"), 
						res.getString("value"), 
						u));
			}
			entity.setProperties(props);
			return entity;
		} catch (SQLException e) {
			String msg = "Failed to retrieve entity from database. SQLException: "+e.getMessage()+".";
			_log.error(msg + " Chaining and rethrowing.");
			_log.debug(e.getStackTrace());
			throw new DAOException(msg, e);
		}
	}

	@Override
	public boolean save(Entity entity) throws DAOException {
		_log.debug("entity:"+entity);
		PropertyDefinitionDAO propDefDAO = new PropertyDefinitionDAO(_db);

		// get the entity's properties from the database
		Entity oldEntity = new Entity(entity.getId()) {
			private static final long serialVersionUID = 1L;

			@Override
			public String getSerializeName() { return null; }
		};
		oldEntity = setProperties(oldEntity);

		// store them in a HashMap to speed up finding them by id
		HashMap<Long, Property> oldProps = new HashMap<Long, Property>();
		HashMap<Long, Property> newProps = new HashMap<Long, Property>();

		Iterator<Property> it = entity.getProperties().iterator();
		while (it.hasNext()) {
			Property item = it.next();
			newProps.put(item.getId(), item);
		}

		try {
			it = oldEntity.getProperties().iterator();
			while (it.hasNext()) {
				Property item = it.next();

				// check if the new propList contains the item and mark it deleted otherwise
				if (!newProps.containsKey(item.getId())) {
					PreparedStatement stmt = _db.prepareStatement("UPDATE entityProperty SET max_rev = ? WHERE eprop_id = ?");
					stmt.setInt(1, entity.getRev()-1);
					stmt.setLong(2, item.getId());
				}
				else oldProps.put(item.getId(), item);
			}

			// find properties that were deleted
			it = entity.getProperties().iterator();
			while (it.hasNext()) {
				Property item = it.next();

				// first check if the item changed (if it did, mark the old property deleted and set the
				// eprop_id to null to trigger an INSERT in the next if)
				if (item.hasId() && oldProps.containsKey(item.getId())) {
					// property's still there => check if it changed
					if (!oldProps.get(item.getId()).equals(item)) {
						PreparedStatement stmt = _db.prepareStatement("UPDATE entityProperty set max_rev = ? WHERE eprop_id = ?");
						stmt.setInt(1, entity.getRev()-1);
						stmt.setLong(2, item.getId());
						stmt.executeUpdate();

						item._setId(null);
					}
				}

				// add new properties
				if (!item.hasId()) {
					PropertyTypeDefinition propDef;
					try {
						propDef = propDefDAO.get(item.getName(), entity.getLocaleId());
					} catch (NotFoundException e) {
						// TODO clean propDefDAO from NotFoundExceptions
						throw new DAOException(e.getMessage(), e);
					} 
					// new property, save it
					PreparedStatement pstmt = _db.prepareStatement("INSERT INTO entityProperty (prop_id, ent_id, value, unit_id, min_rev) VALUES (?,?,?,?,?)");
					pstmt.setLong(1, propDef.getId());
					pstmt.setLong(2, entity.getId());
					pstmt.setString(3, item.getValue());

					if (item.hasUnit()) {
						pstmt.setLong(4, item.getUnit().getId());
					}
					else pstmt.setNull(4, Types.BIGINT);

					pstmt.setInt(5, entity.getRev());
					pstmt.executeUpdate();

					Statement stmt = _db.createStatement();
					ResultSet res = stmt.executeQuery("SELECT currval('entityproperty_eprop_id_seq');");
					res.next();
					item._setId(res.getLong(1));

				}
			}
			return true;
		} catch (SQLException e) {
			String msg = "Failed to retrieve entity from database. SQLException: "+e.getMessage()+".";
			_log.error(msg + " Chaining and rethrowing.");
			_log.debug(e.getStackTrace());
			throw new DAOException(msg, e);
		}
	}
}
