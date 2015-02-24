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

/** Håndterer kommunikasjon mellom programmet og MySQL-databasen */
public class Database {
	public static boolean DEBUG = false;
	private static String url = "jdbc:mysql://mysql.stud.ntnu.no/";
	private static String dbName = "alekh_prosjekt1";
	private static String driver = "com.mysql.jdbc.Driver";
	private static String userName = "alekh_PU";
	private static String password = "abcd1234";
	

	public static boolean initializeDatabase() {
		try {
			// Sletter alle tidligere tabeller
			makeStatement("DROP TABLE koie");
			
			// Oppretter Person-tabellen
			makeStatement("CREATE TABLE PERSON"
						+ "(PERSONID INT NOT NULL, "
						+ "NAME VARCHAR(20) NOT NULL, "
						+ "USERNAME VARCHAR(20)	NOT NULL, "
						+ "PASSWORD	VARCHAR(20)	NOT NULL"
						+ "PRIMARY KEY (PERSONID))");
			
			//Oppretter PersonGroup-tabellen		
			makeStatement("CREATE TABLE PersonGroup"
						+ "(PERSONID INT NOT NULL"
						+ "GRUOPID INT NOT NULL"
						+ "PRIMARY KEY (PERSONID, GROUPID)"
						+ "FOREIGN KEY (PERSONID) REFERENCES PERSON(PERSONID)"
						+ "ON UPDATE CASCADE ON DELETE CASCADE,"
						+ "FOREIGN KEY (GROUPID) REFERENCES GROUP(GROUPID)"
						+ "ON UPDATE CASCADE ON DELETE CASCADE))");
			
			//Oppretter Group-tabellen		
			makeStatement("CREATE TABLE Group"
						+ "(GROUPID INT	NOT NULL,"
						+ "NAME VARCHAR(20)	NOT NULL,"
						+ "PRIMARY KEY (GROUPID));");
			
			//Oppretter Event-tabellen		
			makeStatement("CREATE TABLE Event"
						+ "(EVENTID INT NOT NULL"
						+ "NAME VARCHAR(20) NOT NULL"
						+ "START		CHAR(14)		NOT NULL,"
						+ "END			CHAR(14)		NOT NULL,"
						+"DESCRIPTION	VARCHAR(100),"
						+"ROOMID		INT,"
						+"PRIMARY KEY (EVENTID),"
						+"FOREIGN KEY (ROOMID) REFERENCES ROOM(ROOMID) ON UPDATE CASCADE))");

			makeStatement("CREATE TABLE Event"
			
	
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
	private static ResultSet makeQuery(String query) {
		ResultSet res = null;
		try {
			Connection conn = getConnection();
			Statement st = conn.createStatement();
			res = st.executeQuery(query);
			//conn.close(); // Må kommenteres ut for at getIdNameMap skal fungere...
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
