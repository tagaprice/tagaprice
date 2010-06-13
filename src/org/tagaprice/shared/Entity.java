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
	
	public Entity (Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
}
