package org.tagaprice.client.gwt.client;

import org.tagaprice.client.gwt.client.dispatch.*;
import org.tagaprice.client.gwt.client.generics.ProductCoreColumnDefinitions;
import org.tagaprice.client.gwt.client.ui.*;
import org.tagaprice.client.gwt.shared.entities.ProductCore;

import com.google.gwt.event.shared.*;
import com.google.gwt.place.shared.PlaceController;
/**
 * The ClientFactory provides singletones for <ul><li>an EventBus</li><li>a PlaceController</li><li>and the Views</li></ul>.
 * @author Helga Weik (kaltra)
 *
 */
public class ClientFactoryImpl implements ClientFactory {
	/**
	 * The EventBus is unique for the whole GWT Application.
	 */
	private static final EventBus eventBus = new SimpleEventBus();
	/**
	 * The PlaceController is unique for the whole GWT Application.
	 */
	private static final PlaceController placeController = new PlaceController(
			ClientFactoryImpl.eventBus);
	private static final ListProductsViewImpl<ProductCore> productListView = new ListProductsViewImpl<ProductCore>();
	private static final EditProductView editProductView = new EditProductViewImpl();
	private static final ProductCoreColumnDefinitions productCoreColumnDefinitions = new ProductCoreColumnDefinitions();

	private static final ProductServiceDispatchImpl productServiceDispatch = new ProductServiceDispatchImpl();

	public ClientFactoryImpl() {
		ClientFactoryImpl.productListView.setColumnDefinitions(ClientFactoryImpl.productCoreColumnDefinitions
				.getColumnDefinitions());
	}

	@Override
	public EventBus getEventBus() {
		return ClientFactoryImpl.eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		return ClientFactoryImpl.placeController;
	}

	@Override
	public EditProductView getEditProductView() {
		return ClientFactoryImpl.editProductView;
	}
	@Override
	public ListProductsView<ProductCore> getListProductsView() {
		return ClientFactoryImpl.productListView;
	}

	@Override
	public ProductServiceDispatch getProductServiceDispatch() {
		return ClientFactoryImpl.productServiceDispatch;
	}

}
