package org.tagaprice.client.gwt.client.features.accountmanagement.register.devView;

import org.tagaprice.client.gwt.client.features.accountmanagement.register.IRegisterView;

import com.claudiushauptmann.gwt.recaptcha.client.RecaptchaWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;


public class RegisterViewImpl extends Composite implements IRegisterView {
	interface RegisterViewImplUiBinder extends UiBinder<Widget, RegisterViewImpl>{}
	private static RegisterViewImplUiBinder uiBinder = GWT.create(RegisterViewImplUiBinder.class);

	@UiField
	SimplePanel _recaptchPanel;

	public RegisterViewImpl() {
		initWidget(RegisterViewImpl.uiBinder.createAndBindUi(this));

		RecaptchaWidget reWidget = new RecaptchaWidget("6LfpzcISAAAAAPZpztEIl3Mcql5lFbMFFMZeqvwJ");
		_recaptchPanel.setWidget(reWidget);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEmailIsFree(boolean inFree) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getConfirmPassword() {
		// TODO Auto-generated method stub
		return null;
	}

}
