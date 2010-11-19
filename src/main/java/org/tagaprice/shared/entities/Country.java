package org.tagaprice.shared.entities;

import org.tagaprice.shared.ISerializable;

/**
 * Represents a country by code, title and localTitle.
 * This class is immutable.
 */
public class Country implements ISerializable {
	private static final long serialVersionUID = 1L;

	private String _code = null;
	private String _title = null;
	private String _localTitle = null;

	/**
	 * Default constructor needed for Serialization.
	 */
	public Country() {

	}

	/**
	 * Create a new Country representation
	 * 
	 * @param code
	 *            country code (e.g. "at" for Austria)
	 * @param title
	 *            (English/international) title of this country (e.g. "Austria")
	 * @param localTitle
	 *            title in the users current locale setting (e.g. in local german: "Deutschland")
	 */
	public Country(String code, String title, String localTitle) {
		_code = code;
		_title = title;
		_localTitle = localTitle;
	}

	@Override
	public String getSerializeName() {
		return "country";
	}

	/**
	 * @return the country code (e.g. "at" for Austria)
	 */
	public String getCode() {
		return _code;
	}

	/**
	 * @return (English/international) title of this country (e.g. "Austria")
	 */
	public String getTitle() {
		return _title;
	}

	/**
	 * 
	 * @return title in the users current locale setting (e.g. in local german: "Deutschland")
	 */
	public String getLocalTitle() {
		return _localTitle;
	}

	@Override
	public boolean equals(Object o) {
		boolean areEqual = true;

		if (o instanceof Country) {
			Country c = (Country) o;

			if (!Entity._compare(getCode(), c.getCode()))
				areEqual = false;
			if (!Entity._compare(getTitle(), c.getTitle()))
				areEqual = false;
			if (!Entity._compare(getLocalTitle(), c.getLocalTitle()))
				areEqual = false;
		} else
			areEqual = false;

		return areEqual;
	}
}
