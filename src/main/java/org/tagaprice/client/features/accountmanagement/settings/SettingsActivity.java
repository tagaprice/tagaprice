package org.tagaprice.client.features.accountmanagement.settings;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.features.accountmanagement.settings.ISettingsView.Presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class SettingsActivity implements Activity, Presenter {

	private ClientFactory _clientFactory;
	private SettingsPlace _place;
	private ISettingsView _settingsView;
	
	public SettingsActivity(SettingsPlace place, ClientFactory clientFactory) {
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
		Window.setTitle("Settings");
		
		_settingsView = _clientFactory.getSettingsView();
		_settingsView.setPresenter(this);
		
		panel.setWidget(_settingsView);
		
	}

	@Override
	public void goTo(Place place) {
		_clientFactory.getPlaceController().goTo(place);		
	}

}
