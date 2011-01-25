package org.tagaprice.client.gwt.shared.entities;


/**
 * The {@link AEntity} class is used to FIND, CREATE and UPDATE different entities. It contains two constructors, one
 * for FIND and one for CREATE an {@link AEntity}.
 * If you like to UPDATE a {@link AEntity} you have to FIND a {@link AEntity} first. The server will set the
 * {@link IUser} and the RevisionID.
 * Don't change the RevisionID on the client (by hand). Only the server will change it by an UPDATE.
 * 
 */
public abstract class AEntity<T> implements IEntity<T> {


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
	 * @param title The title of the {@link AEntity}. It must not be null.
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
	 *            The title of the {@link AEntity}. Every {@link AEntity} needs a title. It must not be null.
	 */
	public AEntity(String title) {
		this(null, title);
	}


	@Override
	public String getTitle() {
		return _title;
	}

	@Override
	public void setTitle(String title){
		_title = title;
	}


	@Override
	public IRevisionId getRevisionId() {
		return _revId;
	}

	/**
	 * Set the RevisionId of the {@link AEntity}.
	 * @param revisionID set the RevisionID.
	 */
	@Override
	public void setRevisionId(IRevisionId revisionID){
		_revId=revisionID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_revId == null) ? 0 : _revId.hashCode());
		result = prime * result + ((_title == null) ? 0 : _title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AEntity other = (AEntity) obj;
		if (_revId == null) {
			if (other._revId != null)
				return false;
		} else if (!_revId.equals(other._revId))
			return false;
		if (_title == null) {
			if (other._title != null)
				return false;
		} else if (!_title.equals(other._title))
			return false;
		return true;
	}


}
