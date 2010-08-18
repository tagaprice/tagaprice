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
package org.tagaprice.server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.Entity;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;

/**
 * DAO class for the database table all Entities inherit
 * 
 * Other DAO-Classes (e.g. ProductDAO) use this to handle the revisioning stuff
 * Note: Don't inherit this Class (which isn't possible anyway...) but use getInstance() instead
 * @author Manuel Reithuber
 */
public class EntityDAO implements DAOClass<Entity> {
	protected DBConnection db;
	private LocaleDAO localeDAO;
	private PropertyDAO propertyDAO;
	private static EntityDAO instance = null;

	/**
	 * Constructor that provides an easy way to supply a modified DBConnection object
	 * (e.g. with auto-rollback for unit test cases) 
	 * @param db DBConnection object
	 */
	private EntityDAO(DBConnection db) {
		this.db = db;
		this.localeDAO = LocaleDAO.getInstance(db);
		this.propertyDAO = PropertyDAO.getInstance(db);
	}
	
	public static EntityDAO getInstance(DBConnection db) {
		if (instance == null) {
			instance = new EntityDAO(db);
		}
		return instance;
	}

	public void get(Entity e) throws SQLException, NotFoundException {
		String sql = "SELECT e.ent_id, rev, title, locale_id, e.creator, r.creator AS revCreator " +
			" FROM entity e INNER JOIN entityRevision r ON (e.ent_id = r.ent_id";
		if (e.getRev() == 0) sql += " AND e.current_revision = r.rev";
		sql += ") WHERE e.ent_id = ?";
		
		if (e.getRev() > 0) sql += " AND r.rev = ?";
		
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setLong(1, e.getId());
		if (e.getRev() > 0) pstmt.setLong(2, e.getRev());
		
		ResultSet res = pstmt.executeQuery();
		
		if (res.next()) {
			e.setTitle(res.getString("title"));
			e._setRev(res.getInt("rev"));
			e._setLocaleId(res.getInt("locale_id"));
			e._setCreatorId(res.getLong("creator"));
			e._setRevCreatorId(res.getLong("revcreator"));
		}
		else {
			String msg = "Entity (id: "+e.getId();
			if (e.getRev()>0) msg += ", rev: "+e.getRev();
			msg +=") not found!";
			throw new NotFoundException(msg); 
		}
		
		// get properties
		propertyDAO.get(e);
	}
	
	public void save(Entity entity) throws SQLException, NotFoundException, RevisionCheckException, InvalidLocaleException {
		if (entity.getId() != null) { // try to update an existing entity
			// first re-fetch the Entity and then compare them to see if something has changed
			// ent_id, locale_id and created_at are read only, therefore will not be checked or saved
			
			Entity eCompare = _getEntity(entity.getId());
			//if (!entity.equals(eCompare)) { // it should not be possible to save a entity under the wrong ent_id
			if (eCompare.getTitle() != entity.getTitle()) {
				// they differ => save changes
				_newRevision(entity);
			}
			
			
		}
		else { // create new one
			_newEntity(entity);
		}
		
		// save properties
		//propertyDAO.save(entity);
	}
	
	private Entity _getEntity(long id) throws SQLException, NotFoundException {
		// TODO try to remove duplicate code (in get() and _getEntity())
		Entity rc = null;
		String sql = "SELECT e.ent_id, e.created_at AS entityDate, r.created_at AS revDate, rev, title, locale_id, e.creator, r.creator AS revCreator" +
			" FROM entity e INNER JOIN entityRevision r" +
			" ON (e.ent_id = r.ent_id AND e.current_revision = r.rev)" +
			" WHERE e.ent_id = ?";
		
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setLong(1, id);
		ResultSet res = pstmt.executeQuery();
		
		if (!res.next()) throw new NotFoundException("didn't find entity with the ID "+id);
		
		rc = new Entity(res.getLong("ent_id"), res.getInt("rev"), res.getString("title"), res.getInt("locale_id"), res.getLong("creator"), res.getLong("revcreator")) {
			private static final long serialVersionUID = 1L;

			@Override
			public String getSerializeName() {
				return "privateEntityImpl";
			}
		};
		
		rc._setCreatorId(res.getLong("creator"));
		rc._setRevCreatorId(res.getLong("revcreator"));

		return rc;
	}
	
	// insert a new entity revision
	private void _newRevision(Entity e) throws SQLException, NotFoundException, RevisionCheckException {
		PreparedStatement pstmt;
		ResultSet res;
		int rev = e.getRev();
		
		db.begin(); // begin transaction
		
		// first check that the new revision doesn't exist yet
		pstmt = db.prepareStatement("SELECT current_revision FROM entity WHERE ent_id = ?");
		pstmt.setLong(1, e.getId());
		res = pstmt.executeQuery();
		if (!res.next()) throw new NotFoundException("Couldn't find entity "+e.getId());
		if (res.getInt("current_revision") != rev++) throw new RevisionCheckException("Revision out of date: "+rev);
		
		// create new EntityRevision element
		pstmt = db.prepareStatement("INSERT INTO entityRevision (ent_id, rev, title, creator) VALUES (?, ?, ?, ?)");
		pstmt.setLong(1, e.getId());
		pstmt.setInt(2, rev);
		pstmt.setString(3, e.getTitle());
		pstmt.setLong(4, e.getRevCreatorId());
		pstmt.executeUpdate();

		// update current_revision in entity
		pstmt = db.prepareStatement("UPDATE entity SET current_revision = ? WHERE ent_id = ?");
		pstmt.setInt(1, rev);
		pstmt.setLong(2, e.getId());
		pstmt.executeUpdate();
		
		// increment revision in e
		e._setRev(e.getRev()+1);
		
		db.commit();
	}
	
	private void _newEntity(Entity e) throws SQLException, RevisionCheckException, InvalidLocaleException {
		PreparedStatement pstmt;
		ResultSet res;
		
		db.begin(); // start transaction

		if (e.getRev() != 0) throw new RevisionCheckException("new entities have to use revision 0");
		
		// check localeId
		localeDAO.require(e.getLocaleId());
		
		// create entity
		String sql;
		if (e.getCreatorId() == null) {
			sql = "INSERT INTO entity (locale_id, current_revision, creator) VALUES (?, 1, currval('entity_ent_id_seq'))";
		}
		else sql = "INSERT INTO entity (locale_id, current_revision, creator) VALUES (?, 1, ?)";
		pstmt = db.prepareStatement(sql);
		pstmt.setLong(1, e.getLocaleId());
		if (e.getCreatorId() != null) pstmt.setLong(2, e.getCreatorId());
		pstmt.executeUpdate();

		// Query current ent_id
		pstmt = db.prepareStatement("SELECT currval('entity_ent_id_seq') AS ent_id");
		res = pstmt.executeQuery();
		res.next();

		long id = res.getLong("ent_id");

		if (e.getCreatorId() == null) {
			e._setCreatorId(id);
			e._setRevCreatorId(id);
		}

		// create entityRevision
		sql = "INSERT INTO entityRevision(ent_id, rev, title, creator) VALUES "
			+"(currval('entity_ent_id_seq'), ?, ?, ?)";
		pstmt = db.prepareStatement(sql);
		pstmt.setInt(1, e.getRev()+1);
		pstmt.setString(2, e.getTitle());
		pstmt.setLong(3, e.getRevCreatorId());
		pstmt.executeUpdate();
		
		db.commit();

		e._setId(id);
		e._setRev(1);
	}
}
