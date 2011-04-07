package org.tagaprice.server.dao.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IPackageDAO;
import org.tagaprice.server.dao.IReceiptDAO;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.IRevisionId;
import org.tagaprice.shared.entities.RevisionId;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.dump.Category;
import org.tagaprice.shared.entities.dump.ICategory;
import org.tagaprice.shared.entities.dump.Quantity;
import org.tagaprice.shared.entities.productmanagement.IPackage;
import org.tagaprice.shared.entities.productmanagement.IProduct;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.receiptManagement.Currency;
import org.tagaprice.shared.entities.receiptManagement.IReceipt;
import org.tagaprice.shared.entities.receiptManagement.IReceiptEntry;
import org.tagaprice.shared.entities.receiptManagement.Price;
import org.tagaprice.shared.entities.receiptManagement.Receipt;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;
import org.tagaprice.shared.entities.shopmanagement.IShop;
import org.tagaprice.shared.entities.shopmanagement.ISubsidiary;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.entities.shopmanagement.Subsidiary;

public class ReceiptDAO implements IReceiptDAO {

	IPackageDAO packageDAO;
	Random random = new Random(44646776);
	int productIdCounter = 1;
	ArrayList<IReceipt> _receiptList = new ArrayList<IReceipt>();

	public ReceiptDAO(IDaoFactory daoFactory) {
		packageDAO = daoFactory.getPackageDAO();
		
		IRevisionId r1 = new RevisionId(new Long(random.nextLong()).toString(), null);
		IRevisionId r2 = new RevisionId(new Long(random.nextLong()).toString(), null);
		ISubsidiary tempAddres = new Subsidiary(r2, new Address("Holzhausergasse 9", 48.21975481443672, 16.38885498046875));
		IShop tempshop = new Shop(r1, "Billa");
		tempAddres.setShop(tempshop);

		//Create test product
		//IReceipt tempReceipt = new Receipt("First Receipt", new Date(), tempAddres, new ArrayList<IReceiptEntry>());
		IReceipt tempReceipt = new Receipt(new RevisionId("firstReceipt"), "First Receipt",  new Date(), tempAddres);


		ICategory root = new Category("root",null);
		//Add receipt entries
		{
			IPackage ipack = new Package(new Quantity(1.2, Unit.kg));
			IProduct iprodc = new Product("Bergkäse 4", new Category("food",root), Unit.g);
			ipack.setProduct(iprodc);
			iprodc.addPackage(ipack);
			IReceiptEntry ire = new ReceiptEntry(new Price(15, Currency.dkk), ipack);
			tempReceipt.addReceiptEntriy(ire);
		}

		{
			IPackage ipack = new Package(new Quantity(1.5, Unit.l));
			IProduct iprodc = new Product("CocaCola", new Category("food",root), Unit.l);
			ipack.setProduct(iprodc);
			iprodc.addPackage(ipack);
			IReceiptEntry ire = new ReceiptEntry(new Price(30, Currency.dkk), ipack);
			tempReceipt.addReceiptEntriy(ire);
		}

		{
			IReceiptEntry ire = new ReceiptEntry(new Price(30, Currency.dkk), packageDAO.get(new RevisionId("secondReceipt")));
			tempReceipt.addReceiptEntriy(ire);
		}

		_receiptList.add(tempReceipt);
	}

	@Override
	public IReceipt create(IReceipt receipt) {
		receipt.setRevisionId(new RevisionId(receipt.getTitle()));
		_receiptList.add(receipt);
		return receipt;
	}

	@Override
	public IReceipt get(IRevisionId receiptId) {
		for (IReceipt r:_receiptList) {
			if(r.getRevisionId().getId()==receiptId.getId()) {
				return r;
			}
		}
		return null;
	}

	@Override
	public IReceipt update(IReceipt receipt) {
		for(IReceipt ir:_receiptList){
			if(ir.getRevisionId().getId()==receipt.getRevisionId().getId()){
				ir=receipt;
				ir.getRevisionId().setRevision(ir.getRevisionId().getRevision()+1);
				return ir;
			}
		}
		return null;
	}

	@Override
	public void delete(IReceipt shop) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<IReceipt> list() {
		// TODO Auto-generated method stub
		return null;
	}

}
