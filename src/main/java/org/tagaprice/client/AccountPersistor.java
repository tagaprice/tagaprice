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
	private boolean isLoggedIn = false;

	/**
	 * Returns global Address
	 */
	public Address getAddress() {
		return I_ADDRESS;
	}

	/**
	 * Set Global Address. Saves it also in the cookies.
	 * @param address setGlobalAddress
	 */
	public void setAddress(Address address) {
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
		return isLoggedIn;
	}

	/**
	 * Logout user and remove sid
	 */
	public void logout() {
		isLoggedIn = false;
		Cookies.removeCookie("TAP_SID");
	}

	/**
	 * Log user in and set session id
	 * @param sessionId new SessionId
	 */
	public void setSessionId(String sessionId) {
		isLoggedIn = true;
		Cookies.setCookie("TAP_SID", sessionId);
	}
}
