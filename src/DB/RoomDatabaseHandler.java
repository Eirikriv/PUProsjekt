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

}
