package org.tagaprice.server.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.tagaprice.core.api.ICategoryService;
import org.tagaprice.core.entities.Category;
import org.tagaprice.server.dao.interfaces.ICategoryDAO;


public class DefaultCategoryService implements ICategoryService {

	private ICategoryDAO _categoryDao;
	private static Logger _log = LoggerFactory.getLogger(DefaultCategoryService.class);

	public void setCategoryDAO(ICategoryDAO categoryDao) {
		_categoryDao = categoryDao;
	}

	@Transactional
	@Override
	public List<Category> getAll() {
		List<Category> categories = _categoryDao.getAll();
		_log.debug("number of categories found: "+categories.size());
		return categories;
//		// WORKAROUND for hibernate being lazy
//		for(Category cat : categories) {
//			cat.get
//		}
	}

}
