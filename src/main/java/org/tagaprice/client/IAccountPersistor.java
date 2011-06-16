package org.tagaprice.client;

import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.receiptManagement.Receipt;

public interface IAccountPersistor {
	public void setClientFactory(ClientFactory clientFactory);

	public String getSessionId();

	/**
	 * Returns global Address
	 */
	public Address getAddress();


	/**
	 * Set Global Address. Saves it also in the cookies.
	 * @param address setGlobalAddress
	 */
	public void setAddress(Address address);

	/**
	 * @return true if user is logged in.
	 */
	public boolean isLoggedIn();

	/**
	 * Logout user and remove sid
	 */
	public void logout() ;

	/**
	 * Log user in and set session id
	 * @param sessionId new SessionId
	 */
	public void setSessionId(String sessionId);

	/**
	 * Login and set SessionId
	 * @param email email
	 * @param password Password
	 */
	public void login(String email, String password);

	/**
	 * This method checks the login status and will fire all necessary events
	 */
	public void checkLogin();

	/**
	 * Set a receipt draft. (only one i possible)
	 * @param draft
	 */
	public void setReceiptDraft(Receipt draft);

	/**
	 * Returns the receipt draft or null
	 * @return receipt draft or null
	 */
	public Receipt getReceiptDraft();

}
