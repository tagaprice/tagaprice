package org.tagaprice.server.utilities;

import java.security.SecureRandom;

/**
 * <p>
 * This class uses {@link SecureRandom} to generate random numbers.
 * </p>
 * 
 * @author haja
 *
 */
public class RandomNumberGenerator {

	/**
	 * Generates a secure random number using {@link SecureRandom}.
	 * 
	 * @param length
	 *            length of the generated number in bytes
	 */
	public static byte[] generateRandom(int length) {
		SecureRandom secureRandom = new SecureRandom();
		final byte[] number = new byte[length];
		secureRandom.nextBytes(number);
		return number;
	}
}
