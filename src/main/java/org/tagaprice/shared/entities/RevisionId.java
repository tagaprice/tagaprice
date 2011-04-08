package org.tagaprice.shared.entities;

import java.io.Serializable;
import java.util.Date;

import org.tagaprice.shared.entities.accountmanagement.User;

/**
 * A {@link RevisionId} is used to
 * - FIND an {@link AEntity}, by the ID or ID and Revision
 * - UPDATE an {@link AEntity} by the ID and Revision
 */
public class RevisionId implements Serializable {
	private static final long serialVersionUID = 2011503142739304476L;
	private String _id = null;
	private String _rev = null;
	private User _user;
	private Date _date;

	/**
	 * This constructor is used by the serialization algorithm
	 */
	public RevisionId() {}

	/**
	 * <b>GET</b>
	 * Initializes Revision id with given productId and Revision 0L.
	 * @param id
	 */
	public RevisionId(String id) {
		this(id, null);
	}

	/**
	 * <b>UPDATE and GET</b>
	 * @param id
	 * @param revision
	 */
	public RevisionId(String id, String revision) {
		setId(id);
		setRevision(revision);
	}

	public void setId(String id) {
		_id = id;
	}

	public String getId() {
		return _id;
	}

	public void setRevision(String rev) {
		_rev = rev;
	}

	public String getRevision() {
		return _rev;
	}

	public void setUser(User user) {
		_user = user;
	}

	public User getUser() {
		return _user;
	}

	public void setDate(Date date) {
		_date = date;
	}

	public Date getDate() {
		return _date;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RevisionId [_id=" + _id + ", _rev=" + _rev + ", _user=" + _user + ", _date=" + _date + "]";
	}





}
