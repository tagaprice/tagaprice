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
import org.tagaprice.server.dao.IShopDao;
import org.tagaprice.server.dao.IUnitDao;

/**
 * IDaoFactory implementation providing a CouchDB persistence layer
 */
public class CouchDbDaoFactory implements IDaoFactory {
	static Properties m_dbConfig = null;
	
	private ICategoryDao m_categoryDAO = null;
	private IPackageDao m_packageDAO = null;
	private IProductDao m_productDAO = null;
	private IReceiptDao m_receiptDAO = null;
	private IShopDao m_shopDAO = null;
	private IUnitDao m_unitDAO = null;
	private UserDao m_userDao = null;
	
	private EntityDao m_entityDAO = null;
	
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
		try {
			InputStream stream = CouchDbDaoFactory.class.getResourceAsStream("/"+filename+".default");
			if (stream != null) defaults.load(stream);
		}
		catch (IOException e) { /* ignore if we can't read the default config as long as we can read the specific one */ }
		
		Properties rc = new Properties(defaults);
		InputStream stream = CouchDbDaoFactory.class.getResourceAsStream("/"+filename); 
		if (stream != null) rc.load(stream);
		else throw new IOException("Couldn't load resource file '"+filename+"'. Make sure it exists and is accessible");
		return rc;
	}

	/**
	 * Default DAO factory constructor
	 */
	public CouchDbDaoFactory() {
	}
	
	@Override
	public ICategoryDao getCategoryDAO() {
		if (m_categoryDAO == null) {
			m_categoryDAO = new CategoryDao(this);
		}
		return m_categoryDAO;
	}

	@Override
	public IPackageDao getPackageDAO() {
		if (m_packageDAO == null) {
			m_packageDAO = new PackageDao(this);
		}
		return m_packageDAO;
	}

	@Override
	public IProductDao getProductDAO() {
		if (m_productDAO == null) {
			m_productDAO = new ProductDao(this);
		}
		return m_productDAO;
	}

	@Override
	public IReceiptDao getReceiptDAO() {
		if (m_receiptDAO == null) {
			m_receiptDAO = new ReceiptDao(this);
		}
		return m_receiptDAO;
	}

	@Override
	public IShopDao getShopDAO() {
		if (m_shopDAO == null) {
			m_shopDAO = new ShopDao(this);
		}
		return m_shopDAO;
	}

	@Override
	public IUnitDao getUnitDAO() {
		if (m_unitDAO == null) {
			m_unitDAO = new UnitDao(this);
		}
		return m_unitDAO;
	}
	
	public UserDao getUserDao() {
		if (m_userDao == null) {
			m_userDao = new UserDao(this);
		}
		return m_userDao;
	}
	
	EntityDao _getEntityDao() {
		if (m_entityDAO == null) {
			m_entityDAO = new EntityDao(this);
		}
		return m_entityDAO;
	}

}
