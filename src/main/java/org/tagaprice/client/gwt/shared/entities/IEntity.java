package org.tagaprice.client.gwt.shared.entities;

import java.io.Serializable;

public interface IEntity extends Serializable  {


	/**
	 * Returns the RevisionId of the {@link AEntity}, if NULL you get an {@link NullPointerException}
	 * @return Returns the RevisionId of the {@link AEntity
	 */
	public IRevisionId getRevisionId() throws NullPointerException;



	/**
	 * Returns the Title of the {@link AEntity}, if Title is NULL you get NULL back
	 * @return Returns the Title of the {@link AEntity}
	 */
	public String getTitle() throws NullPointerException;

	/**
	 * This method can be used by the client and the server.
	 * @param title Sets the entity title
	 */
	public void setTitle(String title);
}
