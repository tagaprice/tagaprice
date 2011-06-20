package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.tagaprice.server.dao.IPackageDao;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class PackageDao extends DaoClass<Package> implements IPackageDao {
	public PackageDao(CouchDbDaoFactory daoFactory) {
		super(daoFactory, Package.class, "package", daoFactory._getEntityDao());
	}

	@Override
	public List<Package> find(Package searchPattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Package> findPackageByProduct(String productId) throws DaoException{

		ViewResult<Package> result = m_db.queryViewByKeys("package/toproduct", Package.class, Arrays.asList(productId), null, null);
		List<Package> rc = new ArrayList<Package>();

		for (ValueRow<Package> row: result.getRows()) {
			Package product = row.getValue();
			rc.add(product);
		}

		return rc;
	}

}
