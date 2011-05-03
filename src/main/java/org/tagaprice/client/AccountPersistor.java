package org.tagaprice.client;

import org.tagaprice.client.generics.events.InfoBoxShowEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent.INFOTYPE;
import org.tagaprice.client.generics.events.LoginChangeEvent;
import org.tagaprice.client.generics.events.WaitForAddressEvent;
import org.tagaprice.client.generics.facebook.FBCore;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;
import org.tagaprice.shared.exceptions.WrongEmailOrPasswordException;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This class holds all important information that we need to know at the start of tagaprice, or during surfing around.
 * 
 * 
 */
public class AccountPersistor {
	private static final MyLogger _logger = LoggerFactory.getLogger(AccountPersistor.class);
	private Address I_ADDRESS;
	private FBCore _fbCore = new FBCore();
	private ClientFactory _clientFactory;

	public AccountPersistor() {
		//Start Facebook
		_fbCore.init(Config.CONFIG.facebookAppId(), true, true, true);
	}

	public void setClientFactory(ClientFactory clientFactory) {
		_clientFactory=clientFactory;
	}
	
	public String getSessionId() {
		return Cookies.getCookie("TAP_SID");
	}

	/**
	 * Returns global Address
	 */
	public Address getAddress() {

		if(Cookies.getCookie("TAP_address")!=null &&
				Cookies.getCookie("TAP_Lat")!=null &&
				Cookies.getCookie("TAP_Lng")!=null){

			if(I_ADDRESS==null)I_ADDRESS = new Address(
					Cookies.getCookie("TAP_address"),
					Double.parseDouble(Cookies.getCookie("TAP_Lat")),
					Double.parseDouble(Cookies.getCookie("TAP_Lng")));
		}

		return I_ADDRESS;
	}


	/**
	 * Set Global Address. Saves it also in the cookies.
	 * @param address setGlobalAddress
	 */
	public void setAddress(Address address) {
		AccountPersistor._logger.log("setAddress: "+address);
		if(I_ADDRESS==null)I_ADDRESS=new Address();
		I_ADDRESS.setAddress(address.getAddress());
		I_ADDRESS.setLat(address.getLat());
		I_ADDRESS.setLng(address.getLng());

		Cookies.setCookie("TAP_address", address.getAddress());
		Cookies.setCookie("TAP_Lat", "" + address.getLat());
		Cookies.setCookie("TAP_Lng", "" + address.getLng());
	}

	/**
	 * @return true if user is logged in.
	 */
	public boolean isLoggedIn() {
		if(getSessionId()==null)
			return false;
		else
			return true;
	}

	/**
	 * Logout user and remove sid
	 */
	public void logout() {
		AccountPersistor._logger.log("LogOut Button clicked");
		Cookies.removeCookie("TAP_SID");
		_clientFactory.getEventBus().fireEvent(new LoginChangeEvent(false));

		//Go To Login Place
		//goTo(new LoginPlace());



		_clientFactory.getLoginService().setLogout(new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void value) {
				AccountPersistor._logger.log("Logout was ok: " + value);
				//Send User login event

			}

			@Override
			public void onFailure(Throwable caught) {
				try {
					throw caught;
				} catch (UserNotLoggedInException e) {
					AccountPersistor._logger.log("Login problem: " + e);
				} catch (Throwable e) {
					AccountPersistor._logger.log("Unexpected error: " + e);
				}
			}
		});
	}

	/**
	 * Log user in and set session id
	 * @param sessionId new SessionId
	 */
	public void setSessionId(String sessionId) {
		Cookies.setCookie("TAP_SID", sessionId);
		checkLogin();
	}

	/**
	 * Login and set SessionId
	 * @param email email
	 * @param password Password
	 */
	public void login(String email, String password){
		_clientFactory.getLoginService().setLogin(email, password, new AsyncCallback<String>() {

			@Override
			public void onSuccess(String sessionId) {
				AccountPersistor._logger.log("Login OK. SessionId: " + sessionId);
				setSessionId(sessionId);

				//Send User login event
				_clientFactory.getEventBus().fireEvent(new LoginChangeEvent(true));

				//Go to user Area
			}

			@Override
			public void onFailure(Throwable caught) {
				try {
					throw caught;
				} catch (WrongEmailOrPasswordException e) {
					AccountPersistor._logger.log("Login problem: " + e);
					_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(AccountPersistor.class, "Login problem: " + e, INFOTYPE.ERROR));
				} catch (Throwable e) {
					AccountPersistor._logger.log("Unexpected error: " + e);
				}

			}
		});
	}

	/**
	 * This method checks the login status and will fire all necessary events
	 */
	public void checkLogin(){

		//Start Account Initialisation
		//clientFactory.getAccountPersistor().getAddress();

		//Get Position if no one is saved in the cookies.
		if(getAddress()==null){
			_clientFactory.getEventBus().fireEvent(new WaitForAddressEvent());
		}

		//Test if user is logged in
		if(isLoggedIn()){
			AccountPersistor._logger.log("User is loggedIn");
			_clientFactory.getEventBus().fireEvent(new LoginChangeEvent(true));
		}else{
			AccountPersistor._logger.log("User is not loggedIn");
			_clientFactory.getEventBus().fireEvent(new LoginChangeEvent(false));
			checkFB();
		}

	}


	private void checkFB(){
		//Check current status via async
		_fbCore.getLoginStatus(new AsyncCallback<JavaScriptObject>() {

			@Override
			public void onSuccess(JavaScriptObject result) {
				AccountPersistor._logger.log("Findout the facebook login status");
				//myHellow(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				AccountPersistor._logger.log("Facebook login Problem");
			}
		});
	}
}
