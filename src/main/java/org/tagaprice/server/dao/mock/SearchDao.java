package org.tagaprice.server.dao.mock;

import java.util.ArrayList;
import java.util.List;

import org.tagaprice.server.dao.ISearchDao;
import org.tagaprice.server.rpc.InitServlet;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class SearchDao extends DaoClass<Document> implements ISearchDao {

	
	@Override
	public List<Document> search(String query, BoundingBox bbox)
			throws DaoException {
		// TODO Auto-generated method stub
		
		
		ArrayList<Document> rc = new ArrayList<Document>();
		
		rc.addAll(InitServlet.getDaoFactory().getShopDao().list());
		rc.addAll(InitServlet.getDaoFactory().getProductDao().list());
	//	rc.addAll(_m_productDAO.find(query));
	//	rc.addAll(_m_shopDAO.find(query));
		
		return rc;
	}

}
