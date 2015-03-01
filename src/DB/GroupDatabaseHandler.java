package DB;

import java.sql.ResultSet;
import java.util.ArrayList;

public class GroupDatabaseHandler implements DatabaseHandler {
	@Override
	public ArrayList<String> get(String primaryKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(String[] info) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(String[] info) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(String primaryKey) {
		// TODO Auto-generated method stub
		return false;
	}
	
	//Henter alle events til en gruppe
	//Returnerer en liste på formen [[name, description, start, end], ...]
	public static ArrayList<ArrayList<String>> getGroupEvents(String groupID) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		try {
			String q = "SELECT Event.Name, Event.Description, Event.Start, Event.End\n"
					+  "FROM Group, Event, GroupEvent\n"
					+  "WHERE Group.GroupID = GroupEvent.GroupID\n"
					+  "AND EventID = GroupEvent.EventID\n"
					+  "AND Group.GroupID = " + groupID
					+  "\nORDER BY Event.Start";
			ResultSet rs = Database.makeQuery(q);
			while (rs.next()) {
				ArrayList<String> temp = new ArrayList<String>();
				for (int i = 1; i <= 4; i++)
					temp.add(rs.getString(i));
				list.add(temp);
			}
			return list;
			
		}
		catch(Exception e) {
			throw new IllegalArgumentException("GroupID " + groupID + " does not exist");
		}
	}
	
	//Henter ut alle gruppemedlemer av en gruppe
	//Returnerer en liste på [[], ...]
	public static ArrayList<String> getGroupMembers(int groupID) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			String query = "SELECT Person.Username\n"
						+  "FROM Person, Groups, PersonGroup\n"
						+  "WHERE Person.PersonID = PersonInGroup.PersonID\n"
						+  "AND Groups.GroupID = PersonGroup.GroupID AND GroupID = " + groupID + "\n"
						+  "GROUP BY Groups.GroupID";
			ResultSet rs = Database.makeQuery(query);
			while (rs.next()) {
				list.add(rs.getString(1));
			}
			return list;
		}
		catch(Exception e) {
			throw new IllegalArgumentException("This name does not exist.");
		}
	}
}
