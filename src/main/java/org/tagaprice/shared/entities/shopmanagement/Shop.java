package org.tagaprice.shared.entities.shopmanagement;

import java.util.ArrayList;

import org.tagaprice.shared.entities.AEntity;
import org.tagaprice.shared.entities.Address;

/**
 * A shop contains a group of {@link ISubsidiary}. Eg. the shop is "Billa" and the subsidiary is
 * "Karlsplatz 14, 1050 Wien"
 * 
 */
public class Shop extends AEntity implements IShop {
	private static final long serialVersionUID = 1L;

	private ArrayList<ISubsidiary> _subsidiary = new ArrayList<ISubsidiary>();
	private ArrayList<IShop> _kids = new ArrayList<IShop>();
	private Address _address = new Address();
	private IShop _parent;

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


	@Override
	public void setSubsidiary(ArrayList<ISubsidiary> addresses) {
		_subsidiary.clear();

		for (ISubsidiary ad : addresses) {
			ad.setShop(this);
			_subsidiary.add(ad);
		}

	}



	@Override
	public void addSubsidiary(ISubsidiary address) {
		address.setShop(this);
		_subsidiary.add(address);
	}



	@Override
	public ArrayList<ISubsidiary> getSubsidiaries() {
		return _subsidiary;
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



	@Override
	public void setParent(IShop parent) {
		_parent=parent;
	}



	@Override
	public IShop getParent() {
		return _parent;
	}



	@Override
	public void setKids(ArrayList<IShop> kids) {
		_kids.clear();

		for(IShop s:kids){
			s.setParent(this);
			_kids.add(s);
		}
	}



	@Override
	public void addKid(IShop kid) {
		kid.setParent(this);
		_kids.add(kid);

	}



	@Override
	public ArrayList<IShop> getKids() {
		return _kids;
	}



	@Override
	public void setAddress(Address address) {
		_address=address;
	}



	@Override
	public Address getAddress() {
		return _address;
	}



}
