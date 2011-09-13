package org.tagaprice.client.features.accountmanagement.settings;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.features.accountmanagement.settings.ISettingsView.Presenter;
import org.tagaprice.client.generics.events.InfoBoxDestroyEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent.INFOTYPE;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
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

	@Override
	public void onPasswordChange() {
		_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(SettingsActivity.class));
		
		if(!_settingsView.getNewPassword().trim().equals(_settingsView.getConfirmPassword().trim())){
			_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(SettingsActivity.class, "new password and confirm password are not equal", INFOTYPE.INFO));
		}else if(_settingsView.getNewPassword().length()<6){
			_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(SettingsActivity.class, "password must be longer than 5 characters", INFOTYPE.INFO));
		}else{
			_clientFactory.getLoginService().setNewPassword(_settingsView.getNewPassword().trim(), _settingsView.getConfirmPassword().trim(), new AsyncCallback<Boolean>() {
				
				@Override
				public void onSuccess(Boolean response) {
					_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(SettingsActivity.class));
					if(response){
						_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(SettingsActivity.class, "changed password", INFOTYPE.SUCCESS));
					}else{
						_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(SettingsActivity.class, "something went wrong", INFOTYPE.ERROR));
					}
						
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onFailure(Throwable e) {
					Log.error(e.getMessage());	
					
				}
			});
		}
		
	}

}
