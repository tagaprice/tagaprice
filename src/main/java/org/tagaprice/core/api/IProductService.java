package org.tagaprice.core.api;

import java.util.List;

import org.tagaprice.core.entities.Product;

public interface IProductService {

	/**
	 * Attempts to save given product.
	 * @param product Product to save with at least one revision set. The ids of the product and it's revisions must be the same value. If the ids are null, the product will be treated as new.
	 * @return the actually saved product with all its revisions and the ids set.
	 * @throws ServerException Thrown to indicate that the Server has failed handling the latest request.
	 * @throws OutdatedRevisionException Thrown to indicate that at least one of the revisions of the given product is outdated, i.e. another revision of the product has been saved already.
	 */
	Product save(Product product) throws ServerException, OutdatedRevisionException;

	/**
	 * Returns all products, which have at least one revision with the title containing given title.
	 * @param title Title to look for.
	 * @return Returns all products, which have at least one revision with the title containing given title. If no product could be found, returns an empty list.
	 *  @throws ServerException Thrown to indicate that the Server has failed handling the latest request.
	 */
	List<Product> getByTitle(String title) throws ServerException;

	/**
	 * Returns a list of all products in the database.
	 */
	List<Product> getAll();

	/**
	 * Return a {@link Product} and all its revisions with the specified id.
	 */
	Product getById(Long id);
}
