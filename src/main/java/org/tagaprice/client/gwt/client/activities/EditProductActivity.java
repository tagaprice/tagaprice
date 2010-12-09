package org.tagaprice.client.gwt.client.activities;

import java.util.HashMap;

import org.tagaprice.client.gwt.client.ClientFactory;
import org.tagaprice.client.gwt.client.places.*;
import org.tagaprice.client.gwt.client.ui.EditProductView;
import org.tagaprice.client.gwt.shared.entities.*;
import org.tagaprice.client.gwt.shared.logging.*;


import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class EditProductActivity implements EditProductView.Presenter, Activity {
	private static final MyLogger logger = LoggerFactory
			.getLogger(EditProductActivity.class);

	private EditProductPlace place;
	private ClientFactory clientFactory;
/**
 * The EditProductActivity constructor takes the argument EditProductPlace 
 * and the argument ClientFactory
 * A new activity will be created for each EditProductPlace
 * The ClientFactory is used by the EditProductPlace to obtain a reference to the EditProductView
 * @param place
 * @param clientFactory
 */
	public EditProductActivity(EditProductPlace place,
			ClientFactory clientFactory) {
		this.place = place;
		this.clientFactory = clientFactory;
	}
/**
 * The method provides a warning to the user before stopping the EditProduct activity by closing
 * the window or navigating to another place
 */
	@Override
	public String mayStop() {
		// TODO Auto-generated method stub
		return null;
	}
/**
 * 
 */
	@Override
	public void onCancel() {
		// TODO Auto-generated method stub

	}
/**
 * 
 */
	@Override
	public void onStop() {
		// TODO Auto-generated method stub

	}
/**
 * The start method is invoked by the ProductManager and sets things in motion by updating the view
 * and starts a new Activity
 */
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		logger.log("activity startet");
		final EditProductView editProductView = this.clientFactory
				.getEditProductView();
		editProductView.setPresenter(this);
		final Product p = new ProductImpl();
		if (this.place.isNewProduct()) {
			logger.log("is new product");
			this.updateView(p);
		} else {
			logger.log("is existing product");
			this.clientFactory.getProductServiceDispatch().getProductById(
					this.place.getProductId(), new AsyncCallback<Product>() {
/**
 * If the start() method was successful then the method onSuccess is called and returns 
 * the message "successful call"
 * @param result
 */
						@Override
						public void onSuccess(Product result) {
							logger.log("succesful call");
							EditProductActivity.this.updateView(result);

					}
 /**
  * If the start() method failed then the method onFailure() is called
  */

						@Override
						public void onFailure(Throwable caught) {

							// TODO Auto-generated method stub
							logger.log("UNSUCCESFULL call");
						}
					});
		}

		panel.setWidget(editProductView.asWidget());
	}

	public void updateView(Product p) {
		EditProductView editProductView = this.clientFactory
				.getEditProductView();
		editProductView.setId(p.getId());
		editProductView.setName(p.getName());
		editProductView.setPrice(p.getPrice());
		editProductView.setDescription(p.getDescription());
		editProductView.setCategory(p.getCategory());
	}
/**
 * This method invokes the PlaceController to navigate to a new Place in the browser
 */
	@Override
	public void goTo(Place place) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSaveButtonClicked(ClickEvent event) {
		logger.log("Save Button clicked");
		EditProductView editProductView = this.clientFactory
				.getEditProductView();

		Product p = new ProductImpl(editProductView.getId(),
				editProductView.getName(), editProductView.getPrice(),
				editProductView.getDescription(), editProductView.getCategory());
		final PlaceController placeController = this.clientFactory
				.getPlaceController();
		this.clientFactory.getProductServiceDispatch().saveProduct(p,
				!this.place.isNewProduct(), new AsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
						placeController.goTo(new ProductListPlace(
								new HashMap<String, String>()));
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Fehlermeldung

					}
				});
	}

	@Override
	public void onCancelButtonClicked(ClickEvent event) {
		this.clientFactory.getPlaceController().goTo(
				new ProductListPlace(new HashMap<String, String>()));
	}
}
