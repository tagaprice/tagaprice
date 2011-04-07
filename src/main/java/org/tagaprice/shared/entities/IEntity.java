package org.tagaprice.shared.entities;

import org.tagaprice.shared.entities.ISEntity;

import org.svenson.JSONProperty;

public interface IEntity extends ISEntity  {

	/**
	 * Returns the Title of the {@link AEntity}, if Title is NULL you get NULL back
	 * @return Returns the Title of the {@link AEntity} or null.
	 */
	@JSONProperty(value="title")
	public String getTitle();

	/**
	 * This method can be used by the client and the server.
	 * @param title Sets the entity title
	 */
	public void setTitle(String title);


}
