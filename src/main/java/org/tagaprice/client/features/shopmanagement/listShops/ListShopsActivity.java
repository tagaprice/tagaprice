package org.tagaprice.client.features.shopmanagement.listShops;

import java.util.ArrayList;
import java.util.List;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.shared.entities.shopmanagement.*;
import org.tagaprice.shared.rpc.shopmanagement.IShopServiceAsync;

import com.allen_sauer.gwt.log.client.Log;
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
public class ListShopsActivity extends AbstractActivity implements org.tagaprice.client.features.shopmanagement.listShops.ListShopsView.Presenter {

	private ListShopsPlace place;
	private ClientFactory clientFactory;
	private ArrayList<Shop> products;

	private IShopServiceAsync shopServiceAsync;

	private ListShopsView listShopsView;

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
		listShopsView = this.clientFactory.getListShopsView();
		shopServiceAsync = this.clientFactory.getShopService();
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

	}

	/**
	 * The start method is invoked by the ActivityManager and sets things in motion by updating
	 * the view and starts a new Activity
	 */
	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) {
		Log.debug("Activity starts...");

		listShopsView.setPresenter(this);

		this.shopServiceAsync.getShops(null, new AsyncCallback<List<Shop>>() {

			@Override
			public void onSuccess(List<Shop> result) {
				Log.debug("received results: " + result);
				if(result != null) {
					listShopsView.setData(result);
				}

				panel.setWidget(listShopsView.asWidget());


			}

			@Override
			public void onFailure(Throwable caught) {
				Log.error(caught.getLocalizedMessage());

			}
		});




	}

}
