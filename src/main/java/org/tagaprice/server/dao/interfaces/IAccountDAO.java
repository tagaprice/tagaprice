package org.tagaprice.server.dao.interfaces;

import org.tagaprice.shared.exception.DAOException;


/**
 * 
 * @author Martin Weik (afraidoferrors)
 * Describes methods to access and save Account Objects.
 * 
 */
public interface IAccountDAO {

	/**
	 * TODO change return type to Account (first check what Address in account is for)
	 * Retrieves the user id indicated by given mail and password.
	 * @param mail E-mail address of user.
	 * @param password Password as entered by user.
	 * @return If successful and password is valid, returns the user id indicated by given mail. If the user id could not be found or the password is invalid, returns -1.
	 * @throws DAOException
	 */
	long getUserIdByMailAndPassword(String mail, String password) throws DAOException;

	/**
	 * TODO change return type to Account (first check what Address in account is for)
	 * TODO it is possible to retrieve a uid from password: potential security leak and getUserIdByMailAndPassword is unnecessary
	 * Retrieves the user id indicated by given mail.
	 * @param mail E-mail address of user.
	 * @return If successful and password is valid, returns the user id indicated by given mail. If the user id could not be found, returns -1.
	 * @throws DAOException
	 */
	long getUserIdByMail(String mail) throws DAOException;


	//	/**
	//	 * Returns an Object of type Account if email is a valid emailaddress and exists in database.
	//	 * Otherwise it returns null.
	//	 * @param email a valid emailaddress
	//	 * @return an existing account or null
	//	 */
	//	public Account getByEmailadress(String email) throws IllegalArgumentException, DAOException;
	//	/**
	//	 * Returns an Object of type Account.
	//	 * @param id
	//	 * @return
	//	 */
	//	public Account getById(long id) throws DAOException;
	//
	//	/**
	//	 * Saves an object of type Account if Account is a valid Object.
	//	 * <ul>
	//	 * <li>TODO If the object is in a valid state defined by {@link Account} (Method?)
	//	 * <li>If the id of the given object is not set, the object will be stored as a new entry in database</li>
	//	 * <li>If the id of the given object is set and the id exists, the object will be updated in database</li>
	//	 * <li>If the id of the given object is set and the id does NOT exist, an {@link IllegalArgumentException} will be thrown</li>
	//	 *
	//	 * @see Account
	//	 * @param account
	//	 */
	//	public void save(Account account) throws IllegalArgumentException, DAOException;


}
