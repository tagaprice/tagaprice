package org.tagaprice.server.dao.couchdb;

import java.util.List;

import org.tagaprice.server.dao.IShopDAO;
import org.tagaprice.shared.entities.shopmanagement.Shop;

public class ShopDAO extends DAOClass<Shop> implements IShopDAO {
	public ShopDAO() {
		super(Shop.class, "shop");
	}
	
	@Override
	public List<Shop> list() {
		throw new UnsupportedOperationException("CategoryDAO.find() wasn't implemented yet");
	}

}
