package org.tagaprice.server.dao.couchdb;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

/**
 * IDaoFactory implementation providing a CouchDB persistence layer
 */
public class CouchDbDaoFactory implements IDaoFactory {
	static Properties m_dbConfig = null;
	
	private ICategoryDao m_categoryDao = null;
	private IPackageDao m_packageDao = null;
	private IProductDao m_productDao = null;
	private IReceiptDao m_receiptDao = null;
	private ISessionDao m_sessionDao = null;
	private IShopDao m_shopDao = null;
	private IUnitDao m_unitDao = null;
	private UserDao m_userDao = null;
	
	private EntityDao m_entityDao = null;
	
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
		return new ServerImpl(
				dbConfig.getProperty("host", "localhost"),
				Integer.parseInt(dbConfig.getProperty("port", "5984")),
				Boolean.parseBoolean(dbConfig.getProperty("ssl", "false"))); // we don't need ssl on localhost
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
				defaults.load(stream);
				propertiesRead = true;
			}
		}
		catch (IOException e) { /* ignore if we can't read the default config as long as we can read the specific one */ }
		
		Properties rc = new Properties(defaults);
		InputStream stream = CouchDbDaoFactory.class.getResourceAsStream("/"+filename); 
		if (stream != null) {
			rc.load(stream);
			propertiesRead = true;
		}
		
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

}
