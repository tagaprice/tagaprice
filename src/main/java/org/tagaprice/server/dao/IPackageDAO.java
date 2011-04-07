package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.productmanagement.IPackage;

public interface IPackageDAO extends IDAOClass<IPackage> {
	public List<IPackage> find(final IPackage searchPattern);
}
