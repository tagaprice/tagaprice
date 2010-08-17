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
package org.tagaprice.shared;


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
	private int rating; //in percent 0-100
	private Address address;
	
	public ShopData() {
		super();
	}
	
	/**
	 * 
	 * @param id
	 * @param name
	 * @param imageSrc
	 * @param progress
	 * @param rating
	 */
	public ShopData(
			long id, 
			int rev, 
			String title, 
			int localeId, 
			Long typeId,
			String imageSrc, 
			int progress, 
			int rating) {
		this(
				id, 
				rev, 
				title, 
				localeId, 
				typeId,
				imageSrc, 
				progress, 
				rating, 
				null);
	}
	
	/**
	 * 
	 * @param id
	 * @param imageSrc
	 * @param progress
	 * @param rating
	 * @param street
	 * @param city
	 * @param country
	 */
	public ShopData(
			long id, 
			int rev, 
			String title, 
			int localeId, 
			Long typeId,
			String imageSrc, 
			int progress, 
			int rating, 
			Address address){
		super(id, rev, title, localeId);
		setImageSrc(imageSrc);
		setProgress(progress);
		setRating(rating);
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
	public double getLat() {
		return address.getLat();
	}

	

	/**
	 * 
	 * @return
	 */
	public double getLng() {
		return address.getLng();
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
	
	// TODO implement missing ShopData.equals()
}
