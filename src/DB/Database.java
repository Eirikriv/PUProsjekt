package DB;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/** Håndterer kommunikasjon mellom programmet og MySQL-databasen */
public class Database {
	public static boolean DEBUG = true;
	private static String url = "jdbc:mysql://mysql.stud.ntnu.no/";
	private static String dbName = "alekh_PU_DB";
	private static String driver = "com.mysql.jdbc.Driver";
	private static String userName = "alekh_PU";
	private static String password = "abcd1234";
	

	public static boolean initializeDatabase() {
		try {
			// Sletter alle tidligere tabeller 
			makeStatement("DROP TABLE PersonInGroup");
			makeStatement("DROP TABLE Person;");
			makeStatement("DROP TABLE Group0");
			makeStatement("DROP TABLE Room");
//			makeStatement("DROP TABLE PartOfEvent");
//			makeStatement("DROP TABLE Event");
			
			// Oppretter Person-tabellen
			makeStatement("CREATE TABLE Person"
					+ "(PersonID INT NOT NULL AUTO_INCREMENT,"
					+ "Name VARCHAR(20) NOT NULL,"
					+ "Username VARCHAR(20)	NOT NULL,"
					+ "Password	VARCHAR(20)	NOT NULL,"
					+ "PRIMARY KEY (PersonID));");
			
			//Oppretter Group-tabellen		
			makeStatement("CREATE TABLE Group0"
					+ "(GroupID INT NOT NULL,"
					+ "Name VARCHAR(20) NOT NULL,"
					+ "PRIMARY KEY (GroupID));");
			
			//Oppretter PersonGroup-tabellen		
			makeStatement("CREATE TABLE PersonInGroup"
					+ "(PersonID INT NOT NULL,"
					+ "GroupID INT NOT NULL,"
					+ "PRIMARY KEY (PersonID, GroupID),"
					+ "FOREIGN KEY (PersonID) REFERENCES Person(PersonID) "
					+ "ON UPDATE CASCADE ON DELETE CASCADE,"
					+ "FOREIGN KEY (GroupID) REFERENCES Group0(GroupID) "
					+ "ON UPDATE CASCADE ON DELETE CASCADE);");
			
			//Oppretter Room-tabellen
			makeStatement("CREATE TABLE Room"
					+ "(RoomID INT NOT NULL AUTO_INCREMENT,"
					+ "Capacity INT NOT NULL,"
					+ "Description VARCHAR(100),"
					+ "PRIMARY KEY (RoomID));");
			
			//Oppretter Event-tabellen		
			makeStatement("CREATE TABLE Event"
					+ "(EventID INT NOT NULL AUTO_INCREMENT,"
					+ "Name VARCHAR(20) NOT NULL,"
					+ "Start CHAR(14) NOT NULL,"
					+ "End CHAR(14) NOT NULL,"
					+ "Description VARCHAR(100),"
					+ "RoomID INT,"
					+ "PRIMARY KEY (EventID),"
					+ "FOREIGN KEY (RoomID) REFERENCES ROOM(RoomID) "
					+ "ON UPDATE CASCADE);");
			
			//Oppretter PartOfEvent-tabellen
			makeStatement("CREATE TABLE PartOfEvent"
					+ "(ObjectID INT NOT NULL,"
					+ "EventID INT NOT NULL,"
					+ "PRIMARY KEY (ObjectID, EventID),"
					+ "FOREIGN KEY (ObjectID) REFERENCES Person(PersonID) "
					+ "ON UPDATE CASCADE ON DELETE CASCADE, "
					+ "FOREIGN KEY (EventID) REFERENCES Event(EventID) "
					+ "ON UPDATE CASCADE ON DELETE CASCADE);");
			
			makeStatement("INSERT INTO Person\n"
					+ "VALUES('1', 'Cecilie Teisberg', 'cecilite', 'passord');");
			makeStatement("INSERT INTO Person\n"
					+ "VALUES('2', 'Anders Rønold', 'anron', 'passord1');");
			makeStatement("INSERT INTO Group0(id, name)"
					+ "VALUES('3', 'PU');");
			makeStatement("INSERT INTO PersonInGroup\n"
					+ "VALUES('1', '3');");
			makeStatement("INSERT INTO PersonInGroup\n"
					+ "VALUES('2', '3');");
			//makeStatement("INSERT INTO")
			return true;

		} catch (Exception e) {
			if (DEBUG)
				e.printStackTrace();
			return false;
		}
	}

	/**
	 * Gjør en spørring mot databasen
	 * @param query En query-streng
	 * @return Et resultat-sett
	 */
	public static ResultSet makeQuery(String query) {
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
	 * Utfører statements mot databasen
	 * @param statement Statement som skal utføres
	 * @return returnerer om statementen ble fullført
	 */
	private static boolean makeStatement(String statement) {
		try {
			Connection conn = getConnection();
			Statement st = conn.createStatement();
			int res = st.executeUpdate(statement); //trenger denne å lagres??
			//conn.close();
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
