package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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

	/**
	 * Fetch child category IDs recursively until there are no more children or maxDepth reaches 0 
	 * @param parentIDs IDs of the parents whose children should be fetched
	 * @param maxDepth This parameter will be decremented with each recursive call (until it reaches 0)
	 * @return Long list of children, grandchildren, ... (not including the parentIDs)
	 */
	private List<String> getChildIDsRecursively(List<String> parentIDs, int maxDepth) {
		List<String> rc = new ArrayList<String>();
		if (maxDepth > 0) {
			ViewResult<?> result = m_db.queryViewByKeys(getDocumentType()+"/childrenOf", Category.class, parentIDs, null, null);

			// direct children
			for (ValueRow<?> row: result.getRows()) {
				rc.add(row.getId());
			}

			if (!rc.isEmpty()) {
				rc.addAll(getChildIDsRecursively(rc, maxDepth--));
			}
		}
		return rc;
	}

	/**
	 * Fetch all the IDs of child categories recursively (with a maximal recursion depth of 5)
	 * @param parentIDs IDs of the parents whose children should be fetched
	 * @return Long list of children, grandchildren, ... (not including the parentIDs)
	 */
	public List<String> getChildIDsRecursively(List<String> parentIDs) {
		return getChildIDsRecursively(parentIDs, 5);
	}

	
	@Override
	protected void _injectFields(Category ... categories) throws DaoException {
		Set<String> parentIDs = new TreeSet<String>();

		for(Category category: categories) {
			if (category.getParentId() != null) parentIDs.add(category.getParentId());
		}

		Map<String, Category> parents = getBulk(parentIDs.toArray(new String[parentIDs.size()]));
		
		for(Category category: categories) {
			if (category.getParentId() != null) {
				category.setParent(parents.get(category.getParentId()));
			}
		}
	}

}
