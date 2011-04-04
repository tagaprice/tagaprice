package org.tagaprice.client.generics;

import java.util.HashMap;

/**
 * The {@link TokenCreator} is able implode and explode a token. You should use the {@link TokenCreator} when you create
 * a token or you wanna split a token.
 * With the {@link TokenCreator} it is very easy and secure, when you work with your own tokens.
 * 
 */
public class TokenCreator {

	public static final String encodedSlash = "%2F";
	public static final String encodedPercent = "%25";

	/**
	 * Return a {@link TokenCreator}  that can split a token.
	 * @param token The token you want to split.
	 * @return An {@link Exploder} object depending on your token
	 */
	public static Exploder getExploder(String token) {
		return new Exploder(token);
	}

	/**
	 * Returns a {@link TokenCreator} with which you can create a new token.
	 * @return a {@link Imploder} object helps you creating a token.
	 */
	public static Imploder getImploder() {
		return new Imploder();
	}

	public static class Exploder {
		String _token;
		String _root;
		HashMap<String, String> _nodes = new HashMap<String, String>();

		private Exploder(String token) {
			_token = token;
			doExplosion();
		}

		/**
		 * Return the root token element. It has no value.
		 * @return Return the root token element. It has no value.
		 */
		public String getRoot() {
			return _root;
		}

		/**
		 * Return a value given by a name.
		 * @param name The parameter name, from which you wanna have the value.
		 * @return The value of the specified name.
		 */
		public String getNode(String name) {
			return _nodes.get(name);
		}

		private void doExplosion() {

			String[] t = _token.split("/");

			if (t.length > 1) {
				if (t[1] != null) {
					_root = t[1];
				}

				for (int i = 2; i < t.length; i = i + 2) {
					String varname = t[i];
					String value = t[i+1];

					varname = varname.replace(TokenCreator.encodedPercent, "%");
					varname = varname.replace(TokenCreator.encodedSlash, "/");

					value = value.replace(TokenCreator.encodedPercent, "%");
					value = value.replace(TokenCreator.encodedSlash, "/");

					_nodes.put(varname, value);
				}
			}
		}
	}

	public static class Imploder {

		private String _root;
		private StringBuffer _nodes = new StringBuffer();

		/**
		 * Set the root token element. It has no value.
		 * @param root The root token element.
		 */
		public void setRoot(String root) {
			_root = root;
		}


		/**
		 * Add a token name and value to the tree.
		 * @param name The name of the token parameter.
		 * @param value The value of this parameter.
		 */
		public void addNode(String varname, String value) {

			varname = varname.replace("%", TokenCreator.encodedPercent);
			varname = varname.replace("/", TokenCreator.encodedSlash);

			value = value.replace("%", TokenCreator.encodedPercent);
			value = value.replace("/", TokenCreator.encodedSlash);

			_nodes.append("/");
			_nodes.append(varname);
			_nodes.append("/");
			_nodes.append(value);
		}


		/**
		 * Return the whole token.
		 * @return the whole token.
		 */
		public String getToken() {
			// User StringBuffer because it's much faster than a+b
			StringBuffer a = new StringBuffer();
			a.append("/");
			a.append(_root);
			a.append(_nodes.toString());

			return a.toString();
		}
	}

}
