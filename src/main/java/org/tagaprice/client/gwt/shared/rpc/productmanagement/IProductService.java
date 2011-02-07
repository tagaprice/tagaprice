package org.tagaprice.client.gwt.shared.rpc.productmanagement;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.IRevisionId;
import org.tagaprice.client.gwt.shared.entities.dump.ICategory;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;
import org.tagaprice.core.api.UserNotLoggedInException;

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
	 * @param searchCriteria
	 * @return
	 */
	public ArrayList<IProduct> getProducts(IProduct searchCriteria);


	/**
	 * CREATE or UPDATE a {@link IProduct} on the server.
	 * CREATE: A product with id=0 or RevisionID=null gets a new id and the initial revisionID 1.
	 * UPDATE: the existing product gets a new revisionID incremented by one. Throws an exception, if the given revisionID is not the last one for this product.
	 * @param product The {@link IProduct} you want to UPDATE/CREATE
	 * @return the SAVED or UPDATED {@link IProduct}
	 */
	public IProduct saveProduct(IProduct product) throws UserNotLoggedInException;

	public ArrayList<ICategory> getCategories();

}
