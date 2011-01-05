package org.tagaprice.server.dao.interfaces;

import java.util.List;

import org.tagaprice.core.entities.Product;

public interface IProductDAO {

	Product save(Product product);

	Product getById(Long id);

	List<Product> getAll();

	int countAll();
}
