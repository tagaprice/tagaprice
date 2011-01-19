package org.tagaprice.client.gwt.client.features.shopmanagement.createShop;

import org.tagaprice.client.gwt.client.ClientFactory;
import org.tagaprice.client.gwt.shared.logging.LoggerFactory;
import org.tagaprice.client.gwt.shared.logging.MyLogger;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CreateShopActivity implements ICreateShopView.Presenter, Activity {
	private static final MyLogger _logger = LoggerFactory.getLogger(CreateShopActivity.class);


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

		ICreateShopView createShopView = _clientFactory.getCreateShopView();
		createShopView.setPresenter(this);

		panel.setWidget(createShopView);

	}

	@Override
	public void goTo(Place place) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSaveEvent(ClickEvent event) {
		System.out.println("save Shop send RPC");
		// TODO Auto-generated method stub

	}

}
