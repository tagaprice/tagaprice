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
	private String _password = null;

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
	
	public boolean checkPassword(String password) {
		return _password.equals(password);
	}
	
	@JSONProperty(value="password")
	public String getPassword(String password) {
		return _password;
	}
	
	public void setPassword(String password) {
		_password = password;
	}
	
	@Override
	public boolean equals(Object otherObject) {
		boolean rc = true;
		if (otherObject instanceof User) {
			User other = (User) otherObject;
			if (!super.equals(other)) rc = false;
			else if (!_equals(_mail, other._mail)) rc = false;
			else if (!_equals(_password, other._password)) rc = false;
		}
		else rc = false;
		
		return rc;
	}
}
