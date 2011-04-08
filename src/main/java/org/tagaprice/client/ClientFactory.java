package org.tagaprice.client;

import org.tagaprice.client.features.accountmanagement.login.ILoginView;
import org.tagaprice.client.features.accountmanagement.login.ILogoutView;
import org.tagaprice.client.features.accountmanagement.register.IRegisterView;
import org.tagaprice.client.features.accountmanagement.register.IRegisteredView;
import org.tagaprice.client.features.productmanagement.createProduct.*;
import org.tagaprice.client.features.productmanagement.listProducts.ListProductsView;
import org.tagaprice.client.features.receiptmanagement.createReceipt.ICreateReceiptView;
import org.tagaprice.client.features.receiptmanagement.listReceipts.IListReceiptsView;
import org.tagaprice.client.features.shopmanagement.createShop.ICreateShopView;
import org.tagaprice.client.features.shopmanagement.listShops.ListShopsView;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.rpc.accountmanagement.ILoginServiceAsync;
import org.tagaprice.shared.rpc.categorymanagement.ICategoryServiceAsync;
import org.tagaprice.shared.rpc.productmanagement.IProductServiceAsync;
import org.tagaprice.shared.rpc.receiptmanagement.IReceiptServiceAsync;
import org.tagaprice.shared.rpc.searchmanagement.ISearchServiceAsync;
import org.tagaprice.shared.rpc.shopmanagement.IShopServiceAsync;

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
	 * Singleton for ListProductsView
	 * 
	 * @return
	 */
	ListProductsView<Product> getListProductsView();

	/**
	 * Locale Dispatch for remote RPC Service
	 * 
	 * @return
	 */
	IProductServiceAsync getProductService();


	IReceiptServiceAsync getReceiptService();


	ILoginServiceAsync getLoginService();

	IShopServiceAsync getShopService();

	ICategoryServiceAsync getCategoryService();

	ISearchServiceAsync getSearchService();


	/****************** VIEWS ***********************/

	/**
	 * Returns the CreateProductView for the selected screen
	 * @return the CreateProductView for the selected screen
	 */
	ICreateProductView getCreateProductView();

	/**
	 * Singleton for EditProductView
	 * 
	 * @return
	 */
	ICreateProductView getEditProductView();


	/**
	 * Returns the LoginView
	 * @return the LoginView
	 */
	ILoginView getLoginView();


	/**
	 * This view displays a logout button
	 * @return ILogoutView
	 */
	ILogoutView getLogoutView();


	/**
	 * Returns the CreateShopView
	 * @return the CreateShopView
	 */
	ICreateShopView<ReceiptEntry> getCreateShopView();

	ListShopsView<Shop> getListShopsView();

	ICreateReceiptView getCreateReceiptView();


	IRegisterView getRegisterView();

	IRegisteredView getRegisteredView();

	IListReceiptsView getListReceiptsView();


	/****************** GlobalAddress ***********************/
	/**
	 * Returns global Address
	 */
	Address getAddress();

	/**
	 * Set Global Address
	 * @param address setGlobalAddress
	 */
	public void setAddress(Address address);
}
