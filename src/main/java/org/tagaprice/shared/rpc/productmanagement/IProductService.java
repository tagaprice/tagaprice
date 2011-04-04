package org.tagaprice.client.gwt.shared.rpc.productmanagement;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.IRevisionId;
import org.tagaprice.client.gwt.shared.entities.dump.ICategory;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IPackage;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;
import org.tagaprice.client.gwt.shared.exceptions.UserNotLoggedInException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("productservice")
public interface IProductService extends RemoteService {

	/**
	 * Get a unique product from the server
	 * 
	 * @param revionsId
	 *            The ID (and Revision) of the {@link IProduct} you want to find. The ID in {@link IRevisionId} must not
	 *            be null.
	 * @return The {@link IProduct} corresponding to the {@link IRevisionId}
	 */
	public IProduct getProduct(IRevisionId revionsId);

	/**
	 * Depending on the search criterias returns the method a list with IProducts
	 * 
	 * @param searchCriteria
	 * @return
	 */
	public ArrayList<IProduct> getProducts(IProduct searchCriteria);


	/**
	 * CREATE or UPDATE a {@link IProduct} on the server.
	 * CREATE: A product with id=0 or RevisionID=null gets a new id and the initial revisionID 1.
	 * UPDATE: the existing product gets a new revisionID incremented by one. Throws an exception, if the given
	 * revisionID is not the last one for this product.
	 * 
	 * @param product
	 *            The {@link IProduct} you want to UPDATE/CREATE
	 * @return the SAVED or UPDATED {@link IProduct}
	 */
	public IProduct saveProduct(IProduct product) throws UserNotLoggedInException;

	/**
	 * Returns a list of all categories. TODO Return
	 * 
	 * @return
	 */
	@Deprecated
	public ArrayList<ICategory> getCategories();


	/**
	 * Returns all children for a {@link ICategory}.
	 * 
	 * @param revisionId
	 *            of an {@link ICategory}
	 * @return list of all children. Children will not hold there parents!
	 */
	public ArrayList<ICategory> getCategoryChilds(IRevisionId revisionId);

	/**
	 * Returns a {@link ICategory} with all parents!
	 * 
	 * @param revisionId
	 *            of an {@link ICategory}
	 * @return a {@link ICategory} with all parents!
	 */
	public ICategory getCategory(IRevisionId revisionId);


	/**
	 * Returns a {@link IPackage} by the given {@link IRevisionId}. The {@link IPackage} will also hold its parent
	 * {@link IProduct}
	 * 
	 * @param revisionId
	 *            of the {@link IPackage}
	 * @return a {@link IPackage} by the given {@link IRevisionId}. The {@link IPackage} will also hold its parent
	 *         {@link IProduct}
	 */
	public IPackage getPackage(IRevisionId revisionId);


	/**
	 * Save or create {@link IPackage}. The {@link IProduct} must be included.
	 * 
	 * @param ipackage
	 *            {@link IPackage} that should be created or saved.
	 * @return return the new {@link IPackage} with {@link IRevisionId}
	 * @throws UserNotLoggedInException
	 */
	public IPackage savePackage(IPackage ipackage) throws UserNotLoggedInException;

}
