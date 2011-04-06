package org.tagaprice.server.dao;

public interface IDaoFactory {
	ICategoryDAO getCategoryDAO();
	IPackageDAO getPackageDAO();
	IProductDAO getProductDAO();
	IReceiptDAO getReceiptDAO();
	IShopDAO getShopDAO();
}
