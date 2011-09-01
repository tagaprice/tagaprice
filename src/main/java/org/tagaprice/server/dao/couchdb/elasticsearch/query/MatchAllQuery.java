package org.tagaprice.server.dao.couchdb.elasticsearch.query;

public class MatchAllQuery implements Query {

	@Override
	public String queryType() {
		return "match_all";
	}
}
