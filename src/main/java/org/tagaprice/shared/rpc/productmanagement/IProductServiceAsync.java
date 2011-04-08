package org.tagaprice.shared.rpc.productmanagement;

import java.util.List;

import org.tagaprice.shared.entities.IRevisionId;
import org.tagaprice.shared.entities.dump.Category;
import org.tagaprice.shared.entities.productmanagement.IPackage;
import org.tagaprice.shared.entities.productmanagement.Product;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IProductServiceAsync {

	void getProduct(String id, String revision, AsyncCallback<Product> callback);
	
	void getProduct(String id, AsyncCallback<Product> callback);

	void findProducts(Product searchCriteria, AsyncCallback<List<Product>> callback);

	void saveProduct(Product product, AsyncCallback<Product> callback);

	@Deprecated
	void getCategories(AsyncCallback<List<Category>> callback);


	void getPackage(String id, String revision, AsyncCallback<IPackage> callback);
	void getPackage(String id, AsyncCallback<IPackage> callback);

	void savePackage(IPackage ipackage, AsyncCallback<IPackage> callback);

	void getCategory(IRevisionId revisionId, AsyncCallback<Category> callback);

	void getCategoryChilds(IRevisionId revisionId, AsyncCallback<List<Category>> callback);
}
