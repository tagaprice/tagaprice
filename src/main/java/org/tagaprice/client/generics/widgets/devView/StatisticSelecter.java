package org.tagaprice.client.generics.widgets.devView;

import java.util.Date;
import java.util.List;

import org.tagaprice.client.generics.widgets.IStatisticSelecter;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class StatisticSelecter extends Composite implements IStatisticSelecter {

	public StatisticSelecter() {
		initWidget(new Label("show me a statistic"));
	}

	@Override
	public void setStatisticResults(List<StatisticResult> results) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDate(Date begin, Date end) {
		// TODO Auto-generated method stub

	}

}
