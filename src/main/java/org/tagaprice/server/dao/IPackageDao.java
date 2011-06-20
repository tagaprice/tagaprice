package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.exceptions.dao.DaoException;

public interface IPackageDao extends IDaoClass<Package> {
	public List<Package> find(final Package searchPattern);
	public List<Package> findPackageByProduct(String productId) throws DaoException;
}
