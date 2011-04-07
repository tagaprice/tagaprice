package org.tagaprice.server.dao.couchdb;

import java.util.List;

import org.tagaprice.server.dao.IShopDAO;
import org.tagaprice.shared.entities.shopmanagement.IShop;

public class ShopDAO extends DAOClass<IShop> implements IShopDAO {
	public ShopDAO() {
		super(IShop.class, "shop");
	}
	
	@Override
	public List<IShop> list() {
		throw new UnsupportedOperationException("CategoryDAO.find() wasn't implemented yet");
	}

}
