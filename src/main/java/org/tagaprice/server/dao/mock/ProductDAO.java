package org.tagaprice.server.dao.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.tagaprice.server.dao.ICategoryDAO;
import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IProductDAO;
import org.tagaprice.server.rpc.ProductServiceImpl;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.entities.dump.Quantity;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;

public class ProductDAO implements IProductDAO {
	MyLogger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	int productIdCounter = 1;
	HashMap<String, Product> productsAllRevisions = new HashMap<String, Product>();
	HashMap<String, Product> productsLatest = new HashMap<String, Product>();
	ICategoryDAO categoryDAO;

	public ProductDAO(IDaoFactory daoFactory) {
		logger.log("Load mock.ProductDAO...");

		categoryDAO = daoFactory.getCategoryDAO();

		Category food = categoryDAO.get("food");
		Category nonalcoholics = categoryDAO.get("nonalcoholics");

		// TestProduct
		Product bergkasese = new Product("Bergkäse 4", food, new Unit("grams"));
		bergkasese = create(bergkasese);

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

		update(bergkasese);

		create(new Product("Extrawurst von der Theke", food, new Unit("grams")));
		create(new Product("Limonade", nonalcoholics, new Unit("liters")));

		System.out.println("ProductService startet. Size is " + this.productsAllRevisions.size() + ", "
				+ this.productsLatest.size() + ". Counter is " + this.productIdCounter + ".");
	}

	@Override
	public Product create(final Product product) {
		logger.log("new product");
		// SAVE
		// make a copy ... to get sure
		Product newProduct = product;
		// set a productID and Revision 1
		newProduct.setId(product.getTitle());
		// Save it into the hashmaps
		this.productsAllRevisions.put(newProduct.getId(), newProduct);
		this.productsLatest.put(newProduct.getId(), newProduct);

		return newProduct;
	}

	@Override
	public Product get(String id, String revision) {
		Product rc = null;

		if (id != null) {
			if (revision == null) {
				// get the current revision
				Product product = this.productsLatest.get(id);
				if (product != null) {
					rc = product;
				}
			} else {
				// get a specific revision
				Product product = this.productsAllRevisions.get(id);
				if (product != null) {
					rc = product;
				}
			}
		}
		return rc;
	}

	@Override
	public Product get(String id) {
		return get(id, null);
	}

	@Override
	public Product update(final Product product) {
		logger.log("update product");
		Product updateProduct = this.productsAllRevisions.get(product.getId());
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


			this.productsAllRevisions.put(updateProduct.getId(), updateProduct);
			this.productsLatest.put(updateProduct.getId(), updateProduct);
		}

		return product;
	}

	@Override
	public void delete(Product shop) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Product> find(Product searchPattern) {
		ArrayList<Product> products = new ArrayList<Product>();
		for (Product p : this.productsLatest.values()) {
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
