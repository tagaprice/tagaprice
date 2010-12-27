package org.tagaprice.client.gwt.test;

import java.util.ArrayList;

import org.tagaprice.client.gwt.client.features.productmanagement.ProductServiceDispatch;
import org.tagaprice.client.gwt.shared.entities.*;
import org.tagaprice.client.gwt.shared.entities.old.Product;
import org.tagaprice.client.gwt.shared.entities.old.ProductCore;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.NotificationMole;

public class ProductServiceDispatchSuccess implements ProductServiceDispatch {

	@Override
	public void getProductById(int id, AsyncCallback<Product> callback) {
		// TODO Auto-generated method stub
	}

	@Override
	public void getProductByName(String name, AsyncCallback<Product> callback) {
		// TODO Auto-generated method stub

	}

	public ArrayList<ProductCore> productCores;

	@Override
	public void getProducts(AsyncCallback<ArrayList<ProductCore>> callback) {
		callback.onSuccess(productCores);

	}

	@Override
	public void getProductsByCategory(String category, AsyncCallback<Product> callback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveProduct(Product product, boolean exists, AsyncCallback<Void> callback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMole(NotificationMole mole) {
		// TODO Auto-generated method stub

	}

}
