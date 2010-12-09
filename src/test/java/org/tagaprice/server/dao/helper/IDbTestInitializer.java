package org.tagaprice.server.dao.helper;



public interface IDbTestInitializer {

	public abstract void dropAndRecreate();

	/** TODO not implemented */
	public abstract void fillTables();

}