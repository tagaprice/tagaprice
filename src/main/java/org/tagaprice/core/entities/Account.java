package org.tagaprice.core.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="account")
@PrimaryKeyJoinColumn(name="uid")
public class Account extends RevisionableEntity {
	private String _email;
	private Date _lastLogin;
	private Boolean _locked;

	public Account(Long id, String title, Locale locale, Date createdAt, int revisionNumber, Account creator,
			Group group, String email, Date lastLogin, Boolean locked) {
		super(id, title, locale, createdAt, revisionNumber, creator, group);
		_email = email;
		_lastLogin = lastLogin;
		_locked = locked;
	}

	@Override
	public Integer getRevisionNumber() {
		return 0;
	}

	@Column(name="mail")
	public String getEmail() {
		return _email;
	}
	@SuppressWarnings("unused")
	private void setEmail(String email) {
		_email = email;
	}

	@Column(name="last_login")
	public Date getLastLogin() {
		return _lastLogin;
	}
	@SuppressWarnings("unused")
	private void setLastLogin(Date lastLogin) {
		_lastLogin = lastLogin;
	}

	@Column(name="locked")
	public Boolean isLocked() {
		return _locked;
	}
	@SuppressWarnings("unused")
	private void setLocked(Boolean locked) {
		_locked = locked;
	}
}
