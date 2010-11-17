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
 * Filename: EntityDAO.java
 * Date: 13.06.2010
*/
package org.tagaprice.server.dao.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.interfaces.IEntityDAO;
import org.tagaprice.shared.SerializableArrayList;
import org.tagaprice.shared.entities.Entity;
import org.tagaprice.shared.entities.Property;
import org.tagaprice.shared.exception.DAOException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;

/**
 * DAO class for the database table all Entities inherit
 * 
 * Other DAO-Classes (e.g. ProductDAO) use this to handle the revisioning stuff
 * Note: Don't inherit this Class (which isn't possible anyway...) but use getInstance() instead
 * @author Manuel Reithuber
 */
public class EntityDAO implements IEntityDAO {
	protected DBConnection _db;
	private LocaleDAO _localeDAO;
	private PropertyDAO _propertyDAO;
	private static Logger _log = Logger.getLogger(EntityDAO.class);

	/**
	 * Constructor that provides an easy way to supply a modified DBConnection object
	 * (e.g. with auto-rollback for unit test cases) 
	 * @param db DBConnection object
	 */
	public EntityDAO(DBConnection db) {
		this._db = db;
		this._localeDAO = new LocaleDAO(db);
		this._propertyDAO = new PropertyDAO(db);
	}
	
	@Override
	public <T extends Entity> T getById(T entity, long id) throws DAOException {
		return getByIdAndRev(entity, id, 0);
	}
	
	@Override
	public <T extends Entity> T getByIdAndRev(T entity, long id, long rev) throws DAOException {
		_log.debug("entity:"+entity);
		_log.debug("id:"+id);
		_log.debug("rev:"+rev);
		
		String sql = "SELECT e.ent_id, rev, title, locale_id, e.creator, r.creator AS revCreator " +
		" FROM entity e INNER JOIN entityRevision r ON (e.ent_id = r.ent_id";
		if (rev == 0)
			sql += " AND e.current_revision = r.rev";
		sql += ") WHERE e.ent_id = ?";
		if (rev > 0) sql += " AND r.rev = ?";

		try {
			PreparedStatement pstmt = _db.prepareStatement(sql);
			pstmt.setLong(1, entity.getId());
			if (rev > 0) pstmt.setLong(2, rev);

			ResultSet res = pstmt.executeQuery();

			if (res.next()) {
				entity.setTitle(res.getString("title"));
				entity.setRev(res.getInt("rev"));
				entity.setLocaleId(res.getInt("locale_id"));
				entity.setCreatorId(res.getLong("creator"));
				entity.setRevCreatorId(res.getLong("revcreator"));
			}
			else {
				String msg = "Entity (id: "+entity.getId();
				if (entity.getRev()>0) msg += ", rev: "+entity.getRev();
				msg += ") not found!";
			}
			
			// get and set properties
			SerializableArrayList<Property> props = _propertyDAO.getPropertiesByIdAndRef(entity.getId(), entity.getRev());
			
			entity.setProperties(props); //instead create new entity and set its property
			
			return entity;
		} catch (SQLException e) {
			String msg = "Failed to retrieve entity from database. SQLException: "+e.getMessage()+".";
			_log.error(msg + " Chaining and rethrowing.");
			_log.debug(e.getStackTrace());
			throw new DAOException(msg, e);
		}
	}
	
	@Override
	public <T extends Entity> T save(T entity) throws DAOException {
		try {
			T newVersion;
			if(entity.getId() == null) { //create new entity
				newVersion = newEntity(entity);
			} else {
//				TODO compare entity differently
//				Entity oldVersion = getEntity(entity.getId()); 
//				if (!entity.equals(oldVersion)) { // it should not be possible to save a entity under the wrong ent_id
//					// they differ => save changes
					newVersion = newRevision(entity);
//				}
			}
			
			if(newVersion == null)
				return null;
			_propertyDAO.saveProperties(newVersion); //ignore return value
			return newVersion;
		} catch (SQLException e) {
			String msg = "Failed to save entity. SQLException: "+e.getMessage()+".";
			_log.debug(e.getStackTrace());
			_log.error(msg+" Chaining and rethrowing.");
			throw new DAOException(msg, e);
		}  catch (NotFoundException e) {
			String msg = "Failed to save entity. NotFoundException: "+e.getMessage()+".";
			_log.debug(e.getStackTrace());
			_log.error(msg+" Chaining and rethrowing.");
			throw new DAOException(msg, e);
		} catch (RevisionCheckException e) {
			String msg = "Failed to save entity. RevisionCheckException: "+e.getMessage()+".";
			_log.debug(e.getStackTrace());
			_log.error(msg+" Chaining and rethrowing.");
			throw new DAOException(msg, e);
		}
	}
	
	
	protected void resolveCreator(Entity e) throws NotFoundException, SQLException {
		throw new NotFoundException("Creator not found: null");
	}

