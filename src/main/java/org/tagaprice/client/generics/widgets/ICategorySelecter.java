package org.tagaprice.client.generics.widgets;

import java.util.List;

import org.tagaprice.shared.entities.dump.Category;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * This interface defines the primary methods to select a {@link Category}. This interface must me implemented for
 * every screen.
 * 
 */
public interface ICategorySelecter extends IsWidget {

	/**
	 * Set the current category
	 * @param category the current category
	 */
	public void setCategory(Category category);

	public Category getCategory();

	void setAvailableCategories(List<Category> categories);
}
