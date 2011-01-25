package org.tagaprice.core.api;

import java.util.List;

import org.tagaprice.core.entities.BasicShop;
import org.tagaprice.core.entities.Session;
import org.tagaprice.core.entities.Shop;

public interface IShopService {

	/**
	 * Gets the shop with matching id.
	 * @return matching {@link Shop}, or null, if no match was found.
	 * 
	 * @throws ServerException Thrown to indicate that the Server has failed handling the latest request.
	 */
	Shop getById(long id) throws ServerException;

	/**
	 * Saves given shop.
	 * @param shop {@link Shop} to save. Must not be null.
	 * @return saved Shop with the id set.
	 * @throws ServerException Thrown to indicate that the Server has failed handling the latest request. This can be due to not allowed null-values or internal errors.
	 */
	Shop save(Shop shop, Session session) throws ServerException, UserNotLoggedInException;

	/**
	 * Returns all shops which match given title exactly.
	 * @throws ServerException Thrown to indicate that the Server has failed handling the latest request.
	 */
	List<BasicShop> getByTitle(String title) throws ServerException;

	/**
	 * Returns all shops which match given title anywhere in the title. The matching is not case sensitive.
	 * @throws ServerException Thrown to indicate that the Server has failed handling the latest request.
	 */
	List<BasicShop> getByTitleFuzzy(String title) throws ServerException;

	/**
	 * Returns all shops as {@link BasicShop}s.
	 * @throws ServerException Thrown to indicate that the Server has failed handling the latest request.
	 */
	List<BasicShop> getAll() throws ServerException;
}
