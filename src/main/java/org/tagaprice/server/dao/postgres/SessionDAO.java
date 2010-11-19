package org.tagaprice.server.dao.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.interfaces.ISessionDAO;
import org.tagaprice.shared.entities.Session;
import org.tagaprice.shared.exception.DAOException;

public class SessionDAO implements ISessionDAO {
	private DBConnection _db;
	private static Logger _log = Logger.getLogger(SessionDAO.class);


	@Override
	public Session save(Session session) throws DAOException {
		SessionDAO._log.debug("session:"+session);
		// users are allowed to have several active sessions simultaneously
		String sql = "INSERT INTO session (uid, sid) VALUES (?, ?)";
		try {
			PreparedStatement pstmt = _db.prepareStatement(sql);
			pstmt.setLong(1, session.getUserId());
			pstmt.setString(2, session.getSessionId());
			pstmt.executeUpdate();
			return new Session(session);
		} catch (SQLException e) {
			String msg = "Failed to save session to database. SQLException: "+e.getMessage()+".";
			SessionDAO._log.error(msg + " Chaining and rethrowing.");
			SessionDAO._log.debug(e.getStackTrace());
			throw new DAOException(msg, e);
		}
	}

	@Override
	public boolean delete(String sid) throws DAOException {
		String sql = "DELETE FROM session WHERE sid = ?";
		try {
			PreparedStatement pstmt = _db.prepareStatement(sql);
			pstmt.setString(1, sid);

			if(pstmt.executeUpdate() != 1)
				return false;

			return true;
		} catch (SQLException e) {
			String msg = "Failed to delete session from database. SQLException: "+e.getMessage()+".";
			SessionDAO._log.error(msg + " Chaining and rethrowing.");
			SessionDAO._log.debug(e.getStackTrace());
			throw new DAOException(msg, e);
		}
	}


	@Override
	public Session getBySessionId(String sid) throws DAOException {
		SessionDAO._log.debug("sid:"+sid);

		String sql = "SELECT uid FROM session WHERE sid = ?";

		//TODO Check time slice.

		try {
			PreparedStatement pstmt = _db.prepareStatement(sql);
			pstmt.setString(1, sid);
			ResultSet res = pstmt.executeQuery();

			if(!res.next()) {
				return null;
			}
			return new Session(res.getString("sid"), res.getLong("uid"));
		} catch (SQLException e) {
			String msg = "Failed to retrieve uid from database. SQLException: "+e.getMessage()+".";
			SessionDAO._log.error(msg + " Chaining and rethrowing.");
			SessionDAO._log.debug(e.getStackTrace());
			throw new DAOException(msg, e);
		}
	}

}
