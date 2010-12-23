package org.tagaprice.client.gwt.shared.entities.productmanagement;

import org.tagaprice.client.gwt.shared.entities.AEntity;
import org.tagaprice.client.gwt.shared.entities.dump.ICategory;
import org.tagaprice.client.gwt.shared.entities.dump.IUnit;

/**
 * This class is used to send {@link Product} data from client to server and from server to client. It will also be used
 * as model. (Read MVP pattern on <a href="http://code.google.com/webtoolkit/articles/mvp-architecture.html#model" />)
 * 
 */
public class Product extends AEntity implements IProduct {

	private static final long serialVersionUID = 1L;

	ICategory _category;
	IUnit _iUnit;

	@Override
	public void setCategory(ICategory category) {
		_category=category;

	}

	@Override
	public ICategory getCategory() {
		return _category;
	}

	@Override
	public void setUnit(IUnit unit) {
		_iUnit=unit;
	}

	@Override
	public IUnit getUnit() {
		return _iUnit;
	}

}
