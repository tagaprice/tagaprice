package org.tagaprice.client.gwt.server.diplomat;

import java.util.*;

import org.tagaprice.client.gwt.shared.entities.*;
import org.tagaprice.client.gwt.shared.entities.dump.*;
import org.tagaprice.client.gwt.shared.entities.productmanagement.*;
import org.tagaprice.client.gwt.shared.logging.*;
import org.tagaprice.client.gwt.shared.rpc.productmanagement.IProductService;
import org.tagaprice.core.entities.*;
import org.tagaprice.core.entities.Category;
import org.tagaprice.core.entities.Locale;
import org.tagaprice.core.entities.Product;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
/**
 * This is the Servlet that relies on the server-service
 * @author Martin
 *
 */
public class ProductServiceImpl extends RemoteServiceServlet implements
IProductService {

	private org.tagaprice.core.api.IProductService coreService;
	private static Locale defaultLocale = new Locale(1, "de", "de");
	private static Account defaultAccount = new Account(1L, "love@you.org", "super", new Date());

	/**
	 * 
	 */
	private static final long serialVersionUID = 1733780607553359495L;
	MyLogger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Override
	public IProduct getProduct(IRevisionId revionsId) {
		return null;
	}

	@Override
	public ArrayList<IProduct> getProducts(IProduct searchCriteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IProduct saveProduct(IProduct product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ICategory> getCategories() {
		// TODO Auto-generated method stub
		return null;
	}

	private org.tagaprice.core.entities.Product convertProductToCore(final IProduct productGWT) {
		//Default values for new product...
		Long productId = 0L;
		Integer revisionNumber = 1;
		String title = productGWT.getTitle();
		Date date = new Date();
		Category category = new Category(null, productGWT.getCategory().getTitle(), null, new Date(), ProductServiceImpl.defaultAccount);
		//If product allready exists...
		if(productGWT.getRevisionId() != null && productGWT.getRevisionId().getId() != null && productGWT.getRevisionId().getId() != 0L) {
			//productId =
		}
		ProductRevision revision = new ProductRevision(productId, revisionNumber, title, date, ProductServiceImpl.defaultAccount, null, null, category, "");
		Product productCore = new Product(productGWT.getRevisionId().getId(), ProductServiceImpl.defaultLocale, null);
		return null;
	}

	/**
	 * 
	 * @param productCore
	 * @param revision when 0, then the latest revision is returned.
	 * @return
	 */
	private IProduct convertProductToGWT(final Product productCore, int revisionToGet) {
		//these are allways existing products!!!
		//get the data from the latest revision
		long id = 0;
		long revision = 0;
		String title = "";
		ICategory category = null;
		IQuantity quantity = null;

		IRevisionId revisionId = new RevisionId(id, revision);
		IProduct productGWT = new org.tagaprice.client.gwt.shared.entities.productmanagement.Product(title, category, quantity);


		return productGWT;
	}

}
