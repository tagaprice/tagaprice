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
package org.tagaprice.shared.data;

import org.tagaprice.shared.Serializable;

public class Address implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String street;
	private String city;
	private Country country;
	private Double lat;
	private Double lng;
	
	
	@Override
	public String getSerializeName() {
		return "Address";
	}

	public Address() {
		// TODO Auto-generated constructor stub
	}

	public Address(String street, String city, Country country, Double lat, Double lng) {
		this.street = street;
		this.city = city;
		this.country = country;
		this.lat = lat;
		this.lng = lng;
	}


	public Address(String street, String city, Country country) {
		this.street = street;
		this.city = city;
		this.country = country;
	}


	public Address(Double lat, Double lng) {
		this.lat = lat;
		this.lng = lng;
	}


	public String getStreet() {
		return street;
	}


	public String getCity() {
		return city;
	}


	public Country getCountry() {
		return country;
	}


	public Double getLat() {
		return lat;
	}


	public Double getLng() {
		return lng;
	}
	
	
	
	public void setStreet(String street) {
		this.street = street;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public void setCoordinates(Double lat, Double lng) {
		this.lat = lat;
		this.lng = lng;
	}
	
	public void setAddress(String street, String city, Country country) {
		setStreet(street);
		setCity(city);
		setCountry(country);
	}
	
	public boolean equals(Object o) {
		boolean rc = true;
		
		if (o instanceof Address) {
			Address a = (Address) o;
			
			if (!Entity._compare(getStreet(), a.getStreet())) rc = false;
			if (!Entity._compare(getCity(), a.getCity())) rc = false;
			if (!Entity._compare(getCountry(), a.getCountry())) rc = false;
			if (!Entity._compare(getLat(), a.getLat())) rc = false;
			if (!Entity._compare(getLng(), a.getLng())) rc = false;
		}
		else rc = false;
		
		return rc;
	}
}
