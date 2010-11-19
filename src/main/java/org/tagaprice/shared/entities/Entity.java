/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License.
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
 */

/**
 * Project: TagAPriceUI
 * Filename: Entity.java
 * Date: May 17, 2010
 */
package org.tagaprice.shared.entities;

import org.tagaprice.server.dao.postgres.EntityDAO;
import org.tagaprice.shared.ISerializable;
import org.tagaprice.shared.SerializableArrayList;

/**
 * Abstract class holding general information which every entity deriving from this class has,
 * 
 * that is: an ID, a title, a revision number, a creatorID and creatorID for this revision, a localeID
 * and a list of properties.
 * 
 * Entities are under revision control, but only support a linear revision history. So they have no support for merging
 * different versions or creating multiple branches.
 */
public abstract class Entity implements ISerializable {
	private static final long serialVersionUID = 1L;

	private Long _id = null;
	private String _title;
	private int _rev = 0;
	private Integer _localeId = -1;
	private Long _creatorId = null;
	private Long _revCreatorId = null;

	private SerializableArrayList<Property> properties = new SerializableArrayList<Property>();

	/**
	 * default constructor (required for serialization)
	 */
	public Entity() {
		this(null, 0, null, null, null, null);
	}

	/**
	 * Constructor used to request the last revision of an {@link Entity} from the database.
	 * 
	 * @param id
	 *            {@link Entity} ID
	 */
	public Entity(Long id) {
		this(id, 0);
	}

	/**
	 * Constructor used to query a specific revision of an {@link Entity} from the database.
	 * 
	 * @param id
	 *            {@link Entity} ID
	 * @param rev
	 *            {@link Entity} revision (must be > 0)
	 */
	public Entity(Long id, int rev) {
		this(id, rev, null, null, null, null);
	}

	/**
	 * Constructor used to save an existing Entity into the database.
	 * 
	 * @param id
	 *            {@link Entity} ID
	 * @param rev
	 *            current {@link Entity} revision (will be checked by the EntityDAO) (must be > 0)
	 * @param title
	 *            (new) Entity title
	 * @param revCreatorId
	 *            revision's creator
	 */
	public Entity(Long id, int rev, String title, Long revCreatorId) {
		this(id, rev, title, null, null, revCreatorId);
	}

	/**
	 * Constructor for storing a new {@link Entity} in the database.
	 * 
	 * @param title
	 *            new Entity's title
	 * @param localeId
	 *            new Entity's locale
	 * @param creatorId
	 *            new Entity's creator
	 */
	public Entity(String title, int localeId, Long creatorId) {
		this(null, 0, title, localeId, creatorId, creatorId);
	}

	/**
	 * TODO Constructor to set all fields. This is used only for testing. We may want to remove this when refactoring tests.
	 * 
	 * @param id
	 *            {@link Entity} ID
	 * @param rev
	 *            revision number (must be > 0)
	 * @param title
	 *            Entity Title
	 * @param localeId
	 *            Entity locale
	 * @param creatorId
	 *            Entity creator ID
	 * @param revCreatorId
	 *            revision creator ID
	 */
	protected Entity(Long id, int rev, String title, Integer localeId, Long creatorId, Long revCreatorId) {
		_id = id;
		_title = title;
		_rev = rev;
		_localeId = localeId;
		_creatorId = creatorId;
		_revCreatorId = revCreatorId;
	}

	/**
	 * @return the ID of this {@link Entity}
	 */
	public Long getId() {
		return _id;
	}

	/**
	 * @return true, if this {@link Entity} has an ID, false otherwise.
	 */
	public boolean hasId() {
		return _id != null;
	}

	/**
	 * This method should just be used by {@link EntityDAO}
	 * Set the ID of this {@link Entity}.
	 * 
	 * @param id
	 *            new entity ID
	 */
	public void _setId(long id) {
		this._id = id;
	}

