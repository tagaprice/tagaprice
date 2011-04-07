package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.IRevisionId;
import org.tagaprice.shared.entities.productmanagement.IPackage;

public interface IPackageDAO {
	public IPackage create(final IPackage pkg);
	public IPackage get(IRevisionId id);
	public IPackage update(final IPackage pkg);
	public void delete(IPackage pkg);
	public List<IPackage> find(final IPackage searchPattern);
}
