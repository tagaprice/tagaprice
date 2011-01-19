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



public class DbSaveAssertUtility {

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

		String getEntityStatment = "SELECT ent_id FROM entity WHERE ent_id = " + product.getId();

		DbSaveAssertUtility._jdbcOperations.query(getEntityStatment, new ProductAsserter(product)); // TODO use prepared statement here
	}


	/**
	 * TODO implement this method
	 */
	public static void assertEntitySaved(ProductRevision productRevision) {
	}


}
