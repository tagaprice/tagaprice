package org.tagaprice.shared.entities.shopmanagement;

import org.svenson.JSONProperty;
import org.tagaprice.shared.entities.AEntity;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.accountmanagement.User;

/**
 * A shop contains a group of {@link ISubsidiary}. Eg. the shop is "Billa" and the subsidiary is
 * "Karlsplatz 14, 1050 Wien"
 * 
 */
public class Shop extends AEntity {
	private static final long serialVersionUID = 1L;

	private Address _address = new Address();
	private Shop _parent;

	/**
	 * This constructor is used by the serialization algorithm
	 */
	public Shop() {
	}



	/**
	 * <b>NEW</b>
	 * Constructor to create a new {@link Shop}
	 * 
	 * @param creator Creator of the current entity revision 
	 * @param title Title of the shop
	 */
	public Shop(User creator, String title) {
		super(creator, title);
	}


	/**
	 * <b>UPDATE and GET</b>
	 * Constructor to update a {@link Shop}
	 * 
	 * @param creator Creator of the current entity revision 
	 * @param revisionId
	 * @param title
	 */
	public Shop(User creator, String shopId, String revision, String title) {
		super(creator, shopId, revision, title);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Shop";
	}

	/**
	 * Set The parent {@link Shop}
	 * @param parent the parent {@link Shop}
	 */
	public void setParent(Shop parent) {
		_parent=parent;
	}

	/**
	 * Returns the {@link Shop} parent
	 * @return the parent. If null no parent.
	 */
	@JSONProperty(ignore=true)
	public Shop getParent() {
		return _parent;
	}


	/**
	 * Set an {@link Address} to the {@link Shop}
	 * @param address {@link Shop} address
	 */
	public void setAddress(Address address) {
		_address=address;
	}

	/**
	 * @return the address of the {@link Shop}
	 */
	public Address getAddress() {
		return _address;
	}



}
