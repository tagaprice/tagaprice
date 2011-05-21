package org.tagaprice.server.dao.mock;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.tagaprice.server.dao.IProductDao;
import org.tagaprice.server.rpc.ProductServiceImpl;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.logging.LoggerFactory;

public class ProductDao extends DaoClass<Product> implements IProductDao {

	@Override
	public List<Product> find(Product searchPattern) {
		ArrayList<Product> rc = new ArrayList<Product>();

		for (Deque<Product> deque: m_data.values()) {
			Product product = deque.peek();

			if (searchPattern != null
					&& product.getTitle().toLowerCase().contains((searchPattern.getTitle().toLowerCase()))) {
				rc.add(product);
			} else if (searchPattern == null) {
				rc.add(product);
			}
		}

		return rc;
	}

}
