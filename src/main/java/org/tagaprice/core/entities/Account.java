package org.tagaprice.core.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="entityrevision")
@SecondaryTables({
	@SecondaryTable(name="entity", pkJoinColumns= {@PrimaryKeyJoinColumn(name="ent_id", referencedColumnName="ent_id")})
	,
	@SecondaryTable(name="account", pkJoinColumns= {@PrimaryKeyJoinColumn(name="ent_id", referencedColumnName="uid")})

})
public class Account {
	private Long _id = null;
	private String _title;
	private Locale _locale = null;
	private Date _createdAt = null;
	private Integer _revisionNumber = null;
	private Account _creator = null;
	private Group _group;

	private String _email;
	private Date _lastLogin;
	private Boolean _locked;


	public Account() { }


	public Account(Long id, String title, Locale locale, Date createdAt, Integer revisionNumber, Account creator,
			Group group, String email, Date lastLogin, Boolean locked) {
		_id = id;
		_title = title;
		_locale = locale;
		_createdAt = createdAt;
		_revisionNumber = revisionNumber;
		_creator = creator;
		_group = group;
		_email = email;
		_lastLogin = lastLogin;
		_locked = locked;
	}

	@Id
	@Column(name="ent_id")
	public Long getId() {
		return _id;
	}
	@SuppressWarnings("unused")
	private void setId(Long id) {
		this._id = id;
	}

	@Column(name="title")
	public String getTitle() {
		return _title;
	}
	@SuppressWarnings("unused")
	private void setTitle(String title) {
		this._title = title;
	}

	@ManyToOne
	@JoinColumn(name = "locale_id")
	public Locale getLocale() {
		return _locale;
	}
	@SuppressWarnings("unused")
	private void setLocale(Locale locale) {
		this._locale = locale;
	}

	@Column(name="created_at")
	public Date getCreatedAt() {
		return _createdAt;
	}
	@SuppressWarnings("unused")
	private void setCreatedAt(Date createdAt) {
		this._createdAt = createdAt;
	}

	@Id
	@Column(name="rev")
	public Integer getRevisionNumber() {
		return 0;
	}
	@SuppressWarnings("unused")
	private void setRevisionNumber(Integer revisionNumber) {
		return;
	}

	@ManyToOne
	@JoinColumn(name = "creator")
	public Account getCreator() {
		return _creator;
	}
	@SuppressWarnings("unused")
	private void setCreator(Account creator) {
		this._creator = creator;
	}

	@Transient
	public Group getGroup() {
		return _group;
	}
	@SuppressWarnings("unused")
	private void setGroup(Group group) {
		this._group = group;
	}

	@Column(table="account",name="mail")
	public String getEmail() {
		return _email;
	}
	@SuppressWarnings("unused")
	private void setEmail(String email) {
		_email = email;
	}

	@Column(table="account",name="last_login")
	public Date getLastLogin() {
		return _lastLogin;
	}
	@SuppressWarnings("unused")
	private void setLastLogin(Date lastLogin) {
		_lastLogin = lastLogin;
	}

	@Column(table="account",name="locked")
	public Boolean isLocked() {
		return _locked;
	}
	@SuppressWarnings("unused")
	private void setLocked(Boolean locked) {
		_locked = locked;
	}
}
