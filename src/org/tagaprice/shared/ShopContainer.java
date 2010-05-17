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
public class ShopContainer implements EntityContainer {
	private static final long serialVersionUID = 1;
	
	private int id;
	private String name;
	private String imageSrc;
	private int progress; //In percent 0-100
	private int rating; //in percent 0-100
	private String street;
	private String city;
	private String country;
	private double lat;
	private double lng;
	
	
	/**
	 * 
	 * @param id
	 * @param name
	 * @param imageSrc
	 * @param progress
	 * @param rating
	 */
	public ShopContainer(
			int id,
			String name,
			String imageSrc,
			int progress,
			int rating) {
		this(id,name, imageSrc, progress, rating, null, null, null, 0.0, 0.0);
	}
	
	/**
	 * 
	 * @param id
	 * @param name
	 * @param imageSrc
	 * @param progress
	 * @param rating
	 * @param street
	 * @param city
	 * @param country
	 * @param lat
	 * @param lng
	 */
	public ShopContainer(
			int id,
			String name,
			String imageSrc,
			int progress,
			int rating,
			String street,
			String city,
			String country,
			double lat,
			double lng){
		
		setId(id);
		setName(name);
		setImageSrc(imageSrc);
		setProgress(progress);
		setRating(rating);
		setStreet(street);
		setCity(city);
		setCountry(country);
		setLat(lat);
		setLng(lng);
	}
	
	/**
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
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
	public String getStreet() {
		return street;
	}

	/**
	 * 
	 * @param street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * 
	 * @return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * 
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * 
	 * @return
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * 
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * 
	 * @return
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * 
	 * @param lat
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}

	/**
	 * 
	 * @return
	 */
	public double getLng() {
		return lng;
	}

	/**
	 * 
	 * @param lng
	 */
	public void setLng(double lng) {
		this.lng = lng;
	}
}
