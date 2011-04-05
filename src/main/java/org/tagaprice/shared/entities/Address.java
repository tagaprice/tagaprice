package org.tagaprice.shared.entities;

import java.io.Serializable;

import org.tagaprice.shared.entities.productmanagement.Country;

public class Address implements Serializable {

	private static final long serialVersionUID = 4080140561327538031L;

	private String _city;
	private Country _country;
	private double _lat;
	private double _lng;
	private String _street;
	private String _zip;

	public Address() {
		// TODO Auto-generated constructor stub
	}

	public Address(String city, Country country, double lat, double lng, String street, String zip) {
		super();
		_city = city;
		_country = country;
		_lat = lat;
		_lng = lng;
		_street = street;
		_zip = zip;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return _city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		_city = city;
	}

	/**
	 * @return the country
	 */
	public Country getCountry() {
		return _country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(Country country) {
		_country = country;
	}

	/**
	 * @return the lat
	 */
	public double getLat() {
		return _lat;
	}

	/**
	 * @param lat the lat to set
	 */
	public void setLat(double lat) {
		_lat = lat;
	}

	/**
	 * @return the lng
	 */
	public double getLng() {
		return _lng;
	}

	/**
	 * @param lng the lng to set
	 */
	public void setLng(double lng) {
		_lng = lng;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return _street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		_street = street;
	}

	/**
	 * @return the zip
	 */
	public String getZip() {
		return _zip;
	}

	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		_zip = zip;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Address [_city=" + _city + ", _country=" + _country + ", _lat=" + _lat + ", _lng=" + _lng
		+ ", _street=" + _street + ", _zip=" + _zip + "]";
	}



}
