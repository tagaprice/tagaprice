package org.tagaprice.shared.rpc.productmanagement;

import java.util.List;

import org.tagaprice.shared.entities.IRevisionId;
import org.tagaprice.shared.entities.dump.Category;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("productservice")
public interface IProductService extends RemoteService {

	/**
	 * Get a unique product from the server
	 * 
	 * @param revionsId
	 *            The ID (and Revision) of the {@link Product} you want to find. The ID in {@link IRevisionId} must not
	 *            be null.
	 * @return The {@link Product} corresponding to the {@link IRevisionId}
	 */
	public Product getProduct(String id, String revision);
	
	public Product getProduct(String id);

	/**
	 * Depending on the search criterias returns the method a list with IProducts
	 * 
	 * @param searchCriteria
	 * @return
	 */
	public List<Product> findProducts(Product searchCriteria);


	/**
	 * CREATE or UPDATE a {@link Product} on the server.
	 * CREATE: A product with id=0 or RevisionID=null gets a new id and the initial revisionID 1.
	 * UPDATE: the existing product gets a new revisionID incremented by one. Throws an exception, if the given
	 * revisionID is not the last one for this product.
	 * 
	 * @param product
	 *            The {@link Product} you want to UPDATE/CREATE
	 * @return the SAVED or UPDATED {@link Product}
	 */
	public Product saveProduct(Product product) throws UserNotLoggedInException;

	/**
	 * Returns a list of all categories. TODO Return
	 * 
	 * @return
	 */
	@Deprecated
	public List<Category> getCategories();


	/**
	 * Returns all children for a {@link Category}.
	 * 
	 * @param revisionId
	 *            of an {@link Category}
	 * @return list of all children. Children will not hold there parents!
	 */
	public List<Category> getCategoryChilds(IRevisionId revisionId);

	/**
	 * Returns a {@link Category} with all parents!
	 * 
	 * @param revisionId
	 *            of an {@link Category}
	 * @return a {@link Category} with all parents!
	 */
	public Category getCategory(IRevisionId revisionId);


	/**
	 * Returns a {@link Package} by the given {@link IRevisionId}. The {@link Package} will also hold its parent
	 * {@link Product}
	 * 
	 * @param id {@link Package Package's} ID
	 * @param revision {@link Package Package's} revision
	 * @return a {@link Package} by the given {@link IRevisionId}. The {@link Package} will also hold its parent
	 *         {@link Product}
	 */
	public Package getPackage(String id, String revision);
	
	public Package getPackage(String id);


	/**
	 * Save or create {@link Package}. The {@link Product} must be included.
	 * 
	 * @param ipackage
	 *            {@link Package} that should be created or saved.
	 * @return return the new {@link Package} with {@link IRevisionId}
	 * @throws UserNotLoggedInException
	 */
	public Package savePackage(Package ipackage) throws UserNotLoggedInException;

}
