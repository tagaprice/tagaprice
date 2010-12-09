package org.tagaprice.client.gwt.client.rpc;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.*;



import com.google.gwt.user.client.rpc.*;

@RemoteServiceRelativePath("productservice")
public interface ProductService extends RemoteService {

	public Product getProductById(int id);

	public Product getProductByName(String name);

	public ArrayList<ProductCore> getProducts();

	public Product getProductsByCategory(String category);

	public void saveProduct(Product product, boolean exists);

}
