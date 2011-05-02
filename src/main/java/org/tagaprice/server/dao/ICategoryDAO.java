package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.exceptions.dao.DaoException;

public interface ICategoryDAO extends IDaoClass<Category> {
	public List<Category> find(final Category searchPattern);
	public List<Category> list() throws DaoException;
	public List<Category> getChildren(String id) throws DaoException;
	List<Category> getChildren(Category parent) throws DaoException;
}
