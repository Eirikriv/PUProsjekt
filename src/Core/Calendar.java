package Core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//import java.sql.ResultSetMetaData;

import DB.DatabaseHandler;

public class Calendar {
	String name = "Cecilie Teisberg";
	
	public ArrayList<ArrayList<String>> outputPersonEvents() throws SQLException {
		ResultSet rs = DatabaseHandler.getPersonEvents(name);
//		ResultSetMetaData rsmd = rs.getMetaData();
		
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		ArrayList<String> eventName = new ArrayList<String>();
		ArrayList<String> eventDesc = new ArrayList<String>();
		ArrayList<String> eventStart = new ArrayList<String>();
		ArrayList<String> eventEnd = new ArrayList<String>();
		
//		int x = 0;
		while(rs.next()) {
			eventName.add(rs.getString(1));
			eventDesc.add(rs.getString(2));
			eventStart.add(rs.getString(3));
			eventEnd.add(rs.getString(4));
//			x++;
		}
		result.add(eventName);
		result.add(eventDesc);
		result.add(eventStart);
		result.add(eventEnd);
//		String output = rsmd.getColumnLabel(1) + "		" + rsmd.getColumnLabel(2) + "		" + rsmd.getColumnLabel(3) + "		" + rsmd.getColumnLabel(4) + "\n";
//		for (int i=0; i< x; i++) {
//			output += eventName.get(i) + "		" + eventDesc.get(i) + "		" + eventStart.get(i) + "		" + eventEnd.get(i);
//		}
//		
//		System.out.println(output);
		return result;
	}
	
}
