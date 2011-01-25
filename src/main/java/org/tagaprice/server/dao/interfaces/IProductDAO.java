package org.tagaprice.server.dao.interfaces;

import java.util.List;

import org.tagaprice.core.entities.Product;

public interface IProductDAO {

	/**
	 * Saves given {@link Product} and returns the actually saved product (TODO with all its revisions).
	 * @param product the {@link Product} to save.
	 */
	Product save(Product product);

	/**
	 * Retrieves the {@link Product} with given id.
	 * @param id id of the {@link Product} to retrieve.
	 * @return the retrieved {@link Product}, or null if the product does not exist in the database.
	 */
	Product getById(Long id);

	/**
	 * Retrieves a {@link List} of all {@link Product}s.
	 */
	List<Product> getAll();

	/**
	 * Count all {@link Product}s.
	 */
	int countAll();


	Product update(Product product);

	void evict(Product persistedProduct);
}
