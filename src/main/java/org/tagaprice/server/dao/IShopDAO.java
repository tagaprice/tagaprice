package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.shopmanagement.IShop;

public interface IShopDAO extends IDAOClass<IShop> {
	public List<IShop> list();
}
