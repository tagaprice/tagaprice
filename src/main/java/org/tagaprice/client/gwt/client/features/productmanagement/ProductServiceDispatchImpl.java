package org.tagaprice.client.gwt.client.features.productmanagement;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.*;
import org.tagaprice.client.gwt.shared.rpc.*;
import org.tagaprice.client.gwt.shared.rpc.productmanagement.*;
import org.tagaprice.client.gwt.shared.rpc.productmanagement.old.ProductService;
import org.tagaprice.client.gwt.shared.rpc.productmanagement.old.ProductServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.NotificationMole;

/**
 * forwards calls to the real ProductService. makes caching possible.
 * 
 */

public class ProductServiceDispatchImpl implements ProductServiceDispatch {
	private static ProductServiceAsync productServiceAsync = GWT.create(ProductService.class);



	private NotificationMole mole;

	@Override
	public void setMole(NotificationMole mole) {
		this.mole = mole;
	}

	@Override
	public void getProductById(int id, final AsyncCallback<Product> callback) {
		if (ProductServiceDispatchImpl.productServiceAsync == null) {
			ProductServiceDispatchImpl.productServiceAsync = GWT.create(ProductService.class);
		}
		mole.show("loading product");
		ProductServiceDispatchImpl.productServiceAsync.getProductById(id, new AsyncCallback<Product>() {

			@Override
			public void onSuccess(Product result) {
				callback.onSuccess(result);
				mole.hide();
			}

			@Override
			public void onFailure(Throwable caught) {
				callback.onFailure(caught);

			}
		});

	}

	@Override
	public void getProductByName(String name, AsyncCallback<Product> callback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getProducts(final AsyncCallback<ArrayList<ProductCore>> callback) {
		mole.show("loading products");
		// Caching...

		// Anfrage schon mal bekommen?
		// Ja -> aus dem Cache holen...

		// Nein -> Anfrage an Server weiter leiten
		ProductServiceDispatchImpl.productServiceAsync.getProducts(new AsyncCallback<ArrayList<ProductCore>>() {

			@Override
			public void onSuccess(ArrayList<ProductCore> result) {
				callback.onSuccess(result);
				mole.hide();
			}

			@Override
			public void onFailure(Throwable caught) {
				callback.onFailure(caught);
			}
		});

	}

	@Override
	public void getProductsByCategory(String category, AsyncCallback<Product> callback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveProduct(Product product, boolean exists, final AsyncCallback<Void> callback) {
		mole.show("saving");
		ProductServiceDispatchImpl.productServiceAsync.saveProduct(product, exists, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				callback.onSuccess(result);
				mole.hide();
			}

			@Override
			public void onFailure(Throwable caught) {
				callback.onFailure(caught);
			}
		});

	}
}
