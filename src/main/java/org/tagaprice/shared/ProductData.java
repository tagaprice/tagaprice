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
 * Filename: ProductPreviewContainer.java
 * Date: 14.05.2010
*/
package org.tagaprice.shared;

/**
 * Contains all important information to represent a
 * product. 
 *
 */
public class ProductData extends Entity {
	private static final long serialVersionUID = 1L;

	private Long brandId; // TODO create a Brand class
	private Long typeId;
	private String imageSrc;
	private int progress; //In percent 0-100
	private int rating; //in percent 0-100
	private Price price;
	private Quantity quantity;
	private boolean hasReceipt;
	
	public ProductData() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	/**
	 * 
	 * @param id
	 * @param brandId
	 * @param typeId
	 * @param title
	 * @param imageSrc
	 * @param progress
	 * @param rating
	 * @param price
	 * @param quantitiy
	 */
	public ProductData(
			Long id,
			int rev,
			String title,
			int localeId,
			Long brandId,
			Long typeId,
			String imageSrc,
			int progress,
			int rating,
			Price price,
			Quantity quantity) {
		
		this(
				id,
				rev,
				title,
				localeId,
				brandId,
				typeId,
				imageSrc,
				progress,
				rating,
				price,
				quantity,
				true);
	}
	

	/**
	 * 
	 * @param id
	 * @param brandId
	 * @param typeId
	 * @param title
	 * @param imageSrc
	 * @param progress
	 * @param rating
	 * @param price
	 * @param quantitiy
	 * @param hasReceipt
	 */
	public ProductData(
			Long id,
			int rev,
			String title,
			int localeId,
			Long brandId,
			Long typeId,
			String imageSrc,
			int progress,
			int rating,
			Price price,
			Quantity quantity,
			boolean hasReceipt) {
		
		super(id, rev, title, localeId);
		setBrandId(brandId);
		setTypeId(typeId);
		setImageSrc(imageSrc);
		setPrice(price);
		setRating(rating);
		setQuantity(quantity);
		setHasReceipt(hasReceipt);
	}

	/**
	 * @return the brandId
	 */
	public Long getBrandId() {
		return brandId;
	}


	/**
	 * @param brandId the brandId to set
	 */
	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}


	/**
	 * @return the typeId
	 */
	public Long getTypeId() {
		return typeId;
	}


	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	/**
	 * 
	 * @return
	 */
	public String getImageSrc(){
		return imageSrc;
	}	
	
	/**
	 * 
	 * @param imageSrc
	 */
	public void setImageSrc(String imageSrc){
		this.imageSrc=imageSrc;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getProgress(){
		return progress;
	}
	
	/**
	 * 
	 * @param progress
	 */
	public void setProgress(int progress){
		this.progress=progress;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getRating(){
		return rating;
	}
	
	/**
	 * 
	 * @param rating
	 */
	public void setRating(int rating){
		this.rating=rating;
	}
	
	/**
	 * 
	 * @return
	 */
	public Price getPrice() {
		return price;
	}
	
	/**
	 * 
	 * @param price
	 */
	public void setPrice(Price price) {
		this.price = price;
	}
	
	/**
	 * 
	 * @return
	 */
	public Quantity getQuantity() {
		return quantity;
	}
	
	/**
	 * 
	 * @param quantitiy
	 */
	public void setQuantity(Quantity quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean hasReceipt() {
		return hasReceipt;
	}
	
	/**
	 * 
	 * @param hasReceipt
	 */
	public void setHasReceipt(boolean hasReceipt) {
		this.hasReceipt = hasReceipt;
	}

	@Override
	public String getSerializeName() {
		return "product";
	}
	
	@Override
	public boolean equals(Object o) {
		boolean rc = super.equals(o);

		if (rc && o instanceof ProductData) {
			ProductData p = (ProductData) o;
			if (getBrandId() != p.getBrandId()) rc = false;
			if (getTypeId() != p.getTypeId()) rc = false;
			if (getImageSrc() == null) {
				if (p.getImageSrc() != null) rc = false;
			}
			if (getProgress() != p.getProgress()) rc = false;
			if (getRating() != p.getRating()) rc = false;
			if (getPrice() == null) {
				if (p.getPrice() != null) rc = false;
			}
			else if (!getPrice().equals(p.getPrice())) rc = false;
			if (getQuantity() == null) {
				if (p.getQuantity() != null) rc = false;
			}
			else if (!getQuantity().equals(p.getQuantity())) rc = false;
			if (hasReceipt() != p.hasReceipt()) rc = false;
		}
		else rc = false;
		
		return rc;
	}
}
