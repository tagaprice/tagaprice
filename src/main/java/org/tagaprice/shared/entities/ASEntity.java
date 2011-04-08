package org.tagaprice.shared.entities;

import java.io.Serializable;

import org.svenson.JSONProperty;

/**
 * The {@link ASEntity} class is used to FIND, CREATE and UPDATE different entities. It contains two constructors, one
 * for FIND and one for CREATE an {@link ASEntity}.
 * If you like to UPDATE a {@link ASEntity} you have to FIND a {@link ASEntity} first. The server will set the
 * {@link User} and the RevisionID.
 * Don't change the RevisionID on the client (by hand). Only the server will change it by an UPDATE.
 * 
 */
public abstract class ASEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String _id = null;
	private String _rev = null;

	/**
	 * <b>NEW</b>
	 * Is used to create a new {@link ASEntity}
	 */
	public ASEntity() {
	}

	/**
	 * <b>UPDATE and GET</b>
	 * This constructor is used by the server to fetch a {@link ASEntity} after SAVING or FINDING a {@link ASEntity}.
	 * @param revisionID
	 */
	public ASEntity(String id, String revision){
		setId(id);
		setRevision(revision);
	}

	@JSONProperty(value="_rev", ignoreIfNull = true)
	public String getRevision() {
		return _rev;
	}

	public void setRevision(String revision) {
		_rev=revision;
	}

	@JSONProperty(value="_id", ignoreIfNull = true)
	public String getId() {
		return _id;
	}

	public void setId(String entityId) {
		_id = entityId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ASEntity [id: " + _id + ", rev: "+_rev+"]";
	}


}
