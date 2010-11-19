/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License.
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
 */
package org.tagaprice.shared.entities;

/**
 * A {@link LocalAccount} represents an user account of TagAPrice.
 * Stores account data + password.
 */
public class LocalAccount extends Account {

	private static final long serialVersionUID = 1L;
	private String _password;

	/**
	 * default constructor (used for serialization)
	 */
	public LocalAccount() {
		super();
	}

	/**
	 * constructor for querying a {@link LocalAccount}'s current revision
	 * @param id user ID
	 */
	public LocalAccount(long id) {
		super(id);
	}

	/**
	 * query a specific LocalAccount revision
	 * @param id user ID
	 * @param rev revision to get
	 */
	public LocalAccount(long id, int rev) {
		super(id, rev);
	}


	/**
	 * constructor for creating a new {@link LocalAccount}
	 * @param title {@link Account}'s descriptive name (e.g. "Administrator")
	 * @param localeId the locale setting of this user
	 * @param creatorId creator of this revision
	 * @param mail email address of this {@link Account}
	 * @param password password of this {@link LocalAccount}
	 * @param address address of this {@link Account}
	 */
	public LocalAccount(
			String title,
			int localeId,
			Long creatorId,
			String mail,
			String password,
			Address address) {
		super(title, localeId, mail, address);

		_password = password;
	}

	/**
	 * @return the password for this {@link LocalAccount}
	 */
	public String getPassword() {
		return _password;
	}

	/**
	 * @param password the password for this {@link LocalAccount}
	 */
	public void setPassword(String password) {
		_password = password;
	}

	@Override
	public boolean equals(Object o) {
		boolean rc = true;

		if (o instanceof LocalAccount) {
			LocalAccount a = (LocalAccount) o;
			if (!super.equals(a)) rc = false;
		}
		else rc = false;

		return rc;
	}

	@Override
	public String getSerializeName() {
		return "localAccount";
	}
}
