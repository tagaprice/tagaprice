package org.tagaprice.shared.entities;

import org.svenson.JSONProperty;
import org.tagaprice.shared.entities.ASimpleEntity;


/**
 * The {@link AEntity} class is used to FIND, CREATE and UPDATE different entities. It contains two constructors, one
 * for FIND and one for CREATE an {@link AEntity}.
 * If you like to UPDATE a {@link AEntity} you have to FIND a {@link AEntity} first. The server will set the
 * {@link User} and the RevisionID.
 * Don't change the RevisionID on the client (by hand). Only the server will change it by an UPDATE.
 * 
 */
public abstract class AEntity extends ASimpleEntity {
	private static final long serialVersionUID = 1L;

	private String _title;


	/**
	 * This constructor is used by the serialization algorithm
	 */
	public AEntity() {
	}

	/**
	 * <b>UPDATE and GET</b>
	 * This constructor is used by the server to fetch a {@link AEntity} after SAVING or FINDING a {@link AEntity}.
	 * 
	 * @param id
	 *            Unique EntityID
	 * @param title The title of the {@link AEntity}. It must not be null.
	 */
	public AEntity(String id, String revision, String title) {
		super(id, revision);
		setTitle(title);
	}

	/**
	 * <b>NEW</b>
	 * Is used to create a new {@link AEntity}
	 * 
	 * @param title
	 *            The title of the {@link AEntity}. Every {@link AEntity} needs a title. It must not be null.
	 */
	public AEntity(String title) {
		this(null, null, title);
	}


	/**
	 * Returns the title
	 * @return the title
	 */
	@JSONProperty(value="title")
	public String getTitle() {
		return _title;
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
		return "AEntity ["+super.toString()+", title=" + _title + "]";
	}


	@Override
	public boolean equals(Object otherObject) {
		boolean rc = true;

		if (otherObject instanceof AEntity) {
			AEntity other = (AEntity) otherObject;
			if (!super.equals(other)) rc = false;
			else if (!_equals(_title, other._title)) rc = false;
		}
		else rc = false;
		return rc;
	}


}
