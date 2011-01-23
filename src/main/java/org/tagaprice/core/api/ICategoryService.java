package org.tagaprice.core.api;

import java.util.List;

import org.tagaprice.core.entities.Category;

public interface ICategoryService {

	/**
	 * Returns all currently persisted {@link Category}s or an empty list if any have been persisted.
	 */
	List<Category> getAll();

}
