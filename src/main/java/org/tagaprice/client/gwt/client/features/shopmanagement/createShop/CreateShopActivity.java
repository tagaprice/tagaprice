package org.tagaprice.client.gwt.client.features.shopmanagement.createShop;

import org.tagaprice.client.gwt.client.ClientFactory;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Country;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.Shop;
import org.tagaprice.client.gwt.shared.logging.LoggerFactory;
import org.tagaprice.client.gwt.shared.logging.MyLogger;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CreateShopActivity implements ICreateShopView.Presenter, Activity {
	private static final MyLogger _logger = LoggerFactory.getLogger(CreateShopActivity.class);

	private IShop _shopData;
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


		//Add test data to view
		_shopData = new Shop("shopt", "Flossgasse1a", "1020", "Wien", Country.at, 15.00155, 42.1515);
		_createShopView.setTitle(_shopData.getTitle());
		_createShopView.setStreet(_shopData.getStreet());
		_createShopView.setZip(_shopData.getZip());
		_createShopView.setCity(_shopData.getCity());
		_createShopView.setCountry(_shopData.getCountry());
		_createShopView.setLat(_shopData.getLat());
		_createShopView.setLng(_shopData.getLng());

		panel.setWidget(_createShopView);


	}

	@Override
	public void goTo(Place place) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSaveEvent(ClickEvent event) {


		if(_shopData==null){

			_shopData = new Shop(
					_createShopView.getTitle(),
					_createShopView.getStreet(),
					_createShopView.getZip(),
					_createShopView.getCity(),
					_createShopView.getCountry(),
					_createShopView.getLat(),
					_createShopView.getLat());

		}else{
			_shopData.setTitle(_createShopView.getTitle());
			_shopData.setStreet(_createShopView.getStreet());
			_shopData.setZip(_createShopView.getZip());
			_shopData.setCity(_createShopView.getCity());
			_shopData.setCountry(_createShopView.getCountry());
			_shopData.setLat(_createShopView.getLat());
			_shopData.setLng(_createShopView.getLng());
		}

		System.out.println("save Shop send RPC: "+_shopData);

	}

}
