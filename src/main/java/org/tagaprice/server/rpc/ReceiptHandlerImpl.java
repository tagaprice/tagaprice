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
import java.util.Date;
import java.util.List;

import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.postgres.LocaleDAO;
import org.tagaprice.server.dao.postgres.LoginDAO;
import org.tagaprice.server.dao.postgres.ReceiptDAO;
import org.tagaprice.shared.entities.Receipt;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.InvalidLoginException;
import org.tagaprice.shared.exception.ServerException;
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
	public Receipt get(Receipt data) throws ServerException {
		try {
			return receiptDao.getById(loginDao.getId(getSid()));
		} catch (Exception e) {
			// TODO clean login dao from exceptions and remove this catch block
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<Receipt> getUserReceipts() throws ServerException {
		try {
			return receiptDao.getUserReceipts(loginDao.getId(getSid()));
		} catch (Exception e) {
			// TODO clean login dao from exceptions and remove this catch block
			throw new ServerException(e.getMessage(), e);
		}
	}
	

	@Override
	public Receipt save(Receipt data) throws ServerException {
		if(data==null){
			
			try {
				data = new Receipt("default title", localeId, loginDao.getId(getSid()), new Date(), 1, null, null, true);
			} catch (Exception e) {
				// TODO clean login dao from exceptions and remove this catch block
				throw new ServerException(e.getMessage(), e);
			}		 
			
		}
		receiptDao.save(data);
		return data;
	}

	private String getSid() throws InvalidLoginException{
		return loginDao.getSid(this.getThreadLocalRequest().getCookies());	
	}


	
}
