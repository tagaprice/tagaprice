package org.tagaprice.client.gwt.shared.entities;

/**
 * The {@link ASEntity} class is used to FIND, CREATE and UPDATE different entities. It contains two constructors, one
 * for FIND and one for CREATE an {@link ASEntity}.
 * If you like to UPDATE a {@link ASEntity} you have to FIND a {@link ASEntity} first. The server will set the
 * {@link IUser} and the RevisionID.
 * Don't change the RevisionID on the client (by hand). Only the server will change it by an UPDATE.
 * 
 */
public abstract class ASEntity<T> implements ISEntity<T> {

	private IRevisionId _revId;

	/**
	 * <b>NEW</b>
	 * Is used to create a new {@link ASEntity}
	 */
	public ASEntity() {
		setRevisionId(null);
	}

	/**
	 * <b>UPDATE and GET</b>
	 * This constructor is used by the server to fetch a {@link ASEntity} after SAVING or FINDING a {@link ASEntity}.
	 * @param revisionID
	 */
	public ASEntity(IRevisionId revisionID){
		setRevisionId(revisionID);
	}


	@Override
	public IRevisionId getRevisionId() {
		return _revId;
	}


	@Override
	public void setRevisionId(IRevisionId revisionID){
		_revId=revisionID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ASEntity [_revId=" + _revId + "]";
	}


}
