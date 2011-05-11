package org.tagaprice.server.dao.couchdb.elasticsearch.filter;

import org.tagaprice.server.dao.couchdb.elasticsearch.query.Term;

public class TermFilter implements Filter {
	Term m_term;

	public TermFilter(Term term) {
		m_term = term;
	}

	public Term getTerm() {
		return m_term;
	}
}
