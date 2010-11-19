/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License.
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
 */

/**
 * Project: TagAPriceUI
 * Filename: ShopPreviewContainer.java
 * Date: 16.05.2010
 */
package org.tagaprice.shared.entities;


/**
 * A {@link Shop} represents a shop where products can be bought.
 * It has the following properties:
 * - A {@link Category}
 * - An image (represented as an URL)
 * - An {@link Address} representing the location of the shop.
 * 
 * Further properties:
 * - A rating in percent (-1 meaning unrated)
 * - A progress meter representing the completeness of the collected information about this {@link SHop} in percent.
 */
public class Shop extends Entity {
	private static final long serialVersionUID = 1;

	/** ID of the category for this {@link Shop} */
	private Long _categoryId;
	private String _imageSrc;
	/** In percent 0-100 */
	private int _progress;
	/** in percent 0-100, -1 means unrated */
	private int _rating;
	private Address _address = new Address();

	/**
	 * Default constructor needed for serialization.
	 */
	public Shop() {
		super();
	}

	/**
	 * Constructor for querying a shop's current revision.
	 * @param id Shop ID
	 */
	public Shop(long id) {
		super(id);
	}

	/**
	 * Constructor for querying a specific shop revision.
	 * @param id Shop ID
	 * @param rev Shop revision
	 */
	public Shop(long id, int rev) {
		super(id, rev);
	}

	/**
	 * Constructor for saving a new Shop
	 * @param title descriptive Shop Name (must not be empty)
	 * @param localeId shop's locale ID
	 * @param creatorId shop's creator
	 * @param typeId shop type (may be null)
	 * @param imageSrc shop photo or logo URL (may be null)
	 * @param address shop address (may be null)
	 */
	public Shop(String title, int localeId, long creatorId, Long typeId, String imageSrc, Address address) {
		super(title, localeId, creatorId);
		_categoryId = typeId;
		_imageSrc = imageSrc;
		_address = address;
	}

	/**
	 * Constructor for saving changes of an existing {@link Shop}.
	 * @param id Shop ID
	 * @param rev Shop revision
	 * @param title descriptive Shop Name (must not be empty)
	 * @param localeId shop's locale ID
	 * @param creatorId shop's creator
	 * @param typeId shop type (may be null)
	 * @param imageSrc shop photo or logo URL (may be null)
	 * @param address shop address (may be null)
	 */
	public Shop(long id, int rev, String title, long creatorId, Long typeId, String imageSrc, Address address) {
		super(id, rev, title, creatorId);
		_categoryId = typeId;
		_imageSrc = imageSrc;
		_address = address;
	}

	/**
	 * @return the URL of the image of this {@link Shop}
	 */
	public String getImageSrc() {
		return _imageSrc;
	}

	/**
	 * 
	 * @param imageSrc URL of the image of this {@link Shop}
	 */
	public void setImageSrc(String imageSrc) {
		_imageSrc = imageSrc;
	}

	/**
	 * @return completeness of the collected information about this {@link SHop} in percent.
	 * If aprox. all data about this Product is known, this will return 100.
	 */
	public int getProgress() {
		return _progress;
	}

	/**
	 * 
	 * @param progress completeness of the collected information about this {@link Shop}, in percent
	 * (valid values in the range of 1 - 100)
	 */
	public void setProgress(int progress) {
		_progress = progress;
	}

	/**
	 * @return rating of this {@link Shop}
	 */
	public int getRating() {
		return _rating;
	}

	/**
	 * 
	 * @param rating rating of this {@link Shop}
	 */
	public void setRating(int rating) {
		_rating = rating;
	}

	/**
	 * @return {@link Address} of this {@link Shop}
	 */
	public Address getAddress() {
		return _address;
	}

	/**
	 * @param address {@link Address} of this {@link Shop}
	 */
	public void setAddress(Address address) {
		_address = address;
	}

	/**
	 * @return the latitude
	 * @deprecated use getAddress instead ?
	 */
	@Deprecated
	public Double getLat() {
		return _address != null ? _address.getLatitude() : null;
	}

	/**
	 * @return the longitude
	 * @deprecated use getAddress instead ?
	 */
	@Deprecated
	public Double getLng() {
		return _address != null ? _address.getLongitude() : null;
	}

	/**
	 * @return the ID of the type of this {@link Shop}
	 */
	public Long getTypeId() {
		return _categoryId;
	}

	/**
	 * @param typeId ID of the type of this {@link Shop}
	 */
	public void setTypeId(Long typeId) {
		_categoryId = typeId;
	}


	@Override
	public String getSerializeName() {
		return "shop";
	}

	@Override
	public boolean equals(Object o) {
		boolean rc = true;

		if (o instanceof Shop) {
			Shop s = (Shop) o;

			if (!super.equals(s)) rc = false;
			if (!Entity._compare(getTypeId(), s.getTypeId())) rc = false;
			if (!Entity._compare(getImageSrc(), s.getImageSrc())) rc = false;
			if (getProgress() != s.getProgress()) rc = false;
			if (getRating() != s.getRating()) rc = false;
			if (!Entity._compare(getAddress(), s.getAddress())) rc = false;
		}
		else rc = false;

		return rc;
	}

	/**
	 * TODO implement this function (newRevision)
	 */
	@Override
	public <T extends Entity> T newRevision() {
		// TODO Auto-generated method stub
		return null;
	}
}
