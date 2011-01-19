package org.tagaprice.server.dao.interfaces;

import org.tagaprice.core.entities.Shop;


public interface IShopDAO {

	Shop getShopById(long id);

	Shop saveShop(Shop shop);

}
