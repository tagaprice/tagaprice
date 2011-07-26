package org.tagaprice.client.features.accountmanagement.login.desktopView;

import org.tagaprice.client.features.accountmanagement.login.ILoginView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoginViewImpl extends Composite implements ILoginView {

	private VerticalPanel vePa = new VerticalPanel();
	private TextBox email = new TextBox();
	private PasswordTextBox password = new PasswordTextBox();
	private Button signInButton = new Button("Sign in");
	private Presenter _presenter;

	public LoginViewImpl() {
		initWidget(vePa);
		setStyleName("loginView");
		
		//facebook
		Image fb = new Image("desktopView/fb-login-button.png");
		fb.setStyleName("loginView-pic");
		vePa.add(fb);
		
		//twitter
		Image tw = new Image("https://si0.twimg.com/images/dev/buttons/sign-in-with-twitter-l.png");
		vePa.add(tw);
		vePa.add(new HTML("<br />"));
		
		//Username
		Label emailText = new Label("Email");
		Label passwordText = new Label("Password");
		
		vePa.add(emailText);
		vePa.add(email);
		vePa.add(passwordText);
		vePa.add(password);
		
		//sign in button
		vePa.add(signInButton);
		vePa.setCellHorizontalAlignment(signInButton, HorizontalPanel.ALIGN_RIGHT);
		signInButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_presenter.onLoginEvent();				
			}
		});
		
		password.addKeyDownHandler(new KeyDownHandler() {
			
			@Override
			public void onKeyDown(KeyDownEvent key) {
				if(key.getNativeKeyCode() == 13){
					_presenter.onLoginEvent();
				}
				
			}
		});
		
		
		//forgot Password
		HTML passwordForgotText = new HTML("<a href=\"\">Forgot password?</a>");
		vePa.add(passwordForgotText);
		vePa.add(new HTML("<hr />"));
		HTML noUser = new HTML("<a href=\"#Register:/REGISTER\">Don't havean account? Sign up!</a>");
		vePa.add(noUser);
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;		
	}

	@Override
	public String getEmail() {
		return email.getText();
	}

	@Override
	public String getPassword() {
		return password.getText();
	}

}
