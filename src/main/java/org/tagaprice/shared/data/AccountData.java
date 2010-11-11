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
package org.tagaprice.shared.data;

public class AccountData extends Entity {
	private static final long serialVersionUID = 1L;

	private String mail;
	private Address address;

	/**
	 * Default constructor needed for serialization
	 */
	public AccountData() {}

	/**
	 * constcructor used to query an account's current revision from the database
	 * (using AccountDAO) 
	 * @param id Account ID
	 */
	public AccountData(Long id) {
		super(id);
	}
	
	/**
	 * constructor used to query a specific revision from the DB
	 *(using AccountDAO)
	 * @param id Account ID
	 * @param rev account revision
	 */
	public AccountData(Long id, int rev) {
		super(id, rev);
	}

	/**
	 * constructor used to create a new Account
	 * @param title Account's descriptive name (e.g. "Administrator")
	 * @param localeId the Locale of an account just tells us which locale the user prefers
	 *     (a user isn't locked into a single local version of the site)
	 */
	public AccountData(String title, int localeId, String mail, Address address) {
		super(title, localeId, null);
		this.mail = mail;
		this.address = address;
	}

	/**
	 * constructor used to update an existing Account
	 * @param id Account ID
	 * @param rev current Account revision (will be checked by AccountDAO to detect concurrent storage requests)
	 * @param title Account's (new) descriptive name (e.g. "Administrator")
	 * @param revCreatorId revision creator (most times this will be the user himself, but sometimes admins will change a user's details)
	 */
	public AccountData(Long id, int rev, String title, Long revCreatorId, String mail, Address address) {
		super(id, rev, title, revCreatorId);
		this.mail = mail;
		this.address = address;
	}

	@Override
	public String getSerializeName() {
		return "account";
	}
	
	@Override
	public boolean equals(Object o) {
		boolean rc = true;
		if (o instanceof AccountData) {
			AccountData a = (AccountData) o;
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
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String getMail() {
		return mail;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public Address getAddress() {
		return address;
	}
}
