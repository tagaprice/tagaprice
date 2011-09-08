package org.tagaprice.client.features.categorymanagement;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.features.categorymanagement.ICategoryView.Presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Label;

public class CategoryActivity extends AbstractActivity implements Presenter {

	
	private ClientFactory _clientFactory;
	private CategoryPlace _place;
	
	public CategoryActivity(CategoryPlace place, ClientFactory clientFactory) {
		_place=place;
		_clientFactory=clientFactory;
	}
	
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		Log.debug("Activity starts...");
		
		
		panel.setWidget(new Label("category"));
		
	}

	@Override
	public void goTo(Place place) {
		// TODO Auto-generated method stub
		
	}

}
