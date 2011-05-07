package org.tagaprice.client.generics.widgets;

import java.util.List;

import org.tagaprice.shared.entities.searchmanagement.StatisticResult;

import com.google.gwt.user.client.ui.IsWidget;

public interface IStatisticSelecter extends IsWidget {

	public void setStatisticResults(List<StatisticResult> results);
}
