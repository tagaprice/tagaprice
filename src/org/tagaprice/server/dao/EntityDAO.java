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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.Entity;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;

public abstract class EntityDAO {
	protected DBConnection db;
	public EntityDAO() throws FileNotFoundException, IOException {
		db = new DBConnection();
	}
	
	/**
	 * Constructor that provides an easy way to supply a modified DBConnection object
	 * (e.g. with auto-rollback for unit test cases) 
	 * @param db DBConnection object
	 */
	public EntityDAO(DBConnection db) {
		this.db = db;
	}
	
	public boolean get(Entity e) throws SQLException {
		String sql = "SELECT e.ent_id, rev, title, locale_id" +
			" FROM entity e INNER JOIN entityRevision r ON (e.ent_id = r.ent_id AND e.current_revision = r.rev" +
			" WHERE ent_id = ?";
		boolean rc = false;
		
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setLong(1, e.getId());
		
		ResultSet res = pstmt.executeQuery();
		
		rc = res.next();
		e.setTitle(res.getString("title"));
		e._setRev(res.getInt("rev"));
		e._setLocaleId(res.getInt("locale_id"));
		
		
		return rc;
	}
	
	public void save(Entity entity) throws SQLException, NotFoundException, RevisionCheckException, InvalidLocaleException {
		if (entity.getId() != null) { // try to update an existing entity
			// first re-fetch the Entity and then compare them to see if something has changed
			// ent_id, locale_id and created_at are read only, therefore will not be checked or saved
			
			Entity eCompare = _getEntity(entity.getId());
			//if (!entity.equals(eCompare)) { // it should not be possible to save a entity under the wrong ent_id
			if (eCompare.getRev() != entity.getRev()) {
				// they differ => save changes
				_newRevision(entity);
			}
			
			
		}
		else { // create new one
			_newEntity(entity);
		}
		
	}
	
	private Entity _getEntity(long id) throws SQLException, NotFoundException {
		Entity rc = null;
		String sql = "SELECT e.ent_id, e.created_at AS entityDate, r.created_at AS revDate, rev, title, locale_id" +
			" FROM entity e INNER JOIN entityRevision r" +
			" ON (e.ent_id = r.ent_id AND e.current_revision = r.rev)" +
			" WHERE e.ent_id = ?";
		
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setLong(1, id);
		ResultSet res = pstmt.executeQuery();
		
		if (!res.next()) throw new NotFoundException("didn't find entity with the ID "+id);
		
		rc = new Entity(res.getLong("ent_id"), res.getInt("rev"), res.getString("title"), res.getInt("locale_id")) {
			private static final long serialVersionUID = 1L;

			@Override
			public String getSerializeName() {
				return "privateEntityImpl";
			}
		};

		return rc;
	}
	
	// insert a new entity revision
	private void _newRevision(Entity e) throws SQLException, NotFoundException, RevisionCheckException {
		PreparedStatement pstmt;
		ResultSet res;
		int rev = e.getRev();
		
		db.setAutoCommit(false); // begin transaction
		
		// first check that the new revision doesn't exist yet
		pstmt = db.prepareStatement("SELECT current_revision FROM entity WHERE ent_id = ?");
		pstmt.setLong(1, e.getId());
		res = pstmt.executeQuery();
		if (!res.next()) throw new NotFoundException("Couldn't find entity "+e.getId());
		if (res.getInt("current_revision") != rev++) throw new RevisionCheckException("Revision out of date: "+rev);
		
		// create new EntityRevision element
		pstmt = db.prepareStatement("INSERT INTO entityRevision (ent_id, rev, title) VALUES (?,?,?)");
		pstmt.setLong(1, e.getId());
		pstmt.setInt(2, rev);
		pstmt.setString(3, e.getTitle());

		// update current_revision in entity
		pstmt = db.prepareStatement("UPDATE entity SET current_revision = ?");
		pstmt.setInt(1, rev);
		pstmt.execute();
		
		db.commit();
		db.setAutoCommit(true);
	}
	
	private void _newEntity(Entity e) throws SQLException, RevisionCheckException, InvalidLocaleException {
		PreparedStatement pstmt;
		ResultSet res;
		
		db.setAutoCommit(false); // start transaction

		if (e.getRev() != 0) throw new RevisionCheckException("new entities have to use revision 0");
		
		// check localeId
		pstmt = db.prepareStatement("SELECT COUNT(*) FROM locale WHERE locale_id = ?");
		pstmt.setInt(1, e.getLocaleId());
		res = pstmt.executeQuery();
		res.next();
		if (res.getInt(1) == 0) throw new InvalidLocaleException("Locale id "+e.getLocaleId()+" not found");
		
		// create entity
		pstmt = db.prepareStatement("INSERT INTO entity (locale_id, current_revision) VALUES (?, ?)");
		pstmt.setLong(1, e.getLocaleId());
		pstmt.setInt(2, e.getRev()+1);
		pstmt.execute();
		
		// create entityRevision
		pstmt = db.prepareStatement("INSERT INTO entityRevision (ent_id, rev, title) VALUES (currval('entity_ent_id_seq'),?,?)");
		pstmt.setInt(1, e.getRev()+1);
		pstmt.setString(2, e.getTitle());
		pstmt.execute();
		
		pstmt = db.prepareStatement("SELECT currval('entity_ent_id_seq') AS ent_id");
		res = pstmt.executeQuery();
		e._setId(res.getLong("ent_id"));

		db.commit();
		db.setAutoCommit(true);
	}
}
