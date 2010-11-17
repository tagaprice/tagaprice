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
package org.tagaprice.shared.entities;


/**
 * Contains all important information to represent a product. 
 */
public class Product extends Entity {
	private static final long serialVersionUID = 1L;

	/** TODO create a Brand class */
	private Long brandId;
	private Long typeId;
	private String imageSrc;
	/** In percent 0-100 */
	private int progress = 0;
	/** in percent 0-100, -1 means unrated */
	private int rating = -1;
	private Price avgPrice;
	private Quantity quantity = null;
	private boolean hasReceipt;
	
	/**
	 * default constructor (used for serialization)
	 */
	public Product() {
		super();
	}
	
	/**
	 * constructor for querying a Product's current revision 
	 * @param id Product ID
	 */
	public Product(Long id) {
		super(id);
	}
	
	/**
	 * query a specific product revision
	 * @param id Poduct ID
	 * @param rev revision to query
	 */
	public Product(Long id, int rev) {
		super(id, rev);
	}
	
	/**
	 * constructor for creating a new Product
	 * @param title descriptive Product title (must not be empty or NULL)
	 * @param localeId Product locale
	 * @param creatorId Product's creator
	 * @param brandId Product Brand (may be null if not yet set)
	 * @param typeId ProductType (may be null if not yet set)
	 * @param imageSrc image file URL (may be null if not yet set)
	 * @param qantity Product quantity (e.g. weight, volume, ...)
	 */
	public Product(String title, int localeId, long creatorId, Long brandId, Long typeId, String imageSrc, Quantity qantity) {
		super(title, localeId, creatorId);
		this.brandId = brandId;
		this.typeId = typeId;
		this.imageSrc = imageSrc;
		this.quantity = qantity;
	}
	
	/**
	 * constructor for saving an existing Product
	 * @param id Product ID
	 * @param rev last existing revision (e.g. the revision this version is based on)
	 * @param title (new) descriptive Product name
	 * @param creatorId revision's creator ID
	 * @param brandId (new) Brand ID (might be null)
	 * @param typeId (new) ProductType ID (might be null)
	 * @param imageSrc (new) image source URL (might be null)
	 * @param qantity (new) quantity (e.g. weight) 
	 */
	public Product(long id, int rev, String title, long creatorId, Long brandId, Long typeId, String imageSrc, Quantity qantity) {
		super(id, rev, title, creatorId);
		this.brandId = brandId;
		this.typeId = typeId;
		this.imageSrc = imageSrc;
		this.quantity = qantity;
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
	 * @return the image URL
	 */
	public String getImageSrc(){
		return imageSrc;
	}	
	
	/**
	 * 
	 * @param imageSrc the image URL
	 */
	public void setImageSrc(String imageSrc){
		this.imageSrc=imageSrc;
	}
	
	/**
	 * 
	 * @return completeness of the collected information about this {@link Product} in percent.
	 * If aprox. all data about this Product is known, this will return 100.
	 */
	public int getProgress(){
		return progress;
	}
	
	/**
	 * 
	 * @param progress completeness of the collected information about this product, in percent
	 * (valid values in the range of 1 - 100)
	 */
	public void setProgress(int progress){
		this.progress=progress;
	}
	
	/**
	 * 
	 * @return the rating of this product
	 */
	public int getRating(){
		return rating;
	}
	
	/**
	 * 
	 * @param rating the rating of this product
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
	 * @return {@link Quantity} of this product
	 */
	public Quantity getQuantity() {
		return quantity;
	}
	
	/**
	 * 
	 * @param quantitiy {@link Quantity} of this product
	 */
	public void setQuantity(Quantity quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * 
	 * @return true, if a receipt exists for this product
	 */
	public boolean hasReceipt() {
		return hasReceipt;
	}
	
	/**
	 * 
	 * @param hasReceipt true, if a receipt exists for this product
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

		if (rc && o instanceof Product) {
			Product p = (Product) o;
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
