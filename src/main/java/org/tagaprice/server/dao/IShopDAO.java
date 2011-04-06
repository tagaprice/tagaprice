package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.IRevisionId;
import org.tagaprice.shared.entities.shopmanagement.IShop;

public interface IShopDAO {
	public IShop create(IShop shop);
	public IShop get(IRevisionId id);
	public IShop update(IShop shop);
	public void delete(IShop shop);
	public List<IShop> list();
}
