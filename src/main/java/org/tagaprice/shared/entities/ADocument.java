package org.tagaprice.shared.entities;

import java.util.List;

import org.svenson.JSONProperty;
import org.tagaprice.shared.entities.accountmanagement.User;

import com.google.gwt.user.client.rpc.IsSerializable;


/**
 * The {@link ADocument} class is used to FIND, CREATE and UPDATE different documents. It contains two constructors, one
 * for FIND and one for CREATE an {@link ADocument}.
 * If you like to UPDATE a {@link ADocument} you have to FIND a {@link ADocument} first. The server will set the
 * {@link User} and the RevisionID.
 * Don't change the RevisionID on the client (by hand). Only the server will change it by an UPDATE.
 * 
 */
public abstract class ADocument implements IsSerializable {
	private static final long serialVersionUID = 1L;

	private String _id = null;
	private String _rev = null;
	private String _docType = null;
	private User _creator = null;

	private String _title;


	/**
	 * This constructor is used by the serialization algorithm
	 */
	public ADocument() {
	}

	/**
	 * <b>UPDATE and GET</b>
	 * This constructor is used by the server to fetch a {@link ADocument} after SAVING or FINDING a {@link ADocument}.
	 * @param creator Creator of the current document revision 
	 * @param id Unique EntityID
	 * @param title The title of the {@link ADocument}. It must not be null.
	 */
	public ADocument(User creator, String id, String revision, String title) {
		_creator = creator;
		setId(id);
		setRevision(revision);
		setTitle(title);
	}

	/**
	 * <b>NEW</b>
	 * Is used to create a new {@link ADocument}
	 * 
	 * @param creator Creator of the current document revision 
	 * @param title
	 *            The title of the {@link ADocument}. Every {@link ADocument} needs a title. It must not be null.
	 */
	public ADocument(User creator, String title) {
		this(creator, null, null, title);
	}

	/**
	 * Returns the creator of the Entity's current revision
	 * @return Entity creator
	 */
	@JSONProperty(ignore=true)
	public User getCreator() {
		return _creator;
	}

	/**
	 * Internal method necessary for CouchDB injection
	 * @return This revision's creator UID
	 */
	public String getCreatorId() {
		return _creator != null ? _creator.getId() : null;
	}

	/**
	 * Get the type name of the entity. This is required for the CouchDB DAO to work.
	 * Just ignore it anywhere else in the application.
	 * @return
	 */
	@JSONProperty(value="docType")
	public String getDocType() {
		return _docType;
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
	 * Returns the Revision of the {@link ASimpleEntity} or null, if it wasn't yet set (e.g. for unsaved {@link ASimpleEntity})
	 * @return Returns the Revision of the {@link ASimpleEntity} or null, if Revision is not set.
	 */
	@JSONProperty(value="_rev", ignoreIfNull = true)
	public String getRevision() {
		return _rev;
	}

	/**
	 * Returns the title
	 * @return the title
	 */
	@JSONProperty(value="title", ignoreIfNull=true)
	public String getTitle() {
		return _title;
	}


	public void setCreator(User creator) {
		_creator = creator;
	}

	/**
	 * Internal method necessary for CouchDB injection
	 * @param creatorId UID of the current revision's creator
	 */
	public void setCreatorId(String creatorId) {
		setCreator(creatorId != null ? new User(creatorId, null, null) : null);
	}

	/**
	 * This method is required for the CouchDB JSON injector to work.
	 * Just ignore it anywhere else in the application.
	 * 
	 * @param typeName Type name (e.g. "product" or "shop")
	 */
	public void setDocType(String typeName) {
		_docType = typeName;
	}

	/**
	 * Set the Entity ID
	 * @param entityId Entity ID
	 */
	public void setId(String entityId) {
		_id = entityId;
	}

	/**
	 * Set the Entity revision
	 * @param revision Revision ID
	 */
	public void setRevision(String revision) {
		_rev=revision;
	}

	/**
	 * Set the title
	 * @param title title
	 */
	public void setTitle(String title){
		_title = title;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ADocument [id: " + _id + ", rev: "+_rev+", title=" + _title + "]";
	}


	@Override
	public boolean equals(Object otherObject) {
		boolean rc = true;

		if (otherObject instanceof ADocument) {
			ADocument other = (ADocument) otherObject;
			if (!_equals(_id, other._id)) rc = false;
			else if (!_equals(_rev, other._rev)) rc = false;
			else if (!_equals(_creator, other._creator)) rc = false;
			else if (!_equals(_title, other._title)) rc = false;
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
