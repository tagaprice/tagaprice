package org.tagaprice.server.dao.couchdb;

import org.tagaprice.server.dao.ICategoryDAO;
import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IPackageDAO;
import org.tagaprice.server.dao.IProductDAO;
import org.tagaprice.server.dao.IReceiptDAO;
import org.tagaprice.server.dao.IShopDAO;

/**
 * IDaoFactory implementation providing a CouchDB persistence layer
 */
public class CouchDBDaoFactory implements IDaoFactory {
	ICategoryDAO categoryDAO;
	IPackageDAO packageDAO;
	IProductDAO productDAO;
	IReceiptDAO receiptDAO;
	IShopDAO shopDAO;
	
	/**
	 * DAO factory constructor that allows the caller to specify a prefix String that will
	 * be used when connecting to databases.
	 * 
	 * This constructor comes in handy when writing DAO-Tests e.g.
	 */
	CouchDBDaoFactory(String dbPrefix) {
		categoryDAO = new CategoryDAO(dbPrefix);
		packageDAO = new PackageDAO(dbPrefix);
		productDAO = new ProductDAO(dbPrefix);
		receiptDAO = new ReceiptDAO(dbPrefix);
		shopDAO = new ShopDAO(dbPrefix);
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
