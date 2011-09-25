package org.tagaprice.server.dao.couchdb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.jcouchdb.db.Options;
import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.tagaprice.server.dao.IPackageDao;
import org.tagaprice.server.dao.IProductDao;
import org.tagaprice.server.dao.IReceiptDao;
import org.tagaprice.server.dao.IShopDao;
import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.receiptManagement.Currency;
import org.tagaprice.shared.entities.receiptManagement.Price;
import org.tagaprice.shared.entities.receiptManagement.Receipt;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.exceptions.dao.DaoException;
import com.allen_sauer.gwt.log.client.Log;

public class ReceiptDao extends DaoClass<Receipt> implements IReceiptDao {

	IPackageDao m_packageDAO;
	IProductDao m_productDAO;
	IShopDao m_shopDAO;


	public ReceiptDao(CouchDbDaoFactory daoFactory) {
		super(daoFactory, Receipt.class, Document.Type.RECEIPT, null);
		m_shopDAO = daoFactory.getShopDao();
		m_packageDAO = daoFactory.getPackageDao();
		m_productDAO = daoFactory.getProductDao();
	}


	@Override
	public Receipt create(Receipt receipt) throws DaoException {

		BigDecimal price = new BigDecimal("0.0");
		for(ReceiptEntry re: receipt.getReceiptEntries()){
			if(re.getPackageId()==null){
				re.setPackage(m_packageDAO.create(re.getPackage()));
				
			}
			price=price.add(re.getPrice().getPrice());
		}
		receipt.setPrice(new Price(price, Currency.euro));
		Receipt rc = super.create(receipt);
		return rc;

	}

	@Override
	public Receipt update(Receipt receipt) throws DaoException {
		BigDecimal price = new BigDecimal("0.0");
		for(ReceiptEntry re: receipt.getReceiptEntries()){
			if(re.getPackageId()==null){
				re.setPackage(m_packageDAO.create(re.getPackage()));
			}
			price=price.add(re.getPrice().getPrice());
		}
		receipt.setPrice(new Price(price, Currency.euro));
		return super.update(receipt);
	}

	@Override
	public List<Receipt> list() throws DaoException {
		Log.debug("List");
		ViewResult<?> result = m_db.queryView("receipt/all", Receipt.class, null, null);
		List<Receipt> rc = new ArrayList<Receipt>();

		for (ValueRow<?> row: result.getRows()) {
			Log.debug("rowId: "+row.getId());
			Receipt receipt = get(row.getId());
			Log.debug("Listsize: "+receipt.getReceiptEntries().size());
			Log.debug("receiptEntrysize: "+receipt.getReceiptEntries().size());
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
	public List<Receipt> listByPackage(String packageId) throws DaoException {
		ViewResult<?> result = m_db.queryView("receipt/byUser", Receipt.class, new Options().key(packageId), null);
		List<Receipt> rc = new ArrayList<Receipt>();

		for (ValueRow<?> row: result.getRows()) {
			Log.debug("rowId: "+row.getId());
			Receipt receipt = get(row.getId());
			Log.debug("Listsize: "+receipt.getReceiptEntries().size());
			Log.debug("receiptEntrysize: "+receipt.getReceiptEntries().size());
			rc.add(receipt);
		}

		return rc;
	}

	@Override
	public List<Receipt> listByUser(String userId) throws DaoException {
		ViewResult<?> result = m_db.queryView("receipt/byUser", Receipt.class, new Options().key(userId), null);

		Set<String> receiptIDs = new TreeSet<String>();
		
		for (ValueRow<?> row: result.getRows()) {
			receiptIDs.add(row.getId());
		}
		
		Map<String, Receipt> receipts = getBulk(receiptIDs.toArray(new String[receiptIDs.size()]));
	
		return new ArrayList<Receipt>(receipts.values());
	}
	
	@Override
	public List<Receipt> listByShop(String shopId, Date from, Date to) throws DaoException {
		ViewResult<?> result = m_db.queryView("receipt/byShop", Receipt.class, new Options().key(shopId), null);
		List<Receipt> rc = new ArrayList<Receipt>();

		for (ValueRow<?> row: result.getRows()) {
			Date date = new Date(Long.parseLong(row.getValue().toString()));
			if (!date.after(to) && !date.before(from)) {
				rc.add(get(row.getId()));
			}
		}

		return rc;
	}

	@Override
	protected void _injectFields(Receipt ... receipts) throws DaoException {
		Set<String> shopIDs = new TreeSet<String>();
		Set<String> packageIDs = new TreeSet<String>();
		Set<String> productIDs = new TreeSet<String>();
		
		for (Receipt receipt: receipts) {
			if (receipt.getShopId() != null) shopIDs.add(receipt.getShopId());
			
			for (ReceiptEntry entry: receipt.getReceiptEntries()) {
				if (entry.getPackageId() != null) {
					packageIDs.add(entry.getPackageId());
				}
			}
		}
		
		Map<String, Shop> shops = m_shopDAO.getBulk(shopIDs.toArray(new String[shopIDs.size()]));
		Map<String, Package> packages = m_packageDAO.getBulk(packageIDs.toArray(new String[packageIDs.size()]));

		// inject the products into the packages
		for (Package pkg: packages.values()) {
			if (pkg.getProductId() != null) productIDs.add(pkg.getProductId());
		}
		Map<String, Product> products = m_productDAO.getBulk(productIDs.toArray(new String[productIDs.size()]));
		for (Package pkg: packages.values()) {
			if (pkg.getProductId() != null) {
				pkg.setProduct(products.get(pkg.getProductId()));
			}
		}


		for (Receipt receipt: receipts) {
			if(receipt.getShopId() != null){
				receipt.setShop(shops.get(receipt.getShopId()));
			}
			
			for(ReceiptEntry entry: receipt.getReceiptEntries()) {
				if(entry.getPackageId()!=null) {
					entry.setPackage(packages.get(entry.getPackageId()));
				}
			}
		}
	}

}
