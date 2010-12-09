package org.tagaprice.shared.enums;

import org.tagaprice.shared.ISerializable;

public enum SearchType implements ISerializable {

	/**
	 * Search for everything in the db.
	 */
	ALL,
	/**
	 * Will only search for products
	 */
	PRODCUT,

	/**
	 * Will only search for shops
	 */
	SHOP;

	@Override
	public String getSerializeName() {
		return "org.tagaprice.client.widgets.SearchWidget";
	}
}