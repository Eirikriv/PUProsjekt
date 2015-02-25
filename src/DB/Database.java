package DB;

import java.io.FileInputStream;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/** H�ndterer kommunikasjon mellom programmet og MySQL-databasen */
public class Database {
	public static boolean DEBUG = false;
	private static String url = "jdbc:mysql://mysql.stud.ntnu.no/";
	private static String dbName = "alekh_PU_DB";
	private static String driver = "com.mysql.jdbc.Driver";
	private static String userName = "alekh_PU";
	private static String password = "abcd1234";
	

	public static boolean initializeDatabase() {
		try {
			// Sletter alle tidligere tabeller --> TRENGS DENNE????
			//makeStatement("DROP TABLE koie");
			
			// Oppretter Person-tabellen
			makeStatement("CREATE TABLE Person"
					+ "(PersonID INT NOT NULL AUTO_INCREMENT, "
					+ "Name VARCHAR(20) NOT NULL, "
					+ "Username VARCHAR(20)	NOT NULL, "
					+ "Password	VARCHAR(20)	NOT NULL"
					+ "PRIMARY KEY (PersonID))");
			
			//Oppretter PersonGroup-tabellen		
			makeStatement("CREATE TABLE PersonGroup"
					+ "(PersonID INT NOT NULL"
					+ "GroupID INT NOT NULL"
					+ "PRIMARY KEY (PersonID, GroupID)"
					+ "FOREIGN KEY (PERSONID) REFERENCES PERSON(PersonID)"
					+ "ON UPDATE CASCADE ON DELETE CASCADE,"
					+ "FOREIGN KEY (GROUPID) REFERENCES GROUP(GroupID)"
					+ "ON UPDATE CASCADE ON DELETE CASCADE))");
			
			//Oppretter Group-tabellen		
			makeStatement("CREATE TABLE Group"
					+ "(GroupID INT	NOT NULL AUTO_INCREMENT,"
					+ "Name VARCHAR(20)	NOT NULL,"
					+ "PRIMARY KEY (GroupID));");
			
			//Oppretter PartOfEvent-tabellen
			makeStatement("CREATE TABLE PartOfEvent"
					+ "(ObjectID INT NOT NULL, "
					+ "EventID INT NOT NULL, "
					+ "PRIMARY KEY (ObjectID, EventID), "
					+ "FOREIGN KEY (ObjectID) REFERENCES PERSON(PersonID) OR GROUP(GroupID) "
					+ "ON UPDATE CASCADE ON DELETE CASCADE, "
					+ "FOREIGN KEY (EventID) REFERENCES EVENT(EventID) "
					+ "ON UPDATE CASCADE ON DELETE CASCADE)");

			//Oppretter Event-tabellen		
			makeStatement("CREATE TABLE Event"
					+ "(EventID INT NOT NULL AUTO_INCREMENT"
					+ "Name VARCHAR(20) NOT NULL"
					+ "Start CHAR(14) NOT NULL,"
					+ "End CHAR(14) NOT NULL,"
					+ "Description VARCHAR(100),"
					+ "RoomID INT,"
					+ "PRIMARY KEY (EventID),"
					+ "FOREIGN KEY (RoomID) REFERENCES ROOM(RoomID) "
					+ "ON UPDATE CASCADE))");
			
			//Oppretter Room-tabellen
			makeStatement("CREATE TABLE Room"
					+ "(RoomID INT NOT NULL AUTO_INCREMENT, "
					+ "Capacity INT NOT NULL, "
					+ "Description VARCHAR(100), "
					+ "PRIMARY KEY (RoomID)");
			
			return true;

		} catch (Exception e) {
			if (DEBUG)
				e.printStackTrace();
			return false;
		}
	}


	/**
	 * Gj�r en sp�rring mot databasen
	 * @param query En query-streng
	 * @return Et resultat-sett
	 */
	private static ResultSet makeQuery(String query) {
		ResultSet res = null;
		try {
			Connection conn = getConnection();
			Statement st = conn.createStatement();
			res = st.executeQuery(query);
			//conn.close(); // M� kommenteres ut for at getIdNameMap skal fungere...
		} catch (Exception e) {
			if (DEBUG)
				e.printStackTrace();
			return null;
		}
		return res;
	}

	/**
	 * Utf�rer statements mot databasen
	 * @param statement Statement som skal utf�res
	 * @return returnerer om statementen ble fullf�rt
	 */
	private static boolean makeStatement(String statement) {
		try {
			Connection conn = getConnection();
			Statement st = conn.createStatement();
			int res = st.executeUpdate(statement);
			conn.close();
			return true;
		} catch (Exception e) {
			if (DEBUG)
				e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Returnerer en kobling til databasen
	 * @return Et Connection-objekt
	 */
	private static Connection getConnection() {
		try {		
			Class.forName(driver).newInstance();
			Connection conn = DriverManager.getConnection(url+dbName,userName,password);
			return conn;
			
		} catch (Exception e) {
			return null;
		}
	}
}
