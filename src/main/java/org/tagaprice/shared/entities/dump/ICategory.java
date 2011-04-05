package org.tagaprice.shared.entities.dump;

import org.tagaprice.shared.entities.IEntity;

public interface ICategory extends IEntity<ICategory> {

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

	public void setId(long id);

	public long getId();

}
