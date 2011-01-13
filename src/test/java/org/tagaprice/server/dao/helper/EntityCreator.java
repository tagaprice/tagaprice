package org.tagaprice.server.dao.helper;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.tagaprice.core.entities.Locale;
import org.tagaprice.core.entities.Product;
import org.tagaprice.core.entities.ProductRevision;

public class EntityCreator {
	private static Date _standardDate = new Date(1293145200000L); //2010/12/24


	/**
	 * Creates a Product with given id, an associated locale with given id and as many revisions (starting with revision id) as given numberRevisions indicates.
	 * All created objects will be initialized with reasonable default values.
	 * @param id Id of Product to create. Can be null, if not must be greater than 0.
	 * @param numberRevisions Number of ProductRevisions to be added to Product. Must not be null and must be greater than 0.
	 * @param localeId Id of Locale for product. Must not be null and must be greater than 0.
	 */
	public static Product createProductWithRevisions(Long id, Integer numberRevisions, Integer localeId) {
		Set<ProductRevision> revisions = new HashSet<ProductRevision>();

		for(int rev = 1; rev<=numberRevisions;rev++)
			revisions.add(createProductRevision(id, rev));

		Product productToSave = new Product(id, EntityCreator.createLocale(localeId), revisions);
		return productToSave;
	}

	/**
	 * Creates a ProductRevision with given id and revision and reasonable default values.
	 * @param id id Id of Product to create. Can be null, if not must be greater than 0.
	 * @param rev RevisionNumber of this revision. Must not be null and must be greater than 0.
	 */
	public static ProductRevision createProductRevision(Long id, Integer rev) {
		return new ProductRevision(id, rev, "title", EntityCreator._standardDate, null, null, null, null, "someImageUrl");
	}

	public static Date getDefaultDate() {
		return EntityCreator._standardDate;
	}

	/**
	 * Creates a Locale with given id and reasonable default values.
	 * @return
	 */
	public static Locale createLocale(Integer id) {
		return new Locale(id, "german", "deutsch");
	}

	/**
	 * Creates a Product with given id, an associated locale with a default id and as many revisions (starting with revision id) as given numberRevisions indicates.
	 * All created objects will be initialized with reasonable default values.
	 * @param id Id of Product to create. Can be null, if not must be greater than 0.
	 * @param numberRevisions Number of ProductRevisions to be added to Product. Must not be null and must be greater than 0.
	 */
	public static Product createProductWithRevisions(Long id, Integer numberRevisions) {
		return createProductWithRevisions(id, numberRevisions, 1);
	}
}
