/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: tagaprice
 * Filename: Account.java
 * Date: 21.07.2010
*/
package org.tagaprice.shared.entities;

/**
 * Represents an user account with address and email.
 */
public class Account extends Entity {
	private static final long serialVersionUID = 1L;

	private String _mail;
	private Address _address;

	/**
	 * Default constructor needed for serialization.
	 */
	public Account() {}

	/**
	 * Constructor used to query an account's current revision from the database.
	 * (using AccountDAO)
	 * @param id Account ID
	 */
	public Account(Long id) {
		super(id);
	}
	
	/**
	 * Constructor used to query a specific revision from the DB.
	 *(using AccountDAO)
	 * @param id Account ID
	 * @param rev account revision
	 */
	public Account(Long id, int rev) {
		super(id, rev);
	}

	/**
	 * Constructor used to create a new Account.
	 * @param title Account's descriptive name (e.g. "Administrator")
	 * @param localeId the preferred locale setting of this user
	 */
	public Account(String title, int localeId, String mail, Address address) {
		super(title, localeId, null);
		this._mail = mail;
		this._address = address;
	}

	/**
	 * Constructor used to update an existing Account.
	 * @param id Account ID
	 * @param rev current Account revision (will be checked by AccountDAO to detect concurrent storage requests)
	 * @param title Account's (new) descriptive name (e.g. "Administrator")
	 * @param revCreatorId revision creator (most times this will be the user himself, but sometimes admins will change a user's details)
	 */
	public Account(Long id, int rev, String title, Long revCreatorId, String mail, Address address) {
		super(id, rev, title, revCreatorId);
		this._mail = mail;
		this._address = address;
	}

	@Override
	public String getSerializeName() {
		return "account";
	}
	
	@Override
	public boolean equals(Object o) {
		boolean rc = true;
		if (o instanceof Account) {
			Account a = (Account) o;
			if (!super.equals(o)) rc = false;
			if (!_compare(getMail(), a.getMail())) rc = false;
		}
		else rc = false;
		return rc;
	}

	@Override
	public String toString() {
		return "Account {\n" +
				"mail: "+getMail()+
				"\naddress: "+getAddress()+"\n"+
				super.toString()+
				"}\n";
	}
	
	/**
	 * @param mail the email address of this account
	 */
	public void setMail(String mail) {
		this._mail = mail;
	}
	
	/**
	 * @return the email address of this account
	 */
	public String getMail() {
		return _mail;
	}
	
	/**
	 * @param address the address of this account
	 */
	public void setAddress(Address address) {
		this._address = address;
	}
	
	/**
	 * @return the address of this account
	 */
	public Address getAddress() {
		return _address;
	}

	@Override
	public <T extends Entity> T newRevision() {
		// TODO Auto-generated method stub
		return null;
	}
}
