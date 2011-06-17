package org.tagaprice.client.features.accountmanagement.login;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.generics.facebook.FBCore;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

@Deprecated
public class Login3Activity implements ILoginView.Presenter, ILogoutView.Presenter, Activity {

	private ILoginView loginView;
	private ILogoutView logoutView;
	private Login3Place _place;
	private ClientFactory _clientFactory;
	private FBCore _fbCore = new FBCore();

	public Login3Activity(Login3Place place, ClientFactory clientFactory) {
		Log.debug("LoginActivity created");

		_place = place;
		_clientFactory = clientFactory;
		//_fbCore.init(Config.CONFIG.facebookAppId(), true, true, true);


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


		if (!_clientFactory.getAccountPersistor().isLoggedIn()) {
			loginView = _clientFactory.getLoginView();
			loginView.setPresenter(this);

			panel.setWidget(loginView);
		} else {
			logoutView = _clientFactory.getLogoutView();
			logoutView.setPresenter(this);
			panel.setWidget(logoutView);
		}

	}

	@Override
	public void goTo(Place place) {
		this._clientFactory.getPlaceController().goTo(place);
	}

	@Override
	public void onLoginEvent() {
		Log.debug("Login Button clicked");
		if (loginView != null) {
			//loginView.getEmail();
			//loginView.getPassword();

			if (!loginView.getEmail().isEmpty() && !loginView.getPassword().isEmpty()) {

				_clientFactory.getAccountPersistor().login(loginView.getEmail(), loginView.getPassword());

			}
		}
	}

	@Override
	public void onLogOutEvent() {
		_clientFactory.getAccountPersistor().logout();
	}

}
