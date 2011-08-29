package org.tagaprice.server.dao.mock;

import java.math.BigDecimal;
import java.util.Date;
import org.tagaprice.server.dao.ICategoryDao;
import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IPackageDao;
import org.tagaprice.server.dao.IProductDao;
import org.tagaprice.server.dao.IReceiptDao;
import org.tagaprice.server.dao.ISearchDao;
import org.tagaprice.server.dao.ISessionDao;
import org.tagaprice.server.dao.IShopDao;
import org.tagaprice.server.dao.IStatisticDao;
import org.tagaprice.server.dao.IUnitDao;
import org.tagaprice.server.dao.IUserDao;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.Quantity;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.accountmanagement.User;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.receiptManagement.Currency;
import org.tagaprice.shared.entities.receiptManagement.Price;
import org.tagaprice.shared.entities.receiptManagement.Receipt;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.exceptions.dao.DaoException;
import com.allen_sauer.gwt.log.client.Log;

public class MockDaoFactory implements IDaoFactory {
	private final ICategoryDao m_categoryDAO = new CategoryDao();
	private final IPackageDao m_packageDAO = new PackageDao();
	private final IProductDao m_productDAO = new ProductDao();
	private final IReceiptDao m_receiptDAO = new ReceiptDao();
	private final ISessionDao m_sessionDAO = new SessionDao();
	private final IShopDao m_shopDAO = new ShopDao();
	private final IUnitDao m_unitDAO = new UnitDao();
	private final IUserDao m_userDAO = new UserDao();
	private final IStatisticDao m_statisticDAO = new StatisticDao();
	private ISearchDao m_searchDao = new SearchDao();

	private final User m_testUser = new User("testUser_id", "testRev", "Test User");

