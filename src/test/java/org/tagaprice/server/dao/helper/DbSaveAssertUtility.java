package org.tagaprice.server.dao.helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.tagaprice.core.entities.Product;
import org.tagaprice.core.entities.ProductRevision;
import org.tagaprice.core.entities.Receipt;
import org.tagaprice.core.entities.ReceiptEntry;
import org.tagaprice.core.entities.Shop;


/**
 * <p>
 * This class asserts that a given entity is saved in the database.
 * </p>
 * <p>
 * It does so by generating a SQL-statemtent to query the object and then asserts the retrieved resultSet.
 * {@link DbSaveAssertUtility#setSimpleJdbcTemplate(SimpleJdbcTemplate)} must be called before the assert methods can be
 * used.
 * </p>
 * 
 * @author haja
 * 
 */
public class DbSaveAssertUtility {

	// private asserter classes

	private static abstract class EntityAsserter<T> implements ResultSetExtractor<T> {
		protected T _entity;

		public EntityAsserter<T> setEntity(T entity) {
			_entity = entity;
			return this;
		}
	}

	private static class ShopAsserter extends EntityAsserter<Shop> {
		@Override
		public Shop extractData(ResultSet rs) throws SQLException, DataAccessException {
			assertThat("resultSet empty", rs.next(), is(true));
			assertThat(rs.getLong("shop_id"), is(_entity.getId()));
			assertThat(rs.getString("title"), is(_entity.getTitle()));
			assertThat(rs.getDouble("latitude"), is(_entity.getLatitude()));
			assertThat(rs.getDouble("longitude"), is(_entity.getLongitude()));
			assertThat("more than one row in resultSet", rs.next(), is(false));
			return null;
		}
	}

	private static class ReceiptEntryAsserter extends EntityAsserter<ReceiptEntry> {
		@Override
		public ReceiptEntry extractData(ResultSet rs) throws SQLException, DataAccessException {
			assertThat("resultSet empty", rs.next(), is(true));
			assertThat(rs.getLong("receipt_id"), is(_entity.getReceiptId()));
			assertThat(rs.getLong("product_id"), is(_entity.getProductId()));
			assertThat(rs.getInt("product_revision"), is(_entity.getProductRevisionNumber()));
			assertThat(rs.getInt("product_count"), is(_entity.getCount()));
			assertThat(rs.getLong("price"), is(_entity.getPricePerItem()));
			assertThat("more than one row in resultSet", rs.next(), is(false));
			return null;
		}
	}

	private static class ReceiptAsserter extends EntityAsserter<Receipt> {
		@Override
		public Receipt extractData(ResultSet rs) throws SQLException, DataAccessException {
			assertThat("resultSet empty", rs.next(), is(true));
			assertThat(rs.getLong("receipt_id"), is(_entity.getId()));
			assertThat(rs.getLong("shop_id"), is(_entity.getShop().getShopId()));
			assertThat(rs.getTimestamp("created_at"), is(_entity.getCreatedAt()));
			assertThat(rs.getLong("creator"), is(_entity.getCreator().getUid()));
			assertThat("more than one row in resultSet", rs.next(), is(false));
			return null;
		}
	}

	private static class ProductAsserter extends EntityAsserter<Product> {
		@Override
		public Product extractData(ResultSet rs) throws SQLException, DataAccessException {
			assertThat("resultSet empty", rs.next(), is(true));
			assertThat(rs.getLong("ent_id"), is(_entity.getId()));
			assertThat("more than one row in resultSet", rs.next(), is(false));
			return null;
		}
	}

	//
	// fields
	//

	private static final Logger _log = Logger.getLogger(DbSaveAssertUtility.class);
	private static JdbcOperations _jdbcOperations;

	// asserters cached for performance
	private static final ProductAsserter _productAsserter = new ProductAsserter();
	private static final ReceiptAsserter _receiptAsserter = new ReceiptAsserter();
	private static final ReceiptEntryAsserter _receiptEntryAsserter = new ReceiptEntryAsserter();
	private static final ShopAsserter _shopAsserter = new ShopAsserter();


	/**
	 * Set the {@link SimpleJdbcTemplate}.
	 */
	public static void setSimpleJdbcTemplate(SimpleJdbcTemplate template) {
		DbSaveAssertUtility._jdbcOperations = template.getJdbcOperations();
	}


	/**
	 * asserts that the product id was saved, no {@link ProductRevision}s are asserted.
	 * 
	 * @see DbSaveAssertUtility#assertEntitySaved(ProductRevision)
	 */
	public static void assertEntitySaved(Product product) {
		DbSaveAssertUtility._log.info("asserting product: " + product.getId());

		String getEntityStatment = "SELECT ent_id FROM product WHERE ent_id = " + product.getId();
		DbSaveAssertUtility._jdbcOperations.query(getEntityStatment, DbSaveAssertUtility._productAsserter.setEntity(product)); // TODO use prepared
		// statement here
	}


	/**
	 * TODO implement this method
	 */
	public static void assertEntitySaved(ProductRevision productRevision) {
	}

	/**
	 * Asserts that the receipt was saved, no {@link ReceiptEntry}s are asserted.
	 * 
	 * @see DbSaveAssertUtility#assertEntitySaved(ReceiptEntry)
	 */
	public static void assertEntitySaved(Receipt receipt) {
		DbSaveAssertUtility._log.info("asserting receipt: " + receipt);

		String getEntityStatment = "SELECT receipt_id, shop_id, created_at, creator FROM receipt WHERE receipt_id = "
			+ receipt.getId();
		DbSaveAssertUtility._jdbcOperations.query(getEntityStatment, DbSaveAssertUtility._receiptAsserter.setEntity(receipt)); // TODO use prepared
		// statement here
	}

	/**
	 * Asserts that the receiptEntry was saved.
	 */
	public static void assertEntitySaved(ReceiptEntry receiptEntry) {
		DbSaveAssertUtility._log.info("asserting receiptEntry: " + receiptEntry);

		String getEntityStatment = "SELECT receipt_id, product_id, product_revision, product_count, price FROM receiptEntry WHERE"
			+ " receipt_id = " + receiptEntry.getReceiptId() + " AND product_id = " + receiptEntry.getProductId();

		// TODO use prepared statement here
		DbSaveAssertUtility._jdbcOperations.query(getEntityStatment, DbSaveAssertUtility._receiptEntryAsserter.setEntity(receiptEntry));

	}

	/**
	 * Asserts that the shop was saved.
	 */
	public static void assertEntitySaved(Shop shop) {
		DbSaveAssertUtility._log.info("asserting shop: " + shop);

		String getEntityStatment = "SELECT shop_id, title, latitude, longitude FROM shop WHERE shop_id = "
			+ shop.getId();
		DbSaveAssertUtility._jdbcOperations.query(getEntityStatment, DbSaveAssertUtility._shopAsserter.setEntity(shop)); // TODO use prepared
		// statement here
	}


}
