package org.tagaprice.client.gwt.client.features.productmanagement.createProduct;

import java.util.ArrayList;

import org.tagaprice.client.gwt.client.ClientFactory;
import org.tagaprice.client.gwt.shared.entities.RevisionId;
import org.tagaprice.client.gwt.shared.entities.dump.*;
import org.tagaprice.client.gwt.shared.entities.productmanagement.*;
import org.tagaprice.client.gwt.shared.logging.LoggerFactory;
import org.tagaprice.client.gwt.shared.logging.MyLogger;
import org.tagaprice.core.api.UserNotLoggedInException;
import org.tagaprice.core.entities.Unit;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.NotificationMole;
import com.google.gwt.user.client.ui.PopupPanel;

public class CreateProductActivity implements ICreateProductView.Presenter, Activity {
	private static final MyLogger _logger = LoggerFactory.getLogger(CreateProductActivity.class);

	private CreateProductPlace _place;
	private ClientFactory _clientFactory;
	private IProduct _product;

	public CreateProductActivity(CreateProductPlace place, ClientFactory clientFactory) {
		CreateProductActivity._logger.log("CreateProductActivity created");
		_place = place;
		_clientFactory = clientFactory;
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
		// TODO Auto-generated method stub

	}

	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) {
		_product = null;
		CreateProductActivity._logger.log("activity startet");

		ICreateProductView createProductView = _clientFactory.getCreateProductView();
		createProductView.setPresenter(this);

		this._clientFactory.getProductServiceDispatch().getCategories(new AsyncCallback<ArrayList<ICategory>>() {

			@Override
			public void onSuccess(ArrayList<ICategory> result) {
				int resultsize = 0;
				if(result != null) {
					resultsize = result.size();
				}
				CreateProductActivity._logger.log("Received " + resultsize + " categories");
				_clientFactory.getCreateProductView().setAvailableCategories(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				CreateProductActivity._logger.log("ERROR receiving list of categories");

			}
		});

		if (_place.getRevisionId().getId() == 0L) {
			CreateProductActivity._logger.log("Create new Product");

			updateView(new Product("", new Category("newProduct"), Unit.piece));
			panel.setWidget(createProductView);
			// panel.setWidget(new Label("Create new Product"));
		} else {
			CreateProductActivity._logger.log("Get Product: id=" + _place.getRevisionId().getId() + ", rev: "
					+ _place.getRevisionId().getRevision());
			// panel.setWidget(new
			// Label("Get Product: id="+_place.getRevisionId().getId()+", rev: "+_place.getRevisionId().getRevision()));


			CreateProductActivity._logger.log("Load Categories...");


			this._clientFactory.getProductServiceDispatch().getProduct(_place.getRevisionId(), new AsyncCallback<IProduct>() {

				@Override
				public void onSuccess(IProduct result) {
					updateView(result);
					panel.setWidget(_clientFactory.getCreateProductView());
				}

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub

				}
			});


		}

	}

	@Override
	public void goTo(Place place) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSaveEvent() {
		CreateProductActivity._logger.log("Save Product");
		IProduct product = this.getProduct();
		//	product = new Product("default Title", new Category("default Category"), new Quantity(999L, Unit.ml));
		CreateProductActivity._logger.log(product.toString());

		this._clientFactory.getProductServiceDispatch().saveProduct(product, new AsyncCallback<IProduct>() {

			@Override
			public void onSuccess(IProduct result) {
				updateView(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				try{
					throw caught;
				}catch (UserNotLoggedInException e){
					//TODO This stuff must be implementet at an global place
					final PopupPanel pop = new PopupPanel();
					final NotificationMole mole = new NotificationMole();
					pop.show();
					pop.setPopupPosition(Window.getClientWidth() / 2, Window.getClientHeight() / 2);
					pop.add(mole);
					mole.setMessage("user not logged in "+e.getMessage());
					mole.setAnimationDuration(500);
					mole.show();

					Timer t = new Timer() {
						@Override
						public void run() {
							mole.hide();
							Timer t2 = new Timer() {

								@Override
								public void run() {
									pop.hide();
								}
							};
							t2.schedule(500);
						}
					};

					t.schedule(2000);
					CreateProductActivity._logger.log(e.getMessage());
				}catch (Throwable e){
					// last resort -- a very unexpected exception
					CreateProductActivity._logger.log(e.getMessage());
					e.printStackTrace();
				}

				//updateView(new Product("Fehler beim Speichern", new Category("bla"), new Quantity()));
				CreateProductActivity._logger.log(caught.getLocalizedMessage());

			}
		});

	}

	@Override
	public void onTitleSelectedEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUnitSelectedEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCategorySelectedEvent() {
		// TODO Auto-generated method stub

	}

	private void updateView(IProduct product) {
		_product = product;
		if(product == null)
			return;
		ICreateProductView view = this._clientFactory.getEditProductView();
		if(product.getRevisionId() != null) {
			view.setRevisionId(product.getRevisionId());
		} else {
			view.setRevisionId(new RevisionId());
		}
		view.setTitle(product.getTitle());
		view.setCategory(product.getCategory());
		view.setUnit(product.getUnit());

		view.setPackages(product.getPackages());
	}

	private IProduct getProduct() {
		IProduct product;
		if(_product == null) {
			product = new Product();
		} else {
			product = _product;
		}
		ICreateProductView view = this._clientFactory.getEditProductView();
		product.setTitle(view.getProductTitle());
		product.setCategory(view.getCategory());
		product.setUnit(view.getUnit());
		product.addPackages(view.getPackages());
		return product;

	}

}
