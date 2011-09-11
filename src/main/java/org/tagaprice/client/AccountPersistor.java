package org.tagaprice.client;

import java.util.ArrayList;
import java.util.List;

import org.tagaprice.client.generics.events.InfoBoxShowEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent.INFOTYPE;
import org.tagaprice.client.generics.events.LoginChangeEvent;
import org.tagaprice.client.generics.facebook.FBCore;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.Address.LatLon;
import org.tagaprice.shared.entities.receiptManagement.Receipt;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;
import org.tagaprice.shared.exceptions.WrongEmailOrPasswordException;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This class holds all important information that we need to know at the start of tagaprice, or during surfing around.
 * 
 * 
 */
public class AccountPersistor implements IAccountPersistor {


	private Address I_ADDRESS;
	private FBCore _fbCore = new FBCore();
	private ClientFactory _clientFactory;
	private Receipt _receipt=null;

	public AccountPersistor() {
		//Start Facebook
		_fbCore.init(Config.CONFIG.facebookAppId(), true, true, true);
	}

	@Override
	public void setClientFactory(ClientFactory clientFactory) {
		_clientFactory=clientFactory;
	}

	@Override
	public String getSessionId() {
		return Cookies.getCookie("TAP_SID");
	}

	
	public void addAddress(Address address){
		
		if(Cookies.getCookie("TAP_address_count")==null){
			Cookies.setCookie("TAP_address_count", "0");
		}
		
		
		int c = Integer.parseInt(Cookies.getCookie("TAP_address_count"));
		
		Cookies.setCookie("TAP_address_street_array_"+c,address.getStreet());
		Cookies.setCookie("TAP_address_lat_array_"+c,""+address.getPos().getLat());
		Cookies.setCookie("TAP_address_lon_array_"+c,""+address.getPos().getLon());
		c++;
		Cookies.setCookie("TAP_address_count", ""+c);
	}
	
	
	public List<Address> getAddressList(){
		ArrayList<Address> rc = new ArrayList<Address>();
		
		if(Cookies.getCookie("TAP_address_count")!=null){
			int c = Integer.parseInt(Cookies.getCookie("TAP_address_count"));
			
			for(int i=0;i<c;i++){
				
				Address a = new Address();
				
				a.setStreet(Cookies.getCookie("TAP_address_street_array_"+i));
				
				
				a.setPos(new LatLon(
						Double.parseDouble(Cookies.getCookie("TAP_address_lat_array_"+i)), 
						Double.parseDouble(Cookies.getCookie("TAP_address_lon_array_"+i))));
				
				
				rc.add(a);
			}
			
		}
		
		return rc;
	}
	
	/**
	 * Returns global Address
	 */
	
	public Address getCurAddress() {

		if(Cookies.getCookie("TAP_cur_address_street")!=null &&
				Cookies.getCookie("TAP_cur_address_lat")!=null &&
				Cookies.getCookie("TAP_cur_address_lon")!=null){
			
			Address a = new Address();
			a.setStreet(Cookies.getCookie("TAP_cur_address_street"));
			a.setPos(new LatLon(
					Double.parseDouble(Cookies.getCookie("TAP_cur_address_lat")), 
					Double.parseDouble(Cookies.getCookie("TAP_cur_address_lon"))));
		
			return a;
		}

		return null;
	}
	


	/**
	 * Set Global Address. Saves it also in the cookies.
	 * @param address setGlobalAddress
	 */
	public void setCurAddress(Address address) {
		Log.debug("setAddress: "+address);

		Cookies.setCookie("TAP_cur_address_street", address.getStreet());
		Cookies.setCookie("TAP_cur_address_lat", ""+address.getPos().getLat());
		Cookies.setCookie("TAP_cur_address_lon", ""+address.getPos().getLon());
	}

	/**
	 * @return true if user is logged in.
	 */
	@Override
	public boolean isLoggedIn() {
		if(getSessionId()==null)
			return false;
		else
			return true;
	}

	/**
	 * Logout user and remove sid
	 */
	@Override
	public void logout() {
		Log.debug("LogOut Button clicked");
		Cookies.removeCookie("TAP_SID");
		_clientFactory.getEventBus().fireEvent(new LoginChangeEvent(false));

		//Go To Login Place
		//goTo(new LoginPlace());



		_clientFactory.getLoginService().setLogout(new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void value) {
				Log.debug("Logout was ok: " + value);
				//Send User login event

			}

			@Override
			public void onFailure(Throwable caught) {
				try {
					throw caught;
				} catch (UserNotLoggedInException e) {
					Log.warn("Login problem: " + e);
				} catch (Throwable e) {
					Log.error("Unexpected error: " + e);
				}
			}
		});
	}

	/**
	 * Log user in and set session id
	 * @param sessionId new SessionId
	 */
	@Override
	public void setSessionId(String sessionId) {
		Cookies.setCookie("TAP_SID", sessionId);
		checkLogin();
	}

	/**
	 * Login and set SessionId
	 * @param email email
	 * @param password Password
	 */
	@Override
	public void login(String email, String password){
		_clientFactory.getLoginService().setLogin(email, password, new AsyncCallback<String>() {

			@Override
			public void onSuccess(String sessionId) {
				Log.debug("Login OK. SessionId: " + sessionId);
				setSessionId(sessionId);

				//Send User login event
				_clientFactory.getEventBus().fireEvent(new LoginChangeEvent(true));

				//Go to user Area
				_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(AccountPersistor.class, "You are logged in.", INFOTYPE.SUCCESS));

			}

			@Override
			public void onFailure(Throwable caught) {
				try {
					throw caught;
				} catch (WrongEmailOrPasswordException e) {
					Log.warn("Login problem: " + e);
					_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(AccountPersistor.class, "Your email and password is incorrect. Register or try again. ", INFOTYPE.ERROR));
				} catch (Throwable e) {
					Log.error("Unexpected error: " + e);
				}

			}
		});
	}

	/**
	 * This method checks the login status and will fire all necessary events
	 */
	@Override
	public void checkLogin(){

		//Start Account Initialisation
		//clientFactory.getAccountPersistor().getAddress();

		//Get Position if no one is saved in the cookies.
		/*
		if(getAddress()==null){
			_clientFactory.getEventBus().fireEvent(new WaitForAddressEvent());
		}
		*/

		//Test if user is logged in
		if(isLoggedIn()){
			Log.debug("User is loggedIn");
			_clientFactory.getEventBus().fireEvent(new LoginChangeEvent(true));
		}else{
			Log.debug("User is not loggedIn");
			_clientFactory.getEventBus().fireEvent(new LoginChangeEvent(false));
			checkFB();
		}

	}


	private void checkFB(){
		//Check current status via async
		_fbCore.getLoginStatus(new AsyncCallback<JavaScriptObject>() {

			@Override
			public void onSuccess(JavaScriptObject result) {
				Log.debug("Findout the facebook login status");
				//myHellow(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Log.error("Facebook login Problem");
			}
		});
	}

	@Override
	public void setReceiptDraft(Receipt draft) {
		Log.debug("set Receipt draft: ");
		_receipt=draft;
	}

	@Override
	public Receipt getReceiptDraft() {
		return _receipt;
	}
}
