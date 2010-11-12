package org.tagaprice.server.rpc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.tagaprice.client.widgets.SearchWidget.SearchType;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.ProductDAO;
import org.tagaprice.server.dao.ShopDAO;
import org.tagaprice.server.dao.interfaces.IShopDAO;
import org.tagaprice.shared.BoundingBox;
import org.tagaprice.shared.Entity;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Unit;
import org.tagaprice.shared.exception.DAOException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.rpc.SearchHandler;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class SearchHandlerImpl extends RemoteServiceServlet implements SearchHandler{
	ProductDAO productDao;
	IShopDAO shopDao;
	DBConnection db;
	
	public SearchHandlerImpl() {
		try {
			db = new DBConnection();
			productDao = new ProductDAO(db);
			shopDao = new ShopDAO(db);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	@Override
	public ArrayList<Entity> search(String search, SearchType searchType)
			throws IllegalArgumentException, DAOException {
		ArrayList<Entity> mockUp = new ArrayList<Entity>();
		
		if(searchType.equals(SearchType.ALL)){
			getProduct(search, mockUp);
			getShop(search, mockUp);
		}else if(searchType.equals(SearchType.SHOP)){
			getShop(search, mockUp);
		}else if(searchType.equals(SearchType.PRODCUT)){
			getProduct(search, mockUp);
		}		
		
		return mockUp;
	}

	@Override
	public ArrayList<Entity> search(String search, SearchType searchType, BoundingBox bbox)
			throws IllegalArgumentException, DAOException {
		ArrayList<Entity> mockUp = new ArrayList<Entity>();

		if(searchType.equals(SearchType.ALL)){
			getProduct(search, mockUp);
			getShop(search, mockUp);
		}else if(searchType.equals(SearchType.SHOP)){
			getShop(search, mockUp);
		}else if(searchType.equals(SearchType.PRODCUT)){
			getProduct(search, mockUp);
		}
		return mockUp;
	}

	@Override
	public ArrayList<Entity> search(String search, ShopData shopData)
			throws IllegalArgumentException, DAOException {
		ArrayList<Entity> mockUp = new ArrayList<Entity>();
		getProduct(search, mockUp);
		return mockUp;
	}

	
	private void getProduct(String search, ArrayList<Entity> mockUp) throws DAOException{
		String sql = "SELECT * FROM product pr " +
				"INNER JOIN entity en " +
				"ON en.ent_id = pr.prod_id " +
				"INNER JOIN entityrevision er " +
				"ON er.ent_id=pr.prod_id AND er.rev=current_revision " +
				"WHERE LOWER(er.title) LIKE ?";
		
		
		try {
			PreparedStatement pstmt = db.prepareStatement(sql);
			pstmt.setString(1, "%"+search.toLowerCase()+"%");
			ResultSet res = pstmt.executeQuery();
			
			while(res.next()){
				try {
					ProductData sp = new ProductData(res.getLong("ent_id"));
					productDao.get(sp);
					sp.setAvgPrice(new Price(15, 4, 1, "€", 1));
					sp.setImageSrc("logo.png");
					sp.setQuantity(new Quantity(125, new Unit(23, 2, "g", 1, null, 0)));
					sp.setTypeId(20l);
					mockUp.add(sp);
				} catch (NotFoundException e) {
					throw new DAOException(e.getMessage(), e);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void getShop(String search, ArrayList<Entity> mockUp) throws DAOException{
		String sql = "SELECT * FROM shop pr " +
				"INNER JOIN entity en " +
				"ON en.ent_id = pr.shop_id " +
				"INNER JOIN entityrevision er " +
				"ON er.ent_id=pr.shop_id AND er.rev=current_revision " +
				"WHERE LOWER(er.title) LIKE ?";
		
		
		
		try {
			PreparedStatement pstmt = db.prepareStatement(sql);
			pstmt.setString(1, "%"+search.toLowerCase()+"%");
			ResultSet res = pstmt.executeQuery();
			
			while(res.next()){
				ShopData shop = shopDao.getById(res.getLong("ent_id"));
				mockUp.add(shop);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
