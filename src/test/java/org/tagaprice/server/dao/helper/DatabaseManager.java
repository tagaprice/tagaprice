package org.tagaprice.server.dao.helper;

import java.io.*;
import java.sql.*;
import java.util.*;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.*;
import org.dbunit.dataset.*;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.slf4j.*;
import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.entities.Entity;
/**
 * A ConnectinoManager that uses {@link DBConnection} for convenience Testing with DBUnit.
 * Easy method for simple adding data with {@link XmlDataSet}.
 * The tables are deleted and created with every testrun.
 * Needs a Postgres DB with existing database! Don't foregt jdbc.properties!
 * 
 * @author Martin Weik (afraidoferrors)
 *
 */
public class DatabaseManager {
	static Logger logger = LoggerFactory.getLogger(DatabaseManager.class);

	private static DBConnection dbConnection;
	private static Connection javaConnection;
	private static DatabaseConnection dbUnitConnection;
	private static boolean tablesExist = false;

	/**
	 * This method is to export data from the database into xml files.
	 * It will interactive ask You connection parameters and the table name and then store it to a file.
	 * @param args nothing
	 */
	public static void main(String[] args) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException cnfe) {
			System.err.println("Couldn't find driver class:");
			cnfe.printStackTrace();
		}
		String hostname = "localhost";
		PrintStream out = System.out;
		Scanner in = new Scanner(System.in);
		out.print("databasename: ");
		String databasename = in.nextLine();
		out.print("username: ");
		String username = in.nextLine();
		out.print("password: ");
		String password = in.nextLine();
		out.print("table: ");
		String tablename = in.nextLine();

		Connection c;
		DatabaseConnection dc;
		QueryDataSet qds;
		try {
			c = DriverManager.getConnection("jdbc:postgresql://" + hostname + "/" + databasename,
					username, password);
			dc = new DatabaseConnection(c);
			qds = new QueryDataSet(dc);
			qds.addTable(tablename, "SELECT * FROM " + tablename);
			File xmlFile = new File("./src/test/resources/WEB-INF/testdata/" + tablename + "_dao.xml");
			FileOutputStream fos = new FileOutputStream(xmlFile);
			XmlDataSet.write(qds, fos);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseUnitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


	/**
	 * a easy to use facade for {@link DBConnection}
	 * @return a {@link Connection} to the database through {@link DBConnection}
	 */
	public static Connection getConnection() {
		try {
			//initialise connections if not allready done
			if(DatabaseManager.dbConnection == null)
				DatabaseManager.dbConnection = new DBConnection();
			if(DatabaseManager.javaConnection == null)
				DatabaseManager.javaConnection = DatabaseManager.dbConnection.getConnection();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//DatabaseManager.tablesExist();
		return DatabaseManager.javaConnection;
	}

	/**
	 * If called the first time per run, drops all tables and creates new ones.
	 * @return false if something bad happend, true if everthing is ok
	 */
	public static boolean tablesExist() {

		if(!DatabaseManager.tablesExist) {
			//delete all tables and create them again
			try {
				Statement queryTables = DatabaseManager.javaConnection.createStatement();
				Statement queryDrop = DatabaseManager.javaConnection.createStatement();
				ResultSet resultsTables;
				// TODO this must be fixed. types in postgres are...
				queryDrop.execute("DROP TYPE IF EXISTS propertytype CASCADE");
				boolean goOn = true;
				do {
					queryTables.execute("SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'");
					resultsTables = queryTables.getResultSet();
					if((goOn = resultsTables.next())) {
						String queryDropString = "DROP TABLE IF EXISTS " + resultsTables.getString(1) + " CASCADE";
						System.out.println(queryDropString + ": " + queryDrop.execute(queryDropString));
					}
				} while(goOn);

				queryTables.execute("SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'");
				resultsTables = queryTables.getResultSet();
				while(resultsTables.next()) {
					System.out.println(resultsTables.getString(1));
				}

				DatabaseManager.loadTables();
				DatabaseManager.tablesExist = true;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return DatabaseManager.tablesExist;
	}

	public static DBConnection getDBConnection() {
		if(DatabaseManager.dbConnection == null){
			DatabaseManager.getConnection();
		}
		return DatabaseManager.dbConnection;
	}

	public static DatabaseConnection getDBUnitConnection() {
		if(DatabaseManager.dbUnitConnection == null || DatabaseManager.javaConnection == null) {
			DatabaseManager.getConnection();
		}
		try {
			DatabaseManager.dbUnitConnection = new DatabaseConnection(DatabaseManager.javaConnection);
		} catch (DatabaseUnitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DatabaseManager.dbUnitConnection;
	}

	private static void loadTables() throws FileNotFoundException, SQLException {
		String directoryName = "src/main/sql/tables/";
		File directory = new File(directoryName);
		ArrayList<String> files = new ArrayList<String>();
		files.addAll(Arrays.asList(directory.list()));
		Collections.sort(files);
		for (String filename : files) {
			DatabaseManager.logger.info(filename);
			Statement stmnt = DatabaseManager.javaConnection.createStatement();
			if (filename.endsWith(".sql")) {
				System.out.println("Parsing " + directoryName + filename);
				File file = new File(directoryName + filename);
				Scanner scanner = new Scanner(file);
				scanner.useDelimiter(";");
				while (scanner.hasNext()) {
					String sql = scanner.next();
					//System.out.println("- " + sql);
					if (sql.trim().equals("")) {

					} else {
						stmnt.execute(sql);
					}
				}
			}
		}

	}

	/**
	 * Takes a class object of a given class and sets up the database.
	 * The returned {@link IDataSet} can be used to compare results from getXXByYY methods.
	 * The DataFiles are looked up in ??? and named like Classname_data.xml.
	 * Before inserting all data in the database are erased.
	 * @param myClass the class of the desired Entity
	 * @return the found dataset for comparision
	 */
	public static IDataSet setupEntitytable(Class<? extends Entity> entityClass) {
		return DatabaseManager.setupEntitytable(entityClass, true);
	}

	/**
	 * Takes a class object of a given class and sets up the database.
	 * The returned {@link IDataSet} can be used to compare results from getXXByYY methods.
	 * The DataFiles are looked up in ??? and named like Classname_data.xml
	 * @param myClass the class of the desired Entity
	 * @param clean if true, all data in the database are erased
	 * @return the found dataset for comparision
	 */
	public static IDataSet setupEntitytable(Class<? extends Entity> entityClass, boolean clean) {
		if(DatabaseManager.dbUnitConnection == null) {
			DatabaseManager.getDBUnitConnection();
		}
		/*
		 * This doesn't work and I have NO idea why????
		 * Look at DBConnection.loadRessourceFile
		 *
		try {
			String path = "account.properties";
			System.out.println(path);
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
			System.out.println(is);
		} catch (Exception e) {
			System.out.println("Ressource nicht gefunden");
			System.out.println(e);
		}
		 */
		File xmlFile = new File("./src/test/resources/WEB-INF/testdata/" + entityClass.getSimpleName() + "_dao.xml");
		System.out.println(xmlFile.getAbsolutePath());

		try {
			IDataSet dataSet = new XmlDataSet(new FileInputStream(xmlFile));
			DatabaseOperation.CLEAN_INSERT.execute(DatabaseManager.getDBUnitConnection(), dataSet);
			return dataSet;
		} catch (DataSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseUnitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}