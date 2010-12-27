package org.tagaprice.client.gwt.shared.entities.productmanagement;

import java.util.ArrayList;

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
	private ArrayList<IQuantity> _quantities = new ArrayList<IQuantity>();

	@Override
	public void setCategory(ICategory category) {
		_category=category;

	}

	@Override
	public ICategory getCategory() {
		return _category;
	}


	@Override
	public void addQuantity(IQuantity quantity) {
		_quantities.add(quantity);
	}

	@Override
	public void addQuantities(ArrayList<IQuantity> quantities) {
		_quantities.addAll(quantities);

	}

	@Override
	public ArrayList<IQuantity> getQuantities() {
		return _quantities;
	}

}
