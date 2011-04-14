package org.tagaprice.server.dao.mock;

import org.tagaprice.server.dao.ICategoryDAO;
import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IPackageDAO;
import org.tagaprice.server.dao.IProductDAO;
import org.tagaprice.server.dao.IReceiptDAO;
import org.tagaprice.server.dao.IShopDAO;
import org.tagaprice.server.dao.IUnitDAO;

public class MockDaoFactory implements IDaoFactory {
	ICategoryDAO m_categoryDAO = new CategoryDAO(this);
	IPackageDAO m_packageDAO = new PackageDAO();
	IProductDAO m_productDAO = new ProductDAO(this);
	IReceiptDAO m_receiptDAO = new ReceiptDAO(this);
	IShopDAO m_shopDAO = new ShopDAO();
	IUnitDAO m_unitDAO = new UnitDAO();

	@Override
	public ICategoryDAO getCategoryDAO() {
		return m_categoryDAO;
	}

	@Override
	public IPackageDAO getPackageDAO() {
		return m_packageDAO;
	}

	@Override
	public IProductDAO getProductDAO() {
		return m_productDAO;
	}

	@Override
	public IReceiptDAO getReceiptDAO() {
		return m_receiptDAO;
	}

	@Override
	public IShopDAO getShopDAO() {
		return m_shopDAO;
	}

	@Override
	public IUnitDAO getUnitDAO() {
		return m_unitDAO;
	}
}
