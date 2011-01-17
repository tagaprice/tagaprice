package org.tagaprice.server.dao.interfaces;

import java.util.List;

import org.tagaprice.core.entities.Product;
import org.tagaprice.core.entities.ProductRevision;

public interface IProductRevisionDAO {

	/**
	 * Retrieves a {@link List} of all {@link Product}s, which title has given title in it, ignoring differences in case.
	 */
	List<ProductRevision> getByTitle(String title);

}
