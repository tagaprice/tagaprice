package org.tagaprice.client;

import java.util.List;

import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.accountmanagement.User;
import org.tagaprice.shared.entities.receiptManagement.Receipt;

public interface IAccountPersistor {
	public void setClientFactory(ClientFactory clientFactory);

	/**
	 * Returns global Address
	 */
	//public Address getAddress();


	/**
	 * Set Global Address. Saves it also in the cookies.
	 * @param address setGlobalAddress
	 */
	//public void setAddress(Address address);

	/**
	 * @return true if user is logged in.
	 */
	public boolean isLoggedIn();

	/**
	 * Logout user and remove sid
	 */
	public void logout() ;


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

	public Address getCurAddress();
	
	public void setCurAddress(Address address);
	
	public List<Address> getAddressList();
	
	public void addAddress(Address address);
	
	/**
	 * 
	 * @return current logged in user or null
	 */
	public User getUser();
	
}
