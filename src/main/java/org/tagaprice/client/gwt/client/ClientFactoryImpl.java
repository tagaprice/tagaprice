package org.tagaprice.client.gwt.client;

import org.tagaprice.client.gwt.client.features.accountmanagement.login.ILoginView;
import org.tagaprice.client.gwt.client.features.accountmanagement.login.ILogoutView;
import org.tagaprice.client.gwt.client.features.accountmanagement.login.devView.LoginViewImpl;
import org.tagaprice.client.gwt.client.features.accountmanagement.login.devView.LogoutViewImpl;
import org.tagaprice.client.gwt.client.features.accountmanagement.register.IRegisterView;
import org.tagaprice.client.gwt.client.features.accountmanagement.register.devView.RegisterViewImpl;
import org.tagaprice.client.gwt.client.features.productmanagement.createProduct.ICreateProductView;
import org.tagaprice.client.gwt.client.features.productmanagement.createProduct.devView.*;
import org.tagaprice.client.gwt.client.features.productmanagement.listProducts.*;
import org.tagaprice.client.gwt.client.features.productmanagement.listProducts.devView.*;
import org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt.ICreateReceiptView;
import org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt.devView.CreateReceiptViewImpl;
import org.tagaprice.client.gwt.client.features.shopmanagement.createShop.ICreateShopView;
import org.tagaprice.client.gwt.client.features.shopmanagement.createShop.devView.CreateShopViewImpl;
import org.tagaprice.client.gwt.client.features.shopmanagement.listShops.ListShopsView;
import org.tagaprice.client.gwt.client.features.shopmanagement.listShops.devView.*;
import org.tagaprice.client.gwt.shared.entities.Address;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;
import org.tagaprice.client.gwt.shared.entities.receiptManagement.IReceiptEntry;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;
import org.tagaprice.client.gwt.shared.rpc.accountmanagement.ILoginService;
import org.tagaprice.client.gwt.shared.rpc.accountmanagement.ILoginServiceAsync;
import org.tagaprice.client.gwt.shared.rpc.categorymanagement.ICategoryService;
import org.tagaprice.client.gwt.shared.rpc.categorymanagement.ICategoryServiceAsync;
import org.tagaprice.client.gwt.shared.rpc.productmanagement.IProductService;
import org.tagaprice.client.gwt.shared.rpc.productmanagement.IProductServiceAsync;
import org.tagaprice.client.gwt.shared.rpc.receiptmanagement.IReceiptService;
import org.tagaprice.client.gwt.shared.rpc.receiptmanagement.IReceiptServiceAsync;
import org.tagaprice.client.gwt.shared.rpc.searchmanagement.ISearchService;
import org.tagaprice.client.gwt.shared.rpc.searchmanagement.ISearchServiceAsync;
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
	private static final ProductCoreColumnDefinitions productCoreColumnDefinitions = new ProductCoreColumnDefinitions();
	private static final ShopColumnDefinitions shopColumnDefinitions = new ShopColumnDefinitions();

	//private static final ProductServiceDispatchImpl productServiceDispatch = new ProductServiceDispatchImpl();

	//VIEWS
	private static final CreateShopViewImpl<IReceiptEntry> createShopview = new CreateShopViewImpl<IReceiptEntry>();
	private static final ListShopsViewImpl<IShop> listShopsView = new ListShopsViewImpl<IShop>();
	private static final ILoginView loginView = new LoginViewImpl();
	private static final ILogoutView LOGOUT_VIEW = new LogoutViewImpl();
	private static final ICreateReceiptView CREATE_RECEIPT_VIEW = new CreateReceiptViewImpl();
	private static final ListProductsViewImpl<IProduct> productListView = new ListProductsViewImpl<IProduct>();
	private static final ICreateProductView createProductView = new CreateProductViewImpl();
	private static final IRegisterView registerView = new RegisterViewImpl();
	//RPC
	private static final IShopServiceAsync I_SHOP_SERVICE_ASYNC = GWT.create(IShopService.class);
	private static final IReceiptServiceAsync I_RECEIPT_SERVICE_ASYNC = GWT.create(IReceiptService.class);
	private static final IProductServiceAsync I_PRODUCT_SERVICE_ASYNC = GWT.create(IProductService.class);
	private static final ICategoryServiceAsync I_CATEGORY_SERVICE_ASYNC = GWT.create(ICategoryService.class);
	private static final ISearchServiceAsync I_SEARCH_SERVICE_ASYNC = GWT.create(ISearchService.class);
	private static final ILoginServiceAsync I_LOGIN_SERVICE_ASYNC = GWT.create(ILoginService.class);

	private static Address I_ADDRESS;

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
	public ICreateProductView getCreateProductView() {
		return ClientFactoryImpl.createProductView;
	}

	@Override
	public ILoginView getLoginView() {
		return ClientFactoryImpl.loginView;
	}

	@Override
	public ICreateShopView<IReceiptEntry> getCreateShopView() {
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
		return ClientFactoryImpl.I_SHOP_SERVICE_ASYNC;
	}

	@Override
	public ListShopsView<IShop> getListShopsView() {
		return ClientFactoryImpl.listShopsView;
	}

	@Override
	public ICreateReceiptView getCreateReceiptView() {
		return ClientFactoryImpl.CREATE_RECEIPT_VIEW;
	}

	@Override
	public IReceiptServiceAsync getReceiptService() {
		return ClientFactoryImpl.I_RECEIPT_SERVICE_ASYNC;
	}

	@Override
	public IProductServiceAsync getProductService() {
		return ClientFactoryImpl.I_PRODUCT_SERVICE_ASYNC;
	}

	@Override
	public ICategoryServiceAsync getCategoryService() {
		return ClientFactoryImpl.I_CATEGORY_SERVICE_ASYNC;
	}

	@Override
	public ISearchServiceAsync getSearchService() {
		// TODO Auto-generated method stub
		return ClientFactoryImpl.I_SEARCH_SERVICE_ASYNC;
	}

	@Override
	public Address getAddress() {
		return ClientFactoryImpl.I_ADDRESS;
	}

	@Override
	public void setAddress(Address address) {
		if(ClientFactoryImpl.I_ADDRESS==null)ClientFactoryImpl.I_ADDRESS=new Address();

		ClientFactoryImpl.I_ADDRESS.setStreet(address.getStreet());
		ClientFactoryImpl.I_ADDRESS.setCity(address.getCity());
		ClientFactoryImpl.I_ADDRESS.setCountry(address.getCountry());
		ClientFactoryImpl.I_ADDRESS.setZip(address.getZip());
		ClientFactoryImpl.I_ADDRESS.setLat(address.getLat());
		ClientFactoryImpl.I_ADDRESS.setLng(address.getLng());
	}

	@Override
	public IRegisterView getRegisterView() {
		return ClientFactoryImpl.registerView;
	}


}
