/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: TagAPriceUI
 * Filename: RegistrationPage.java
 * Date: 07.07.2010
*/
package org.tagaprice.client.user;

import org.tagaprice.client.TitlePanel;
import org.tagaprice.shared.rpc.UserHandler;
import org.tagaprice.shared.rpc.UserHandlerAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RegistrationPage extends Composite {

	VerticalPanel vePa1 = new VerticalPanel();
	private UserHandlerAsync userHandler = GWT.create(UserHandler.class);
	
	/**
	 * Start a new user Registration
	 * @param varificationCode If not null user has just being verified.
	 */
	public RegistrationPage(String verificationCode) {
		initWidget(vePa1);
		
		vePa1.setWidth("100%");
		
		
		if(verificationCode==null)
			drawRegistartion();
	}
	
	private void drawRegistartion(){
		final TextBox userName = new TextBox();
		final Label userNameLabel = new Label("More the 5 letters");
		final PasswordTextBox passwort = new PasswordTextBox();
		final Label passwortLabel = new Label("bad");
		final PasswordTextBox passwortConfirm = new PasswordTextBox();
		final Label passwortConfirmLabel = new Label("equal");
		final TextBox email = new TextBox();
		final Label emailLabel = new Label("not valid");
		final TextBox emailConfirm = new TextBox();
		final Label emailConfirmLabel = new Label("equal");
		
		
		//Text
		vePa1.add(new Label("Hier steht vielleicht ein wenig Text. "));
		
		//User Data
		Grid userData = new Grid(10,20);
		userData.setText(0, 0, "Username");
		userData.setWidget(0, 1, userName);
		userData.setWidget(1, 1, userNameLabel);
		
		userData.setText(2, 0, "Password");
		userData.setWidget(2, 1, passwort);
		userData.setWidget(3, 1, passwortLabel);
		
		userData.setText(4, 0, "Confirm Password");
		userData.setWidget(4, 1, passwortConfirm);
		userData.setWidget(5, 1, passwortConfirmLabel);
		
		
		userData.setText(6, 0, "Email");
		userData.setWidget(6, 1, email);
		userData.setWidget(7, 1, emailLabel);
		
		
		userData.setText(8, 0, "Confirm Email");
		userData.setWidget(8, 1, emailConfirm);
		userData.setWidget(9, 1, emailConfirmLabel);
		
		
		//Validate Data		
		userName.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if(userName.getText().trim().length()<5){
					userNameLabel.setText("More the 5 letters");
				}else{
					userNameLabel.setText("Check if used...");
					
				}
			}
		});
		
		passwort.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if(passwort.getText().trim().length()<5){
					passwortLabel.setText("bad");
				}else if(passwort.getText().trim().length()<8){
					passwortLabel.setText("ok");
				}else if(passwort.getText().trim().length()>8){
					passwortLabel.setText("good");
				}
				passwortConfirm.setText("");
				passwortConfirmLabel.setText("not equal");
			}
		});
		
		passwortConfirm.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if(passwort.getText().trim().equals(passwortConfirm.getText().trim())){
					passwortConfirmLabel.setText("equal");
				}else{
					passwortConfirmLabel.setText("not equal");
				}
				
			}
		});
		
		email.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if(email.getText().trim().matches(".+@.+\\.[a-z]+")){
					emailLabel.setText("Check if used...");
					
					userHandler.isEmailEvalable(email.getText().trim(), new AsyncCallback<Boolean>() {
						
						@Override
						public void onSuccess(Boolean result) {
							
							if(result){
								emailLabel.setText("evalable");
							}else{
								emailLabel.setText("email aready used");
							}
							
						}
						
						@Override
						public void onFailure(Throwable caught) {
							System.out.println("Connection proplem");							
						}
					});
				}else{
					emailLabel.setText("not valid");
				}
				emailConfirm.setText("");
				emailConfirmLabel.setText("not equal");
			}
		});
		
		emailConfirm.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if(email.getText().trim().equals(emailConfirm.getText().trim())){
					emailConfirmLabel.setText("equal");
				}else{
					emailConfirmLabel.setText("not equal");
				}
				
			}
		});
		
		
		vePa1.add(new TitlePanel("Userdaten", userData, TitlePanel.Level.H2));
		
	}
}
