package org.tagaprice.server.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

import org.apache.log4j.Logger;

//TODO doku
public class SaltGenerator {
	private static Random rnd = null;
	private static Logger _log = Logger.getLogger(SaltGenerator.class);


	private static long _getSeed() {
		long rc = 0;

		try {
			FileInputStream in = new FileInputStream("/dev/urandom");
			int n;

			for (int i = 0; i < 8; i++) {
				n = in.read();
				if(n >= 0) {
					rc *= 256;
					rc += n;
				}
			}
		}
		catch (IOException e) { // /dev/urandom can't be read
			/// TODO use log4j
			SaltGenerator._log.warn("Warning: using current time as random seed");
			rc = System.currentTimeMillis();
		}

		return rc;
	}

	public static String generateSalt(int len) {
		String rc = "";

		// first time initialization
		if (SaltGenerator.rnd == null) {
			SaltGenerator.rnd = new Random(SaltGenerator._getSeed());
		}

		for (int i = 0; i < len; i++) {
			int n = SaltGenerator.rnd.nextInt(62);
			char c;
			if (n < 26) c = (char)(n+'a');
			else if (n < 52) c = (char)(n-26+'A');
			else c = (char) (n-52+'0');
			rc += c;
		}
		return rc;
	}
}
