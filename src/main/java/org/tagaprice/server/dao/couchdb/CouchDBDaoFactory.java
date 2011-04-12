package org.tagaprice.server.dao.couchdb;

import org.tagaprice.server.dao.IDaoFactory;

/**
 * IDaoFactory implementation providing a CouchDB persistence layer
 */
public class CouchDBDaoFactory implements IDaoFactory {
	String m_dbPrefix;
	
	CategoryDAO m_categoryDAO = null;
	PackageDAO m_packageDAO = null;
	ProductDAO m_productDAO = null;
	ReceiptDAO m_receiptDAO = null;
	ShopDAO m_shopDAO = null;
	UnitDAO m_unitDAO = null;
	
	/**
	 * DAO factory constructor that allows the caller to specify a prefix String that will
	 * be used when connecting to databases.
	 * 
	 * This constructor comes in handy when writing DAO-Tests e.g.
	 */
	CouchDBDaoFactory(String dbPrefix) {
		m_dbPrefix = dbPrefix;
	}
	
	/**
	 * Default DAO factory constructor
	 * 
	 * This Constructor is needed for the DAO Factory injection to work properly.
	 * It simply sets the DB prefix to "". This may change in further versions where
	 * the prefix might be read from a configuration file for example.
	 */
	public CouchDBDaoFactory() {
		this("");
	}
	
	@Override
	public CategoryDAO getCategoryDAO() {
		if (m_categoryDAO == null) {
			m_categoryDAO = new CategoryDAO(m_dbPrefix);
		}
		return m_categoryDAO;
	}

	@Override
	public PackageDAO getPackageDAO() {
		if (m_packageDAO == null) {
			m_packageDAO = new PackageDAO(m_dbPrefix);
		}
		return m_packageDAO;
	}

	@Override
	public ProductDAO getProductDAO() {
		if (m_productDAO == null) {
			m_productDAO = new ProductDAO(m_dbPrefix);
		}
		return m_productDAO;
	}

	@Override
	public ReceiptDAO getReceiptDAO() {
		if (m_receiptDAO == null) {
			m_receiptDAO = new ReceiptDAO(m_dbPrefix);
		}
		return m_receiptDAO;
	}

	@Override
	public ShopDAO getShopDAO() {
		if (m_shopDAO == null) {
			m_shopDAO = new ShopDAO(m_dbPrefix);
		}
		return m_shopDAO;
	}

	@Override
	public UnitDAO getUnitDAO() {
		if (m_unitDAO == null) {
			m_unitDAO = new UnitDAO(m_dbPrefix);
		}
		return m_unitDAO;
	}
}
