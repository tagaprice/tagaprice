package org.tagaprice.server.dao.couchdb;

import org.jcouchdb.db.Database;
import org.tagaprice.server.dao.IDAOClass;
import org.tagaprice.shared.entities.ISEntity;
import org.tagaprice.shared.entities.IRevisionId;

public class DAOClass<T extends ISEntity> implements IDAOClass<T> {
	protected Database m_db;
	Class<? extends T> m_class;
	
	protected DAOClass(Class<? extends T> classObject, String dbName) {
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
		return m_db.getDocument(m_class, id.getId());
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
