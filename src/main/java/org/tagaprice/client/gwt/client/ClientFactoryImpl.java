package org.tagaprice.client.gwt.client;

import org.tagaprice.client.gwt.client.features.accountmanagement.login.ILoginView;
import org.tagaprice.client.gwt.client.features.accountmanagement.login.ILogoutView;
import org.tagaprice.client.gwt.client.features.accountmanagement.login.devView.LoginViewImpl;
import org.tagaprice.client.gwt.client.features.accountmanagement.login.devView.LogoutViewImpl;
import org.tagaprice.client.gwt.client.features.productmanagement.*;
import org.tagaprice.client.gwt.client.features.productmanagement.createProduct.ICreateProductView;
import org.tagaprice.client.gwt.client.features.productmanagement.createProduct.devView.*;
import org.tagaprice.client.gwt.client.features.productmanagement.listProducts.*;
import org.tagaprice.client.gwt.client.features.productmanagement.listProducts.devView.*;
import org.tagaprice.client.gwt.client.features.shopmanagement.createShop.ICreateShopView;
import org.tagaprice.client.gwt.client.features.shopmanagement.createShop.devView.CreateShopViewImpl;
import org.tagaprice.client.gwt.client.features.shopmanagement.listShops.ListShopsView;
import org.tagaprice.client.gwt.client.features.shopmanagement.listShops.devView.*;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;
import org.tagaprice.client.gwt.shared.rpc.accountmanagement.ILoginService;
import org.tagaprice.client.gwt.shared.rpc.accountmanagement.ILoginServiceAsync;
import org.tagaprice.client.gwt.shared.rpc.shopmanagement.*;

import com.google.gwt.core.client.GWT;
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
	private static final ICreateProductView createProductView = new CreateProductViewImpl();
	private static final ProductCoreColumnDefinitions productCoreColumnDefinitions = new ProductCoreColumnDefinitions();
	private static final ProductServiceDispatchImpl productServiceDispatch = new ProductServiceDispatchImpl();

	private static final IShopServiceAsync shopService = GWT.create(IShopService.class);
	private static final ICreateShopView createShopview = new CreateShopViewImpl();
	private static final ListShopsViewImpl<IShop> listShopsView = new ListShopsViewImpl<IShop>();
	private static final ShopColumnDefinitions shopColumnDefinitions = new ShopColumnDefinitions();

	private static final ILoginServiceAsync I_LOGIN_SERVICE_ASYNC = GWT.create(ILoginService.class);
	private static final ILoginView loginView = new LoginViewImpl();
	private static final ILogoutView LOGOUT_VIEW = new LogoutViewImpl();



	public ClientFactoryImpl() {
		ClientFactoryImpl.productListView.setColumnDefinitions(ClientFactoryImpl.productCoreColumnDefinitions
				.getColumnDefinitions());
		ClientFactoryImpl.listShopsView.setColumnDefinitions(ClientFactoryImpl.shopColumnDefinitions.getColumnDefinitions());
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
		return ClientFactoryImpl.createProductView;
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
		return ClientFactoryImpl.createProductView;
	}

	@Override
	public ILoginView getLoginView() {
		return ClientFactoryImpl.loginView;
	}

	@Override
	public ICreateShopView getCreateShopView() {
		return ClientFactoryImpl.createShopview;
	}

	@Override
	public ILoginServiceAsync getLoginService() {
		return ClientFactoryImpl.I_LOGIN_SERVICE_ASYNC;
	}

	@Override
	public ILogoutView getLogoutView() {
		return ClientFactoryImpl.LOGOUT_VIEW;
	}

	@Override
	public IShopServiceAsync getShopService() {
		return ClientFactoryImpl.shopService;
	}

	@Override
	public ListShopsView<IShop> getListShopsView() {
		return ClientFactoryImpl.listShopsView;
	}

}
