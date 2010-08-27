package org.tagaprice.server.rpc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.LoginDAO;
import org.tagaprice.server.dao.ShopDAO;
import org.tagaprice.shared.PropertyValidator;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;
import org.tagaprice.shared.rpc.ShopHandler;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ShopHandlerImpl extends RemoteServiceServlet implements ShopHandler{
	ShopDAO sDao;
	LoginDAO loginDao; 
	
	public ShopHandlerImpl() {
		DBConnection db;
		try {
			db = new DBConnection();
			sDao = new ShopDAO(db);
			loginDao = new LoginDAO(db);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		
	}
	
	@Override
	public ShopData get(Long id) throws IllegalArgumentException {
		
		ShopData sd = new ShopData();				
		sd._setId(id);
		
		try {
			sDao.get(sd);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		} catch (NotFoundException e) {
			throw new IllegalArgumentException(e);
		}		
		return sd;
	}

	@Override
	public ShopData save(ShopData data) throws IllegalArgumentException {
		// TODO Auto-generated method stub		
		TypeHandlerImpl th = new TypeHandlerImpl();
		
		if(PropertyValidator.isValid(th.get(data.getTypeId()), data.getProperties())){		
			try {				
				data._setCreatorId(loginDao.getId(getSid()));
				sDao.save(data);				
			} catch (SQLException e) {
				throw new IllegalArgumentException(e);
			} catch (NotFoundException e) {
				throw new IllegalArgumentException(e);
			} catch (RevisionCheckException e) {
				throw new IllegalArgumentException(e);
			} catch (InvalidLocaleException e) {
				throw new IllegalArgumentException(e);
			}			
			
		}else{
			System.out.println("save InVALID");
		}
		

		return data;
	}
	
	private String getSid(){
		return loginDao.getSid(this.getThreadLocalRequest().getCookies());
	}

}
