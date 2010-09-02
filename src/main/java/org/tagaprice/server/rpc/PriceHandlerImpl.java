/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: TagAPrice
 * Filename: PriceHandlerImpl.java
 * Date: 02.06.2010
*/
package org.tagaprice.server.rpc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.tagaprice.client.PriceMapWidget.PriceMapType;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.LocaleDAO;
import org.tagaprice.server.dao.LoginDAO;
import org.tagaprice.server.dao.ProductDAO;
import org.tagaprice.server.dao.ReceiptDAO;
import org.tagaprice.server.dao.ShopDAO;
import org.tagaprice.shared.Address;
import org.tagaprice.shared.BoundingBox;
import org.tagaprice.shared.Country;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.PriceData;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Unit;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.rpc.PriceHandler;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class PriceHandlerImpl extends RemoteServiceServlet implements PriceHandler {

	private DBConnection db;
	private ShopDAO shopDAO;
	private ProductDAO productDAO;
	
	public PriceHandlerImpl() {
		try {
			db = new DBConnection();
			shopDAO = new ShopDAO(db);
			productDAO = new ProductDAO(db);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		} 
	}
	
	@Override
	public ArrayList<PriceData> get(Long id, BoundingBox bbox, PriceMapType type)
			throws IllegalArgumentException {
		ArrayList<PriceData> list = new ArrayList<PriceData>();
		
		PreparedStatement pstmt; 
		try {
		
			if(type.equals(PriceMapType.PRODUCT)){
				//return Last price plus location
				
				
					String sql = "SELECT * FROM receiptentry ree " +
					"INNER JOIN receipt re " +
					"ON re.rid=ree.rid " +
					"WHERE ree.pid = ? ";
					
					pstmt = db.prepareStatement(sql);
					pstmt.setLong(1, id);
					
					ResultSet res = pstmt.executeQuery();
					
					while(res.next()){
						ProductData tempProduct = new ProductData(res.getLong("pid"));
						ShopData tempShop = new ShopData(res.getLong("sid"));
						productDAO.get(tempProduct);
						shopDAO.get(tempShop);
						
						
						list.add(new PriceData(
								tempProduct, 
								tempShop,
								new Price(res.getInt("price"), 23, 8, "€", 1), 
								new Date(res.getDate("created_at").getTime())));
					}
					
					
				
				
			}
			
			if(type.equals(PriceMapType.SHOP)){
				String sql = "SELECT * FROM receiptentry ree " +
				"INNER JOIN receipt re " +
				"ON re.rid=ree.rid " +
				"WHERE re.sid = ? ";
				
				pstmt = db.prepareStatement(sql);
				pstmt.setLong(1, id);
				
				ResultSet res = pstmt.executeQuery();
				
				while(res.next()){
					ProductData tempProduct = new ProductData(res.getLong("pid"));
					//ShopData tempShop = new ShopData(res.getLong("sid"));
					productDAO.get(tempProduct);
					//shopDAO.get(tempShop);
					
					
					list.add(new PriceData(
							tempProduct, 
							null,
							new Price(res.getInt("price"), 23, 8, "€", 1), 
							new Date(res.getDate("created_at").getTime())));
				}
				
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return list;
	}

}
