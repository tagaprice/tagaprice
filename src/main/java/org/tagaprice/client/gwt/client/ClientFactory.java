package org.tagaprice.client.gwt.client;

import org.tagaprice.client.gwt.client.features.productmanagement.ProductServiceDispatch;
import org.tagaprice.client.gwt.client.features.productmanagement.editProduct.EditProductView;
import org.tagaprice.client.gwt.client.features.productmanagement.listProducts.ListProductsView;
import org.tagaprice.client.gwt.shared.entities.ProductCore;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;

public interface ClientFactory {
	/**
	 * GWT Magic EventBus. Is never used programmatically.
	 * 
	 * @return
	 */
	EventBus getEventBus();

	/**
	 * GWT Magic PlaceController. Is used to change the Place (-> Activity ->
	 * Data -> View)
	 * 
	 * @return
	 */
	PlaceController getPlaceController();

	/**
	 * Singleton for EditProductView
	 * 
	 * @return
	 */
	EditProductView getEditProductView();

	/**
	 * Singleton for ListProductsView
	 * 
	 * @return
	 */
	ListProductsView<ProductCore> getListProductsView();

	/**
	 * Locale Dispatch for remote RPC Service
	 * 
	 * @return
	 */
	ProductServiceDispatch getProductServiceDispatch();
}
