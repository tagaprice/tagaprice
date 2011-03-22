package org.tagaprice.server.dao.helper;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.tagaprice.client.gwt.shared.entities.Unit;
import org.tagaprice.core.entities.Account;
import org.tagaprice.core.entities.BasicReceipt;
import org.tagaprice.core.entities.BasicShop;
import org.tagaprice.core.entities.Category;
import org.tagaprice.core.entities.Locale;
import org.tagaprice.core.entities.Product;
import org.tagaprice.core.entities.ProductRevision;
import org.tagaprice.core.entities.Receipt;
import org.tagaprice.core.entities.ReceiptEntry;
import org.tagaprice.core.entities.Session;
import org.tagaprice.core.entities.Shop;

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
	private static final Date _standardDate = new Date(1293213600000L); // 2010/12/24, 19:00:00000, DO NOT CHANGE THIS
	// (testdata is set to this date also)
	private static final Double _standardAmount = 100d; // DO NOT CHANGE THIS (testdata is set to this date also)
	private static final Unit _standardUnit = Unit.g;
	// Coordinates of Vienna
	private static double _defaultLatitude = 48.208889;
	private static double _defaultLongitude = 16.3725;

	// free ids as in testdata (DO NOT CHANGE UNLESS THIS IS CHANGED IN TESTDATA)
	public static final long nextFreeShopId = 4L;
	public static final long nextFreeProductId = 4L;
	public static final long nextFreeAccountId = 3L;
	public static final long nextFreeCategoryId = 4L;
	public static final long nextFreeReceiptId = 3L;
	public static final int nextFreeLocaleId = 2;

	/*
	 * 
	 * 
	 * ProductRevisions
	 */
	public static Set<ProductRevision> createProductRevisions(Long id, int numberRevisions, Account creator, Unit unit,
			Category category) {
		Set<ProductRevision> revisions = new HashSet<ProductRevision>();
		for (int rev = 1; rev <= numberRevisions; rev++)
			revisions.add(createProductRevision(id, rev, creator, unit, category));
		return revisions;
	}

	/*
	 * 
	 * 
	 * ProductRevision
	 */

	public static ProductRevision createProductRevision(Long id, Integer rev, Account creator, Unit unit,
			Category category) {
		return createProductRevision(id, rev, "title", creator, unit, category);
	}

	public static ProductRevision createProductRevision(Long id, Integer rev, String title, Account creator, Unit unit,
			Category category) {
		return createProductRevision(id, rev, "title", HibernateSaveEntityCreator._standardDate, creator, unit,
				category);
	}

	public static ProductRevision createProductRevision(Long id, Integer rev, String title, Date createdAt,
			Account creator, Unit unit, Category category) {
		return createProductRevision(id, rev, title, createdAt, creator, unit,
				HibernateSaveEntityCreator._standardAmount, category, "someImageUrl");
	}

	public static ProductRevision createProductRevisionWithNullValues(Long id, Integer rev) {
		return createProductRevision(id, rev, null, null, null, null, null, null, null);
	}


	public static ProductRevision createProductRevision(Long productId, int i,
			String string, Account creator, Category cat, String string2) {
		return createProductRevision(productId, i, string, creator, HibernateSaveEntityCreator._standardUnit, HibernateSaveEntityCreator._standardAmount, cat, string2);
	}

	public static ProductRevision createProductRevision(Long productId, int i, String string, Account creator, Unit defaultUnit, Double defaultAmount, Category cat1, String string2) {
		return createProductRevision(productId, i, string, HibernateSaveEntityCreator._standardDate, creator, defaultUnit, defaultAmount, cat1, string2);
	}

	public static ProductRevision createProductRevision(Long id, Integer rev, String title, Date createdAt,
			Account creator, Unit unit, Double amount, Category category, String imageUrl) {
		return new ProductRevision(id, rev, title, createdAt, creator, unit, amount, category, imageUrl);
	}



	/*
	 * 
	 * 
	 * 
	 * Default value getters
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
	 */

	public static Product createProduct(Long id, Locale locale, ProductRevision revision) {
		Set<ProductRevision> revisions = new HashSet<ProductRevision>();
		revisions.add(revision);
		return createProduct(id, locale, revisions);
	}

	public static Product createProduct(Long id, Locale locale, Set<ProductRevision> revisions) {
		Product productToSave = new Product(id, locale, revisions);
		return productToSave;
	}

	/*
	 * 
	 * 
	 * 
	 * Account
	 */
	public static Account createAccount(Long id) {
		return createAccount(id, "user1@mail.com");
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
	 */
	public static Session createSession() {
		return new Session("sessionId".getBytes());
	}

	/*
	 * 
	 * 
	 * Shops
	 */

	/**
	 * create a default shop with given id
	 */
	public static Shop createShop(Long id) {
		return createShop(id, "defaultShopTitle");
	}

	public static Shop createShop(Long id, String title) {
		return createShop(id, title, HibernateSaveEntityCreator._defaultLatitude,
				HibernateSaveEntityCreator._defaultLongitude);
	}

	public static Shop createShop(Long id, String title, Double latitude, Double longitude) {
		return createShop(id, title, latitude, longitude, new HashSet<ReceiptEntry>());
	}

	public static Shop createShop(Long id, String title, double latitude, double longitude,
			HashSet<ReceiptEntry> receiptEntries) {
		return new Shop(id, title, latitude, longitude, receiptEntries);
	}

	/*
	 * 
	 * 
	 * BasicReceipts
	 */
	public static BasicReceipt createBasicReceipt(Long receiptId, long shopId) {
		return createBasicReceipt(receiptId, createBasicShop(shopId));
	}


	public static BasicReceipt createBasicReceipt(long receiptId, BasicShop shop) {
		return createBasicReceipt(receiptId, shop, HibernateSaveEntityCreator._standardDate);
	}

	private static BasicReceipt createBasicReceipt(long receiptId, BasicShop shop, Date date) {
		return new BasicReceipt(receiptId, shop, date);
	}

	/*
	 * 
	 * Receipts
	 */
	public static Receipt createReceipt(long receiptId, long shopId, Date createdAt, Account creator,
			Set<ReceiptEntry> receiptEntries) {
		return new Receipt(receiptId, HibernateSaveEntityCreator.createBasicShop(shopId), createdAt, creator,
				receiptEntries);
	}

	public static Receipt createReceipt(long receiptId, long shopId, Date createdAt, Account creator) {
		return createReceipt(receiptId, shopId, createdAt, creator, new HashSet<ReceiptEntry>());
	}

	/*
	 * 
	 * 
	 * ReceiptEntries
	 */

	public static ReceiptEntry createReceiptEntry(BasicReceipt basicReceipt, ProductRevision rev, int count,
			long pricePerItemInCent) {
		return new ReceiptEntry(basicReceipt, rev, count, pricePerItemInCent);
	}

	public static ReceiptEntry createReceiptEntry(long receiptId, long shopId, long prodId, int prodRevNr, int count,
			long pricePerItemInCent) {
		return createReceiptEntry(createBasicReceipt(receiptId, shopId),
				HibernateSaveEntityCreator.createProductRevisionWithNullValues(prodId, prodRevNr), count,
				pricePerItemInCent);
	}

	public static BasicShop createBasicShop(long id, String title) {
		return new BasicShop(id, title);
	}

	public static BasicShop createBasicShop(long id) {
		return createBasicShop(id, "defaultBasicShopTitle");
	}

	/*
	 * 
	 * 
	 * Categories
	 * 
	 */
	public static Category createCategory(Long categoryId, Long acccountId) {
		return createCategory(categoryId, HibernateSaveEntityCreator.createAccount(acccountId));
	}
	public static Category createCategory(Long id, Account creator) {
		return new Category(id, "newRootCategory", null, HibernateSaveEntityCreator._standardDate, creator);

	}

	public static Category createCategory(Long id, Category parent, String title, Account creator) {
		return new Category(id, title, parent, HibernateSaveEntityCreator._standardDate, creator);

	}
}
