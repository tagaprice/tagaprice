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
package org.tagaprice.client.account;

import org.tagaprice.client.Page;
import org.tagaprice.client.TitlePanel;
import org.tagaprice.client.InfoBox.BoxType;
import org.tagaprice.shared.Address;
import org.tagaprice.shared.rpc.LocalAccountHandler;
import org.tagaprice.shared.rpc.LocalAccountHandlerAsync;
import com.google.code.gwt.geolocation.client.Geolocation;
import com.google.code.gwt.geolocation.client.Position;
import com.google.code.gwt.geolocation.client.PositionCallback;
import com.google.code.gwt.geolocation.client.PositionError;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MarkerDragEndHandler;
import com.google.gwt.maps.client.geocode.Geocoder;
import com.google.gwt.maps.client.geocode.LatLngCallback;
import com.google.gwt.maps.client.geocode.LocationCallback;
import com.google.gwt.maps.client.geocode.Placemark;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RegistrationPage extends Page {

	private VerticalPanel vePa1 = new VerticalPanel();
	private LocalAccountHandlerAsync userHandler = GWT.create(LocalAccountHandler.class);
	private TextBox userName = new TextBox();
	private Label userNameLabel = new Label("Username (More the 5 letters)");
	private PasswordTextBox password = new PasswordTextBox();
	private Label passwordLabel = new Label("Password (bad)");
	private PasswordTextBox passwortConfirm = new PasswordTextBox();
	private Label passwortConfirmLabel = new Label("Confirm Password (equal)");
	private TextBox email = new TextBox();
	private Label emailLabel = new Label("Email (not valid)");
	private TextBox emailConfirm = new TextBox();
	private Label emailConfirmLabel = new Label("Confirm Email (equal)");
	
	//Address
	private ListBox language = new ListBox();
	private LatLng latLng;
	private Geolocation myGeo = Geolocation.getGeolocation();
	private Geocoder geoCoder = new Geocoder();
	private MapWidget map = new MapWidget();
	private Marker marker;	
	private TextBox street = new TextBox();
	private TextBox zip = new TextBox();
	private TextBox county = new TextBox();
	private TextBox country = new TextBox();
	
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
		vePa1.add(new Label("Hier steht vielleicht ein wenig Text. "));
		
		//User Data
		Grid userData = new Grid(10,1);
		userData.setWidth("100%");
		userData.getCellFormatter().setWidth(0, 0, "100%");
		userData.setWidget(0, 0, userNameLabel);
		userData.setWidget(1, 0, userName);
		userName.setWidth("100%");
		
		userData.setWidget(2, 0, passwordLabel);
		userData.setWidget(3, 0, password);
		password.setWidth("100%");
		
		userData.setWidget(4, 0, passwortConfirmLabel);
		userData.setWidget(5, 0, passwortConfirm);		
		passwortConfirm.setWidth("100%");
		
		userData.setWidget(6, 0, emailLabel);
		userData.setWidget(7, 0, email);
		email.setWidth("100%");
		
		userData.setWidget(8, 0, emailConfirmLabel);
		userData.setWidget(9, 0, emailConfirm);
		emailConfirm.setWidth("100%");
		
		//grid-Style
		userData.getCellFormatter().setStyleName(0, 0, "RegistrationPage-Row");
		userData.getCellFormatter().setStyleName(1, 0, "RegistrationPage-Row");
		userData.getCellFormatter().setStyleName(2, 0, "RegistrationPage-Row");
		userData.getCellFormatter().setStyleName(3, 0, "RegistrationPage-Row");
		userData.getCellFormatter().setStyleName(4, 0, "RegistrationPage-Row");
		userData.getCellFormatter().setStyleName(5, 0, "RegistrationPage-Row");
		userData.getCellFormatter().setStyleName(6, 0, "RegistrationPage-Row");
		userData.getCellFormatter().setStyleName(7, 0, "RegistrationPage-Row");
		userData.getCellFormatter().setStyleName(8, 0, "RegistrationPage-Row");
		userData.getCellFormatter().setStyleName(9, 0, "RegistrationPage-Row");
		
		
		//Validate Data		
		userName.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if(userName.getText().trim().length()<1){
					userNameLabel.setText("Name (must not be empty)");
				}else{
					userNameLabel.setText("Name");
				}
			}
		});
		
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
				emailConfirm.setText("");
				emailConfirmLabel.setText("Confirm Email (not equal)");
			}
		});
		
		emailConfirm.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if(email.getText().trim().equals(emailConfirm.getText().trim())){
					emailConfirmLabel.setText("Confirm Email (equal)");
				}else{
					emailConfirmLabel.setText("Confirm Email (not equal)");
				}
				
			}
		});
		
		
		vePa1.add(new TitlePanel("Userdaten", userData, TitlePanel.Level.H2));
		
		
		//Alternative Connertions
		VerticalPanel altConn = new VerticalPanel();
		altConn.setWidth("100%");
		
		
		altConn.add(new Image("http://a0.twimg.com/images/dev/buttons/sign-in-with-twitter-l.png"));
		altConn.add(new Image("http://developers.facebook.com/images/devsite/login-button.png"));
		
		vePa1.add(new TitlePanel("Alternatve Connetions", altConn, TitlePanel.Level.H2));
		
		
		//Optional
		VerticalPanel optionalVePa = new VerticalPanel();
		optionalVePa.setWidth("100%");
		
		//Language
		Grid languageGrid = new Grid(2, 1);
		languageGrid.setWidth("100%");
		languageGrid.getCellFormatter().setStyleName(0, 0, "RegistrationPage-Row");
		languageGrid.getCellFormatter().setStyleName(1, 0, "RegistrationPage-Row");
		optionalVePa.add(languageGrid);
		
		language.setWidth("100%");
		language.addItem("British-English", "en-uk");
		language.addItem("US-Englisch", "en-us");
		language.addItem("German","de-de");
		
		languageGrid.setText(0, 0, "Language");
		languageGrid.setWidget(1, 0, language);
		
		
		//Address
		
		latLng = LatLng.newInstance(map.getCenter().getLatitude(), map.getCenter().getLongitude());
		MarkerOptions makerOption = MarkerOptions.newInstance();
		makerOption.setDraggable(true);
		marker = new Marker(latLng, makerOption);
		map.addOverlay(marker);
		map.setWidth("100%");
		map.setHeight("200px");
		map.setZoomLevel(14);
		optionalVePa.add(map);
		
		//Style
		Grid optionalGrid = new Grid(8, 1);
		optionalGrid.setWidth("100%");
		optionalGrid.getCellFormatter().setWidth(0, 0, "100%");
		optionalVePa.add(optionalGrid);
		

		
		//address
		optionalGrid.setText(0, 0, "Street");
		optionalGrid.setWidget(1, 0, street);
		optionalGrid.setText(2, 0, "ZIP");
		optionalGrid.setWidget(3, 0, zip);
		optionalGrid.setText(4, 0, "County");
		optionalGrid.setWidget(5, 0, county);
		optionalGrid.setText(6, 0, "Country");
		optionalGrid.setWidget(7, 0, country);
		
		//Style
		street.setWidth("100%");
		zip.setWidth("100%");
		county.setWidth("100%");
		country.setWidth("100%");
		
		optionalGrid.getCellFormatter().setStyleName(0, 0, "RegistrationPage-Row");
		optionalGrid.getCellFormatter().setStyleName(1, 0, "RegistrationPage-Row");
		optionalGrid.getCellFormatter().setStyleName(2, 0, "RegistrationPage-Row");
		optionalGrid.getCellFormatter().setStyleName(3, 0, "RegistrationPage-Row");
		optionalGrid.getCellFormatter().setStyleName(4, 0, "RegistrationPage-Row");
		optionalGrid.getCellFormatter().setStyleName(5, 0, "RegistrationPage-Row");
		optionalGrid.getCellFormatter().setStyleName(6, 0, "RegistrationPage-Row");
		optionalGrid.getCellFormatter().setStyleName(7, 0, "RegistrationPage-Row");
		
		setAutoPosition();	
		
		
		//Drag Marker
		map.setPinchToZoom(true);
		marker.setDraggingEnabled(true);
		marker.addMarkerDragEndHandler(new MarkerDragEndHandler() {
			
			@Override
			public void onDragEnd(MarkerDragEndEvent event) {
				latLng=marker.getLatLng();				
				setLocationByPoint(latLng);
				
			}
		});
		
		
		//Auto Show Button
		HorizontalPanel autoShowHoPa = new HorizontalPanel();
		autoShowHoPa.setWidth("100%");
		Button show = new Button("Show");
		show.setWidth("100%");
		Button auto = new Button("Auto");
		auto.setWidth("100%");
		autoShowHoPa.add(auto);
		autoShowHoPa.add(show);		
		optionalVePa.add(autoShowHoPa);
		optionalVePa.setCellHorizontalAlignment(autoShowHoPa, HasHorizontalAlignment.ALIGN_LEFT);
		
		auto.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setAutoPosition();				
			}
		});
		
		
		show.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				geoCoder.getLatLng(
						street.getText().trim()+", "
						+zip.getText().trim()+", "
						+county.getText().trim()+", "
						+country.getText().trim(), new LatLngCallback() {					
					@Override
					public void onSuccess(LatLng point) {
						latLng=point;
						map.setCenter(latLng);				
						marker.setLatLng(latLng);
					}					
					@Override
					public void onFailure() {
						// TODO Auto-generated method stub
						System.err.println("find LatLng error");
					}
				});
				
			}
		});
		
		
		
		vePa1.add(new TitlePanel("Optional", optionalVePa, TitlePanel.Level.H2));
		
		
		//AGB
		VerticalPanel gtcVePa = new VerticalPanel();
		gtcVePa.setWidth("100%");
		
		gtcVePa.add(new Label("You have to agree with your general terms and conditions!"));
		gtcVePa.add(gtc);
		vePa1.add(new TitlePanel("general terms and conditions ", gtcVePa, TitlePanel.Level.H2));
		
		
		//TODO Register
		Button register = new Button("register");
		register.setWidth("100%");
		vePa1.add(register);
		
		register.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				
				if(!userName.getText().trim().isEmpty() &&
						password.getText().trim().equals(passwortConfirm.getText().trim()) &&
						email.getText().trim().equals(emailConfirm.getText().trim()) && 
						gtc.getValue()){
					showInfo("Waiting...", BoxType.WARNINGBOX);
					userHandler.registerNewUser(
							userName.getText().trim(), 
							password.getText().trim(), 
							passwortConfirm.getText().trim(), 
							email.getText().trim(), 
							emailConfirm.getText().trim(), 
							new Address(), 
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
										showInfo("Bitte die eingabe nochmals kontrollieren!", BoxType.WARNINGBOX);
									}
								}
							});	
				}else{
					showInfo("Bitte die eingabe nochmals kontrollieren!", BoxType.WARNINGBOX);
				}
				
				
						
			}
		});
		
	}
	
	private void setLocationByPoint(LatLng latLng){
		geoCoder.getLocations(latLng, new LocationCallback() {
			
			@Override
			public void onSuccess(JsArray<Placemark> locations) {
				street.setText(locations.get(0).getStreet());
				zip.setText(locations.get(0).getPostalCode());
				county.setText(locations.get(0).getCity());
				country.setText(locations.get(0).getCountry());
			}
			
			@Override
			public void onFailure(int statusCode) {
				// TODO Auto-generated method stub
				System.err.println("geoCoder error");
			}
		});
	}
	
	private void setAutoPosition(){
		myGeo.getCurrentPosition(new PositionCallback() {
			
			@Override
			public void onSuccess(Position position) {
				latLng=LatLng.newInstance(position.getCoords().getLatitude(), position.getCoords().getLongitude());
				map.setCenter(latLng);				
				marker.setLatLng(latLng);
				setLocationByPoint(latLng);
			}
			
			@Override
			public void onFailure(PositionError error) {
				// TODO Auto-generated method stub
				System.out.println("position error");
			}
		});
	}

	@Override
	public void setAddress(Address address) {
		// TODO Auto-generated method stub
		
	}
}
