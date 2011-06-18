package org.tagaprice.server.dao.mock;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.tagaprice.server.dao.ICategoryDao;
import org.tagaprice.shared.entities.categorymanagement.Category;

import com.allen_sauer.gwt.log.client.Log;

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
		Log.debug("parentid: "+id);

		for (Deque<Category> deque: m_data.values()) {

			Category category = deque.peek();

			if(id==null && category.getParent()==null){
				rc.add(category);
			}else if (id!=null && category.getParent()!=null && category.getParent().getId().equals(id)) {
				rc.add(category);
			}

		}

		return rc;
	}

}
