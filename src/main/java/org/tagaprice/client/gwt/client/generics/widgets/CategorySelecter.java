package org.tagaprice.client.gwt.client.generics.widgets;

import org.tagaprice.client.gwt.shared.entities.dump.ICategory;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;

/**
 * The class will manage the Category selection, and will also communicate with the server.
 *
 */
public class CategorySelecter extends Composite implements ICategorySelecter {

	ListBox categories = new ListBox();

	public CategorySelecter() {
		initWidget(categories);
	}

	@Override
	public void setCategory(ICategory category){

	}
}
