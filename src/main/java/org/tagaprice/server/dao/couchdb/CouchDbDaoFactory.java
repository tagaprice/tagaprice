package org.tagaprice.server.dao.couchdb;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
import org.tagaprice.server.dao.ISessionDao;
import org.tagaprice.server.dao.IShopDao;
import org.tagaprice.server.dao.IUnitDao;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;


/**
 * IDaoFactory implementation providing a CouchDB persistence layer
 */
public class CouchDbDaoFactory implements IDaoFactory {
	private static final String defaultHost = "localhost";
	private static final String defaultPort = "5984";
	private static final String defaultDatabase = "tagaprice";
	private static final String defaultSsl = "false"; // defaulting to false (we don't need ssl on localhost)

	private static Properties m_dbConfig = null;
	private static Server m_server = null;
	
	private ICategoryDao m_categoryDao = null;
	private IPackageDao m_packageDao = null;
	private IProductDao m_productDao = null;
	private IReceiptDao m_receiptDao = null;
	private ISessionDao m_sessionDao = null;
	private IShopDao m_shopDao = null;
	private IUnitDao m_unitDao = null;
	private UserDao m_userDao = null;
	
	private EntityDao m_entityDao = null;
	
	private static  MyLogger m_logger = LoggerFactory.getLogger(CouchDbDaoFactory.class);
	
	static Properties getConfiguration() throws IOException {
		if (m_dbConfig == null) {
			m_dbConfig = _readProperties("couchdb.properties");
		}
		return m_dbConfig;
	}
	
	static void _setConfiguration(Properties dbConfig) {
		m_dbConfig = dbConfig;
	}
	
	static Server getServerObject(Properties dbConfig) {
		if (m_server == null) {
			String host = dbConfig.getProperty("host", defaultHost);
			int port = Integer.parseInt(dbConfig.getProperty("port", defaultPort));
			boolean useSsl = Boolean.parseBoolean(dbConfig.getProperty("ssl", defaultSsl));
			m_server = new ServerImpl(host, port, useSsl);

			if (dbConfig.containsKey("user") || dbConfig.containsKey("password")) {
				String user = dbConfig.getProperty("user");
				String password = dbConfig.getProperty("password");
	
				AuthScope authScope = new AuthScope(host, port);
				Credentials credentials = new UsernamePasswordCredentials(user, password);
				m_logger.log("Connecting to the CouchDB server at '"+host+":"+port+"' as user '"+user+"'");
				m_server.setCredentials(authScope, credentials);
			}
			else {
				m_logger.log("Connecting anonymously to the CouchDB server at '"+host+":"+port+"'");
			}
	
			if (useSsl) m_logger.log("using SSL");
		}
		return m_server;
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
	private static Properties _readProperties(String filename) throws IOException {
		Properties defaults = new Properties();
		boolean propertiesRead = false;
		
		try {
			InputStream stream = CouchDbDaoFactory.class.getResourceAsStream("/"+filename+".default");
			if (stream != null) {
				m_logger.log("Reading default configuration file '"+filename+".default'");
				defaults.load(stream);
				propertiesRead = true;
			}
			else m_logger.log("Couldn't read default configuration file '"+filename+".default'");

		}
		catch (IOException e) { /* ignore if we can't read the default config as long as we can read the specific one */ }
		
		Properties rc = new Properties(defaults);
		InputStream stream = CouchDbDaoFactory.class.getResourceAsStream("/"+filename); 
		if (stream != null) {
			m_logger.log("Reading configuration file '"+filename+"'");
			rc.load(stream);
			propertiesRead = true;
		}
		else  m_logger.log("Couldn't read configuration file '"+filename+"'");
		
		if (!propertiesRead) throw new IOException("Couldn't load resource file '"+filename+"'. Make sure it exists and is accessible");
		
		return rc;
	}

	/**
	 * Default DAO factory constructor
	 */
	public CouchDbDaoFactory() {
	}
	
	@Override
	public ICategoryDao getCategoryDao() {
		if (m_categoryDao == null) {
			m_categoryDao = new CategoryDao(this);
		}
		return m_categoryDao;
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
	
	public UserDao getUserDao() {
		if (m_userDao == null) {
			m_userDao = new UserDao(this);
		}
		return m_userDao;
	}
	
	EntityDao _getEntityDao() {
		if (m_entityDao == null) {
			m_entityDao = new EntityDao(this);
		}
		return m_entityDao;
	}
	
	@Override
	public void init() throws ServletException {
		InitialInjector injector = new InitialInjector();
		String dbName;
		try {
			dbName = getConfiguration().getProperty("database", defaultDatabase);
			injector.init(getServerObject(), dbName);
		} catch (Exception e) {
			m_logger.log("Error while initializing CouchDB");
			e.printStackTrace();
			throw new ServletException("Error while initializing CouchDB", e);
		}
	}
}
