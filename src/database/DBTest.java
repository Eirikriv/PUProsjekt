package database;

//import java.sql.ResultSet;
import java.sql.SQLException;

public class DBTest {
	public static void main(String[] args) throws SQLException {
		Database.initializeDatabase();
		PersonDatabaseHandler pdbh = new PersonDatabaseHandler();
		pdbh.add(new String[]{"cecilite", "Cecilie Teisberg", "password"});
		pdbh.add(new String[]{"eirikriv", "Eirik Rivedal", "password1"});
		pdbh.add(new String[]{"niconiel", "Nicolaj Nielsen", "password2"});
		pdbh.add(new String[]{"martibni", "Martin Børø Nilsen", "password3"});
		pdbh.add(new String[]{"andersro", "Anders Rønold", "password4"});
		GroupDatabaseHandler gdbh = new GroupDatabaseHandler();
		String groupID = gdbh.add(new String[]{"PU-gruppe 58"});
		gdbh.addGroupMember(groupID, "cecilite");
		gdbh.addGroupMember(groupID, "eirikriv");
		gdbh.addGroupMember(groupID, "niconiel");
		gdbh.addGroupMember(groupID, "martibni");
		gdbh.addGroupMember(groupID, "andersro");
		RoomDatabaseHandler rdbh = new RoomDatabaseHandler();
		rdbh.add(new String[]{"R E5-103", "20", "Grupperom"});
		EventDatabaseHandler edbh = new EventDatabaseHandler();
		String eventID = edbh.add(new String[]{"PU-gruppemøte", "2015-03-02 12:15", "2015-03-02 14:00", "Fortsettelse av jobbing med kalendersystem", "RE5-103"});
		edbh.addPerson(eventID, "cecilite");
		edbh.addPerson(eventID, "eirikriv");
		edbh.addPerson(eventID, "niconiel");
		edbh.addPerson(eventID, "martibni");
		edbh.addPerson(eventID, "andersro");
//		String query = "SELECT Person.Name, Event.Name, Start, End, Description\n"
//				+ "FROM Person, PersonEvent, Event\n"
//				+ "WHERE Person.PersonID = PersonEvent.PersonID AND Event.EventID = PersonEvent.EventID\n"
//				+ "GROUP BY	Person.Name;";
//		ResultSet rs = Database.makeQuery(query);
//		while (rs.next()){
//			System.out.println(rs.getString(1));
//			System.out.println(rs.getString(2));
//			System.out.println(rs.getString(3));
//			System.out.println(rs.getString(4));
//			System.out.println(rs.getString(5));
//			System.out.println();
//		}
//		query = "SELECT	Groups.Name, Person.Name\n"
//				+ "FROM Groups, PersonInGroup, Person\n"
//				+ "WHERE Groups.GroupID = PersonInGroup.GroupID AND\n"
//				+ "Person.PersonID = PersonInGroup.PersonID\n"
//				+ "GROUP BY	Groups.Name;";
//		rs = Database.makeQuery(query);
//		while (rs.next()){
//			System.out.println(rs.getString(1));
//			System.out.println(rs.getString(2));
//		}
//		query = "SELECT	Person.Name, Event.Name, Start, End, Description\n"
//				+ "FROM Groups, GroupEvent, Event, PersonInGroup, Person\n"
//				+ "WHERE Groups.GroupID = GroupEvent.GroupID AND\n"
//				+ "Event.EventID = GroupEvent.EventID AND\n"
//				+ "Person.PersonID = PersonInGroup.PersonID AND Groups.GroupID = PersonInGroup.GroupID\n"
//				+ "GROUP BY	Person.Name;";
//		rs = Database.makeQuery(query);
//		while (rs.next()) {
//			System.out.println(rs.getString(1));
//			System.out.println(rs.getString(2));
//			System.out.println(rs.getString(3));
//			System.out.println(rs.getString(4));
//			System.out.println(rs.getString(5));
//			System.out.println();
//		}
//		query = "SELECT	Event.Name, Event.RoomID, Capacity, Room.Description\n"
//				+ "FROM Event, Room\n"
//				+ "WHERE Event.RoomID = Room.RoomID;";
//		rs = Database.makeQuery(query);
//		while (rs.next()){
//			System.out.println(rs.getString(1));
//			System.out.println(rs.getString(2));
//			System.out.println(rs.getString(3));
//			System.out.println(rs.getString(4));
//			System.out.println();;
//		}
	}
}
