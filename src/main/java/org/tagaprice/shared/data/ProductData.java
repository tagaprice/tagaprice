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
package org.tagaprice.shared.data;

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
	private int progress = 0; //In percent 0-100
	private int rating = -1; //in percent 0-100, -1 means unrated
	private Price avgPrice;
	private Quantity quantity = null;
	private boolean hasReceipt;
	
	/**
	 * default constructor (used for serialization)
	 */
	public ProductData() {
		super();
	}
	
	/**
	 * constructor for querying a Product's current revision 
	 * @param id Product ID
	 */
	public ProductData(Long id) {
		super(id);
	}
	
	/**
	 * query a specific product revision (using ProductDAO)
	 * @param id Poduct ID
	 * @param rev revision
	 */
	public ProductData(Long id, int rev) {
		super(id, rev);
	}
	
	/**
	 * constructor for creating a new Product (using ProductDAO)
	 * @param title descriptive Product title (must not be empty or NULL)
	 * @param localeId Product locale
	 * @param creatorId Product's creator
	 * @param brandId Product Brand (may be null if not yet set)
	 * @param typeId ProductType (may be null if not yet set)
	 * @param imageSrc image file URL (may be null if not yet set)
	 * @param qty Product quantity (e.g. weight, volume, ...)
	 */
	public ProductData(String title, int localeId, long creatorId, Long brandId, Long typeId, String imageSrc, Quantity qty) {
		super(title, localeId, creatorId);
		this.brandId = brandId;
		this.typeId = typeId;
		this.imageSrc = imageSrc;
		this.quantity = qty;
	}
	
	/**
	 * constructor for saving an existing Product
	 * @param id Product ID
	 * @param rev last existing revision (will be checked by ProductDAO to detect concurrent storage attempts) 
	 * @param title (new) descriptive Product name
	 * @param creatorId revision's creator ID
	 * @param brandId (new) Brand ID (might be null)
	 * @param typeId (new) ProductType ID (might be null)
	 * @param imageSrc (new) image source URL (might be null)
	 * @param qty (new) quantity (e.g. weight) 
	 */
	public ProductData(long id, int rev, String title, long creatorId, Long brandId, Long typeId, String imageSrc, Quantity qty) {
		super(id, rev, title, creatorId);
		this.brandId = brandId;
		this.typeId = typeId;
		this.imageSrc = imageSrc;
		this.quantity = qty;
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
	 * @return product's average price
	 */
	public Price getAvgPrice() {
		return avgPrice;
	}
	
	/**
	 * @param price product's average price
	 */
	public void setAvgPrice(Price price) {
		this.avgPrice = price;
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
			if (!_compare(getBrandId(), p.getBrandId())) rc = false;
			if (!_compare(getTypeId(), p.getTypeId())) rc = false;
			if (!_compare(getImageSrc(), p.getImageSrc())) rc = false;
			if (getProgress() != p.getProgress()) rc = false;
			if (getRating() != p.getRating()) rc = false;
			if (!_compare(getAvgPrice(), p.getAvgPrice())) rc = false;
			if (!_compare(getQuantity(), p.getQuantity())) rc = false;
			if (hasReceipt() != p.hasReceipt()) rc = false;
		}
		else rc = false;
		
		return rc;
	}
	
	@Override
	public String toString() {
		return "Product {\n" +
				"brandId: " + getBrandId() +
				"\ntypeId: " + getTypeId() +
				"\nimageSrc: " + getImageSrc() +
				"\nprogress: " + + getProgress() +
				"\nrating: " + getRating() +
				"\navgPrice: " + getAvgPrice() +
				"\nquantity: " + getQuantity() +
				"\nhasReceipt: " + hasReceipt() + "\n" +
				super.toString() +
				"}\n";
	}
}
