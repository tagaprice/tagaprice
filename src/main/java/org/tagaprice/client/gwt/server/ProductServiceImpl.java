package org.tagaprice.client.gwt.server;

import java.util.ArrayList;

import org.tagaprice.client.gwt.client.rpc.ProductService;
import org.tagaprice.client.gwt.shared.entities.*;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ProductServiceImpl extends RemoteServiceServlet implements
		ProductService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -589742797415233033L;
	ArrayList<Product> products;

	public ProductServiceImpl() {
		this.products = new ArrayList<Product>();
		this.products.add(new ProductImpl(0, "NOEM Mix Erdbeere", 95,
				"supertolles Joghurt", "Milchprodukte"));
		this.products.add(new ProductImpl(1, "Soletti", 120, "Knabberspass",
				"Knabberzeugs"));
		this.products.add(new ProductImpl(2, "Milch", 95, "gute Milch",
				"Milchprodukte"));
	}

	@Override
	public Product getProductById(int id) {
		for (Product p : this.products) {
			if (p.getId() == id) {
				return p;
			}
		}
		return null;
	}

	@Override
	public Product getProductByName(String name) {
		for (Product p : this.products) {
			if (p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}

	@Override
	public ArrayList<ProductCore> getProducts() {
		ArrayList<ProductCore> productCores = new ArrayList<ProductCore>();
		for (Product p : this.products) {
			productCores.add(new ProductCoreImpl(p.getId(), p.getName()));
		}
		return productCores;
	}

	@Override
	public Product getProductsByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveProduct(Product product, boolean exists) {
		// TODO proper id management
		if (exists) {
			for (Product existingProduct : this.products) {
				if (existingProduct.getId() == product.getId()) {
					existingProduct.setName(product.getName());
					existingProduct.setDescription(product.getDescription());
					existingProduct.setCategory(product.getCategory());
				}
			}
		} else {
			this.products.add(product);
		}

	}

}
