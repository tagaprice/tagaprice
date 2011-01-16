package org.tagaprice.client.gwt.shared.rpc.productmanagement;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.IRevisionId;
import org.tagaprice.client.gwt.shared.entities.dump.ICategory;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IProductServiceAsync {

	void getProduct(IRevisionId revisionId, AsyncCallback<IProduct> callback);

	void getProducts(IProduct searchCriteria, AsyncCallback<ArrayList<IProduct>> callback);

	void saveProduct(IProduct product, AsyncCallback<IProduct> callback);

	void getCategories(AsyncCallback<ArrayList<ICategory>> callback);
}
