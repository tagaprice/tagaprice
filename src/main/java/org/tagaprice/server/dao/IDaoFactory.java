package org.tagaprice.server.dao;

import javax.servlet.ServletException;

public interface IDaoFactory {
	ICategoryDao getProductCategoryDao();
	ICategoryDao getShopCategoryDao();
	IPackageDao getPackageDao();
	IProductDao getProductDao();
	IReceiptDao getReceiptDao();
	ISearchDao getSearchDao();
	ISessionDao getSessionDao();
	IShopDao getShopDao();
	IUnitDao getUnitDao();
	IUserDao getUserDao();
	IStatisticDao getStatisticDao();

	void init() throws ServletException;
}
