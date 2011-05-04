package org.tagaprice.client.features.productmanagement.createProduct;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.shared.entities.productmanagement.*;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CreateProductActivity implements ICreateProductView.Presenter, Activity {
	private static final MyLogger _logger = LoggerFactory.getLogger(CreateProductActivity.class);

	private CreateProductPlace _place;
	private ClientFactory _clientFactory;
	private Product _product;
	private ICreateProductView _createProductView;

	public CreateProductActivity(CreateProductPlace place, ClientFactory clientFactory) {
		CreateProductActivity._logger.log("CreateProductActivity created");
		_place = place;
		_clientFactory = clientFactory;
	}

	@Override
	public void goTo(Place place) {
		_clientFactory.getPlaceController().goTo(place);
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
	public void onCategorySelectedEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSaveEvent() {
		CreateProductActivity._logger.log("Save Product");


		//Get data from View
		_product.setTitle(_createProductView.getProductTitle());
		_product.setCategory(_createProductView.getCategory());
		_product.setUnit(_createProductView.getUnit());
		_product.setPackages(_createProductView.getPackages());

		this._clientFactory.getProductService().saveProduct(_clientFactory.getAccountPersistor().getSessionId(), _product, new AsyncCallback<Product>() {

			@Override
			public void onFailure(Throwable caught) {
				try{
					throw caught;
				}catch (UserNotLoggedInException e){
					CreateProductActivity._logger.log(e.getMessage());
				}catch (Throwable e){
					// last resort -- a very unexpected exception
					CreateProductActivity._logger.log(e.getMessage());
					e.printStackTrace();
				}

				CreateProductActivity._logger.log(caught.getLocalizedMessage());

			}

			@Override
			public void onSuccess(Product result) {
				updateView(result);
			}
		});

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTitleSelectedEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUnitSelectedEvent() {
		CreateProductActivity._logger.log("Unit has changed");

	}

	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) {
		_product = new Product();
		CreateProductActivity._logger.log("activity startet");

		_createProductView = _clientFactory.getCreateProductView();
		_createProductView.setPresenter(this);


		if (_place.getId() == null) {
			CreateProductActivity._logger.log("Create new Product");

			updateView(_product);
			panel.setWidget(_createProductView);
			// panel.setWidget(new Label("Create new Product"));
		} else {
			CreateProductActivity._logger.log("Get Product: id=" + _place.getId() + ", rev: "
					+ _place.getRevision());
			// panel.setWidget(new
			// Label("Get Product: id="+_place.getRevisionId().getId()+", rev: "+_place.getRevisionId().getRevision()));


			CreateProductActivity._logger.log("Load Categories...");


			this._clientFactory.getProductService().getProduct(_place.getId(), _place.getRevision(), new AsyncCallback<Product>() {

				@Override
				public void onFailure(Throwable caught) {
					CreateProductActivity._logger.log("ERROR at getProduct");
				}

				@Override
				public void onSuccess(Product result) {
					updateView(result);
					panel.setWidget(_createProductView);
				}
			});


		}

	}

	private void updateView(Product product) {
		_product = product;
		ICreateProductView view = this._clientFactory.getEditProductView();
		view.setTitle(product.getTitle());
		view.setCategory(product.getCategory());
		view.setUnit(product.getUnit());

		view.setPackages(product.getPackages());
	}

}
