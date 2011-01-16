package org.tagaprice.client.gwt.server.mock;

import java.util.*;

import org.tagaprice.client.gwt.shared.entities.*;
import org.tagaprice.client.gwt.shared.entities.dump.*;
import org.tagaprice.client.gwt.shared.entities.productmanagement.*;
import org.tagaprice.client.gwt.shared.rpc.productmanagement.IProductService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ProductServiceImpl extends RemoteServiceServlet implements
IProductService {

	int productIdCounter = 1;
	HashMap<IRevisionId, IProduct> products = new HashMap<IRevisionId, IProduct>();

	/**
	 * 
	 */
	private static final long serialVersionUID = 5070553288173843014L;

	/**
	 * Initialization with some products and Categories.
	 */
	public ProductServiceImpl() {
		ICategory root = new Category("root");
		ICategory food = new Category("food");
		ICategory vegetables = new Category("vegetables");
		ICategory beverages = new Category("beverages");
		ICategory alcoholics = new Category("alcohol");
		ICategory nonalcoholics = new Category("nonalcoholics");
		root.addChildCategory(food);
		root.addChildCategory(beverages);
		food.addChildCategory(vegetables);
		beverages.addChildCategory(alcoholics);
		beverages.addChildCategory(nonalcoholics);

		this.saveProduct(new Product("Bergkäse", food, new Quantity(100, Unit.g)));
		this.saveProduct(new Product("Extrawurst von der Theke", food, new Quantity(0, Unit.g)));
		this.saveProduct(new Product("Limonade", nonalcoholics, new Quantity(1.5, Unit.l)));
	}


	@Override
	public IProduct getProduct(IRevisionId revionsId) {
		return this.products.get(revionsId);
	}

	@Override
	public IProduct saveProduct(final IProduct product) {
		//Check productId and revisionId
		//if productId == 0 -> save as new product
		if(product.getRevisionId() == null || product.getRevisionId().getId() == 0L) {
			//SAVE
			IProduct newProduct = product.copy();
			newProduct.setRevisionId(new RevisionId(this.productIdCounter++));
			this.products.put(product.getRevisionId(), newProduct);
			return newProduct;
		} else {
			//UPDATE
			IProduct updateProduct = this.products.get(product.getRevisionId());
			if(updateProduct == null) {
				//ERROR
				return null;
			} else {
				//else
				//get product
				//get latest revision
				//compare revisionIds
				updateProduct = updateProduct.copy();
				updateProduct.getRevisionId().setRevision(updateProduct.getRevisionId().getRevision() + 1);
				this.products.put(updateProduct.getRevisionId(), updateProduct);
				return updateProduct;
			}
		}
	}

	@Override
	public ArrayList<IProduct> getProducts(IProduct searchCriteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ICategory> getCategories() {
		// TODO Auto-generated method stub
		return null;
	}


}
