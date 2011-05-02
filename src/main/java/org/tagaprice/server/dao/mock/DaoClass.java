package org.tagaprice.server.dao.mock;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Random;

import org.tagaprice.server.dao.IDaoClass;
import org.tagaprice.shared.entities.ASimpleEntity;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class DaoClass<T extends ASimpleEntity> implements IDaoClass<T> {
	protected Map<String, Deque<T>> m_data;
	private Random random = new Random();
	
	@Override
	public T create(T entity) throws DaoException {
		if (entity.getId() != null) throw new DaoException("Newly created entities must not have an ID!");

		String id = new Long(random.nextLong()).toString();
		String rev = "1";
		
		entity.setId(id);
		entity.setRevision(rev);
		
		Deque<T> deque = new ArrayDeque<T>();
		deque.push(entity);
		
		m_data.put(id, deque);
		
		return entity;
	}

	@Override
	public T get(String id) throws DaoException {
		return get(id, null);
	}

	@Override
	public T get(String id, String revision) throws DaoException {
		T rc = null;
	
		if (!m_data.containsKey(id)) throw new DaoException("Entity not found (id: '"+id+"')!");
	
		if (revision == null) {
			// get the last revision
			rc = m_data.get(id).peek();
		}
		else {
			// find a specific revision
			for(T entity: m_data.get(id)) {
				if (entity.getRevision().equals(revision)) {
					rc = entity;
					break;
				}
			}
		}
		
		return rc;
	}

	@Override
	public T update(T entity) throws DaoException {
		if (entity.getId() == null) throw new DaoException("Entities must have an ID when you update them!");
		if (!m_data.containsKey(entity.getId())) throw new DaoException("Entity not found! (id: '"+entity.getId()+"')");
	
		Deque<T> deque = m_data.get(entity.getId());
		T currentRevision = deque.peek();
		
		if (!currentRevision.getRevision().equals(entity.getRevision())) {
			throw new DaoException("Entity revision don't match ('"+entity.getRevision()+"' and '"+currentRevision.getRevision()+"')");
		}

		String rev = new Integer(m_data.get(entity.getId()).size()+1).toString();
		entity.setRevision(rev);
		
		deque.push(entity);
		
		return entity;
	}

	@Override
	public void delete(T entity) throws DaoException {
		if (m_data.containsKey(entity.getId())) {
			m_data.remove(entity.getId());
		}
		else throw new DaoException("Entity not found: '"+entity.getId()+"'");
		
	}

}
