package org.tagaprice.client.gwt.server.diplomat;

import java.util.*;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.tagaprice.client.gwt.shared.entities.*;
import org.tagaprice.client.gwt.shared.entities.dump.*;
import org.tagaprice.client.gwt.shared.entities.productmanagement.*;
import org.tagaprice.client.gwt.shared.rpc.productmanagement.IProductService;

import org.tagaprice.core.api.ICategoryService;
import org.tagaprice.core.api.OutdatedRevisionException;
import org.tagaprice.core.api.ServerException;

import org.tagaprice.core.entities.*;
import org.tagaprice.core.entities.Category;
import org.tagaprice.core.entities.Locale;
import org.tagaprice.core.entities.Product;
import org.tagaprice.server.boot.Boot;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * This is the Servlet that relies on the server-service
 * 
 * @author Martin
 * 
 */
public class ProductServiceImpl extends RemoteServiceServlet implements IProductService {

	private ICategoryService _coreCategoryService;
	private org.tagaprice.core.api.IProductService _coreProductService;

	// dummy values
	private static Locale defaultLocale = new Locale(1, "de", "de");
	private static Account defaultAccount = new Account(1L, "love@you.org", "super", new Date());
	private static Category defaultCategory = new Category(1L, "X", null, new Date(), ProductServiceImpl.defaultAccount);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1733780607553359495L;

	Logger _log = LoggerFactory.getLogger(ProductServiceImpl.class);


	public ProductServiceImpl() {
		_log.debug("Starting GWT-ProductService");

		try {
			String service = "defaultProductService";
			_log.debug("Attempting to load "+service+" from core.api");
			_coreProductService = (org.tagaprice.core.api.IProductService) Boot.getApplicationContext().getBean(service);
			_log.debug("Loaded "+service+" successfully.");

			service = "defaultCategoryService";
			_log.debug("Attempting to load "+service+" from core.api");
			_coreCategoryService = (org.tagaprice.core.api.ICategoryService) Boot.getApplicationContext().getBean(service);
			_log.debug("Loaded "+service+" successfully.");

			_coreProductService = (org.tagaprice.core.api.IProductService) Boot.getApplicationContext().getBean("defaultProductService");

			//TODO/WORKAROUND this should be mapped by spring, but does not work yet


			//			((DefaultProductService) coreService).setProductDAO((IProductDAO) Boot.getApplicationContext().getBean("defaultProductDAO"));
			//			((DefaultProductService) coreService).setProductRevisionDAO((IProductRevisionDAO) Boot.getApplicationContext().getBean("defaultProductRevisionDAO")); //TODO/WORKAROUND this should be mapped by spring, but does not work yet

		} catch (Exception e) {
			_log.debug(e.getClass() + ": " + e.getMessage());
		} finally {
		}
	}

	@Override
	public IProduct getProduct(IRevisionId revionsId) {
		_log.debug("revisionsId: " + revionsId);


		Product product = _coreProductService.getById(revionsId.getId());

		_log.debug("found product: " + product);

		return convertProductToGWT(product, 0);
	}

	@Override
	public ArrayList<IProduct> getProducts(IProduct searchCriteria) {
		_log.debug("getProducts for name " + searchCriteria);

		List<Product> list = new ArrayList<Product>();
		try {
			if (searchCriteria != null) {
				list = _coreProductService.getByTitle(searchCriteria.getTitle());
			} else {
				list = _coreProductService.getAll();
			}
		} catch (ServerException e) {
			_log.error("Exception thrown: " + e.getMessage());
			e.printStackTrace();
		}
		ArrayList<IProduct> returnList = new ArrayList<IProduct>();

		for(Product p: list) {

			returnList.add(convertProductToGWT(p, 0));
		}
		return returnList;
	}

	@Override
	public IProduct saveProduct(IProduct product) {
		_log.debug("product :"+product);

		try {

			Product productCore = _coreProductService.save(convertProductToCore(product));

			return convertProductToGWT(productCore, 0);
		} catch (OutdatedRevisionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<ICategory> getCategories() {
		_log.debug("attempting to get available categories");

		ArrayList<ICategory> gwtCategories = new ArrayList<ICategory>();

		try {
			ArrayList<Category> coreCategories = new ArrayList<Category>(_coreCategoryService.getAll());
			_log.debug("received " + coreCategories.size() + " from CoreCategoriesService");
			CategoryConverter categoryConverter = CategoryConverter.getInstance();

			for(Category cc: coreCategories) {
				gwtCategories.add(categoryConverter.convertCoreCategoryToGWT(cc));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return gwtCategories;

	}

	/**
	 * 
	 * @param productGWT
	 * @return
	 */

	public org.tagaprice.core.entities.Product convertProductToCore(final IProduct productGWT) {
		_log.debug("Convert GWT -> core, id: " + productGWT.getRevisionId());
		// Default values for new product...
		Long productId = 0L;
		Integer revisionNumber = 0;

		if (productGWT.getRevisionId() != null) {
			productId = productGWT.getRevisionId().getId();
			revisionNumber = new Long(productGWT.getRevisionId().getRevision()).intValue();
		}
		String title = productGWT.getTitle();
		Date date = new Date();
		// TODO Category must never be null!
		Category category;
		if (productGWT.getCategory() != null) {
			category = new Category(new Long(productGWT.getCategory().getId()), productGWT.getCategory().getTitle(),
					null, new Date(), ProductServiceImpl.defaultAccount);
		} else {
			category = ProductServiceImpl.defaultCategory;
		}

		Double amount = productGWT.getQuantity().getQuantity();
		Unit unit = productGWT.getQuantity().getUnit();

		// ProductRevision quantity = new ProductRevision(productId, revisionNumber, title, date,
		// ProductServiceImpl.defaultAccount, unit, amount , category, "");
		// If product already exists...
		ProductRevision revision = new ProductRevision(productId, revisionNumber, title, date,
				ProductServiceImpl.defaultAccount, unit, amount, category, "");
		Set<ProductRevision> revisions = new HashSet<ProductRevision>();
		revisions.add(revision);

		Product productCore = new Product(productGWT.getRevisionId().getId(), ProductServiceImpl.defaultLocale,
				revisions);
		return productCore;
	}

	/**
	 * 
	 * @param productCore
	 * @param revision
	 *            when 0, then the latest revision is returned.
	 * @return
	 */
	public IProduct convertProductToGWT(final Product productCore, int revisionToGet) {
		_log.debug("Convert core -> GWT, id: " + productCore.getId() + ", rev: " + revisionToGet);
		// these are always existing products!!!
		ProductRevision pr = productCore.getCurrentRevision();


		// get the data from the latest revision
		long id = productCore.getId();
		long revision = pr.getRevisionNumber();
		String title = pr.getTitle();
		ICategory category = new org.tagaprice.client.gwt.shared.entities.dump.Category(pr.getCategory().getTitle());
		IQuantity quantity = new Quantity(pr.getAmount(), pr.getUnit());

		IRevisionId revisionId = new RevisionId(id, revision);
		IProduct productGWT = new org.tagaprice.client.gwt.shared.entities.productmanagement.Product(revisionId, title,
				category, quantity);
		return productGWT;
	}

	public void setProductService(org.tagaprice.core.api.IProductService productService) {

		_coreProductService = productService;

	}

}
