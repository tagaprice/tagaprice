package org.tagaprice.server.dao.interfaces;

import java.util.List;

import org.tagaprice.core.entities.BasicShop;
import org.tagaprice.core.entities.Shop;


public interface IShopDAO {

	Shop getShopById(long id);

	Shop saveShop(Shop shop);

	/**
	 * Returns all shops which match given title exactly.
	 */
	List<BasicShop> getByTitle(String title);

	/**
	 * Returns all shops which match given title anywhere in the title. The matching is not case sensitive.
	 */
	List<BasicShop> getByTitleFuzzy(String title);

}
