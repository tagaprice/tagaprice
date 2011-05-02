package org.tagaprice.server.dao;

public interface IDaoFactory {
	ICategoryDao getCategoryDao();
	IPackageDao getPackageDao();
	IProductDao getProductDao();
	IReceiptDao getReceiptDao();
	IShopDao getShopDao();
	IUnitDao getUnitDao();
}
