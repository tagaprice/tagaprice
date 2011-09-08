package org.tagaprice.client.features.categorymanagement.shop;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.features.categorymanagement.ICategoryView.Presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ShopCategoryActivity extends AbstractActivity implements Presenter {

	
	private ClientFactory _clientFactory;
	private ShopCategoryPlace _place;
	
	public ShopCategoryActivity(ShopCategoryPlace place, ClientFactory clientFactory) {
		_place=place;
		_clientFactory=clientFactory;
	}
	
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		Log.debug("Activity starts...");
		
		
		panel.setWidget(_clientFactory.getShopCategoryView());
		
	}

	@Override
	public void goTo(Place place) {
		// TODO Auto-generated method stub
		
	}

}
