package org.tagaprice.client.features.startmanagement;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.features.startmanagement.IStartView.Presenter;
import org.tagaprice.client.generics.events.DisplayLoginEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent;
import org.tagaprice.client.generics.events.DisplayLoginEvent.LoginType;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

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
		Window.setTitle("TagAPrice - the consumer-created location-aware price comparison platform");
		
		
		_startView = _clientFactory.getStartView();
		_startView.setPresenter(this);

		
		

		
		Log.debug("redir: "+_place.getRedirect());
		if(_place.getRedirect()!=null && _place.getRedirect().equals("true")){
			eventBus.fireEvent(new DisplayLoginEvent(LoginType.login));
		}else if(_place.getInviteKey()!=null){
			eventBus.fireEvent(new DisplayLoginEvent(LoginType.register, _place.getInviteKey()));
			_startView.setInviteKey(_place.getInviteKey());
		}
		
		panel.setWidget(_startView);
	}

	@Override
	public void goTo(Place place) {
		_clientFactory.getPlaceController().goTo(place);
	}

	@Override
	public void onInvieteMe() {
		_clientFactory.getEventBus().fireEvent(new DisplayLoginEvent(LoginType.invite));		
	}

	@Override
	public void onRegisterWithKey() {
		if(!_startView.getInviteKey().equals("your invite key"))
			_clientFactory.getEventBus().fireEvent(new DisplayLoginEvent(LoginType.register, _startView.getInviteKey()));				
	}



}
