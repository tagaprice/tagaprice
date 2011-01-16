package org.tagaprice.client.gwt.client.features.accountmanagement.login;

import org.tagaprice.client.gwt.client.ClientFactory;
import org.tagaprice.client.gwt.shared.logging.LoggerFactory;
import org.tagaprice.client.gwt.shared.logging.MyLogger;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class LoginActivity implements ILoginView.Presenter, Activity {
	private static final MyLogger _logger = LoggerFactory.getLogger(LoginActivity.class);

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

		ILoginView view = _clientFactory.getLoginView();
		view.setPresenter(this);

		panel.setWidget(view);
	}

	@Override
	public void goTo(Place place) {
		// TODO Auto-generated method stub


	}

}
