package org.tagaprice.server.dao.helper;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.tagaprice.core.entities.Account;
import org.tagaprice.core.entities.BasicReceipt;
import org.tagaprice.core.entities.BasicShop;
import org.tagaprice.core.entities.Category;
import org.tagaprice.core.entities.Locale;
import org.tagaprice.core.entities.Product;
import org.tagaprice.core.entities.ProductRevision;
import org.tagaprice.core.entities.ReceiptEntry;
import org.tagaprice.core.entities.Session;
import org.tagaprice.core.entities.Shop;
import org.tagaprice.core.entities.Unit;

/**
 * This class is used for testing purposes only.
 * 
 * Entities can freshly created, i.e. new objects will be created with each call, by calling the various create methods.
 * This class is "hibernate session save" by only providing methods where you have to specify the identifier yourself.
 * So if you create two objects with the same identifier it's your own fault.
 * 
 * @author "forste"
 *
 */
public class HibernateSaveEntityCreator {
	private static final Date _standardDate = new Date(1293213600000L); //2010/12/24, 19:00:00000, DO NOT CHANGE THIS (testdata is set to this date also)
	private static final Double _standardAmount = 100d; // DO NOT CHANGE THIS (testdata is set to this date also)
	private static final Unit _standardUnit = Unit.g;
	// Coordinates of Vienna
	private static double _defaultLatitude = 48.208889;
	private static double _defaultLongitude = 16.3725;

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

	public static ProductRevision createProductRevision(Long id, Integer rev, Account creator, Unit unit, Category category) {
		return createProductRevision(id, rev, "title", creator, unit, category);
	}

	public static ProductRevision createProductRevision(Long id, Integer rev, String title, Account creator, Unit unit, Category category) {
		return createProductRevision(id, rev, "title", HibernateSaveEntityCreator._standardDate, creator, unit, category);
	}

	public static ProductRevision createProductRevision(Long id, Integer rev, String title, Date createdAt, Account creator, Unit unit, Category category) {
		return createProductRevision(id, rev, title, createdAt, creator, unit, HibernateSaveEntityCreator._standardAmount, category, "someImageUrl");
	}

	public static ProductRevision createProductRevisionWithNullValues(Long id, Integer rev) {
		return createProductRevision(id, rev, null, null, null, null, null, null, null);
	}

	public static ProductRevision createProductRevision(Long id, Integer rev, String title, Date createdAt, Account creator, Unit unit, Double amount, Category category, String imageUrl) {
		return new ProductRevision(id, rev, title, createdAt, creator, unit, amount, category, imageUrl);
	}

	/*
	 * 
	 * 
	 * 
	 * Default value getters
	 * 
	 * 
	 * 
	 */
	public static Date getDefaultDate() {
		return HibernateSaveEntityCreator._standardDate;
	}

	public static Unit getDefaultUnit() {
		return HibernateSaveEntityCreator._standardUnit;
	}

	public static Double getDefaultAmount() {
		return HibernateSaveEntityCreator._standardAmount;
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
		return new Category(id, "newRootCategory", null, HibernateSaveEntityCreator._standardDate, creator);

	}

	/*
	 * 
	 * 
	 * Shops
	 * 
	 * 
	 */

	/**
	 * create a default shop with given id
	 */
	public static Shop createShop(Long id) {
		return createShop(id, "defaultShopTitle");
	}

	public static Shop createShop(Long id, String title) {
		return createShop(id, title, HibernateSaveEntityCreator._defaultLatitude, HibernateSaveEntityCreator._defaultLongitude);
	}

	public static Shop createShop(Long id, String title, Double latitude, Double longitude) {
		return createShop(id, title, latitude, longitude, new HashSet<ReceiptEntry>());
	}

	public static Shop createShop(Long id, String title, double latitude, double longitude, HashSet<ReceiptEntry> receiptEntries) {
		return new Shop(id, title, latitude, longitude, receiptEntries);
	}

	/*
	 * 
	 * 
	 * BasicReceipts
	 * 
	 * 
	 */
	public static BasicReceipt createBasicReceipt(Long receiptId, long shopId) {
		return createBasicReceipt(receiptId, createBasicShop(shopId));
	}


	public static BasicReceipt createBasicReceipt(long receiptId, BasicShop shop) {
		return createBasicReceipt(receiptId, shop, HibernateSaveEntityCreator._standardDate);
	}

	private static BasicReceipt createBasicReceipt(long receiptId,
			BasicShop shop, Date date) {
		return new BasicReceipt(receiptId, shop, date);
	}

	/*
	 * 
	 * 
	 * ReceiptEntries
	 * 
	 * 
	 */

	public static ReceiptEntry createReceiptEntry(
			BasicReceipt basicReceipt, ProductRevision rev1, int count, long pricePerItemInCent) {
		return new ReceiptEntry(basicReceipt, rev1, count, pricePerItemInCent);
	}

	public static BasicShop createBasicShop(long id, String title) {
		return new BasicShop(id, title);
	}

	public static BasicShop createBasicShop(long id) {
		return createBasicShop(id, "defaultBasicShopTitle");
	}
}
