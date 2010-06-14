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
package org.tagaprice.shared;

public abstract class Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id = null;
	private String name;
	private int rev = 0;
	private int localeId = -1;
	
	public Entity() {
		this(null, 0, null, -1);
	}
	
	/*public Entity(Long id) {
		this(id, null, 0);
	}*/
	
	public Entity(Long id, int rev, int localeId) {
		this(id, rev, null, localeId);
	}
	
	public Entity(String name, int localeId) {
		this(null, 0, name, localeId);
	}
	
	/*public Entity(Long id, String name) {
		this(id, name, 0, );
	}*/
	
	public Entity(Long id, int rev, String name, int localeId) {
		this.id = id;
		this.name = name;
		this.rev = rev;
		this.localeId = localeId;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getRev() {
		return rev;
	}
	
	public int getLocaleId() {
		return localeId;
	}
	
	public boolean equals(Object o) {
		boolean rc = true; // TODO set to true
		
		if (rc && o instanceof Entity) {
			Entity e = (Entity) o;
			if (e.getId() != getId()) rc = false;
			if (getName() != null) {
				if (!getName().equals(e.getName())) rc = false;
			}
			else if (e.getName() != null) rc = false;
			if (getRev() != e.getRev()) rc = false;
			if (getLocaleId() != e.getLocaleId()) rc = false;
		}
		else rc = false;

		return rc;
	}
}
