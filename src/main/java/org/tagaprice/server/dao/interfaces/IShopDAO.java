package org.tagaprice.server.dao.interfaces;

import java.util.List;

import org.tagaprice.core.entities.BasicShop;
import org.tagaprice.core.entities.Shop;


public interface IShopDAO {

	Shop getById(long id);

	/**
	 * TODO while shops aren't versioned...
	 * saves given shop and returns a shop with set id.
	 */
	Shop save(Shop shop);

	/**
	 * Returns all shops which match given title exactly.
	 */
	List<BasicShop> getByTitle(String title);

	/**
	 * Returns all shops which match given title anywhere in the title. The matching is not case sensitive.
	 */
	List<BasicShop> getByTitleFuzzy(String title);

	/**
	 * Returns all shops as {@link BasicShop}s
	 */
	List<BasicShop> getAll();

}
