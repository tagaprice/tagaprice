package org.tagaprice.server.service.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.tagaprice.core.entities.Account;
import org.tagaprice.core.entities.Category;
import org.tagaprice.core.entities.Locale;
import org.tagaprice.core.entities.Product;
import org.tagaprice.core.entities.ProductRevision;
import org.tagaprice.core.entities.ReceiptEntry;
import org.tagaprice.core.entities.Session;
import org.tagaprice.core.entities.Shop;
import org.tagaprice.server.dao.helper.HibernateSaveEntityCreator;

/**
 * This class is used for testing purposes only.
 * 
 * Entities can freshly created, i.e. new objects will be created with each call, by calling the various create methods.
 * This class is not hibernate session save, so two calls may create the same object with the same identifier, which leads - running it in a hibernate session - to a hibernate exceptions.
 * Use HibernateSaveEntityCreator instead to have hibernate save objects.
 * @author "forste"
 *
 */
public class EntityCreator {
	private static final Date _standardDate = HibernateSaveEntityCreator.getDefaultDate();
	// Coordinates of Vienna
	private static double _defaultLatitude = 48.208889;
	private static double _defaultLongitude = 16.3725;
	/*
	 * 
	 * 
	 * Product with revisions
	 * 
	 * 
	 */
	public static Product createProductWithRevisions(Long id, Integer numberRevisions) {
		return createProductWithRevisions(id, numberRevisions, 1, null);
	}

	public static Product createProductWithRevisions(Long id, Integer numberRevisions, Integer localeId, Category category) {
		Set<ProductRevision> revisions = new HashSet<ProductRevision>();

		for(int rev = 1; rev<=numberRevisions;rev++)
			revisions.add(createProductRevision(id, rev, category));

		return createProductWithRevisions(id, revisions, localeId);
	}

	public static Product createProductWithRevisions(Long id, ProductRevision productRevision1, ProductRevision productRevision2) {
		HashSet<ProductRevision> revisions = new HashSet<ProductRevision>();
		revisions.add(productRevision1);
		revisions.add(productRevision2);
		return createProductWithRevisions(id, revisions, 1);
	}

	public static Product createProductWithRevisions(Long id, ProductRevision productRevision) {
		HashSet<ProductRevision> revisions = new HashSet<ProductRevision>();
		revisions.add(productRevision);
		return createProductWithRevisions(id, revisions, 1);
	}

	public static Product createProductWithRevisions(Long id, Set<ProductRevision> revisions, Integer localeId) {
		return HibernateSaveEntityCreator.createProduct(id, EntityCreator.createLocale(localeId), revisions);
	}

	public static Product createProductWithRevisions(Long id, Set<ProductRevision> revisions) {
		return createProductWithRevisions(id, revisions, 1);
	}

	/*
	 * 
	 * 
	 * 
	 * ProductRevision
	 * 
	 * 
	 * 
	 * 
	 * 
	 */


	public static ProductRevision createProductRevision(Long id, int rev) {
		return createProductRevision(id, rev, "title");
	}

	public static ProductRevision createProductRevision(Long id, int rev, Category category) {
		return createProductRevision(id, rev, "title", EntityCreator._standardDate, category);
	}


	public static ProductRevision createProductRevision(Long id, Integer rev, String title) {
		return createProductRevision(id, rev, title, EntityCreator._standardDate);
	}

	public static ProductRevision createProductRevision(Long id, int rev, String title, Date createdAt) {
		return createProductRevision(id, rev, title, createdAt, EntityCreator.createAccount());
	}

	public static ProductRevision createProductRevision(Long id, int rev, String title, Date createdAt, Category category) {
		return createProductRevision(id, rev, title, createdAt, EntityCreator.createAccount(), category);
	}

	public static ProductRevision createProductRevision(Long id, int rev, String title, Date createdAt, Account creator) {
		return createProductRevision(id, rev, title, createdAt, creator, null);
	}

	public static ProductRevision createProductRevision(Long id, int rev, String title, Date createdAt, Account creator, Category category) {
		return HibernateSaveEntityCreator.createProductRevision(id, rev, title, createdAt, creator, null, category);
	}

	/*
	 * 
	 * Date
	 * 
	 * 
	 */
	public static Date getDefaultDate() {
		return EntityCreator._standardDate;
	}

	/*
	 * 
	 * 
	 * Account
	 * 
	 * 
	 */

	public static Account createAccount() {
		return createAccount("user@mail.com");
	}

	public static Account createAccount(String email) {
		return HibernateSaveEntityCreator.createAccount(1L, email, "12345");
	}

	/*
	 * 
	 * Locale
	 * 
	 * 
	 */
	public static Locale createLocale(Integer id) {
		return HibernateSaveEntityCreator.createLocale(id);
	}

	/*
	 * 
	 * 
	 * Sessions
	 * 
	 * 
	 */
	public static Session createSession() {
		return HibernateSaveEntityCreator.createSession();
	}

	/*
	 * 
	 * 
	 * Shops
	 * 
	 * 
	 */
	public static Shop createShop(Long id) {
		return new Shop(id, "defaultShopTitle", EntityCreator._defaultLatitude , EntityCreator._defaultLongitude , new HashSet<ReceiptEntry>());
	}
}