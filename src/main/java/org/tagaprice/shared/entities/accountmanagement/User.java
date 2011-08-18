package org.tagaprice.shared.entities.accountmanagement;

import org.svenson.JSONProperty;
import org.tagaprice.shared.entities.AEntity;

/**
 * Simple class that represents any kind of entity-creating user.
 * A User is identified by it's UID (see {@link AEntity.getId()}.
 * The UI will display the user name returned by {@link ASimpleEntity.getId()}
 */
public class User extends AEntity {
	private static final long serialVersionUID = 1L;
	
	private String _mail = null;
	private String _passwordHash = null;
	private String _passwordSalt = null;
	private boolean _confirmed = false;
	private String _confirm = null;
	private String _confirmSalt = null;

	/**
	 * @return the confirm
	 */
	public String getConfirm() {
		return _confirm;
	}

	/**
	 * @param confirm the confirm to set
	 */
	public void setConfirm(String confirm) {
		_confirm = confirm;
	}

	/**
	 * @return the confirmSalt
	 */
	public String getConfirmSalt() {
		return _confirmSalt;
	}

	/**
	 * @param confirmSalt the confirmSalt to set
	 */
	public void setConfirmSalt(String confirmSalt) {
		_confirmSalt = confirmSalt;
	}

	/**
	 * Default constuctor (necessary for serialization)
	 */
	public User() {
		super();
	}
	
	/**
	 * Constructor for new User instances
	 * @param title Descriptive User name (e.g. "John Doe")
	 */
	public User(String title) {
		super(null, title);
	}

	/**
	 * Constructor for User instances queried from the database
	 * @param uid User ID
	 * @param revision Entity revision
	 * @param title Descriptive User name (e.g. "John Doe")
	 */
	public User(String uid, String revision, String title) {
		super(null, uid, revision, title);
	}
	
	@JSONProperty(value="mail", ignoreIfNull=true)
	public String getMail() {
		return _mail;
	}
	
	public void setMail(String mail) {
		_mail = mail;
	}
	
	@JSONProperty(value="passwordHash", ignoreIfNull=true)
	public String getPasswordHash() {
		return _passwordHash;
	}
	
	@JSONProperty(value="passwordSalt", ignoreIfNull=true)
	public String getPasswordSalt() {
		return _passwordSalt;
	}
	
	public void setPasswordHash(String passwordHash) {
		_passwordHash = passwordHash;
	}
	
	public void setPasswordSalt(String passwordSalt) {
		_passwordSalt = passwordSalt;
	}
	
	
	/**
	 * @return the confirmed
	 */
	public boolean isConfirmed() {
		return _confirmed;
	}

	/**
	 * @param confirmed the confirmed to set
	 */
	public void setConfirmed(boolean confirmed) {
		_confirmed = confirmed;
	}

	@Override
	public boolean equals(Object otherObject) {
		boolean rc = true;
		if (otherObject instanceof User) {
			User other = (User) otherObject;
			if (!super.equals(other)) rc = false;
			else if (!_equals(_mail, other._mail)) rc = false;
			else if (!_equals(_passwordHash, other._passwordHash)) rc = false;
			else if (!_equals(_passwordSalt, other._passwordSalt)) rc = false;
		}
		else rc = false;
		
		return rc;
	}
}
