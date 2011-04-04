package org.tagaprice.shared.rpc.productmanagement;

import java.util.ArrayList;

import org.tagaprice.shared.entities.IRevisionId;
import org.tagaprice.shared.entities.dump.ICategory;
import org.tagaprice.shared.entities.productmanagement.IPackage;
import org.tagaprice.shared.entities.productmanagement.IProduct;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IProductServiceAsync {

	void getProduct(IRevisionId revisionId, AsyncCallback<IProduct> callback);

	void getProducts(IProduct searchCriteria, AsyncCallback<ArrayList<IProduct>> callback);

	void saveProduct(IProduct product, AsyncCallback<IProduct> callback);

	@Deprecated
	void getCategories(AsyncCallback<ArrayList<ICategory>> callback);

	void getCategoryChilds(IRevisionId revisionId, AsyncCallback<ICategory> callback);

	void getCategory(IRevisionId revisionId, AsyncCallback<ICategory> callback);


	void getPackage(IRevisionId revisionId, AsyncCallback<IPackage> callback);

	void savePackage(IPackage ipackage, AsyncCallback<IPackage> callback);
}
