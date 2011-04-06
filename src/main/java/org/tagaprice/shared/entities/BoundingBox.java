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

import java.io.Serializable;

/**
 * This class implements a rectangular BoundingBox.
 */
public class BoundingBox implements Serializable {
	/// default serial version id
	private double x1, y1;
	private double x2, y2;

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
	public BoundingBox(double x1, double y1, double x2, double y2)
	{
		//Make sure the the coordinates of (x1, y1) are lower than for the point (x2, y2).
		if (x1 <= x2)
		{
			this.x1 = x1;
			this.x2 = x2;
		}
		else
		{
			this.x1 = x2;
			this.x2 = x1;
		}

		if (y1 <= y2)
		{
			this.y1 = y1;
			this.y2 = y2;
		}
		else
		{
			this.y1 = y2;
			this.y2 = y1;
		}
	}

	/**
	 * Getter for x1. Setters are not required because a bounding box is read-only.
	 * @return
	 */
	public double getX1()
	{
		return (x1);
	}

	/**
	 * Getter for y1. Setters are not required because a bounding box is read-only.
	 * @return
	 */
	public double getY1()
	{
		return (y1);
	}

	/**
	 * Getter for x2. Setters are not required because a bounding box is read-only.
	 * @return
	 */
	public double getX2()
	{
		return (x2);
	}

	/**
	 * Getter for y2. Setters are not required because a bounding box is read-only.
	 * @return
	 */
	public double getY2()
	{
		return (y2);
	}

	@Override
	public String toString() {
		return "x1: "+x1+", y1: "+y1+", x2: "+x2+", y2: "+y2;
	}

}