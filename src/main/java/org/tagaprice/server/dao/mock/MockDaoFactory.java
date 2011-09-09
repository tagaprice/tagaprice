package org.tagaprice.server.dao.mock;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

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
	private static Random _random = _createPRNG();

	private final User m_testUser = new User("test@tagaprice.org");

	public MockDaoFactory() throws DaoException {
		Log.debug("Initialize Mock data");
		// TODO Auto-generated constructor stub
		
		
		String salt = generateSalt(24);
		String pwdHash = null;
		
		try {
			pwdHash = md5("tagaprice"+salt);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		m_testUser.setMail("test@tagaprice.org");
		m_testUser.setPasswordSalt(salt);
		m_testUser.setPasswordHash(pwdHash);
		
		m_userDAO.create(m_testUser);


		//Create Categories
		Category food = m_categoryDAO.create(new Category(m_testUser, "food", null));
		Category vegetables = m_categoryDAO.create(new Category(m_testUser, "vegetables", food));
		Category beverages = m_categoryDAO.create(new Category(m_testUser, "beverages", null));
		Category nonalcoholics = m_categoryDAO.create(new Category(m_testUser, "nonalcoholics", beverages));


		//Create units
		Unit g = m_unitDAO.create(new Unit(m_testUser, "g",null,1.0));
		Unit l = m_unitDAO.create(new Unit(m_testUser, "l",null,1.0));
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
		is2.setAddress(new Address("Blumauergasse 1B", 48.0, 16.0));
		//is2.setParent(s1);
		is2=m_shopDAO.update(is2);




		Shop is3 = m_shopDAO.create(new Shop(m_testUser, "Billa - Holzhausergasse 9", vegetables));
		is3.setAddress(new Address("Holzhausergasse 9", 48.0, 16.0));
		//is3.setParent(s1);
		is3=m_shopDAO.update(is3);





		//2 shop
		//Create address for Shop(bills)
		//Create some Shop
		//Shop s2 = m_shopDAO.create(new Shop(m_testUser, "Hofer",vegetables));
		Shop is = m_shopDAO.create(new Shop(m_testUser, "Hofer - Schüttelstraße 19A",vegetables));
		is.setAddress(new Address("Schüttelstraße 19A", 48.21048970218907, 16.396751403808594));
		//is.setParent(s2);
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
	
	/**
	 * Use a Pseudo Random Number Generator to generate an arbitrary-length random String that
	 * can be used as password hash salt (or for anything else)
	 * 
	 * @param len Desired length of the returned String
	 * @return Random String with the given length
	 */
	private static String generateSalt(int len) {
		String rc = "";

		for (int i = 0; i < len; i++) {
			int n = _random.nextInt(62);
			char c;
			if (n < 26) c = (char)(n+'a');
			else if (n < 52) c = (char)(n-26+'A');
			else c = (char) (n-52+'0');
			rc += c;
		}
		return rc;
	}
	
	/**
	 * Creates, initializes and returns a Pseudo Random Number Generator object
	 * 
	 * On UNIX-like systems this function initializes the PRNG using data read from
	 * /dev/urandom to make sure the seed is less predictable.
	 * @return PRNG object
	 */
	private static Random _createPRNG() {
		Random rc = null;

		try {
			FileInputStream in = new FileInputStream("/dev/urandom");
			int n;
			long seed = 0;

			// read 8 characters and put them in a long variable
			for (int i = 0; i < 8; i++) {
				n = in.read();
				if(n >= 0) {
					seed *= 256;
					seed += n;
				}
			}

			rc = new Random(seed);
		}
		catch (IOException e) { // /dev/urandom can't be read
			Log.error("Warning: using current time as random seed");
			rc = new Random();
		}

		return rc;
	}
	
	public String md5(String in) throws NoSuchAlgorithmException {
		// calculate hash
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(in.getBytes());
		byte[] hash = md5.digest();
		StringBuffer rc = new StringBuffer();
		for (int i=0; i<hash.length; i++) {
			String hex = "0"+Integer.toHexString(0xFF & hash[i]);
			if (hex.length()>2) hex = hex.substring(hex.length()-2);
			rc.append(hex);
		}

		return rc.toString();
	}
}
