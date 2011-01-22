package org.tagaprice.client.gwt.shared.entities.shopmanagement;

import org.tagaprice.client.gwt.shared.entities.IEntity;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Country;

public interface IShop extends IEntity<IShop> {

	public void setStreet(String street);

	public void setZip(String zip);

	public void setCity(String city);

	public void setCountry(Country country);

	public void setLat(double lat);

	public void setLng(double lng);

	String getStreet();

	String getZip();

	String getCity();

	Country getCountry();

	double getLat();

	double getLng();

}
