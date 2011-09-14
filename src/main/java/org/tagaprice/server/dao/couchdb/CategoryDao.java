package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.List;

import org.jcouchdb.db.Options;
import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.tagaprice.server.dao.ICategoryDao;
import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.exceptions.dao.DaoException;

import com.allen_sauer.gwt.log.client.Log;

public class CategoryDao extends DaoClass<Category> implements ICategoryDao {
	public CategoryDao(CouchDbDaoFactory daoFactory, Document.Type docType) {
		super(daoFactory, Category.class, docType, null);
	}

	@Override
	public List<Category> find(Category searchPattern) {
		throw new UnsupportedOperationException("CategoryDao.find() wasn't implemented yet");
	}

	@Override
	public List<Category> list() throws DaoException {
		ViewResult<?> result = m_db.queryView(getDocumentType()+"/all", Category.class, null, null);
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
		Log.debug("getChildren: "+id);
		ViewResult<?> result = m_db.queryView(getDocumentType()+"/childrenOf", Category.class, new Options().key(id), null);
		List<Category> rc = new ArrayList<Category>();
		Log.debug("listSize: "+result.getRows().size());

		for (ValueRow<?> row: result.getRows()) {
			Log.debug("addChildren to List: "+row.getId());
			Category category = get(row.getId());
			rc.add(category);
		}

		return rc;
	}

	
	@Override
	protected void _injectFields(Category category) throws DaoException {
		if (category.getParent() != null) {
			category.setParent(get(category.getParent().getId()));
		}
	}

}
