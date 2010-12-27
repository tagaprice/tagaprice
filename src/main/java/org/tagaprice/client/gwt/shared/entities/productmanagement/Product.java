package org.tagaprice.client.gwt.shared.entities.productmanagement;

import org.tagaprice.client.gwt.shared.entities.AEntity;
import org.tagaprice.client.gwt.shared.entities.dump.ICategory;
import org.tagaprice.client.gwt.shared.entities.dump.IQuantity;

/**
 * This class is used to send {@link Product} data from client to server and from server to client. It will also be used
 * as model. (Read MVP pattern on <a href="http://code.google.com/webtoolkit/articles/mvp-architecture.html#model" />)
 * 
 */
public class Product extends AEntity implements IProduct {

	private static final long serialVersionUID = 1L;

	private ICategory _category;
	private IQuantity _quantity;

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

}
