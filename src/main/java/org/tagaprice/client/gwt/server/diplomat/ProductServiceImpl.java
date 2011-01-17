package org.tagaprice.client.gwt.server.diplomat;

import java.util.*;

import org.tagaprice.client.gwt.shared.entities.*;
import org.tagaprice.client.gwt.shared.entities.dump.*;
import org.tagaprice.client.gwt.shared.entities.productmanagement.*;
import org.tagaprice.client.gwt.shared.logging.*;
import org.tagaprice.client.gwt.shared.rpc.productmanagement.IProductService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
/**
 * This is the Servlet that relies on the server-service
 * @author Martin
 *
 */
public class ProductServiceImpl extends RemoteServiceServlet implements
IProductService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1733780607553359495L;
	MyLogger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Override
	public IProduct getProduct(IRevisionId revionsId) {
		// TODO Auto-generated method stub
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

}
