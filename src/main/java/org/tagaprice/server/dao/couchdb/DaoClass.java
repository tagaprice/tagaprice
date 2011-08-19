package org.tagaprice.server.dao.couchdb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jcouchdb.db.Database;
import org.jcouchdb.db.Server;
import org.tagaprice.server.dao.IDaoClass;
import org.tagaprice.server.dao.couchdb.elasticsearch.ElasticSearchClient;
import org.tagaprice.server.dao.couchdb.elasticsearch.result.Hit;
import org.tagaprice.server.dao.couchdb.elasticsearch.result.SearchResult;
import org.tagaprice.shared.entities.ADocument;
import org.tagaprice.shared.exceptions.dao.DaoException;
import org.tagaprice.shared.exceptions.dao.TypeMismatchException;
import com.allen_sauer.gwt.log.client.Log;

/**
 * Base class for all the other CouchDB DAO classes
 * @param <T> Datatype of the documents that will be queried
 */
public class DaoClass<T extends ADocument> implements IDaoClass<T> {
	/// JCouchDB server object
	private Server m_server;

	/// ElasticSearch client object
	private ElasticSearchClient m_searchClient;

	/// JCouchDB database object
	protected Database m_db;


	/// CouchDB connection properties
	private CouchDbConfig m_dbConfig;

	private CouchDbDaoFactory m_daoFactory;

	/// Meta class object of the documents that will be queried
	private Class<? extends T> m_class;

	private DaoClass<? super T> m_superClassDao = null;

	/**
	 * Document type name (e.g. "product", "shop", ...)
	 * This is stored in each CouchDB document so that we can find out of which type they are
	 */
	private String m_docType;

	/**
	 * Constructor
	 * @param classObject Class object necessary to instantiate new objects when get() gets called
	 * @param objectType type name (e.g. "product", "shop", ...)
	 */
	protected DaoClass(CouchDbDaoFactory daoFactory, Class<? extends T> classObject, String objectType, DaoClass<? super T> superClassDao) {
		String dbName;

		m_class = classObject;
		m_docType = objectType;
		m_superClassDao = superClassDao;
		m_daoFactory = daoFactory;

		try {
			m_dbConfig = CouchDbDaoFactory.getConfiguration();
		}
		catch (IOException e) {
			Log.debug("Error while reading couchdb.properties!");
			e.printStackTrace();
			return;
		}

		dbName = m_dbConfig.getCouchDatabase();

		m_server = CouchDbDaoFactory.getServerObject(m_dbConfig);

		m_db = new Database(m_server, dbName);
	}

	/**
	 * Create a new document in the CouchDB
	 * @param document Document to be persisted
	 * @return document (the same object as passed in the parameter)
	 * @throws DaoException if anything went wrong while storing the Document
	 */
	@Override
	public T create(T document) throws DaoException {
		document.setDocType(m_docType);

		// check if the creator exists
		_checkCreatorId(document.getCreatorId());

		// do the saving
		m_db.createDocument(document);

		return document;
	}

	public List<T> find(String query) throws DaoException {
		if (m_searchClient == null) {
			try {
				m_searchClient = new ElasticSearchClient(CouchDbDaoFactory.getConfiguration());
			}
			catch (IOException e) {
				throw new DaoException("Error while fetching the database configuration!", e);
			}
		}

		SearchResult searchResult = m_searchClient.find(query, m_docType, 50);
		List<T> rc = new ArrayList<T>();

		for (Hit hit: searchResult.getHits().getHits()) {
			/// TODO find a way to avoid calling get() here (we should be able to use hit.getSource() directly)
			T item = get(hit.getId());
			if (item != null) rc.add(item);
		}

		return rc;
	}

	/**
	 * Request a specific revision of a document from the database
	 * @param id Document ID
	 * @param revision Document revision (if it's null, the current revision will be queried)
	 * @return Requested CouchDB document squeezed into a Document object
	 */
	@Override
	public T get(String id, String revision) throws DaoException {
		T rc = m_db.getDocument(m_class, id);
		if (!rc.getDocType().equals(m_docType)) throw new TypeMismatchException("Requested type ('"+m_docType+"') doesn't match actual type: '"+rc.getDocType()+"'");

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
	 * @return Requested CouchDB document squeezed into a Document object
	 */
	@Override
	public T get(String id) throws DaoException {
		return get(id, null);
	}

	/**
	 * Update a CouchDB document
	 * @param document Document to persist
	 * @return document (the same object as passed in the parameter)
	 * @throws DaoException if there was an error while saving the Document
	 */
	@Override
	public T update(T document) throws DaoException {
		document.setDocType(m_docType);

		// check if the creator exists
		_checkCreatorId(document.getCreatorId());

		// do the saving
		m_db.updateDocument(document);

		return document;
	}

	/**
	 * Delete an Document from the database
	 * @param document Document to remove
	 */
	@Override
	public void delete(T document) {
		m_db.delete(document);
	}

	/**
	 * Returns the name of the CouchDB Database involved
	 * @return
	 */
	public String getDbName() {
		return m_db.getName();
	}

	/**
	 * Checks if the creatorId != null and if it's valid (by using UserDao.get())
	 * 
	 * This method is overloaded by UserDao because User objects <em>must</em> have a null creator
	 * @param creatorId Creator UID to check
	 * @throws DaoException if the creatorId is null or is no valid user ID
	 */
	protected void _checkCreatorId(String creatorId) throws DaoException {
		// A valid creator is mandatory for all Documents except for User objects
		if (creatorId == null) throw new DaoException("The creator must not be null when creating or updating a Document!!!");

		// check if the creator exists
		m_daoFactory.getUserDao().get(creatorId);
	}

	/**
	 * Callback method that's called by get() for the class itself and its superclass hierarchy (using _getSuperClassDao())
	 * @param document Document that wants its fields being injected
	 * @throws DaoException if there was any problem when injecting the fields
	 */
	protected void _injectFields(T document) throws DaoException {
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
