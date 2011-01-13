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
	 * <ul>
	 * <li>Creates a Product with given id, an associated locale with given id and as many revisions (starting with revision id) as given numberRevisions indicates.</li>
	 * <li>All created objects will be initialized with reasonable default values.</li>
	 * </ul>
	 * @param id Id of Product to create. Can be null, if not must be greater than 0.
	 * @param numberRevisions Number of ProductRevisions to be added to Product. Must not be null and must be greater than 0.
	 * @param localeId Id of Locale for product. Must not be null and must be greater than 0.
	 */
	public static Product createProductWithRevisions(Long id, Integer numberRevisions, Integer localeId) {
		Set<ProductRevision> revisions = new HashSet<ProductRevision>();

		for(int rev = 1; rev<=numberRevisions;rev++)
			revisions.add(createProductRevision(id, rev));

		return createProductWithRevisions(id, revisions, localeId);
	}

	/**
	 * <ul>
	 * <li>Creates a ProductRevision with given id and revision.</li>
	 * <li>All created objects will be initialized with reasonable default values.</li>
	 * </ul>
	 * @param id id Id of Product to create. Can be null, if not must be greater than 0.
	 * @param rev RevisionNumber of this revision. Must not be null and must be greater than 0.
	 */
	public static ProductRevision createProductRevision(Long id, Integer rev) {
		return createProductRevision(id, rev, "title");
	}

	public static ProductRevision createProductRevision(Long id, Integer rev, String title) {
		return new ProductRevision(id, rev, title, EntityCreator._standardDate, null, null, null, null, "someImageUrl");
	}

	public static Date getDefaultDate() {
		return EntityCreator._standardDate;
	}

	/**
	 * Creates a Locale with given id and reasonable default values.
	 * <ul>
	 * <li>Creates a Locale with given id.</li>
	 * <li>All created objects will be initialized with reasonable default values.</li>
	 * </ul>
	 * @return
	 */
	public static Locale createLocale(Integer id) {
		return new Locale(id, "german", "deutsch");
	}

	/**
	 * <ul>
	 * <li>Creates a Product with given id, an associated locale with a default id and as many revisions (starting with revision id) as given numberRevisions indicates.</li>
	 * <li>All created objects will be initialized with reasonable default values.</li>
	 * </ul>
	 * @param id Id of Product to create. Can be null, if not must be greater than 0.
	 * @param numberRevisions Number of ProductRevisions to be added to Product. Must not be null and must be greater than 0.
	 */
	public static Product createProductWithRevisions(Long id, Integer numberRevisions) {
		return createProductWithRevisions(id, numberRevisions, 1);
	}

	/**
	 * <ul>
	 * <li>Creates a Product with given id, an associated locale with a default id and a given productRevision.</li>
	 * <li>All created objects will be initialized with reasonable default values.</li>
	 * </ul>
	 */
	public static Product createProductWithRevisions(Long id, ProductRevision productRevision) {
		HashSet<ProductRevision> revisions = new HashSet<ProductRevision>();
		revisions.add(productRevision);
		return createProductWithRevisions(id, revisions, 1);
	}

	/**
	 * <ul>
	 * <li>Creates a Product with given id, an associated locale with given id and given revisions.</li>
	 * <li>All created objects will be initialized with reasonable default values.</li>
	 * </ul>
	 */
	public static Product createProductWithRevisions(Long id, Set<ProductRevision> revisions, Integer localeId) {
		Product productToSave = new Product(id, EntityCreator.createLocale(localeId), revisions);
		return productToSave;
	}

	/**
	 * <ul>
	 * <li>Creates a Product with given id, an associated locale with a default id and given revisions.</li>
	 * <li>All created objects will be initialized with reasonable default values.</li>
	 * </ul>
	 */
	public static Product createProductWithRevisions(Long id, Set<ProductRevision> revisions) {
		Product productToSave = new Product(id, EntityCreator.createLocale(1), revisions);
		return productToSave;
	}
}
