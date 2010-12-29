package org.tagaprice.server.dao.interfaces;

import org.tagaprice.core.entities.Product;

public interface IProductDAO {

	Product save(Product product);

	Product getById(Long id);

}
