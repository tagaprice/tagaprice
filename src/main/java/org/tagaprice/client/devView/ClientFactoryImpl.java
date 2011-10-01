package org.tagaprice.client.devView;

import org.tagaprice.client.AccountPersistor;
import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.features.accountmanagement.inviteFriends.IInviteFriendsView;
import org.tagaprice.client.features.accountmanagement.login.ILoginView;
import org.tagaprice.client.features.accountmanagement.login.devView.LoginViewImpl;
import org.tagaprice.client.features.accountmanagement.login.devView.LogoutViewImpl;
import org.tagaprice.client.features.accountmanagement.settings.ISettingsView;
import org.tagaprice.client.features.categorymanagement.ICategoryView;
import org.tagaprice.client.features.productmanagement.createProduct.ICreateProductView;
import org.tagaprice.client.features.productmanagement.createProduct.devView.*;
import org.tagaprice.client.features.receiptmanagement.createReceipt.ICreateReceiptView;
import org.tagaprice.client.features.receiptmanagement.createReceipt.devView.CreateReceiptViewImpl;
import org.tagaprice.client.features.receiptmanagement.listReceipts.IListReceiptsView;
import org.tagaprice.client.features.receiptmanagement.listReceipts.devView.ListReceiptsViewImpl;
import org.tagaprice.client.features.searchmanagement.ISearchView;
import org.tagaprice.client.features.shopmanagement.createShop.ICreateShopView;
import org.tagaprice.client.features.shopmanagement.createShop.devView.CreateShopViewImpl;
import org.tagaprice.client.features.startmanagement.IStartView;
import org.tagaprice.client.features.startmanagement.devView.StartViewImpl;
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
import org.tagaprice.shared.rpc.shopmanagement.*;

import com.allen_sauer.gwt.log.client.Log;
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
	private static final PlaceController placeController = new PlaceController(ClientFactoryImpl.eventBus);


	//VIEWS
	private static ICreateShopView _createShopview;
	private static final ILoginView loginView = new LoginViewImpl();
	private static final ILoginView LOGOUT_VIEW = new LogoutViewImpl();
	private static ICreateReceiptView CREATE_RECEIPT_VIEW;
	private static final ICreateProductView createProductView = new CreateProductViewImpl();
	private static final IListReceiptsView listReceiptView = new ListReceiptsViewImpl();
	private static final IStartView startView = new StartViewImpl();

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
		return ClientFactoryImpl.createProductView;
	}

	@Override
	public ILoginView getLoginView() {
		return ClientFactoryImpl.loginView;
	}

	@Override
	public ICreateShopView getCreateShopView() {
		return ClientFactoryImpl._createShopview;
	}
	
	@Override
	public void setCreateShopview(ICreateShopView createShopView) {
		if(ClientFactoryImpl._createShopview==null)
			ClientFactoryImpl._createShopview = createShopView;		
	}

	@Override
	public ILoginServiceAsync getLoginService() {
		return ClientFactoryImpl.I_LOGIN_SERVICE_ASYNC;
	}

	@Override
	public ILoginView getLogoutView() {
		return ClientFactoryImpl.LOGOUT_VIEW;
	}

	@Override
	public IShopServiceAsync getShopService() {
		return ClientFactoryImpl.I_SHOP_SERVICE_ASYNC;
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
	public IListReceiptsView getListReceiptsView() {
		return ClientFactoryImpl.listReceiptView;
	}

	@Override
	public AccountPersistor getAccountPersistor() {
		return ClientFactoryImpl.s_accountPersistor;
	}

	@Override
	public IStartView getStartView() {
		return ClientFactoryImpl.startView;
	}

	@Override
	public ISearchView getSearchView() {
		// TODO Auto-generated method stub
		Log.debug("getSearchView is null ......................");
		return null;
	}

	@Override
	public ICategoryView getShopCategoryView() {
		// TODO Auto-generated method stub
		Log.debug("getShopCategoryView is null ......................");
		return null;
	}

	@Override
	public ICategoryView getProductCategoryView() {
		// TODO Auto-generated method stub
		Log.debug("getProductCategoryView is null ......................");
		return null;
	}

	@Override
	public ISettingsView getSettingsView() {
		// TODO Auto-generated method stub
		Log.debug("getSettingsView is null ......................");
		return null;
	}

	@Override
	public IInviteFriendsView getInivteFriendsView() {
		// TODO Auto-generated method stub
		return null;
	}

	


}
