package org.tagaprice.server.dao.mock;

import java.util.HashMap;
import java.util.List;

import org.tagaprice.server.dao.ICategoryDAO;
import org.tagaprice.shared.entities.IRevisionId;
import org.tagaprice.shared.entities.dump.Category;
import org.tagaprice.shared.entities.dump.ICategory;

public class CategoryDAO implements ICategoryDAO {
	HashMap<String, ICategory> categories = new HashMap<String, ICategory>();
	
	
	CategoryDAO() {
		ICategory root = new Category("root",null);
		this.categories.put("root", root);
		ICategory food = new Category("food", root);
		this.categories.put("food", food);
		ICategory vegetables = new Category("vegetables", root);
		this.categories.put("vegetables", vegetables);
		ICategory beverages = new Category("beverages", root);
		this.categories.put("beverages", beverages);
		ICategory alcoholics = new Category("alcohol", root);
		this.categories.put("alcoholics", alcoholics);
		ICategory nonalcoholics = new Category("nonalcoholics", root);
		this.categories.put("nonalcoholics", nonalcoholics);

		nonalcoholics.setParentCategory(beverages);
		alcoholics.setParentCategory(beverages);
		vegetables.setParentCategory(food);
		beverages.setParentCategory(root);
		food.setParentCategory(root);

	}

	@Override
	public ICategory create(ICategory category) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ICategory get(IRevisionId revisionId) {
		ICategory rc = null;
		
		if (revisionId != null && categories.containsKey(revisionId.getId())) {
			rc = categories.get(revisionId.getId());
		}
		
		return rc;
	}

	@Override
	public ICategory update(ICategory product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(ICategory product) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ICategory> find(ICategory searchPattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ICategory> list() {
		// TODO Auto-generated method stub
		return null;
	}

}
