package org.tagaprice.client;

import org.tagaprice.shared.entities.Address;
import com.google.gwt.user.client.Cookies;

/**
 * This class holds all important information that we need to know at the start of tagaprice, or during surfing around.
 * 
 * 
 */
public class AccountPersistor {
	private Address I_ADDRESS;

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

		return new Address();
	}


	/**
	 * Set Global Address. Saves it also in the cookies.
	 * @param address setGlobalAddress
	 */
	public void setAddress(Address address) {
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
		if(Cookies.getCookie("TAP_SID")==null)
			return false;
		else
			return true;
	}

	/**
	 * Logout user and remove sid
	 */
	public void logout() {
		Cookies.removeCookie("TAP_SID");
	}

	/**
	 * Log user in and set session id
	 * @param sessionId new SessionId
	 */
	public void setSessionId(String sessionId) {
		Cookies.setCookie("TAP_SID", sessionId);
	}
}
