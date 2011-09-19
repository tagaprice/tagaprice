package org.tagaprice.shared.entities;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.svenson.JSONProperty;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Address implements IsSerializable {
	public static class LatLon implements IsSerializable{
		private double _lat;
		private double _lon;
		
		public LatLon(double lat, double lon) {
			_lat = lat;
			_lon = lon;
		}
		
		public LatLon() {
			_lat = 1;
			_lon = 2;
		}
		
		public double getLat() {
			return _lat;
		}
		
		public double getLon() {
			return _lon;
		}
		
		public void setLat(double lat) {
			_lat = lat;
		}
		
		public void setLon(double lon) {
			_lon = lon;
		}
		
		public LonLat toLonLat() {
			return new LonLat(getLon(), getLat());
		}
		
		public String toString() {
			return "{lat: "+getLat()+", lon: "+getLon()+"}";
		}
		
		public static LatLon fromLonLat(LonLat lonLat) {
			return new LatLon(lonLat.lat(), lonLat.lon());
		}
	}
	private static final long serialVersionUID = 1L;

	private String _address;
	private String _street;
	private String _postalcode;
	private String _city;
	private String _countrycode;
	private LatLon _position = new LatLon();

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
		setPos(new LatLon(lat, lon));
	}

	
	


	public Address(String street, String postalcode, String city,
			String countrycode, double lat, double lon) {
		_street = street;
		_postalcode = postalcode;
		_city = city;
		_countrycode = countrycode;
		setPos(new LatLon(lat, lon));
	}

	public LatLon getPos() {
		return _position;
	}

	public void setPos(LatLon position) {
		_position = position;
	}
	
	/**
	 * @return the address
	 */
	@Deprecated
	public String getAddress() {
		return _address;
	}


	/**
	 * @param address the address to set
	 */
	@Deprecated
	public void setAddress(String address) {
		_address = address;
	}


	
	
	/**
	 * @return the street
	 */
	@JSONProperty(ignoreIfNull=true)
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
	@JSONProperty(ignoreIfNull=true)
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
	@JSONProperty(ignoreIfNull=true)
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
	@JSONProperty(ignoreIfNull=true)
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
				+ ", _countrycode=" + _countrycode + ", _position=" + _position
				+ "]";
	}

}
