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
package org.tagaprice.shared;

public class Account extends Entity {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor needed for serialization
	 */
	public Account() {}

	/**
	 * constcructor used to query an account's current revision from the database
	 * (using AccountDAO) 
	 * @param id Account ID
	 */
	public Account(Long id) {
		super(id);
	}
	
	/**
	 * constructor used to query a specific revision from the DB
	 *(using AccountDAO)
	 * @param id Account ID
	 * @param rev account revision
	 */
	public Account(Long id, int rev) {
		super(id, rev);
	}

	/**
	 * constructor used to create a new Account
	 * @param title Account's descriptive name (e.g. "Administrator")
	 * @param localeId the Locale of an account just tells us which locale the user prefers
	 *     (a user isn't locked into a single local version of the site)
	 */
	public Account(String title, int localeId) {
		super(title, localeId, null);
	}

	/**
	 * constructor used to update an existing Account
	 * @param id Account ID
	 * @param rev current Account revision (will be checked by AccountDAO to detect concurrent storage requests)
	 * @param title Account's (new) descriptive name (e.g. "Administrator")
	 * @param revCreatorId revision creator (most times this will be the user himself, but sometimes admins will change a user's details)
	 */
	public Account(Long id, int rev, String title, Long revCreatorId) {
		super(id, rev, title, revCreatorId);
	}

	@Override
	public String getSerializeName() {
		return "account";
	}
	
	@Override
	public boolean equals(Object o) {
		boolean rc = false;
		if (o instanceof Account) rc = super.equals(o);
		return rc;
	}

	@Override
	public String toString() {
		return "Account {\n" +
				super.toString()+
				"}\n";
	}
}
