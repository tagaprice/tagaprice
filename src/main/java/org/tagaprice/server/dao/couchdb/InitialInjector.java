package org.tagaprice.server.dao.couchdb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.jcouchdb.db.Database;
import org.jcouchdb.db.Server;
import org.jcouchdb.exception.NotFoundException;
import org.svenson.JSONParser;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;

/**
 * This class reads the files in src/main/webapp/WEB-INF/couchdb_inject/ and applies them to the database if necessary.
 * 
 * The couchdb database contains a special document called "version" that stores the version information
 */
public class InitialInjector {
	Database m_db = null;
	MyLogger m_logger = LoggerFactory.getLogger(InitialInjector.class);

	public void init(Server server, String dbName) throws IOException {
		if (!server.listDatabases().contains(dbName)) {
			server.createDatabase(dbName);
		}
		
		if (getDbVersion(server, dbName) == null) {
			// perform a full injection
			m_logger.log("Injecting initial CouchDB documents");
			m_db = new Database(server, dbName);
			_injectFolder("view");
			_injectFolder("data");
		}
	}
	
	public static int[] getDbVersion(Server server, String dbName) {
		int rc[] = null;
		
		if (server.listDatabases().contains(dbName)) {
			Database db = new Database(server, dbName);
			try {
				Map<?,?> versionDoc = db.getDocument(Map.class, "version");
				if (versionDoc.containsKey("major") && versionDoc.containsKey("minor")) {
					rc = new int[2];
					rc[0] = Integer.parseInt(versionDoc.get("major").toString());
					rc[1] = Integer.parseInt(versionDoc.get("minor").toString());
				}
			}
			catch (NotFoundException e) {/* we'll simply return null in this case */}
		}
		
		return rc;
	}
	
	private void _injectFolder(String folderName) throws IOException {
		String path = "/couchdb_inject/"+folderName+"/";
		String  files[] = _allFilesIn(path);
		for (String filePath: files) {
			File file = new File(filePath);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String json = "";
			while (reader.ready()) {
				json += reader.readLine();
			}
			Map<?, ?> map = JSONParser.defaultJSONParser().parse(HashMap.class, json);
			
			m_db.createOrUpdateDocument(map);
		}
	}
	
	private String[] _allFilesIn(String path) {
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
	
	protected File _getDirFile(String path) {
		URL url = getClass().getClassLoader().getResource(path);
		File rc = null;
		if (url == null) {
			throw new NullPointerException("Couldn't get directory listing of '"+path+"'");
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
