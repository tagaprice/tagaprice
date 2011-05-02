package org.tagaprice.client.features.shopmanagement.createShop;

import java.util.List;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.generics.events.AddressChangedEvent;
import org.tagaprice.client.generics.events.AddressChangedEventHandler;
import org.tagaprice.client.generics.events.InfoBoxShowEvent;
import org.tagaprice.client.generics.events.WaitForAddressEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent.INFOTYPE;
import org.tagaprice.shared.entities.shopmanagement.*;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CreateShopActivity implements ICreateShopView.Presenter, Activity {
	private static final MyLogger _logger = LoggerFactory.getLogger(CreateShopActivity.class);

	private Shop _shop;
	private ICreateShopView _createShopView;
	private CreateShopPlace _place;
	private ClientFactory _clientFactory;

	public CreateShopActivity(CreateShopPlace place, ClientFactory clientFactory) {
		CreateShopActivity._logger.log("CreateProductActivity created");
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
	public void onSaveEvent() {
		CreateShopActivity._logger.log("Save Shop");
		_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(CreateShopActivity.class, "Try to save shop", INFOTYPE.INFO));

		//Get data from View
		_shop.setTitle(_createShopView.getShopTitle());
		_shop.setAddress(_createShopView.getAddress());
		_shop.setParent(_createShopView.getBranding());

		_clientFactory.getShopService().saveShop(null, _shop, new AsyncCallback<Shop>() {

			@Override
			public void onFailure(Throwable caught) {

				try{
					throw caught;
				}catch (UserNotLoggedInException e){
					CreateShopActivity._logger.log(e.getMessage());
				}catch (Throwable e){
					// last resort -- a very unexpected exception
					CreateShopActivity._logger.log(e.getMessage());
				}

				CreateShopActivity._logger.log("exception "+caught.getMessage());
			}

			@Override
			public void onSuccess(Shop result) {
				CreateShopActivity._logger.log("got updated shop: " + result);
				updateView(result);
			}
		});
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) {
		_shop=new Shop();
		CreateShopActivity._logger.log("activity startet");
		_createShopView = _clientFactory.getCreateShopView();
		_createShopView.setPresenter(this);

		if (_place.getId() != null) {
			// Existing product... trying to load
			_clientFactory.getShopService().getShop(_place.getId(), _place.getRevision(),
					new AsyncCallback<Shop>() {

				@Override
				public void onFailure(Throwable caught) {

					CreateShopActivity._logger.log(caught.getMessage());
				}

				@Override
				public void onSuccess(Shop result) {
					CreateShopActivity._logger.log("got shop: " + result);
					updateView(result);
					panel.setWidget(_createShopView);
				}
			});


		} else {
			// new product... reseting view
			CreateShopActivity._logger.log("Create new shop");
			updateView(_shop);
			panel.setWidget(_createShopView);

			if(_clientFactory.getAccountPersistor().getAddress()==null){
				_clientFactory.getEventBus().fireEvent(new WaitForAddressEvent());
			}else{
				_createShopView.setAddress(_clientFactory.getAccountPersistor().getAddress());
			}

			_clientFactory.getEventBus().addHandler(AddressChangedEvent.TYPE, new AddressChangedEventHandler() {

				@Override
				public void onAddressChanged(AddressChangedEvent event) {
					_createShopView.setAddress(_clientFactory.getAccountPersistor().getAddress());
					//_createShopView.setAddress(event.getAddress());
				}
			});


		}

	}




	private void updateView(Shop shop){
		_shop = shop;
		_createShopView.setShopTitle(shop.getTitle());
		_createShopView.setAddress(shop.getAddress());
		_createShopView.setBranding(shop.getParent());
	}


	@Override
	public void brandingSearch(String search) {

		_clientFactory.getSearchService().searchShop(search, null, new AsyncCallback<List<Shop>>() {

			@Override
			public void onSuccess(List<Shop> results) {
				_createShopView.setBrandingSearchResults(results);
			}

			@Override
			public void onFailure(Throwable e) {
				CreateShopActivity._logger.log("ShopSerachError: "+e);
			}
		});




	}

}
