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
 * Filename: Locale.java
 * Date: 15.06.2010
*/
package org.tagaprice.shared.data;

import org.tagaprice.shared.Serializable;

/**
 * Represents a locale setting.
 */
public class Locale implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private Integer fallbackId;
	private String title;
	private String localTitle;

	/**
	 * Default constructor needed for serialization
	 */
	public Locale() {
		this(-1, -1, null, null);
	}
	
	/**
	 * Consturctor used by the DAO to create a locale.
	 * @param id ID of this locale
	 * @param fallbackId fallback ID
	 * @param title title of this locale
	 * @param localTitle localized title of this locale
	 */
	public Locale(int id, Integer fallbackId, String title, String localTitle) {
		this.id = id;
		this.fallbackId = fallbackId;
		this.title = title;
		this.localTitle = localTitle;
	}
	
	/**
	 * @return ID of this locale
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @return ID of the fallback of this locale
	 */
	public Integer getFallbackId() {
		return fallbackId;
	}
	
	/**
	 * @return title of this locale
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @return loclalized title of this locale
	 */
	public String getLocalTitle() {
		return localTitle;
	}
	
	@Override
	public String getSerializeName() {
		return "locale";
	}
	
	@Override
	public boolean equals(Object o) {
		boolean rc = true;
		if (o instanceof Locale) {
			Locale l = (Locale) o;
			if (l.getId() != getId()) rc = false;
			
			if (getFallbackId() != null) {
				if (!getFallbackId().equals(l.getFallbackId())) rc = false;
			}
			else if (l.getFallbackId() != null) rc = false;
			
			if (getTitle() != null) {
				if (!getTitle().equals(l.getTitle())) rc = false;
			}
			else if (l.getTitle() != null) rc = false;
			
			if (getLocalTitle() != null) {
				if (!getLocalTitle().equals(l.getLocalTitle())) rc = false;
			}
			else if (l.getLocalTitle() != null) rc = false;
		}
		else rc = false;
		
		return rc;
	}

}
