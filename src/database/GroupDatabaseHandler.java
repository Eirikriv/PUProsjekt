package database;

import java.sql.ResultSet;
import java.util.ArrayList;

public class GroupDatabaseHandler implements DatabaseHandler {
	@Override
	public ArrayList<String> get(String GroupID) {
		ArrayList<String> groupList = new ArrayList<String>();
		try {
			String query = "SELECT * FROM Groups WHERE Groups.GroupID = '" + GroupID + "';";
			ResultSet rs = Database.makeQuery(query);
			while (rs.next()) {
				for (int i = 1; i <= 2; i++)
					groupList.add(rs.getString(i));
			}
			return groupList;
		}
		catch(Exception e) {
			throw new IllegalArgumentException("Group: " + GroupID + " does not exist.");
		}
	}

	@Override
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
	public ArrayList<ArrayList<String>> getGroupEvents(String groupID) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		try {
			String q = "SELECT Event.Name, Event.Description, Event.Start, Event.End\n"
					+  "FROM Group, Event, GroupEvent\n"
					+  "WHERE Group.GroupID = GroupEvent.GroupID\n"
					+  "AND EventID = GroupEvent.EventID\n"
					+  "AND Group.GroupID = " + groupID
					+  "\nORDER BY Event.Start;";
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
					+ "VALUES('" + username + "'," + groupID + ");";
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
}
