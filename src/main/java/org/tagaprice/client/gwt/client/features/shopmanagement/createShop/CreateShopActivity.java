package org.tagaprice.client.gwt.client.features.shopmanagement.createShop;

import org.tagaprice.client.gwt.client.ClientFactory;
import org.tagaprice.client.gwt.shared.entities.RevisionId;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.*;
import org.tagaprice.client.gwt.shared.logging.LoggerFactory;
import org.tagaprice.client.gwt.shared.logging.MyLogger;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CreateShopActivity implements ICreateShopView.Presenter, Activity {
	private static final MyLogger _logger = LoggerFactory.getLogger(CreateShopActivity.class);

	private IShop _shop;
	private ICreateShopView _createShopView;
	private CreateShopPlace _place;
	private ClientFactory _clientFactory;

	public CreateShopActivity(CreateShopPlace place, ClientFactory clientFactory) {
		CreateShopActivity._logger.log("CreateProductActivity created");
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
		CreateShopActivity._logger.log("activity startet");

		_createShopView = _clientFactory.getCreateShopView();
		_createShopView.setPresenter(this);

		if (_place.getRevisionId() != null && _place.getRevisionId().getId() != 0L) {
			// Existing product... trying to load
			_clientFactory.getShopService().getShop(new RevisionId(_place.getRevisionId().getId()),
					new AsyncCallback<IShop>() {

				@Override
				public void onSuccess(IShop result) {
					CreateShopActivity._logger.log("got shop: " + result);
					setShop(result);

				}

				@Override
				public void onFailure(Throwable caught) {
					CreateShopActivity._logger.log("got exception");
					CreateShopActivity._logger.log(caught.getMessage());

				}
			});

		} else {
			// new product... reseting view
		}

		panel.setWidget(_createShopView);


	}

	@Override
	public void goTo(Place place) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSaveEvent() {
		_clientFactory.getShopService().save(getShop(), new AsyncCallback<IShop>() {

			@Override
			public void onSuccess(IShop result) {
				CreateShopActivity._logger.log("got updated shop: " + result);
				setShop(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				CreateShopActivity._logger.log("got exception");
				CreateShopActivity._logger.log(caught.getMessage());
			}
		});
	}

	private void setShop(IShop shop) {
		_shop = shop;
		_createShopView.setRevisionId(shop.getRevisionId());
		_createShopView.setShopTitle(shop.getTitle());
		_createShopView.setCountry(shop.getCountry());
		_createShopView.setCity(shop.getCity());
		_createShopView.setZip(shop.getZip());
		_createShopView.setStreet(shop.getStreet());
		_createShopView.setLatLng(LatLng.newInstance(shop.getLat(), shop.getLng()));
	}

	private IShop getShop() {
		IShop shop;
		if (_shop != null) {
			shop = _shop;
		} else {
			shop = new Shop();
		}
		shop.setTitle(_createShopView.getShopTitle());
		shop.setCountry(_createShopView.getCountry());
		shop.setCity(_createShopView.getCity());
		shop.setZip(_createShopView.getZip());
		shop.setStreet(_createShopView.getStreet());
		shop.setLat((_createShopView.getLatLng().getLatitude()));
		shop.setLng((_createShopView.getLatLng().getLongitude()));
		return shop;
	}

}
