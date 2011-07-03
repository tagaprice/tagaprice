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

	private double _southWestLat, _southWestLng;
	private double _northEastLat, _northEastLng;

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
	public BoundingBox(double southWestLat, double southWestLng, double northEastLat, double northEastLng)
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

		if (southWestLng <= northEastLng)
		{
			this._southWestLng = southWestLng;
			this._northEastLng = northEastLng;
		}
		else
		{
			this._southWestLng = northEastLng;
			this._northEastLng = southWestLng;
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
	public double getSouthWestLng()
	{
		return (_southWestLng);
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
	public double getNorthEastLng()
	{
		return (_northEastLng);
	}

	@Override
	public String toString() {
		return "_southWestLat: "+_southWestLat+", _southWestLng: "+_southWestLng+", _northEastLat: "+_northEastLat+", _northEastLng: "+_northEastLng;
	}

}