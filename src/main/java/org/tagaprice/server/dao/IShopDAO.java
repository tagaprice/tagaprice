package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.exceptions.dao.DaoException;

public interface IShopDAO extends IDaoClass<Shop> {
	public List<Shop> list() throws DaoException;
}
