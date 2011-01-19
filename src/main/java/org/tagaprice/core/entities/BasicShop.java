package org.tagaprice.core.entities;

import java.io.Serializable;

import javax.persistence.*;

/**
 * <p>
 * Don't attempt to save this entity! Use {@link Shop} instead!
 * </p>
 * <p>
 * This class only provides some basic information about a shop, i.e. it doesn't provide any references to {@link Product}s.
 * </p>
 * 
 * @author haja
 * 
 */
@Entity
@Table(name = "shop")
@SuppressWarnings("unused")
public class BasicShop implements Serializable {

	private static final long serialVersionUID = 1L;

	private long _shopId;
	private String _title;

	protected BasicShop() {
	}

	public BasicShop(long shopId, String shopTitle) {
		_shopId = shopId;
		_title = shopTitle;
	}

	@Id
	@Column(name = "shop_id")
	public Long getShopId() {
		return _shopId;
	}

	private void setShopId(long shopId) {
		_shopId = shopId;
	}


	public String getTitle() {
		return _title;
	}

	private void setTitle(String title) {
		_title = title;
	}


	@Override
	public String toString() {
		return "BasicShop [_shopId=" + _shopId + ", _title=" + _title + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (_shopId ^ (_shopId >>> 32));
		result = prime * result + ((_title == null) ? 0 : _title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicShop other = (BasicShop) obj;
		if (_shopId != other._shopId)
			return false;
		if (_title == null) {
			if (other._title != null)
				return false;
		} else if (!_title.equals(other._title))
			return false;
		return true;
	}
}
