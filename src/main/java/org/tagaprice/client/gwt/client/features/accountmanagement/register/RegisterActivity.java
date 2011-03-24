package org.tagaprice.client.gwt.client.features.accountmanagement.register;

import org.tagaprice.client.gwt.client.ClientFactory;
import org.tagaprice.client.gwt.shared.logging.LoggerFactory;
import org.tagaprice.client.gwt.shared.logging.MyLogger;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Label;

public class RegisterActivity implements IRegisterView.Presenter, Activity {
	private static final MyLogger _logger = LoggerFactory.getLogger(RegisterActivity.class);

	private RegisterPlace _place;
	private ClientFactory _clientFactory;

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

		panel.setWidget(new Label("Add here the Register bla bla"));
	}

	@Override
	public void goTo(Place place) {
		this._clientFactory.getPlaceController().goTo(place);
	}

	@Override
	public void checkEmail() {
		// TODO Auto-generated method stub

	}

}
