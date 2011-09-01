package org.tagaprice.server.dao.couchdb;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import javax.servlet.ServletException;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.jcouchdb.db.Server;
import org.jcouchdb.db.ServerImpl;
import org.tagaprice.server.dao.ICategoryDao;
import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IPackageDao;
import org.tagaprice.server.dao.IProductDao;
import org.tagaprice.server.dao.IReceiptDao;
import org.tagaprice.server.dao.ISearchDao;
import org.tagaprice.server.dao.ISessionDao;
import org.tagaprice.server.dao.IShopDao;
import org.tagaprice.server.dao.IStatisticDao;
import org.tagaprice.server.dao.IUnitDao;
import org.tagaprice.server.dao.couchdb.elasticsearch.ElasticSearchClient;
import org.tagaprice.shared.exceptions.dao.DaoException;

import com.allen_sauer.gwt.log.client.Log;


/**
 * IDaoFactory implementation providing a CouchDB persistence layer
 */
public class CouchDbDaoFactory implements IDaoFactory {
	private static CouchDbConfig m_dbConfig = null;
	private static Server m_server = null;

	private ICategoryDao m_productCategoryDao = null;
	private ICategoryDao m_shopCategoryDao = null;
	private IPackageDao m_packageDao = null;
	private IProductDao m_productDao = null;
	private IReceiptDao m_receiptDao = null;
	private ISearchDao m_searchDao = null;
	private ISessionDao m_sessionDao = null;
	private IShopDao m_shopDao = null;
	private IUnitDao m_unitDao = null;
	private UserDao m_userDao = null;
	private StatisticDao m_statisticDao = null;
	private DocumentDao m_documentDao = null;
	
	private ElasticSearchClient m_elasticSearchClient;


	static CouchDbConfig getConfiguration() throws IOException {
		if (CouchDbDaoFactory.m_dbConfig == null) {
			CouchDbDaoFactory.m_dbConfig = _readProperties("couchdb.properties");
		}
		return CouchDbDaoFactory.m_dbConfig;
	}

	static void _setConfiguration(CouchDbConfig dbConfig) {
		CouchDbDaoFactory.m_dbConfig = dbConfig;
	}

	static Server getServerObject(CouchDbConfig dbConfig) {
		if (CouchDbDaoFactory.m_server == null) {
			String host = dbConfig.getCouchHost();
			int port = dbConfig.getCouchPort();
			boolean useSsl = dbConfig.getCouchSsl();
			CouchDbDaoFactory.m_server = new ServerImpl(host, port, useSsl);

			if (dbConfig.hasLoginData()) {
				String user = dbConfig.getCouchUser();
				String password = dbConfig.getCouchPassword();

				AuthScope authScope = new AuthScope(host, port);
				Credentials credentials = new UsernamePasswordCredentials(user, password);
				Log.debug("Connecting to the CouchDB server at '"+host+":"+port+"' as user '"+user+"'");
				CouchDbDaoFactory.m_server.setCredentials(authScope, credentials);
			}
			else {
				Log.debug("Connecting anonymously to the CouchDB server at '"+host+":"+port+"'");
			}

			if (useSsl) Log.debug("using SSL");
		}
		return CouchDbDaoFactory.m_server;
	}

	static Server getServerObject() throws IOException {
		return getServerObject(getConfiguration());
	}

	/**
	 * Read a CouchDB connection configuration file
	 * @param filename file that should be read
	 * @return Properties object containing the configuration
	 * @throws IOException if the config file couldn't be read
	 */
	private static CouchDbConfig _readProperties(String filename) throws IOException {
		CouchDbConfig rc = new CouchDbConfig();
		InputStream stream = CouchDbDaoFactory.class.getResourceAsStream("/"+filename);
		if (stream != null) {
			Log.debug("Reading configuration file '"+filename+"'");
			rc.load(stream);
		}
		else  {
			Log.error("Couldn't read configuration file '"+filename+"'");
			throw new IOException("Couldn't load resource file '"+filename+"'. Make sure it exists and is accessible");
		}

		return rc;
	}

	/**
	 * Default DAO factory constructor
	 */
	public CouchDbDaoFactory() throws DaoException {
		try {
			m_elasticSearchClient = new ElasticSearchClient(getConfiguration());
		}
		catch (IOException e) {
			throw new DaoException("Error while creating the search index!", e);
		}
		catch (URISyntaxException e) {
			throw new DaoException("Error while loading some search resources: "+e.getMessage(), e);
		}
	}

	public ElasticSearchClient getElasticSearchClient() {
		return m_elasticSearchClient;
	}
	
	@Override
	public ICategoryDao getProductCategoryDao() {
		if (m_productCategoryDao == null) {
			m_productCategoryDao = new CategoryDao(this,"productCategory");
		}
		return m_productCategoryDao;
	}

	@Override
	public IPackageDao getPackageDao() {
		if (m_packageDao == null) {
			m_packageDao = new PackageDao(this);
		}
		return m_packageDao;
	}

	@Override
	public IProductDao getProductDao() {
		if (m_productDao == null) {
			m_productDao = new ProductDao(this);
		}
		return m_productDao;
	}

	@Override
	public IReceiptDao getReceiptDao() {
		if (m_receiptDao == null) {
			m_receiptDao = new ReceiptDao(this);
		}
		return m_receiptDao;
	}
	
	@Override
	public ISearchDao getSearchDao() {
		if (m_searchDao == null) {
			m_searchDao = new SearchDao(this);
		}
		return m_searchDao;
	}

	@Override
	public ISessionDao getSessionDao() {
		if (m_sessionDao == null) {
			m_sessionDao = new SessionDao(this);
		}
		return m_sessionDao;
	}

	@Override
	public IShopDao getShopDao() {
		if (m_shopDao == null) {
			m_shopDao = new ShopDao(this);
		}
		return m_shopDao;
	}

	@Override
	public IUnitDao getUnitDao() {
		if (m_unitDao == null) {
			m_unitDao = new UnitDao(this);
		}
		return m_unitDao;
	}

	@Override
	public UserDao getUserDao() {
		if (m_userDao == null) {
			m_userDao = new UserDao(this);
		}
		return m_userDao;
	}

	DocumentDao _getDocumentDao() {
		if (m_documentDao == null) {
			m_documentDao = new DocumentDao(this);
		}
		return m_documentDao;
	}

	@Override
	public void init() throws ServletException {
		InitialInjector injector = new InitialInjector();
		String dbName;
		try {
			dbName = getConfiguration().getCouchDatabase();
			injector.init(getServerObject(), dbName);
		} catch (Exception e) {
			Log.error("Error while initializing CouchDB");
			e.printStackTrace();
			throw new ServletException("Error while initializing CouchDB", e);
		}
	}

	@Override
	public IStatisticDao getStatisticDao() {
		if(m_statisticDao == null){
			m_statisticDao = new StatisticDao(this);
		}
		return m_statisticDao;
	}

	@Override
	public ICategoryDao getShopCategoryDao() {
		if(m_shopCategoryDao == null)
			m_shopCategoryDao = new CategoryDao(this, "shopCategory");
		return m_shopCategoryDao;
	}
}
