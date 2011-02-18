package org.tagaprice.client.gwt.shared.entities.shopmanagement;

import org.tagaprice.client.gwt.shared.entities.IRevisionId;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Country;


public class Address implements IAddress {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5821304030783727083L;
	private String _city;
	private Country _country;
	private double _lat;
	private double _lng;
	private IRevisionId _revisionId;
	private String _street;
	private String _zip;

	public Address() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor to update a {@link Shop}
	 * @param revisionId
	 * @param title
	 * @param street
	 * @param zip
	 * @param city
	 * @param country
	 * @param lat
	 * @param lng
	 */
	public Address(IRevisionId revisionId, String street, String zip, String city, Country country, double lat, double lng) {
		this(street, zip, city, country, lat, lng);
		setRevisionId(_revisionId);

	}

	/**
	 * Constructor to create a new {@link Address}
	 * @param title
	 * @param street
	 * @param zip
	 * @param city
	 * @param country
	 * @param lat
	 * @param lng
	 */
	public Address(String street, String zip, String city, Country country, double lat, double lng) {
		setStreet(street);
		setZip(zip);
		setCity(city);
		setCountry(country);
		setLat(lat);
		setLng(lng);
	}

	@Override
	public String getCity() {
		return _city;
	}

	@Override
	public Country getCountry() {
		return _country;
	}

	@Override
	public double getLat() {
		return _lat;
	}


	@Override
	public double getLng() {
		return _lng;
	}

	@Override
	public String getStreet() {
		return _street;
	}

	@Override
	public String getZip() {
		return _zip;
	}

	@Override
	public void setCity(String city) {
		_city=city;
	}

	@Override
	public void setCountry(Country country) {
		_country=country;
	}

	@Override
	public void setLat(double lat) {
		_lat=lat;
	}


	@Override
	public void setLng(double lng) {
		_lng=lng;
	}

	@Override
	public void setRevisionId(IRevisionId revisionId) {
		_revisionId=revisionId;

	}

	@Override
	public void setStreet(String street) {
		_street=street;
	}

	@Override
	public void setZip(String zip) {
		_zip=zip;
	}

	@Override
	public IRevisionId getRevisionID() {
		return _revisionId.copy();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Address [_city=" + _city + ", _country=" + _country + ", _lat=" + _lat + ", _lng=" + _lng
		+ ", _revisionId=" + _revisionId + ", _street=" + _street + ", _zip=" + _zip + "]";
	}



}
