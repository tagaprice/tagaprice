/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License.
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
 */

/**
 * Project: TagAPrice
 * Filename: BoundingBox.java
 * Date: 02.06.2010
 */
package org.tagaprice.shared.entities;

import org.tagaprice.shared.entities.Address.LatLon;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * This class implements a rectangular BoundingBox.
 */
public class BoundingBox implements IsSerializable {
	/// default serial version id
	private static final long serialVersionUID = 1L;

	private double _northLat, _southLat;
	private double _westLon, _eastLon;

	/**
	 * This constructor is used by the serialization algorithm
	 */
	public BoundingBox(){}

	/**
	 * TODO Describe LatLong in the box
	 * Constructor.
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public BoundingBox(double southLat, double westLon, double northLat, double eastLon)
	{
		//Make sure the the coordinates of (x1, y1) are lower than for the point (x2, y2).
		if (southLat <= northLat)
		{
			this._southLat = southLat;
			this._northLat = northLat;
		}
		else
		{
			this._southLat = northLat;
			this._northLat = southLat;
		}

		if (westLon <= eastLon)
		{
			this._westLon = westLon;
			this._eastLon = eastLon;
		}
		else
		{
			this._westLon = eastLon;
			this._eastLon = westLon;
		}
	}

	/**
	 * Getter for x1. Setters are not required because a bounding box is read-only.
	 * @return
	 */
	public double getSouthLat()
	{
		return _southLat;
	}

	/**
	 * Getter for y1. Setters are not required because a bounding box is read-only.
	 * @return
	 */
	public double getWestLon()
	{
		return _westLon;
	}

	/**
	 * Getter for x2. Setters are not required because a bounding box is read-only.
	 * @return
	 */
	public double getNorthLat()
	{
		return _northLat;
	}

	/**
	 * Getter for y2. Setters are not required because a bounding box is read-only.
	 * @return
	 */
	public double getEastLon()
	{
		return _eastLon;
	}
	
	/**
	 * Returns true if the given point is within the BoundingBox's boundaries
	 * @param point Point to check
	 * @return true if it's within the BoundingBox, false otherwise
	 */
	public boolean contains(LatLon point) {
		return 
			point.getLat() <= getNorthLat() &&
			point.getLat() >= getSouthLat() &&
			point.getLon() <= getEastLon() &&
			point.getLon() >= getWestLon();
	}
	
	@Override
	public boolean equals(Object otherObject) {
		if (!(otherObject instanceof BoundingBox)) return false;
		BoundingBox other = (BoundingBox) otherObject;
		if (other.getNorthLat() != getNorthLat()) return false;
		if (other.getEastLon() != getEastLon()) return false;
		if (other.getSouthLat() != getSouthLat()) return false;
		if (other.getWestLon() != getWestLon()) return false;
		return true;
	}

	@Override
	public String toString() {
		return "_southLat: "+_southLat+", _westLon: "+_westLon+", _northLat: "+_northLat+", _eastLon: "+_eastLon;
	}

}