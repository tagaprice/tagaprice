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

import java.util.ArrayList;

/**
 * Contains all important information to represent a
 * product. 
 *
 */
public class ProductData implements Entity{
	private static final long serialVersionUID = 1L;

	private long id;
	private long brandId;
	private long typeId;
	private String name;
	private String imageSrc;
	private int progress; //In percent 0-100
	private int rating; //in percent 0-100
	private Price price;
	private Quantity quantity;
	private boolean hasReceipt;
	private ArrayList<PropertyData> properties = new ArrayList<PropertyData>(); 
	
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
			int id,
			int brandId,
			int typeId,
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
			int id,
			int brandId,
			int typeId,
			String name,
			String imageSrc,
			int progress,
			int rating,
			Price price,
			Quantity quantity,
			boolean hasReceipt) {
		
		setId(id);
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
	 * 
	 * @return
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * 
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
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
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
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
	public ArrayList<PropertyData> getProperties() {
		return properties;
	}


	/**
	 * @param properties the properties to set
	 */
	public void setProperties(ArrayList<PropertyData> properties) {
		this.properties = properties;
	}
	
	
	
}
