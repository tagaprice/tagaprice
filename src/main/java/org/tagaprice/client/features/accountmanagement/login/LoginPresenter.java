package org.tagaprice.client.features.accountmanagement.login;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.generics.events.InfoBoxDestroyEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent.INFOTYPE;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;

public class LoginPresenter implements ILoginView.Presenter, ILogoutView.Presenter{

	private ILoginView loginView;
	private ClientFactory _clientFactory;
	private SimplePanel _view = new SimplePanel();
	private Timer t;

	public LoginPresenter(ClientFactory clientFactory) {
		Log.debug("LoginPresenter created");
		_clientFactory = clientFactory;


		Log.debug("init");

		loginView = _clientFactory.getLoginView();
		loginView.setPresenter(this);
		loginView.showSignInUp(true);
		_view.setWidget(loginView);
		
	}

	public IsWidget getView(){
		return _view;
	}



	@Override
	public void onLogOutEvent() {
		_clientFactory.getAccountPersistor().logout();
		loginView.showSignInUp(true);
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
				_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(LoginPresenter.class));
				_clientFactory.getAccountPersistor().login(loginView.getEmail(), loginView.getPassword());

			}
		}
	}
	
	@Override
	public void onRegisterButtonEvent() {
		_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(LoginPresenter.class));

		Log.debug("Register Button Pressed");
		Log.debug("Email: "+loginView.getEmail());
		Log.debug("PW: "+loginView.getPassword());

		if(loginView.getEmail().isEmpty())
			_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(LoginPresenter.class, "Email is empty", INFOTYPE.ERROR,0));

		if(loginView.getPassword().isEmpty())
			_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(LoginPresenter.class, "Password is empty", INFOTYPE.ERROR,0));

		if(!loginView.getPassword().isEmpty() && loginView.getPassword().length()<6)
			_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(LoginPresenter.class, "Password must have more than 6 characters", INFOTYPE.ERROR,0));



		if(
				!loginView.getEmail().isEmpty() &&
				loginView.getPassword().length()>=6){
			final InfoBoxShowEvent registerShow = new InfoBoxShowEvent(LoginPresenter.class, "Try to register...", INFOTYPE.INFO);
			_clientFactory.getEventBus().fireEvent(registerShow);

			_clientFactory.getLoginService().isEmailAvailable(loginView.getEmail(), new AsyncCallback<Boolean>() {

				@Override
				public void onSuccess(Boolean response) {
					if(response==true){

						_clientFactory.getLoginService().registerUser(
								loginView.getEmail(),
								loginView.getPassword(),
								true,
								new AsyncCallback<Boolean>() {

									@Override
									public void onSuccess(Boolean isok) {
										Log.debug("registration successful");
										if(isok){
											_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(registerShow));
											_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(LoginPresenter.class, "Juhu. You are registered. Waiting for confirming", INFOTYPE.INFO));
											//goTo(new StartPlace());
											
											 t = new Timer() {
												
												@Override
												public void run() {
													_clientFactory.getLoginService().isEmailConfirmed(loginView.getEmail(), new AsyncCallback<Boolean>() {
														
														@Override
														public void onSuccess(Boolean response) {
															if(response){
																t.cancel();
																_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(LoginPresenter.class, "You are registered and confirmed. Please LogIn", INFOTYPE.SUCCESS,0));
																//onLoginEvent();
																loginView.showSignInUp(true);
															}
														}
														
														@Override
														public void onFailure(Throwable e) {
															Log.error(e.getMessage());		
														}
													});
												}
											};
											
											t.scheduleRepeating(2000);
											
											
											
											loginView.showWaitForConfirmation();

										}else{
											Log.error("Unexpected registration problem");
											_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(LoginPresenter.class, "Unexpacted problem", INFOTYPE.ERROR,0));

										}

									}

									@Override
									public void onFailure(Throwable e) {
										Log.error(e.getMessage());

									}
								});
					}else{
						_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(registerShow));

						Log.debug("Email not available");
						_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(LoginPresenter.class, "Email not available ", INFOTYPE.ERROR,0));
					}

				}

				@Override
				public void onFailure(Throwable e) {
					Log.error(e.getMessage());
				}
			});
		}

	}
	
	

}
