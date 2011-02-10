package org.tagaprice.client.gwt.shared.entities.shopmanagement;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.AEntity;
import org.tagaprice.client.gwt.shared.entities.IRevisionId;

public class Shop extends AEntity<IShop> implements IShop {

	private ArrayList<IAddress> _addresses = new ArrayList<IAddress>();

	/**
	 * 
	 */
	private static final long serialVersionUID = 2788170513486885397L;

	public Shop() {	}



	/**
	 * Constructor to create a new {@link Shop}
	 * @param title
	 */
	public Shop(String title) {
		super(title);
	}


	/**
	 * Constructor to update a {@link Shop}
	 * @param revisionId
	 * @param title
	 * @param addresses
	 */
	public Shop(IRevisionId revisionId, String title, ArrayList<IAddress> addresses) {
		super(revisionId, title);
		setAddresses(addresses);
	}




	@Override
	public IShop copy() {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public void setAddresses(ArrayList<IAddress> addresses) {
		_addresses.clear();
		_addresses.addAll(addresses);

	}



	@Override
	public void addAddress(IAddress address) {
		_addresses.add(address);
	}



	@Override
	public ArrayList<IAddress> getAddresses() {
		return _addresses;
	}


}
