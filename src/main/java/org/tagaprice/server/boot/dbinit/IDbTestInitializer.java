package org.tagaprice.server.boot.dbinit;

import java.util.List;

import org.dbunit.dataset.IDataSet;
import org.springframework.core.io.Resource;


/**
 * This interface is used to setup a databes and fill it with testdata
 * It drops and re-creates the configured tables and fills it with data
 * 
 * @author haja
 * 
 */
public interface IDbTestInitializer {

	/**
	 * @param tablesToDrop
	 *            the tables to drop when calling {@link IDbTestInitializer#dropAndRecreate()}
	 */
	public void setTablesToDrop(List<String> tablesToDrop);


	/**
	 * @param scriptsToExecuteResourcePaths
	 *            the scripts to execute when calling {@link IDbTestInitializer#dropAndRecreate()}.
	 *            These scripts must be specified as classpathLocation strings.
	 *            These scripts typically consist of DDL to recreate the dropped tables.
	 */
	public void setScriptsToExecute(List<String> scriptsToExecuteResourcePaths);

	/**
	 * Sets the data to fill the database with using DbUnit.
	 * @param fillDataResourcePath
	 *            The data to fill the database with when calling {@link IDbTestInitializer#fillTables()}.
	 *            The data must be specified as classpathLocation string to DbUnit xml-file.
	 */
	public void setDbUnitData(Resource fillData);

	/**
	 * Drops the tables and executes the scripts specified by the setters
	 */
	public void dropAndRecreate();

	/**
	 * fill the tables with data from DbUnit
	 * @return the {@link IDataSet} that was parsed by DbUnit and inserted into the database.
	 */
	public IDataSet fillTables();


	/**
	 * removes the data added by {@link IDbTestInitializer#fillTables()} from the database.
	 */
	void resetTables();

}