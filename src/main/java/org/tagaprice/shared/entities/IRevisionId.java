package org.tagaprice.shared.entities;

import java.io.Serializable;
import java.util.Date;

import org.svenson.JSONProperty;
import org.tagaprice.shared.entities.accountmanagement.IUser;

/**
 * This interface is used to set an unique Id and revision for an {@link AEntity}. And to FIND an {@link AEntity}
 * 
 */
public interface IRevisionId extends Serializable {

	/**
	 * Set a unique id for an {@link AEntity}. It is used to find an {@link AEntity} and by UPDATE an {@link AEntity}
	 * 
	 * @param id
	 *            unique id. Is set to FIND an {@link AEntity}.
	 */
	public void setId(String id);


	/**
	 * Returns a unique {@link AEntity} ID
	 * @return Returns a unique {@link AEntity} ID
	 */
	@JSONProperty(value="_id")
	public String getId();

	/**
	 * Set the revision id for a {@link AEntity}. This method must not be used on the client.
	 * 
	 * @param rev
	 *            Set the revision id for a {@link AEntity}. Only the server is allowed to set the RevisonId.
	 */
	public void setRevision(String rev);


	/**
	 * Returns the RevisionId for a {@link AEntity}
	 * 
	 * @return Returns the RevisionId for a {@link AEntity}
	 */
	@JSONProperty(value="_rev")
	public String getRevision();

	/**
	 * Set the user who created this {@link IRevision}
	 * 
	 * @param user
	 *            The user who created this {@link IRevision}
	 */
	public void setUser(IUser user);

	/**
	 * 
	 * @return Returns the user, who created the {@link IRevision} of a special {@link AEntity}
	 */
	@JSONProperty(value="author")
	public IUser getUser();

	/**
	 * Set the {@link Date} when the {@link IRevision} is going to be created. This method must not be used on the client.
	 * 
	 * @param date
	 *            The date when the {@link IRevision} is going to be created. Only the server in allowed to use this
	 *            method.
	 */
	public void setDate(Date date);

	/**
	 * 
	 * @return Returned the {@link Date} when the {@link IRevision} was created.
	 */
	@JSONProperty(value="creationDate")
	public Date getDate();



}
