package org.tagaprice.shared.rpc.categorymanagement;

import java.util.List;

import org.tagaprice.shared.entities.dump.Category;

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
	 */
	public Category getCategory(String id, String revision);

	/**
	 * Returns a {@link Category} with all parents!
	 * 
	 * @param id
	 *            of an {@link Category}
	 * @return a {@link Category} with all parents!
	 */
	public Category getCategory(String id);

	/**
	 * Returns all children for a {@link Category}. If id is Null, all root elements are returned.
	 * 
	 * @param id
	 *            of an {@link Category}
	 * @return list of all children. Children will not hold there parents!
	 */
	public List<Category> getCategoryChilds(String id);


}
