package org.tagaprice.client.features.receiptmanagement.createReceipt;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.generics.events.AddressChangedEvent;
import org.tagaprice.client.generics.events.AddressChangedEventHandler;
import org.tagaprice.client.generics.events.InfoBoxShowEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent.INFOTYPE;
import org.tagaprice.client.generics.events.WaitForAddressEvent;
import org.tagaprice.shared.entities.Quantity;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.receiptManagement.Currency;
import org.tagaprice.shared.entities.receiptManagement.Price;
import org.tagaprice.shared.entities.receiptManagement.Receipt;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.exceptions.dao.DaoException;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CreateReceiptActivity implements ICreateReceiptView.Presenter, Activity {

	private CreateReceiptPlace _place;
	private ClientFactory _clientFactory;
	private Receipt _receipt;
	private ICreateReceiptView _createReceiptView;

	public CreateReceiptActivity(CreateReceiptPlace place, ClientFactory clientFactory) {
		Log.debug("CreateProductActivity created");
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
		Log.debug("Try Save Receipt");

		//Get data from View

		_receipt.setTitle(_createReceiptView.getTitle());
		_receipt.setDate(_createReceiptView.getDate());
		_receipt.setShop(_createReceiptView.getShop());
		_receipt.setReceiptEntries(_createReceiptView.getReceiptEntries());



		_clientFactory.getReceiptService().saveReceipt(_clientFactory.getAccountPersistor().getSessionId(), _receipt, new AsyncCallback<Receipt>() {

			@Override
			public void onFailure(Throwable e) {
				_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(CreateReceiptActivity.class, "Save error: "+e, INFOTYPE.ERROR,0));

				Log.error("ERROR at saving a Receipt: "+e);
			}

			@Override
			public void onSuccess(Receipt response) {
				Log.debug("Receipt saved: "+_receipt);
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
		Log.debug("Start productSearch: "+productSearch);

		_clientFactory.getSearchService().searchProduct(
				productSearch,
				_createReceiptView.getShop(),
				new AsyncCallback<List<Product>>() {

					@Override
					public void onFailure(Throwable e) {
						Log.error("productSearch ERROR: "+e);
					}

					@Override
					public void onSuccess(List<Product> response) {
						_createReceiptView.setProductSearchResults(response);
					}
				});
	}

	@Override
	public void shopSearchStringHasChanged(String shopSearch) {
		Log.debug("Start shopSearch: "+shopSearch);

		_clientFactory.getSearchService().searchShop(
				shopSearch,
				_createReceiptView.getBoundingBox(),
				new AsyncCallback<List<Shop>>() {

					@Override
					public void onFailure(Throwable e) {
						Log.error("shopSearch ERROR: "+e);
					}

					@Override
					public void onSuccess(List<Shop> response) {
						Log.debug("ShopSearch successfull: count: "+response.size());
						_createReceiptView.setShopSearchResults(response);
					}
				});

	}

	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) {
		_receipt=new Receipt();
		Log.debug("activity startet");
		_createReceiptView = _clientFactory.getCreateReceiptView();
		_createReceiptView.setPresenter(this);



		if (_place.getId() == null) {
			Log.debug("Create new Receipt");
			_receipt.setDate(new Date());
			updateView(_receipt);
			panel.setWidget(_createReceiptView);


			if(_clientFactory.getAccountPersistor().getAddress()==null){
				_clientFactory.getEventBus().fireEvent(new WaitForAddressEvent());
			}else{
				_createReceiptView.setAddress(_clientFactory.getAccountPersistor().getAddress());
			}


		} else {
			Log.debug("Get Receipt: id= "+_place.getId());

			_clientFactory.getReceiptService().getReceipt(_place.getId(), new AsyncCallback<Receipt>() {

				@Override
				public void onFailure(Throwable e) {
					Log.error("ERROR AT Get Receipt: id= "+_place.getId()+"e:"+ e);

				}

				@Override
				public void onSuccess(Receipt response) {

					//Add Shop or Product
					if(_place.getAddType()!=null && _place.getAddType().equals("shop") && _place.getAddId()!=null){
						//get from database
						_clientFactory.getShopService().getShop(_place.getAddId(), new AsyncCallback<Shop>() {

							@Override
							public void onSuccess(Shop result) {
								_receipt.setShop(result);
								updateView(_receipt);
							}

							@Override
							public void onFailure(Throwable caught) {
								try{
									throw caught;
								}catch (DaoException e){
									Log.error("DaoException at getShop: "+caught.getMessage());
								}catch (Throwable e){
									Log.error("Unexpected exception: "+caught.getMessage());
									_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(CreateReceiptActivity.class, "Unexpected exception: "+caught.getMessage(), INFOTYPE.ERROR,0));
								}

							}
						});

					}else if(_place.getAddType()!=null && _place.getAddType().equals("product") && _place.getAddId()!=null){
						_clientFactory.getProductService().getProduct(_place.getAddId(), new AsyncCallback<Product>() {

							@Override
							public void onSuccess(Product result) {
								Log.debug("Get Product sucessfull id: "+result.getId());
								Package np = new Package(new Quantity(new BigDecimal("0.0"), result.getUnit()));
								np.setProduct(result);
								_receipt.addReceiptEntriy(new ReceiptEntry(new Price(new BigDecimal("0"), Currency.dkk), np));
								updateView(_receipt);
							}

							@Override
							public void onFailure(Throwable caught) {
								try{
									throw caught;
								}catch (DaoException e){
									Log.error("DaoException at getProduct: "+caught.getMessage());
								}catch (Throwable e){
									Log.error("Unexpected exception: "+caught.getMessage());
									_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(CreateReceiptActivity.class, "Unexpected exception: "+caught.getMessage(), INFOTYPE.ERROR,0));
								}


							}
						});
					}



					Log.debug("Result: "+response);
					updateView(response);
					panel.setWidget(_createReceiptView);
				}
			});
		}

		_clientFactory.getEventBus().addHandler(AddressChangedEvent.TYPE, new AddressChangedEventHandler() {

			@Override
			public void onAddressChanged(AddressChangedEvent event) {
				_createReceiptView.setAddress(_clientFactory.getAccountPersistor().getAddress());
				//_createReceiptView.setAddress(event.getAddress());
			}

		});



	}

	private void updateView(Receipt receipt){
		_receipt=receipt;

		_createReceiptView.setTitle(_receipt.getTitle());
		_createReceiptView.setDate(_receipt.getDate());
		_createReceiptView.setShop(_receipt.getShop());
		_createReceiptView.setReceiptEntries(_receipt.getReceiptEntries());
	}

	@Override
	public String getId() {
		return _receipt.getId();
	}

}
