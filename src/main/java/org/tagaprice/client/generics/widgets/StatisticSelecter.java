package org.tagaprice.client.generics.widgets;

import java.util.Date;
import java.util.List;

import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;

public class StatisticSelecter extends Composite implements IStatisticSelecter {

	private IStatisticSelecter statisticSelecter = GWT.create(IStatisticSelecter.class);

	public StatisticSelecter() {
		initWidget(statisticSelecter.asWidget());
	}

	@Override
	public void setStatisticResults(List<StatisticResult> results) {
		statisticSelecter.setStatisticResults(results);
	}

	@Override
	public void setDate(Date begin, Date end) {
		statisticSelecter.setDate(begin, end);
	}

	@Override
	public void addStatisticChangeHandler(IStatisticChangeHandler handler) {
		statisticSelecter.addStatisticChangeHandler(handler);
	}

	@Override
	public void setType(TYPE type) {
		statisticSelecter.setType(type);

	}

	@Override
	public BoundingBox getBoundingBox() {
		return statisticSelecter.getBoundingBox();
	}

	@Override
	public Date getBeginDate() {
		return statisticSelecter.getBeginDate();
	}

	@Override
	public Date getEndDate() {
		return statisticSelecter.getEndDate();
	}

	@Override
	public void setLatLon(double lat, double lon) {
		statisticSelecter.setLatLon(lat, lon);
	}
	
	public void setMapVisible(boolean visible){
		statisticSelecter.setMapVisible(visible);
	}

	@Override
	public void setLoading() {
		statisticSelecter.setLoading();
	}

}
