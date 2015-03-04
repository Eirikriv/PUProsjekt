package database;

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
			makeStatement("DROP TABLE IF EXISTS PersonInGroup");
			makeStatement("DROP TABLE IF EXISTS PersonEvent");
			makeStatement("DROP TABLE IF EXISTS GroupEvent");
			makeStatement("DROP TABLE IF EXISTS Event");
			makeStatement("DROP TABLE IF EXISTS Room");
			makeStatement("DROP TABLE IF EXISTS Person;");
			makeStatement("DROP TABLE IF EXISTS Groups");
			
			// Oppretter Person-tabellen
			makeStatement("CREATE TABLE Person"
					+ "(Username VARCHAR(20) NOT NULL,"
					+ "Name VARCHAR(20) NOT NULL,"
					+ "Password	VARCHAR(20)	NOT NULL,"
					+ "PRIMARY KEY (Username));");
			
			//Oppretter Group-tabellen		
			makeStatement("CREATE TABLE Groups"
					+ "(GroupID INT NOT NULL AUTO_INCREMENT,"
					+ "Name VARCHAR(20) NOT NULL,"
					+ "PRIMARY KEY (GroupID));");
			
			//Oppretter PersonInGroup-tabellen		
			makeStatement("CREATE TABLE PersonInGroup"
					+ "(Username VARCHAR(20) NOT NULL,"
					+ "GroupID INT NOT NULL,"
					+ "PRIMARY KEY (Username, GroupID),"
					+ "FOREIGN KEY (Username) REFERENCES Person(Username) "
					+ "ON UPDATE CASCADE ON DELETE CASCADE,"
					+ "FOREIGN KEY (GroupID) REFERENCES Groups(GroupID) "
					+ "ON UPDATE CASCADE ON DELETE CASCADE);");
			
			//Oppretter Room-tabellen
			makeStatement("CREATE TABLE Room"
					+ "(RoomID VARCHAR(20) NOT NULL,"
					+ "Capacity INT NOT NULL,"
					+ "Description VARCHAR(100),"
					+ "PRIMARY KEY (RoomID));");
			
			//Oppretter Event-tabellen		
			makeStatement("CREATE TABLE Event"
					+ "(EventID INT NOT NULL AUTO_INCREMENT,"
					+ "Name VARCHAR(20) NOT NULL,"
					+ "Start CHAR(16) NOT NULL,"
					+ "End CHAR(16) NOT NULL,"
					+ "Description VARCHAR(100),"
					+ "RoomID VARCHAR(20),"
					+ "PRIMARY KEY (EventID),"
					+ "FOREIGN KEY (RoomID) REFERENCES Room(RoomID) "
					+ "ON UPDATE CASCADE ON DELETE NO ACTION);");
			
			//Oppretter PersonEvent-tabellen
			makeStatement("CREATE TABLE PersonEvent"
					+ "(Username VARCHAR(20) NOT NULL,"
					+ "EventID INT NOT NULL,"
					+ "PRIMARY KEY (Username, EventID),"
					+ "FOREIGN KEY (Username) REFERENCES Person(Username) "
					+ "ON UPDATE CASCADE ON DELETE CASCADE, "
					+ "FOREIGN KEY (EventID) REFERENCES Event(EventID) "
					+ "ON UPDATE CASCADE ON DELETE CASCADE);");
			
			//Oppretter GroupEvent-tabellen
			makeStatement("CREATE TABLE GroupEvent"
					+ "(GroupID INT NOT NULL,"
					+ "EventID INT NOT NULL,"
					+ "PRIMARY KEY (GroupID, EventID),"
					+ "FOREIGN KEY (GroupID) REFERENCES Groups(GroupID) "
					+ "ON UPDATE CASCADE ON DELETE CASCADE, "
					+ "FOREIGN KEY (EventID) REFERENCES Event(EventID) "
					+ "ON UPDATE CASCADE ON DELETE CASCADE);");
			
//			makeStatement("INSERT INTO Person(Name, Username, Password)\n"
//					+ "VALUES('Cecilie Teisberg', 'cecilite', 'passord');");
//			makeStatement("INSERT INTO Person(Name, Username, Password)\n"
//					+ "VALUES('Anders Rønold', 'andronol', 'passord1');");
//			makeStatement("INSERT INTO Groups(Name)\n"
//					+ "VALUES('PU');");
//			makeStatement("INSERT INTO PersonInGroup\n"
//					+ "VALUES('1', '1');");
//			makeStatement("INSERT INTO PersonInGroup\n"
//					+ "VALUES('2', '1');");
//			makeStatement("INSERT INTO Room\n"
//					+ "VALUES('R1', '300', 'Forelesningssal')");
//			makeStatement("INSERT INTO Event(Name, Start, End, RoomID)\n"
//					+ "VALUES('Fysikkforelesning', '2015-02-26 08:15', '2015-02-26 10:00', 'R1')");
//			makeStatement("INSERT INTO GroupEvent\n"
//					+ "VALUES('1', '1')");
//			makeStatement("INSERT INTO PersonEvent\n"
//					+ "VALUES('1', '1')");
//			makeStatement("INSERT INTO PersonEvent\n"
//					+ "VALUES('2', '1')");
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
	public static boolean makeStatement(String statement) {
		try {
			Connection conn = getConnection();
			Statement st = conn.createStatement();
			st.executeUpdate(statement);
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
