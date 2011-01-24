package org.tagaprice.client.gwt.client.features.accountmanagement.login;

import org.tagaprice.client.gwt.client.ClientFactory;
import org.tagaprice.client.gwt.shared.logging.LoggerFactory;
import org.tagaprice.client.gwt.shared.logging.MyLogger;
import org.tagaprice.core.api.UserNotLoggedInException;
import org.tagaprice.core.api.WrongEmailOrPasswordException;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class LoginActivity implements ILoginView.Presenter, ILogoutView.Presenter, Activity {
	private static final MyLogger _logger = LoggerFactory.getLogger(LoginActivity.class);

	private ILoginView loginView;
	private ILogoutView logoutView;
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

		System.out.println("showCooky: "+Cookies.getCookie("TAP_SID"));



		if(Cookies.getCookie("TAP_SID")==null || Cookies.getCookie("TAP_SID").isEmpty()){
			System.out.println("showLogIN");
			loginView = _clientFactory.getLoginView();
			loginView.setPresenter(this);

			panel.setWidget(loginView);
		}else{
			System.out.println("showLogou");
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
		LoginActivity._logger.log("Login Button clicked");
		if(loginView!=null){
			loginView.getEmail();
			loginView.getPassword();

			if(!loginView.getEmail().isEmpty() && !loginView.getPassword().isEmpty()){

				try {
					_clientFactory.getLoginService().setLogin(loginView.getEmail(), loginView.getPassword(), new AsyncCallback<String>() {

						@Override
						public void onSuccess(String sessionId) {
							LoginActivity._logger.log("Login OK. SessionId: "+sessionId);
							Cookies.setCookie("TAP_SID", sessionId);
							goTo(new LoginPlace(sessionId));
						}

						@Override
						public void onFailure(Throwable e) {
							LoginActivity._logger.log("Login problem: "+e);

						}
					});
				} catch (WrongEmailOrPasswordException e) {
					LoginActivity._logger.log("Login exception: "+e);
				}
			}
		}
	}

	@Override
	public void onLogOutEvent() {
		LoginActivity._logger.log("LogOut Button clicked");
		Cookies.removeCookie("TAP_SID");
		goTo(new LoginPlace());


		try {
			_clientFactory.getLoginService().setLogout(new AsyncCallback<Void>() {

				@Override
				public void onSuccess(Void value) {
					LoginActivity._logger.log("Logout was ok: "+value);

				}

				@Override
				public void onFailure(Throwable e) {
					LoginActivity._logger.log("Logout problem: "+e);

				}
			});
		} catch (UserNotLoggedInException e) {
			LoginActivity._logger.log("Logout exception: "+e);
		}


	}

}
