package org.tagaprice.shared.entities;

import java.io.Serializable;

import org.tagaprice.shared.entities.IRevisionId;

public interface ISEntity extends Serializable {


	/**
	 * Returns the RevisionId of the {@link ASEntity} or null, if IRevisionId is not set.
	 * @return Returns the RevisionId of the {@link ASEntity or null, if IRevisionId is not set.
	 */
	public IRevisionId getRevisionId() ;

	/**
	 * Set the revisionID of the {@link ASEntity}.
	 * @param revisionId of the {@link ASEntity}.
	 */
	public void setRevisionId(IRevisionId revisionId);
}
