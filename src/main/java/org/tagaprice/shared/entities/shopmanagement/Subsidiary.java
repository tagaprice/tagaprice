package org.tagaprice.shared.entities.shopmanagement;

import org.tagaprice.client.gwt.shared.entities.ASEntity;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.IRevisionId;

/**
 * A Subsidiary is a location of a shop. Only a {@link Subsidiary} can include products
 *
 */
public class Subsidiary extends ASEntity<ISubsidiary> implements ISubsidiary {

	private Address _address = new Address();
	private IShop _shop;

	/**
	 * This constructor is used by the serialization algorithm
	 */
	public Subsidiary() {}

	/**
	 * <b>UPDATE and GET</b>
	 * Constructor to update a {@link Address}
	 * @param revisionId the revision of the {@link Subsidiary}
	 * @param address
	 */
	public Subsidiary(IRevisionId revisionId, Address address) {
		this(address);
		setRevisionId(revisionId);

	}

	/**
	 * <b>NEW</b>
	 * Constructor to create a new {@link Address}
	 * @param address
	 */
	public Subsidiary(Address address) {
		setAddress(address);
	}

	@Override
	public void setShop(IShop shop) {
		_shop=shop;
	}


	@Override
	public void setAddress(Address address) {
		_address=address;
	}

	@Override
	public Address getAddress() {
		return _address;
	}

	@Override
	public IShop getShop() {
		return _shop;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Subsidiary [_address=" + _address + "]";
	}



}
