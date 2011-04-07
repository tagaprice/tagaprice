package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.dump.ICategory;

public interface ICategoryDAO extends IDAOClass<ICategory> {
	public List<ICategory> find(final ICategory searchPattern);
	public List<ICategory> list();
}
