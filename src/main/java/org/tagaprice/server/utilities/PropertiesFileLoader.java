package org.tagaprice.server.utilities;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Utilityclass for loading .properties files.
 * @author "haja"
 *
 */
public class PropertiesFileLoader {

	/**
	 * Opens a ressource file as {@link InputStream}.
	 * @param fileName path to the ressource to load
	 * @return {@link InputStream} to the given ressource
	 * @throws FileNotFoundException if the given ressource could not be found
	 */
	public static InputStream loadResourceFile(String fileName) throws FileNotFoundException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(fileName);

		if (inputStream == null) {
			throw new FileNotFoundException("Error: Couldn't open property file '" + fileName + "'");
		}

		return inputStream;
	}
}
