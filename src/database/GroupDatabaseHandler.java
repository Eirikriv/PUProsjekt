package database;

import java.sql.ResultSet;
import java.util.ArrayList;

public class GroupDatabaseHandler implements DatabaseHandler {
	
	//Returnerer en liste på formen [name]
	public ArrayList<String> get(String GroupID) {
		ArrayList<String> groupList = new ArrayList<String>();
		try {
			String query = "SELECT Name FROM Groups WHERE Groups.GroupID = " + GroupID + ";";
			ResultSet rs = Database.makeQuery(query);
			while (rs.next()) {
				groupList.add(rs.getString(1));
			}
			return groupList;
		}
		catch(Exception e) {
			throw new IllegalArgumentException("Group: " + GroupID + " does not exist.");
		}
	}

	//Tar inn en liste på formen [name]
	public String add(String[] info) {
		String returnString = "";
		try {
			Database.makeStatement("INSERT INTO Groups(Name)"
						+ "VALUES('" + info[0] + "');");
			ResultSet rs = Database.makeQuery("SELECT MAX(GroupID) FROM Groups;");
			while (rs.next()) {
				returnString = rs.getString(1);
			}
			if(returnString.isEmpty()){
				throw new IllegalStateException();}else{return returnString;}
		}
		catch (Exception e){
			e.printStackTrace();
			throw new IllegalStateException();
		}
	}

	@Override
	public boolean update(String[] info) {
		try {
			Database.makeStatement("UPDATE Groups "
								 + "SET name = '"+ info[1]+ "''"
								 + "WHERE GroupID = '" + info[0] + "';");
			return true;
		}
		catch (Exception e){
			return false;
		}
	}

	@Override
	public boolean remove(String GroupID) {
		try {
			Database.makeStatement("DELETE FROM Groups "
								+  "WHERE GroupID = '" + GroupID + "';");
			return true;
		}
		catch (Exception e){
			return false;
		}
	}
	
	//Henter alle events til en gruppe
	//Returnerer en liste på formen [[name, description, start, end], ...]
	public ArrayList<String> getGroupEvents(String groupID) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			String q = "SELECT Event.EventID "
					+  "FROM Group, Event, GroupEvent "
					+  "WHERE Group.GroupID = GroupEvent.GroupID "
					+  "AND EventID = GroupEvent.EventID "
					+  "AND Group.GroupID = " + groupID + " "
					+  "ORDER BY Event.Start;";
			ResultSet rs = Database.makeQuery(q);
			while (rs.next()) {
					list.add(rs.getString(1));
			}
			return list;
			
		}
		catch(Exception e) {
			throw new IllegalArgumentException("GroupID " + groupID + " does not exist");
		}
	}
	
	//Henter ut alle gruppemedlemer av en gruppe
	//Returnerer en liste på [[], ...]
	public ArrayList<String> getGroupMembers(String groupID) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			String query = "SELECT Person.Username, Person.Name "
						+  "FROM Person, Groups, PersonInGroup "
						+  "WHERE Person.Username = PersonInGroup.Username "
						+  "AND Groups.GroupID = PersonInGroup.GroupID "
						+  "AND Groups.GroupID = '" + groupID + "';";
			ResultSet rs = Database.makeQuery(query);
			while (rs.next()) {
				list.add(rs.getString(1) + "<" + rs.getString(2) + ">");
			}
			return list;
		}
		catch(Exception e) {
			throw new IllegalArgumentException("This name does not exist.");
		}
	}
	
	public boolean addGroupMember(String groupID, String username) {
		try {
			String stmt = "INSERT INTO PersonInGroup "
					+ "VALUES('" + username + "'," + groupID + ", 'You have been added to the group');";
			Database.makeStatement(stmt);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	public boolean removeGroupMember(String groupID, String username) {
		try {
			String stmt = "DELETE FROM PersonInGroup "
					+ "WHERE GroupID = " + groupID + " AND Username = '" + username + "';";
			Database.makeStatement(stmt);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	public ArrayList<String> getAllGroups() {
		ArrayList<String> info = new ArrayList<String>();
		try {
			String query = "SELECT Groups.Name, Groups.GroupID "
					+ "FROM Groups;";
			ResultSet rs = Database.makeQuery(query);
			while(rs.next()) {
				info.add(rs.getString(2)+":"+rs.getString(1));

			}
			return info;
		}
			
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Something went wrong");
		}
	}
}
