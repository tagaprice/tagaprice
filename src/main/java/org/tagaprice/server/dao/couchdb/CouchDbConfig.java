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
}
