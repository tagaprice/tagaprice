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
package org.tagaprice.shared.utility;

import org.tagaprice.shared.ISerializable;

/**
 * This class implements an immutable rectangular BoundingBox.
 * @author MK
 */
public class BoundingBox implements ISerializable {
	/// default serial version id
	private static final long serialVersionUID = 1L;
	private double x1, y1;
	private double x2, y2;
	
	/**
	 * StandardConstruction. Is required for the serialization
	 * Don't call this constructor since this class is immutable.
	 */
	public BoundingBox()
	{
	}
	
	/**
	 * Create a new bounding box.
	 * This constructor assures, that when calling getX1, it always returns the smaller value of x1 and x2.
	 * The same holds true for y1 and y2 respectively.
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
	 * @return x1
	 */
	public double getX1()
	{
		return (x1);
	}

	/**
	 * @return y1
	 */
	public double getY1()
	{
		return (y1);
	}

	/**
	 * @return x2
	 */	
	public double getX2()
	{
		return (x2);
	}
	
	/**
	 * @return y2
	 */
	public double getY2()
	{
		return (y2);
	}
	
	public String toString() {
		return "x1: "+x1+", y1: "+y1+", x2: "+x2+", y2: "+y2;
	}

	public String getSerializeName() {
		return "boundingBox";
	}
}
