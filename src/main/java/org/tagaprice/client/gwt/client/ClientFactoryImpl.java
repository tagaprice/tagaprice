package org.tagaprice.client.gwt.client;

import org.tagaprice.client.gwt.client.features.accountmanagement.login.ILoginView;
import org.tagaprice.client.gwt.client.features.accountmanagement.login.devView.LoginViewImpl;
import org.tagaprice.client.gwt.client.features.productmanagement.*;
import org.tagaprice.client.gwt.client.features.productmanagement.createProduct.ICreateProductView;
import org.tagaprice.client.gwt.client.features.productmanagement.createProduct.devView.CreateProductViewImpl;
import org.tagaprice.client.gwt.client.features.productmanagement.listProducts.*;
import org.tagaprice.client.gwt.client.features.productmanagement.listProducts.devView.ListProductsViewImpl;
import org.tagaprice.client.gwt.client.generics.ProductCoreColumnDefinitions;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;

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
	private static final ListProductsViewImpl<IProduct> productListView = new ListProductsViewImpl<IProduct>();
	private static final ICreateProductView editProductView = new CreateProductViewImpl();
	private static final ProductCoreColumnDefinitions productCoreColumnDefinitions = new ProductCoreColumnDefinitions();

	private static final ProductServiceDispatchImpl productServiceDispatch = new ProductServiceDispatchImpl();

	private static final ILoginView loginView = new LoginViewImpl();

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
	public ICreateProductView getEditProductView() {
		return ClientFactoryImpl.editProductView;
	}
	@Override
	public ListProductsView<IProduct> getListProductsView() {
		return ClientFactoryImpl.productListView;
	}

	@Override
	public ProductServiceDispatch getProductServiceDispatch() {
		return ClientFactoryImpl.productServiceDispatch;
	}

	@Override
	public ICreateProductView getCreateProductView() {
		return ClientFactoryImpl.editProductView;
	}

	@Override
	public ILoginView getLoginView() {
		return ClientFactoryImpl.loginView;
	}

}
