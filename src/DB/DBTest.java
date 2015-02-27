package DB;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBTest {
	public static void main(String[] args) throws SQLException {
		Database.initializeDatabase();
		String query = "SELECT Person.Name, Event.Name, Start, End, Description\n"
				+ "FROM Person, PersonEvent, Event\n"
				+ "WHERE Person.PersonID = PersonEvent.PersonID AND Event.EventID = PersonEvent.EventID\n"
				+ "GROUP BY	Person.Name;";
		ResultSet rs = Database.makeQuery(query);
		while (rs.next()){
			System.out.println(rs.getString(1));
			System.out.println(rs.getString(2));
			System.out.println(rs.getString(3));
			System.out.println(rs.getString(4));
			System.out.println(rs.getString(5));
			System.out.println();
		}
		query = "SELECT	Groups.Name, Person.Name\n"
				+ "FROM Groups, PersonInGroup, Person\n"
				+ "WHERE Groups.GroupID = PersonInGroup.GroupID AND\n"
				+ "Person.PersonID = PersonInGroup.PersonID\n"
				+ "GROUP BY	Groups.Name;";
		rs = Database.makeQuery(query);
		while (rs.next()){
			System.out.println(rs.getString(1));
			System.out.println(rs.getString(2));
		}
		query = "SELECT	Person.Name, Event.Name, Start, End, Description\n"
				+ "FROM Groups, GroupEvent, Event, PersonInGroup, Person\n"
				+ "WHERE Groups.GroupID = GroupEvent.GroupID AND\n"
				+ "Event.EventID = GroupEvent.EventID AND\n"
				+ "Person.PersonID = PersonInGroup.PersonID AND Groups.GroupID = PersonInGroup.GroupID\n"
				+ "GROUP BY	Person.Name;";
		rs = Database.makeQuery(query);
		while (rs.next()) {
			System.out.println(rs.getString(1));
			System.out.println(rs.getString(2));
			System.out.println(rs.getString(3));
			System.out.println(rs.getString(4));
			System.out.println(rs.getString(5));
			System.out.println();
		}
		query = "SELECT	Event.Name, Event.RoomID, Capacity, Room.Description\n"
				+ "FROM Event, Room\n"
				+ "WHERE Event.RoomID = Room.RoomID;";
		rs = Database.makeQuery(query);
		while (rs.next()){
			System.out.println(rs.getString(1));
			System.out.println(rs.getString(2));
			System.out.println(rs.getString(3));
			System.out.println(rs.getString(4));
			System.out.println();;
		}
	}
}
