package database;

import java.sql.ResultSet;
import java.util.ArrayList;

public class RoomDatabaseHandler implements DatabaseHandler {

	//Henter ut info om rom med RomID id
	//Returnerer en liste på formen [[RoomId, capacity, description], ...]
	public ArrayList<String> get(String roomID) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			String query = "SELECT *\n"
					+  "FROM Room\n"
					+  "WHERE Room.RoomID = " + roomID;
			ResultSet rs = Database.makeQuery(query);
			while (rs.next()) {
				for (int i = 2; i <= 3; i++)
					list.add(rs.getString(i));
			}
			return list;
		}
		catch(Exception e) {
			throw new IllegalArgumentException("RoomID " + roomID + " does not exist");
		}
	}

	@Override
	public String add(String[] info) {
		try {
			Database.makeStatement("INSERT INTO Room\n"
						+ "VALUES( '"+info[0]+"', '"+ info[1] +"' , '" + info[2] + "');");
			return info[0];
		}
		catch (Exception e){
			return null;
		}
	}

	@Override
	public boolean update(String[] info) {
		try {
			Database.makeStatement("UPDATE Room\n"
								 + "SET RoomId = '"+ info[0]+"', Capacity = '"+ info[1] +"'\n"
								 + "WHERE RoomId = '"+info[0]+"';");
		return true;
		}
		catch (Exception e){
		return false;
		}
	}

	@Override
	public boolean remove(String primaryKey) {
		try {
			Database.makeStatement("DELETE FROM Room\n"
								+  "WHERE roomID = '"+primaryKey+"';");
		return true;
		}
		catch (Exception e){
		return false;
		}
	}
	
	public ArrayList<String> getAvailableRooms(String start, String end, String capacity) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			String query = "SELECT Room.RoomID "
						+  "FROM Room "
						+  "WHERE Room.RoomID NOT IN"
						+  "(SELECT Room.RoomID "
						+  "FROM Room, Event "
						+  "WHERE Room.RoomID = Event.RoomID "
						+  "AND Event.Start < '" + end + "' AND Event.End > '" +start+ "')"
						+  "AND Room.Capacity > " + capacity + ";";
//						+  "OR (Event.Start < '" +start+ "' AND Event.End > '" + start + "') "
//						+  "OR (Event.Start < '" + end + "' AND Event.End > '" + end + "')););";
			
			ResultSet rs = Database.makeQuery(query);
			while (rs.next()) {
				list.add(rs.getString(1));
			}
			if (list.size() == 0)
				return null;
			return list;
		}
		catch(Exception e) {
			throw new IllegalArgumentException("Does not work");
		}
	}

	public ArrayList<ArrayList<String>> getRoomEvents(String groupID) {
		// TODO Auto-generated method stub
		return null;
	}

}
