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
 * Filename: ReceiptHandlerImpl.java
 * Date: 30.05.2010
*/
package org.tagaprice.server.rpc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.LocaleDAO;
import org.tagaprice.server.dao.LoginDAO;
import org.tagaprice.server.dao.ReceiptDAO;
import org.tagaprice.shared.Address;
import org.tagaprice.shared.Country;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Unit;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.InvalidLoginException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;
import org.tagaprice.shared.rpc.ReceiptHandler;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ReceiptHandlerImpl extends RemoteServiceServlet implements ReceiptHandler {

	private DBConnection db;
	private LoginDAO loginDao;
	private ReceiptDAO receiptDao;
	private int localeId;

	
	public ReceiptHandlerImpl() {
		try {
			db = new DBConnection();
			loginDao = new LoginDAO(db);
			receiptDao = new ReceiptDAO(db);
			localeId = new LocaleDAO(db).get("English").getId();
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		} catch (InvalidLocaleException e) {
			throw new IllegalArgumentException(e);
		}
		
	}
	
	
	@Override
	public ReceiptData get(Long id) throws IllegalArgumentException {
		
		
		//MockMock
		ReceiptData receiptData;
		
		
		if(id==null){
			ArrayList<ProductData> myProducts = new ArrayList<ProductData>();
			receiptData = new ReceiptData(
					15L, 2,
					"Default name", 2,
					new Date(), 
					0, 
					null, 
					myProducts,
					true);
		}else{
			ArrayList<ProductData> myProducts = new ArrayList<ProductData>();
			ProductData p = new ProductData(152L, 8, "Grouda geschnitten", 2, 15L, 16L, "logo.png", new Quantity(260, new Unit(23, 3, "g", 2, null, 0)));
			p.setAvgPrice(new Price(325, 23, 8, "€", 2));
			myProducts.add(p);
			p = new ProductData(120L, 3, "Ja!Natürlich Milch 1L", 2, 15L, 16L, "logo.png", new Quantity(1, new Unit(24, 4, "l", 2, null, 0)));
			p.setAvgPrice(new Price(98, 23, 8, "€", 2));
			myProducts.add(p);
			
			receiptData = new ReceiptData(
					15l, 1,
					"Christmas shopping", 
					10,
					new Date(), 
					0, 
					new ShopData(15, 9, "Billa Flossgasse", 3, 30l, "logo.png", new Address("Flossgasse 1A", "1020 Wien", new Country("at", "Austria", "Österreich"))), 
					myProducts,
					false);
		}
		
		
		
		
		return receiptData;
	}

	@Override
	public ReceiptData save(ReceiptData data) throws IllegalArgumentException, InvalidLoginException {
		if(data==null){
			
			try {
				data = new ReceiptData("default title", localeId, loginDao.getId(getSid()), new Date(), 1, null, null, true);
			} catch (SQLException e) {
				throw new IllegalArgumentException("SQLException: "+e);
			}		 
			
		}
		
		//create new Draft
		try {
			receiptDao.save(data);
		}  catch (SQLException e){
			throw new IllegalArgumentException("SQLException: "+e);
		} catch (NotFoundException e) {
			throw new IllegalArgumentException("NotFoundException: "+e);		
		} catch (RevisionCheckException e) {
			throw new IllegalArgumentException("RevisionCheckException: "+e);
		} catch (InvalidLocaleException e) {
			throw new IllegalArgumentException("InvalidLocaleException: "+e);
		}

		return data;
	}

	private String getSid() throws InvalidLoginException{
		return loginDao.getSid(this.getThreadLocalRequest().getCookies());	
	}
}
