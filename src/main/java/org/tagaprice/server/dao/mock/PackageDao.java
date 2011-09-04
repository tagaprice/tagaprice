package org.tagaprice.server.dao.mock;

import java.util.List;

import org.tagaprice.server.dao.IPackageDao;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class PackageDao extends DaoClass<Package> implements IPackageDao {

	@Override
	public List<Package> find(Package searchPattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Package> listByProduct(String productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> listIDsByProduct(String productId) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

}
