package org.tagaprice.server.dao.couchdb;

import java.io.IOException;
import java.util.Properties;

import org.jcouchdb.db.Database;
import org.jcouchdb.db.Server;
import org.tagaprice.server.dao.IDaoClass;
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
public class DaoClass<T extends ASimpleEntity> implements IDaoClass<T> {
	/// JCouchDB server object
	private Server m_server;
	
	/// JCouchDB database object
	protected Database m_db;
	
	/// Logger instance
	private MyLogger m_logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	/// CouchDB connection properties
	private Properties m_properties;
	
	/// Metaclass object of the entities that will be queried
	private Class<? extends T> m_class;
	
	private DaoClass<? super T> m_superClassDao = null; 
	
	/**
	 * Entity type name (e.g. "product", "shop", ...)
	 * This is stored in each CouchDB document so that we can find out of which type they are
	 */
	private String m_entityType;
	
	/**
	 * Constructor
	 * @param classObject Class object necessary to instantiate new objects when get() gets called 
	 * @param objectType type name (e.g. "product", "shop", ...)
	 */
	protected DaoClass(Class<? extends T> classObject, String objectType, DaoClass<? super T> superClassDao) {
		InitialInjector injector = new InitialInjector();
		String dbName;

		m_class = classObject;
		m_entityType = objectType;
		m_superClassDao = superClassDao;

		try {
			m_properties = CouchDBDaoFactory.getConfiguration();
		}
		catch (IOException e) {
			m_logger.log("Error while reading couchdb.properties!");
			e.printStackTrace();
			return;
		}
		
		dbName = m_properties.getProperty("database", "tagaprice");
		
		m_server = CouchDBDaoFactory.getServerObject(m_properties);

		try {
			injector.init(m_server, dbName);
		} catch (IOException e) {
			m_logger.log("Error while initializing CouchDB");
			e.printStackTrace();
		}
		m_db = new Database(m_server, dbName);
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

		// inject fields (recursively for all superClassDaos)
		DaoClass<? super T> daoClass = this;
		while (daoClass != null) {
			daoClass._injectFields(rc);
			daoClass = daoClass._getSuperClassDao();
		}
		
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
	
	/**
	 * Returns the name of the CouchDB Database involved
	 * @return
	 */
	public String getDbName() {
		return m_db.getName();
	}
	
	/**
	 * Callback method that's called by get() for the class itself and its superclass hierarchy (using _getSuperClassDao()) 
	 * @param entity Entity that wants its fields being injected
	 * @throws DaoException if there was any problem when injecting the fields
	 */
	protected void _injectFields(T entity) throws DaoException {
		// do nothing here
	}
	
	/**
	 * Returns the super DAO class passed to the DaoClass' constructor
	 * @return super class' DAO or null
	 */
	private DaoClass<? super T> _getSuperClassDao() {
		return m_superClassDao;
	}
}
