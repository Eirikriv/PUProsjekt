package Core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.sql.ResultSetMetaData;

import DB.DatabaseHandler;

public class Calender {
	String name = "Cecilie Teisberg";
	
	public void printPersonEvents() throws SQLException {
		ResultSet rs = DatabaseHandler.getPersonEvents(name);
		ResultSetMetaData rsmd = rs.getMetaData();
		
		ArrayList<String> eventID = new ArrayList<String>();
		ArrayList<String> eventName = new ArrayList<String>();
		ArrayList<String> eventDesc = new ArrayList<String>();
		ArrayList<String> eventStart = new ArrayList<String>();
		ArrayList<String> eventEnd = new ArrayList<String>();
		int x = 0;
		while(rs.next()) {
			eventID.add(rs.getString(1));
			eventName.add(rs.getString(2));
			eventStart.add(rs.getString(3));
			eventEnd.add(rs.getString(4));
			eventDesc.add(rs.getString(5));
			x++;
		}
		String output = rsmd.getColumnLabel(1) + "		" + rsmd.getColumnLabel(2) + "		" + rsmd.getColumnLabel(3) + "		" + rsmd.getColumnLabel(4) + "		" + rsmd.getColumnLabel(5);
		for (int i=0; i< x; i++) {
			output += eventID.get(i) + "		" + eventName.get(i) + "		" + eventStart.get(i) + "		" + eventEnd.get(i) + "		" + eventDesc.get(i);
		}
		
		System.out.println(output);
	}
	
}
