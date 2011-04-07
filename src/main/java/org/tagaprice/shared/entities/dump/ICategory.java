package org.tagaprice.shared.entities.dump;

import org.tagaprice.shared.entities.IEntity;

public interface ICategory extends IEntity {

	/**
	 * Returns the parent category.
	 * @return
	 */
	public ICategory getParentCategory();

	/**
	 * Sets the parent category.
	 * @param category
	 */
	public void setParentCategory(ICategory category);


}
