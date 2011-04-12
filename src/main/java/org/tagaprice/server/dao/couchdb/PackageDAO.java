package org.tagaprice.server.dao.couchdb;

import java.util.List;

import org.tagaprice.server.dao.IPackageDAO;
import org.tagaprice.shared.entities.productmanagement.Package;

public class PackageDAO extends DAOClass<Package> implements IPackageDAO {
	public PackageDAO(String dbPrefix) {
		super(dbPrefix, Package.class, "package");
	}
	
	@Override
	public List<Package> find(Package searchPattern) {
		// TODO Auto-generated method stub
		return null;
	}

}
