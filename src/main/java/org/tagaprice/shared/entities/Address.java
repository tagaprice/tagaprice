package org.tagaprice.shared.entities;

import java.io.Serializable;

public class Address implements Serializable {

	private static final long serialVersionUID = 4080140561327538031L;

	private String _address;
	private double _lat;
	private double _lng;

	/**
	 * This constructor is used by the serialization algorithm
	 */
	public Address() {}


	/**
	 * <b>NEW</b>
	 * Constructor to create a new Address
	 * @param address address
	 * @param lat latitude
	 * @param lng longitude
	 */
	public Address(String address, double lat, double lng) {
		super();
		_address=address;
		_lat = lat;
		_lng = lng;
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


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Address [_address=" + _address + ", _lat=" + _lat + ", _lng=" + _lng + "]";
	}





}
