package org.tagaprice.client.features.receiptmanagement.createReceipt;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.generics.events.AddressChangedEvent;
import org.tagaprice.client.generics.events.AddressChangedEventHandler;
import org.tagaprice.client.generics.events.InfoBoxDestroyEvent;
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
import org.tagaprice.shared.exceptions.UserNotLoggedInException;
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

		//Check if this is a draft or existing receipt and save before change view
		if((_place.getId()!=null && _place.getId().equals("draft")) || _place.getId()==null){
			//Get data from View
			_receipt.setTitle(_createReceiptView.getTitle());
			_receipt.setDate(_createReceiptView.getDate());
			_receipt.setShop(_createReceiptView.getShop());
			_receipt.setReceiptEntries(_createReceiptView.getReceiptEntries());
			_clientFactory.getAccountPersistor().setReceiptDraft(_receipt);
		}else{
			onSaveEvent();
		}


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

		//infox
		//destroy all
		_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(CreateReceiptActivity.class));


		final InfoBoxShowEvent trySaving = new InfoBoxShowEvent(CreateReceiptActivity.class, "saving...", INFOTYPE.INFO,0);
		_clientFactory.getEventBus().fireEvent(trySaving);

		_clientFactory.getReceiptService().saveReceipt(_clientFactory.getAccountPersistor().getSessionId(), _receipt, new AsyncCallback<Receipt>() {

			@Override
			public void onFailure(Throwable caught) {
				_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(trySaving));
				try{
					throw caught;
				}catch (UserNotLoggedInException e){
					Log.warn(e.getMessage());
					_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(CreateReceiptActivity.class, "Please login or create new user to save.", INFOTYPE.ERROR));
				}catch (Throwable e){
					Log.error(e.getMessage());
				}
			}

			@Override
			public void onSuccess(Receipt response) {
				_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(trySaving));
				_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(CreateReceiptActivity.class, "Receipt saved successful", INFOTYPE.SUCCESS));

				Log.debug("Receipt saved: "+_receipt);
				updateView(response);

				//delete draft
				_clientFactory.getAccountPersistor().setReceiptDraft(null);
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
					public void onFailure(Throwable caught) {
						try{
							throw caught;
						}catch (DaoException e){
							Log.warn(e.getMessage());
						}catch (Throwable e){
							Log.error(e.getMessage());
						}
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
					public void onFailure(Throwable caught) {
						try{
							throw caught;
						}catch (DaoException e){
							Log.warn(e.getMessage());
						}catch (Throwable e){
							Log.error(e.getMessage());
						}
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

		if((_place.getId() == null || (_place.getId()!=null && _place.getId().equals("draft")))  && _clientFactory.getAccountPersistor().getReceiptDraft()!=null){
			Log.debug("Create start with draft");
			_receipt=_clientFactory.getAccountPersistor().getReceiptDraft();
			updateView(_receipt);
			panel.setWidget(_createReceiptView);

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


			if(_clientFactory.getAccountPersistor().getAddress()==null){
				_clientFactory.getEventBus().fireEvent(new WaitForAddressEvent());
			}else{
				_createReceiptView.setAddress(_clientFactory.getAccountPersistor().getAddress());
			}

		}else if (_place.getId() == null && _clientFactory.getAccountPersistor().getReceiptDraft()==null) {
			Log.debug("Create new Receipt");
			_receipt.setDate(new Date());

			//create Draft
			_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(CreateReceiptActivity.class, "Draft created.", INFOTYPE.INFO));

			_clientFactory.getAccountPersistor().setReceiptDraft(_receipt);

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
