package org.tagaprice.core.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <p>
 * This class represents an user-account of some type. For instance this type can be local (saved in our database) or
 * OAuth or similar. Different user-account types should subclass this class.
 * </p>
 * 
 * <p>
 * An {@link Account} has the following properties:
 * <ul>
 * <li>Uid: primary identifier in the database</li>
 * <li>email: email-address of the user who owns this account. This must be unique since it represents this user</li>
 * <li>lastLogin: date of the last login of this user</li>
 * </ul>
 * </p>
 * 
 * <p>
 * This class is immutable. Properties once set, cannot be changed.
 * </p>
 * 
 * 
 * @author haja
 * 
 */
@Entity
@Table(name = "account")
@SuppressWarnings("unused")
public class Account {

	private Long _uid = null;
	private String _email;
	private Date _lastLogin;
	private String _password;

	/**
	 * this constructor is need for hibernate.
	 */
	protected Account() {
	}

	/**
	 * Initialize a new {@link Account}.
	 * 
	 * @param uid
	 *            Uid of this account. Can be null, in which case this account is treated as new concerning the database
	 *            and a fresh id will be created and assigend. If uid is not null it must not be greater than 0.
	 * @param email
	 *            email-address of the user who owns this account. This must be unique since it represents this user
	 * @param lastLogin
	 *            date of the last login of this user
	 */
	public Account(Long uid, String email, String password, Date lastLogin) {
		_uid = uid;
		_email = email;
		_password = password;
		_lastLogin = lastLogin;
	}

	@Id
	public Long getUid() {
		return _uid;
	}

	private void setUid(Long uid) {
		_uid = uid;
	}


	@Column(name = "email")
	public String getEmail() {
		return _email;
	}

	private void setEmail(String email) {
		_email = email;
	}

	@Column(name = "password")
	private String getPassword() {
		return _password;
	}
	public void setPassword(String password) {
		_password = password;
	}


	@Column(name = "last_login")
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