	@SuppressWarnings("unused")
	@Deprecated
	private Entity getEntity(long id) throws DAOException {
		Entity rc = new Entity(id) {
			private static final long serialVersionUID = 1L;

			@Override
			public String getSerializeName() {
				return null;
			}

			@Override
			public <T extends Entity> T newRevision() {
				return null;
			}
		};
		
		return getById(rc, id);
	}
	
	private <T extends Entity> T newRevision(T e) throws SQLException, NotFoundException, RevisionCheckException {
		PreparedStatement pstmt;
		ResultSet res;
		int rev = e.getRev();
		
		_db.begin(); // begin transaction
		
		// first check that the new revision doesn't exist yet
		pstmt = _db.prepareStatement("SELECT current_revision FROM entity WHERE ent_id = ?");
		pstmt.setLong(1, e.getId());
		res = pstmt.executeQuery();

		if (!res.next()) throw new NotFoundException("Couldn't find entity with id:"+e.getId());
		if (res.getInt("current_revision") != rev) throw new RevisionCheckException("Revision out of date: "+rev);
		rev++;

		// create new EntityRevision element
		pstmt = _db.prepareStatement("INSERT INTO entityRevision (ent_id, rev, title, creator) VALUES (?, ?, ?, ?)");
		pstmt.setLong(1, e.getId());
		pstmt.setInt(2, rev);
		pstmt.setString(3, e.getTitle());
		pstmt.setLong(4, e.getRevCreatorId());
		pstmt.executeUpdate();

		// update current_revision in entity
		pstmt = _db.prepareStatement("UPDATE entity SET current_revision = ? WHERE ent_id = ?");
		pstmt.setInt(1, rev);
		pstmt.setLong(2, e.getId());
		pstmt.executeUpdate();

		// create new revision
		//e.setRev(e.getRev()+1);
		T newVersion = e.<T>newRevision();
		
		_db.commit();
		
		return newVersion;
		
	}
	
	private <T extends Entity> T newEntity(T e) throws SQLException, RevisionCheckException, DAOException, NotFoundException {
		PreparedStatement pstmt;
		ResultSet res;
		
		_db.begin(); // start transaction

		if (e.getRev() != 0) throw new RevisionCheckException("new entities have to use revision 0");
		
		// check localeId
		if(_localeDAO.getById(e.getLocaleId()) == null)
			return null;
		
		// create entity
		String sql;
		if (e.getCreatorId() == null) {
			sql = "INSERT INTO entity (locale_id, current_revision, creator) VALUES (?, 1, currval('entity_ent_id_seq'))";
		}
		else sql = "INSERT INTO entity (locale_id, current_revision, creator) VALUES (?, 1, ?)";
		pstmt = _db.prepareStatement(sql);
		pstmt.setLong(1, e.getLocaleId());
		if (e.getCreatorId() != null) pstmt.setLong(2, e.getCreatorId());
		pstmt.executeUpdate();

		// Query current ent_id
		pstmt = _db.prepareStatement("SELECT currval('entity_ent_id_seq') AS ent_id");
		res = pstmt.executeQuery();
		res.next();

		long id = res.getLong("ent_id");
		e._setId(id);

		if (e.getCreatorId() == null) resolveCreator(e); //TODO messy code

		// create entityRevision
		sql = "INSERT INTO entityRevision(ent_id, rev, title, creator) VALUES "
			+"(currval('entity_ent_id_seq'), ?, ?, ?)";
		pstmt = _db.prepareStatement(sql);
		pstmt.setInt(1, e.getRev()+1);
		pstmt.setString(2, e.getTitle());
		pstmt.setLong(3, e.getRevCreatorId());
		pstmt.executeUpdate();

//		create new revision
//		e.setRev(1);
		T newVersion = e.<T>newRevision();

		_db.commit();
		
		return newVersion;
	}
	
}
