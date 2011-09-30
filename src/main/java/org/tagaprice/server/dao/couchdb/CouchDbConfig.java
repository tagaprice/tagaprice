package org.tagaprice.server.dao.couchdb;

import java.util.Properties;

public class CouchDbConfig extends Properties {
	private static final long serialVersionUID = 1L;

	public CouchDbConfig(Properties defaults) {
		super(defaults);
	}

	public CouchDbConfig() {
	}

	public String getCouchDatabase() {
		return getProperty("couchdb.database", "tagaprice");
	}

	public String getCouchHost() {
		return getProperty("couchdb.host", "localhost");
	}

	public String getCouchPassword() {
		return getProperty("couchdb.password");
	}

	public int getCouchPort() {
		return Integer.parseInt(getProperty("couchdb.port", "5984"));
	}

	public boolean getCouchSsl() {
		// defaulting to false (we don't need ssl on localhost)
		return Boolean.parseBoolean(getProperty("couchdb.ssl", "false"));
	}

	public String getCouchUser() {
		return getProperty("couchdb.user");
	}

	public String getElasticSearchHost() {
		return getProperty("elasticSearch.host", "localhost");
	}

	public String getElasticSearchIndex() {
		return getProperty("elasticSearch.index", "tagaprice");
	}

	public int getElasticSearchPort() {
		return Integer.parseInt(getProperty("elasticSearch.port", "9300"));
	}

	public boolean hasLoginData() {
		return containsKey("user") || containsKey("password");
	}

	public String getStatisticsDb() {
		return getProperty("couchdb.database.statistics", "tagaprice-statistics");
	}

	public String getStatisticsIndex() {
		return getProperty("elasticSearch.index.statistics", "tagaprice-statistics");
	}

	/**
	 * Returns a copy of this object with some replaced values so that the
	 * statistic-database and -index are used instead of the regular ones
	 * @return A configuration object containing the statistics-settings 
	 */
	public CouchDbConfig getStatisticsConfig() {
		CouchDbConfig rc = new CouchDbConfig(this);

		rc.setProperty("couchdb.database", getStatisticsDb());
		rc.setProperty("elasticSearch.index", getStatisticsIndex());

		return rc;
	}
}
