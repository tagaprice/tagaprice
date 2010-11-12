package org.tagaprice.shared.data;

import org.tagaprice.shared.Serializable;

/**
 * Represents a country by code, title and localTitle.
 * This class is immutable.
 */
public class Country implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String code = null;
	private String title = null;
	private String localTitle = null;

	/**
	 * Default constructor needed for Serialization.
	 */
	public Country() {
		
	}
	
	/**
	 * Create a new Country representation
	 * TODO check this param assumptions and comment getters
	 * @param code country code (e.g. AT for Austria ?)
	 * @param title (english/international ?) title of this country (e.g. Austria)
	 * @param localTitle title in the users current locale setting ? (e.g. in local german: Deutschland) ?
	 */
	public Country(String code, String title, String localTitle) {
		this.code = code;
		this.title = title;
		this.localTitle = localTitle;
	}
	
	@Override
	public String getSerializeName() {
		return "country";
	}

	public String getCode() {
		return code;
	}
	
	public String getTitle() {
		return title;
	}
	
	
	public String getLocalTitle() {
		return localTitle;
	}
	
	@Override
	public boolean equals(Object o) {
		boolean rc = true;
		
		if (o instanceof Country) {
			Country c = (Country) o;
			
			if (!Entity._compare(getCode(), c.getCode())) rc = false;
			if (!Entity._compare(getTitle(), c.getTitle())) rc = false;
			if (!Entity._compare(getLocalTitle(), c.getLocalTitle())) rc = false;
		}
		else rc = false;
		
		return rc;
	}
}
