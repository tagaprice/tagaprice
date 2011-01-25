package org.tagaprice.client.gwt.client.features.shopmanagement.listShops;

import java.util.ArrayList;

import org.tagaprice.client.gwt.client.ClientFactory;
import org.tagaprice.client.gwt.shared.entities.productmanagement.*;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.*;
import org.tagaprice.client.gwt.shared.logging.*;
import org.tagaprice.client.gwt.shared.rpc.shopmanagement.IShopServiceAsync;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

/**
 * The class implements the Presenter interface of the ListProductsView
 * 
 * @author Helga Weik (kaltra)
 * 
 */
public class ListShopsActivity extends AbstractActivity implements org.tagaprice.client.gwt.client.features.shopmanagement.listShops.ListShopsView.Presenter {
	private static MyLogger logger = LoggerFactory.getLogger(ListShopsActivity.class);

	private ListShopsPlace place;
	private ClientFactory clientFactory;
	private ArrayList<IShop> products;

	private final ListShopsView<IShop> listShopsView;
	private IShopServiceAsync shopServiceAsync;

	/**
	 * 
	 * The ListProductsActivity constructor takes the argument ProductListPlace
	 * and the argument ClientFactory
	 * A new activity will be created for each ListProductsPlace
	 * The ClientFactory is used by the ListProductsPlace to obtain a reference to the ListProductsView
	 * 
	 * @param place
	 * @param clientFactory
	 */
	public ListShopsActivity(ListShopsPlace place, ClientFactory clientFactory) {
		// Store parameters...
		this.place = place;
		this.clientFactory = clientFactory;

		// load data to work
		this.listShopsView = this.clientFactory.getListShopsView();
		this.shopServiceAsync = this.clientFactory.getShopService();
	}

	/**
	 * The method provides a warning to the user before stopping the ListProducts activity
	 * by closing the window or navigating to another place
	 */

	@Override
	public void goTo(Place place) {
		this.clientFactory.getPlaceController().goTo(place);
	}

	@Override
	public String mayStop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCancel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStop() {
		this.listShopsView.setPresenter(null);

	}

	/**
	 * The start method is invoked by the ActivityManager and sets things in motion by updating
	 * the view and starts a new Activity
	 */
	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) {
		ListShopsActivity.logger.log("Activity starts...");

		listShopsView.setPresenter(this);

		this.shopServiceAsync.getShops(null, new AsyncCallback<ArrayList<IShop>>() {

			@Override
			public void onSuccess(ArrayList<IShop> result) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});


	}

	@Override
	public void onSearch(String searchtext) {
		ListShopsActivity.logger.log("search for " + searchtext);
		IShop shop = new Shop(searchtext, "","","",Country.at, 0.1, 0.1);
		this.shopServiceAsync.getShops(shop , new AsyncCallback<ArrayList<IShop>>() {

			@Override
			public void onSuccess(ArrayList<IShop> result) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	public void onAddShop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEditShop(int index) {
		// TODO Auto-generated method stub

	}
}
