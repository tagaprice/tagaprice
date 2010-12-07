package org.tagaprice.client.gwt.client;

import org.tagaprice.client.gwt.client.dispatch.ProductServiceDispatch;
import org.tagaprice.client.gwt.client.generics.ProductCoreColumnDefinitions;
import org.tagaprice.client.gwt.client.ui.*;
import org.tagaprice.client.gwt.shared.entities.ProductCore;

import com.google.gwt.event.shared.*;
import com.google.gwt.place.shared.PlaceController;
/**
 * The ClientFactory provieds an EventBus, a PlaceController and the Views(EditProduct and ListProducts)
 * @author Helga Weik (kaltra)
 *
 */
public class ClientFactoryImpl implements ClientFactory {
	private static final EventBus eventBus = new SimpleEventBus();
	private static final PlaceController placeController = new PlaceController(
			eventBus);
	private static final ListProductsViewImpl<ProductCore> productListView = new ListProductsViewImpl<ProductCore>();
	private static final EditProductView editProductView = new EditProductViewImpl();
	private static final ProductCoreColumnDefinitions productCoreColumnDefinitions = new ProductCoreColumnDefinitions();

	private static final ProductServiceDispatch productServiceDispatch = new ProductServiceDispatch();

	public ClientFactoryImpl() {
		productListView.setColumnDefinitions(productCoreColumnDefinitions
				.getColumnDefinitions());
	}
/**
 * The EventBus return an event ??
 */
	@Override
	public EventBus getEventBus() {
		return eventBus;
	}
/**
 * The PlaceController registers an event???
 */
	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}
/**
 * 
 */
	@Override
	public EditProductView getEditProductView() {
		return editProductView;
	}
/**
 * 
 */
	@Override
	public ListProductsView<ProductCore> getListProductsView() {
		return productListView;
	}

	@Override
	public ProductServiceDispatch getProductServiceDispatch() {
		return productServiceDispatch;
	}

}
