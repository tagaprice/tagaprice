package org.tagaprice.client.gwt.server.mock;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.*;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;
import org.tagaprice.client.gwt.shared.rpc.productmanagement.IProductService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ProductServiceImpl extends RemoteServiceServlet implements
IProductService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5070553288173843014L;

	@Override
	public IProduct getProduct(IRevisionId revionsId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IProduct saveProduct(IProduct product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<IProduct> getProducts(IProduct searchCriteria) {
		// TODO Auto-generated method stub
		return null;
	}


}
