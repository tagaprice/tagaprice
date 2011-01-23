package org.tagaprice.client.gwt.shared.entities.productmanagement;

import org.tagaprice.client.gwt.shared.entities.*;
import org.tagaprice.client.gwt.shared.entities.dump.ICategory;
import org.tagaprice.client.gwt.shared.entities.dump.IQuantity;

/**
 * This class is used to send {@link Product} data from client to server and from server to client. It will also be used
 * as model. (Read MVP pattern on <a href="http://code.google.com/webtoolkit/articles/mvp-architecture.html#model" />)
 * 
 */
public class Product extends AEntity<IProduct> implements IProduct {


	private static final long serialVersionUID = 4858431133448109402L;
	private ICategory _category;
	private IQuantity _quantity;

	public Product() {}

	/**
	 * Constructor to update a {@link Product}
	 * @param revisionId
	 * @param title
	 * @param category
	 * @param quantity
	 */
	public Product(IRevisionId revisionId, String title, ICategory category, IQuantity quantity) {
		super(revisionId, title);
		this._category = category;
		this._quantity = quantity;
	}

	/**
	 * Constructor to create a new {@link Product}
	 * @param title
	 * @param category
	 * @param quantity
	 */
	public Product(String title, ICategory category, IQuantity quantity) {
		super(title);
		this._category = category;
		this._quantity = quantity;
	}

	@Override
	public void setCategory(ICategory category) {
		_category=category;

	}

	@Override
	public ICategory getCategory() {
		return _category;
	}


	@Override
	public void setQuantity(IQuantity quantity) {
		_quantity=quantity;
	}

	@Override
	public IQuantity getQuantity() {
		return _quantity;
	}

	@Override
	public IProduct copy() {
		if(this.getRevisionId() != null) {
			return new Product(this.getRevisionId().copy(), this.getTitle(), this._category.copy(), this._quantity.copy());
		} else {
			return new Product(this.getTitle(), this._category.copy(), this._quantity.copy());
		}
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Product) {
			Product p = (Product) o;
			return super.equals(o) && this._category.equals(p._category) && this._quantity.equals(p._quantity);
		} else {
			return false;
		}
	}

	public boolean equals(Product p) {
		return false;
	}

	@Override
	public String toString() {
		String temp;
		if(this.getRevisionId()!= null){
			temp = this.getRevisionId().toString();
		}else{
			temp = "Revision is null";
		}
		return "Product: " + this.getTitle() + ", " + temp ;
	}

}
