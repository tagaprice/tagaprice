package org.tagaprice.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface Config extends Messages {
	public static final Config CONFIG = GWT.create(Config.class);

	String recaptchaPuplicKey();
}
