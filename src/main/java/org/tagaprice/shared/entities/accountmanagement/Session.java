package org.tagaprice.shared.entities.accountmanagement;

import java.util.Date;

import org.tagaprice.shared.entities.ASimpleEntity;

/**
 * Entity class representing a logged in user
 */
public class Session extends ASimpleEntity {
	private static final long serialVersionUID = 1L;
	private Date m_expiresAt;
	
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
		m_expiresAt = expiresAt;
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
		m_expiresAt = expiresAt;
	}
	
	/**
	 * Returns the current expiration date of this session
	 * @return Session expiration date
	 */
	public Date getExpiresAt() {
		return m_expiresAt;
	}
	
	/**
	 * Internal method (used by the injector only) that sets the Session's expiration date 
	 * @param expiresAt Session expiration timestamp
	 */
	public void setExpiresAt(Date expiresAt) {
		m_expiresAt = expiresAt;
	}
}
