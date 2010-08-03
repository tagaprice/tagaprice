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

	public Account(Long id, int rev) {
		super(id, rev, id, id);
		// TODO check if it would make sense to save creator or revCreator somewhere
		//    (e.g. to see if one of the admins changed some user-properties)
	}

	/**
	 * @param title
	 * @param localeId the Locale of an account just tells us which locale the user prefers
	 *     (a user isn't locked into a single local version of the site)
	 */
	public Account(String title, int localeId) {
		super(title, localeId);
	}

	public Account(Long id, int rev, String title, int localeId) {
		super(id, rev, title, localeId);
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

}
