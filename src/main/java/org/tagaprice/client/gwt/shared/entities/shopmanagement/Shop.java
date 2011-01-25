package org.tagaprice.client.gwt.shared.entities.shopmanagement;

import org.tagaprice.client.gwt.shared.entities.AEntity;
import org.tagaprice.client.gwt.shared.entities.IRevisionId;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Country;

public class Shop extends AEntity<IShop> implements IShop {

	private String _street;
	private String _zip;
	private String _city;
	private Country _country;
	private double _lat;
	private double _lng;

	/**
	 * 
	 */
	private static final long serialVersionUID = 2788170513486885397L;

	public Shop() {	}



	/**
	 * Constructor to create a new {@link Shop}
	 * @param title
	 * @param street
	 * @param zip
	 * @param city
	 * @param country
	 * @param lat
	 * @param lng
	 */
	public Shop(String title, String street, String zip, String city, Country country, double lat, double lng) {
		super(title);
		_street = street;
		_zip = zip;
		_city = city;
		_country = country;
		_lat = lat;
		_lng = lng;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Shop [_street=" + _street + ", _zip=" + _zip + ", _city=" + _city + ", _country=" + _country
		+ ", _lat=" + _lat + ", _lng=" + _lng + "]";
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
	public Shop(IRevisionId revisionId,String title, String street, String zip, String city, Country country, double lat, double lng) {
		super(revisionId, title);
		_street = street;
		_zip = zip;
		_city = city;
		_country = country;
		_lat = lat;
		_lng = lng;
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
	public String getStreet() {
		return _street;
	}

	@Override
	public String getZip() {
		return _zip;
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
	public IShop copy() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_city == null) ? 0 : _city.hashCode());
		result = prime * result + ((_country == null) ? 0 : _country.hashCode());
		//Removed calculation of long+lat
		result = prime * result + ((_street == null) ? 0 : _street.hashCode());
		result = prime * result + ((_zip == null) ? 0 : _zip.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Shop other = (Shop) obj;
		if (_city == null) {
			if (other._city != null)
				return false;
		} else if (!_city.equals(other._city))
			return false;
		if (_country == null) {
			if (other._country != null)
				return false;
		} else if (!_country.equals(other._country))
			return false;
		if (_street == null) {
			if (other._street != null)
				return false;
		} else if (!_street.equals(other._street))
			return false;
		if (_zip == null) {
			if (other._zip != null)
				return false;
		} else if (!_zip.equals(other._zip))
			return false;
		return true;
	}











}
