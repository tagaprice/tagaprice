package org.tagaprice.server.dao.interfaces;

import java.util.List;

import org.tagaprice.core.entities.Category;

public interface ICategoryDAO {

	Category save(Category category);

	List<Category> getAll();

}
