package org.tagaprice.shared.entities.shopmanagement;

import java.util.ArrayList;

import org.tagaprice.shared.entities.AEntity;
import org.tagaprice.shared.entities.Address;

/**
 * A shop contains a group of {@link ISubsidiary}. Eg. the shop is "Billa" and the subsidiary is
 * "Karlsplatz 14, 1050 Wien"
 * 
 */
public class Shop extends AEntity {
	private static final long serialVersionUID = 1L;

	private ArrayList<Shop> _kids = new ArrayList<Shop>();
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
	 * @param title
	 *            title of the shop
	 */
	public Shop(String title) {
		super(title);
	}


	/**
	 * <b>UPDATE and GET</b>
	 * Constructor to update a {@link Shop}
	 * 
	 * @param revisionId
	 * @param title
	 */
	public Shop(String shopId, String revision, String title) {
		super(shopId, revision, title);
	}

	public void setParent(Shop parent) {
		_parent=parent;
	}

	public Shop getParent() {
		return _parent;
	}

	public void setChildren(ArrayList<Shop> kids) {
		_kids.clear();

		for (Shop s: kids) {
			s.setParent(this);
			_kids.add(s);
		}
	}

	public void addChild(Shop kid) {
		kid.setParent(this);
		_kids.add(kid);

	}

	public ArrayList<Shop> getChildren() {
		return _kids;
	}

	public void setAddress(Address address) {
		_address=address;
	}

	public Address getAddress() {
		return _address;
	}



}
