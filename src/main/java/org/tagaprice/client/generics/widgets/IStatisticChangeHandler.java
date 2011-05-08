package org.tagaprice.client.generics.widgets;

import java.util.Date;

import org.tagaprice.shared.entities.BoundingBox;


public interface IStatisticChangeHandler {

	public void onChange(BoundingBox bbox, Date begin, Date end);
}
