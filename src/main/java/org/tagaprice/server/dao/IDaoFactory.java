package org.tagaprice.server.dao;

import javax.servlet.ServletException;

public interface IDaoFactory {
	ICategoryDao getCategoryDao();
	IPackageDao getPackageDao();
	IProductDao getProductDao();
	IReceiptDao getReceiptDao();
	ISessionDao getSessionDao();
	IShopDao getShopDao();
	IUnitDao getUnitDao();
	IUserDao getUserDao();
	IStatisticDao getStatisticDao();

	void init() throws ServletException;
}
