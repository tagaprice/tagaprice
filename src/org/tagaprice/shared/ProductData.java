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

	private long brandId; // TODO create a Brand class
	private long typeId;
	private String imageSrc;
	private int progress; //In percent 0-100
	private int rating; //in percent 0-100
	private Price price;
	private Quantity quantity;
	private boolean hasReceipt;
	private SearchResult<PropertyData> properties = new SearchResult<PropertyData>(); 
	
	public ProductData() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	/**
	 * 
	 * @param id
	 * @param brandId
	 * @param typeId
	 * @param name
	 * @param imageSrc
	 * @param progress
	 * @param rating
	 * @param price
	 * @param quantitiy
	 */
	public ProductData(
			long id,
			long brandId,
			long typeId,
			String name,
			String imageSrc,
			int progress,
			int rating,
			Price price,
			Quantity quantity) {
		
		this(
				id,
				brandId,
				typeId,
				name,
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
	 * @param name
	 * @param imageSrc
	 * @param progress
	 * @param rating
	 * @param price
	 * @param quantitiy
	 * @param hasReceipt
	 */
	public ProductData(
			long id,
			long brandId,
			long typeId,
			String name,
			String imageSrc,
			int progress,
			int rating,
			Price price,
			Quantity quantity,
			boolean hasReceipt) {
		
		super(id);
		setBrandId(brandId);
		setTypeId(typeId);
		setName(name);
		setImageSrc(imageSrc);
		setPrice(price);
		setRating(rating);
		setQuantity(quantity);
		setHasReceipt(hasReceipt);
		
	}

	/**
	 * @return the brandId
	 */
	public long getBrandId() {
		return brandId;
	}


	/**
	 * @param brandId the brandId to set
	 */
	public void setBrandId(long brandId) {
		this.brandId = brandId;
	}


	/**
	 * @return the typeId
	 */
	public long getTypeId() {
		return typeId;
	}


	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(long typeId) {
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


	/**
	 * @return the properties
	 */
	public SearchResult<PropertyData> getProperties() {
		return properties;
	}


	/**
	 * @param properties the properties to set
	 */
	public void setProperties(SearchResult<PropertyData> properties) {
		this.properties = properties;
	}


	@Override
	public String getSerializeName() {
		return "product";
	}
	
	@Override
	public boolean equals(Object o) {
		boolean rc = true;

		if (o instanceof ProductData) {
			ProductData p = (ProductData) o;
			if (getId() != p.getId()) rc = false;
			if (getBrandId() != p.getBrandId()) rc = false;
			if (getTypeId() != p.getTypeId()) rc = false;
			if (getName() == null) {
				if (p.getName() != null) rc = false;
			}
			else if (!getName().equals(p.getName())) rc = false;
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
