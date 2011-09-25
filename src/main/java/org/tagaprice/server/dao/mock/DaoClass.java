package org.tagaprice.server.dao.mock;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.tagaprice.server.dao.IDaoClass;
import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class DaoClass<T extends Document> implements IDaoClass<T> {
	protected Map<String, Deque<T>> m_data = new HashMap<String, Deque<T>>();
	private Random random = new Random();
	
	@Override
	public T create(T document) throws DaoException {
		if (document.getId() != null) throw new DaoException("Newly created Documents must not have an ID!");

		String id = new Long(random.nextLong()).toString();
		String rev = "1";
		
		document.setId(id);
		document.setRevision(rev);
		
		Deque<T> deque = new ArrayDeque<T>();
		deque.push(document);
		
		m_data.put(id, deque);
		
		return document;
	}

	@Override
	public T get(String id) throws DaoException {
		return get(id, null);
	}

	@Override
	public T get(String id, String revision) throws DaoException {
		T rc = null;
	
		if (!m_data.containsKey(id)) throw new DaoException("Document not found (id: '"+id+"')!");
	
		if (revision == null) {
			// get the last revision
			rc = m_data.get(id).peek();
		}
		else {
			// find a specific revision
			for(T document: m_data.get(id)) {
				if (document.getRevision().equals(revision)) {
					rc = document;
					break;
				}
			}
		}
		
		return rc;
	}
	
	@Override
	public T getOnly(String id, String revision) throws DaoException {
		return get(id, revision);
	}

	@Override
	public T getOnly(String id) throws DaoException {
		return get(id);
	}
	
	@Override
	public Map<String, T> getBulk(String ... ids) throws DaoException {
		Map<String, T> rc = new HashMap<String, T>();

		for (String id: ids) {
			rc.put(id, get(id));
		}

		return null;
	}

	@Override
	public Map<String, T> getBulkOnly(String ... ids) throws DaoException {
		return getBulk(ids);
	}

	@Override
	public T update(T document) throws DaoException {
		if (document.getId() == null) throw new DaoException("Documents must have an ID when you update them!");
		if (!m_data.containsKey(document.getId())) throw new DaoException("Document not found! (id: '"+document.getId()+"')");
	
		Deque<T> deque = m_data.get(document.getId());
		T currentRevision = deque.peek();
		
		if (!currentRevision.getRevision().equals(document.getRevision())) {
			throw new DaoException("Document revisions don't match ('"+document.getRevision()+"' and '"+currentRevision.getRevision()+"')");
		}

		String rev = new Integer(m_data.get(document.getId()).size()+1).toString();
		document.setRevision(rev);
		
		deque.push(document);
		
		return document;
	}

	@Override
	public void delete(Document ... documents) throws DaoException {
		for (Document document: documents) {
			if (m_data.containsKey(document.getId())) {
				m_data.remove(document.getId());
			}
			else throw new DaoException("Document not found: '"+document.getId()+"'");
		}
	}

	protected List<T> _getCurrentRevisions() {
		List<T> rc = new ArrayList<T>();
		for (Deque<T> deque: m_data.values()) {
			rc.add(deque.peek());
		}
		return rc;
	}
}
