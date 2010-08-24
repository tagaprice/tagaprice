package org.tagaprice.server.rpc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import org.tagaprice.server.DBConnection;
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
	
	public ShopHandlerImpl() {
		DBConnection db;
		try {
			db = new DBConnection();
			sDao = new ShopDAO(db);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public ShopData get(Long id) throws IllegalArgumentException {
		
		ShopData sd = new ShopData();
		sd._setId(id);
		
		try {
			sDao.get(sd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return sd;
	}

	@Override
	public ShopData save(ShopData data) throws IllegalArgumentException {
		// TODO Auto-generated method stub		
		TypeHandlerImpl th = new TypeHandlerImpl();
		
		if(PropertyValidator.isValid(th.get(data.getTypeId()), data.getProperties())){
			System.out.println("save VALID");
			
			data._setCreatorId(1l);
			
			try {
				sDao.save(data);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RevisionCheckException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidLocaleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
			
		}else{
			System.out.println("save InVALID");
		}
		

		return data;
	}

}
