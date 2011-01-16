package org.tagaprice.server.dao.helper;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.tagaprice.core.entities.Account;
import org.tagaprice.core.entities.Category;
import org.tagaprice.core.entities.Locale;
import org.tagaprice.core.entities.Product;
import org.tagaprice.core.entities.ProductRevision;
import org.tagaprice.core.entities.Session;
import org.tagaprice.core.entities.Unit;

public class EntityCreator {
	private static final Date _standardDate = new Date(1293145200000L); //2010/12/24
	private static Account _cachedAccount;


	/*
	 * 
	 * 
	 * ProductRevisions
	 * 
	 * 
	 * 
	 */
	public static  Set<ProductRevision> createProductRevisions(Long id, int numberRevisions, Account creator, Unit unit, Category category) {
		Set<ProductRevision> revisions = new HashSet<ProductRevision>();
		for(int rev = 1; rev<=numberRevisions;rev++)
			revisions.add(createProductRevision(id, rev, creator, unit, category));
		return revisions;
	}

	/*
	 * 
	 * 
	 * ProductRevision
	 * 
	 * 
	 * 
	 */

	public static ProductRevision createProductRevision(Long id, int rev, Account creator, Unit unit, Category category) {
		return createProductRevision(id, rev, "title", creator, unit, category);
	}

	public static ProductRevision createProductRevision(Long id, int rev, String title, Account creator, Unit unit, Category category) {
		return createProductRevision(id, rev, "title", EntityCreator._standardDate, creator, unit, category);
	}

	public static ProductRevision createProductRevision(Long id, int rev, String title, Date createdAt, Account creator, Unit unit, Category category) {
		return new ProductRevision(id, rev, title, createdAt, creator, unit, null, category, "someImageUrl");
	}

	/*
	 * 
	 * 
	 * 
	 * Default Date
	 * 
	 * 
	 * 
	 */
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

	/*
	 * 
	 * 
	 * Product
	 * 
	 * 
	 */

	public static Product createProduct(Long id, Locale locale, Set<ProductRevision> revisions) {
		Product productToSave = new Product(id, locale, revisions);
		return productToSave;
	}

	/*
	 * 
	 * 
	 * 
	 * Account
	 * 
	 * 
	 */
	public static Account createAccount(Long id) {
		return createAccount(id, "standard@email.org");
	}

	public static Account createAccount(Long id, String email) {
		return createAccount(id, email, "12345");
	}

	public static Account createAccount(Long id, String email, String password) {
		return new Account(id, email, password, EntityCreator._standardDate);
	}


	/*
	 * 
	 * 
	 * Session
	 * 
	 * 
	 */
	public static Session createSession() {
		return new Session("sessionId".getBytes());
	}

	/*
	 * Category
	 */
	public static Category createCategory(Long id, Account creator) {
		return new Category(4L, "newRootCategory", null, EntityCreator._standardDate, creator);

	}
}
