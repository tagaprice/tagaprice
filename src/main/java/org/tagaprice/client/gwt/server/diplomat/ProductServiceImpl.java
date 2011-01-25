package org.tagaprice.client.gwt.server.diplomat;

import java.util.*;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.tagaprice.client.gwt.server.diplomat.converter.*;
import org.tagaprice.client.gwt.shared.entities.*;
import org.tagaprice.client.gwt.shared.entities.dump.*;
import org.tagaprice.client.gwt.shared.entities.productmanagement.*;
import org.tagaprice.client.gwt.shared.rpc.productmanagement.IProductService;

import org.tagaprice.core.api.ICategoryService;
import org.tagaprice.core.api.OutdatedRevisionException;
import org.tagaprice.core.api.ServerException;
import org.tagaprice.core.api.UserNotLoggedInException;

import org.tagaprice.core.entities.Category;
import org.tagaprice.core.entities.Product;
import org.tagaprice.core.entities.Session;
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


	/**
	 * 
	 */
	private static final long serialVersionUID = 1733780607553359495L;

	Logger _log = LoggerFactory.getLogger(ProductServiceImpl.class);


	public ProductServiceImpl() {
		_log.debug("Starting Core-ProductService");

		try {
			String service = "defaultProductService";
			_log.debug("Attempting to load "+service+" from core.api");
			_coreProductService = (org.tagaprice.core.api.IProductService) Boot.getApplicationContext().getBean(service);
			_log.debug("Loaded "+service+" successfully.");

			service = "defaultCategoryService";
			_log.debug("Attempting to load "+service+" from core.api");
			_coreCategoryService = (org.tagaprice.core.api.ICategoryService) Boot.getApplicationContext().getBean(service);
			_log.debug("Loaded "+service+" successfully.");

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

		ProductConverter converter = ProductConverter.getInstance();
		Product product = _coreProductService.getById(revionsId.getId());
		_log.debug("found product: " + product);

		IProduct gwtProduct;
		gwtProduct = converter.convertProductToGWT(product, revionsId.getRevision());
		return gwtProduct;
	}

	@Override
	public ArrayList<IProduct> getProducts(IProduct searchCriteria) {
		_log.debug("getProducts for name " + searchCriteria);

		ProductConverter converter = ProductConverter.getInstance();

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

			returnList.add(converter.convertProductToGWT(p, 0));
		}
		return returnList;
	}

	@Override
	public IProduct saveProduct(IProduct product) {
		_log.debug("product :"+product);

		ProductConverter converter = ProductConverter.getInstance();

		try {
			Product productCore = converter.convertProductToCore(product);

//			Session session = (Session) getThreadLocalRequest().getSession().getAttribute("session");
			Session session = Session.getRootToken(); //TODO replace this call by the one above
			productCore = this._coreProductService.save(productCore, session);

			return converter.convertProductToGWT(productCore, 0);
		} catch (OutdatedRevisionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserNotLoggedInException e) {
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

	public void setProductService(org.tagaprice.core.api.IProductService productService) {

		_coreProductService = productService;

	}

}
