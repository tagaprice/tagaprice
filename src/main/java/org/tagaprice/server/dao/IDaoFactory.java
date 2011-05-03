package org.tagaprice.server.dao;

public interface IDaoFactory {
	ICategoryDao getCategoryDao();
	IPackageDao getPackageDao();
	IProductDao getProductDao();
	IReceiptDao getReceiptDao();
	ISessionDao getSessionDao();
	IShopDao getShopDao();
	IUnitDao getUnitDao();
	IUserDao getUserDao();
}
