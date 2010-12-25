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
	 */
	public AEntity(IRevisionId revisionId, String title) {
		setRevisionId(revisionId);
		setTitle(title);

	}

	/**
	 * <b>CLIENT USE ONLY</b>
	 * Is used to create a new {@link AEntity}
	 * 
	 * @param title
	 *            The title of the {@link AEntity}. Every {@link AEntity} needs a title.
	 */
	public AEntity(String title) {
		this(null, title);
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
	public IRevisionId getRevisionId() throws NullPointerException {
		if(_revId==null) throw new NullPointerException("Revision and ID are not set.");
		return _revId;
	}

	/**
	 * Set the RevisionId of the {@link AEntity}.
	 * @param revisionID set the RevisionID.
	 */
	private void setRevisionId(IRevisionId revisionID){
		_revId=revisionID;
	}



}