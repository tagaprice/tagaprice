package org.tagaprice.server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.Country;
import org.tagaprice.shared.exception.NotFoundException;

public class CountryDAO {
	private static CountryDAO instance = null;
	private DBConnection db;

	private CountryDAO(DBConnection db) {
		this.db = db;
	}
	
	public static CountryDAO getInstance(DBConnection db) {
		if (instance == null) instance = new CountryDAO(db);
		return instance;
	}
	
	public Country get(String code) throws SQLException, NotFoundException {
		PreparedStatement pstmt = db.prepareStatement("SELECT country_code, title, localTitle FROM country WHERE country_code = ?");
		pstmt.setString(1, code);
		ResultSet res = pstmt.executeQuery();
		
		if (!res.next()) throw new NotFoundException("Country not found: "+code);
		return new Country(res.getString("country_code"), res.getString("title"), res.getString("localtitle"));
	}
}
