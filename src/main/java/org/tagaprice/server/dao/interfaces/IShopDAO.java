package org.tagaprice.server.dao.interfaces;

import java.util.List;

import org.tagaprice.core.entities.Shop;

public interface IShopDAO {

	Shop getById(Long id);

	List<Shop> getByTitle(String title);

}
