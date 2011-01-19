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
			rs.next();
			assertThat(rs.getLong("ent_id"), is(_product.getId()));
			return new Product(_product.getId(), null, null);
		}
	}



	private static final Logger _log = Logger.getLogger(DbSaveAssertUtility.class);
	private static JdbcOperations _jdbcOperations;
	private static SimpleJdbcTemplate _template;


	/**
	 * Set the {@link SimpleJdbcTemplate}.
	 */
	public static void setSimpleJdbcTemplate(SimpleJdbcTemplate template) {
		DbSaveAssertUtility._jdbcOperations = template.getJdbcOperations();
		DbSaveAssertUtility._template = template;
		//		try {
		//			DatabaseConfig config = new DatabaseConnection(dataSource.getConnection()).getConfig();
		//			config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
		//		} catch (DatabaseUnitException e) {
		//			DbSaveAssertUtility._log.error(e);
		//		} catch (SQLException e) {
		//			DbSaveAssertUtility._log.error(e);
		//		}
	}


	/**
	 * asserts that the product id was saved, no {@link ProductRevision}s are asserted.
	 * 
	 * @see DbSaveAssertUtility#assertEntitySaved(ProductRevision)
	 */
	public static void assertEntitySaved(Product product) {
		String getEntityStatment = "SELECT ent_id FROM entity WHERE ent_id = ?";
		assertThat(DbSaveAssertUtility._template.queryForLong(getEntityStatment, product.getId()), is(4L));

		//		DbSaveAssertUtility._jdbcOperations.query(getEntityStatment, new ProductAsserter(product)); // TODO use prepared statement here
	}


	public static void assertEntitySaved(ProductRevision productRevision) {

		// JdbcOperations jdbcOperations = DbSaveAssertUtility._jdbcTemplate.getJdbcOperations();
		// String getEntityStatment = "SELECT ent_id FROM product WHERE ent_id = %s"; // TODO adopt this
		//
		// // TODO use prepared statement here
		// jdbcOperations.query(String.format(getEntityStatment, productRevision.getId().toString()),
		// DbSaveAssertUtility._productExtractor);

	}


}
