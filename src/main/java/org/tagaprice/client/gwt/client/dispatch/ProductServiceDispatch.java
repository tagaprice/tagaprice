package org.tagaprice.client.gwt.client.dispatch;

import java.util.ArrayList;

import org.tagaprice.client.gwt.client.rpc.*;
import org.tagaprice.client.gwt.shared.entities.*;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * forwards calls to the real ProductService. makes caching possible.
 * 
 */

public class ProductServiceDispatch implements ProductServiceAsync {
	private static ProductServiceAsync productServiceAsync = GWT
			.create(ProductService.class);

	@Override
	public void getProductById(int id, final AsyncCallback<Product> callback) {
		productServiceAsync.getProductById(id, new AsyncCallback<Product>() {

			@Override
			public void onSuccess(Product result) {
				callback.onSuccess(result);
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
		// Caching...

		// Anfrage schon mal bekommen?
		// Ja -> aus dem Cache holen...

		// Nein -> Anfrage an Server weiter leiten
		productServiceAsync
				.getProducts(new AsyncCallback<ArrayList<ProductCore>>() {

					@Override
					public void onSuccess(ArrayList<ProductCore> result) {
						callback.onSuccess(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						callback.onFailure(caught);
					}
				});

	}

	@Override
	public void getProductsByCategory(String category,
			AsyncCallback<Product> callback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveProduct(Product product, boolean exists,
			final AsyncCallback<Void> callback) {
		productServiceAsync.saveProduct(product, exists,
				new AsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
						callback.onSuccess(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						callback.onFailure(caught);
					}
				});

	}
}
