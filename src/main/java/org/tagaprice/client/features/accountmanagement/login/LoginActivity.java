package org.tagaprice.client.features.accountmanagement.login;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.Config;
import org.tagaprice.client.generics.events.InfoBoxShowEvent;
import org.tagaprice.client.generics.events.LoginChangeEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent.INFOTYPE;
import org.tagaprice.client.generics.facebook.FBCore;
import org.tagaprice.client.generics.facebook.FBEvent;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;
import org.tagaprice.shared.exceptions.WrongEmailOrPasswordException;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.JavaScriptObject;
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
	private FBCore _fbCore = new FBCore();
	private FBEvent _fbEvent = new FBEvent();

	public LoginActivity(LoginPlace place, ClientFactory clientFactory) {
		LoginActivity._logger.log("LoginActivity created");

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
		LoginActivity._logger.log("activity startet");


		//Start Facebook
		_fbCore.init(Config.CONFIG.facebookAppId(), true, true, true);


		//Wait for login
		_fbEvent.subscribe("auth.sessionChange", new AsyncCallback<JavaScriptObject>() {

			@Override
			public void onSuccess(JavaScriptObject result) {
				LoginActivity._logger.log("Facebook loginstatus has changed");
				//myHellow(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				LoginActivity._logger.log("Facebook login Problem");
			}
		});

		//Check current status via async
		_fbCore.getLoginStatus(new AsyncCallback<JavaScriptObject>() {

			@Override
			public void onSuccess(JavaScriptObject result) {
				LoginActivity._logger.log("Findout the facebook login status");
				//myHellow(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				LoginActivity._logger.log("Facebook login Problem");
			}
		});

		if (Cookies.getCookie("TAP_SID") == null || Cookies.getCookie("TAP_SID").isEmpty()) {
			System.out.println("showLogIN");
			loginView = _clientFactory.getLoginView();
			loginView.reset();
			loginView.setPresenter(this);

			panel.setWidget(loginView);
		} else {
			System.out.println("showLogou");
			logoutView = _clientFactory.getLogoutView();
			logoutView.reset();
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
		if (loginView != null) {
			loginView.getEmail();
			loginView.getPassword();

			if (!loginView.getEmail().isEmpty() && !loginView.getPassword().isEmpty()) {


				_clientFactory.getLoginService().setLogin(loginView.getEmail(), loginView.getPassword(),
						new AsyncCallback<String>() {

					@Override
					public void onSuccess(String sessionId) {
						LoginActivity._logger.log("Login OK. SessionId: " + sessionId);
						Cookies.setCookie("TAP_SID", sessionId);

						//Send User login event
						_clientFactory.getEventBus().fireEvent(new LoginChangeEvent(true));

						goTo(new LoginPlace(sessionId));
					}

					@Override
					public void onFailure(Throwable caught) {

						try {
							throw caught;
						} catch (WrongEmailOrPasswordException e) {
							LoginActivity._logger.log("Login problem: " + e);
							_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(LoginActivity.class, "Login problem: " + e, INFOTYPE.INFO));
						} catch (Throwable e) {
							LoginActivity._logger.log("Unexpected error: " + e);
						}



					}
				});
			}
		}
	}

	@Override
	public void onLogOutEvent() {
		LoginActivity._logger.log("LogOut Button clicked");
		Cookies.removeCookie("TAP_SID");
		_clientFactory.getEventBus().fireEvent(new LoginChangeEvent(false));
		goTo(new LoginPlace());



		_clientFactory.getLoginService().setLogout(new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void value) {
				LoginActivity._logger.log("Logout was ok: " + value);
				//Send User login event

			}

			@Override
			public void onFailure(Throwable caught) {
				try {
					throw caught;
				} catch (UserNotLoggedInException e) {
					LoginActivity._logger.log("Login problem: " + e);
				} catch (Throwable e) {
					LoginActivity._logger.log("Unexpected error: " + e);
				}
			}
		});



	}

}
