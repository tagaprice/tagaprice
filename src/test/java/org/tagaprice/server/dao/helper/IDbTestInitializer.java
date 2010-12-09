package org.tagaprice.server.dao.helper;


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
	public void setTablesToDrop(Iterable<String> tablesToDrop);


	/**
	 * @param scriptsToExecuteResourcePaths
	 *            the scripts to execute when calling {@link IDbTestInitializer#dropAndRecreate()}.
	 *            These scripts must be specified as classpathLocation strings.
	 *            These scripts typically consist of DDL to recreate the dropped tables.
	 */
	public void setScriptsToExecute(Iterable<String> scriptsToExecuteResourcePaths);

	/**
	 * Drops the tables and executes the scripts specified by the setters
	 */
	public void dropAndRecreate();

	/**
	 * TODO not implemented, should return the dataSet
	 * fill the tables with data from DbUnit
	 */
	public void fillTables();

}