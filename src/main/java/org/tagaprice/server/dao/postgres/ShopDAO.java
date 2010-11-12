package org.tagaprice.server.dao.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.log4j.Logger;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.CountryDAO;
import org.tagaprice.server.dao.interfaces.IShopDAO;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.exception.DAOException;
import org.tagaprice.shared.exception.NotFoundException;

public class ShopDAO implements IShopDAO {
	private DBConnection _db;
	private EntityDAO _entityDAO;
	private CountryDAO _countryDAO;
	private static Logger _log = Logger.getLogger(ShopDAO.class);
	
	public ShopDAO(DBConnection db) {
		this._db = db;
		_entityDAO = new EntityDAO(db);
		_countryDAO = new CountryDAO(db);
	}

	@Override
	public ShopData getById(long id) throws DAOException {
		return getByIdAndRef(id, 0);
	}
	

	@Override
	public ShopData getByIdAndRef(long id, long rev) throws DAOException {
		_log.debug("id:"+id);
		//Get Entity Data
		ShopData shop;
		shop = _entityDAO.getByIdAndRev(new ShopData(), id, rev);
		if(shop == null) return null;

		// TODO implement fetching of a specific shop revision
		//Get Shop Data
		String sql = "SELECT type_id, imageUrl, lat, lng, street, city, country_code " +
				"FROM shopRevision " +
				"INNER JOIN ENTITY ON(ent_id = shop_id) " +
				"WHERE shop_id = ? AND rev = ?";
		PreparedStatement pstmt;
		try {
			pstmt = _db.prepareStatement(sql);
			pstmt.setLong(1, shop.getId());
			pstmt.setLong(2, shop.getRev());
			ResultSet res = pstmt.executeQuery();

			if (!res.next()) return null;

			if (res.getString("type_id") != null) shop.setTypeId(res.getLong("type_id"));
			else shop.setTypeId(null);

			shop.setImageSrc(res.getString("imageurl"));

			if (res.getString("lat") != null && res.getString("lng") != null) {
				shop.getAddress().setCoordinates(res.getDouble("lat"), res.getDouble("lng"));
			}
			else shop.getAddress().setCoordinates(null, null);

			String countryCode = res.getString("country_code"); 

			shop.getAddress().setAddress(
					res.getString("street"),
					res.getString("city"),
					countryCode != null ? _countryDAO.get(countryCode) : null);
			return shop;
		} catch (SQLException e) {
			String msg = "Failed to retrieve shop. SQLException: "+e.getMessage()+".";
			_log.error(msg+" Chaining and rethrowing.");
			_log.debug(e.getStackTrace());
			throw new DAOException(msg, e);
		} catch (NotFoundException e) {
			// TODO clean countryDAO from NotFoundExceptions
			throw new DAOException(e.getMessage(), e);
		}
	}

	@Override
	public boolean save(ShopData shop) throws DAOException {
		_log.debug("shop:"+shop);
		PreparedStatement pstmt;

		try {
			if(!_entityDAO.save(shop))
				return false;

			if (shop.getRev() == 1) {
				// create a new Shop
				pstmt = _db.prepareStatement("INSERT INTO shop (shop_id) VALUES (?)");
				pstmt.setLong(1, shop.getId());
				pstmt.executeUpdate();
			} else if (shop.getRev() < 1) {
				throw new DAOException("EntityDAO returned shop with revision < 0!");
			}

			String sql = "INSERT INTO shopRevision (shop_id, rev, type_id, imageUrl, lat, lng, street, city, country_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = _db.prepareStatement(sql);
			pstmt.setLong(1, shop.getId());
			pstmt.setInt(2, shop.getRev());

			if (shop.getTypeId() != null) pstmt.setLong(3, shop.getTypeId());
			else pstmt.setNull(3, Types.BIGINT);

			pstmt.setString(4, shop.getImageSrc());


			if (shop.getLat() != null && shop.getLng() != null) {
				pstmt.setDouble(5, shop.getLat());
				pstmt.setDouble(6, shop.getLng());
			} else {
				pstmt.setNull(5, Types.DOUBLE);
				pstmt.setNull(6, Types.DOUBLE);
			}

			if (shop.getAddress() != null) {
				pstmt.setString(7, shop.getAddress().getStreet());
				pstmt.setString(8, shop.getAddress().getCity());
				if (shop.getAddress().getCountry() != null)
					pstmt.setString(9, shop.getAddress().getCountry().getCode());
				else pstmt.setNull(9, Types.VARCHAR);

			}
			else {
				pstmt.setNull(7, Types.VARCHAR);
				pstmt.setNull(8, Types.VARCHAR);
				pstmt.setNull(9, Types.VARCHAR);
			}

			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			String msg = "Failed to retrieve shop. SQLException: "+e.getMessage()+".";
			_log.error(msg+" Chaining and rethrowing.");
			_log.debug(e.getStackTrace());
			throw new DAOException(msg, e);
		}
	}
}