	/**
	 * @return title of this {@link Entity}
	 */
	public String getTitle() {
		return _title;
	}

	/**
	 * @param title
	 *            title of this {@link Entity}
	 */
	public void setTitle(String title) {
		this._title = title;
	}

	/**
	 * @return the revision number of this {@link Entity}
	 */
	public int getRev() {
		return _rev;
	}

	/**
	 * This method should just be used by {@link EntityDAO}
	 * Set the revision of this {@link Entity}.
	 * 
	 * @param rev
	 *            new revision
	 */
	public void setRev(int rev) {
		this._rev = rev;
	}

	/**
	 * @return the creatorID for this {@link Entity}
	 */
	public Long getCreatorId() {
		return _creatorId;
	}

	/**
	 * This method should just be called by {@link EntityDAO}
	 * Set the ID of the creator of this {@link Entity}.
	 * 
	 * @param creatorId
	 *            Creator ID
	 */
	public void setCreatorId(Long creatorId) {
		this._creatorId = creatorId;
	}

	/**
	 * @return the CreatorID of this revision
	 */
	public Long getRevCreatorId() {
		return _revCreatorId;
	}

	/**
	 * This method should just be called by {@link EntityDAO}
	 * Set the ID of the creator of this revision of this {@link Entity}.
	 * 
	 * @param revCreatorId
	 *            revision's creator ID
	 */
	public void setRevCreatorId(Long revCreatorId) {
		this._revCreatorId = revCreatorId;
	}

	/**
	 * @return the localeID
	 */
	public Integer getLocaleId() {
		return _localeId;
	}

	/**
	 * This method should just be used by {@link EntityDAO}
	 * Set the ID of the locale of this {@link Entity}.
	 * 
	 * @param localeId
	 *            new localeId
	 */
	public void setLocaleId(Integer localeId) {
		this._localeId = localeId;
	}

	/**
	 * @return the properties of this {@link Entity}
	 */
	public SerializableArrayList<Property> getProperties() {
		return properties;
	}

	/**
	 * @param properties
	 *            the properties to set
	 */
	public void setProperties(SerializableArrayList<Property> properties) {
		this.properties = properties;
	}


	/**
	 * compare two objects and return true if either both of them are null or they're equal
	 * 
	 * @param a
	 *            first Object
	 * @param b
	 *            second Object
	 * @return true if they're equal, false otherwise
	 */
	public static boolean _compare(Object a, Object b) {
		return a == null ? b == null : a.equals(b);
	}


	@Override
	public boolean equals(Object o) {
		boolean rc = true;

		if (rc && o instanceof Entity) {
			Entity e = (Entity) o;
			if (!Entity._compare(getId(), e.getId()))
				rc = false;
			if (getRev() != e.getRev())
				rc = false;
			if (!Entity._compare(getTitle(), e.getTitle()))
				rc = false;
			if (!Entity._compare(getLocaleId(), e.getLocaleId()))
				rc = false;
			if (!Entity._compare(getCreatorId(), e.getCreatorId()))
				rc = false;
			if (!Entity._compare(getRevCreatorId(), e.getRevCreatorId()))
				rc = false;
			if (!Entity._compare(getProperties(), e.getProperties()))
				rc = false;
		} else
			rc = false;

		return rc;
	}

	@Override
	public String toString() {
		return "Entity {\n" + "id: " + getId() + "\nrev: " + getRev() + "\ntitle: " + getTitle() + "\nlocale: "
		+ getLocaleId() + "\ncreator: " + getCreatorId() + "\nrevCreator: " + getRevCreatorId()
		+ "\nproperties: " + getProperties().toString() + "\n}\n";
	}

	/**
	 * Returns a shallow copy of this object with the revision increased by one. If original revision has been -1,
	 * revision of returned object will be set to 0.
	 * 
	 * @param <T>
	 * @return
	 */
	public abstract <T extends Entity> T newRevision();
}
