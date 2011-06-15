package org.tagaprice.client.features.accountmanagement.login;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.generics.facebook.FBCore;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;

public class LoginPresenter implements ILoginView.Presenter, ILogoutView.Presenter{

	private ILoginView loginView;
	private ILogoutView logoutView;
	private ClientFactory _clientFactory;
	private FBCore _fbCore = new FBCore();
	private SimplePanel _view = new SimplePanel();

	public LoginPresenter(ClientFactory clientFactory) {
		Log.debug("LoginPresenter created");
		_clientFactory = clientFactory;


		Log.debug("init");

		if (!_clientFactory.getAccountPersistor().isLoggedIn()) {
			loginView = _clientFactory.getLoginView();
			loginView.setPresenter(this);
			_view.setWidget(loginView);
		} else {
			logoutView = _clientFactory.getLogoutView();
			logoutView.setPresenter(this);
			_view.setWidget(logoutView);
		}
	}

	public IsWidget getView(){
		return _view;
	}



	@Override
	public void onLogOutEvent() {
		_clientFactory.getAccountPersistor().logout();
	}

	@Override
	public void goTo(Place place) {
		_clientFactory.getPlaceController().goTo(place);
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

}
