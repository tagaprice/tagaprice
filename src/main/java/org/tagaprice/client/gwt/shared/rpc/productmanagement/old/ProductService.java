package org.tagaprice.client.gwt.shared.rpc.productmanagement.old;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.*;



import com.google.gwt.user.client.rpc.*;

@RemoteServiceRelativePath("productserviceOld")
public interface ProductService extends RemoteService {

	public Product getProductById(int id);

	public Product getProductByName(String name);

	public ArrayList<ProductCore> getProducts();

	public Product getProductsByCategory(String category);

	public void saveProduct(Product product, boolean exists);

}
