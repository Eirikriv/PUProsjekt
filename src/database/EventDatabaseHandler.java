package database;

import java.sql.ResultSet;
import java.util.ArrayList;

public class EventDatabaseHandler implements DatabaseHandler {

	@Override
	public ArrayList<String> get(String primaryKey) {
		return null;
	}
	
	//Tar inn en liste på formen [title, start, end, desc, roomID]
	public String add(String[] eventInfo) {
		try {
			Database.makeStatement("INSERT INTO Event(Name, Start, End, Description, RoomID) "
				+ "VALUES( '"+eventInfo[0]+"', '"+ eventInfo[1] +"', '"+ eventInfo[2] +"', '"+ eventInfo[3] +"', '"+ eventInfo[4] +"');");
			ResultSet rs = Database.makeQuery("SELECT MAX(EventID) FROM Event;");
			while(rs.next()) {
				return "" + rs.getInt(1);
			}
			return null;
		}
		catch (Exception e){
			return null;
		}
	}
	
	//Tar inn en liste på formen [name, start, end, desc, roomID]
	public boolean update(String[] eventInfo) {
		try {
			Database.makeStatement("UPDATE Event\n"
								 + "SET name = '"+ eventInfo[0]+", start = '"+ eventInfo[1] + "', end = '"+eventInfo[2] +"', desc = '"+ eventInfo[3]+ "', roomID = '"+eventInfo[4]+"\n"
								 + "WHERE name = '"+eventInfo[0]+"';");
		return true;
		}
		catch (Exception e){
		return false;
		}
	}
	@Override
	public boolean remove(String primaryKey) {
		try {
			Database.makeStatement("DELETE FROM Event"
								+  "WHERE EventID = "+primaryKey+";");
		return true;
		}
		catch (Exception e){
		return false;
		}
	}
	
	public boolean addPerson(String eventID, String username) {
		try {
			String stmt = "INSERT INTO PersonEvent "
					+ "VALUES('" + username + "', '" + eventID + "');";
			Database.makeStatement(stmt);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	public boolean removePerson(String eventID, String username) {
		try {
			String stmt = "DELETE FROM PersonEvent "
					+ "WHERE GroupID = " + eventID + " AND Username = '" + username + "';";
			Database.makeStatement(stmt);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
}
