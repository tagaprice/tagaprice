package org.tagaprice.server.dao;

import java.util.List;
import java.util.Map;

import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.exceptions.dao.DaoException;

public interface IPackageDao extends IDaoClass<Package> {
	public List<Package> find(final Package searchPattern);
	public Map<String, List<Package>> listByProducts(String ... productIDs) throws DaoException;
	
	/**
	 * Returns a list of all the Package-IDs for a product
	 * @param productId Product ID to query
	 * @return Package list
	 * @throws DaoException
	 */
	List<String> listIDsByProduct(String productId) throws DaoException;
}
