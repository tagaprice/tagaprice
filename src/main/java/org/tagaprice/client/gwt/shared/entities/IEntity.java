package org.tagaprice.client.gwt.shared.entities;

import java.io.Serializable;

public interface IEntity extends Serializable  {

	/**
	 * Returns the unique {@link AEntity} id
	 * @return Returns the unique {@link AEntity} id
	 */
	public long getId();

	/**
	 * Sets a unique {@link AEntity} id. This methods must not be used on client side.
	 * @param id Set a unique {@link AEntity} id. Only the server is allowed to user this method.
	 */
	public void setId(long id);

	/**
	 * Returns the RevisionId of the {@link AEntity}
	 * @return Returns the RevisionId of the {@link AEntity
	 */
	public IRevision getRev();



	/**
	 * Set the RevisionId of the {@link AEntity}. This method must not be used by the client.
	 * @param rev set the RevisionID. Only the server is allowed to user this method.
	 */
	public void setRev(IRevision rev);
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
