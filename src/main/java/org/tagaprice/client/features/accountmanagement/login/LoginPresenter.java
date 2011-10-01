package org.tagaprice.client.features.accountmanagement.login;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.generics.events.InfoBoxDestroyEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent.INFOTYPE;
import org.tagaprice.shared.exceptions.InvitationKeyUsedOrInvalidException;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;

public class LoginPresenter implements ILoginView.Presenter{

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
		loginView.setLoginView();
		_view.setWidget(loginView);
		
	}

	public IsWidget getView(){
		return _view;
	}



	@Override
	public void onLogOutEvent() {
		_clientFactory.getAccountPersistor().logout();
		loginView.setLoginView();
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

		if(loginView.getDisplayName().trim().isEmpty())
			_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(LoginPresenter.class, "Displayname must not be empty", INFOTYPE.ERROR,0));

		if(loginView.getInvitationKey().trim().isEmpty())
			_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(LoginPresenter.class, "Invite key must not be empty", INFOTYPE.ERROR,0));


		if(
				!loginView.getEmail().isEmpty() &&
				loginView.getPassword().length()>=6 &&
				!loginView.getDisplayName().trim().isEmpty()){
			final InfoBoxShowEvent registerShow = new InfoBoxShowEvent(LoginPresenter.class, "Try to register...", INFOTYPE.INFO);
			_clientFactory.getEventBus().fireEvent(registerShow);

			_clientFactory.getLoginService().isEmailAvailable(loginView.getEmail(), new AsyncCallback<Boolean>() {

				@Override
				public void onSuccess(Boolean response) {
					if(response==true){
						
						_clientFactory.getLoginService().registerUser(
								loginView.getDisplayName(),
								loginView.getEmail(),
								loginView.getPassword(),
								loginView.getInvitationKey(),
								new AsyncCallback<Boolean>() {

									@Override
									public void onSuccess(Boolean isok) {
										Log.debug("registration successful");
										if(isok){
											_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(registerShow));
											_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(LoginPresenter.class, "Juhu. You are registered. Waiting for confirming", INFOTYPE.INFO));
											//goTo(new StartPlace());
											loginView.setConfirmationSentView();
											
											
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
																loginView.setLoginView();
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
											
											
											
											

										}else{
											Log.error("Unexpected registration problem");
											_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(LoginPresenter.class, "Unexpacted problem", INFOTYPE.ERROR,0));

										}

									}

									@Override
									public void onFailure(Throwable caught) {
										try {
											throw caught;
										} catch (InvitationKeyUsedOrInvalidException e) {
											_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(LoginPresenter.class, "Invitation key already in use or not valid.", INFOTYPE.ERROR,0));
											Log.warn("Login problem: " + e);
										} catch (Throwable e) {
											Log.error("Unexpected error: " + e);
										}

									}
								});
					}else{
						_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(registerShow));

						Log.debug("Email not available");
						_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(LoginPresenter.class, "Email not available ", INFOTYPE.ERROR,0));
					}

				}

				@Override
				public void onFailure(Throwable caught) {
					try {
						throw caught;
					} catch (InvitationKeyUsedOrInvalidException e) {
						_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(LoginPresenter.class, "Invitation key already in use or not valid.", INFOTYPE.ERROR,0));
						Log.warn("Login problem: " + e);
					} catch (Throwable e) {
						Log.error("Unexpected error: " + e);
					}
				}
			});
		}

	}

	@Override
	public void onInviteEvent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForgotPasswordEvent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRegisterView(String key) {
		loginView.setRegisterView(key);		
	}

	@Override
	public void setLoginView() {
		loginView.setLoginView();		
	}

	@Override
	public void setInviteMeView() {
		loginView.setInviteMeView();
	}

	@Override
	public void setForgotPasswortView() {
		loginView.setForgotPasswortView();
	}

	@Override
	public void setConfirmationSentView() {
		loginView.setConfirmationSentView();
	}
	
	

}
