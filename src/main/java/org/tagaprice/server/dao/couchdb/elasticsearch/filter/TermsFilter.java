package org.tagaprice.server.dao.couchdb.elasticsearch.filter;

import org.tagaprice.server.dao.couchdb.elasticsearch.query.Terms;

public class TermsFilter implements Filter {
	Terms m_terms;

	public TermsFilter() {
	}
	
	public TermsFilter terms(Terms terms) {
		m_terms = terms;
		return this;
	}

	public Terms getTerms() {
		return m_terms;
	}

}
