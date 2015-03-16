package database;

import java.sql.ResultSet;
import java.util.ArrayList;

public class EventDatabaseHandler implements DatabaseHandler {

	//Returnerer en liste på formen [title, owner, start, end, desc, roomID]
	public ArrayList<String> get(String primaryKey) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			String query = "SELECT * FROM Event WHERE Event.EventID = " + primaryKey + ";";
			ResultSet rs = Database.makeQuery(query);
			while (rs.next()) {
				for (int i = 2; i <= 7; i++)
					list.add(rs.getString(i));
			}
			return list;
		}
		catch(Exception e) {
			throw new IllegalArgumentException("EventID: " + primaryKey + " does not exist.");
		}
	}
	
	//Tar inn en liste på formen [title, owner, start, end, desc, roomID]
	public String add(String[] eventInfo) {
		try {
			Database.makeStatement("INSERT INTO Event(Title, Owner, Start, End, Description, RoomID) "
				+ "VALUES( '"+eventInfo[0]+"', '"+ eventInfo[1] +"', '"+ eventInfo[2] +"', '"+ eventInfo[3] +"', '"+ eventInfo[4] +"', '" + eventInfo[5] + "');");
			ResultSet rs = Database.makeQuery("SELECT MAX(EventID) FROM Event;");
			while(rs.next()) {
				return "" + rs.getInt(1);
			}
			return null;
		}
		catch (Exception e){
			return null;
		}
	}
	
	//Tar inn en liste på formen [eventID, title, start, end, desc, roomID]
	public boolean update(String[] eventInfo) {
		try {
			Database.makeStatement("UPDATE Event\n"
								 + "SET title = '"+ eventInfo[1]+", start = '"+ eventInfo[2] + "', end = '"+eventInfo[3] +"', desc = '"+ eventInfo[4]+ "', roomID = '"+eventInfo[5]+"\n"
								 + "WHERE EventID = '"+eventInfo[0]+"';");
		return true;
		}
		catch (Exception e){
		return false;
		}
	}
	@Override
	public boolean remove(String primaryKey) {
		try {
			Database.makeStatement("DELETE FROM Event "
								+  "WHERE EventID = " + primaryKey + ";");
		return true;
		}
		catch (Exception e){
		return false;
		}
	}
	
	public boolean addPerson(String eventID, String username) {
		try {
			String stmt = "INSERT INTO PersonEvent(Username, EventID, Notification) "
					+ "VALUES('" + username + "', '" + eventID + "', " + "You were added to the event);";
			Database.makeStatement(stmt);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	public boolean removePerson(String eventID, String username) {
		try {
			String stmt = "DELETE FROM PersonEvent "
					+ "WHERE GroupID = " + eventID + " AND Username = '" + username + "';";
			Database.makeStatement(stmt);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	public ArrayList<String> getAllParticipants(String eventID) {
		ArrayList<String> participants = new ArrayList<String>();
		try {
			String query = "SELECT Username FROM PersonEvent "
					+ "WHERE EventID = " + eventID + " AND "
					+ "(Status is NULL OR Status = '1');";
			ResultSet rs = Database.makeQuery(query);
			while (rs.next())
				participants.add(rs.getString(1));
			return participants;
		} catch(Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}
	
	public ArrayList<String> getAllDeclined(String eventID) {
		ArrayList<String> declined = new ArrayList<String>();
		try {
			String query = "SELECT Username FROM PersonEvent "
					+ "WHERE EventID = '" + eventID + "' AND Status = 0;";
			ResultSet rs = Database.makeQuery(query);
			while (rs.next())
				declined.add(rs.getString(1));
			return declined;
		} catch(Exception e) {
			e.printStackTrace();
			throw new IllegalStateException();
		}
	}
}
