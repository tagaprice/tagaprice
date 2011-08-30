package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.List;

import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.tagaprice.server.dao.IPackageDao;
import org.tagaprice.server.dao.IProductDao;
import org.tagaprice.server.dao.IReceiptDao;
import org.tagaprice.server.dao.IShopDao;
import org.tagaprice.shared.entities.receiptManagement.Receipt;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;
import org.tagaprice.shared.exceptions.dao.DaoException;
import com.allen_sauer.gwt.log.client.Log;

public class ReceiptDao extends DaoClass<Receipt> implements IReceiptDao {

	IPackageDao m_packageDAO;
	IProductDao m_productDAO;
	IShopDao m_shopDAO;


	public ReceiptDao(CouchDbDaoFactory daoFactory) {
		super(daoFactory, Receipt.class, "receipt", daoFactory._getDocumentDao());
		m_shopDAO = daoFactory.getShopDao();
		m_packageDAO = daoFactory.getPackageDao();
		m_productDAO = daoFactory.getProductDao();
	}


	@Override
	public Receipt create(Receipt receipt) throws DaoException {

		for(ReceiptEntry re: receipt.getReceiptEntries()){
			if(re.getPackageId()==null){
				re.setPackage(m_packageDAO.create(re.getPackage()));
			}
		}

		Receipt rc = super.create(receipt);
		return rc;

	}

	@Override
	public Receipt update(Receipt receipt) throws DaoException {
		for(ReceiptEntry re: receipt.getReceiptEntries()){
			if(re.getPackageId()==null){
				re.setPackage(m_packageDAO.create(re.getPackage()));
			}
		}
		return super.update(receipt);
	}

	@Override
	public List<Receipt> list() throws DaoException {
		Log.debug("List");
		ViewResult<?> result = m_db.queryView("receipt/all", Receipt.class, null, null);
		List<Receipt> rc = new ArrayList<Receipt>();

		Log.debug("Listsize: "+result.getRows().size());

		for (ValueRow<?> row: result.getRows()) {
			Log.debug("rowId: "+row.getId());
			Receipt receipt = get(row.getId());
			rc.add(receipt);
		}


		return rc;
	}
	
	@Override
	public Receipt get(String id, String revision) throws DaoException {
		return super.get(id, revision);
	}
	
	@Override
	public Receipt get(String id) throws DaoException {
		Log.debug("getreceipt. Id: "+id);
		Receipt r = get(id,null);
		
		Log.debug(r.getDocType()+", c:"+r.getReceiptEntries().size());
		
		return r;
	}

	@Override
	protected void _injectFields(Receipt receipt) throws DaoException {
		if(receipt.getShopId() != null){
			receipt.setShop(m_shopDAO.get(receipt.getShopId()));
		}
		
		for(ReceiptEntry re:receipt.getReceiptEntries()){
			if(re.getPackageId()!=null){
				re.setPackage(m_packageDAO.get(re.getPackageId()));
				re.getPackage().setProduct(m_productDAO.get(re.getPackage().getProductId()));
			}
		}
	}

}
