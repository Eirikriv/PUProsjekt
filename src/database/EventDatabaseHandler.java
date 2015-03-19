package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

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
			Database.makeStatement("UPDATE Event "
								 + "SET Title = '"+ eventInfo[1]+"', Start = '"+ eventInfo[2] + "', End = '"+eventInfo[3] +"', Description = '"+ eventInfo[4]+ "', RoomID = '"+eventInfo[5]+"' "
								 + "WHERE EventID = "+eventInfo[0]+";");
			Database.makeStatement("UPDATE PersonEvent "
									+"SET PersonEvent.Notification = 'This event has been updated' "
									+"WHERE PersonEvent.EventID = "+eventInfo[0]+";");
		return true;
		}
		catch (Exception e){
			System.out.println("yolo");
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
					+ "VALUES('" + username + "', " + eventID + ", " + "'You were added to the event');";
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
					+ "WHERE EventID = " + eventID + " AND Username = '" + username + "';";
			Database.makeStatement(stmt);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	public boolean removeAllPersons(String eventID){
		ArrayList<String> list = new ArrayList<String>();
		String query = "SELECT Person.Username FROM Person, Event, PersonEvent WHERE Person.Username = PersonEvent.Username AND PersonEvent.EventID = "+eventID+";";
		ResultSet rs = Database.makeQuery(query);
		try {
			while(rs.next()) {
				list.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(String s : list) {
			removePerson(eventID,s);
		}
		return true;
	}
	
	public ArrayList<String> getAllInvited(String eventID) {
		ArrayList<String> invited = new ArrayList<String>();
		try {
			String query = "SELECT Username FROM PersonEvent "
					+ "WHERE EventID = " + eventID + " AND "
					+ "Status = 0;";
			ResultSet rs = Database.makeQuery(query);
			while (rs.next())
				invited.add(rs.getString(1));
			return invited;
		} catch(Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}
	
	public ArrayList<String> getAllParticipants(String eventID) {
		ArrayList<String> participants = new ArrayList<String>();
		try {
			String query = "SELECT Username FROM PersonEvent "
					+ "WHERE EventID = " + eventID + " AND "
					+ "Status = 1;";
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
					+ "WHERE EventID = " + eventID + " AND Status = -1;";
			ResultSet rs = Database.makeQuery(query);
			while (rs.next())
				declined.add(rs.getString(1));
			return declined;
		} catch(Exception e) {
			e.printStackTrace();
			throw new IllegalStateException();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void eventStartsSoon(String username) {
//		PersonDatabaseHandler p = new PersonDatabaseHandler();
//		ArrayList<String> eventID = p.getPersonEvents(username);
//		EventDatabaseHandler e = new EventDatabaseHandler();
		ArrayList<ArrayList<String>> events = new ArrayList<ArrayList<String>>(); 
//		for (String id : eventID) {
//			String time ="";
//			time += e.get(id).get(2);
//			System.out.println(time);
//			events.add(time);
//		}
//		
		try {
			String query = "SELECT Start, Event.EventID FROM Event, Person, PersonEvent "
					+ "WHERE Event.EventID = PersonEvent.EventID AND Person.Username = PersonEvent.Username AND PersonEvent.Username = '"+username+"' AND (Status = 0 OR Status = 1);";
			ResultSet rs = Database.makeQuery(query);
			while (rs.next()) {				
				ArrayList<String> list = new ArrayList<String>();
				list.add(rs.getString(1));
				list.add(rs.getString(2));
				events.add(list);
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new IllegalStateException();
		}
		
		Date d = new Date();
		for(int i=0; i<events.size(); i++) {
			LocalDate startDate = LocalDate.parse(events.get(i).get(0).split(" ")[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			LocalTime startHour = LocalTime.parse(events.get(i).get(0).split(" ")[1], DateTimeFormatter.ofPattern("HH:mm"));
			System.out.println("OK");
			if (startDate.getMonthValue()==(1+d.getMonth()) && startDate.getYear() == (1900+d.getYear()) && startDate.getDayOfMonth() == d.getDate() && (startHour.getHour()-d.getHours())<=2) {
				try {
					String statement = "UPDATE PersonEvent SET Notification = 'Event starts in less than 2 hours' WHERE Username = '" + username + "' AND EventID = " + events.get(i).get(1) + ";";
					Database.makeStatement(statement);
				} catch(Exception ex) {
					throw new IllegalArgumentException();
				}
			}
			System.out.println(startDate.getMonthValue() <= (1+d.getMonth()));
			if (startDate.getMonthValue() <= (1+d.getMonth()) && startDate.getYear() <= (1900+d.getYear()) && startDate.getDayOfMonth() <= d.getDate() && (Math.abs(startHour.getHour())-Math.abs(d.getHours()))<0){
				try {
					String statement = "UPDATE PersonEvent SET Notification = NULL WHERE Username = '" + username + "' AND EventID = " + events.get(i).get(1) + ";";
					Database.makeStatement(statement);
				} catch(Exception ex) {
					throw new IllegalArgumentException();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		EventDatabaseHandler e = new EventDatabaseHandler();
		e.eventStartsSoon("cecilite");
	}
}
