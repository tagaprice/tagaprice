package org.tagaprice.server.dao.couchdb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.jcouchdb.db.Database;
import org.jcouchdb.db.Server;
import org.svenson.JSONParser;
import org.svenson.JSONParseException;

import com.allen_sauer.gwt.log.client.Log;

/**
 * This class reads the files in src/main/webapp/WEB-INF/couchdb_inject/ and applies them to the database if necessary.
 * 
 * The couchdb database contains a special document called "version" that stores the version information
 */
public class InitialInjector {
	Database m_db = null;

	public void init(Server server, String dbName, String baseDir) throws IOException {
		if (!server.listDatabases().contains(dbName)) {
			server.createDatabase(dbName);

			// perform a full injection
			Log.debug("Injecting initial CouchDB documents");
			m_db = new Database(server, dbName);
			_injectFolder(baseDir+"/view");
			try {
				_injectFolder(baseDir+"/data");
			}
			catch (FileNotFoundException e) {
				// no data directory found, but that's no problem
			}
		}
	}

	private void _injectFolder(String folderName) throws IOException {
		String path = "/couchdb_inject/"+folderName+"/";
		String  files[] = allFilesIn(path);
		for (String filePath: files) {
			File file = new File(filePath);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String json = "";
			while (reader.ready()) {
				json += reader.readLine();
			}
			try {
				Map<?, ?> map = JSONParser.defaultJSONParser().parse(HashMap.class, json);
				m_db.createOrUpdateDocument(map);
			}
			catch (JSONParseException e) {
				JSONParseException newE = new JSONParseException(e.getMessage()+" (in "+file.getPath()+")");
				newE.setStackTrace(e.getStackTrace());
				throw newE;
			}
		}
	}

	public static String[] allFilesIn(String path) throws FileNotFoundException {
		File dirFile = _getDirFile(path);
		String rc[] = new String[0];

		String[] files = dirFile.list();
		rc = new String[files.length];

		int i = 0;
		for(String filename: files) {
			rc[i++] = dirFile.getAbsolutePath()+"/"+filename;
		}

		return rc;
	}

	protected static File _getDirFile(String path) throws FileNotFoundException {
		URL url = InitialInjector.class.getClassLoader().getResource(path);
		File rc = null;
		if (url == null) {
			throw new FileNotFoundException("Couldn't get directory listing of '"+path+"'");
		}
		else if (/* url != null && */ url.getProtocol().equals("file")) {
			try {
				rc = new File(url.toURI());
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			throw new IllegalArgumentException("Unsupported URL protocol");
		}

		return rc;
	}
}
