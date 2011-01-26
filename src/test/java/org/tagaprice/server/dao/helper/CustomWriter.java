package org.tagaprice.server.dao.helper;

import java.io.BufferedWriter;
import java.io.IOException;

public class CustomWriter {

	private BufferedWriter writer;

	public CustomWriter(BufferedWriter bufferedWriter) {
		writer = bufferedWriter;
	}

	public void write(String string) throws IOException {
		writer.write(string);
		System.out.println(string);
		
	}

	public void close() throws IOException {
		writer.close();
	}

}
