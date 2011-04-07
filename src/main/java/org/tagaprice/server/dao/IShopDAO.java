package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.shopmanagement.Shop;

public interface IShopDAO extends IDAOClass<Shop> {
	public List<Shop> list();
}
