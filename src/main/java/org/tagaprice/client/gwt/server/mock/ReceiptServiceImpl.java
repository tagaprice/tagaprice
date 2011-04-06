package org.tagaprice.client.gwt.server.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import org.tagaprice.client.gwt.shared.entities.Address;
import org.tagaprice.client.gwt.shared.entities.IRevisionId;
import org.tagaprice.client.gwt.shared.entities.RevisionId;
import org.tagaprice.client.gwt.shared.entities.Unit;
import org.tagaprice.client.gwt.shared.entities.dump.Category;
import org.tagaprice.client.gwt.shared.entities.dump.ICategory;
import org.tagaprice.client.gwt.shared.entities.dump.Quantity;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IPackage;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Product;
import org.tagaprice.client.gwt.shared.entities.receiptManagement.*;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.Subsidiary;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.ISubsidiary;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.Shop;
import org.tagaprice.client.gwt.shared.logging.LoggerFactory;
import org.tagaprice.client.gwt.shared.logging.MyLogger;
import org.tagaprice.client.gwt.shared.rpc.receiptmanagement.IReceiptService;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Package;
import org.tagaprice.client.gwt.shared.exceptions.UserNotLoggedInException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ReceiptServiceImpl extends RemoteServiceServlet implements IReceiptService {


	private static final long serialVersionUID = 3420788026998858664L;

	ProductServiceImpl productImpl = new ProductServiceImpl();
	ShopServiceImpl shopImple = new ShopServiceImpl();
	Random random = new Random(44646776);
	int productIdCounter = 1;
	ArrayList<IReceipt> _receiptList = new ArrayList<IReceipt>();
	MyLogger logger = LoggerFactory.getLogger(ReceiptServiceImpl.class);

	public ReceiptServiceImpl() {
		IRevisionId r1 = new RevisionId(random.nextLong(), 1);
		IRevisionId r2 = new RevisionId(random.nextLong(), 1);
		ISubsidiary tempAddres = new Subsidiary(r2, new Address("Holzhausergasse 9", 48.21975481443672, 16.38885498046875));
		IShop tempshop = new Shop(r1, "Billa");
		tempAddres.setShop(tempshop);

		//Create test product
		//IReceipt tempReceipt = new Receipt("First Receipt", new Date(), tempAddres, new ArrayList<IReceiptEntry>());
		IReceipt tempReceipt = new Receipt(new RevisionId(productIdCounter++, 1), "First Receipt",  new Date(), tempAddres);


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
			IReceiptEntry ire = new ReceiptEntry(new Price(30, Currency.dkk), productImpl.getPackage(new RevisionId(2L, 1)));
			tempReceipt.addReceiptEntriy(ire);
		}

		_receiptList.add(tempReceipt);
	}

	@Override
	public IReceipt saveReceipt(IReceipt receipt) {

		logger.log("Receipt saved: "+receipt);

		IReceipt returnReceipt=null;
		//Create or update Receipt
		if(receipt.getRevisionId()==null){
			receipt.setRevisionId(new RevisionId(productIdCounter++, 1));
			_receiptList.add(receipt);
			returnReceipt=receipt;
		}
		else {
			for(IReceipt ir:_receiptList){
				if(ir.getRevisionId().getId()==receipt.getRevisionId().getId()){
					ir=receipt;
					ir.getRevisionId().setRevision(ir.getRevisionId().getRevision()+1);
					returnReceipt=ir;
				}
			}

		}


		//create or update Shop
		//shopImple.saveShop(shop)

		//Create or update address


		//create or update Product


		//create or update Package

		return returnReceipt;
	}

	@Override
	public IReceipt getReceipt(long receiptid) {

		for (IReceipt r:_receiptList)
			if(r.getRevisionId().getId()==receiptid)
				return r;


		return null;
	}

	@Override
	@Deprecated
	public ArrayList<ReceiptEntry> getReceiptEntriesByProductId(long productid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<IReceipt> getReceits() throws UserNotLoggedInException {

		return _receiptList;
	}

}
