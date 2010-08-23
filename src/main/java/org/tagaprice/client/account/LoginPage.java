package org.tagaprice.client.account;

import org.tagaprice.client.InfoBoxComposite;
import org.tagaprice.client.TitlePanel;
import org.tagaprice.client.InfoBox.BoxType;
import org.tagaprice.client.TitlePanel.Level;
import org.tagaprice.shared.rpc.LocalAccountHandler;
import org.tagaprice.shared.rpc.LocalAccountHandlerAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoginPage extends InfoBoxComposite{
	private VerticalPanel vePa1 = new VerticalPanel();
	private Label userNameLabel = new Label("Username");
	private TextBox userName = new TextBox();
	private Label passwordLabel = new Label("Password");
	private PasswordTextBox password = new PasswordTextBox();
	private Button loginButton = new Button("Login");
	private LocalAccountHandlerAsync userHandler = GWT.create(LocalAccountHandler.class);
	
	
	public LoginPage() {
		init(vePa1);
		vePa1.setWidth("100%");
		
		
		//UserLogin
		VerticalPanel vePa2 = new VerticalPanel();
		vePa2.setWidth("100%");
		vePa2.add(userNameLabel);
		vePa2.add(userName);
		vePa2.add(passwordLabel);
		vePa2.add(password);
		vePa2.add(loginButton);
		
		//Style
		userName.setWidth("100%");
		password.setWidth("100%");
		loginButton.setWidth("100%");
		
		
		vePa1.add(new TitlePanel("Login", vePa2, Level.H2));
		
		loginButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				/*
				userHandler.login(userName.getText().trim(), password.getText().trim(), new AsyncCallback<Boolean>() {
					
					@Override
					public void onSuccess(Boolean result) {
						// TODO Auto-generated method stub
						if(result)
							showInfo("succsessfull Login", BoxType.WARNINGBOX);
						else
							showInfo("Please Check your password and username", BoxType.WARNINGBOX);		
					}
					
					@Override
					public void onFailure(Throwable caught) {
						showInfo("Login or other error", BoxType.WARNINGBOX);
						
					}
				});
				*/
				
			}
		});
		
		
		//Alternative Login
		VerticalPanel vePa3 = new VerticalPanel();
		vePa2.setWidth("100%");
		
		vePa3.add(new Image("http://a0.twimg.com/images/dev/buttons/sign-in-with-twitter-l.png"));
		vePa3.add(new Image("http://developers.facebook.com/images/devsite/login-button.png"));
		
		vePa1.add(new TitlePanel("Alternative Login", vePa3, Level.H2));
		
		
		
	}
}
