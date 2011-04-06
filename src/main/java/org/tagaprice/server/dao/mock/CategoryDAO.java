package org.tagaprice.server.dao.mock;

import java.util.ArrayList;
import java.util.List;

import org.tagaprice.server.dao.ICategoryDAO;
import org.tagaprice.shared.entities.IRevisionId;
import org.tagaprice.shared.entities.dump.Category;
import org.tagaprice.shared.entities.dump.ICategory;

public class CategoryDAO implements ICategoryDAO {
	ArrayList<ICategory> categories = new ArrayList<ICategory>();
	
	CategoryDAO() {
		ICategory root = new Category("root",null);
		this.categories.add(root);
		ICategory food = new Category("food", root);
		this.categories.add(food);
		ICategory vegetables = new Category("vegetables", root);
		this.categories.add(vegetables);
		ICategory beverages = new Category("beverages", root);
		this.categories.add(beverages);
		ICategory alcoholics = new Category("alcohol", root);
		this.categories.add(alcoholics);
		ICategory nonalcoholics = new Category("nonalcoholics", root);
		this.categories.add(nonalcoholics);

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
	public ICategory get(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ICategory get(IRevisionId id) {
		// TODO Auto-generated method stub
		return null;
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
