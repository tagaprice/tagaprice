package org.tagaprice.client.generics.widgets;

import org.tagaprice.client.generics.events.CategorySelectedEventHandler;
import org.tagaprice.shared.entities.categorymanagement.Category;

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

	boolean isReadOnly();

	void setReadOnly(boolean read);
	
	void addCategorySelectedEventHandler(CategorySelectedEventHandler handler);
	
	public void config(boolean categoryTypeIsProduct,boolean isHeadline);
}
