package org.tagaprice.client.gwt.client.features.accountmanagement.login;

import org.tagaprice.client.gwt.client.ClientFactory;
import org.tagaprice.client.gwt.shared.logging.LoggerFactory;
import org.tagaprice.client.gwt.shared.logging.MyLogger;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class LoginActivity implements ILoginView.Presenter, Activity {
	private static final MyLogger _logger = LoggerFactory.getLogger(LoginActivity.class);

	private ILoginView loginView;
	private LoginPlace _place;
	private ClientFactory _clientFactory;

	public LoginActivity(LoginPlace place, ClientFactory clientFactory) {
		LoginActivity._logger.log("LoginActivity created");

		_place=place;
		_clientFactory=clientFactory;

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
		LoginActivity._logger.log("activity startet");

		loginView = _clientFactory.getLoginView();
		loginView.setPresenter(this);

		panel.setWidget(loginView);
	}

	@Override
	public void goTo(Place place) {
		// TODO Auto-generated method stub


	}

	@Override
	public void onLoginEvent() {
		LoginActivity._logger.log("Login Button clicked");
		if(loginView!=null){
			loginView.getEmail();
			loginView.getPassword();

			if(!loginView.getEmail().isEmpty() && !loginView.getPassword().isEmpty()){

				_clientFactory.getLoginService().setLogin(loginView.getEmail(), loginView.getPassword(), new AsyncCallback<String>() {

					@Override
					public void onSuccess(String sessionId) {
						LoginActivity._logger.log("Login OK. SessionId: "+sessionId);
					}

					@Override
					public void onFailure(Throwable e) {
						LoginActivity._logger.log("Login exception: "+e);

					}
				});
			}
		}
	}

}
