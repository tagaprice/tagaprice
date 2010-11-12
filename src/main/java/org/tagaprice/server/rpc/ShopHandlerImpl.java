package org.tagaprice.server.rpc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.ShopDAO;
import org.tagaprice.server.dao.postgres.LoginDAO;
import org.tagaprice.shared.PropertyValidator;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Category;
import org.tagaprice.shared.exception.DAOException;
import org.tagaprice.shared.exception.InvalidLoginException;
import org.tagaprice.shared.exception.ServerException;
import org.tagaprice.shared.rpc.ShopHandler;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ShopHandlerImpl extends RemoteServiceServlet implements ShopHandler{
	ShopDAO shopDao; //TODO use mock instead
	LoginDAO loginDao; 
	
	public ShopHandlerImpl() {
		DBConnection db;
		try {
			db = new DBConnection();
			shopDao = new ShopDAO(db);
			loginDao = new LoginDAO(db);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		
	}
	
	@Override
	public ShopData get(long id) throws DAOException {
		return shopDao.getById(id);
	}

	@Override
	public ShopData save(ShopData data) throws IllegalArgumentException, InvalidLoginException, ServerException {
		// TODO Auto-generated method stub		
		CategoryHandlerImpl th = new CategoryHandlerImpl();
		
		if(PropertyValidator.isValid(th.get(new Category(data.getTypeId())), data.getProperties())){		
			try {				
				data.setCreatorId(loginDao.getId(getSid()));
				shopDao.save(data);				
			} catch (SQLException e) {
				//TODO catch exception loginDao
				throw new DAOException(e.getMessage(), e);
			}			
		} else{
			System.out.println("save InVALID");
		}
		

		return data;
	}
	
	private String getSid() throws InvalidLoginException{
		return loginDao.getSid(this.getThreadLocalRequest().getCookies());
	}

}
