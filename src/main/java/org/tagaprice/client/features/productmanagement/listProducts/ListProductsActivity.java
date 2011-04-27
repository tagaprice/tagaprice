package org.tagaprice.client.features.productmanagement.listProducts;

import java.util.List;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.features.productmanagement.listProducts.ListProductsView.Presenter;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.logging.*;
import org.tagaprice.shared.rpc.productmanagement.IProductServiceAsync;

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
public class ListProductsActivity extends AbstractActivity implements Presenter {
	private static MyLogger logger = LoggerFactory.getLogger(ListProductsActivity.class);

	private ListProductsPlace place;
	private ClientFactory clientFactory;
	private List<Product> products;

	private final ListProductsView listProductsView;
	private IProductServiceAsync productServiceAsync;

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
	public ListProductsActivity(ListProductsPlace place, ClientFactory clientFactory) {
		// Store parameters...
		this.place = place;
		this.clientFactory = clientFactory;

		// load data to work
		listProductsView = clientFactory.getListProductsView();
		productServiceAsync = clientFactory.getProductService();
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
		this.listProductsView.setPresenter(null);

	}

	/**
	 * The start method is invoked by the ActivityManager and sets things in motion by updating
	 * the view and starts a new Activity
	 */
	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) {
		ListProductsActivity.logger.log("Activity starts...");


		listProductsView.setPresenter(this);

		this.productServiceAsync.findProducts(null, new AsyncCallback<List<Product>>() {

			@Override
			public void onSuccess(List<Product> result) {
				ListProductsActivity.logger.log("RPC request successfull: " + result.size() + " items");
				products = result;
				listProductsView.setData(result);
				panel.setWidget(listProductsView.asWidget());
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});


	}

}
