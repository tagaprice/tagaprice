package org.tagaprice.client.pages.account;

import org.tagaprice.client.RPCHandlerManager;
import org.tagaprice.client.pages.APage;
import org.tagaprice.client.widgets.TitleWidget;
import org.tagaprice.client.widgets.InfoBoxWidget.BoxType;
import org.tagaprice.client.widgets.TitleWidget.Headline;
import org.tagaprice.shared.Address;
import org.tagaprice.shared.rpc.LocalAccountHandler;
import org.tagaprice.shared.rpc.LocalAccountHandlerAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Displays a Login or Logout page depending on the state of the user. It will
 * also control the login/logout mechanism via TagAPrice login or oauth login
 * like twitter or facebook.
 * 
 */
public class LoginPage extends APage {
	private Button _loginButton = new Button("Login");
	private TextBox _mail = new TextBox();
	private Label _mailLabel = new Label("Mail");
	private PasswordTextBox _password = new PasswordTextBox();
	private Label _passwordLabel = new Label("Password");
	private LocalAccountHandlerAsync _userHandler = RPCHandlerManager
			.getLocalAccountHandler();
	private VerticalPanel _vePa1 = new VerticalPanel();

	/**
	 * Create a login/logout view and controls the login/logout mechanism.
	 * 
	 * @param loggedIn
	 *            If TRUE it will display a logout button. If FALSE it will
	 *            display a Login Form, and alternative oauth login buttons.
	 */
	public LoginPage(boolean loggedIn) {
		_mail.setTitle("tap_email");
		_password.setTitle("tap_password");

		init(_vePa1);
		_vePa1.setWidth("100%");

		if (loggedIn) {
			showLoggedIn();
		} else {
			showNotLoggedIn();
		}

	}

	@Override
	public void setAddress(Address address) {
		// TODO Auto-generated method stub

	}

	private void showLoggedIn() {
		_vePa1.add(new Label("Your are logged in!"));
		_vePa1.add(new Button("Logout", new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				_userHandler.logout(new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable arg0) {
						Cookies.removeCookie("TaPSId");
						showInfo("Problem at logout!", BoxType.WARNINGBOX);
					}

					@Override
					public void onSuccess(Boolean result) {
						if (result) {
							Cookies.removeCookie("TaPSId");
							showInfo("You are logged out", BoxType.WARNINGBOX);
							History.newItem("home/");
						} else {
							Cookies.removeCookie("TaPSId");
							showInfo("Problem at logout!", BoxType.WARNINGBOX);
						}
					}
				});

			}
		}));
	}

	private void showNotLoggedIn() {
		// UserLogin
		VerticalPanel vePa2 = new VerticalPanel();
		vePa2.setWidth("100%");
		vePa2.add(_mailLabel);
		vePa2.add(_mail);
		vePa2.add(_passwordLabel);
		vePa2.add(_password);
		vePa2.add(_loginButton);

		// Style
		_mail.setWidth("100%");
		_password.setWidth("100%");
		_loginButton.setWidth("100%");

		_vePa1.add(new TitleWidget("Login", vePa2, Headline.H2));

		_loginButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				System.out.println("send");

				_userHandler.login(_mail.getText().trim(), _password.getText()
						.trim(), new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						showInfo("Login or other error", BoxType.WARNINGBOX);

					}

					@Override
					public void onSuccess(String result) {

						System.out.println("result: " + result);

						if (result != null) {
							Cookies.setCookie("TaPSId", result);
							showInfo("succsessfull Login", BoxType.WARNINGBOX);
							History.newItem("home/");
						} else
							showInfo("Mail and password don't match",
									BoxType.WARNINGBOX);
					}
				});

			}
		});

		// Alternative Login
		VerticalPanel vePa3 = new VerticalPanel();
		vePa2.setWidth("100%");

		vePa3.add(new Image(
				"http://a0.twimg.com/images/dev/buttons/sign-in-with-twitter-l.png"));
		vePa3.add(new Image(
				"http://developers.facebook.com/images/devsite/login-button.png"));

		_vePa1.add(new TitleWidget("Alternative Login", vePa3, Headline.H2));

	}
}
