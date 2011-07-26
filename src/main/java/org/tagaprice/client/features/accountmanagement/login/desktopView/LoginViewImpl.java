package org.tagaprice.client.features.accountmanagement.login.desktopView;

import org.tagaprice.client.features.accountmanagement.login.ILoginView;

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
		
		vePa.add(signInButton);
		vePa.setCellHorizontalAlignment(signInButton, HorizontalPanel.ALIGN_RIGHT);
		
		
		//forgot Password
		HTML passwordForgotText = new HTML("<a href=\"\">Forgot password?</a>");
		vePa.add(passwordForgotText);
		vePa.add(new HTML("<hr />"));
		HTML noUser = new HTML("<a href=\"\">Don't havean account? Sign up!</a>");
		vePa.add(noUser);
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
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

}
