package org.tagaprice.shared.rpc.categorymanagement;

import java.util.List;

import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.exceptions.dao.DaoException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("categoryservice")
public interface ICategoryService extends RemoteService {

	/**
	 * Returns a category plus all parents
	 * 
	 * @param id
	 * @param revision
	 * @return
	 * @throws DaoException if something went wrong with requesting the data
	 */
	public Category getCategory(String id, String revision) throws DaoException;

	/**
	 * Returns a {@link Category} with all parents!
	 * 
	 * @param id
	 *            of an {@link Category}
	 * @return a {@link Category} with all parents!
	 * @throws DaoException if something went wrong with requesting the data
	 */
	public Category getCategory(String id) throws DaoException;

	/**
	 * Returns all children for a {@link Category}. If id is Null, all root elements are returned.
	 * 
	 * @param id
	 *            of an {@link Category}
	 * @return list of all children. Children will not hold there parents!
	 * @throws DaoException 
	 */
	public List<Category> getCategoryChildren(String id) throws DaoException;


}
