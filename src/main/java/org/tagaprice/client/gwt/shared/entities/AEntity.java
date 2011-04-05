package org.tagaprice.client.gwt.shared.entities;


/**
 * The {@link AEntity} class is used to FIND, CREATE and UPDATE different entities. It contains two constructors, one
 * for FIND and one for CREATE an {@link AEntity}.
 * If you like to UPDATE a {@link AEntity} you have to FIND a {@link AEntity} first. The server will set the
 * {@link IUser} and the RevisionID.
 * Don't change the RevisionID on the client (by hand). Only the server will change it by an UPDATE.
 * 
 */
public abstract class AEntity<T> extends ASEntity<T> {

	private IRevisionId _revId;
	private String _title;


	/**
	 * This constructor is used by the serialization algorithm
	 */
	public AEntity() {
	}

	/**
	 * <b>SERVER USE ONLY</b>
	 * This constructor is used by the server to fetch a {@link AEntity} after SAVING or FINDING a {@link AEntity}.
	 * 
	 * @param id
	 *            Unique EntityID
	 * @param title The title of the {@link AEntity}. It must not be null.
	 */
	public AEntity(IRevisionId revisionId, String title) {
		super(revisionId);
		setTitle(title);

	}

	/**
	 * <b>CLIENT USE ONLY</b>
	 * Is used to create a new {@link AEntity}
	 * 
	 * @param title
	 *            The title of the {@link AEntity}. Every {@link AEntity} needs a title. It must not be null.
	 */
	public AEntity(String title) {
		this(null, title);
	}



	public String getTitle() {
		return _title;
	}


	public void setTitle(String title){
		_title = title;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AEntity [_revId=" + _revId + ", _title=" + _title + "]";
	}



}
