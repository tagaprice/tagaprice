package org.tagaprice.client.gwt.shared.rpc.productmanagement;

import org.tagaprice.client.gwt.shared.entities.IRevisionId;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("productservice")
public interface IProductService extends RemoteService {

	/**
	 * Get a unique product from the server
	 * 
	 * @param revionsId
	 *            The ID (and Revision) of the {@link IProduct} you wanna find. The ID in {@link IRevisionId} must not
	 *            be null.
	 * @return The {@link IProduct} corresponding to the {@link IRevisionId}
	 */
	public IProduct getProduct(IRevisionId revionsId);


	/**
	 * CREATE or UPDATE a {@link IProduct} on the server.
	 * @param product The {@link IProduct} you wanna UPDATE/CREATE
	 * @return the SAVED or UPDATED {@link IProduct}
	 */
	public IProduct saveProduct(IProduct product);
}
