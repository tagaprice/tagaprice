package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.exceptions.dao.DaoException;

public interface IShopDao extends IDaoClass<Shop> {
	public List<Shop> find(String searchPattern) throws DaoException;
	public List<Shop> list() throws DaoException;
	List<String> findIDsInBBox(BoundingBox bbox) throws DaoException;
}
