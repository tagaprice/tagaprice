package org.tagaprice.shared.entities.accountmanagement;

import java.util.Date;

import org.svenson.JSONProperty;
import org.tagaprice.shared.entities.ASimpleEntity;

/**
 * Entity class representing a logged in user
 */
public class Session extends ASimpleEntity {
	private static final long serialVersionUID = 1L;
	private Date m_expirationDate;
	private String m_expirationString;
	
	/**
	 * Default constructor (for serialization)
	 */
	public Session() {
		
	}
	
	/**
	 * Constructor for newly created sessions
	 * @param user User the session belongs to
	 * @param expiresAt Session expiration time
	 */
	public Session(User user, Date expiresAt) {
		super(user, null, null);
		m_expirationDate = expiresAt;
	}

	/**
	 * Constructor for Session objects queried from the database
	 * @param user User the session belongs to
	 * @param id Session id
	 * @param rev Session revision
	 * @param expiresAt Session expiration time
	 */
	public Session(User user, String id, String rev, Date expiresAt) {
		super(user, id, rev);
		m_expirationDate = expiresAt;
	}
	
	/**
	 * Returns the current expiration date of this session
	 * @return Session expiration date
	 */
	@JSONProperty(ignore=true)
	public Date getExpirationDate() {
		return m_expirationDate;
	}
	
	/**
	 * Internal method (used by the injector only) that sets the Session's expiration date 
	 * @param expiresAt Session expiration timestamp
	 */
	public void setExpirationDate(Date expiresAt) {
		m_expirationDate = expiresAt;
	}
	
	/**
	 * Internal Expiration Date method necessary for the CouchDB DAO to work
	 * @return Date string that was set by calling setExpiresAt(String)
	 */
	@JSONProperty(value="expiresAt")
	public String getExpirationDateString() {
		return m_expirationString;
	}
	
	public void setExpiresAt(String dateString) {
		m_expirationString = dateString;
	}
}
