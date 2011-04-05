package org.tagaprice.client.gwt.shared.entities.shopmanagement;

import org.tagaprice.client.gwt.shared.entities.Address;
import org.tagaprice.client.gwt.shared.entities.IRevisionId;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Country;


public class Subsidiary implements ISubsidiary {


	private static final long serialVersionUID = -5821304030783727083L;
	private Address _address = new Address();
	private IRevisionId _revisionId;
	private IShop _shop;

	public Subsidiary() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor to update a {@link Address}
	 * @param revisionId
	 * @param title
	 * @param street
	 * @param zip
	 * @param city
	 * @param country
	 * @param lat
	 * @param lng
	 */
	public Subsidiary(IRevisionId revisionId, String street, String zip, String city, Country country, double lat, double lng) {
		this(street, zip, city, country, lat, lng);
		setRevisionId(revisionId);

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
	public Subsidiary(String street, String zip, String city, Country country, double lat, double lng) {
		setStreet(street);
		setZip(zip);
		setCity(city);
		setCountry(country);
		setLat(lat);
		setLng(lng);
	}

	@Override
	public String getCity() {
		return _address.getCity();
	}

	@Override
	public Country getCountry() {
		return _address.getCountry();
	}

	@Override
	public double getLat() {
		return _address.getLat();
	}


	@Override
	public double getLng() {
		return _address.getLng();
	}

	@Override
	public String getStreet() {
		return _address.getStreet();
	}

	@Override
	public String getZip() {
		return _address.getPostalcode();
	}

	@Override
	public void setCity(String city) {
		_address.setCity(city);
	}

	@Override
	public void setCountry(Country country) {
		_address.setCountry(country);
	}

	@Override
	public void setLat(double lat) {
		_address.setLat(lat);
	}


	@Override
	public void setLng(double lng) {
		_address.setLng(lng);
	}

	@Override
	public void setRevisionId(IRevisionId revisionId) {
		_revisionId=revisionId;

	}

	@Override
	public void setStreet(String street) {
		_address.setStreet(street);
	}

	@Override
	public void setZip(String zip) {
		_address.setPostalcode(zip);
	}

	@Override
	public IRevisionId getRevisionID() {
		return _revisionId;
	}


	@Override
	public void setShop(IShop shop) {
		_shop=shop;
	}

	@Override
	public IShop getShop() {
		return _shop;
	}



}
