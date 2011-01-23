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
 * @author Helga
 * 
 */
public class ProductServiceImpl extends RemoteServiceServlet implements IProductService {

	private ICategoryService _coreCategoryService;
	private org.tagaprice.core.api.IProductService _coreProductService;

	// dummy values
	public static final Locale defaultCoreLocale = new Locale(1, "de", "de");
	public static final Account defaultCoreAccount = new Account(1L, "love@you.org", "super", new Date());
	public static final Category defaultCoreCategory = new Category(1L, "X", null, new Date(), ProductServiceImpl.defaultCoreAccount);

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
			Product productCore;
			String newProductTitle = "newProduct";
			Unit newProductUnit = Unit.kg;
			Double newProductAmount = 2.3;
			String newProductImageURL = "";
			Category category = new Category(null, "testcat", null, new Date(), ProductServiceImpl.defaultCoreAccount);

			ProductRevision newProductRevision = new ProductRevision(null, 1, newProductTitle, new Date(), ProductServiceImpl.defaultCoreAccount, newProductUnit, newProductAmount, category , newProductImageURL);
			java.util.HashSet<ProductRevision> revisions = new java.util.HashSet<ProductRevision>();
			revisions.add(newProductRevision);

			productCore = new Product(null, ProductServiceImpl.defaultCoreLocale, revisions);

			this._coreProductService.save(productCore);

			return convertProductToGWT(productCore, 0);
		} catch (OutdatedRevisionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
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
		Long productId = null;
		Integer revisionNumber = 0;

		if (productGWT.getRevisionId() != null) {
			productId = productGWT.getRevisionId().getId();
			revisionNumber = new Long(productGWT.getRevisionId().getRevision()).intValue();


		}
		String title = productGWT.getTitle();
		Date date = new Date();
		String  imageUrl = "";

		// TODO Category must never be null!
		Category category;
		if (productGWT.getCategory() != null) {
			category = new Category(new Long(productGWT.getCategory().getId()), productGWT.getCategory().getTitle(),
					null, new Date(), ProductServiceImpl.defaultCoreAccount);
		} else {
			category = ProductServiceImpl.defaultCoreCategory;
		}

		Double amount = productGWT.getQuantity().getQuantity();
		Unit unit = productGWT.getQuantity().getUnit();

		// If product already exists...
		ProductRevision revision = new ProductRevision(productId, revisionNumber, title, date,
				ProductServiceImpl.defaultCoreAccount, unit, amount, category, "");
		Set<ProductRevision> revisions = new HashSet<ProductRevision>();
		revisions.add(revision);

		// ids must be the same value. if they are null the product must be created as new.

		Product productCore = new Product(productId, ProductServiceImpl.defaultCoreLocale, revisions);
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
