package org.tagaprice.server.dao.interfaces;

import org.tagaprice.shared.entities.Country;
import org.tagaprice.shared.exception.DAOException;

public interface ICountryDAO {

	/**
	 * Retrieves the country indicated by given countryCode.
	 * @param countryCode The country code to identify a country. Length must be limited to 2 characters.
	 * @return If successful, returns country indicated by given countryCode. Otherwise returns null.
	 * @throws DAOException
	 */
	Country getByCountryCode(String countryCode) throws DAOException;

}
