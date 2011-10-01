package org.tagaprice.server.dao;

import org.tagaprice.server.dao.couchdb.InvitationDao.InvitationRequest;
import org.tagaprice.shared.entities.accountmanagement.User;

public interface IInvitationDao {

	
	/**
	 * Check an invitation code and return true if it is usable
	 * @param key Invitation code
	 * @return True if the key is usable, false otherwise (invalid or already used)
	 */
	public boolean checkKey(String key);
	
	/**
	 * Mark the given invitation code used by <i>user</i> 
	 * @param key Invitation code
	 * @param user User that activated his/her profile with the given key
	 * @return True if the key was used successfully, false if it was invalid or has already been used
	 */
	public boolean useKey(String key, User user);
	
	/**
	 * Create an invitation code that the given user can use to invite other people
	 * @param user User that issues the invitation code
	 * @return Invitation code
	 */
	public String generateKey(User user);

	/**
	 * Create an {@link InvitationRequest} document with a mail address to which the invitation will
	 * be sent as soon as there are more slots available
	 * 
	 * @param mail E-Mail address
	 */
	void requestInvitation(String mail);
}
