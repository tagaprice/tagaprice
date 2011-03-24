package org.tagaprice.client.gwt.client.features.accountmanagement.register;

import org.tagaprice.client.gwt.client.ClientFactory;
import org.tagaprice.client.gwt.shared.logging.LoggerFactory;
import org.tagaprice.client.gwt.shared.logging.MyLogger;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
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

		if(_registerView==null)_registerView=_clientFactory.getRegisterView();
		_registerView.setPresenter(this);
		panel.setWidget(_registerView);
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
		RegisterActivity._logger.log("Register Button Pressed");

		RegisterActivity._logger.log("Email: "+_registerView.getEmail());
		RegisterActivity._logger.log("PW: "+_registerView.getPassword());
		RegisterActivity._logger.log("PW2: "+_registerView.getConfirmPassword());
		RegisterActivity._logger.log("challange: "+_registerView.getChallenge());
		RegisterActivity._logger.log("response: "+_registerView.getResponse());


	}

}
