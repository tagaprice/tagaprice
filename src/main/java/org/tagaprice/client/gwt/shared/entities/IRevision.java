package org.tagaprice.client.gwt.shared.entities;

import java.io.Serializable;
import java.util.Date;

import org.tagaprice.client.gwt.shared.entities.dump.IUser;

/**
 * Implements a revision for a {@link AEntity}. Only the server is allowed to create a new {@link IRevision}
 * 
 */
public interface IRevision extends Serializable {

	/**
	 * Set the revision id for a {@link AEntity}. This method must not be used on the client.
	 * 
	 * @param rev
	 *            Set the revision id for a {@link AEntity}. Only the server is allowed to set the RevisonId.
	 */
	public void setRevision(long rev);


	/**
	 * Returns the RevisionId for a {@link AEntity}
	 * 
	 * @return Returns the RevisionId for a {@link AEntity}
	 */
	public long getRevision();

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
	public IUser getUser();

	/**
	 * 
	 * @return Returned the {@link Date} when the {@link IRevision} was created.
	 */
	public Date getDate();

	/**
	 * Set the {@link Date} when the {@link IRevision} is going to be created. This method must not be used on the client.
	 * 
	 * @param date
	 *            The date when the {@link IRevision} is going to be created. Only the server in allowed to use this
	 *            method.
	 */
	public void setDate(Date date);

}
