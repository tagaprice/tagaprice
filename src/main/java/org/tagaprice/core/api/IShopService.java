package org.tagaprice.core.api;

import java.util.List;

import org.tagaprice.core.entities.Shop;
import org.tagaprice.server.dao.interfaces.IShopDAO;

public interface IShopService {

	/**
	 * Returns a {@link Shop} identified by given id or null if no {@link Shop} could be found.
	 */
	Shop getById(Long id);

	List<Shop> getByTitle(String title);

}
