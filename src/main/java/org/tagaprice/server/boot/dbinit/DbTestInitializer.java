package org.tagaprice.server.boot.dbinit;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.jdbc.SimpleJdbcTestUtils;

/**
 * This class sets up HsqlDB for testing.
 * It drops and re-creates the configured tables TODO and fills it with data from DbUnit
 * 
 * @author haja
 * 
 */
public class DbTestInitializer implements IDbTestInitializer {

	private static final Logger _log = LoggerFactory.getLogger(DbTestInitializer.class);

	private SimpleJdbcTemplate _jdbcTemplate;
	private IDatabaseConnection _connection;

	private Iterable<String> _tablesToDrop;
	private List<Resource> _scriptsToExecute;

	private IDataSet _dbUnitDataSet;



	/**
	 * Configure the {@link DbTestInitializer} with a {@link DataSource}.
	 */
	public DbTestInitializer(DataSource dataSource) {
		_jdbcTemplate = new SimpleJdbcTemplate(dataSource);
		try {
			_connection = new DatabaseConnection(dataSource.getConnection());
			DatabaseConfig config = _connection.getConfig();
			config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
		} catch (DatabaseUnitException e) {
			DbTestInitializer._log.error(e);
		} catch (SQLException e) {
			DbTestInitializer._log.error(e);
		}
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tagaprice.server.dao.helper.IDbTestInitializer#dropAndRecreate()
	 */
	@Override
	public void dropAndRecreate() {
		DbTestInitializer._log.info("dropAndRecreate");

		JdbcOperations jdbcOperations = _jdbcTemplate.getJdbcOperations();
		String dropTableSqlStatement = "DROP TABLE IF EXISTS %s CASCADE";
		for (String table : _tablesToDrop) {
			DbTestInitializer._log.debug("Dropping table: " + table);

			// TODO use prepared statment here
			jdbcOperations.execute(String.format(dropTableSqlStatement, table));
		}

		for (Resource resource : _scriptsToExecute) {
			DbTestInitializer._log.debug("Executing script: " + resource.getFilename());

			SimpleJdbcTestUtils.executeSqlScript(_jdbcTemplate, resource, false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tagaprice.server.dao.helper.IDbTestInitializer#fillTables()
	 */
	@Override
	public IDataSet fillTables() {
		DbTestInitializer._log.info("fillTables");
		try {
			DatabaseOperation.CLEAN_INSERT.execute(_connection, _dbUnitDataSet);
		} catch (DatabaseUnitException e) {
			DbTestInitializer._log.error(e);
		} catch (SQLException e) {
			DbTestInitializer._log.error(e);
		}
		return _dbUnitDataSet;
	}

	@Override
	public void resetTables() {
		DbTestInitializer._log.info("resetTables");
		try {
			DatabaseOperation.DELETE.execute(_connection, _dbUnitDataSet);
		} catch (DatabaseUnitException e) {
			DbTestInitializer._log.error(e);
		} catch (SQLException e) {
			DbTestInitializer._log.error(e);
		}
	}


	@Override
	public void setTablesToDrop(List<String> tablesToDrop) {
		_tablesToDrop = tablesToDrop;
	}


	@Override
	public void setScriptsToExecute(List<String> scriptsToExecuteResourcePaths) {
		LinkedList<Resource> scriptsToExecute = new LinkedList<Resource>();

		for (String resourceString : scriptsToExecuteResourcePaths) {
			scriptsToExecute.add(new ClassPathResource(resourceString));
		}
		_scriptsToExecute = scriptsToExecute;
	}


	@Override
	public void setDbUnitData(Resource fillData) {
		try {
			_dbUnitDataSet = new XmlDataSet(fillData.getInputStream());
		} catch (DataSetException e) {
			DbTestInitializer._log.error(e);
		} catch (IOException e) {
			DbTestInitializer._log.error(e);
		}
	}
}
