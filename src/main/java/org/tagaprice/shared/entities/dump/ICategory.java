package org.tagaprice.shared.entities.dump;

import org.svenson.JSONProperty;
import org.tagaprice.shared.entities.IEntity;

public interface ICategory extends IEntity {

	/**
	 * Returns the parent category.
	 * @return
	 */
	@JSONProperty(value="parent", ignoreIfNull=true)
	public ICategory getParentCategory();

	/**
	 * Sets the parent category.
	 * @param category
	 */
	public void setParentCategory(ICategory category);


}
