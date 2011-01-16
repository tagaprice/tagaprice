package org.tagaprice.client.gwt.shared.entities.productmanagement;

import org.tagaprice.client.gwt.shared.entities.AEntity;
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
		return new Product(this.getTitle(), this._category.copy(), this._quantity.copy());
	}

}
