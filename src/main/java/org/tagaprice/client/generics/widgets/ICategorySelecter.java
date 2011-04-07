package org.tagaprice.client.generics.widgets;

import java.util.List;

import org.tagaprice.shared.entities.dump.ICategory;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * This interface defines the primary methods to select a {@link ICategory}. This interface must me implemented for
 * every screen.
 * 
 */
public interface ICategorySelecter extends IsWidget {

	/**
	 * Set the current category
	 * @param category the current category
	 */
	public void setCategory(ICategory category);

	public ICategory getCategory();

	void setAvailableCategories(List<ICategory> categories);
}
