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
 * Contains all important information to represent a shop.
 */
public class Shop extends Entity {
	private static final long serialVersionUID = 1;
	
	/** ID of the type for this {@link Shop} */
	private Long typeId;
	private String imageSrc;
	/** In percent 0-100 */
	private int progress;
	/** in percent 0-100, -1 means unrated */
	private int rating;
	private Address address = new Address();
	
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
		this.typeId = typeId;
		this.imageSrc = imageSrc;
		this.address = address;
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
		this.typeId = typeId;
		this.imageSrc = imageSrc;
		this.address = address;
	}
	
	/**
	 * @return the URL of the image of this {@link Shop}
	 */
	public String getImageSrc() {
		return imageSrc;
	}

	/**
	 * 
	 * @param imageSrc URL of the image of this {@link Shop}
	 */
	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	/**
	 * @return completeness of the collected information about this {@link SHop} in percent.
	 * If aprox. all data about this Product is known, this will return 100.
	 */
	public int getProgress() {
		return progress;
	}

	/**
	 * 
	 * @param progress completeness of the collected information about this {@link Shop}, in percent
	 * (valid values in the range of 1 - 100)
	 */
	public void setProgress(int progress) {
		this.progress = progress;
	}

	/**
	 * @return rating of this {@link Shop}
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * 
	 * @param rating rating of this {@link Shop}
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * @return {@link Address} of this {@link Shop}
	 */
	public Address getAddress() {
		return address;
	}
	
	/**
	 * @param address {@link Address} of this {@link Shop}
	 */
	public void setAddress(Address address) {
		this.address = address;
	}
	
	/**
	 * @return the latitude
	 * @deprecated use getAddress instead ?
	 */
	public Double getLat() {
		return address != null ? address.getLat() : null;
	}

	/**
	 * @return the longitude
	 * @deprecated use getAddress instead ?
	 */
	public Double getLng() {
		return address != null ? address.getLng() : null;
	}

	/**
	 * @return the ID of the type of this {@link Shop}
	 */
	public Long getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId ID of the type of this {@link Shop}
	 */
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
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
			if (!_compare(getTypeId(), s.getTypeId())) rc = false;
			if (!_compare(getImageSrc(), s.getImageSrc())) rc = false;
			if (getProgress() != s.getProgress()) rc = false;
			if (getRating() != s.getRating()) rc = false;
			if (!_compare(getAddress(), s.getAddress())) rc = false;
		}
		else rc = false;
		
		return rc;
	}
}
