package org.tagaprice.client.gwt.shared.entities;

import java.io.Serializable;

import org.tagaprice.client.gwt.shared.entities.productmanagement.Country;

public class Address implements Serializable {

	private static final long serialVersionUID = 4080140561327538031L;

	private String _city;
	private Country _country;
	private double _lat;
	private double _lng;
	private String _postalcode;
	private String _street;

	/**
	 * This constructor is used by the serialization algorithm
	 */
	public Address() {}


	/**
	 * <b>NEW</b>
	 * Constructor to create a new Address
	 * @param city city
	 * @param country {@link Country}
	 * @param lat latitude
	 * @param lng longitude
	 * @param street street
	 * @param postalcode postalcode
	 */
	public Address(String street, String postalcode, String city, Country country, double lat, double lng) {
		super();
		_city = city;
		_country = country;
		_lat = lat;
		_lng = lng;
		_street = street;
		_postalcode = postalcode;
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
	 * @return the lat
	 */
	public double getLat() {
		return _lat;
	}

	/**
	 * @return the lng
	 */
	public double getLng() {
		return _lng;
	}

	/**
	 * @return the postalcode
	 */
	public String getPostalcode() {
		return _postalcode;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return _street;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		_city = city;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(Country country) {
		_country = country;
	}

	/**
	 * @param lat the lat to set
	 */
	public void setLat(double lat) {
		_lat = lat;
	}

	/**
	 * @param lng the lng to set
	 */
	public void setLng(double lng) {
		_lng = lng;
	}

	/**
	 * @param postalcode the postalcode to set
	 */
	public void setPostalcode(String postalcode) {
		_postalcode = postalcode;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		_street = street;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Address [_city=" + _city + ", _country=" + _country + ", _lat=" + _lat + ", _lng=" + _lng
		+ ", _street=" + _street + ", _zip=" + _postalcode + "]";
	}



}
