package org.tagaprice.client.generics.widgets;

import java.util.List;

import org.tagaprice.shared.entities.dump.ICategory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;

/**
 * This is a wrapper class that implements the {@link ICategorySelecter} for every screen.
 * With this wrapper it is possible to change the screen and use the classes in a
 * {@link com.google.gwt.uibinder.client.UiBinder} or create new instances.
 * 
 */
public class CategorySelecter extends Composite implements ICategorySelecter {

	private ICategorySelecter categorySelecter = GWT.create(ICategorySelecter.class);

	public CategorySelecter() {
		//categorySelecter = GWT.create(ICategorySelecter.class);
		initWidget(categorySelecter.asWidget());
	}

	@Override
	public void setCategory(ICategory category) {
		categorySelecter.setCategory(category);
	}

	@Override
	public ICategory getCategory() {
		return categorySelecter.getCategory();
	}

	@Override
	public void setAvailableCategories(List<ICategory> categories) {
		categorySelecter.setAvailableCategories(categories);
	}

}