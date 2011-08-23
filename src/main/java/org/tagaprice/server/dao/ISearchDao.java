package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.exceptions.dao.DaoException;

public interface ISearchDao {
	public List<Document> search(String query) throws DaoException;
}
