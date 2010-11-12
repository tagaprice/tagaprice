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

import org.apache.log4j.Logger;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.interfaces.IEntityDAO;
import org.tagaprice.server.dao.postgres.LocaleDAO;
import org.tagaprice.shared.Entity;
import org.tagaprice.shared.exception.DAOException;
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
public class EntityDAO implements IEntityDAO {
	protected DBConnection db;
	private LocaleDAO localeDAO;
	private PropertyDAO propertyDAO;
	private static Logger log = Logger.getLogger(EntityDAO.class);

	/**
	 * Constructor that provides an easy way to supply a modified DBConnection object
	 * (e.g. with auto-rollback for unit test cases) 
	 * @param db DBConnection object
	 */
	public EntityDAO(DBConnection db) {
		this.db = db;
		this.localeDAO = new LocaleDAO(db);
		this.propertyDAO = new PropertyDAO(db);
	}
	
	@Override
	public <T extends Entity> T getById(T entity, long id) throws DAOException {
		return getByIdAndRev(entity, id, 0);
	}
	
	@Override
	public <T extends Entity> T getByIdAndRev(T entity, long id, long rev) throws DAOException {
		log.debug("entity:"+entity);
		log.debug("id:"+id);
		log.debug("rev:"+rev);
		
		String sql = "SELECT e.ent_id, rev, title, locale_id, e.creator, r.creator AS revCreator " +
		" FROM entity e INNER JOIN entityRevision r ON (e.ent_id = r.ent_id";
		if (rev == 0) 
			sql += " AND e.current_revision = r.rev";
		sql += ") WHERE e.ent_id = ?";
		if (rev > 0) sql += " AND r.rev = ?";

		try {
			PreparedStatement pstmt = db.prepareStatement(sql);
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
			// get properties
			propertyDAO.get(entity);
			return entity;
		} catch (SQLException e) {
			String msg = "Failed to retrieve entity from database. SQLException: "+e.getMessage()+".";
			log.error(msg + " Chaining and rethrowing.");
			log.debug(e.getStackTrace());
			throw new DAOException(msg, e);
		} catch (NotFoundException e) {
			// TODO clean up NotFoundExceptions in propertyDAO
			e.printStackTrace();
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	@Override
	public boolean save(Entity entity) throws DAOException {
		try {
			if (entity.getId() != null) { // try to update an existing entity
				// first re-fetch the Entity and then compare them to see if something has changed
				// ent_id, locale_id and created_at are read only, therefore will not be checked or saved
				Entity eCompare = getEntity(entity.getId());
				if (!entity.equals(eCompare)) { // it should not be possible to save a entity under the wrong ent_id
					// they differ => save changes
					newRevision(entity);
				}
			} else {
				newEntity(entity);
			}
			propertyDAO.save(entity);
			return true;
		} catch (SQLException e) {
			String msg = "Failed to save entity. SQLException: "+e.getMessage()+".";
			log.debug(e.getStackTrace());
			log.error(msg+" Chaining and rethrowing.");
			throw new DAOException(msg, e);
		}  catch (NotFoundException e) {
			String msg = "Failed to save entity. NotFoundException: "+e.getMessage()+".";
			log.debug(e.getStackTrace());
			log.error(msg+" Chaining and rethrowing.");
			throw new DAOException(msg, e);
		} catch (InvalidLocaleException e) {
			String msg = "Failed to save entity. InvalidLocaleException: "+e.getMessage()+".";
			log.debug(e.getStackTrace());
			log.error(msg+" Chaining and rethrowing.");
			throw new DAOException(msg, e);
		} catch (RevisionCheckException e) {
			String msg = "Failed to save entity. RevisionCheckException: "+e.getMessage()+".";
			log.debug(e.getStackTrace());
			log.error(msg+" Chaining and rethrowing.");
			throw new DAOException(msg, e);
		}
	}
	
	
	protected void resolveCreator(Entity e) throws NotFoundException, SQLException {
		throw new NotFoundException("Creator not found: null");
	}
	
	private Entity getEntity(long id) throws DAOException {
		Entity rc = new Entity(id) {
			private static final long serialVersionUID = 1L;

			@Override
			public String getSerializeName() {
				return null;
			}
		};
		
		return getById(rc, id);
	}
	
	private void newRevision(Entity e) throws SQLException, NotFoundException, RevisionCheckException {
		PreparedStatement pstmt;
		ResultSet res;
		int rev = e.getRev();
		
		db.begin(); // begin transaction
		
		// first check that the new revision doesn't exist yet
		pstmt = db.prepareStatement("SELECT current_revision FROM entity WHERE ent_id = ?");
		pstmt.setLong(1, e.getId());
		res = pstmt.executeQuery();
		//TODO next two lines is messy/hard to understand code => restructure + comment
		if (!res.next()) throw new NotFoundException("Couldn't find entity with id:"+e.getId());
		if (res.getInt("current_revision") != rev++) throw new RevisionCheckException("Revision out of date: "+rev); 
//		proposal: create tests first
//		if (res.getInt("current_revision") != rev) throw new RevisionCheckException("Revision out of date: "+rev);
//		rev++;
		
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
		e.setRev(e.getRev()+1);
		
		db.commit();
	}
	
	private void newEntity(Entity e) throws SQLException, RevisionCheckException, InvalidLocaleException, NotFoundException {
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
		e._setId(id);

		if (e.getCreatorId() == null) resolveCreator(e); //TODO messy code

		// create entityRevision
		sql = "INSERT INTO entityRevision(ent_id, rev, title, creator) VALUES "
			+"(currval('entity_ent_id_seq'), ?, ?, ?)";
		pstmt = db.prepareStatement(sql);
		pstmt.setInt(1, e.getRev()+1);
		pstmt.setString(2, e.getTitle());
		pstmt.setLong(3, e.getRevCreatorId());
		pstmt.executeUpdate();

		db.commit();
		e.setRev(1);
	}
	
}
