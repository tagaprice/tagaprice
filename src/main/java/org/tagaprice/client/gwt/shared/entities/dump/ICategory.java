package org.tagaprice.client.gwt.shared.entities.dump;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.IEntity;

public interface ICategory extends IEntity<ICategory> {

	/**
	 * Returns the parent category.
	 * @return
	 */
	public ICategory getParentCategory();

	/**
	 * Returns a List with the child categories.
	 * @return
	 */
	public ArrayList<ICategory> getChildCategories();

	/**
	 * Adds a child Category. Implicitly calls setParent!
	 * @param category
	 */
	public void addChildCategory(ICategory category);

	/**
	 * Sets the parent category.
	 * @param category
	 */
	public void setParentCategory(ICategory category);

	public void setId(long id);

	public long getId();

}
