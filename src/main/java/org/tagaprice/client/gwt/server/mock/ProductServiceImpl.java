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
	HashMap<IRevisionId, IProduct> productsAllRevisions = new HashMap<IRevisionId, IProduct>();
	HashMap<Long, IProduct> productsLatest = new HashMap<Long, IProduct>();

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

		System.out.println("ProductService startet. Size is " + this.productsAllRevisions.size() + ", " + this.productsLatest.size() + ". Counter is " + this.productIdCounter + ".");

	}


	@Override
	public IProduct getProduct(IRevisionId revionsId) {
		if(revionsId != null) {
			if(revionsId.getRevision() == 0L) {
				return this.productsLatest.get(revionsId.getId());
			} else {
				return this.productsAllRevisions.get(revionsId);
			}
		} else {
			return null;
		}
	}

	@Override
	public IProduct saveProduct(final IProduct product) {
		//Check productId and revisionId
		//if productId == 0 -> save as new product
		if(product.getRevisionId() == null || product.getRevisionId().getId() == 0L) {
			//SAVE
			//make a copy ... to get sure
			IProduct newProduct = product.copy();
			//set a productID and Revision 1
			newProduct.setRevisionId(new RevisionId(this.productIdCounter++, 1L));
			//Save it into the hashmaps
			this.productsAllRevisions.put(newProduct.getRevisionId(), newProduct);
			this.productsLatest.put(newProduct.getRevisionId().getId(), newProduct);

			return newProduct;
		} else {
			//UPDATE
			IProduct updateProduct = this.productsAllRevisions.get(product.getRevisionId());
			if(updateProduct == null) {
				//ERROR
				return null;
			} else {
				//else
				//get product
				//get latest revision
				//compare revisionIds
				updateProduct = product.copy();
				updateProduct.getRevisionId().setRevision(updateProduct.getRevisionId().getRevision() + 1);
				this.productsAllRevisions.put(updateProduct.getRevisionId(), updateProduct);
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
