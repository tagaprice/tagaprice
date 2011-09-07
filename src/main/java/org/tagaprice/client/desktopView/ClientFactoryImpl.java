package org.tagaprice.client.desktopView;

import org.tagaprice.client.AccountPersistor;
import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.features.accountmanagement.login.ILoginView;
import org.tagaprice.client.features.accountmanagement.login.ILogoutView;
import org.tagaprice.client.features.accountmanagement.login.desktopView.LoginViewImpl;
import org.tagaprice.client.features.accountmanagement.login.devView.LogoutViewImpl;
import org.tagaprice.client.features.accountmanagement.register.IRegisterView;
import org.tagaprice.client.features.accountmanagement.register.IRegisteredView;
import org.tagaprice.client.features.accountmanagement.register.desktopView.RegisterViewImpl;
import org.tagaprice.client.features.accountmanagement.register.devView.RegisteredViewImpl;
import org.tagaprice.client.features.productmanagement.createProduct.ICreateProductView;
import org.tagaprice.client.features.productmanagement.createProduct.desktopView.CreateProductViewImpl;
import org.tagaprice.client.features.productmanagement.listProducts.ListProductsView;
import org.tagaprice.client.features.productmanagement.listProducts.devView.ListProductsViewImpl;
import org.tagaprice.client.features.receiptmanagement.createReceipt.ICreateReceiptView;
import org.tagaprice.client.features.receiptmanagement.createReceipt.desktopView.CreateReceiptViewImpl;
import org.tagaprice.client.features.receiptmanagement.listReceipts.IListReceiptsView;
import org.tagaprice.client.features.receiptmanagement.listReceipts.desktopView.ListReceiptsViewImpl;
import org.tagaprice.client.features.searchmanagement.ISearchView;
import org.tagaprice.client.features.searchmanagement.desktopView.SearchView;
import org.tagaprice.client.features.shopmanagement.createShop.ICreateShopView;
import org.tagaprice.client.features.shopmanagement.createShop.desktopView.CreateShopViewImpl;
import org.tagaprice.client.features.shopmanagement.listShops.ListShopsView;
import org.tagaprice.client.features.shopmanagement.listShops.devView.ListShopsViewImpl;
import org.tagaprice.client.features.startmanagement.IStartView;
import org.tagaprice.client.features.startmanagement.desktopView.StartViewImpl;
import org.tagaprice.shared.rpc.accountmanagement.ILoginService;
import org.tagaprice.shared.rpc.accountmanagement.ILoginServiceAsync;
import org.tagaprice.shared.rpc.categorymanagement.ICategoryService;
import org.tagaprice.shared.rpc.categorymanagement.ICategoryServiceAsync;
import org.tagaprice.shared.rpc.productmanagement.IProductService;
import org.tagaprice.shared.rpc.productmanagement.IProductServiceAsync;
import org.tagaprice.shared.rpc.receiptmanagement.IReceiptService;
import org.tagaprice.shared.rpc.receiptmanagement.IReceiptServiceAsync;
import org.tagaprice.shared.rpc.searchmanagement.ISearchService;
import org.tagaprice.shared.rpc.searchmanagement.ISearchServiceAsync;
import org.tagaprice.shared.rpc.shopmanagement.IShopService;
import org.tagaprice.shared.rpc.shopmanagement.IShopServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
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
	private static final PlaceController placeController = new PlaceController(ClientFactoryImpl.eventBus);


	//VIEWS
	private static CreateShopViewImpl createShopview;
	private static ListShopsViewImpl listShopsView;
	private static ILoginView loginView;
	private static ILogoutView LOGOUT_VIEW;
	private static ICreateReceiptView CREATE_RECEIPT_VIEW;
	private static ListProductsViewImpl productListView;
	private static ICreateProductView createProductView;
	private static IRegisterView registerView;
	private static IRegisteredView registeredView;
	private static IListReceiptsView listReceiptView;
	private static IStartView startView;
	private static ISearchView searchView;

	//RPC
	private static final IShopServiceAsync I_SHOP_SERVICE_ASYNC = GWT.create(IShopService.class);
	private static final IReceiptServiceAsync I_RECEIPT_SERVICE_ASYNC = GWT.create(IReceiptService.class);
	private static final IProductServiceAsync I_PRODUCT_SERVICE_ASYNC = GWT.create(IProductService.class);
	private static final ICategoryServiceAsync I_CATEGORY_SERVICE_ASYNC = GWT.create(ICategoryService.class);
	private static final ISearchServiceAsync I_SEARCH_SERVICE_ASYNC = GWT.create(ISearchService.class);
	private static final ILoginServiceAsync I_LOGIN_SERVICE_ASYNC = GWT.create(ILoginService.class);

	private static AccountPersistor s_accountPersistor = new AccountPersistor();

	public ClientFactoryImpl() {
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
	public ListProductsView getListProductsView() {
		if(productListView==null)productListView = new ListProductsViewImpl();
		return ClientFactoryImpl.productListView;
	}
	@Override
	public ICreateProductView getCreateProductView() {
		if(createProductView==null)createProductView = new CreateProductViewImpl();
		return ClientFactoryImpl.createProductView;
	}

	@Override
	public ILoginView getLoginView() {
		if(loginView==null)loginView = new LoginViewImpl();
		return ClientFactoryImpl.loginView;
	}

	@Override
	public ICreateShopView getCreateShopView() {
		if(createShopview==null)createShopview = new CreateShopViewImpl();
		return ClientFactoryImpl.createShopview;
	}

	

	@Override
	public ILogoutView getLogoutView() {
		if(LOGOUT_VIEW==null)LOGOUT_VIEW = new LogoutViewImpl();
		return ClientFactoryImpl.LOGOUT_VIEW;
	}

	

	@Override
	public ListShopsView getListShopsView() {
		if(listShopsView==null)listShopsView = new ListShopsViewImpl();
		return ClientFactoryImpl.listShopsView;
	}

	@Override
	public ICreateReceiptView getCreateReceiptView() {
		if(CREATE_RECEIPT_VIEW==null)CREATE_RECEIPT_VIEW = new CreateReceiptViewImpl();
		return ClientFactoryImpl.CREATE_RECEIPT_VIEW;
	}



	@Override
	public IRegisterView getRegisterView() {
		if(registerView==null)registerView = new RegisterViewImpl();
		return ClientFactoryImpl.registerView;
	}

	@Override
	public IRegisteredView getRegisteredView() {
		if(registeredView==null)registeredView = new RegisteredViewImpl();
		return ClientFactoryImpl.registeredView;
	}

	@Override
	public IListReceiptsView getListReceiptsView() {
		if(listReceiptView==null)listReceiptView = new ListReceiptsViewImpl();
		return ClientFactoryImpl.listReceiptView;
	}

	@Override
	public IStartView getStartView() {
		if(startView==null)startView = new StartViewImpl();
		return ClientFactoryImpl.startView;
	}
	
	@Override
	public ISearchView getSearchView() {
		if(searchView==null)
			searchView=new SearchView();
		return ClientFactoryImpl.searchView;
	}
	
	@Override
	public ILoginServiceAsync getLoginService() {
		return ClientFactoryImpl.I_LOGIN_SERVICE_ASYNC;
	}
	
	@Override
	public AccountPersistor getAccountPersistor() {
		return ClientFactoryImpl.s_accountPersistor;
	}
	
	@Override
	public IShopServiceAsync getShopService() {
		return ClientFactoryImpl.I_SHOP_SERVICE_ASYNC;
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

	


}
