package DB;

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
				for (int i = 1; i <= 3; i++)
					list.add(rs.getString(i));
			}
			return list;
		}
		catch(Exception e) {
			throw new IllegalArgumentException("RoomID " + roomID + " does not exist");
		}
	}

	@Override
	public boolean add(String[] info) {
		try {
			Database.makeStatement("INSERT INTO Room\n"
						+ "VALUES( '"+info[0]+"', '"+ info[1] +"');");
			return true;
		}
		catch (Exception e){
			return false;
		}
	}

	@Override
	public boolean update(String[] info) {
		try {
			Database.makeStatement("UPDATE Room\n"
								 + "SET RoomId = '"+ info[0]+", Capacity = '"+ info[1] +"'\n"
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

}
