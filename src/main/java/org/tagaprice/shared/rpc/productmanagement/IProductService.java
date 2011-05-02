package org.tagaprice.shared.rpc.productmanagement;

import java.util.List;

import org.tagaprice.shared.entities.accountmanagement.Session;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;
import org.tagaprice.shared.exceptions.dao.DaoException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("productservice")
public interface IProductService extends RemoteService {

	/**
	 * Get a unique product from the server
	 * 
	 * @param revionsId
	 *            The ID (and Revision) of the {@link Product} you want to find. The ID in {@link RevisionId} must not
	 *            be null.
	 * @return The {@link Product} corresponding to the {@link RevisionId}
	 * @throws DaoException if something went wrong while requesting the data
	 */
	public Product getProduct(String id, String revision) throws DaoException;

	public Product getProduct(String id) throws DaoException;

	/**
	 * Depending on the search criterias returns the method a list with IProducts
	 * 
	 * @param searchCriteria
	 * @return
	 * @throws DaoException if something went wrong while requesting the data
	 */
	public List<Product> findProducts(Product searchCriteria) throws DaoException;


	/**
	 * CREATE or UPDATE a {@link Product} on the server.
	 * CREATE: A product with id=0 or RevisionID=null gets a new id and the initial revisionID 1.
	 * UPDATE: the existing product gets a new revisionID incremented by one. Throws an exception, if the given
	 * revisionID is not the last one for this product.
	 * 
	 * @param product
	 *            The {@link Product} you want to UPDATE/CREATE
	 * @return the SAVED or UPDATED {@link Product}
	 * @throws DaoException when something went wrong while saving the Product
	 */
	public Product saveProduct(Session session, Product product) throws UserNotLoggedInException, DaoException;

	/**
	 * Returns a list of all categories. TODO Return
	 * 
	 * @return
	 * @throws DaoException if something went wrong while requesting the data
	 */
	@Deprecated
	public List<Category> getCategories() throws DaoException;


	/**
	 * Returns all children for a {@link Category}.
	 * 
	 * @param revisionId
	 *            of an {@link Category}
	 * @return list of all children. Children will not hold there parents!
	 */
	public List<Category> getCategoryChildren(String id);

	/**
	 * Returns a {@link Category} with all parents!
	 * 
	 * @param revisionId
	 *            of an {@link Category}
	 * @return a {@link Category} with all parents!
	 */
	public Category getCategory(String id);


	/**
	 * Returns a {@link Package} by the given {@link RevisionId}. The {@link Package} will also hold its parent
	 * {@link Product}
	 * 
	 * @param id {@link Package Package's} ID
	 * @param revision {@link Package Package's} revision
	 * @return a {@link Package} by the given {@link RevisionId}. The {@link Package} will also hold its parent
	 *         {@link Product}
	 * @throws DaoException if something went wrong while requesting the data
	 */
	public Package getPackage(String id, String revision) throws DaoException;

	public Package getPackage(String id) throws DaoException;


	/**
	 * Save or create {@link Package}. The {@link Product} must be included.
	 * 
	 * @param ipackage
	 *            {@link Package} that should be created or saved.
	 * @return return the new {@link Package} with {@link RevisionId}
	 * @throws UserNotLoggedInException
	 * @throws DaoException if something went wrong while saving the Package
	 */
	public Package savePackage(Package ipackage) throws UserNotLoggedInException, DaoException;

}
