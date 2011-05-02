package org.tagaprice.server.dao;

public interface IDaoFactory {
	ICategoryDao getCategoryDAO();
	IPackageDao getPackageDAO();
	IProductDao getProductDAO();
	IReceiptDao getReceiptDAO();
	IShopDao getShopDAO();
	IUnitDao getUnitDAO();
}
