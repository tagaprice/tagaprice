package org.tagaprice.server.dao.interfaces;

import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.exception.DAOException;

public interface IProductDAO {

	/**
	 * Retrieves product indicated by given id.
	 * @param id Id of product to retrieve.
	 * @return Product indicated by given id or null if no product could be found. 
	 * @throws DAOException
	 */
	ProductData getById(long id) throws DAOException;

	/**
	 * Saves given product to storage. If successful given product's revision will be set to the revision in the database.
	 * @param product Product to be saved to storage. If the given product does not provide an id the revision must be 0. If an id is provided the revision must match the current revision.
	 * @return True if product could be saved, false if not.
	 * @throws DAOException
	 */
	boolean save(ProductData product) throws DAOException;

}
