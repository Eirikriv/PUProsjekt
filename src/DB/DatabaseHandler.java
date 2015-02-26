package DB;

import java.sql.ResultSet;
import java.util.ArrayList;

public class DatabaseHandler {
	
	
	public void getPersonEvents(String name) {
		try {
			String query = "SELECT Event.Name, Event.Description, Event.Start, Event.End \n"
						+  "FROM Person, Event, PersonEvent"
						+  "WHERE Person.ID = PersonEvent.PersonID "
						+  "AND EventID = PersonEvent.EventID"
						+  "AND Person.Name =" + name
						+  "ORDER BY Event.Start";
			ResultSet rs = Database.makeQuery(query);
			
			ArrayList<String> EventName = new ArrayList<String>();
			ArrayList<String> EventDesc = new ArrayList<String>();
			ArrayList<String> EventStart = new ArrayList<String>();
			ArrayList<String> EventEnd = new ArrayList<String>();
			
			while(rs.next()) {
				EventName.add(rs.getString(1));
				EventDesc.add(rs.getString(2));
				EventStart.add(rs.getString(3));
				EventEnd.add(rs.getString(4));
			}
			
			for (int i=0; i<EventName.size(); i++) {
				
			}
		}
		catch(Exception e) {
			throw new IllegalArgumentException("This name does not exist.");
		}
	}
}
