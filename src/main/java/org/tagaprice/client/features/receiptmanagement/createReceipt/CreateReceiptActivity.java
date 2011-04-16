package org.tagaprice.client.features.receiptmanagement.createReceipt;

import java.util.List;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.receiptManagement.Receipt;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CreateReceiptActivity implements ICreateReceiptView.Presenter, Activity {
	private static final MyLogger _logger = LoggerFactory.getLogger(CreateReceiptActivity.class);

	private CreateReceiptPlace _place;
	private ClientFactory _clientFactory;
	private Receipt _receipt;
	private ICreateReceiptView _createReceiptView;

	public CreateReceiptActivity(CreateReceiptPlace place, ClientFactory clientFactory) {
		CreateReceiptActivity._logger.log("CreateProductActivity created");
		_place = place;
		_clientFactory = clientFactory;
	}

	@Override
	public void goTo(Place place) {
		this._clientFactory.getPlaceController().goTo(place);
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
	public void onSaveEvent() {
		CreateReceiptActivity._logger.log("Try Save Receipt");

		//Get data from View

		_receipt.setTitle(_createReceiptView.getTitle());
		_receipt.setDate(_createReceiptView.getDate());
		_receipt.setShop(_createReceiptView.getAddress());
		_receipt.setReceiptEntries(_createReceiptView.getReceiptEntries());



		_clientFactory.getReceiptService().saveReceipt(_receipt, new AsyncCallback<Receipt>() {

			@Override
			public void onFailure(Throwable e) {
				CreateReceiptActivity._logger.log("ERROR at saving a Receipt");
			}

			@Override
			public void onSuccess(Receipt response) {
				CreateReceiptActivity._logger.log("Receipt saved: "+_receipt);
				updateView(response);
			}
		});

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void productSearchStringHasChanged(String productSearch) {
		CreateReceiptActivity._logger.log("Start productSearch: "+productSearch);

		_clientFactory.getSearchService().searchProduct(
				productSearch,
				_createReceiptView.getAddress(),
				new AsyncCallback<List<Product>>() {

					@Override
					public void onFailure(Throwable e) {
						CreateReceiptActivity._logger.log("productSearch ERROR: "+e);
					}

					@Override
					public void onSuccess(List<Product> response) {
						_createReceiptView.setProductSearchResults(response);
					}
				});
	}

	@Override
	public void shopSearchStringHasChanged(String shopSearch) {
		CreateReceiptActivity._logger.log("Start shopSearch: "+shopSearch);

		_clientFactory.getSearchService().searchShop(
				shopSearch,
				_createReceiptView.getBoundingBox(),
				new AsyncCallback<List<Shop>>() {

					@Override
					public void onFailure(Throwable e) {
						CreateReceiptActivity._logger.log("shopSearch ERROR: "+e);
					}

					@Override
					public void onSuccess(List<Shop> response) {

						_createReceiptView.setShopSearchResults(response);
					}
				});

	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		_receipt=new Receipt();
		CreateReceiptActivity._logger.log("activity startet");
		_createReceiptView = _clientFactory.getCreateReceiptView();
		_createReceiptView.setPresenter(this);



		if (_place.getId() == null) {
			CreateReceiptActivity._logger.log("Create new Receipt");

			updateView(_receipt);
		} else {
			CreateReceiptActivity._logger.log("Get Receipt: id= "+_place.getId());

			_clientFactory.getReceiptService().getReceipt(_place.getId(), new AsyncCallback<Receipt>() {

				@Override
				public void onFailure(Throwable e) {
					CreateReceiptActivity._logger.log("ERROR AT Get Receipt: id= "+_place.getId()+"e:"+ e);

				}

				@Override
				public void onSuccess(Receipt response) {
					CreateReceiptActivity._logger.log("Result: "+response);
					updateView(_receipt);
				}
			});
		}




		panel.setWidget(_createReceiptView);
	}

	private void updateView(Receipt receipt){
		_receipt=receipt;

		_createReceiptView.setTitle(_receipt.getTitle());
		_createReceiptView.setDate(_receipt.getDate());
		_createReceiptView.setAddress(_receipt.getShop());
		_createReceiptView.setReceiptEntries(_receipt.getReceiptEntries());
	}

}
