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
package org.tagaprice.shared.data;

import org.tagaprice.shared.SerializableArrayList;
import org.tagaprice.shared.Serializable;

/**
 * Abstract class holding general information which every entity has,
 * that is: an ID, a title, a revision number, a createrID and creatorID for this revision,
 * a localeID and a list of properties.
 */
public abstract class Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id = null;
	private String title;
	private int rev = 0;
	private Integer localeId = -1;
	private Long creatorId = null;
	private Long revCreatorId = null;

	/** TODO {@link SerializableArrayList} is used as a list for properties. this may should be refactored... */
	private SerializableArrayList<Property> properties = new SerializableArrayList<Property>(); 

	/**
	 * default constructor (required for serialization)
	 */
	public Entity() {
		this(null, 0, null, null, null, null);
	}
	
	/**
	 * constructor used to request the last revision of an Entity from the database
	 * (in combination with EntityDAO)
	 * @param id Entity ID 
	 */
	public Entity(Long id) {
		this(id, 0);
	}
	
	/**
	 * constructor used to query a specific revision of an Entity from the database
	 * (in combination with EntityDAO)
	 * @param id Entity ID
	 * @param rev Entity revision (must be > 0)
	 */
	public Entity(Long id, int rev) {
		this(id, rev, null, null, null, null);
	}
	
	/**
	 * Constructor used to save an existing Entity into the database
	 *
	 * @param id Entity ID
	 * @param rev current entity revision (will be checked by the EntityDAO)
	 * @param title (new) Entity title
	 * @param revCreatorId revision's creator
	 */
	public Entity(Long id, int rev, String title, Long revCreatorId) {
		this(id, rev, title, null, null, revCreatorId);
	}
	
	/**
	 * constructor for storing a new entity in the database 
	 * @param title new Entity's title
	 * @param localeId new Entity's locale
	 * @param creatorId new Entity's creator
	 */
	public Entity(String title, int localeId, Long creatorId) {
		this(null, 0, title, localeId, creatorId, creatorId);
	}
	
	/**
	 * full constructor
	 * @param id Entity ID
	 * @param rev revision number (must be > 0)
	 * @param title Entity Title
	 * @param localeId Entity locale
	 * @param creatorId Entity creator ID
	 * @param revCreatorId revision creator ID
	 */
	public Entity(Long id, int rev, String title, Integer localeId, Long creatorId, Long revCreatorId) {
		this.id = id;
		this.title = title;
		this.rev = rev;
		this.localeId = localeId;
		this.creatorId = creatorId;
		this.revCreatorId = revCreatorId;
	}
	
	/**
	 * @return the ID of this entity
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @return true, if this entity has an ID, false otherwise.
	 */
	public boolean hasId() {
		return id != null;
	}
	
	/**
	 * This method should just be used by EntityDAO
	 * @param id new entity ID
	 */
	public void _setId(long id) {
		this.id = id;
	}
	
	/**
	 * @return title of this entity
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @param title title of this entity
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * @return the revision number of this entity
	 */
	public int getRev() {
		return rev;
	}
	
	/**
	 * This method should just be used by EntityDAO
	 * @param rev new revision
	 */
	public void _setRev(int rev) {
		this.rev = rev;
	}
	
	/**
	 * @return the creatorID for this entity
	 */
	public Long getCreatorId() {
		return creatorId;
	}
	
	/**
	 * This method should just be called by EntityDAO
	 * @param creatorId Creator ID
	 */
	public void _setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	
	/**
	 * @return the CreatorID of this revision
	 */
	public Long getRevCreatorId() {
		return revCreatorId;
	}
	
	/**
	 * This method should just be called by EntityDAO
	 * @param revCreatorId revision's creator ID
	 */
	public void _setRevCreatorId(Long revCreatorId) {
		this.revCreatorId = revCreatorId;
	}
	
	/**
	 * @return the localeID
	 */
	public Integer getLocaleId() {
		return localeId;
	}
	
	/**
	 * This method should just be used by EntityDAO
	 * @param localeId new localeId
	 */
	public void _setLocaleId(Integer localeId) {
		this.localeId = localeId;
	}
		
	/**
	 * @return the properties of this entity
	 */
	public SerializableArrayList<Property> getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(SerializableArrayList<Property> properties) {
		this.properties = properties;
	}
	
	public boolean equals(Object o) {
		boolean rc = true;
		
		if (rc && o instanceof Entity) {
			Entity e = (Entity) o;
			if (!_compare(getId(), e.getId())) rc = false;
			if (getRev() != e.getRev()) rc = false;
			if (!_compare(getTitle(), e.getTitle())) rc = false;
			if (!_compare(getLocaleId(), e.getLocaleId())) rc = false;
			if (!_compare(getCreatorId(), e.getCreatorId())) rc = false;
			if (!_compare(getRevCreatorId(), e.getRevCreatorId())) rc = false;
			if (!_compare(getProperties(), e.getProperties())) rc = false;
		}
		else rc = false;

		return rc;
	}
	
	/**
	 * compare two objects and return true if either both of them are null or they're equal
	 * @param a first Object
	 * @param b second Object
	 * @return true if they're equal, false otherwise
	 */
	public static boolean _compare(Object a, Object b) {
		return a == null ? b == null : a.equals(b);
	}
	
	public String toString() {
		return "Entity {\n" +
				"id: " + getId() +
				"\nrev: " + getRev() +
				"\ntitle: "+getTitle() +
				"\nlocale: "+getLocaleId() +
				"\ncreator: "+getCreatorId()+
				"\nrevCreator: "+getRevCreatorId()+
				"\nproperties: "+getProperties().toString()+
				"\n}\n";
	}
}
