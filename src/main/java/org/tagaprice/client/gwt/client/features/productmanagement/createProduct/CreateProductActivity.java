package org.tagaprice.client.gwt.client.features.productmanagement.createProduct;

import java.util.ArrayList;

import org.tagaprice.client.gwt.client.ClientFactory;
import org.tagaprice.client.gwt.shared.entities.RevisionId;
import org.tagaprice.client.gwt.shared.entities.dump.*;
import org.tagaprice.client.gwt.shared.entities.productmanagement.*;
import org.tagaprice.client.gwt.shared.logging.LoggerFactory;
import org.tagaprice.client.gwt.shared.logging.MyLogger;
import org.tagaprice.core.entities.Unit;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CreateProductActivity implements ICreateProductView.Presenter, Activity {
	private static final MyLogger _logger = LoggerFactory.getLogger(CreateProductActivity.class);

	private CreateProductPlace _place;
	private ClientFactory _clientFactory;

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
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
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
			panel.setWidget(createProductView);
			updateView(new Product("", new Category("newProduct"), new Quantity(1L, Unit.piece)));
			// panel.setWidget(new Label("Create new Product"));
		} else {
			CreateProductActivity._logger.log("Get Product: id=" + _place.getRevisionId().getId() + ", rev: "
					+ _place.getRevisionId().getRevision());
			// panel.setWidget(new
			// Label("Get Product: id="+_place.getRevisionId().getId()+", rev: "+_place.getRevisionId().getRevision()));

			panel.setWidget(createProductView);
			CreateProductActivity._logger.log("Load Categories...");


			this._clientFactory.getProductServiceDispatch().getProduct(_place.getRevisionId(), new AsyncCallback<IProduct>() {

				@Override
				public void onSuccess(IProduct result) {
					updateView(result);
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
				updateView(new Product("Fehler beim Speichern", new Category("bla"), new Quantity()));
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
		view.setQuantity(product.getQuantity());
	}

	private IProduct getProduct() {
		IProduct product = new Product();
		ICreateProductView view = this._clientFactory.getEditProductView();
		product.setTitle(view.getProductTitle());
		product.setCategory(view.getCategory());
		product.setQuantity(view.getQuantity());
		return product;

	}

}
