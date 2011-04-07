package org.tagaprice.server.dao.couchdb;

import org.jcouchdb.db.Database;
import org.tagaprice.server.dao.IDAOClass;
import org.tagaprice.shared.entities.ISEntity;
import org.tagaprice.shared.entities.IRevisionId;

public class DAOClass<T extends ISEntity> implements IDAOClass<T> {
	protected Database m_db;
	Class<T> m_class;
	
	protected DAOClass(Class<T> classObject, String dbName) {
		m_db = new Database("localhost", dbName);
		m_class = classObject;
	}

	@Override
	public T create(T entity) {
		m_db.createDocument(entity);
		return entity;
	}

	@Override
	public T get(IRevisionId id) {
		m_db.getDocument(m_class, id.getId());
		return null;
	}

	@Override
	public T update(T entity) {
		m_db.createDocument(entity);
		return entity;
	}

	@Override
	public void delete(T entity) {
		m_db.delete(entity);
	}
}
