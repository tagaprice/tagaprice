package org.tagaprice.server.dao.mock;

import java.math.BigDecimal;
import java.util.Date;

import org.tagaprice.server.dao.ICategoryDAO;
import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IPackageDAO;
import org.tagaprice.server.dao.IProductDAO;
import org.tagaprice.server.dao.IReceiptDAO;
import org.tagaprice.server.dao.IShopDAO;
import org.tagaprice.server.dao.IUnitDAO;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.Quantity;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.receiptManagement.Currency;
import org.tagaprice.shared.entities.receiptManagement.Price;
import org.tagaprice.shared.entities.receiptManagement.Receipt;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;

public class MockDaoFactory implements IDaoFactory {
	MyLogger logger = LoggerFactory.getLogger(MockDaoFactory.class);
	private final ICategoryDAO m_categoryDAO = new CategoryDAO(this);
	private final IPackageDAO m_packageDAO = new PackageDAO();
	private final IProductDAO m_productDAO = new ProductDAO(this);
	private final IReceiptDAO m_receiptDAO = new ReceiptDAO(this);
	private final IShopDAO m_shopDAO = new ShopDAO();
	private final IUnitDAO m_unitDAO = new UnitDAO();

	public MockDaoFactory() {
		logger.log("Initialice Mock data");
		// TODO Auto-generated constructor stub


		//Create Categories
		Category food = m_categoryDAO.create(new Category("food", null));
		Category vegetables = m_categoryDAO.create(new Category("vegetables", food));
		Category beverages = m_categoryDAO.create(new Category("beverages", null));
		Category alcoholics = m_categoryDAO.create(new Category("alcohol", beverages));
		Category nonalcoholics = m_categoryDAO.create(new Category("nonalcoholics", beverages));
		Category softAlc = m_categoryDAO.create(new Category("softalk", alcoholics));
		Category hardAlc = m_categoryDAO.create(new Category("hardalk", alcoholics));


		//Create units
		Unit st = m_unitDAO.create(new Unit("st"));
		m_unitDAO.setFactorizedUnit(st.getId(), st.getId(), 1);
		Unit kg = m_unitDAO.create(new Unit("kg"));
		Unit g = m_unitDAO.create(new Unit("g"));
		m_unitDAO.setFactorizedUnit(kg.getId(), g.getId(), 1000);
		m_unitDAO.setFactorizedUnit(kg.getId(), kg.getId(), 1);
		m_unitDAO.setFactorizedUnit(g.getId(), kg.getId(), 0.001);
		m_unitDAO.setFactorizedUnit(g.getId(), g.getId(), 1);


		Unit l = m_unitDAO.create(new Unit("l"));
		Unit ml = m_unitDAO.create(new Unit("ml"));
		m_unitDAO.setFactorizedUnit(l.getId(), ml.getId(), 1000);
		m_unitDAO.setFactorizedUnit(ml.getId(), l.getId(), 0.001);
		m_unitDAO.setFactorizedUnit(ml.getId(), ml.getId(), 1);
		m_unitDAO.setFactorizedUnit(l.getId(), l.getId(), 1);

		//Create Products
		// TestProduct
		Product bergkasese = new Product("Bergkäse 4", vegetables, g);
		bergkasese = m_productDAO.create(bergkasese);


		Package bergkasese1=new Package(new Quantity(new BigDecimal("500.3"), kg));
		bergkasese1.setProduct(bergkasese);
		bergkasese.addPackage(bergkasese1);


		Package bergkasese2=new Package(new Quantity(new BigDecimal("500.5"), g));
		bergkasese2.setProduct(bergkasese);
		bergkasese.addPackage(bergkasese2);


		m_productDAO.update(bergkasese);

		m_productDAO.create(new Product("Extrawurst von der Theke", vegetables, g));
		m_productDAO.create(new Product("Limonade", nonalcoholics, l));


		//Create shops
		//Create address for Shop(bills)
		//Create some Shops
		Shop s1 = m_shopDAO.create(new Shop("Billa"));
		{
			Shop is = m_shopDAO.create(new Shop("Billa - Blumauergasse 1B"));
			is.setAddress(new Address("Blumauergasse 1B", 48.21906856732104, 16.38164520263672));
			is.setParent(s1);
			is=m_shopDAO.update(is);
		}


		{
			Shop is = m_shopDAO.create(new Shop("Billa - Holzhausergasse 9"));
			is.setAddress(new Address("Holzhausergasse 9", 48.21975481443672, 16.38885498046875));
			is.setParent(s1);
			is=m_shopDAO.update(is);
		}




		//2 shop
		//Create address for Shop(bills)
		//Create some Shop
		Shop s2 = m_shopDAO.create(new Shop("Hofer"));
		Shop is = m_shopDAO.create(new Shop("Hofer - Schüttelstraße 19A"));
		is.setAddress(new Address("Schüttelstraße 19A", 48.21048970218907, 16.396751403808594));
		is.setParent(s2);
		is=m_shopDAO.update(is);



		//Create Receipt
		Receipt receipt = new Receipt("First Receipt", new Date(), is);

		//Receipt Entry
		ReceiptEntry recEnt = new ReceiptEntry(new Price(new BigDecimal("15"), Currency.dkk), bergkasese1);
		receipt.addReceiptEntriy(recEnt);
		receipt=m_receiptDAO.create(receipt);


	}

	@Override
	public ICategoryDAO getCategoryDAO() {
		return m_categoryDAO;
	}

	@Override
	public IPackageDAO getPackageDAO() {
		return m_packageDAO;
	}

	@Override
	public IProductDAO getProductDAO() {
		return m_productDAO;
	}

	@Override
	public IReceiptDAO getReceiptDAO() {
		return m_receiptDAO;
	}

	@Override
	public IShopDAO getShopDAO() {
		return m_shopDAO;
	}

	@Override
	public IUnitDAO getUnitDAO() {
		return m_unitDAO;
	}
}
