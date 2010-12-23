package org.tagaprice.client.gwt.shared.entities;


/**
 * The {@link AEntity} class is used to FIND, CREATE and UPDATE different entities. It contains two constructors, one
 * for FIND and one for CREATE an {@link AEntity}.
 * If you like to UPDATE a {@link AEntity} you have to FIND a {@link AEntity} first. The server will set the
 * {@link IUser} and the RevisionID.
 * Don't change the RevisionID on the client (by hand). Only the server will change it by an UPDATE.
 * 
 */
public abstract class AEntity implements IEntity {


	private static final long serialVersionUID = 1L;
	private long _id;
	private IRevisionId _revId;
	private String _title;


	/**
	 * This constructor is used by the serialization algorithm
	 */
	public AEntity() {
	}

	/**
	 * Is used to find a {@link AEntity} with a special id.
	 * 
	 * @param id
	 *            Unique EntityID
	 */
	public AEntity(long id) {
		setId(id);
	}

	/**
	 * Is used to create a new {@link AEntity}
	 * 
	 * @param title
	 *            The title of the {@link AEntity}. Every {@link AEntity} needs a title.
	 */
	public AEntity(String title) {
		setTitle(title);
	}

	@Override
	public long getId() {
		return _id;
	}

	@Override
	public void setId(long id) {
		_id = id;
	}




	@Override
	public String getTitle() {
		return _title;
	}

	@Override
	public void setTitle(String title) {
		_title = title;
	}


	@Override
	public IRevisionId getRevisionId() {
		return _revId;
	}

	@Override
	public void setRevisionId(IRevisionId revisionID) {
		_revId=revisionID;
	}



}
