package org.tagaprice.server.dao.interfaces;

import org.tagaprice.shared.AccountData;

/**
 * 
 * @author Martin Weik (afraidoferrors)
 * Describes methods to access and save AccountData Objects.
 * 
 */
public interface IAccountDAO {
	/**
	 * Returns an Object of type AccountData if email is a valid emailaddress and exists in database.
	 * Otherwise it returns null.
	 * @param email a valid emailaddress
	 * @return an existing account or null
	 */
	public AccountData getByEmailadress(String email) throws IllegalArgumentException;
	/**
	 * Returns an Object of type AccountData. 
	 * @param id 
	 * @return
	 */
	public AccountData getById(long id);
	
	/**
	 * Saves an object of type AccountData if AccountData is a valid Object.
	 * <ul>
	 * <li>TODO If the object is in a valid state defined by {@link AccountData} (Method?)
	 * <li>If the id of the given object is not set, the object will be stored as a new entry in database</li>
	 * <li>If the id of the given object is set and the id exists, the object will be updated in database</li>
	 * <li>If the id of the given object is set and the id does NOT exist, an {@link IllegalArgumentException} will be thrown</li>
	 * 
	 * @see AccountData
	 * @param account
	 */
	public void save(AccountData account) throws IllegalArgumentException;	

}
