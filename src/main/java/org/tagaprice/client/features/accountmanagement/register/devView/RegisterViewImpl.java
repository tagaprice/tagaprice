package org.tagaprice.client.features.accountmanagement.register.devView;

import org.tagaprice.client.features.accountmanagement.register.IRegisterView;
import org.tagaprice.client.Config;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;


public class RegisterViewImpl extends Composite implements IRegisterView {
	interface RegisterViewImplUiBinder extends UiBinder<Widget, RegisterViewImpl>{}
	private static RegisterViewImplUiBinder uiBinder = GWT.create(RegisterViewImplUiBinder.class);
	private Presenter _presenter;



	@UiField
	TextBox _email;

	@UiField
	PasswordTextBox _password;


	@UiField
	CheckBox _terms;

	@UiField
	Button registerButton;

	public RegisterViewImpl() {
		initWidget(RegisterViewImpl.uiBinder.createAndBindUi(this));

	}

	@UiHandler("registerButton")
	public void onSaveButtonClicked(ClickEvent event) {
		_presenter.onRegisterButtonEvent();
	}

	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;
	}

	@Override
	public String getEmail() {
		return _email.getText();
	}

	@Override
	public void setEmailIsFree(boolean inFree) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getPassword() {
		return _password.getText();
	}


	@Override
	public boolean getAgreeTerms() {
		return _terms.getValue();
	}


}
