package org.tagaprice.server.dao.couchdb;

import java.util.List;

import org.tagaprice.server.dao.ICategoryDAO;
import org.tagaprice.shared.entities.dump.ICategory;

public class CategoryDAO extends DAOClass<ICategory> implements ICategoryDAO {
	public CategoryDAO() {
		super(ICategory.class, "category");
	}

	@Override
	public List<ICategory> find(ICategory searchPattern) {
		throw new UnsupportedOperationException("CategoryDAO.find() wasn't implemented yet");
	}

	@Override
	public List<ICategory> list() {
		throw new UnsupportedOperationException("CategoryDAO.list() wasn't implemented yet");
	}

}
