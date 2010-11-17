/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: TagAPriceUI
 * Filename: ReceiptContainer.java
 * Date: 16.05.2010
*/
package org.tagaprice.shared.entities;

import java.util.ArrayList;
import java.util.Date;

/**
 * Contains all information about a receipt.
 */
public class Receipt extends Entity {
	private static final long serialVersionUID = 1L;

	private boolean _draft;
	private Date _date;
	/** in Cent (or similar) TODO what's that for? */
	private int _bill;
	private Shop _shop;
	private ArrayList<Product> _products = new ArrayList<Product>();
	
	/**
	 * default constructor needed for serialization
	 */
	public Receipt() {
		super();
	}
	
	/**
	 * constructor used for querying the current revision of a {@link Receipt}from the database
	 * @param id Receipt ID
	 */
	public Receipt(long id) {
		super(id);
	}
	
	/**
	 * constructor used for querying a specific revision of a {@link Receipt} from the database
	 * @param id Receipt ID
	 * @param rev requested revision
	 */
	public Receipt(long id, int rev) {
		super(id, rev);
	}
	
	/**
	 * constructor used to store a new {@link Receipt} into the database 
	 * @param title descriptive receipt name (should not be empty)
	 * @param localeId receipt locale
	 * @param creatorId receipt's creator
	 * @param date receipt date
	 * @param bill bill in Cent (or similar)
	 * @param shop {@link Shop} this {@link Receipt} is from
	 * @param products list of {@link Product}s of this bill.
	 * @param draft is this {@link Receipt} a draft only?
	 */
	public Receipt(String title, int localeId, long creatorId, Date date, int bill, Shop shop, ArrayList<Product> products, boolean draft) {
		super(title, localeId, creatorId);
		this._date = date;
		this._bill = bill;
		this._shop = shop;
		this._products = products;
		_draft = draft;
	}
	
	/**
	 * constructor used to create a {@link Receipt} with given ID and revision
	 * @param id ID
	 * @param rev revision
	 * @param title descriptive receipt name (should not be empty)
	 * @param localeId receipt locale
	 * @param creatorId receipt's creator
	 * @param date receipt date
	 * @param bill bill in Cent (or similar)
	 * @param shop {@link Shop} this {@link Receipt} is from
	 * @param products list of {@link Product}s of this bill.
	 * @param draft is this {@link Receipt} a draft only? 
	 */
	public Receipt(long id, int rev, String title, long creatorId, Date date, int bill, Shop shop, ArrayList<Product> products, boolean draft) {
		super(id, rev, title, creatorId);
		this._date = date;
		this._bill = bill;
		this._shop = shop;
		this._products = products;
		_draft = draft;
	}
	
	/**
	 * @return true, if this {@link Receipt} is a draft.
	 */
	public boolean getDraft() {
		return _draft;
	}
	
	/**
	 * @param draft set if this {@link Receipt} is a draft.
	 */
	public void setDraft(boolean draft) {
		this._draft = draft;
	}
	
	/**
	 * @return date of this {@link Receipt}.
	 */
	public Date getDate() {
		return _date;
	}
	
	/**
	 * @param date date of this {@link Receipt}.
	 */
	public void setDate(Date date) {
		this._date = date;
	}

	/**
	 * Calculates the total price for all {@link Product}s of this {@link Receipt}
	 * using the avgPrice of the {@link Product}s.
	 * @return the total price for all {@link Product}s of this {@link Receipt}
	 */
	public long getTotalPrice(){
		
		long totalPrice = 0;
		for(Product pd: _products){
			totalPrice= totalPrice+pd.getAvgPrice().getPrice();
		}
		
		return totalPrice;
	}
	
	/**
	 * TODO use {@link Receipt#getTotalPrice()} instead ?
	 * @return the amount of money for this {@link Receipt} in Cent (or similar)
	 */
	public int getBill() {
		return _bill;
	}
	
	/**
	 * @param bill the amount of money for this {@link Receipt} in Cent (or similar)
	 */
	public void setBill(int bill) {
		this._bill = bill;
	}
	
	/**
	 * @return {@link Shop} where this receipt is from.
	 */
	public Shop getShop() {
		return _shop;
	}
	
	/**
	 * @param shop {@link Shop} where this receipt is from.
	 */
	public void setShop(Shop shop) {
		this._shop = shop;
	}
	
	/**
	 * @return all {@link Product}s of this {@link Receipt}.
	 */
	public ArrayList<Product> getProducts() {
		return _products;
	}
	
	/**
	 * @param products {@link Product}s of this {@link Receipt}.
	 */
	public void setProductData(ArrayList<Product> products) {
		this._products = products;
	}


	@Override
	public String getSerializeName() {
		return "receipt";
	}
	
	// TODO implement missing ReceiptData.equals()
}
