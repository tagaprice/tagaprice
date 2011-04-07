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
	 * @return the parent. If null no parrent.
	 */
	public IShop getParent();

	/**
	 * Set {@link IShop} kids. All included {@link IShop} will be deleted and overwritten.
	 * @param kids all {@link IShop} kids
	 */
	public void setKids(ArrayList<IShop> kids);

	/**
	 * Insert one {@link IShop} as kid
	 * @param kid {@link IShop} kid
	 */
	public void addKid(IShop kid);

	/**
	 * Returns all includes {@link IShop} kids
	 * @return includes {@link IShop} kids
	 */
	public ArrayList<IShop> getKids();

	/**
	 * Set some {@link ISubsidiary} to the Shop. The setShop() method is called for every {@link ISubsidiary} and this
	 * {@link IShop} will be added. All include {@link ISubsidiary} will be deleted.
	 * 
	 * @param addresses
	 *            that will be set to the {@link IShop}
	 */
	public void setSubsidiary(ArrayList<ISubsidiary> subsidiaries);



	/**
	 * Add one new {@link ISubsidiary} to this {@link IShop}. The setShop() method is called for every {@link ISubsidiary} and
	 * this {@link IShop} will be added.
	 * 
	 * @param subsidiary
	 *            that will be added to the {@link IShop}
	 */
	public void addSubsidiary(ISubsidiary subsidiary);

	/**
	 * Returns all {@link ISubsidiary}
	 * 
	 * @return all {@link ISubsidiary}
	 */
	public ArrayList<ISubsidiary> getSubsidiaries();

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
