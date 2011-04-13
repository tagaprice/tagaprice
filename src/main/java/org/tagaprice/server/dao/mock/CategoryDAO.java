package org.tagaprice.server.dao.mock;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.tagaprice.server.dao.ICategoryDAO;
import org.tagaprice.shared.entities.dump.Category;

public class CategoryDAO implements ICategoryDAO {
	HashMap<String, Category> categories = new HashMap<String, Category>();
	Random rand = new Random(15645651);

	CategoryDAO() {
		Category food = create(new Category("food", null));
		Category vegetables = create(new Category("vegetables", food));
		Category beverages = create(new Category("beverages", null));
		Category alcoholics = create(new Category("alcohol", beverages));
		Category nonalcoholics = create(new Category("nonalcoholics", beverages));


	}

	@Override
	public Category create(Category category) {
		String id = ""+rand.nextLong();
		category.setId(""+rand.nextLong());
		category.setRevision(id);

		categories.put(id, category);

		return category;
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

	@Override
	public List<Category> childs(String id) {
		return null;
	}

}
