package org.tagaprice.shared.entities;

import java.io.Serializable;

import org.svenson.JSONProperty;

public interface ISEntity extends Serializable {

	@JSONProperty(value="_id")
	public String getId();

	/**
	 * Returns the Revision of the {@link ISEntity} or null, if it wasn't yet set (e.g. for unsaved {@link ISEntity Entities})
	 * @return Returns the Revision of the {@link ISEntity} or null, if IRevisionId is not set.
	 */
	@JSONProperty(value="_rev")
	public String getRevision() ;

	/**
	 * Set the Entity ID
	 * @param entityId Entity ID
	 */
	public void setId(String entityId);
	
	/**
	 * Set the Entity revision
	 * @param revision Revision ID
	 */
	public void setRevision(String revision);
}
