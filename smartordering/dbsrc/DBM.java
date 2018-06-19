package dbsrc;

import java.sql.*;

public class DBM {
	
	private static Connection con;

	public DBM(String username, String password) {

		// initialize jdbc connection to Oracle
		String connectURL = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug";
		
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			con = DriverManager.getConnection(connectURL, username, password);
			System.out.println("Connected to Oracle!");

		} catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			System.exit(0);
		}
	}

	public static Connection getConnection() {
		return con;
	}

	// Return the results set of an entire table
	public static ResultSet getTable(String table) {

		ResultSet results = null;
		
		try {	
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			results = stmt.executeQuery("SELECT * FROM " + table);
		} catch(SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			ex.printStackTrace();
		}

		return results;
	}

	// Print the entire table on console
	public static void showTable(String table) {

		try {
			Statement stmt = con.createStatement();
			ResultSet results = stmt.executeQuery("SELECT * FROM " + table);
			ResultSetMetaData metadata = results.getMetaData();
			int colCount = metadata.getColumnCount();

			System.out.println();

			// print column names
			for(int i = 1; i <= colCount; i++)
				System.out.print(metadata.getColumnName(i) + "\t");

			System.out.println();

			// print data rows
			while(results.next()) {
				for(int i = 1; i <= colCount; i++)
					System.out.print(results.getString(i) + "\t");
				System.out.println();
			}
			stmt.close();

		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}

	/*
	public void runSQLScript(String path){
		try {
			// Give the input file to Reader
			BufferedReader reader = new BufferedReader(new FileReader(path));

			Statement stmt = con.createStatement();

			String line = reader.readLine();
			String buffer = "";

			while(line != null){
				if(line.length() == 0){
					line = reader.readLine();
					continue;
				}

				buffer += line;

				if(line.contains(";")){
					System.out.println(buffer.substring(0, buffer.length() - 1));
					System.out.println("\n");

					try{
						stmt.executeUpdate(buffer.substring(0, buffer.length() - 1));
					}
					catch (Exception e){
						System.out.println(e.getMessage() + "\n");
					}
					buffer = "";
				}
				
				line = reader.readLine();
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}*/
}