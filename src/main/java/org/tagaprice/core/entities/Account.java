package org.tagaprice.core.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name="account")
@SuppressWarnings("unused")
public class Account {
	private Long _uid = null;
	private String _email;
	private Date _lastLogin;


	public Account() { }


	public Account(Long uid, String email, Date lastLogin) {
		_uid = uid;
		_email = email;
		_lastLogin = lastLogin;
	}

	@Id
	public Long getUid() {
		return _uid;
	}
	private void setUid(Long uid) {
		_uid = uid;
	}

	@Column(name="mail")
	public String getEmail() {
		return _email;
	}
	private void setEmail(String email) {
		_email = email;
	}

	@Column(name="last_login")
	public Date getLastLogin() {
		return _lastLogin;
	}
	private void setLastLogin(Date lastLogin) {
		_lastLogin = lastLogin;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_email == null) ? 0 : _email.hashCode());
		result = prime * result + ((_lastLogin == null) ? 0 : _lastLogin.hashCode());
		result = prime * result + ((_uid == null) ? 0 : _uid.hashCode());
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
		Account other = (Account) obj;
		if (_email == null) {
			if (other._email != null)
				return false;
		} else if (!_email.equals(other._email))
			return false;
		if (_lastLogin == null) {
			if (other._lastLogin != null)
				return false;
		} else if (!_lastLogin.equals(other._lastLogin))
			return false;
		if (_uid == null) {
			if (other._uid != null)
				return false;
		} else if (!_uid.equals(other._uid))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Account [_uid=" + _uid + ", _email=" + _email + ", _lastLogin=" + _lastLogin + "]";
	}


}
