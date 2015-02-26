package DB;

import java.sql.ResultSet;
import java.util.ArrayList;

public class DatabaseHandler {
	
	public ResultSet getPersonEvents(String name) {
		try {
			String query = "SELECT Event.Name, Event.Description, Event.Start, Event.End \n"
						+  "FROM Person, Event, PersonEvent"
						+  "WHERE Person.ID = PersonEvent.PersonID "
						+  "AND EventID = PersonEvent.EventID"
						+  "AND Person.Name =" + name
						+  "ORDER BY Event.Start";
			ResultSet rs = Database.makeQuery(query);
			return rs;
		}
			
		catch(Exception e) {
			throw new IllegalArgumentException("This name does not exist.");
		}
	}

	
	public ResultSet getGroupEvents(String id) {
		try {
			String query = "SELECT Event.Name, Event.Description, Event.Start, Event.End \n"
					+  "FROM Group, Event, GroupEvent"
					+  "WHERE Group.ID = GroupEvent.GroupID "
					+  "AND EventID = GroupEvent.EventID"
					+  "AND Group.ID = " + id
					+  "ORDER BY Event.Start";
			ResultSet rs = Database.makeQuery(query);
			return rs;
			
		}
		catch(Exception e) {
			throw new IllegalArgumentException("This name does not exist.");
		}
	}
	
	public ResultSet getRoomInformation(int ID) {
		try {
			String query = "Room.Description \n"
					+  "FROM Room"
					+  "WHERE Room.RoomID = " + ""+ID";
					
			ResultSet rs = Database.makeQuery(query);
			return rs;
			
		}
		catch(Exception e) {
			throw new IllegalArgumentException("This name does not exist.");
		}
	}
	
	
	
}
