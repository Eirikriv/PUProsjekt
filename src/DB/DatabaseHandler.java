package DB;

import java.sql.ResultSet;

public class DatabaseHandler {
	
	public static ResultSet getPersonEvents(String name) {
		Database.initializeDatabase();
		try {
			String query = "SELECT Event.Name, Event.Description, Event.Start, Event.End\n"
						+  "FROM Person, Event, PersonEvent\n"
						+  "WHERE Person.PersonID = PersonEvent.PersonID\n"
						+  "AND Event.EventID = PersonEvent.EventID\n"
						+  "AND Person.Name = '" + name + "'\n"
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
			String query = "SELECT Event.Name, Event.Description, Event.Start, Event.End\n"
					+  "FROM Group, Event, GroupEvent\n"
					+  "WHERE Group.ID = GroupEvent.GroupID\n"
					+  "AND EventID = GroupEvent.EventID\n"
					+  "AND Group.ID = " + id
					+  "\nORDER BY Event.Start";
			ResultSet rs = Database.makeQuery(query);
			return rs;
			
		}
		catch(Exception e) {
			throw new IllegalArgumentException("This name does not exist.");
		}
	}
	
	public ResultSet getRoomInformation(int ID) {
		try {
			String query = "Room.Description\n"
					+  "FROM Room\n"
					+  "WHERE Room.RoomID = " + ID;
					
			ResultSet rs = Database.makeQuery(query);
			return rs;
			
		}
		catch(Exception e) {
			throw new IllegalArgumentException("This name does not exist.");
		}
	}
	}
	
	
	
}
