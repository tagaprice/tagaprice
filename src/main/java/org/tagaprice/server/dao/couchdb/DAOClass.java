package org.tagaprice.server.dao.couchdb;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jcouchdb.db.Database;
import org.jcouchdb.db.Server;
import org.jcouchdb.db.ServerImpl;
import org.tagaprice.server.dao.IDAOClass;
import org.tagaprice.server.rpc.ProductServiceImpl;
import org.tagaprice.shared.entities.ASimpleEntity;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;

public class DAOClass<T extends ASimpleEntity> implements IDAOClass<T> {
	private Server m_server;
	protected Database m_db;
	private MyLogger m_logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	/// CouchDB connection properties
	Properties m_properties;
	
	Class<? extends T> m_class;
	String m_prefixedDBName;
	
	protected DAOClass(String dbPrefix, Class<? extends T> classObject, String dbName) {
		m_prefixedDBName = dbPrefix+dbName;
		m_class = classObject;

		try {
			m_properties = _readProperties("couchdb.properties");
		}
		catch (IOException e) {
			m_logger.log("Error while reading couchdb.properties!");
			e.printStackTrace();
		}
		
		m_server = new ServerImpl(
				m_properties.getProperty("host", "localhost"),
				Integer.parseInt(m_properties.getProperty("port", "5984")),
				Boolean.parseBoolean(m_properties.getProperty("ssl", "false"))); // we don't need ssl on localhost

			
		// TODO use server.setCredentials() to authenticate
		createDB();
	}
	
	private Properties _readProperties(String filename) throws IOException {
		Properties defaults = new Properties();
		try {
			InputStream stream = getClass().getResourceAsStream(filename+".default");
			if (stream != null) defaults.load(stream);			
		}
		catch (IOException e) { /* ignore if we can't read the default config as long as we can read the specific one */ }
		Properties rc = new Properties(defaults);
		InputStream stream = getClass().getResourceAsStream(filename); 
		if (stream != null) rc.load(stream);
		else throw new IOException("Couldn't load resource file '"+filename+"'. Make sure it exists and is accessible");
		return rc;
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
		m_db.updateDocument(entity);
		return entity;
	}

	@Override
	public void delete(T entity) {
		m_db.delete(entity);
	}
}
