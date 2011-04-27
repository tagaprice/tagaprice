package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.List;

import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.tagaprice.server.dao.ICategoryDAO;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class CategoryDAO extends DAOClass<Category> implements ICategoryDAO {
	public CategoryDAO(String dbPrefix) {
		super(dbPrefix, Category.class, "category");
	}

	@Override
	public List<Category> find(Category searchPattern) {
		throw new UnsupportedOperationException("CategoryDAO.find() wasn't implemented yet");
	}

	@Override
	public List<Category> list() throws DaoException {
		ViewResult<?> result = m_db.listDocuments(null, null);
		List<Category> rc = new ArrayList<Category>();

		System.out.println("CatList:");
		for (ValueRow<?> row: result.getRows()) {
			Category category = get(row.getId());
			rc.add(category);
		}

		return rc;
	}

	@Override
	public List<Category> children(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