	public MockDaoFactory() throws DaoException {
		Log.debug("Initialize Mock data");
		// TODO Auto-generated constructor stub


		//Create Categories
		Category food = m_categoryDAO.create(new Category(m_testUser, "food", null));
		Category vegetables = m_categoryDAO.create(new Category(m_testUser, "vegetables", food));
		Category beverages = m_categoryDAO.create(new Category(m_testUser, "beverages", null));
		Category nonalcoholics = m_categoryDAO.create(new Category(m_testUser, "nonalcoholics", beverages));


		//Create units
		Unit st = m_unitDAO.create(new Unit(m_testUser, "st",null,1.0));
		Unit g = m_unitDAO.create(new Unit(m_testUser, "g",null,1.0));
		Unit kg = m_unitDAO.create(new Unit(m_testUser, "kg",g,1000));
		Unit mg = m_unitDAO.create(new Unit(m_testUser, "mg",g,0.001));
		Unit l = m_unitDAO.create(new Unit(m_testUser, "l",null,1.0));
		Unit ml = m_unitDAO.create(new Unit(m_testUser, "ml",l,1000));
		/*
		m_unitDAO.setFactorizedUnit(st.getId(), st.getId(), 1);
		Unit kg = m_unitDAO.create(new Unit(m_testUser, "kg"));
		Unit g = m_unitDAO.create(new Unit(m_testUser, "g"));
		m_unitDAO.setFactorizedUnit(kg.getId(), g.getId(), 1000);
		m_unitDAO.setFactorizedUnit(kg.getId(), kg.getId(), 1);
		m_unitDAO.setFactorizedUnit(g.getId(), kg.getId(), 0.001);
		m_unitDAO.setFactorizedUnit(g.getId(), g.getId(), 1);


		Unit l = m_unitDAO.create(new Unit(m_testUser, "l"));
		Unit ml = m_unitDAO.create(new Unit(m_testUser, "ml"));
		m_unitDAO.setFactorizedUnit(l.getId(), ml.getId(), 1000);
		m_unitDAO.setFactorizedUnit(ml.getId(), l.getId(), 0.001);
		m_unitDAO.setFactorizedUnit(ml.getId(), ml.getId(), 1);
		m_unitDAO.setFactorizedUnit(l.getId(), l.getId(), 1);
		 */

		//Create Products
		// TestProduct
		Product bergkasese = new Product(m_testUser, "Bergkäse 4", vegetables, g);
		bergkasese = m_productDAO.create(bergkasese);


		Package bergkasese1=new Package(new Quantity(new BigDecimal("500.3"), g));
		bergkasese1.setProduct(bergkasese);
		bergkasese.addPackage(bergkasese1);


		Package bergkasese2=new Package(new Quantity(new BigDecimal("500.5"), g));
		bergkasese2.setProduct(bergkasese);
		bergkasese.addPackage(bergkasese2);


		m_productDAO.update(bergkasese);

		m_productDAO.create(new Product(m_testUser, "Extrawurst von der Theke", vegetables, g));
		m_productDAO.create(new Product(m_testUser, "Limonade", nonalcoholics, l));


		//Create shops
		//Create address for Shop(bills)
		//Create some Shops
		//Shop s1 = m_shopDAO.create(new Shop(m_testUser, "Billa", vegetables));

		Shop is2 = m_shopDAO.create(new Shop(m_testUser, "Billa - Blumauergasse 1B", vegetables));
		is2.setAddress(new Address("Blumauergasse 1B", 48.21906856732104, 16.38164520263672));
		//is2.setParent(s1);
		is2=m_shopDAO.update(is2);




		Shop is3 = m_shopDAO.create(new Shop(m_testUser, "Billa - Holzhausergasse 9", vegetables));
		is3.setAddress(new Address("Holzhausergasse 9", 48.21975481443672, 16.38885498046875));
		//is3.setParent(s1);
		is3=m_shopDAO.update(is3);





		//2 shop
		//Create address for Shop(bills)
		//Create some Shop
		Shop s2 = m_shopDAO.create(new Shop(m_testUser, "Hofer",vegetables));
		Shop is = m_shopDAO.create(new Shop(m_testUser, "Hofer - Schüttelstraße 19A",vegetables));
		is.setAddress(new Address("Schüttelstraße 19A", 48.21048970218907, 16.396751403808594));
		is.setParent(s2);
		is=m_shopDAO.update(is);


		{
			//Create Receipt
			Receipt receipt = new Receipt(m_testUser, "First Receipt", new Date(), is);

			//Receipt Entry
			ReceiptEntry recEnt = new ReceiptEntry(new Price(new BigDecimal("15.0"), Currency.euro), bergkasese1);
			receipt.addReceiptEntries(recEnt);
			receipt=m_receiptDAO.create(receipt);
		}

		{
			//Create Receipt
			Receipt receipt = new Receipt(m_testUser, "Second Receipt", new Date(), is3);

			//Receipt Entry
			receipt.addReceiptEntries(new ReceiptEntry(new Price(new BigDecimal("16.0"), Currency.euro), bergkasese1));
			receipt.addReceiptEntries(new ReceiptEntry(new Price(new BigDecimal("17.0"), Currency.euro), bergkasese2));
			receipt=m_receiptDAO.create(receipt);
		}

		{
			//Create Receipt
			Receipt receipt = new Receipt(m_testUser, "Second Receipt", new Date(), is2);

			//Receipt Entry
			receipt.addReceiptEntries(new ReceiptEntry(new Price(new BigDecimal("13.0"), Currency.euro), bergkasese1));
			receipt.addReceiptEntries(new ReceiptEntry(new Price(new BigDecimal("14.0"), Currency.euro), bergkasese2));
			receipt=m_receiptDAO.create(receipt);
		}


	}

	@Override
	public ICategoryDao getProductCategoryDao() {
		return m_categoryDAO;
	}

	@Override
	public IPackageDao getPackageDao() {
		return m_packageDAO;
	}

	@Override
	public IProductDao getProductDao() {
		return m_productDAO;
	}

	@Override
	public IReceiptDao getReceiptDao() {
		return m_receiptDAO;
	}
	
	@Override
	public ISearchDao getSearchDao() {
		return m_searchDao;
	}

	@Override
	public ISessionDao getSessionDao() {
		return m_sessionDAO;
	}

	@Override
	public IShopDao getShopDao() {
		return m_shopDAO;
	}

	@Override
	public IUnitDao getUnitDao() {
		return m_unitDAO;
	}

	@Override
	public IUserDao getUserDao() {
		return m_userDAO;
	}

	@Override
	public void init() {
		// nothing to do here
	}

	@Override
	public IStatisticDao getStatisticDao() {
		return m_statisticDAO;
	}

	@Override
	public ICategoryDao getShopCategoryDao() {
		return m_categoryDAO;
	}
}
