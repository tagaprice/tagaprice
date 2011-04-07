package org.tagaprice.server.dao.couchdb;

import org.tagaprice.server.dao.ICategoryDAO;
import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IPackageDAO;
import org.tagaprice.server.dao.IProductDAO;
import org.tagaprice.server.dao.IReceiptDAO;
import org.tagaprice.server.dao.IShopDAO;

public class CouchDBDaoFactory implements IDaoFactory {
	ICategoryDAO categoryDAO = new CategoryDAO();
	IPackageDAO packageDAO = new PackageDAO();
	IProductDAO productDAO = new ProductDAO();
	IReceiptDAO receiptDAO = new ReceiptDAO();
	IShopDAO shopDAO = new ShopDAO();
	
	@Override
	public ICategoryDAO getCategoryDAO() {
		return categoryDAO;
	}

	@Override
	public IPackageDAO getPackageDAO() {
		return packageDAO;
	}

	@Override
	public IProductDAO getProductDAO() {
		return productDAO;
	}

	@Override
	public IReceiptDAO getReceiptDAO() {
		return receiptDAO;
	}

	@Override
	public IShopDAO getShopDAO() {
		return shopDAO;
	}

}
