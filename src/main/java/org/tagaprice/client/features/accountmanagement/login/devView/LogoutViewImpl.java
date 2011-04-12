package org.tagaprice.client.features.accountmanagement.login.devView;

import org.tagaprice.client.features.accountmanagement.login.ILogoutView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LogoutViewImpl extends Composite implements ILogoutView {

	private Presenter _presenter;

	public LogoutViewImpl() {
		VerticalPanel vePa = new VerticalPanel();
		Button logoutButton = new Button("Logout");
		initWidget(vePa);

		vePa.add(new HTML ( "<fb:login-button autologoutlink='true' perms='publish_stream,read_stream' />" ));

		vePa.add(new Label("Click logout-button to logout"));
		vePa.add(logoutButton);

		logoutButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				_presenter.onLogOutEvent();
			}
		});

	}

	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
