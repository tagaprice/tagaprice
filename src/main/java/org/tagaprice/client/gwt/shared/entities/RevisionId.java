package org.tagaprice.client.gwt.shared.entities;

import java.util.Date;

import org.tagaprice.client.gwt.shared.entities.dump.IUser;

/**
 * A {@link RevisionId} is used to
 * - FIND an {@link AEntity}, by the ID or ID and Revision
 * - UPDATE an {@link AEntity} by the ID and Revision
 */
public class RevisionId implements IRevisionId {


	private static final long serialVersionUID = 2011503142739304476L;
	private Long _id;
	private Long _rev;
	private IUser _user;
	private Date _date;

	public RevisionId(){

	}

	public RevisionId(long id) {
		setId(id);
	}

	public RevisionId(long id, long revision){
		this(id);
		setRevision(revision);
	}



	@Override
	public void setId(long id) {
		_id=id;
	}

	@Override
	public Long getId() {
		return _id;
	}

	@Override
	public void setRevision(long rev) {
		_rev=rev;
	}

	@Override
	public Long getRevision() {
		return _rev;
	}

	@Override
	public void setUser(IUser user) {
		_user=user;
	}

	@Override
	public IUser getUser() {
		return _user;
	}

	@Override
	public void setDate(Date date) {
		_date=date;
	}

	@Override
	public Date getDate() {
		return _date;
	}

}
