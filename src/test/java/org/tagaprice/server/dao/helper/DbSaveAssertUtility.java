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

	private static class ReceiptEntryAsserter implements ResultSetExtractor<ReceiptEntry> {
		private ReceiptEntry _receiptEnt;

		public ReceiptEntryAsserter(ReceiptEntry receiptEntry) {
			_receiptEnt = receiptEntry;
		}

		@Override
		public ReceiptEntry extractData(ResultSet rs) throws SQLException, DataAccessException {
			assertThat(rs.next(), is(true));
			assertThat(rs.getLong("receipt_id"), is(_receiptEnt.getReceiptId()));
			assertThat(rs.getLong("product_id"), is(_receiptEnt.getProductId()));
			assertThat(rs.getInt("product_revision"), is(_receiptEnt.getProductRevisionNumber()));
			assertThat(rs.getInt("product_count"), is(_receiptEnt.getCount()));
			assertThat(rs.getLong("price"), is(_receiptEnt.getPrice()));
			assertThat(rs.next(), is(false));
			return null;
		}
	}


	private static class ReceiptAsserter implements ResultSetExtractor<Receipt> {
		private Receipt _receipt;

		public ReceiptAsserter(Receipt receipt) {
			_receipt = receipt;
		}

		@Override
		public Receipt extractData(ResultSet rs) throws SQLException, DataAccessException {
			assertThat(rs.next(), is(true));
			assertThat(rs.getLong("receipt_id"), is(_receipt.getId()));
			assertThat(rs.getLong("shop_id"), is(_receipt.getShopId()));
			assertThat(rs.getTimestamp("created_at"), is(_receipt.getCreatedAt()));
			assertThat(rs.getLong("creator"), is(_receipt.getCreator().getUid()));
			assertThat(rs.next(), is(false));
			return null;
		}
	}


	private static class ProductAsserter implements ResultSetExtractor<Product> {
		private Product _product = null;

		public ProductAsserter(Product product) {
			_product = product;
		}

		@Override
		public Product extractData(ResultSet rs) throws SQLException, DataAccessException {
			assertThat(rs.next(), is(true));
			assertThat(rs.getLong("ent_id"), is(_product.getId()));
			assertThat(rs.next(), is(false));
			return null;
		}
	}

	private static final Logger _log = Logger.getLogger(DbSaveAssertUtility.class);
	private static JdbcOperations _jdbcOperations;


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
		DbSaveAssertUtility._jdbcOperations.query(getEntityStatment, new ProductAsserter(product)); // TODO use prepared
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
		DbSaveAssertUtility._jdbcOperations.query(getEntityStatment, new ReceiptAsserter(receipt)); // TODO use prepared
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
		DbSaveAssertUtility._jdbcOperations.query(getEntityStatment, new ReceiptEntryAsserter(receiptEntry));

	}


}
