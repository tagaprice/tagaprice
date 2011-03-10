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
	 */
	public Shop(IRevisionId revisionId, String title) {
		super(revisionId, title);
	}


	@Override
	public void setAddresses(ArrayList<IAddress> addresses) {
		_addresses.clear();

		for(IAddress ad: addresses){
			ad.setShop(this);
			_addresses.add(ad);
		}

	}



	@Override
	public void addAddress(IAddress address) {
		address.setShop(this);
		_addresses.add(address);
	}



	@Override
	public ArrayList<IAddress> getAddresses() {
		return _addresses;
	}

	@Override
	public String toString() {
		StringBuffer toStringBuffer = new StringBuffer();
		toStringBuffer.append(super.toString());

		for(IAddress ia:_addresses){
			toStringBuffer.append(ia.toString());
		}

		return toStringBuffer.toString();
	}


}
