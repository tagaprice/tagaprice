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
	private String title;
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
	
	public Entity(String title, int localeId) {
		this(null, 0, title, localeId);
	}
	
	/*public Entity(Long id, String name) {
		this(id, name, 0, );
	}*/
	
	public Entity(Long id, int rev, String title, int localeId) {
		this.id = id;
		this.title = title;
		this.rev = rev;
		this.localeId = localeId;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
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
			if (getTitle() != null) {
				if (!getTitle().equals(e.getTitle())) rc = false;
			}
			else if (e.getTitle() != null) rc = false;
			if (getRev() != e.getRev()) rc = false;
			if (getLocaleId() != e.getLocaleId()) rc = false;
		}
		else rc = false;

		return rc;
	}
}
