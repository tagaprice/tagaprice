package org.tagaprice.server.dao.interfaces;

import java.util.List;

import org.tagaprice.shared.entities.Category;
import org.tagaprice.shared.exception.DAOException;

public interface ICategoryDAO {

	/**
	 * Retrieves category indicated by given id.
	 * @param id Id of category to retrieve.
	 * @return Category indicated by given id or null if no category could be found. 
	 * @throws DAOException
	 */
	Category getById(long id) throws DAOException;

	/**
	 * Retrieves all sub-categories of category indicated by given id.
	 * @param id Id of category to retrieve.
	 * @return Returns a List of sub-categories of category indicated by given id, can be empty but never null.
	 * @throws DAOException
	 */
	List<Category> getCategoryList(long id) throws DAOException;

	/**
	 * Retrieves the id of the root category, i.e. the category which does not have any parent.
	 * @return Returns the id of the root category.
	 * @throws DAOException
	 */
	long getRootCategoryId() throws DAOException;

}
