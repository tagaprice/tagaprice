package org.tagaprice.shared.entities.shopmanagement;

import org.tagaprice.shared.entities.ASEntity;
import org.tagaprice.shared.entities.Address;

/**
 * A Subsidiary is a location of a shop. Only a {@link Subsidiary} can include products
 *
 */
public class Subsidiary extends ASEntity implements ISubsidiary {
	private static final long serialVersionUID = 1L;

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
	public Subsidiary(String subsidiaryId, String revision, Address address) {
		super(subsidiaryId, revision);
		setAddress(address);
	}

	/**
	 * <b>NEW</b>
	 * Constructor to create a new {@link Address}
	 * @param address
	 */
	public Subsidiary(Address address) {
		this(null, null, address);
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
