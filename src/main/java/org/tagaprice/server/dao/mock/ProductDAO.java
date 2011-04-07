package org.tagaprice.server.dao.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.tagaprice.server.dao.ICategoryDAO;
import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IProductDAO;
import org.tagaprice.server.rpc.ProductServiceImpl;
import org.tagaprice.shared.entities.IRevisionId;
import org.tagaprice.shared.entities.RevisionId;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.dump.ICategory;
import org.tagaprice.shared.entities.dump.Quantity;
import org.tagaprice.shared.entities.productmanagement.IPackage;
import org.tagaprice.shared.entities.productmanagement.IProduct;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;

public class ProductDAO implements IProductDAO {
	MyLogger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	int productIdCounter = 1;
	HashMap<IRevisionId, IProduct> productsAllRevisions = new HashMap<IRevisionId, IProduct>();
	HashMap<String, IProduct> productsLatest = new HashMap<String, IProduct>();
	ICategoryDAO categoryDAO;
	
	public ProductDAO(IDaoFactory daoFactory) {
		logger.log("Load mock.ProductDAO...");

		categoryDAO = daoFactory.getCategoryDAO();
		
		ICategory food = categoryDAO.get("food");
		ICategory nonalcoholics = categoryDAO.get("nonalcoholics");
		
		// TestProduct
		IProduct bergkasese = new Product("Bergkäse 4", food, Unit.g);
		bergkasese = create(bergkasese);

		{
			IPackage tPackage=new Package(new Quantity(500, Unit.kg));
			tPackage.setProduct(bergkasese);
			bergkasese.addPackage(tPackage);
		}
		{
			IPackage tPackage=new Package(new Quantity(750, Unit.g));
			tPackage.setProduct(bergkasese);
			bergkasese.addPackage(tPackage);
		}

		update(bergkasese);
		
		create(new Product("Extrawurst von der Theke", food, Unit.g));
		create(new Product("Limonade", nonalcoholics, Unit.l));

		System.out.println("ProductService startet. Size is " + this.productsAllRevisions.size() + ", "
				+ this.productsLatest.size() + ". Counter is " + this.productIdCounter + ".");
	}
	
	@Override
	public IProduct create(final IProduct product) {
		logger.log("new product");
		// SAVE
		// make a copy ... to get sure
		IProduct newProduct = product;
		// set a productID and Revision 1
		newProduct.setId(product.getTitle());
		// Save it into the hashmaps
		this.productsAllRevisions.put(new RevisionId(newProduct.getId(), newProduct.getRevision()), newProduct);
		this.productsLatest.put(newProduct.getId(), newProduct);

		return newProduct;
	}

	@Override
	public IProduct get(String id, String revision) {
		IProduct rc = null;
		
		if (id != null) {
			if (revision == null) {
				// get the current revision
				IProduct product = this.productsLatest.get(id);
				if (product != null) {
					rc = product;
				}
			} else {
				// get a specific revision
				IProduct product = this.productsAllRevisions.get(id);
				if (product != null) {
					rc = product;
				}
			}
		}
		return rc;
	}
	
	@Override
	public IProduct get(String id) {
		return get(id, null);
	}

	@Override
	public IProduct update(final IProduct product) {
		logger.log("update product");
		IProduct updateProduct = this.productsAllRevisions.get(new RevisionId(product.getId(), product.getRevision()));
		if (updateProduct == null) {
			// ERROR
			return product;
		} else {
			// else
			// get product
			// get latest revision
			// compare revisionIds
			updateProduct = product;
			updateProduct.setRevision(updateProduct.getRevision() + 1);


			// find out if we have a new Package


			this.productsAllRevisions.put(new RevisionId(updateProduct.getId(), updateProduct.getRevision()), updateProduct);
			this.productsLatest.put(updateProduct.getId(), updateProduct);
		}
		
		return product;
	}

	@Override
	public void delete(IProduct shop) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<IProduct> find(IProduct searchPattern) {
		ArrayList<IProduct> products = new ArrayList<IProduct>();
		for (IProduct p : this.productsLatest.values()) {
			if (searchPattern != null
					&& p.getTitle().toLowerCase().contains((searchPattern.getTitle().toLowerCase()))) {
				products.add(p);
			} else if (searchPattern == null) {
				products.add(p);
			}
		}
		return products;
	}

}
