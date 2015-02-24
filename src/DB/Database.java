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

			
			// Oppretter tabellene
			makeStatement("CREATE TABLE koie"
						+ "(id SMALLINT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
						+ "name VARCHAR(255) NOT NULL, "
						+ "num_beds SMALLINT NOT NULL, "
						+ "num_seats SMALLINT, "
						+ "year SMALLINT, "
						+ "coordinates VARCHAR(255), "
						+ "terreng VARCHAR(255), "
						+ "sykkel VARCHAR(255), " 
						+ "topptur VARCHAR(255), "
						+ "jakt_og_fiske VARCHAR(255), "
						+ "spesialiteter VARCHAR(255))");

			
	
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
