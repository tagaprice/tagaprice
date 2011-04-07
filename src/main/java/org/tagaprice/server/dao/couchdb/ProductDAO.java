package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.List;

import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.tagaprice.server.dao.IProductDAO;
import org.tagaprice.shared.entities.RevisionId;
import org.tagaprice.shared.entities.productmanagement.IProduct;
import org.tagaprice.shared.entities.productmanagement.Product;

public class ProductDAO extends DAOClass<IProduct> implements IProductDAO {
	public ProductDAO() {
		super(Product.class, "product");
	}

	@Override
	public List<IProduct> find(IProduct searchPattern) {
		/// TODO implement actual searching here
		ViewResult<?> result = m_db.listDocuments(null, null);
		List<IProduct> rc = new ArrayList<IProduct>();
		
		System.out.println("CatList:");
		for (ValueRow<?> row: result.getRows()) {
			IProduct product = get(new RevisionId(row.getId()));
			rc.add(product);
		}
		
		return rc;

	}
}
