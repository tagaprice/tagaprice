package org.tagaprice.shared.entities;

import java.io.Serializable;
import java.util.List;

import org.svenson.JSONProperty;

/**
 * The {@link ASimpleEntity} class is used to FIND, CREATE and UPDATE different entities. It contains two constructors, one
 * for FIND and one for CREATE an {@link ASimpleEntity}.
 * If you like to UPDATE a {@link ASimpleEntity} you have to FIND a {@link ASimpleEntity} first. The server will set the
 * {@link User} and the RevisionID.
 * Don't change the RevisionID on the client (by hand). Only the server will change it by an UPDATE.
 * 
 */
public abstract class ASimpleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String _id = null;
	private String _rev = null;

	/**
	 * <b>NEW</b>
	 * Is used to create a new {@link ASimpleEntity}
	 */
	public ASimpleEntity() {
	}

	/**
	 * <b>UPDATE and GET</b>
	 * This constructor is used by the server to fetch a {@link ASimpleEntity} after SAVING or FINDING a {@link ASimpleEntity}.
	 * @param revisionID
	 */
	public ASimpleEntity(String id, String revision){
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
		return "ASimpleEntity [id: " + _id + ", rev: "+_rev+"]";
	}

	@Override
	public boolean equals(Object otherObject) {
		boolean rc = true;

		if (otherObject instanceof ASimpleEntity) {
			ASimpleEntity other = (ASimpleEntity) otherObject;
			if (!_equals(_id, other._id)) rc = false;
			else if (!_equals(_rev, other._rev)) rc = false;
		}
		else rc = false;

		return rc;
	}

	protected static boolean _equals(Object a, Object b) {
		boolean rc = false;
		if (a == null) rc = b == null;
		else rc = a.equals(b);
		return rc;
	}

	protected static boolean _equalArrays(List<?> a, List<?> b) {
		boolean rc = true;

		if ((a == null || b == null) && a != b) rc = false;
		if (a.size() != b.size()) rc = false;
		else {
			for (int i = 0; rc && i < a.size(); i++) {
				rc = _equals(a.get(i), b.get(i));
			}
		}

		return rc;
	}
}
