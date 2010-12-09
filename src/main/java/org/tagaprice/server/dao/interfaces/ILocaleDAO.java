package org.tagaprice.server.dao.interfaces;

import org.tagaprice.shared.entities.Locale;
import org.tagaprice.shared.exception.DAOException;

/**
 * @author Martin Weik (afraidoferrors) DAO vor getting and setting locales
 */
public interface ILocaleDAO {
	/**
	 * Gets a Locale by its id identified by an integer
	 * 
	 * @param id
	 *            an integer
	 * @return returns a Locale for this id or null if this id does not exist
	 * @throws DAOException
	 */
	public Locale getById(int id) throws DAOException;

	/**
	 * Gets a Locale by its full language name in its own language (e.g. "English", "Deutsch").
	 * 
	 * @param name
	 *            identifiying String for the Locale
	 * @return returns a Locale for this name or null if this name does not
	 *         exist
	 * @throws DAOException
	 */
	public Locale getByFullLanguageName(String name) throws DAOException;
	/**
	 * Gets a Locale by it's Countrycode and it's Languagecode, see http://download.oracle.com/javase/6/docs/api/java/util/Locale.html for valid codes.
	 * @param language the languagecode, see http://www.loc.gov/standards/iso639-2/englangn.html
	 * @param country the countrycode, see http://www.iso.ch/iso/en/prods-services/iso3166ma/02iso-3166-code-lists/list-en1.html
	 * @return
	 * @throws DAOException
	 */
	public Locale getByCountryAndLanguageCode(String country, String language) throws DAOException;

	/**
	 * Gets a Locale by its Languagecode, see {@link http://download.oracle.com/javase/6/docs/api/java/util/Locale.html} for valid codes.
	 * @param language the languagecode, see {@link http://www.loc.gov/standards/iso639-2/englangn.html}
	 * @return
	 * @throws DAOException
	 */
	public Locale getByLanguageCode(String language) throws DAOException;

	/**
	 * Stores a Locale to the database TODO be more precise
	 * 
	 * @param locale
	 * @throws DAOException
	 */
	public void save(Locale locale) throws DAOException;

}
