package org.tagaprice.server.dao.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IPackageDAO;
import org.tagaprice.server.dao.IReceiptDAO;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.dump.Category;
import org.tagaprice.shared.entities.dump.Quantity;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.receiptManagement.Currency;
import org.tagaprice.shared.entities.receiptManagement.Price;
import org.tagaprice.shared.entities.receiptManagement.Receipt;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.entities.productmanagement.Package;

public class ReceiptDAO implements IReceiptDAO {

	IPackageDAO packageDAO;
	Random random = new Random(44646776);
	int productIdCounter = 1;
	ArrayList<Receipt> _receiptList = new ArrayList<Receipt>();
	Receipt tempReceipt;

	public ReceiptDAO(IDaoFactory daoFactory) {
		packageDAO = daoFactory.getPackageDAO();

		Shop tempAddres = new Shop(new Long(random.nextLong()).toString(), "1", "Billa - Holzhausergasse 9");
		tempAddres.setAddress(new Address("Holzhausergasse 9", 48.21975481443672, 16.38885498046875));
		Shop tempshop = new Shop(new Long(random.nextLong()).toString(), "1", "Billa");
		tempAddres.setParent(tempshop);

		//Create test product
		//Receipt tempReceipt = new Receipt("First Receipt", new Date(), tempAddres, new ArrayList<ReceiptEntry>());
		tempReceipt = new Receipt(""+random.nextInt(), "1", "First Receipt",  new Date(), tempAddres);


		Category root = new Category("root",null);
		//Add receipt entries
		{
			Package ipack = new Package(new Quantity(1.2, Unit.kg));
			Product iprodc = new Product("Bergkäse 4", new Category("food",root), Unit.g);
			ipack.setProduct(iprodc);
			iprodc.addPackage(ipack);
			ReceiptEntry ire = new ReceiptEntry(new Price(15, Currency.dkk), ipack);
			tempReceipt.addReceiptEntriy(ire);
		}


		{
			Package ipack = new Package(new Quantity(1.5, Unit.l));
			Product iprodc = new Product("CocaCola", new Category("food",root), Unit.l);
			ipack.setProduct(iprodc);
			iprodc.addPackage(ipack);
			ReceiptEntry ire = new ReceiptEntry(new Price(30, Currency.dkk), ipack);
			tempReceipt.addReceiptEntriy(ire);
		}

		{
			ReceiptEntry ire = new ReceiptEntry(new Price(30, Currency.dkk), packageDAO.get("secondReceipt"));
			tempReceipt.addReceiptEntriy(ire);
		}


		_receiptList.add(tempReceipt);
	}

	@Override
	public Receipt create(Receipt receipt) {
		receipt.setId(receipt.getTitle());
		receipt.setRevision("1");
		_receiptList.add(receipt);
		return receipt;
	}

	@Override
	public Receipt get(String id, String receipt) {
		return tempReceipt;
		/*
		for (Receipt r:_receiptList) {
			if(r.getId() == id) {
				return r;
			}
		}
		return null;
		 */
	}

	@Override
	public Receipt get(String id) {
		return get(id, null);
	}

	@Override
	public Receipt update(Receipt receipt) {
		for(Receipt ir:_receiptList){
			if(ir.getId()==receipt.getId()){
				ir=receipt;
				ir.setRevision(ir.getRevision()+1);
				return ir;
			}
		}
		return null;
	}

	@Override
	public void delete(Receipt shop) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Receipt> list() {
		return _receiptList;
	}

}
