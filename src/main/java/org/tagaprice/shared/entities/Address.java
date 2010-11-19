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
package org.tagaprice.shared.entities;

import org.tagaprice.shared.ISerializable;

/**
 * Holds data about an address.
 * An address can be represented as combination of street, city and country and/or
 * as a combination of latitude and longitude.
 * 
 * Fields that are not explicitly set are null (no lookup or likewise is conducted).
 * 
 * TODO this is currently not saved in the database. save it.
 */
public class Address implements ISerializable {

	private static final long serialVersionUID = 1L;
	private String _street;
	private String _city;
	private Country _country;
	private Double _latitude;
	private Double _longitude;


	@Override
	public String getSerializeName() {
		return "Address";
	}

	/**
	 * Default constructor needed for serialization.
	 */
	public Address() {
	}

	/**
	 * Constructor to represent an {@link Address} by coordinates and street/city/country.
	 * 
	 * @param street
	 *            street of the {@link Address}
	 * @param city
	 *            city of the {@link Address}
	 * @param country
	 *            country of the {@link Address}
	 * @param latitude
	 *            latitude of the position of the {@link Address}
	 * @param longitude
	 *            longitude of the position of the {@link Address}
	 */
	public Address(String street, String city, Country country, Double latitude, Double longitude) {
		_street = street;
		_city = city;
		_country = country;
		_latitude = latitude;
		_longitude = longitude;
	}

	/**
	 * Constructor to represent an {@link Address} bystreet/city/country.
	 * 
	 * @param street
	 *            street of the {@link Address}
	 * @param city
	 *            city of the {@link Address}
	 * @param country
	 *            country of the {@link Address}
	 */
	public Address(String street, String city, Country country) {
		_street = street;
		_city = city;
		_country = country;
	}

	/**
	 * Constructor to represent an {@link Address} by coordinates.
	 * 
	 * @param latitude
	 *            latitude of the position of the {@link Address}
	 * @param longitude
	 *            longitude of the position of the {@link Address}
	 */
	public Address(Double lat, Double lng) {
		_latitude = lat;
		_longitude = lng;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return _street;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return _city;
	}

	/**
	 * @return the country
	 */
	public Country getCountry() {
		return _country;
	}

	/**
	 * @return latitude of the coordinates of this {@link Address}
	 */
	public Double getLatitude() {
		return _latitude;
	}

	/**
	 * @return longitude of the coordinates of this {@link Address}
	 */
	public Double getLongitude() {
		return _longitude;
	}

	/**
	 * @param street set the street
	 */
	public void setStreet(String street) {
		_street = street;
	}

	/**
	 * @param city set the city
	 */
	public void setCity(String city) {
		_city = city;
	}

	/**
	 * @param country set the country
	 */
	public void setCountry(Country country) {
		_country = country;
	}

	/**
	 * @param latitude set the latitude of the coordinates of the {@link Address}
	 * @param longitude set the longitude of the coordinates of the {@link Address}
	 */
	public void setCoordinates(Double latitude, Double longitude) {
		_latitude = latitude;
		_longitude = longitude;
	}

	/**
	 * Set the street/city/country fields of the {@link Address}.
	 * @param street the street
	 * @param city the city
	 * @param country the country
	 */
	public void setAddress(String street, String city, Country country) {
		setStreet(street);
		setCity(city);
		setCountry(country);
	}

	@Override
	public boolean equals(Object o) {
		boolean areEqual = true;

		if (o instanceof Address) {
			Address a = (Address) o;

			if (!Entity._compare(getStreet(), a.getStreet()))
				areEqual = false;
			if (!Entity._compare(getCity(), a.getCity()))
				areEqual = false;
			if (!Entity._compare(getCountry(), a.getCountry()))
				areEqual = false;
			if (!Entity._compare(getLatitude(), a.getLatitude()))
				areEqual = false;
			if (!Entity._compare(getLongitude(), a.getLongitude()))
				areEqual = false;
		} else
			areEqual = false;

		return areEqual;
	}
}
