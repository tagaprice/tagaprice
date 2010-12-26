package org.tagaprice.client.gwt.shared.rpc.productmanagement.old;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.*;
import org.tagaprice.client.gwt.shared.entities.old.Product;
import org.tagaprice.client.gwt.shared.entities.old.ProductCore;



import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProductServiceAsync {

	void getProductById(int id, AsyncCallback<Product> callback);

	void getProductByName(String name, AsyncCallback<Product> callback);

	void getProducts(AsyncCallback<ArrayList<ProductCore>> callback);

	void getProductsByCategory(String category, AsyncCallback<Product> callback);

	void saveProduct(Product product, boolean exists,
			AsyncCallback<Void> callback);

}
