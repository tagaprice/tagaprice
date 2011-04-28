package org.tagaprice.server.dao.couchdb;

import org.tagaprice.server.dao.IDaoFactory;

/**
 * IDaoFactory implementation providing a CouchDB persistence layer
 */
public class CouchDBDaoFactory implements IDaoFactory {
	CategoryDAO m_categoryDAO = null;
	PackageDAO m_packageDAO = null;
	ProductDAO m_productDAO = null;
	ReceiptDAO m_receiptDAO = null;
	ShopDAO m_shopDAO = null;
	UnitDAO m_unitDAO = null;
	
	/**
	 * Default DAO factory constructor
	 */
	public CouchDBDaoFactory() {
	}
	
	@Override
	public CategoryDAO getCategoryDAO() {
		if (m_categoryDAO == null) {
			m_categoryDAO = new CategoryDAO();
		}
		return m_categoryDAO;
	}

	@Override
	public PackageDAO getPackageDAO() {
		if (m_packageDAO == null) {
			m_packageDAO = new PackageDAO();
		}
		return m_packageDAO;
	}

	@Override
	public ProductDAO getProductDAO() {
		if (m_productDAO == null) {
			m_productDAO = new ProductDAO(this);
		}
		return m_productDAO;
	}

	@Override
	public ReceiptDAO getReceiptDAO() {
		if (m_receiptDAO == null) {
			m_receiptDAO = new ReceiptDAO();
		}
		return m_receiptDAO;
	}

	@Override
	public ShopDAO getShopDAO() {
		if (m_shopDAO == null) {
			m_shopDAO = new ShopDAO();
		}
		return m_shopDAO;
	}

	@Override
	public UnitDAO getUnitDAO() {
		if (m_unitDAO == null) {
			m_unitDAO = new UnitDAO();
		}
		return m_unitDAO;
	}
}
