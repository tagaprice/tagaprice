package org.tagaprice.client.gwt.client.generics;

import com.google.gwt.user.client.ui.Widget;

/**
 * For abstraction of table layout.
 * 
 * @param <T>
 */

public abstract class ColumnDefinition<T> {
	public abstract Widget render(T t);

	public abstract String getColumnName();

	public boolean isClickable() {
		return false;
	}

	public boolean isSelectable() {
		return false;
	}
}
