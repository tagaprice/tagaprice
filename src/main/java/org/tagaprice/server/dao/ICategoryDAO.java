package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.dump.Category;

public interface ICategoryDAO extends IDAOClass<Category> {
	public List<Category> find(final Category searchPattern);
	public List<Category> list();
}
