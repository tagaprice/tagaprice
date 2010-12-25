package org.tagaprice.client.gwt.shared.entities;

import java.io.Serializable;

public interface IEntity extends Serializable  {


	/**
	 * Returns the RevisionId of the {@link AEntity}
	 * @return Returns the RevisionId of the {@link AEntity
	 */
	public IRevisionId getRevisionId() throws NullPointerException;



	/**
	 * Returns the Title of the {@link AEntity}
	 * @return Returns the Title of the {@link AEntity}
	 */
	public String getTitle();

	/**
	 * This method can be used by the client and the server.
	 * @param title Sets the entity title
	 */
	public void setTitle(String title);
}
