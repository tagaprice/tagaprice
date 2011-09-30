package org.tagaprice.client.desktopView;

import org.tagaprice.client.AccountPersistor;
import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.features.accountmanagement.login.ILoginView;
import org.tagaprice.client.features.accountmanagement.login.desktopView.LoginViewImpl;
import org.tagaprice.client.features.accountmanagement.login.devView.LogoutViewImpl;
import org.tagaprice.client.features.accountmanagement.settings.ISettingsView;
import org.tagaprice.client.features.accountmanagement.settings.desktopView.SettingsView;
import org.tagaprice.client.features.categorymanagement.ICategoryView;
import org.tagaprice.client.features.categorymanagement.product.desktopView.ProductCategoryView;
import org.tagaprice.client.features.categorymanagement.shop.desktopView.ShopCategoryView;
import org.tagaprice.client.features.productmanagement.createProduct.ICreateProductView;
import org.tagaprice.client.features.productmanagement.createProduct.desktopView.CreateProductViewImpl;
import org.tagaprice.client.features.receiptmanagement.createReceipt.ICreateReceiptView;
import org.tagaprice.client.features.receiptmanagement.createReceipt.desktopView.CreateReceiptViewImpl;
import org.tagaprice.client.features.receiptmanagement.listReceipts.IListReceiptsView;
import org.tagaprice.client.features.receiptmanagement.listReceipts.desktopView.ListReceiptsViewImpl;
import org.tagaprice.client.features.searchmanagement.ISearchView;
import org.tagaprice.client.features.searchmanagement.desktopView.SearchView;
import org.tagaprice.client.features.shopmanagement.createShop.ICreateShopView;
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
	private static ICreateShopView createShopview;
	private static ILoginView loginView;
	private static ILoginView LOGOUT_VIEW;
	private static ICreateReceiptView CREATE_RECEIPT_VIEW;
	private static ICreateProductView createProductView;
	private static IListReceiptsView listReceiptView;
	private static IStartView startView;
	private static ISearchView searchView;
	private static ICategoryView shopCategoryView;
	private static ICategoryView productCategoryView;
	private static ISettingsView settingsView;

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
		return ClientFactoryImpl.createShopview;
	}

	@Override
	public void setCreateShopview(ICreateShopView createShopView) {
		if(ClientFactoryImpl.createShopview==null)
			ClientFactoryImpl.createShopview = createShopView;		
	}

	

	@Override
	public ILoginView getLogoutView() {
		if(LOGOUT_VIEW==null)LOGOUT_VIEW = new LogoutViewImpl();
		return ClientFactoryImpl.LOGOUT_VIEW;
	}

	


	@Override
	public ICreateReceiptView getCreateReceiptView() {
		return ClientFactoryImpl.CREATE_RECEIPT_VIEW;
	}


	@Override
	public void setCreateReceiptView(ICreateReceiptView createReceiptView) {
		if(CREATE_RECEIPT_VIEW==null)CREATE_RECEIPT_VIEW = createReceiptView;		
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
	public ICategoryView getShopCategoryView() {
		if(shopCategoryView==null)
			shopCategoryView=new ShopCategoryView();
		return shopCategoryView;
	}

	@Override
	public ICategoryView getProductCategoryView() {
		if(productCategoryView==null)
			productCategoryView=new ProductCategoryView();
		return productCategoryView;
	}
	
	@Override
	public ISettingsView getSettingsView() {
		if(settingsView==null)
			settingsView=new SettingsView();
		return settingsView;
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
