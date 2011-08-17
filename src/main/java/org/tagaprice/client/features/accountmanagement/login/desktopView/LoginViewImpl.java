package org.tagaprice.client.features.accountmanagement.login.desktopView;

import org.tagaprice.client.features.accountmanagement.login.ILoginView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoginViewImpl extends Composite implements ILoginView {

	private VerticalPanel vePa = new VerticalPanel();
	private TextBox email = new TextBox();
	private PasswordTextBox password = new PasswordTextBox();
	private Button signInButton = new Button("Log in");
	private Button signUpButton = new Button("Sign up!");
	private Presenter _presenter;
	private RadioButton signInRadio = new RadioButton("blabla","I have an account.");
	private RadioButton signUpRadio = new RadioButton("blabla","I'm new!");
	private HTML passwordForgotText = new HTML("<a >Forgot your password?</a>");
	private HTML terms = new HTML("By clicking 'Sign up!' above, you confirm that you accept the <a>terms of service</a>.");

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
		
		//radio
		vePa.add(signInRadio);
		signInRadio.setValue(true);
		vePa.add(signUpRadio);
		
		
		vePa.add(passwordText);
		vePa.add(password);
		
		//sign in button
		
		vePa.add(signInButton);
		vePa.add(signUpButton);
		signUpButton.setVisible(false);
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
		vePa.add(passwordForgotText);
		vePa.add(terms);
		terms.setVisible(false);
		vePa.add(new HTML("<hr />"));
		HTML noUser = new HTML("<a href=\"#Register:/REGISTER\">Don't havean account? Sign up!</a>");
		vePa.add(noUser);
		
		
		//sign in/up select
		signInRadio.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> value) {
				if(value.getValue()){
					signInButton.setVisible(true);
					signUpButton.setVisible(false);
					passwordForgotText.setVisible(true);
					terms.setVisible(false);
				}
			}
		});
		
		signUpRadio.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> value) {
				if(value.getValue()){
					signUpButton.setVisible(true);
					signInButton.setVisible(false);
					passwordForgotText.setVisible(false);
					terms.setVisible(true);
				}				
			}
		});
		
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
