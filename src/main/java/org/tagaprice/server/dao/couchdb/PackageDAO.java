package org.tagaprice.server.dao.couchdb;

import java.util.List;

import org.tagaprice.server.dao.IPackageDAO;
import org.tagaprice.shared.entities.productmanagement.IPackage;

public class PackageDAO extends DAOClass<IPackage> implements IPackageDAO {
	public PackageDAO() {
		super(IPackage.class, "package");
	}
	
	@Override
	public List<IPackage> find(IPackage searchPattern) {
		// TODO Auto-generated method stub
		return null;
	}

}
