package org.tagaprice.server.dao.helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.tagaprice.core.entities.Account;
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
 * It does so by generating a SQL-statement to query the object and then asserts the retrieved resultSet.
 * {@link DbSaveAssertUtility#setSimpleJdbcTemplate(SimpleJdbcTemplate)} must be called before the assert methods can be
 * used.
 * </p>
 * 
 * @author haja
 * 
 */
public class DbSaveAssertUtility {
	//
	// private asserter base class
	//
	private static abstract class EntityAsserter<T> implements ResultSetExtractor<T> {
		protected T _entity;

		public EntityAsserter<T> setEntity(T entity) {
			_entity = entity;
			return this;
		}
	}

	//
	// private prepared statement setters
	//
	private static class SingleIdSetter implements PreparedStatementSetter {
		private long _id;

		public SingleIdSetter setId(long id) {
			_id = id;
			return this;
		}

		@Override
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setLong(1, _id);
		}
	}

	private static class DoubleIdSetter implements PreparedStatementSetter {
		private long _id1;
		private long _id2;

		public DoubleIdSetter setIds(long id1, long id2) {
			_id1 = id1;
			_id2 = id2;
			return this;
		}

		@Override
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setLong(1, _id1);
			ps.setLong(2, _id2);
		}
	}

	//
	// fields
	//

	private static final Logger _log = Logger.getLogger(DbSaveAssertUtility.class);
	private static JdbcOperations _jdbcOperations;

	// asserters cached for performance
	private static final ProductAsserter _productAsserter = new ProductAsserter();
	private static final ProductRevisionAsserter _productRevisionAsserter = new ProductRevisionAsserter();
	private static final ReceiptAsserter _receiptAsserter = new ReceiptAsserter();
	private static final ReceiptEntryAsserter _receiptEntryAsserter = new ReceiptEntryAsserter();
	private static final ShopAsserter _shopAsserter = new ShopAsserter();
	private static final AccountAsserter _accountAsserter = new AccountAsserter();

	// statement setters cached for performance
	private static final SingleIdSetter _singleId = new SingleIdSetter();
	private static final DoubleIdSetter _doubleId = new DoubleIdSetter();


	/**
	 * Set the {@link SimpleJdbcTemplate}. This or {@link DbSaveAssertUtility#setDataSource(DataSource)} must be called
	 * prior to calling any assertEntitySaved method.
	 */
	public static void setSimpleJdbcTemplate(SimpleJdbcTemplate template) {
		DbSaveAssertUtility._jdbcOperations = template.getJdbcOperations();
	}

	/**
	 * Set the {@link DataSource} to generate a {@link SimpleJdbcTemplate}. This or
	 * {@link DbSaveAssertUtility#setSimpleJdbcTemplate(SimpleJdbcTemplate)} must be called prior to calling any
	 * assertEntitySaved method.
	 */
	public static void setDataSource(DataSource dataSource) {
		setSimpleJdbcTemplate(new SimpleJdbcTemplate(dataSource));
	}

	//
	//
	// product
	//
	//

	/**
	 * Asserts that the product id was saved to product and entity tables, no {@link ProductRevision}s are asserted.
	 * 
	 * @see DbSaveAssertUtility#assertEntitySaved(ProductRevision)
	 */
	public static void assertEntitySaved(Product product) {
		DbSaveAssertUtility._log.info("asserting product: " + product.getId());

		String statment = "SELECT p.ent_id AS p_ent_id, " + "ent.ent_id AS ent_ent_id, "
		+ "ent.locale_id AS ent_locale_id "
		+ "FROM product AS p JOIN entity AS ent ON p.ent_id = ent.ent_id WHERE p.ent_id = ?";
		DbSaveAssertUtility._jdbcOperations.query(statment, DbSaveAssertUtility._singleId.setId(product.getId()),
				DbSaveAssertUtility._productAsserter.setEntity(product));
	}

	private static class ProductAsserter extends EntityAsserter<Product> {
		@Override
		public Product extractData(ResultSet rs) throws SQLException, DataAccessException {
			assertThat("resultSet empty", rs.next(), is(true));
			// assert product table
			assertThat(rs.getLong("p_ent_id"), is(_entity.getId()));
			// assert entity table
			assertThat(rs.getLong("ent_ent_id"), is(_entity.getId()));
			assertThat(rs.getInt("ent_locale_id"), is(_entity.getLocale().getId()));
			assertThat("more than one row in resultSet", rs.next(), is(false));
			return null;
		}
	}

	//
	//
	// productRevision
	//
	//

	/**
	 * TODO fix this method
	 * Asserts that the productRevision was saved to productRevision and entityRevision tables.
	 * 
	 * @see DbSaveAssertUtility#assertEntitySaved(Product)
	 */
	public static void assertEntitySaved(ProductRevision productRevision) {
		DbSaveAssertUtility._log.info("asserting productRevision: " + productRevision);

		String statment = "SELECT rev.ent_id AS r_ent_id, " + "rev.rev AS r_rev, " + "rev.unit AS r_unit, "
		+ "rev.amount AS r_amount, " + "rev.category_id AS r_category_id, " + "rev.imageUrl AS r_imageUrl, "
		+ "entRev.ent_id AS er_ent_id, " + "entRev.rev AS er_rev, " + "entRev.title AS er_title, "
		+ "entRev.created_at AS er_created_at, " + "entRev.creator AS er_creator "
		+ "FROM productRevision AS rev JOIN entityRevision AS entRev "
		+ "ON rev.ent_id = entRev.ent_id AND rev.rev = entRev.rev " + "WHERE rev.ent_id = ? AND rev.rev = ?";
		DbSaveAssertUtility._jdbcOperations.query(statment,
				DbSaveAssertUtility._doubleId.setIds(productRevision.getId(), productRevision.getRevisionNumber()),
				DbSaveAssertUtility._productRevisionAsserter.setEntity(productRevision));
	}

	private static class ProductRevisionAsserter extends EntityAsserter<ProductRevision> {
		@Override
		public ProductRevision extractData(ResultSet rs) throws SQLException, DataAccessException {
			assertThat("resultSet empty", rs.next(), is(true));

			// assert productRevision table
			assertThat(rs.getLong("r_ent_id"), is(_entity.getId()));
			assertThat(rs.getInt("r_rev"), is(_entity.getRevisionNumber()));
			assertThat(rs.getString("r_unit"), is(_entity.getUnit().name())); // TODO check if this works
			assertThat(rs.getDouble("r_amount"), is(_entity.getAmount()));
			if (_entity.getCategory() != null)
				assertThat(rs.getLong("r_category_id"), is(_entity.getCategory().getId())); // TODO assert null in db if
			// category is null
			assertThat(rs.getString("r_imageUrl"), is(_entity.getImageURL()));
			// assert entityRevision table
			assertThat(rs.getLong("er_ent_id"), is(_entity.getId()));
			assertThat(rs.getInt("er_rev"), is(_entity.getRevisionNumber()));
			assertThat(rs.getString("er_title"), is(_entity.getTitle()));
			assertThat(rs.getTimestamp("er_created_at"), is(_entity.getCreatedAt()));
			assertThat(rs.getLong("er_creator"), is(_entity.getCreator().getUid()));

			assertThat("more than one row in resultSet", rs.next(), is(false));
			return null;
		}
	}

	//
	//
	// receipt
	//
	//

	/**
	 * Asserts that the receipt was saved, no {@link ReceiptEntry}s are asserted.
	 * 
	 * @see DbSaveAssertUtility#assertEntitySaved(ReceiptEntry)
	 */
	public static void assertEntitySaved(Receipt receipt) {
		DbSaveAssertUtility._log.info("asserting receipt: " + receipt);

		String statement = "SELECT receipt_id, shop_id, created_at, creator FROM receipt WHERE receipt_id = ?";

		DbSaveAssertUtility._jdbcOperations.query(statement, DbSaveAssertUtility._singleId.setId(receipt.getId()),
				DbSaveAssertUtility._receiptAsserter.setEntity(receipt));
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

	//
	//
	// receiptEntry
	//
	//

	/**
	 * Asserts that the receiptEntry was saved.
	 */
	public static void assertEntitySaved(ReceiptEntry receiptEntry) {
		DbSaveAssertUtility._log.info("asserting receiptEntry: " + receiptEntry);

		String statment = "SELECT receipt_id, product_id, product_revision, product_count, price FROM receiptEntry WHERE receipt_id = ? AND product_id = ?";

		DbSaveAssertUtility._jdbcOperations.query(statment,
				DbSaveAssertUtility._doubleId.setIds(receiptEntry.getReceiptId(), receiptEntry.getProductId()),
				DbSaveAssertUtility._receiptEntryAsserter.setEntity(receiptEntry));
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

	//
	//
	// shop
	//
	//

	/**
	 * Asserts that the shop was saved.
	 */
	public static void assertEntitySaved(Shop shop) {
		DbSaveAssertUtility._log.info("asserting shop: " + shop);

		String statement = "SELECT shop_id, title, latitude, longitude FROM shop WHERE shop_id = ?";

		DbSaveAssertUtility._jdbcOperations.query(statement, DbSaveAssertUtility._singleId.setId(shop.getId()),
				DbSaveAssertUtility._shopAsserter.setEntity(shop));
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

	//
	//
	// account
	//
	//

	/**
	 * Asserts that the account was saved to the database.
	 */
	public static void assertEntitySaved(Account account) {
		DbSaveAssertUtility._log.info("asserting account: " + account);

		String statement = "SELECT uid, email, last_login, password FROM account WHERE uid = ?";

		DbSaveAssertUtility._jdbcOperations.query(statement, DbSaveAssertUtility._singleId.setId(account.getUid()),
				DbSaveAssertUtility._accountAsserter.setEntity(account));
	}

	private static class AccountAsserter extends EntityAsserter<Account> {
		@Override
		public Account extractData(ResultSet rs) throws SQLException, DataAccessException {
			assertThat("resultSet empty", rs.next(), is(true));
			assertThat(rs.getLong("uid"), is(_entity.getUid()));
			assertThat(rs.getString("email"), is(_entity.getEmail()));
			assertThat(rs.getTimestamp("last_login"), is(_entity.getLastLogin()));
			assertThat(rs.getString("password"), is(not(nullValue())));
			assertThat("more than one row in resultSet", rs.next(), is(false));
			return null;
		}
	}


}
