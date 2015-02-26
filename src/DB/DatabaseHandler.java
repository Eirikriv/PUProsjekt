package DB;

import java.sql.ResultSet;
import java.util.ArrayList;

public class DatabaseHandler {
	
	//Henter ut all info om person med PersonID id
	//Returnerer en liste på formen [[name, username, password],...]
	public static ArrayList<ArrayList<String>> getPersonInformation(int id) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		try {
			String query = "SELECT * FROM Person WHERE Person.PersonID = " + id + "";
			ResultSet rs = Database.makeQuery(query);
			while (rs.next()) {
				ArrayList<String> temp = new ArrayList<String>();
				for (int i = 2; i <= 4; i++)
					temp.add(rs.getString(i));
				list.add(temp);
			}
			return list;
		}
		catch(Exception e) {
			throw new IllegalArgumentException("PersonID " + id + " does not exist.");
		}
	}

	//Henter ut alle hendelser for person med PersonID id
	//Returnerer en liste på formen [[name, description, start, end], ...] 
	public static ArrayList<ArrayList<String>> getPersonEvents(int id) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		try {
			String query = "SELECT Event.Name, Event.Description, Event.Start, Event.End\n"
						+  "FROM Person, Event, PersonEvent\n"
						+  "WHERE Person.PersonID = PersonEvent.PersonID\n"
						+  "AND Event.EventID = PersonEvent.EventID\n"
						+  "AND Person.PersonID = '" + Integer.toString(id) + "'\n"
						+  "ORDER BY Event.Start";
			ResultSet rs = Database.makeQuery(query);
			while (rs.next()) {
				ArrayList<String> temp = new ArrayList<String>();
				for (int i = 1; i <= 4; i++)
					temp.add(rs.getString(i));
				list.add(temp);
			}
			return list;
		}
		catch(Exception e) {
			throw new IllegalArgumentException("PersonID " + id + " does not exist.");
		}
	}
	
	//Legger til person med name, username og pw
	//Returnerer PersonID til personen eller -1 hvis den ikke finnes (altså ikke ble lagt til)
	public static int addPerson(String name, String username, String pw ) {
		try {
			Database.makeStatement("INSERT INTO Person(Name, Username, Password)\n"
						+ "VALUES('"+ name+"', '"+username+"', '"+pw+"');");
			
			String query = "SELECT *"
					+  "FROM Person\n"
					+  "WHERE Person.Name = '"+name+"'\n";		
			ResultSet rs = Database.makeQuery(query);
			while (rs.next()) {
				if (rs.getString(3).equals(username) && rs.getString(4).equals(pw))
					return rs.getInt(1);
			}
			return -1;
		}
		catch (Exception e){
			throw new IllegalArgumentException("Cannot get the PersonID number back from database");
		}
	}
		
	//Henter alle events til en gruppe
	//Returnerer en liste på formen [[name, description, start, end], ...]
	public static ArrayList<ArrayList<String>> getGroupEvents(String id) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		try {
			String q = "SELECT Event.Name, Event.Description, Event.Start, Event.End\n"
					+  "FROM Group, Event, GroupEvent\n"
					+  "WHERE Group.GroupID = GroupEvent.GroupID\n"
					+  "AND EventID = GroupEvent.EventID\n"
					+  "AND Group.GroupID = " + id
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
			throw new IllegalArgumentException("GroupID " + id + " does not exist");
		}
	}
	
	//Henter ut info om rom med RomID id
	//Returnerer en liste på formen [[RoomId, capacity, description], ...]
	public static ArrayList<ArrayList<String>> getRoomInformation(int id) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		try {
			String query = "SELECT *\n"
					+  "FROM Room\n"
					+  "WHERE Room.RoomID = " + id;
			ResultSet rs = Database.makeQuery(query);
			while (rs.next()) {
				ArrayList<String> temp = new ArrayList<String>();
				for (int i = 1; i <= 3; i++)
					temp.add(rs.getString(i));
				list.add(temp);
			}
			return list;
		}
		catch(Exception e) {
			throw new IllegalArgumentException("RoomID " + id + " does not exist");
		}
	}
	
	
	//HEnter ut alle gruppemedlemer av en gruppe
	//Returnerer en liste på [[], ...]
	public static ArrayList<ArrayList<String>> getGroupMembers(int ID) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		try {
			String query = "SELECT Person.Name\n"
						+  "FROM Person, Groups, PersonGroup\n"
						+  "WHERE Person.PersonID = PersonInGroup.PersonID\n"
						+  "AND Groups.GroupID = PersonGroup.GroupID AND GroupID = " + ID + "\n"
						+  "GROUP BY Groups.GroupID";
			ResultSet rs = Database.makeQuery(query);
			while (rs.next()) {
				ArrayList<String> temp = new ArrayList<String>();
			}
			return list;
		}
		catch(Exception e) {
			throw new IllegalArgumentException("This name does not exist.");
		}
	}
}