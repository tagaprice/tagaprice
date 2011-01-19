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











}
