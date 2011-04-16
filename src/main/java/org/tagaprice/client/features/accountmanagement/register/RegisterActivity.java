package org.tagaprice.client.features.accountmanagement.register;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.features.accountmanagement.register.RegisterPlace.RegisterType;
import org.tagaprice.client.generics.events.InfoBoxDestroyEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent;
import org.tagaprice.client.generics.events.LoginChangeEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent.INFOTYPE;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class RegisterActivity implements IRegisterView.Presenter, Activity {
	private static final MyLogger _logger = LoggerFactory.getLogger(RegisterActivity.class);

	private RegisterPlace _place;
	private ClientFactory _clientFactory;
	private IRegisterView _registerView;

	public RegisterActivity(RegisterPlace place, ClientFactory clientFactory) {
		RegisterActivity._logger.log("RegisterActivity created");

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
		RegisterActivity._logger.log("activity startet");

		if(_place.getRegisterType()==RegisterPlace.RegisterType.REGISTER){
			if (_registerView == null)
				_registerView = _clientFactory.getRegisterView();
			_registerView.setPresenter(this);
			panel.setWidget(_registerView);
		}else if(_place.getRegisterType()==RegisterPlace.RegisterType.THANKS){
			panel.setWidget(_clientFactory.getRegisteredView());
		}
	}

	@Override
	public void goTo(Place place) {
		this._clientFactory.getPlaceController().goTo(place);
	}

	@Override
	public void checkEmail() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRegisterButtonEvent() {
		_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(RegisterActivity.class));

		RegisterActivity._logger.log("Register Button Pressed");


		RegisterActivity._logger.log("Email: "+_registerView.getEmail());
		RegisterActivity._logger.log("PW: "+_registerView.getPassword());

		if(_registerView.getEmail().isEmpty())
			_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(RegisterActivity.class, "Email is empty", INFOTYPE.ERROR,0));

		if(_registerView.getPassword().isEmpty())
			_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(RegisterActivity.class, "Password is empty", INFOTYPE.ERROR,0));

		if(!_registerView.getAgreeTerms())
			_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(RegisterActivity.class, "Please aggree our terms and conditions!", INFOTYPE.ERROR,0));


		if(
				!_registerView.getEmail().isEmpty() &&
				!_registerView.getPassword().isEmpty() &&
				_registerView.getAgreeTerms()){
			_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(RegisterActivity.class, "Register...", INFOTYPE.INFO));

			_clientFactory.getLoginService().isEmailAvailable(_registerView.getEmail(), new AsyncCallback<Boolean>() {

				@Override
				public void onSuccess(Boolean response) {
					if(response==true){

						_clientFactory.getLoginService().registerUser(
								_registerView.getEmail(),
								_registerView.getPassword(),
								_registerView.getAgreeTerms(),
								new AsyncCallback<String>() {

									@Override
									public void onSuccess(String sessionId) {
										if(sessionId!=null){
											_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(RegisterActivity.class, "Juhu. You are registered!!!", INFOTYPE.SUCCESS));
											goTo(new RegisterPlace(RegisterType.THANKS));
											RegisterActivity._logger.log("Login OK. SessionId: " + sessionId);
											Cookies.setCookie("TAP_SID", sessionId);

											//Send LoggedInEvent
											_clientFactory.getEventBus().fireEvent(new LoginChangeEvent(true));
										}else{
											_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(RegisterActivity.class, "Oooops but there is a problem with your registration ;-(", INFOTYPE.ERROR,0));

										}

									}

									@Override
									public void onFailure(Throwable e) {
										_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(RegisterActivity.class, "Exception: "+e, INFOTYPE.ERROR,0));

									}
								});
					}else
						_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(RegisterActivity.class, "Email not available ", INFOTYPE.ERROR,0));


				}

				@Override
				public void onFailure(Throwable e) {
					_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(RegisterActivity.class, "Exception: "+e, INFOTYPE.ERROR,0));

				}
			});
		}




	}
}
