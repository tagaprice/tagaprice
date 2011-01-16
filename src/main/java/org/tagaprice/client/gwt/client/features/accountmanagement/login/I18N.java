package org.tagaprice.client.gwt.client.features.accountmanagement.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface I18N extends Messages {
	public static final I18N I18N = GWT.create(I18N.class);
	String email();
	String password();
}
