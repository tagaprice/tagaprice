package org.tagaprice.client.features.startmanagement;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface I18N extends Messages {
	public static final I18N I18N = GWT.create(I18N.class);
	String title();
}
