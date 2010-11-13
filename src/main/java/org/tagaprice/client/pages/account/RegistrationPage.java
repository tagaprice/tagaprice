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

/**
 * This page handles the registration of a user. It's also possible to register
 * via Twitter, Facebook or other oauth systems.
 * 
 */
// TODO Create oauth authentication system
public class RegistrationPage extends APage {

	private VerticalPanel _vePa1 = new VerticalPanel();
	private LocalAccountHandlerAsync _userHandler = GWT
			.create(LocalAccountHandler.class);
	private TextBox _email = new TextBox();
	private Label _emailLabel = new Label("Email (not valid)");
	private PasswordTextBox _password = new PasswordTextBox();
	private Label _passwordLabel = new Label("Password (bad)");
	private PasswordTextBox passwortConfirm = new PasswordTextBox();
	private Label passwortConfirmLabel = new Label("Confirm Password (equal)");

	// Address
	private ListBox language = new ListBox();

	// AGB
	CheckBox gtc = new CheckBox("I agree.");

	/**
	 * Create a new RegistrationPage and implements some control methods to
	 * check the correct registration.
	 */
	// TODO Create a confirmation class for client and server
	public RegistrationPage() {
		init(_vePa1);
		setStyleName("RegistrationPage");

		_vePa1.setWidth("100%");

		// Text
		_vePa1.add(new Label("Nice text "));

		// User Data
		Grid userData = new Grid(6, 1);
		userData.setWidth("100%");
		userData.getCellFormatter().setWidth(0, 0, "100%");

		userData.setWidget(0, 0, _emailLabel);
		userData.setWidget(1, 0, _email);
		_email.setWidth("100%");

		userData.setWidget(2, 0, _passwordLabel);
		userData.setWidget(3, 0, _password);
		_password.setWidth("100%");

		userData.setWidget(4, 0, passwortConfirmLabel);
		userData.setWidget(5, 0, passwortConfirm);
		passwortConfirm.setWidth("100%");

		// grid-Style
		userData.getCellFormatter().setStyleName(0, 0, "RegistrationPage-Row");
		userData.getCellFormatter().setStyleName(1, 0, "RegistrationPage-Row");
		userData.getCellFormatter().setStyleName(2, 0, "RegistrationPage-Row");
		userData.getCellFormatter().setStyleName(3, 0, "RegistrationPage-Row");
		userData.getCellFormatter().setStyleName(4, 0, "RegistrationPage-Row");
		userData.getCellFormatter().setStyleName(5, 0, "RegistrationPage-Row");

		_password.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (_password.getText().trim().length() < 5) {
					_passwordLabel.setText("Password (bad)");
				} else if (_password.getText().trim().length() < 8) {
					_passwordLabel.setText("Password (ok)");
				} else if (_password.getText().trim().length() > 8) {
					_passwordLabel.setText("Password (good)");
				}
				passwortConfirm.setText("");
				passwortConfirmLabel.setText("Confirm Password (not equal)");
			}
		});

		passwortConfirm.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (_password.getText().trim()
						.equals(passwortConfirm.getText().trim())) {
					passwortConfirmLabel.setText("Confirm Password (equal)");
				} else {
					passwortConfirmLabel
							.setText("Confirm Password (not equal)");
				}

			}
		});

		_email.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (_email.getText().toLowerCase().trim()
						.matches(".+@.+\\.[a-z][a-z]+")) {
					_emailLabel.setText("Email (Check if used...)");

					_userHandler.checkMailAvailability(_email.getText().trim(),
							new AsyncCallback<Boolean>() {

								@Override
								public void onSuccess(Boolean result) {

									if (result) {
										_emailLabel.setText("Email (available)");
									} else {
										_emailLabel
												.setText("Email (already in use)");
									}

								}

								@Override
								public void onFailure(Throwable caught) {
									System.out.println("Connection problem");
								}
							});
				} else {
					_emailLabel.setText("Email (not valid)");
				}
			}
		});

		_vePa1.add(new TitleWidget("Userdaten", userData,
				TitleWidget.Headline.H2));

		// Alternative Connertions
		VerticalPanel altConn = new VerticalPanel();
		altConn.setWidth("100%");

		altConn.add(new Image(
				"http://a0.twimg.com/images/dev/buttons/sign-in-with-twitter-l.png"));
		altConn.add(new Image(
				"http://developers.facebook.com/images/devsite/login-button.png"));

		_vePa1.add(new TitleWidget("Alternatve Connetions", altConn,
				TitleWidget.Headline.H2));

		// Language select

		language.setWidth("100%");
		language.addItem("British-English", "en-uk");
		language.addItem("US-Englisch", "en-us");
		language.addItem("German", "de-de");

		_vePa1.add(new TitleWidget("Language", language, TitleWidget.Headline.H2));

		// AGB
		VerticalPanel gtcVePa = new VerticalPanel();
		gtcVePa.setWidth("100%");

		gtcVePa.add(new Label(
				"You have to agree with your general terms and conditions!"));
		gtcVePa.add(gtc);
		_vePa1.add(new TitleWidget("general terms and conditions ", gtcVePa,
				TitleWidget.Headline.H2));

		// TODO Register
		Button register = new Button("register");
		register.setWidth("100%");
		_vePa1.add(register);

		register.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				if (_password.getText().trim()
						.equals(passwortConfirm.getText().trim())
						&& !_email.getText().trim().isEmpty() && gtc.getValue()) {
					showInfo("Waiting...", BoxType.WARNINGBOX);
					_userHandler.registerNewUser(_password.getText().trim(),
							passwortConfirm.getText().trim(), _email.getText()
									.trim(), gtc.getValue(),
							new AsyncCallback<Boolean>() {

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub
									System.err.println("Registartion failt+ "
											+ caught);

								}

								@Override
								public void onSuccess(Boolean result) {
									// TODO Auto-generated method stub
									if (result) {
										History.newItem("user/registration/confirm");
									} else {
										showInfo("Please check your input.",
												BoxType.WARNINGBOX);
									}
								}
							});
				} else {
					showInfo("Bitte die eingabe nochmals kontrollieren!",
							BoxType.WARNINGBOX);
				}

			}
		});

	}

	@Override
	public void setAddress(Address address) {
		// TODO Auto-generated method stub

	}

}
