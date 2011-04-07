package org.tagaprice.shared.entities.shopmanagement;

import java.util.ArrayList;

import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.IEntity;

public interface IShop extends IEntity {


	/**
	 * Set The parent {@link IShop}
	 * @param parent the parent {@link IShop}
	 */
	public void setParent(IShop parent);

	/**
	 * Returns the {@link IShop} parent
	 * @return the parent. If null no parent.
	 */
	public IShop getParent();

	/**
	 * Set {@link IShop} kids. All included {@link IShop} will be deleted and overwritten.
	 * @param kids all {@link IShop} kids
	 */
	public void setChilds(ArrayList<IShop> childs);

	/**
	 * Insert one {@link IShop} as kid
	 * @param kid {@link IShop} kid
	 */
	public void addChild(IShop child);

	/**
	 * Returns all includes {@link IShop} kids
	 * @return includes {@link IShop} kids
	 */
	public ArrayList<IShop> getChilds();


	/**
	 * Set an {@link Address} to the {@link IShop}
	 * @param address {@link IShop} address
	 */
	public void setAddress(Address address);

	/**
	 * @return the address of the {@link IShop}
	 */
	public Address getAddress();


}
