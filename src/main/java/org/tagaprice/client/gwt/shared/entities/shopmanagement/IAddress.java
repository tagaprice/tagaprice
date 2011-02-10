package org.tagaprice.client.gwt.shared.entities.shopmanagement;

import java.io.Serializable;

import org.tagaprice.client.gwt.shared.entities.IRevisionId;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Country;

/**
 * An Address defines the position where a IShop can be.
 *
 */
public interface IAddress extends Serializable {

	public void setRevisionId(IRevisionId revisionId);

	public void setStreet(String street);

	public void setZip(String zip);

	public void setCity(String city);

	public void setCountry(Country country);

	public void setLat(double lat);

	public void setLng(double lng);


	public String getStreet();

	public String getZip();

	public String getCity();

	public Country getCountry();

	public double getLat();

	public double getLng();

}
