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
package org.tagaprice.shared.entities;

import org.tagaprice.shared.ISerializable;

/**
 * Represents a locale setting.
 * It holds the following information:
 * - An ID to represent this {@link Locale} in the database
 * - A fallbackID if this {@link Locale} is not complete
 * - A title, representing this {@link Locale} with an international/English title
 * - A localized title, representing this {@link Locale} as spelled in the {@link Locale} language.
 */
public class Locale implements ISerializable {
	private static final long serialVersionUID = 1L;

	private int _id;
	private Integer _fallbackId;
	private String _title;
	private String _localTitle;

	/**
	 * Default constructor needed for serialization
	 */
	public Locale() {
		this(-1, -1, null, null);
	}

	/**
	 * Constructor used by the DAO to create a {@link Locale}.
	 * @param id ID of this {@link Locale}
	 * @param fallbackId fallback ID
	 * @param title title of this {@link Locale}
	 * @param localTitle localized title of this {@link Locale}
	 */
	public Locale(int id, Integer fallbackId, String title, String localTitle) {
		_id = id;
		_fallbackId = fallbackId;
		_title = title;
		_localTitle = localTitle;
	}

	/**
	 * @return ID of this {@link Locale}
	 */
	public int getId() {
		return _id;
	}

	/**
	 * @return ID of the fallback of this {@link Locale}
	 */
	public Integer getFallbackId() {
		return _fallbackId;
	}

	/**
	 * @return title of this {@link Locale}
	 */
	public String getTitle() {
		return _title;
	}

	/**
	 * @return loclalized title of this {@link Locale}
	 */
	public String getLocalTitle() {
		return _localTitle;
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
