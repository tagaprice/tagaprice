package org.tagaprice.server.dao.couchdb.elasticsearch.query;

import org.svenson.JSONProperty;

public interface Query {
	@JSONProperty(ignore=true)
	public String queryType();
}
