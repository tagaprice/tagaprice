package org.tagaprice.core.api;

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
}
