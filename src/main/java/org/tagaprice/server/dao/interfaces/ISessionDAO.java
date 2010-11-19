package org.tagaprice.server.dao.interfaces;

import org.tagaprice.shared.entities.Session;
import org.tagaprice.shared.exception.DAOException;

public interface ISessionDAO {

	/**
	 * Saves given (user) session to the storage and returns a copy of the session.
	 * @param session Session to save. Both uid and sid must be set.
	 * @return If successful, returns a copy of given session, if not returns null.
	 * @throws DAOException
	 */
	Session save(Session session) throws DAOException;

	/**
	 * Retrieves the indicated (user) session for given sid.
	 * @param sid
	 * @return If successful, returns the indicated session for given sid. Otherwise returns null.
	 * @throws DAOException
	 */
	Session getBySessionId(String sid) throws DAOException;

	boolean delete(String sid) throws DAOException;

}
