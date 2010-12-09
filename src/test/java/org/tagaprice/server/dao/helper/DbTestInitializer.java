package org.tagaprice.server.dao.helper;

import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
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

	private static final Logger _log = Logger.getLogger(DbTestInitializer.class);

	private SimpleJdbcTemplate _jdbcTemplate;

	private Iterable<String> _tablesToDrop;
	private List<Resource> _scriptsToExecute;


	/**
	 * Configure the {@link DbTestInitializer} with a {@link DataSource}, tables to drop and scripts to execute
	 * after dropping the tables.
	 * These scripts typically consist of DDL to recreate the dropped tables.
	 * 
	 */
	public DbTestInitializer(DataSource dataSource, Iterable<String> tablesToDrop,
			Iterable<String> scriptsToExecuteResourcePaths) {
		_tablesToDrop = tablesToDrop;

		_scriptsToExecute = new LinkedList<Resource>();
		for (String resourceString : scriptsToExecuteResourcePaths) {
			_scriptsToExecute.add(new ClassPathResource(resourceString));
		}

		_jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tagaprice.server.dao.helper.IDbTestInitializer#dropAndRecreate()
	 */
	@Override
	public void dropAndRecreate() {
		DbTestInitializer._log.debug("dropAndRecreate");

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
	public void fillTables() {
		// TODO fill tables with dbUnit
	}
}
