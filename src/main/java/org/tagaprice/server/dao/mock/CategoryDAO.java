package org.tagaprice.server.dao.mock;

import java.util.HashMap;
import java.util.List;

import org.tagaprice.server.dao.ICategoryDAO;
import org.tagaprice.shared.entities.dump.Category;

public class CategoryDAO implements ICategoryDAO {
	HashMap<String, Category> categories = new HashMap<String, Category>();
	
	
	CategoryDAO() {
		Category root = new Category("root",null);
		this.categories.put("root", root);
		Category food = new Category("food", root);
		this.categories.put("food", food);
		Category vegetables = new Category("vegetables", root);
		this.categories.put("vegetables", vegetables);
		Category beverages = new Category("beverages", root);
		this.categories.put("beverages", beverages);
		Category alcoholics = new Category("alcohol", root);
		this.categories.put("alcoholics", alcoholics);
		Category nonalcoholics = new Category("nonalcoholics", root);
		this.categories.put("nonalcoholics", nonalcoholics);

		nonalcoholics.setParentCategory(beverages);
		alcoholics.setParentCategory(beverages);
		vegetables.setParentCategory(food);
		beverages.setParentCategory(root);
		food.setParentCategory(root);

	}

	@Override
	public Category create(Category category) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Category get(String id, String revision) {
		Category rc = null;
		
		if (id != null && categories.containsKey(id)) {
			rc = categories.get(id);
		}
		
		return rc;
	}
	
	@Override
	public Category get(String id) {
		return get(id, null);
	}

	@Override
	public Category update(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Category category) {
		// TODO Auto-generated method stub

	}

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

}
