package org.tagaprice.server.dao.couchdb;

import java.util.List;

import org.tagaprice.server.dao.IPackageDao;
import org.tagaprice.shared.entities.productmanagement.Package;

public class PackageDao extends DaoClass<Package> implements IPackageDao {
	public PackageDao(CouchDbDaoFactory daoFactory) {
		super(Package.class, "package", daoFactory._getEntityDao());
	}
	
	@Override
	public List<Package> find(Package searchPattern) {
		// TODO Auto-generated method stub
		return null;
	}

}
