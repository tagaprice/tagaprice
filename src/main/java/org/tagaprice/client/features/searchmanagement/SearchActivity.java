package org.tagaprice.client.features.searchmanagement;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.features.searchmanagement.ISearchView.Presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Label;

public class SearchActivity extends AbstractActivity implements Presenter {

	private SearchPlace _place;
	private ClientFactory _clientFactory;
	private ISearchView _searchView;
	
	public SearchActivity(SearchPlace place, ClientFactory clientFactory) {
		
		_place=place;
		_clientFactory=clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		Log.debug("Activity starts...");
		
		
		//_searchView.setPresenter(this);
		
		panel.setWidget(new Label("hier sollte suche view sein"));
		
	}

	@Override
	public void goTo(Place place) {
		_clientFactory.getPlaceController().goTo(place);
	}
	

}
