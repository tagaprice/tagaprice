package org.tagaprice.core.api;

import java.util.List;

import org.tagaprice.core.entities.BasicShop;
import org.tagaprice.core.entities.Shop;

public interface IShopService {

	/**
	 * Gets the shop with matching id.
	 */
	Shop getById(long id);

	/**
	 * Saves given shop.
	 * @param shop {@link Shop} to save. Must not be null.
	 * @return saved Shop with the id set.
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

}
