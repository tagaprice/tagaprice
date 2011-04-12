package org.tagaprice.server.dao.couchdb;

import org.jcouchdb.db.Database;
import org.jcouchdb.db.Server;
import org.jcouchdb.db.ServerImpl;
import org.tagaprice.server.dao.IDAOClass;
import org.tagaprice.shared.entities.ASimpleEntity;

public class DAOClass<T extends ASimpleEntity> implements IDAOClass<T> {
	private Server m_server;
	protected Database m_db;
	Class<? extends T> m_class;
	String m_prefixedDBName;
	
	protected DAOClass(String dbPrefix, Class<? extends T> classObject, String dbName) {
		m_server = new ServerImpl("localhost");
		m_prefixedDBName = dbPrefix+dbName;
		m_class = classObject;

		// TODO use server.setCredentials() to authenticate
		createDB();
	}
	
	void createDB() {
		if (!hasDB()) {
			m_server.createDatabase(m_prefixedDBName);
			m_db = new Database(m_server, m_prefixedDBName);
			setupDB();
		}
		else {
			m_db = new Database(m_server, m_prefixedDBName);
		}
	}
	
	void deleteDB() {
		if (m_db != null) {
			Server server = m_db.getServer();
			server.deleteDatabase(m_prefixedDBName);
			m_db = null;
		}
	}
	
	boolean hasDB() {
		return m_server.listDatabases().contains(m_prefixedDBName);
	}
	
	/**
	 * Empty method (overload it if you want some injection-like stuff to be done upon DB creation) 
	 */
	protected void setupDB() {
	}

	@Override
	public T create(T entity) {
		m_db.createDocument(entity);
		return entity;
	}

	@Override
	public T get(String id, String revision) {
		return m_db.getDocument(m_class, id);
	}
	
	@Override
	public T get(String id) {
		return get(id, null);
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
