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

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * This class implements a rectangular BoundingBox.
 */
public class BoundingBox implements IsSerializable {
	/// default serial version id
	private static final long serialVersionUID = 1L;

	private double _southWestLat, _southWestLon;
	private double _northEastLat, _northEastLon;

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
	public BoundingBox(double southWestLat, double southWestLon, double northEastLat, double northEastLon)
	{
		//Make sure the the coordinates of (x1, y1) are lower than for the point (x2, y2).
		if (southWestLat <= northEastLat)
		{
			this._southWestLat = southWestLat;
			this._northEastLat = northEastLat;
		}
		else
		{
			this._southWestLat = northEastLat;
			this._northEastLat = southWestLat;
		}

		if (southWestLon <= northEastLon)
		{
			this._southWestLon = southWestLon;
			this._northEastLon = northEastLon;
		}
		else
		{
			this._southWestLon = northEastLon;
			this._northEastLon = southWestLon;
		}
	}

	/**
	 * Getter for x1. Setters are not required because a bounding box is read-only.
	 * @return
	 */
	public double getSouthWestLat()
	{
		return (_southWestLat);
	}

	/**
	 * Getter for y1. Setters are not required because a bounding box is read-only.
	 * @return
	 */
	public double getSouthWestLon()
	{
		return (_southWestLon);
	}

	/**
	 * Getter for x2. Setters are not required because a bounding box is read-only.
	 * @return
	 */
	public double getNorthEastLat()
	{
		return (_northEastLat);
	}

	/**
	 * Getter for y2. Setters are not required because a bounding box is read-only.
	 * @return
	 */
	public double getNorthEastLon()
	{
		return (_northEastLon);
	}

	@Override
	public String toString() {
		return "_southWestLat: "+_southWestLat+", _southWestLon: "+_southWestLon+", _northEastLat: "+_northEastLat+", _northEastLon: "+_northEastLon;
	}

}