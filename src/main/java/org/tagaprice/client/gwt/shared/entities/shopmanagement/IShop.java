package org.tagaprice.client.gwt.shared.entities.shopmanagement;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.IEntity;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Country;

public interface IShop extends IEntity<IShop> {

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

	/**
	 * Set some {@link IAddress} to the Shop. All include products will be deleted.
	 * @param addresses that will be set to the {@link IShop}
	 */
	public void setAddresses(ArrayList<IAddress> addresses);

	/**
	 * Add one new {@link IAddress} to this {@link IShop}
	 * @param address that will be added to the {@link IShop}
	 */
	public void addAddress(IAddress address);

	/**
	 * Returns all {@link IAddress}es
	 * @return  all {@link IAddress}es
	 */
	public ArrayList<IAddress> getAddresses();

}
