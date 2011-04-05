package org.tagaprice.client.gwt.shared.entities.shopmanagement;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.AEntity;
import org.tagaprice.client.gwt.shared.entities.IRevisionId;

/**
 * A shop contains a group of {@link ISubsidiary}. Eg. the shop is "Billa" and the subsidiary is
 * "Karlsplatz 14, 1050 Wien"
 * 
 */
public class Shop extends AEntity<IShop> implements IShop {

	private ArrayList<ISubsidiary> _subsidiary = new ArrayList<ISubsidiary>();

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
	public Shop(IRevisionId revisionId, String title) {
		super(revisionId, title);
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



}
