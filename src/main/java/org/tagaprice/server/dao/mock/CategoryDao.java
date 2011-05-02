package org.tagaprice.server.dao.mock;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.tagaprice.server.dao.ICategoryDao;
import org.tagaprice.shared.entities.categorymanagement.Category;

public class CategoryDao extends DaoClass<Category> implements ICategoryDao {

	@Override
	public List<Category> find(Category searchPattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> list() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Category> getChildren(Category parent){
		return getChildren(parent.getId());
	}

	@Override
	public List<Category> getChildren(String id) {
		ArrayList<Category> rc = new ArrayList<Category>();

		for (Deque<Category> deque: m_data.values()) {
			Category category = deque.peek();
			if (category.getParentCategory().getId().equals(id)) {
				rc.add(category);
			}
		}

		return rc;
	}

}
