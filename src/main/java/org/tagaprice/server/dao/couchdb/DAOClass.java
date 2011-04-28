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

/**
 * Base class for all the other CouchDB DAO classes
 * @param <T> Datatype of the entities that will be queried
 */
public class DAOClass<T extends ASimpleEntity> implements IDAOClass<T> {
	/// JCouchDB server object
	private Server m_server;
	
	/// JCouchDB database object
	protected Database m_db;
	
	/// Logger instance
	private MyLogger m_logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	/// CouchDB connection properties
	Properties m_properties;
	
	/// Metaclass object of the entities that will be queried
	Class<? extends T> m_class;
	
	/**
	 * Entity type name (e.g. "product", "shop", ...)
	 * This is stored in each CouchDB document so that we can find out of which type they are
	 */
	String m_entityType;
	
	/**
	 * Constructor
	 * @param classObject Class object necessary to instantiate new objects when get() gets called 
	 * @param objectType type name (e.g. "product", "shop", ...)
	 */
	protected DAOClass(Class<? extends T> classObject, String objectType) {
		InitialInjector injector = new InitialInjector();
		String dbName;

		m_class = classObject;
		m_entityType = objectType;

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
	
	/**
	 * Read the CouchDB connection configuration
	 * @param filename file that should be read
	 * @return Properties object containing the configuration
	 * @throws IOException if the config file couldn't be read
	 */
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

	/**
	 * Create a new document in the CouchDB
	 * @param entity Entity to be persisted
	 * @return entity (the same object as passed in the parameter)
	 */
	@Override
	public T create(T entity) {
		entity.setEntityType(m_entityType);
		m_db.createDocument(entity);
		return entity;
	}

	/**
	 * Request a specific revision of a document from the database
	 * @param id Document ID
	 * @param revision Document revision (if it's null, the current revision will be queried) 
	 * @return Requested CouchDB document squeezed into a Entity object 
	 */
	@Override
	public T get(String id, String revision) throws DaoException {
		T rc = m_db.getDocument(m_class, id);
		if (!rc.getEntityType().equals(m_entityType)) throw new TypeMismatchException("Requested type ('"+m_entityType+"') doesn't match actual type: '"+rc.getEntityType()+"'");
		return rc;
	}
	
	/**
	 * Request a document from the database
	 * @param id Document ID
	 * @return Requested CouchDB document squeezed into a Entity object 
	 */
	@Override
	public T get(String id) throws DaoException {
		return get(id, null);
	}

	/**
	 * Update a CouchDB document
	 * @param entity Entity to persist
	 * @return entity (the same object as passed in the parameter)
	 */
	@Override
	public T update(T entity) {
		entity.setEntityType(m_entityType);
		m_db.updateDocument(entity);
		return entity;
	}

	/**
	 * Delete an Entity from the database
	 * @param entity Entity to remove
	 */
	@Override
	public void delete(T entity) {
		m_db.delete(entity);
	}
}
