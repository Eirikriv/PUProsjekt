package DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Core.Person;

public class DatabaseHandler {
	
	public static ArrayList<String> getPersonInformation(String name) {
		try {
			String query = "SELECT * FROM Person WHERE Person.Name = '"+name+"'";
			ResultSet rs = Database.makeQuery(query);
			ArrayList<String> list = new ArrayList<String>();
			list.add(rs.getString(1));
			list.add(rs.getString(2));
			list.add(rs.getString(3));
			list.add(rs.getString(4));
			return list;
		}
		catch(Exception e) {
			throw new IllegalArgumentException("This name does not exist.");
		}
	}

	
	public static ResultSet getPersonEvents(int id) {
		Database.initializeDatabase();
		try {
			String query = "SELECT Event.Name, Event.Description, Event.Start, Event.End\n"
						+  "FROM Person, Event, PersonEvent\n"
						+  "WHERE Person.PersonID = PersonEvent.PersonID\n"
						+  "AND Event.EventID = PersonEvent.EventID\n"
						+  "AND Person.PersonID = '" + Integer.toString(id) + "'\n"
						+  "ORDER BY Event.Start";
			ResultSet rs = Database.makeQuery(query);
			return rs;
		}
		catch(Exception e) {
			throw new IllegalArgumentException("This name does not exist.");
		}
	}

	public static int addPerson(String name, String username, String pw ) {
		Database.makeStatement("INSERT INTO Person(Name, Username, Password)\n"
					+ "VALUES('"+ name+"', '"+username+"', '"+pw+"');");
	
		String query = "SELECT Person.PersonID"
				+  "FROM Person\n"
				+  "WHERE Person.Name = '"+name+"'\n";
			
		ResultSet rs = Database.makeQuery(query);
		try {
			return Integer.parseInt(rs.getString(1));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new IllegalArgumentException("Cant get the PersonID number back from database");
	}
		
	public static void addPersonToGroup(Person person) {
		Database.makeStatement("INSERT INTO Group(Name, Username, Password)\n"
				+ "VALUES('"+ person.getName()+"');");
	}
	
	public static ResultSet getGroupEvents(String id) {
		try {
			String q = "SELECT Event.Name, Event.Description, Event.Start, Event.End\n"
					+  "FROM Group, Event, GroupEvent\n"
					+  "WHERE Group.ID = GroupEvent.GroupID\n"
					+  "AND EventID = GroupEvent.EventID\n"
					+  "AND Group.ID = " + id
					+  "\nORDER BY Event.Start";
			ResultSet reset = Database.makeQuery(q);
			return reset;
			
		}
		catch(Exception e) {
			throw new IllegalArgumentException("This name does not exist.");
		}
	}
	
	public static ResultSet getRoomInformation(int ID) {
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
	
	public static ResultSet getGroupMembers(int ID) {
		try {
			String query = "SELECT Person.Name\n"
						+  "FROM Person, Group, PersonGroup\n"
						+  "WHERE Person.PersonID = PersonGroup.PersonID\n"
						+  "AND "+Integer.toString(ID)+" = PersonGroup.GroupID\n"
						+  "ORDER BY Person.Name";
			ResultSet rs = Database.makeQuery(query);
			return rs;
		}
		catch(Exception e) {
			throw new IllegalArgumentException("This name does not exist.");
		}
	}
}
