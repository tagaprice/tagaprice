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
package org.tagaprice.client.pages.account;

import org.tagaprice.client.pages.APage;
import org.tagaprice.client.widgets.InfoBoxWidget.BoxType;
import org.tagaprice.client.widgets.TitleWidget;
import org.tagaprice.shared.Address;
import org.tagaprice.shared.rpc.LocalAccountHandler;
import org.tagaprice.shared.rpc.LocalAccountHandlerAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RegistrationPage extends APage {

	private VerticalPanel vePa1 = new VerticalPanel();
	private LocalAccountHandlerAsync userHandler = GWT.create(LocalAccountHandler.class);
	private TextBox email = new TextBox();
	private Label emailLabel = new Label("Email (not valid)");
	private PasswordTextBox password = new PasswordTextBox();
	private Label passwordLabel = new Label("Password (bad)");
	private PasswordTextBox passwortConfirm = new PasswordTextBox();
	private Label passwortConfirmLabel = new Label("Confirm Password (equal)");
	
	
	//Address
	private ListBox language = new ListBox();

	//AGB
	CheckBox gtc = new CheckBox("I agree.");
	
	/**
	 * Start a new user Registration
	 * @param varificationCode If not null user has just being verified.
	 */
	public RegistrationPage() {
		init(vePa1);
		setStyleName("RegistrationPage");
		
		vePa1.setWidth("100%");
				
		
		
		//Text
		vePa1.add(new Label("Nice text "));
		
		//User Data
		Grid userData = new Grid(6,1);
		userData.setWidth("100%");
		userData.getCellFormatter().setWidth(0, 0, "100%");
		
		userData.setWidget(0, 0, emailLabel);
		userData.setWidget(1, 0, email);
		email.setWidth("100%");
		
		userData.setWidget(2, 0, passwordLabel);
		userData.setWidget(3, 0, password);
		password.setWidth("100%");
		
		userData.setWidget(4, 0, passwortConfirmLabel);
		userData.setWidget(5, 0, passwortConfirm);		
		passwortConfirm.setWidth("100%");
		
		
		

		
		//grid-Style
		userData.getCellFormatter().setStyleName(0, 0, "RegistrationPage-Row");
		userData.getCellFormatter().setStyleName(1, 0, "RegistrationPage-Row");
		userData.getCellFormatter().setStyleName(2, 0, "RegistrationPage-Row");
		userData.getCellFormatter().setStyleName(3, 0, "RegistrationPage-Row");
		userData.getCellFormatter().setStyleName(4, 0, "RegistrationPage-Row");
		userData.getCellFormatter().setStyleName(5, 0, "RegistrationPage-Row");
		
		
		
		password.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if(password.getText().trim().length()<5){
					passwordLabel.setText("Password (bad)");
				}else if(password.getText().trim().length()<8){
					passwordLabel.setText("Password (ok)");
				}else if(password.getText().trim().length()>8){
					passwordLabel.setText("Password (good)");
				}
				passwortConfirm.setText("");
				passwortConfirmLabel.setText("Confirm Password (not equal)");
			}
		});
		
		passwortConfirm.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if(password.getText().trim().equals(passwortConfirm.getText().trim())){
					passwortConfirmLabel.setText("Confirm Password (equal)");
				}else{
					passwortConfirmLabel.setText("Confirm Password (not equal)");
				}
				
			}
		});
		
		email.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if(email.getText().toLowerCase().trim().matches(".+@.+\\.[a-z][a-z]+")){
					emailLabel.setText("Email (Check if used...)");
					
					userHandler.checkMailAvailability(email.getText().trim(), new AsyncCallback<Boolean>() {
						
						@Override
						public void onSuccess(Boolean result) {
							
							if(result){
								emailLabel.setText("Email (available)");
							}else{
								emailLabel.setText("Email (already in use)");
							}
							
						}
						
						@Override
						public void onFailure(Throwable caught) {
							System.out.println("Connection problem");							
						}
					});
				}else{
					emailLabel.setText("Email (not valid)");
				}
			}
		});

		
		
		vePa1.add(new TitleWidget("Userdaten", userData, TitleWidget.Headline.H2));
		
		
		//Alternative Connertions
		VerticalPanel altConn = new VerticalPanel();
		altConn.setWidth("100%");
		
		
		altConn.add(new Image("http://a0.twimg.com/images/dev/buttons/sign-in-with-twitter-l.png"));
		altConn.add(new Image("http://developers.facebook.com/images/devsite/login-button.png"));
		
		vePa1.add(new TitleWidget("Alternatve Connetions", altConn, TitleWidget.Headline.H2));
		
		
		//Language select	
		
		language.setWidth("100%");
		language.addItem("British-English", "en-uk");
		language.addItem("US-Englisch", "en-us");
		language.addItem("German","de-de");		
		
		vePa1.add(new TitleWidget("Language", language, TitleWidget.Headline.H2));
		
		
		//AGB
		VerticalPanel gtcVePa = new VerticalPanel();
		gtcVePa.setWidth("100%");
		
		gtcVePa.add(new Label("You have to agree with your general terms and conditions!"));
		gtcVePa.add(gtc);
		vePa1.add(new TitleWidget("general terms and conditions ", gtcVePa, TitleWidget.Headline.H2));
		
		
		//TODO Register
		Button register = new Button("register");
		register.setWidth("100%");
		vePa1.add(register);
		
		register.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				
				if(	password.getText().trim().equals(passwortConfirm.getText().trim()) &&
						!email.getText().trim().isEmpty() && 
						gtc.getValue()){
					showInfo("Waiting...", BoxType.WARNINGBOX);
					userHandler.registerNewUser(
							password.getText().trim(), 
							passwortConfirm.getText().trim(), 
							email.getText().trim(),
							gtc.getValue(), new AsyncCallback<Boolean>() {

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub
									System.err.println("Registartion failt+ "+caught);
									
								}

								@Override
								public void onSuccess(Boolean result) {
									// TODO Auto-generated method stub
									if(result){
										History.newItem("user/registration/confirm");
									}else{
										showInfo("Please check your input.", BoxType.WARNINGBOX);
									}
								}
							});	
				}else{
					showInfo("Bitte die eingabe nochmals kontrollieren!", BoxType.WARNINGBOX);
				}
				
				
						
			}
		});
		
	}

	@Override
	public void setAddress(Address address) {
		// TODO Auto-generated method stub
		
	}
	
}
