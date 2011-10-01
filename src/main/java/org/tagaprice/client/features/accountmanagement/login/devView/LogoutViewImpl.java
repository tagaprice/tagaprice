package org.tagaprice.client.features.accountmanagement.login.devView;

import org.tagaprice.client.features.accountmanagement.login.ILoginView;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LogoutViewImpl extends Composite implements ILoginView {

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
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public void setRegisterView(String key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLoginView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setInviteMeView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setForgotPasswortView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setConfirmationSentView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getInvitationKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInviteSentView() {
		// TODO Auto-generated method stub
		
	}


}
