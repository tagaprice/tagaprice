package org.tagaprice.client.gwt.shared.rpc.productmanagement;

import org.tagaprice.client.gwt.shared.entities.IRevisionId;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IProductServiceAsync {

	void getProduct(IRevisionId revisionId, AsyncCallback<IProduct> callback);

	void saveProduct(IProduct product, AsyncCallback<IProduct> callback);
}
