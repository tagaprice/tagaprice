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

/**
 * This class is used for testing purposes only.
 * 
 * Entities can freshly created, i.e. new objects will be created with each call, by calling the various create methods.
 * This class is hibernate session save by only providing methods where you have to specify the identifier yourself.
 * So if you create two objects with the same identifier it's your own mistake.
 * 
 * @author "forste"
 *
 */
public class HibernateSaveEntityCreator {
	private static final Date _standardDate = new Date(1295892000216L); //2010/12/24, 19:00:00000

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
		return createProductRevision(id, rev, "title", HibernateSaveEntityCreator._standardDate, creator, unit, category);
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
		return HibernateSaveEntityCreator._standardDate;
	}

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
		return new Account(id, email, password, HibernateSaveEntityCreator._standardDate);
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
		return new Category(4L, "newRootCategory", null, HibernateSaveEntityCreator._standardDate, creator);

	}
}
