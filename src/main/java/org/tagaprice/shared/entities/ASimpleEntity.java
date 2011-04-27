package org.tagaprice.shared.entities;

import java.io.Serializable;
import java.util.List;

import org.svenson.JSONProperty;

/**
 * The {@link ASimpleEntity} class is used to FIND, CREATE and UPDATE different entities. It contains two constructors, one
 * for FIND and one for CREATE an {@link ASimpleEntity}.
 * If you like to UPDATE a {@link ASimpleEntity} you have to FIND a {@link ASimpleEntity} first. The server will set the
 * {@link User} and the RevisionID.
 * Don't change the RevisionID on the client (by hand). It is required for optimistic locking in the DAO layer.
 * 
 */
public abstract class ASimpleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String _id = null;
	private String _rev = null;
	private String _type = null;

	/**
	 * <b>NEW</b>
	 * Is used to create a new {@link ASimpleEntity}
	 */
	public ASimpleEntity() {
	}

	/**
	 * <b>UPDATE and GET</b>
	 * This constructor is used by the server to fetch a {@link ASimpleEntity} after SAVING or FINDING a {@link ASimpleEntity}.
	 * @param typeName Unique type name (e.g. 
	 * @param revisionID
	 */
	public ASimpleEntity(String id, String revision){
		setId(id);
		setRevision(revision);
	}

	/**
	 * Returns the Revision of the {@link ASimpleEntity} or null, if it wasn't yet set (e.g. for unsaved {@link ASimpleEntity})
	 * @return Returns the Revision of the {@link ASimpleEntity} or null, if Revision is not set.
	 */
	@JSONProperty(value="_rev", ignoreIfNull = true)
	public String getRevision() {
		return _rev;
	}

	/**
	 * Set the Entity revision
	 * @param revision Revision ID
	 */
	public void setRevision(String revision) {
		_rev=revision;
	}

	/**
	 * Set the {@link ASimpleEntity} Id
	 * @return  {@link ASimpleEntity} Id
	 */
	@JSONProperty(value="_id", ignoreIfNull = true)
	public String getId() {
		return _id;
	}

	/**
	 * Set the Entity ID
	 * @param entityId Entity ID
	 */
	public void setId(String entityId) {
		_id = entityId;
	}
	
	/**
	 * Get the type name of the entity. This is required for the CouchDB DAO to work.
	 * Just ignore it anywhere else in the application.
	 * @return
	 */
	@JSONProperty(value="entityType", ignoreIfNull = true)
	public String getTypeName() {
		return _type;
	}
	
	/**
	 * This method is required for the CouchDB JSON injector to work.
	 * Just ignore it anywhere else in the application.
	 * 
	 * @param typeName Type name (e.g. "product" or "shop")
	 */
	public void setTypeName(String typeName) {
		_type = typeName;
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

	/**
	 * Convenience function that checks two objects for equality (and can handle null references)
	 * @param a First object to compare
	 * @param b Second object to compare
	 * @return False if exactly one of them is null, true if both are null, a.equals(b) otherwise 
	 */
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
