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
 * Filename: Currency.java
 * Date: 19.05.2010
*/
package org.tagaprice.shared.entities;

public class Currency extends Entity {
	private static final long serialVersionUID = 1L;

	/**
	 * default constructor (required for serialization)
	 */
	public Currency() {
		super();
	}
	
	/**
	 * constructor used to query the current revision of an existing Currency
	 * @param id Currency ID
	 */
	public Currency(long id) {
		super(id);
	}
	
	/**
	 * constructor used to query a specific revision of an existing Currency
	 * @param id Currency ID
	 * @param rev revision
	 */
	public Currency(long id, int rev) {
		super(id, rev);
	}

	/**
	 * constructor used to create a new Currency
	 * @param title descriptive Currency name
	 * @param localeId Locale
	 * @param creatorId Currency's creator 
	 */
	public Currency(String title, int localeId, long creatorId) {
		super(title, localeId, creatorId);
	}
	
	/**
	 * constructor used to save an existing Currency
	 * @param id Currency ID
	 * @param rev last existing revision (will be checked by CurrencyDAO to detect concurrent storage requests)
	 * @param title descriptive Currency name
	 * @param creatorId revision's creator
	 */
	public Currency(long id, int rev, String title, long creatorId) {
		super(id, rev, title, creatorId);
	}

	@Override
	public String getSerializeName() {
		return "currency";
	}
	
	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}
}
