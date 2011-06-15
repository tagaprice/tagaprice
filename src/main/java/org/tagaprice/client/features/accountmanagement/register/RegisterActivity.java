package org.tagaprice.client.features.accountmanagement.register;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.features.accountmanagement.login.LoginPlace;
import org.tagaprice.client.generics.events.InfoBoxDestroyEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent.INFOTYPE;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class RegisterActivity implements IRegisterView.Presenter, Activity {

	private RegisterPlace _place;
	private ClientFactory _clientFactory;
	private IRegisterView _registerView;

	public RegisterActivity(RegisterPlace place, ClientFactory clientFactory) {
		Log.debug("RegisterActivity created");

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

		Log.debug("Register Button Pressed");
		Log.debug("Email: "+_registerView.getEmail());
		Log.debug("PW: "+_registerView.getPassword());

		if(_registerView.getEmail().isEmpty())
			_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(RegisterActivity.class, "Email is empty", INFOTYPE.ERROR,0));

		if(_registerView.getPassword().isEmpty())
			_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(RegisterActivity.class, "Password is empty", INFOTYPE.ERROR,0));

		if(!_registerView.getPassword().isEmpty() && _registerView.getPassword().length()<6)
			_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(RegisterActivity.class, "Password must have more than 6 characters", INFOTYPE.ERROR,0));

		if(!_registerView.getAgreeTerms())
			_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(RegisterActivity.class, "Please aggree our terms and conditions!", INFOTYPE.ERROR,0));


		if(
				!_registerView.getEmail().isEmpty() &&
				_registerView.getPassword().length()>=6 &&
				_registerView.getAgreeTerms()){
			final InfoBoxShowEvent registerShow = new InfoBoxShowEvent(RegisterActivity.class, "Try to register...", INFOTYPE.INFO);
			_clientFactory.getEventBus().fireEvent(registerShow);

			_clientFactory.getLoginService().isEmailAvailable(_registerView.getEmail(), new AsyncCallback<Boolean>() {

				@Override
				public void onSuccess(Boolean response) {
					if(response==true){

						_clientFactory.getLoginService().registerUser(
								_registerView.getEmail(),
								_registerView.getPassword(),
								_registerView.getAgreeTerms(),
								new AsyncCallback<Boolean>() {

									@Override
									public void onSuccess(Boolean isok) {
										Log.debug("registration successful");
										if(isok){
											_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(registerShow));
											_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(RegisterActivity.class, "Juhu. You are registered!!!", INFOTYPE.INFO));
											goTo(new LoginPlace());

										}else{
											Log.error("Unexpected registration problem");
											_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(RegisterActivity.class, "Unexpacted problem", INFOTYPE.ERROR,0));

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
						_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(RegisterActivity.class, "Email not available ", INFOTYPE.ERROR,0));
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
