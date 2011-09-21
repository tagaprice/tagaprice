package org.tagaprice.client.generics.widgets;

import java.util.Date;
import java.util.List;

import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;

import com.google.gwt.user.client.ui.IsWidget;

public interface IStatisticSelecter extends IsWidget {

	public enum TYPE {
		SHOP, PRODUCT, SHOPCATEGORY, PRODUCTCATEGORY
	}

	public void setLatLon(double lat, double lon);
	
	public void setType(TYPE type);

	public void setStatisticResults(List<StatisticResult> results);

	public void setDate(Date begin, Date end);

	public void addStatisticChangeHandler(IStatisticChangeHandler handler);
	
	public BoundingBox getBoundingBox();
	
	public Date getBeginDate();
	
	public Date getEndDate();
	
	public void setMapVisible(boolean visible);
	
	public void setLoading();
}
