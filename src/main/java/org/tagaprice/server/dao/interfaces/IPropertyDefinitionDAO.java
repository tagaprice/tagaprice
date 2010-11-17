package org.tagaprice.server.dao.interfaces;

import org.tagaprice.shared.PropertyDefinition;
import org.tagaprice.shared.exception.DAOException;

public interface IPropertyDefinitionDAO {

	/**
	 * Retrieves the PropertyTypeDefinition indicated by given id.
	 * @param id Id to identify the PropertyTypeDefinition to retrieve.
	 * @return Returns the PropertyTypeDefinition indicated by given id or null if no PropertyTypeDefinition could be found.
	 * @throws DAOException
	 */
	PropertyDefinition getById(long id) throws DAOException;

	/**
	 * Retrieves the PropertyTypeDefinition indicated by given id and given rev.
	 * @param propertyTypeDefinitionId Id to identify the PropertyTypeDefinition to retrieve.
	 * @param revision Revision of PropertyTypeDefinition to retrieve.
	 * @return Returns the PropertyTypeDefinition indicated by given id and given revision or null if no PropertyTypeDefinition could be found.
	 * @throws DAOException
	 */
	PropertyDefinition getByIdAndRef(long id, int revision) throws DAOException;

	/**
	 * Retrieves the PropertyTypeDefinition indicated by given name and local id of associated entity..
	 * @param propertyTypeDefinitionId Id to identify the PropertyTypeDefinition to retrieve.
	 * @param revision Revision of PropertyTypeDefinition to retrieve.
	 * @return Returns the PropertyTypeDefinition indicated by given id and given revision or null if no PropertyTypeDefinition could be found.
	 * @throws DAOException
	 */
	PropertyDefinition getByNameAndLocalId(String name, int localeId) throws DAOException;
}
