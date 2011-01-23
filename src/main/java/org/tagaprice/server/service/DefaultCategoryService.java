package org.tagaprice.server.service;

import java.util.List;

import org.tagaprice.core.api.ICategoryService;
import org.tagaprice.core.entities.Category;
import org.tagaprice.server.dao.interfaces.ICategoryDAO;

public class DefaultCategoryService implements ICategoryService {

	private ICategoryDAO _categoryDao;

	public void setCategoryDAO(ICategoryDAO categoryDao) {
		_categoryDao = categoryDao;
	}

	@Override
	public List<Category> getAll() {
		return _categoryDao.getAll();
	}

}
