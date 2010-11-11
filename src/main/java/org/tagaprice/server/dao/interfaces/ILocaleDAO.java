package org.tagaprice.server.dao.interfaces;

import org.tagaprice.shared.Locale;
import org.tagaprice.shared.exception.DAOException;

/**
 * @author Martin Weik (afraidoferrors)
 * DAO vor getting and setting locales
 */
public interface ILocaleDAO {
	/**
	 * Gets a Locale by its id identified by an integer
	 * @param id an integer
	 * @return returns a Locale for this id or null if this id does not exist
	 * @throws DAOException
	 */
	public Locale getById(int id) throws DAOException;
	/**
	 * Gets a Locale by its name
	 * @param name identifiying String for the Locale
	 * @return returns a Locale for this name or null if this name does not exist
	 * @throws DAOException
	 */
	public Locale getByName(String name) throws DAOException;
	/**
	 * Stores a Locale to the database
	 * TODO be more precise
	 * @param locale
	 * @throws DAOException
	 */
	public void save(Locale locale) throws DAOException;

}
