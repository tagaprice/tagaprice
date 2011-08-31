package org.tagaprice.shared.entities;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Address implements IsSerializable {

	private static final long serialVersionUID = 1L;

	private String _address;
	private String _street;
	private String _postalcode;
	private String _city;
	private String _countrycode;
	private double _lat;
	private double _lon;

	/**
	 * This constructor is used by the serialization algorithm
	 */
	public Address() {}


	/**
	 * <b>NEW</b>
	 * Constructor to create a new Address
	 * @param address address
	 * @param lat latitude
	 * @param lon longitude
	 */
	public Address(String address, double lat, double lon) {
		super();
		_address=address;
		_lat = lat;
		_lon = lon;
	}

	
	


	public Address(String street, String postalcode, String city,
			String countrycode, double lat, double lon) {
		_street = street;
		_postalcode = postalcode;
		_city = city;
		_countrycode = countrycode;
		_lat = lat;
		_lon = lon;
	}


	/**
	 * @return Latitude
	 */
	public double getLat() {
		return _lat;
	}

	/**
	 * @return Longitude
	 */
	public double getLon() {
		return _lon;
	}



	/**
	 * @param lat the longitude to set
	 */
	public void setLat(double lat) {
		_lat = lat;
	}

	/**
	 * @param lon the longitude to set
	 */
	public void setLon(double lon) {
		_lon = lon;
	}


	/**
	 * @return the address
	 */
	public String getAddress() {
		return _address;
	}


	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		_address = address;
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
	 * @return the postalcode
	 */
	public String getPostalcode() {
		return _postalcode;
	}


	/**
	 * @param postalcode the postalcode to set
	 */
	public void setPostalcode(String postalcode) {
		_postalcode = postalcode;
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
	 * @return the countrycode
	 */
	public String getCountrycode() {
		return _countrycode;
	}


	/**
	 * @param countrycode the countrycode to set
	 */
	public void setCountrycode(String countrycode) {
		_countrycode = countrycode;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Address [_address=" + _address + ", _street=" + _street
				+ ", _postalcode=" + _postalcode + ", _city=" + _city
				+ ", _countrycode=" + _countrycode + ", _lat=" + _lat
				+ ", _lon=" + _lon + "]";
	}

}
