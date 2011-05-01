package org.tagaprice.server.dao.couchdb;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jcouchdb.db.Server;
import org.jcouchdb.db.ServerImpl;
import org.tagaprice.server.dao.ICategoryDAO;
import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IPackageDAO;
import org.tagaprice.server.dao.IProductDAO;
import org.tagaprice.server.dao.IReceiptDAO;
import org.tagaprice.server.dao.IShopDAO;
import org.tagaprice.server.dao.IUnitDAO;

/**
 * IDaoFactory implementation providing a CouchDB persistence layer
 */
public class CouchDBDaoFactory implements IDaoFactory {
	static Properties m_dbConfig = null;
	
	ICategoryDAO m_categoryDAO = null;
	IPackageDAO m_packageDAO = null;
	IProductDAO m_productDAO = null;
	IReceiptDAO m_receiptDAO = null;
	IShopDAO m_shopDAO = null;
	IUnitDAO m_unitDAO = null;
	
	static Properties getConfiguration() throws IOException {
		if (m_dbConfig == null) {
			m_dbConfig = _readProperties("couchdb.properties");
		}
		return m_dbConfig;
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
			InputStream stream = CouchDBDaoFactory.class.getResourceAsStream("/"+filename+".default");
			if (stream != null) defaults.load(stream);
		}
		catch (IOException e) { /* ignore if we can't read the default config as long as we can read the specific one */ }
		
		Properties rc = new Properties(defaults);
		InputStream stream = CouchDBDaoFactory.class.getResourceAsStream("/"+filename); 
		if (stream != null) rc.load(stream);
		else throw new IOException("Couldn't load resource file '"+filename+"'. Make sure it exists and is accessible");
		return rc;
	}

	/**
	 * Default DAO factory constructor
	 */
	public CouchDBDaoFactory() {
	}
	
	@Override
	public ICategoryDAO getCategoryDAO() {
		if (m_categoryDAO == null) {
			m_categoryDAO = new CategoryDAO();
		}
		return m_categoryDAO;
	}

	@Override
	public IPackageDAO getPackageDAO() {
		if (m_packageDAO == null) {
			m_packageDAO = new PackageDAO();
		}
		return m_packageDAO;
	}

	@Override
	public IProductDAO getProductDAO() {
		if (m_productDAO == null) {
			m_productDAO = new ProductDAO(this);
		}
		return m_productDAO;
	}

	@Override
	public IReceiptDAO getReceiptDAO() {
		if (m_receiptDAO == null) {
			m_receiptDAO = new ReceiptDAO();
		}
		return m_receiptDAO;
	}

	@Override
	public IShopDAO getShopDAO() {
		if (m_shopDAO == null) {
			m_shopDAO = new ShopDAO();
		}
		return m_shopDAO;
	}

	@Override
	public IUnitDAO getUnitDAO() {
		if (m_unitDAO == null) {
			m_unitDAO = new UnitDAO();
		}
		return m_unitDAO;
	}
}
