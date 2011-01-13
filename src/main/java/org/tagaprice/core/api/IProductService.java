package org.tagaprice.core.api;

import org.tagaprice.core.entities.Product;

public interface IProductService {

	/**
	 * Attempts to save given product and returns the actually saved product with all its revisions.
	 * Throws an IllegalRevisionException if given product's highest (i.e. newest) revision is older than the highest revision already saved.
	 * @throws IllegalRevisionException
	 */
	Product save(Product input) throws IllegalRevisionException;

	/**
	 * Returns the product
	 * @param id
	 * @return
	 */
	Product getById(Long id);
}
