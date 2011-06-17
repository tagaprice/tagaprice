package org.tagaprice.client.features.startmanagement;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.features.startmanagement.IStartView.Presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class StartActivity implements Activity, Presenter {

	private ClientFactory _clientFactory;
	private StartPlace _place;
	private IStartView _startView;

	public StartActivity(StartPlace place, ClientFactory clientFactory) {
		Log.debug("create class");
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
		Log.debug("activity startet");

		_startView = _clientFactory.getStartView();
		_startView.setPresenter(this);

		panel.setWidget(_startView);

	}

	@Override
	public void goTo(Place place) {
		_clientFactory.getPlaceController().goTo(place);
	}

}
