package org.tagaprice.server.dao.mock;

import java.util.ArrayList;

import org.tagaprice.server.dao.ICategoryDAO;
import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IPackageDAO;
import org.tagaprice.server.dao.IProductDAO;
import org.tagaprice.server.dao.IReceiptDAO;
import org.tagaprice.server.dao.IShopDAO;
import org.tagaprice.server.dao.IUnitDAO;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.entities.dump.Quantity;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;

public class MockDaoFactory implements IDaoFactory {
	MyLogger logger = LoggerFactory.getLogger(MockDaoFactory.class);
	ICategoryDAO m_categoryDAO = new CategoryDAO(this);
	IPackageDAO m_packageDAO = new PackageDAO();
	IProductDAO m_productDAO = new ProductDAO(this);
	IReceiptDAO m_receiptDAO = new ReceiptDAO(this);
	IShopDAO m_shopDAO = new ShopDAO();
	IUnitDAO m_unitDAO = new UnitDAO();

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


		//Create Products
		// TestProduct
		Product bergkasese = new Product("Bergkäse 4", vegetables, new Unit("grams"));
		bergkasese = m_productDAO.create(bergkasese);

		{
			Package tPackage=new Package(new Quantity(500, new Unit("kg")));
			tPackage.setProduct(bergkasese);
			bergkasese.addPackage(tPackage);
		}
		{
			Package tPackage=new Package(new Quantity(750, new Unit("g")));
			tPackage.setProduct(bergkasese);
			bergkasese.addPackage(tPackage);
		}

		m_productDAO.update(bergkasese);

		m_productDAO.create(new Product("Extrawurst von der Theke", vegetables, new Unit("grams")));
		m_productDAO.create(new Product("Limonade", nonalcoholics, new Unit("liters")));


		//Create shops
		//Create address for Shop(bills)
		ArrayList<Shop> al1 = new ArrayList<Shop>();

		{
			Shop is = m_shopDAO.create(new Shop("Billa - Blumauergasse 1B"));
			is.setAddress(new Address("Blumauergasse 1B", 48.21906856732104, 16.38164520263672));
			is=m_shopDAO.update(is);
			al1.add(is);
		}


		{
			Shop is = m_shopDAO.create(new Shop("Billa - Holzhausergasse 9"));
			is.setAddress(new Address("Holzhausergasse 9", 48.21975481443672, 16.38885498046875));
			is=m_shopDAO.update(is);
			al1.add(is);
		}

		//Create some Shops
		Shop s1 = m_shopDAO.create(new Shop("Billa"));
		s1.setChildren(al1);
		s1=m_shopDAO.update(s1);


		//2 shop
		//Create address for Shop(bills)
		ArrayList<Shop> al2 = new ArrayList<Shop>();

		{
			Shop is = m_shopDAO.create(new Shop("Hofer - Schüttelstraße 19A"));
			is.setAddress(new Address("Schüttelstraße 19A", 48.21048970218907, 16.396751403808594));
			is=m_shopDAO.update(is);
			al2.add(is);
		}


		//Create some Shop
		Shop s2 = m_shopDAO.create(new Shop("Hofer"));
		s2.setChildren(al2);
		s2=m_shopDAO.update(s2);
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
