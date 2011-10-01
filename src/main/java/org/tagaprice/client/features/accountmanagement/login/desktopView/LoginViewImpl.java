package org.tagaprice.client.features.accountmanagement.login.desktopView;

import org.tagaprice.client.features.accountmanagement.login.ILoginView;
import org.tagaprice.client.generics.widgets.MorphWidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoginViewImpl extends Composite implements ILoginView {

	private SimplePanel panel = new SimplePanel();
	private VerticalPanel vePa = new VerticalPanel();
	private VerticalPanel innerVePa = new VerticalPanel();
	private MorphWidget email = new MorphWidget();
	private MorphWidget diplayName = new MorphWidget();
	private PasswordTextBox password = new PasswordTextBox();
	private Button signInButton = new Button("Log in");
	private Button signUpButton = new Button("Sign up!");
	private Presenter _presenter;
	private HTML terms = new HTML("By clicking 'Sign up!' above, you confirm that you accept the <a>terms of service</a>.");
	private MorphWidget inviteKey = new MorphWidget();

	private Label headLine = new Label("Login");
	
	
	//views
	
	
	public LoginViewImpl() {
		vePa.setWidth("350px");
		initWidget(vePa);
		//panel.setWidth("180px");
		vePa.setStyleName("loginView");
		
		//header 
		headLine.setStyleName("loginView-headline");
		vePa.add(headLine);
		
		
		//body
		innerVePa.setWidth("100%");
		innerVePa.setStyleName("loginView-body");
		vePa.add(innerVePa);
		
		//buttons and so on
		innerVePa.add(panel);
		
		
		
		
		
		
		
		//events
		//We have to add hander here because of multible fire events
		signInButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_presenter.onLoginEvent();				
			}
		});
		
		signUpButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_presenter.onRegisterButtonEvent();				
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
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;		
	}

	@Override
	public String getEmail() {
		return email.getValue();
	}

	@Override
	public String getPassword() {
		return password.getText();
	}

	

	@Override
	public String getDisplayName() {
		return diplayName.getValue();
	}


	@Override
	public void setRegisterView(String key) {
		headLine.setText("Register");
		VerticalPanel loginViewVePa = new VerticalPanel();
		loginViewVePa.clear();
		loginViewVePa.setWidth("100%");
		
		
		//invite Key
		Label inviteKeyLabel = new Label("Invite key");
		loginViewVePa.add(inviteKeyLabel);
		inviteKey.setValue(key);
		loginViewVePa.add(inviteKey);
		inviteKey.setReadOnly(false);
		loginViewVePa.add(new HTML("<hr />"));
		
		
		//facebook
		Image fb = new Image("desktopView/fb-login-button.png");
		fb.setStyleName("loginView-pic");
		fb.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				String url = "/TagAPrice/socialAuth?id=facebook";
				if(!inviteKey.getValue().isEmpty())
					url+="&invitekey="+inviteKey.getValue();
				Window.open(url, "_self", "");
			}
		});
		loginViewVePa.add(fb);
		
		//twitter
		Image tw = new Image("https://si0.twimg.com/images/dev/buttons/sign-in-with-twitter-l.png");
		tw.setStyleName("loginView-pic");
		tw.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				String url = "/TagAPrice/socialAuth?id=twitter";
				if(!inviteKey.getValue().isEmpty())
					url+="&invitekey="+inviteKey.getValue();
				Window.open(url, "_self", "");
				
			}
		});
		loginViewVePa.add(tw);
		loginViewVePa.add(new HTML("<hr />"));
		
		
		Label displayText = new Label("Display Name");
		
		//diplayName
		loginViewVePa.add(displayText);
		loginViewVePa.add(diplayName);
		diplayName.setReadOnly(false);
				
		
		//email
		Label emailText = new Label("Email");
		loginViewVePa.add(emailText);
		loginViewVePa.add(email);
		email.setReadOnly(false);
		
		//password
		Label passwordText = new Label("Password");
		loginViewVePa.add(passwordText);
		loginViewVePa.add(password);
		password.setStyleName("morphWidget edit");
		
		
		//singIn button
		loginViewVePa.add(signUpButton);
		
		loginViewVePa.add(terms);
		loginViewVePa.add(new HTML("<br />"));
		
		
		//forgot password
		Label passwordForgotText = new Label("Forgot your password?");
		passwordForgotText.setStyleName("loginView-passwordForgot");
		passwordForgotText.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				setForgotPasswortView();
				
			}
		});
		loginViewVePa.add(passwordForgotText);
	
		panel.setWidget(loginViewVePa);	
	}

	@Override
	public void setLoginView() {
		headLine.setText("Login");
		VerticalPanel loginViewVePa = new VerticalPanel();
		loginViewVePa.clear();
		loginViewVePa.setWidth("100%");
		
		//facebook
		Image fb = new Image("desktopView/fb-login-button.png");
		fb.setStyleName("loginView-pic");
		fb.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				String url = "/TagAPrice/socialAuth?id=facebook";
				Window.open(url, "_self", "");
			}
		});
		loginViewVePa.add(fb);
		
		//twitter
		Image tw = new Image("https://si0.twimg.com/images/dev/buttons/sign-in-with-twitter-l.png");
		tw.setStyleName("loginView-pic");
		tw.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				String url = "/TagAPrice/socialAuth?id=twitter";
				Window.open(url, "_self", "");
				
			}
		});
		loginViewVePa.add(tw);
		loginViewVePa.add(new HTML("<hr />"));
		
		//email
		Label emailText = new Label("Email");
		loginViewVePa.add(emailText);
		loginViewVePa.add(email);
		email.setReadOnly(false);
		
		//password
		Label passwordText = new Label("Password");
		loginViewVePa.add(passwordText);
		loginViewVePa.add(password);
		password.setStyleName("morphWidget edit");
		
		
		//singIn button
		loginViewVePa.add(signInButton);
		
		
		//forgot password
		Label passwordForgotText = new Label("Forgot your password?");
		passwordForgotText.setStyleName("loginView-passwordForgot");
		passwordForgotText.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				setForgotPasswortView();
				
			}
		});
		loginViewVePa.add(passwordForgotText);
	
		panel.setWidget(loginViewVePa);
		
	}

	@Override
	public void setInviteMeView() {
		headLine.setText("Invite Me");
		VerticalPanel inviteMeViewVePa = new VerticalPanel();
		
		//email
		Label emailText = new Label("Email");
		inviteMeViewVePa.add(emailText);
		inviteMeViewVePa.add(email);
		email.setReadOnly(false);
		
		
		//button
		Button inviteMe = new Button("Invite Me",new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_presenter.onInviteEvent();
			}
		});
		inviteMe.setStyleName("save");
		inviteMeViewVePa.add(inviteMe);
		
		
		panel.setWidget(inviteMeViewVePa);
	}

	@Override
	public void setForgotPasswortView() {
		headLine.setText("Forgotten Password");
		panel.setWidget(new Label("ForgotPW"));
		
	}

	@Override
	public void setConfirmationSentView() {
		headLine.setText("Waiting for confirmation");
		panel.setWidget(new Label("You should have got an confirmation email. Please click the link to finish the registration."));
		
	}

	@Override
	public String getInvitationKey() {
		return inviteKey.getValue();
	}

	@Override
	public void setInviteSentView() {
		headLine.setText("Invite Me");
		panel.setWidget(new Label("We add you to the invitation queue, and try to send you an invitation as soon as possible."));
	}

}
