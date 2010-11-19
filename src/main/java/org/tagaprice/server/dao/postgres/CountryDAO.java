package org.tagaprice.server.dao.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.interfaces.ICountryDAO;
import org.tagaprice.shared.entities.Country;
import org.tagaprice.shared.exception.DAOException;

public class CountryDAO implements ICountryDAO {
	private DBConnection db;
	private static Logger _log = Logger.getLogger(CountryDAO.class);

	public CountryDAO(DBConnection db) {
		this.db = db;
	}

	public Country getByCountryCode(String code) throws DAOException {
		try {
			PreparedStatement pstmt = db.prepareStatement("SELECT country_code, title, localTitle FROM country WHERE country_code = ?");
			pstmt.setString(1, code);
			ResultSet res = pstmt.executeQuery();

			if (!res.next())
				return null;

			return new Country(res.getString("country_code"), res.getString("title"), res.getString("localtitle"));
		} catch (SQLException e) {
			String msg = "Failed to save entity. SQLException: "+e.getMessage()+".";
			CountryDAO._log.debug(e.getStackTrace());
			CountryDAO._log.error(msg+" Chaining and rethrowing.");
			throw new DAOException(msg, e);
		}
	}
}
