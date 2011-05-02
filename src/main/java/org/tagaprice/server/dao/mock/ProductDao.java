package org.tagaprice.server.dao.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.tagaprice.server.dao.ICategoryDao;
import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IProductDao;
import org.tagaprice.server.rpc.ProductServiceImpl;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;

public class ProductDao implements IProductDao {
	MyLogger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	int productIdCounter = 1;
	HashMap<String, Product> productsAllRevisions = new HashMap<String, Product>();
	HashMap<String, Product> productsLatest = new HashMap<String, Product>();
	ICategoryDao categoryDAO;
	Random random = new Random(7356783);

	public ProductDao(IDaoFactory daoFactory) {
		logger.log("Load mock.ProductDao...");

		categoryDAO = daoFactory.getCategoryDao();


	}

	@Override
	public Product create(Product product) {
		logger.log("new product");
		// SAVE
		// set a productID and Revision 1
		product.setId(new Long(random.nextLong()).toString());
		product.setRevision("1");
		// Save it into the hashmaps
		this.productsAllRevisions.put(product.getId(), product);
		this.productsLatest.put(product.getId(), product);

		return product;
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
			//ERROR
			logger.log("unexpected error");
			return null;
		} else {

			updateProduct = product;
			updateProduct.setRevision(new Integer(Integer.parseInt(updateProduct.getRevision()) + 1).toString());
			// find out if we have a new Package
			this.productsAllRevisions.put(updateProduct.getId(), updateProduct);
			this.productsLatest.put(updateProduct.getId(), updateProduct);
		}

		return updateProduct;
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
