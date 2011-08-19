package org.tagaprice.shared.entities;

import org.svenson.JSONProperty;
import org.tagaprice.shared.entities.ASimpleEntity;
import org.tagaprice.shared.entities.accountmanagement.User;


/**
 * The {@link ADocument} class is used to FIND, CREATE and UPDATE different documents. It contains two constructors, one
 * for FIND and one for CREATE an {@link ADocument}.
 * If you like to UPDATE a {@link ADocument} you have to FIND a {@link ADocument} first. The server will set the
 * {@link User} and the RevisionID.
 * Don't change the RevisionID on the client (by hand). Only the server will change it by an UPDATE.
 * 
 */
public abstract class ADocument extends ASimpleEntity {
	private static final long serialVersionUID = 1L;

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
		super(creator, id, revision);
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
	 * Returns the title
	 * @return the title
	 */
	@JSONProperty(value="title", ignoreIfNull=true)
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
		return "ADocument ["+super.toString()+", title=" + _title + "]";
	}


	@Override
	public boolean equals(Object otherObject) {
		boolean rc = true;

		if (otherObject instanceof ADocument) {
			ADocument other = (ADocument) otherObject;
			if (!super.equals(other)) rc = false;
			else if (!_equals(_title, other._title)) rc = false;
		}
		else rc = false;
		return rc;
	}



}
