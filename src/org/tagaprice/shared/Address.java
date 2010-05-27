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
 * Filename: Address.java
 * Date: May 26, 2010
*/
package org.tagaprice.shared;

public class Address implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String street;
	private String city;
	private String country;
	private double lat;
	private double lng;
	
	
	@Override
	public String getSerializeName() {
		return "Address";
	}


	public Address(String street, String city, String country, double lat, double lng) {
		this.street = street;
		this.city = city;
		this.country = country;
		this.lat = lat;
		this.lng = lng;
	}


	public Address(String street, String city, String country) {
		this.street = street;
		this.city = city;
		this.country = country;
	}


	public Address(double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
	}


	public String getStreet() {
		return street;
	}


	public String getCity() {
		return city;
	}


	public String getCountry() {
		return country;
	}


	public double getLat() {
		return lat;
	}


	public double getLng() {
		return lng;
	}
	
	
	
	
}
