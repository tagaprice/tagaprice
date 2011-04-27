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
import org.tagaprice.shared.exceptions.dao.DaoException;
import org.tagaprice.shared.exceptions.dao.TypeMismatchException;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;

public class DAOClass<T extends ASimpleEntity> implements IDAOClass<T> {
	private Server m_server;
	protected Database m_db;
	private MyLogger m_logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	/// CouchDB connection properties
	Properties m_properties;
	
	Class<? extends T> m_class;
	String m_objectType;
	
	protected DAOClass(String dbPrefix, Class<? extends T> classObject, String objectType) {
		InitialInjector injector = new InitialInjector();
		String dbName;

		m_class = classObject;

		try {
			m_properties = _readProperties("couchdb.properties");
		}
		catch (IOException e) {
			m_logger.log("Error while reading couchdb.properties!");
			e.printStackTrace();
			return;
		}
		
		dbName = m_properties.getProperty("database", "tagaprice");
		
		m_server = new ServerImpl(
				m_properties.getProperty("host", "localhost"),
				Integer.parseInt(m_properties.getProperty("port", "5984")),
				Boolean.parseBoolean(m_properties.getProperty("ssl", "false"))); // we don't need ssl on localhost

		try {
			injector.init(m_server, dbName);
		} catch (IOException e) {
			m_logger.log("Error while initializing CouchDB");
			e.printStackTrace();
		}
		m_db = new Database(m_server, dbName);
	}
	
	private Properties _readProperties(String filename) throws IOException {
		Properties defaults = new Properties();
		try {
			InputStream stream = getClass().getResourceAsStream(filename+".default");
			if (stream != null) defaults.load(stream);
		}
		catch (IOException e) { /* ignore if we can't read the default config as long as we can read the specific one */ }
		Properties rc = new Properties(defaults);
		InputStream stream = getClass().getResourceAsStream("/"+filename); 
		if (stream != null) rc.load(stream);
		else throw new IOException("Couldn't load resource file '"+filename+"'. Make sure it exists and is accessible");
		return rc;
	}

	@Override
	public T create(T entity) {
		m_db.createDocument(entity);
		return entity;
	}

	@Override
	public T get(String id, String revision) throws DaoException {
		T rc = m_db.getDocument(m_class, id);
		if (!rc.getTypeName().equals(m_objectType)) throw new TypeMismatchException("Requested type ('"+m_objectType+"') doesn't match actual type: '"+rc.getTypeName()+"'");
		return rc;
	}
	
	@Override
	public T get(String id) throws DaoException {
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
