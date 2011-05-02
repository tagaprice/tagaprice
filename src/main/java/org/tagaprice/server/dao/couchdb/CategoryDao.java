package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.List;

import org.jcouchdb.db.Options;
import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.tagaprice.server.dao.ICategoryDAO;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class CategoryDao extends DaoClass<Category> implements ICategoryDAO {
	public CategoryDao(CouchDbDaoFactory daoFactory) {
		super(Category.class, "category", daoFactory._getEntityDao());
	}

	@Override
	public List<Category> find(Category searchPattern) {
		throw new UnsupportedOperationException("CategoryDao.find() wasn't implemented yet");
	}

	@Override
	public List<Category> list() throws DaoException {
		ViewResult<?> result = m_db.queryView("category/all", Category.class, null, null);
		List<Category> rc = new ArrayList<Category>();

		System.out.println("CatList:");
		for (ValueRow<?> row: result.getRows()) {
			Category category = get(row.getId());
			rc.add(category);
		}

		return rc;
	}

	@Override
	public List<Category> getChildren(Category parent) throws DaoException {
		return getChildren(parent.getId());
	}
	
	@Override
	public List<Category> getChildren(String id) throws DaoException {
		ViewResult<?> result = m_db.queryView("category/children", Category.class, new Options().key(id), null);
		List<Category> rc = new ArrayList<Category>();

		System.out.println("CatList:");
		for (ValueRow<?> row: result.getRows()) {
			Category category = get(row.getId());
			rc.add(category);
		}

		return rc;
	}

}
