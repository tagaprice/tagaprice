package org.tagaprice.client.features.accountmanagement.login.devView;

import org.tagaprice.client.features.accountmanagement.login.ILoginView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LoginViewImpl extends Composite implements ILoginView {
	interface LoginViewImplUiBinder extends UiBinder<Widget, LoginViewImpl>{}

	private static LoginViewImplUiBinder uiBinder = GWT.create(LoginViewImplUiBinder.class);

	private Presenter _presenter;

	@UiField
	TextBox email;

	@UiField
	PasswordTextBox password;

	@UiField
	Button loginButton;

	@UiField
	SimplePanel _fbPanel;

	public LoginViewImpl() {
		initWidget(LoginViewImpl.uiBinder.createAndBindUi(this));

		//Set Fabecook button
		_fbPanel.setWidget(new HTML ( "<fb:login-button autologoutlink='true' perms='publish_stream,read_stream' />" ));
	}

	@Override
	public String getEmail(){
		return email.getText();
	}

	@Override
	public String getPassword(){
		return password.getText();
	}

	@UiHandler("loginButton")
	public void onLoginButtonCicked(ClickEvent event){
		_presenter.onLoginEvent();
	}

	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;
	}

	@Override
	public void showWaitForConfirmation() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void showSignInUp(boolean showSingIn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}


}
