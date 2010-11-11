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
package org.tagaprice.shared.data;


/**
 * 
 * Contains all important information to represent a
 * shop. 
 *
 */
public class ShopData extends Entity {
	private static final long serialVersionUID = 1;
	
	private Long typeId;
	private String imageSrc;
	private int progress; //In percent 0-100
	private int rating; //in percent 0-100, -1 means unrated
	private Address address = new Address();
	
	/**
	 * default constructor
	 */
	public ShopData() {
		super();
	}
	
	/**
	 * constructor for querying a shop's current revision (using ShopDAO) 
	 * @param id Shop ID
	 */
	public ShopData(long id) {
		super(id);
	}
	
	/**
	 * constructor for querying a specific shop revision (using ShopDAO) 
	 * @param id Shop ID
	 * @param rev Shop revision
	 */
	public ShopData(long id, int rev) {
		super(id, rev);
	}

	/**
	 * constructor for saving a new Shop (using ShopDAO)
	 * @param title descriptive Shop Name (must not be empty)
	 * @param localeId shop's locale ID
	 * @param creatorId shop's creator
	 * @param typeId shop type (may be null)
	 * @param imageSrc shop photo or logo URL (may be null)
	 * @param address shop address (may be null)
	 */
	public ShopData(String title, int localeId, long creatorId, Long typeId, String imageSrc, Address address) {
		super(title, localeId, creatorId);
		this.typeId = typeId;
		this.imageSrc = imageSrc;
		this.address = address;
	}
	
	public ShopData(long id, int rev, String title, long creatorId, Long typeId, String imageSrc, Address address) {
		super(id, rev, title, creatorId);
		this.typeId = typeId;
		this.imageSrc = imageSrc;
		this.address = address;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getImageSrc() {
		return imageSrc;
	}

	/**
	 * 
	 * @param imageSrc
	 */
	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	/**
	 * 
	 * @return
	 */
	public int getProgress() {
		return progress;
	}

	/**
	 * 
	 * @param progress
	 */
	public void setProgress(int progress) {
		this.progress = progress;
	}

	/**
	 * 
	 * @return
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * 
	 * @param rating
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * 
	 * @return
	 */
	public Address getAddress() {
		return address;
	}
	
	/**
	 * 
	 * @return
	 */
	public Double getLat() {
		return address != null ? address.getLat() : null;
	}

	/**
	 * 
	 * @return
	 */
	public Double getLng() {
		return address != null ? address.getLng() : null;
	}

	@Override
	public String getSerializeName() {
		return "shop";
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public boolean equals(Object o) {
		boolean rc = true;
		
		if (o instanceof ShopData) {
			ShopData s = (ShopData) o;
			
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
