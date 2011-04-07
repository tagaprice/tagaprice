package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.tagaprice.server.dao.ICategoryDAO;
import org.tagaprice.shared.entities.RevisionId;
import org.tagaprice.shared.entities.dump.Category;
import org.tagaprice.shared.entities.dump.ICategory;

public class CategoryDAO extends DAOClass<ICategory> implements ICategoryDAO {
	public CategoryDAO() {
		super(Category.class, "category");
	}

	@Override
	public List<ICategory> find(ICategory searchPattern) {
		throw new UnsupportedOperationException("CategoryDAO.find() wasn't implemented yet");
	}

	@Override
	public List<ICategory> list() {
		ViewResult<Map> result = m_db.listDocuments(null, null);
		List<ICategory> rc = new ArrayList<ICategory>();
		
		System.out.println("CatList:");
		for (ValueRow<Map> row: result.getRows()) {
			ICategory category = get(new RevisionId(row.getId()));
			rc.add(category);
		}
		
		return rc;
	}

}
