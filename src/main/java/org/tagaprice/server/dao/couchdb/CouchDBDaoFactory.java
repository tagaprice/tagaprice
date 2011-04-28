package org.tagaprice.server.dao.couchdb;

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
	ICategoryDAO m_categoryDAO = null;
	IPackageDAO m_packageDAO = null;
	IProductDAO m_productDAO = null;
	IReceiptDAO m_receiptDAO = null;
	IShopDAO m_shopDAO = null;
	IUnitDAO m_unitDAO = null;
	
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
